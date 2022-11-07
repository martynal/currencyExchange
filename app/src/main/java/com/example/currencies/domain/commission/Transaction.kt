package com.example.currencies.domain.commission

import java.math.BigDecimal

data class Transaction(
    val sellCurrency: String,
    val sellValue: BigDecimal,
    val receiveCurrency: String,
    val receiveValue: BigDecimal,
)