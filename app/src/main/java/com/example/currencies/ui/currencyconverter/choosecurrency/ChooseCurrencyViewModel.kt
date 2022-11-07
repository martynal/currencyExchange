package com.example.currencies.ui.currencyconverter.choosecurrency

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencies.ChooseCurrency
import com.example.currencies.data.CurrencyRateRepository
import com.example.currencies.data.db.model.CurrencyRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ChooseCurrencyState {
    object Loading : ChooseCurrencyState()
    data class Result(val rates: List<CurrencyRate>) : ChooseCurrencyState()
}

@HiltViewModel
class ChooseCurrencyViewModel @Inject constructor(
    private val currencyRateRepository: CurrencyRateRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val type =
        ChooseCurrencyType.valueOf(checkNotNull(savedStateHandle.get<String>(ChooseCurrency.currencyTypeArg)))
    private val _state: MutableStateFlow<ChooseCurrencyState> =
        MutableStateFlow(ChooseCurrencyState.Loading)
    val state: StateFlow<ChooseCurrencyState> = _state.asStateFlow()

    init {
        getCurrencies(type)
    }

    private fun getCurrencies(type: ChooseCurrencyType) {
        viewModelScope.launch(Dispatchers.IO) {
            when (type) {
                ChooseCurrencyType.SELL -> _state.value =
                    ChooseCurrencyState.Result(currencyRateRepository.getCurrencies())
                ChooseCurrencyType.RECEIVE -> _state.value =
                    ChooseCurrencyState.Result(currencyRateRepository.getCurrenciesWithoutBase())
            }
        }
    }

    fun setCurrency(currencyRate: CurrencyRate) {
        viewModelScope.launch(Dispatchers.IO) {
            when (type) {
                ChooseCurrencyType.SELL -> currencyRateRepository.setCurrencyToSell(currencyRate)
                ChooseCurrencyType.RECEIVE -> currencyRateRepository.setCurrencyToReceive(
                    currencyRate
                )
            }

        }
    }
}