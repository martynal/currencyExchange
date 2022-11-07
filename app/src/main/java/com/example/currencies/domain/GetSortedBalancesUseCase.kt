package com.example.currencies.domain

import com.example.currencies.data.BalanceRepository
import com.example.currencies.data.db.model.Balance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedBalancesUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository
) {
    operator fun invoke(): Flow<List<Balance>> {
        return balanceRepository.getBalances()
            .map { it.sortedByDescending { balance -> balance.value } }
    }
}
