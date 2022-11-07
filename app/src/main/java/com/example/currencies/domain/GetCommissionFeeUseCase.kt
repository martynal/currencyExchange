package com.example.currencies.domain

import com.example.currencies.domain.commission.CommissionRulesManager
import com.example.currencies.domain.commission.Transaction
import java.math.BigDecimal
import javax.inject.Inject

class GetCommissionFeeUseCase @Inject constructor(private val commissionRulesManager: CommissionRulesManager) {
    operator fun invoke(
        sellCurrency: String,
        sellValue: String,
        receiveCurrency: String,
        receiveValue: String
    ): String {
        val transaction = Transaction(
            sellCurrency, BigDecimal(sellValue), receiveCurrency, BigDecimal(receiveValue)
        )
        return commissionRulesManager.calculate(transaction).toPlainString()
    }
}
