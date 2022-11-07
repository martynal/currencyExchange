package com.example.currencies.data

import com.example.currencies.data.api.CurrenciesApiService
import com.example.currencies.data.api.ExchangeRates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRateDataSource @Inject constructor(
    private val currenciesApiService: CurrenciesApiService,

    ) {
    private val refreshIntervalMs: Long = 60000L

    fun getLatestRates(baseCurrency: String): Flow<ExchangeRates> = flow {
        while (true) {
            val latestRates = currenciesApiService.getLatestExchangeRates(baseCurrency)
            emit(latestRates)
            delay(refreshIntervalMs)
        }
    }
}