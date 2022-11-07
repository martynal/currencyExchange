package com.example.currencies.ui.currencyconverter

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun CurrencyConverter(
    onCurrencyFromClick: () -> Unit,
    onCurrencyToClick: () -> Unit,
) {
    Column() {
        BalancesSection()
        CurrencyExchange(
            onCurrencyFromClick = onCurrencyFromClick,
            onCurrencyToClick = onCurrencyToClick,
        )
        RecentHistory()
    }
}