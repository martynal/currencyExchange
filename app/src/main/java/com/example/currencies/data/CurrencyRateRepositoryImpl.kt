package com.example.currencies.data

import com.example.currencies.data.api.asDatabaseModel
import com.example.currencies.data.api.toBalance
import com.example.currencies.data.db.dao.BalanceDao
import com.example.currencies.data.db.dao.CurrencyRateDao
import com.example.currencies.data.db.dao.CurrencyReceiveSelectionTimestampDao
import com.example.currencies.data.db.dao.CurrencySellSelectionTimestampDao
import com.example.currencies.data.db.model.CurrencyRate
import com.example.currencies.data.db.model.CurrencyRateTuple
import com.example.currencies.data.db.model.CurrencyReceiveSelectionTimestamp
import com.example.currencies.data.db.model.CurrencySellSelectionTimestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class CurrencyRateRepositoryImpl @Inject constructor(
    private val currencyRateDataSource: CurrencyRateDataSource,
    private val balanceDao: BalanceDao,
    private val currencyRateDao: CurrencyRateDao,
    private val currencySellSelectionTimestamp: CurrencySellSelectionTimestampDao,
    private val currencyReceiveSelectionTimestamp: CurrencyReceiveSelectionTimestampDao,
) : CurrencyRateRepository {

    override val currencyToSell: Flow<CurrencyRateTuple> =
        currencySellSelectionTimestamp.getLastSelectedCurrency()

    override val currencyToReceive: Flow<CurrencyRateTuple> =
        currencyReceiveSelectionTimestamp.getLastSelectedCurrency()

    override suspend fun setCurrencyToSell(currencyRate: CurrencyRate) {
        currencySellSelectionTimestamp
            .insert(
                CurrencySellSelectionTimestamp(
                    currencyRate.id,
                    Calendar.getInstance().timeInMillis
                )
            )
    }

    override suspend fun setCurrencyToReceive(currencyRate: CurrencyRate) {
        currencyReceiveSelectionTimestamp
            .insert(
                CurrencyReceiveSelectionTimestamp(
                    currencyRate.id,
                    Calendar.getInstance().timeInMillis
                )
            )
    }

    override suspend fun observeRates(baseCurrency: String): Flow<List<CurrencyRate>> {
        return currencyRateDataSource.getLatestRates(baseCurrency)
            .onEach { rates ->
                balanceDao.insertAll(rates.toBalance())
            }
            .map { it.asDatabaseModel() }
            .onEach { rates ->
                currencyRateDao.insertAll(rates)
            }
            .retry()

    }

    override suspend fun getCurrencies() = currencyRateDao.getAll()

    override suspend fun getCurrenciesWithoutBase() = currencyRateDao.getCurrenciesWithoutBase()

    override fun getEurRate(): BigDecimal = currencyRateDao.getEurRate()
}