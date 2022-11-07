package com.example.currencies.domain.commission

import com.example.currencies.data.ConversionRepository
import com.example.currencies.data.CurrencyRateRepository
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class CountCommissionRule @Inject constructor(
    private val currencyRateRepository: CurrencyRateRepository,
    private val conversionRepository: ConversionRepository
) : CommissionRule {
    private val mediumCommission = BigDecimal.valueOf(0.007)
    private val highCommission = BigDecimal.valueOf(0.012)
    private val eurCommission = BigDecimal.valueOf(0.3)

    override fun calculateCommission(
        transaction: Transaction
    ): BigDecimal {
        val midnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val conversionCount = conversionRepository.getTodayConversionCount(midnight)

        return if (conversionCount <= 5) {
            BigDecimal.ZERO
        } else if (conversionCount <= 15) {
            transaction.sellValue.multiply(mediumCommission)
        } else {
            val eurRate = currencyRateRepository.getEurRate()
            transaction.sellValue.multiply(highCommission).plus(
                eurCommission.multiply(eurRate)
            )
        }
    }
}