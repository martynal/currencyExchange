package com.example.currencies.domain

import com.example.currencies.data.BalanceRepository
import com.example.currencies.data.db.model.Balance
import java.math.BigDecimal
import javax.inject.Inject

class DeductBalanceUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository,
) {
    suspend operator fun invoke(
        currency: String,
        value: String,
        commissionFee: String? = null
    ): Balance {
        val balance = balanceRepository.getBalanceByCurrency(currency)
        return if (commissionFee != null)
            balance.copy(
                value = balance.value.minus(BigDecimal(value).plus(BigDecimal(commissionFee)))
            )
        else
            balance.copy(
                value = balance.value.minus(BigDecimal(value))
            )
    }
}