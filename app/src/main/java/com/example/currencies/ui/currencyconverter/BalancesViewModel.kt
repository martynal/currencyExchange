package com.example.currencies.ui.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencies.data.db.model.Balance
import com.example.currencies.domain.GetSortedBalancesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class BalancesState {
    object Loading : BalancesState()
    data class Result(val balances: List<Balance>) : BalancesState()
}

@HiltViewModel
class BalancesViewModel @Inject constructor(
    getSortedBalancesUseCase: GetSortedBalancesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<BalancesState>(BalancesState.Loading)
    val state: StateFlow<BalancesState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getSortedBalancesUseCase().collectLatest { balances ->
                _state.value = BalancesState.Result(balances = balances)
            }
        }
    }
}