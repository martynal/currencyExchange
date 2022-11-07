package com.example.currencies.domain

import com.example.currencies.data.BalanceRepository
import com.example.currencies.data.db.model.Balance
import com.example.currencies.data.db.model.CurrencyRateTuple
import java.math.BigDecimal
import javax.inject.Inject

class IncreaseBalanceUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository
) {
    suspend operator fun invoke(currency: String, value: BigDecimal): Balance {
        val balance = balanceRepository.getBalanceByCurrency(currency)
        return balance.copy(value = balance.value.plus(value))
    }
}