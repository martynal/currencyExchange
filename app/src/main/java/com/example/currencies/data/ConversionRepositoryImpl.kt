package com.example.currencies.data

import com.example.currencies.data.db.dao.ConversionDao
import com.example.currencies.data.db.model.Conversion
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class ConversionRepositoryImpl @Inject constructor(
    private val conversionDao: ConversionDao,
) : ConversionRepository {
    override fun getTodayConversionCount(startDate: Long) =
        conversionDao.getTodayConversionCount(startDate)

    override fun getRecentConversions() = conversionDao.getRecentConversions()

    override fun insert(
        sellCurrency: String,
        sellValue: BigDecimal,
        receiveCurrency: String,
        receiveValue: BigDecimal,
        commissionFee: BigDecimal,
    ) {
        conversionDao.insert(
            Conversion(
                sellCurrency = sellCurrency,
                sellValue = sellValue,
                receiveCurrency = receiveCurrency,
                receiveValue = receiveValue,
                commissionFee = commissionFee,
                timestamp = Calendar.getInstance().timeInMillis
            )
        )
    }
}