package com.example.currencies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencies.data.db.model.CurrencyRateTuple
import com.example.currencies.data.db.model.CurrencyReceiveSelectionTimestamp
import com.example.currencies.data.db.model.CurrencySellSelectionTimestamp
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyReceiveSelectionTimestampDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyReceiveSelectionTimestamp: CurrencyReceiveSelectionTimestamp)

//    @Query("SELECT * FROM currency_selection_timestamp")
//    fun getAll(): Flow<List<CurrencySellSelectionTimestamp>>

    @Query("SELECT name, rate  FROM currency_rate where id=(SELECT currency_id FROM currency_receive_selection_timestamp ORDER BY selection_timestamp DESC LIMIT 1)")
    fun getLastSelectedCurrency(): Flow<CurrencyRateTuple>

}