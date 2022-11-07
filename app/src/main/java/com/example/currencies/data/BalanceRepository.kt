package com.example.currencies.data

import com.example.currencies.data.db.model.Balance
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    fun getBalances(): Flow<List<Balance>>
    suspend fun getBalanceByCurrency(currency: String): Balance
    fun exchange(
        newSellBalance: Balance,
        newReceiveBalance: Balance,
    )
}