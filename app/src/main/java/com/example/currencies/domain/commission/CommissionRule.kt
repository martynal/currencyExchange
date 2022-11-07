package com.example.currencies.domain.commission

import java.math.BigDecimal

interface CommissionRule {
    fun calculateCommission(transaction: Transaction): BigDecimal
}