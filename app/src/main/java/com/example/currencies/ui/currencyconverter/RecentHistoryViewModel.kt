package com.example.currencies.ui.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencies.data.ConversionRepository
import com.example.currencies.data.db.model.Conversion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class RecentHistoryState {
    object Loading : RecentHistoryState()
    data class Result(val rates: List<Conversion>) : RecentHistoryState()
}

@HiltViewModel
class RecentHistoryViewModel @Inject constructor(
    conversionRepository: ConversionRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<RecentHistoryState>(RecentHistoryState.Loading)
    val state: StateFlow<RecentHistoryState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            conversionRepository.getRecentConversions().collect { conversions ->
                _state.value = RecentHistoryState.Result(conversions)
            }
        }
    }
}