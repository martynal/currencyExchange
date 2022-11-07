package com.example.currencies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencies.data.db.model.CurrencyRateTuple
import com.example.currencies.data.db.model.CurrencySellSelectionTimestamp
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencySellSelectionTimestampDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencySellSelectionTimestamp: CurrencySellSelectionTimestamp)

    @Query("SELECT name, rate  FROM currency_rate where id=(SELECT currency_id FROM currency_sell_selection_timestamp ORDER BY selection_timestamp DESC LIMIT 1)")
    fun getLastSelectedCurrency(): Flow<CurrencyRateTuple>
}