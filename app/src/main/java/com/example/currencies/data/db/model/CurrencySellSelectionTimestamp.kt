package com.example.currencies.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "currency_sell_selection_timestamp", foreignKeys = [ForeignKey(
        entity = CurrencyRate::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("currency_id")
    )]
)
data class CurrencySellSelectionTimestamp(
    @ColumnInfo(name = "currency_id") @PrimaryKey val currencyId: Long,
    @ColumnInfo(name = "selection_timestamp") val selectionTimestamp: Long,
)