package com.example.currencies.data

import com.example.currencies.data.db.dao.BalanceDao
import com.example.currencies.data.db.model.Balance
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(
    private val balanceDao: BalanceDao,
) : BalanceRepository {
    override fun getBalances() = balanceDao.getAll()

    override suspend fun getBalanceByCurrency(currency: String) = balanceDao.getAccountByCurrency(currency)

    override fun exchange(
        newSellBalance: Balance,
        newReceiveBalance: Balance,
    ) {
        balanceDao.update(newSellBalance)
        balanceDao.update(newReceiveBalance)
    }
}