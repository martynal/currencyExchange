package com.example.currencies.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal


@Entity(tableName = "currency_rate")
data class CurrencyRate(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rate") val rate: BigDecimal,
    @ColumnInfo(name = "timestamp") val lastSelectedTimestamp: Long = 0L
)

data class CurrencyRateTuple(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rate") val rate: BigDecimal,
)