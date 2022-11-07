package com.example.currencies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencies.data.db.model.CurrencyRate
import java.math.BigDecimal

@Dao
interface CurrencyRateDao {
    @Query("SELECT * FROM currency_rate")
    fun getAll(): List<CurrencyRate>

    @Query("SELECT * FROM currency_rate WHERE id != (SELECT currency_id FROM currency_sell_selection_timestamp ORDER BY selection_timestamp DESC LIMIT 1)")
    fun getCurrenciesWithoutBase(): List<CurrencyRate>

    @Query("SELECT * FROM currency_rate WHERE id LIKE :currencyId")
    fun getCurrencyRateById(currencyId: String): CurrencyRate

    @Query("SELECT rate FROM currency_rate WHERE name LIKE 'EUR'")
    fun getEurRate(): BigDecimal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencyRates: List<CurrencyRate>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyRates: CurrencyRate)
}