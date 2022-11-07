package com.example.currencies.data

import com.example.currencies.data.db.model.Conversion
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface ConversionRepository {
    fun getTodayConversionCount(startDate: Long): Int

    fun getRecentConversions(): Flow<List<Conversion>>

    fun insert(
        sellCurrency: String,
        sellValue: BigDecimal,
        receiveCurrency: String,
        receiveValue: BigDecimal,
        commissionFee: BigDecimal,
    )
}