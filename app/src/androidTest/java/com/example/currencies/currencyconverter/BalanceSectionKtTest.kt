package com.example.currencies.currencyconverter

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.currencies.data.db.model.Balance
import com.example.currencies.ui.currencyconverter.BalancesSectionContent
import com.example.currencies.ui.currencyconverter.BalancesState
import com.example.currencies.ui.theme.CurrenciesTheme
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

/**
 * This could become an unit test if Robolectric is added
 */
class BalancesSectionKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun balancesStateResult() {
        composeTestRule.setContent {
            CurrenciesTheme {
                BalancesSectionContent(
                    BalancesState.Result(
                        listOf(
                            Balance(
                                "EUR".hashCode().toLong(),
                                "EUR",
                                BigDecimal.valueOf(1000)
                            )
                        )
                    )
                )
            }
        }
        composeTestRule.onNodeWithText("EUR").assertIsDisplayed()
    }
}