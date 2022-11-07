package com.example.currencies.data.db.dao

import androidx.room.*
import com.example.currencies.data.db.model.Balance
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {
    @Query("SELECT * FROM balance")
    fun getAll(): Flow<List<Balance>>

    @Query("SELECT * FROM balance WHERE currency LIKE :currency")
    fun getAccountByCurrency(currency: String): Balance

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(balances: List<Balance>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(balances: Balance)

    @Update
    fun update(balances: Balance)
}