package com.example.currencies.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal


@Entity(tableName = "conversion")
data class Conversion(
    @ColumnInfo(name = "sell_currency") val sellCurrency: String,
    @ColumnInfo(name = "sell_value") val sellValue: BigDecimal,
    @ColumnInfo(name = "receive_currency") val receiveCurrency: String,
    @ColumnInfo(name = "receive_value") val receiveValue: BigDecimal,
    @ColumnInfo(name = "commission_fee") val commissionFee: BigDecimal,
    @ColumnInfo(name = "timestamp") val timestamp: Long? = null,
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}