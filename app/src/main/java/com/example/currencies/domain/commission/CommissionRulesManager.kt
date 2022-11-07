package com.example.currencies.domain.commission

import java.math.BigDecimal

class CommissionRulesManager(private val rules: List<CommissionRule>) {

    fun calculate(transaction: Transaction): BigDecimal =
        rules.map { it.calculateCommission(transaction) }.sumOf { it }

}