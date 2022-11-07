package com.example.currencies.domain

import java.math.BigDecimal
import javax.inject.Inject

class IsBelowZeroUseCase @Inject constructor(
    private val deductBalanceUseCase: DeductBalanceUseCase
) {
    suspend operator fun invoke(currency: String, value: String, commissionFee: String): Boolean {
        val balance = deductBalanceUseCase(
            currency,
            value,
            commissionFee
        )
        return balance.value >= BigDecimal.ZERO
    }
}