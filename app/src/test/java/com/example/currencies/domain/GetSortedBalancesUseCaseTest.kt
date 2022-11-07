package com.example.currencies.domain

import com.example.currencies.MainDispatcherRule
import com.example.currencies.data.BalanceRepositoryImpl
import com.example.currencies.data.db.model.Balance
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class GetSortedBalancesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val balanceRepository = mockk<BalanceRepositoryImpl>()

    val useCase = GetSortedBalancesUseCase(balanceRepository)

    @Before
    fun before() {
        every { balanceRepository.getBalances() } answers {
            flow {
                emit(
                    listOf(
                        Balance(
                            "EUR".hashCode().toLong(), "EUR",
                            BigDecimal.valueOf(900)
                        ),
                        Balance(
                            "DOL".hashCode().toLong(), "DOL",
                            BigDecimal.valueOf(10)
                        ),
                        Balance(
                            "PLN".hashCode().toLong(), "PLN",
                            BigDecimal.valueOf(100)
                        )
                    )
                )
            }
        }
    }

    @Test
    fun getSortedBalances() = runTest {
        val sortedBalances = useCase()
        assertEquals(
            listOf(
                Balance(
                    "EUR".hashCode().toLong(), "EUR",
                    BigDecimal.valueOf(900)
                ),
                Balance(
                    "PLN".hashCode().toLong(), "PLN",
                    BigDecimal.valueOf(100)
                ),
                Balance(
                    "DOL".hashCode().toLong(), "DOL",
                    BigDecimal.valueOf(10)
                ),
            ), sortedBalances.first()
        )
    }
}