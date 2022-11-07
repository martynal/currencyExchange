package com.example.currencies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencies.data.db.model.Conversion
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversionDao {
    @Query("SELECT * FROM conversion")
    fun getAll(): Flow<List<Conversion>>

    @Query("SELECT * FROM conversion ORDER BY timestamp DESC LIMIT 5")
    fun getRecentConversions(): Flow<List<Conversion>>

    @Query("SELECT count(*) FROM conversion WHERE timestamp > :startDate ")
    fun getTodayConversionCount(startDate: Long): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(conversion: Conversion)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(conversions: List<Conversion>)
}