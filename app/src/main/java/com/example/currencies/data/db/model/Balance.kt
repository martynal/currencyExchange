package com.example.currencies.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal


@Entity(tableName = "balance")
data class Balance(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "value") val value: BigDecimal = BigDecimal.ZERO
)