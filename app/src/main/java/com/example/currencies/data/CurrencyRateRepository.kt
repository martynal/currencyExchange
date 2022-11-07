package com.example.currencies.data

import com.example.currencies.data.db.model.CurrencyRate
import com.example.currencies.data.db.model.CurrencyRateTuple
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface CurrencyRateRepository {
    val currencyToSell: Flow<CurrencyRateTuple>
    val currencyToReceive: Flow<CurrencyRateTuple?>
    suspend fun setCurrencyToSell(currencyRate: CurrencyRate)
    suspend fun setCurrencyToReceive(currencyRate: CurrencyRate)
    suspend fun observeRates(baseCurrency: String): Flow<List<CurrencyRate>>
    suspend fun getCurrencies(): List<CurrencyRate>
    suspend fun getCurrenciesWithoutBase(): List<CurrencyRate>
    fun getEurRate(): BigDecimal
}