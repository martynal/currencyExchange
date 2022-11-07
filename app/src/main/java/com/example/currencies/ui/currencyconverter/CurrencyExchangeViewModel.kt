package com.example.currencies.ui.currencyconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencies.data.BalanceRepository
import com.example.currencies.data.ConversionRepository
import com.example.currencies.data.CurrencyRateRepository
import com.example.currencies.data.db.model.CurrencyRateTuple
import com.example.currencies.data.util.NetworkMonitor
import com.example.currencies.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

sealed class CurrencyExchangeState {
    object Loading : CurrencyExchangeState()
    data class Result(
        val currencyToSell: CurrencyRateTuple?,
        val currencyToReceive: CurrencyRateTuple?,
        val receiveValue: String,
        val commissionFee: String,
        val isExchangePossible: Boolean,
    ) : CurrencyExchangeState()

    object Error : CurrencyExchangeState()
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    private val currencyRateRepository: CurrencyRateRepository,
    private val balanceRepository: BalanceRepository,
    private val conversionRepository: ConversionRepository,
    private val getCommissionFeeUseCase: GetCommissionFeeUseCase,
    private val increaseBalanceUseCase: IncreaseBalanceUseCase,
    private val deductBalanceUseCase: DeductBalanceUseCase,
    private val isBelowZeroUseCase: IsBelowZeroUseCase,
    private val getReceiveValueUseCase: GetReceiveValueUseCase,
    private val getCurrencyStringUseCase: GetCurrencyStringUseCase,
    private val networkMonitor: NetworkMonitor,
) : ViewModel() {

    private var job: Job? = null

    val currencyExchangeState: StateFlow<CurrencyExchangeState> = currencyExchangeStateStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CurrencyExchangeState.Loading
        )

    private fun currencyExchangeStateStream(): Flow<CurrencyExchangeState> {
        return combine(
            currencyRateRepository.currencyToSell,
            currencyRateRepository.currencyToReceive,
            snapshotFlow { sellValue }.mapLatest { value -> value.ifBlank { "0" } },
            networkMonitor.isOnline,
        ) { currencyToSell, currencyToReceive, sellValue, isOnline ->
            val receiveValue = if (currencyToReceive != null) getReceiveValueUseCase(
                sellValue,
                currencyToReceive.rate
            ) else ""
            val commissionFee = if (currencyToReceive != null) {
                getCommissionFeeUseCase(
                    currencyToSell.name,
                    sellValue,
                    currencyToReceive.name,
                    receiveValue
                )
            } else "0"
            val isBelowZero = isBelowZeroUseCase(currencyToSell.name, sellValue, commissionFee)
            val isNotTheSameCurrency = currencyToSell != currencyToReceive

            val isExchangePossible =
                sellValue != "0" && isBelowZero && isNotTheSameCurrency && isOnline
            CurrencyExchangeState.Result(
                currencyToSell = currencyToSell,
                currencyToReceive = currencyToReceive,
                receiveValue = receiveValue,
                commissionFee = commissionFee,
                isExchangePossible = isExchangePossible
            )
        }.flowOn(Dispatchers.IO)
            .map<CurrencyExchangeState.Result, CurrencyExchangeState> { it }
            .catch {
                emit(CurrencyExchangeState.Error)
            }

    }

    var sellValue by mutableStateOf("")
        private set

    var openDialog by mutableStateOf(false)
        private set

    private val _isConverting = MutableStateFlow(false)
    val isConverting: StateFlow<Boolean> = _isConverting.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRateRepository.currencyToSell.filterNotNull().mapLatest { it.name }
                .distinctUntilChanged()
                .collectLatest {
                    observeRates(it)
                }
        }
    }

    private fun observeRates(baseCurrency: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            currencyRateRepository.observeRates(baseCurrency).cancellable().collect()
        }
    }

    fun exchange() {
        val state = currencyExchangeState.value
        if (state is CurrencyExchangeState.Result && state.currencyToSell != null && state.currencyToReceive != null && state.receiveValue.isNotEmpty() && sellValue.isNotEmpty() && state.commissionFee.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isConverting.value = true
                val newSellBalance = deductBalanceUseCase(state.currencyToSell.name, sellValue)
                val newReceiveBalance =
                    increaseBalanceUseCase(
                        state.currencyToReceive.name,
                        BigDecimal(state.receiveValue)
                    )

                balanceRepository.exchange(
                    newSellBalance,
                    newReceiveBalance
                )
                conversionRepository.insert(
                    state.currencyToSell.name,
                    BigDecimal(sellValue),
                    state.currencyToReceive.name,
                    BigDecimal(state.receiveValue),
                    BigDecimal(state.commissionFee)
                )
                openDialog = true
                _isConverting.value = false
            }
        }
    }

    private fun clear() {
        sellValue = ""
    }

    fun closeDialog() {
        openDialog = false
        clear()
    }

    fun updateSellValue(value: String) {
        sellValue = getCurrencyStringUseCase(sellValue, value)

    }
}
