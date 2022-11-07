package com.example.currencies.domain

import com.example.currencies.MainDispatcherRule
import com.example.currencies.data.BalanceRepositoryImpl
import com.example.currencies.data.db.model.Balance
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal


@OptIn(ExperimentalCoroutinesApi::class)
class DeductBalanceUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val balanceRepository = mockk<BalanceRepositoryImpl>()

    val useCase = DeductBalanceUseCase(balanceRepository)

    @Before
    fun before() {
        coEvery { balanceRepository.getBalanceByCurrency("EUR") } answers {
            Balance(
                "EUR".hashCode().toLong(), "EUR",
                BigDecimal.valueOf(900)
            )
        }
    }

    @Test
    fun calculateNewSellBalance() = runTest {
        val newSellBalance = useCase("EUR", "100")
        Assert.assertEquals(
            Balance(
                "EUR".hashCode().toLong(), "EUR",
                BigDecimal.valueOf(800)
            ), newSellBalance
        )
    }
}