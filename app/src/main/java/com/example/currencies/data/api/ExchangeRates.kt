package com.example.currencies.data.api

import com.example.currencies.data.db.model.Balance
import com.example.currencies.data.db.model.CurrencyRate
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.*


data class ExchangeRates(
    @SerializedName("rates") val rates: Map<String, BigDecimal>,
    @SerializedName("base") val base: String,
    @JsonAdapter(UnixTimestampAdapter::class) @SerializedName("timestamp") val timestamp: Date,
)

fun ExchangeRates.asDatabaseModel(): List<CurrencyRate> {
    return rates.map {
        CurrencyRate(
            id = it.key.hashCode().toLong(),
            name = it.key,
            rate = it.value
        )
    }
}


fun ExchangeRates.toBalance(): List<Balance> {
    return rates.map {
        Balance(
            id = it.key.hashCode().toLong(),
            currency = it.key,
        )
    }
}