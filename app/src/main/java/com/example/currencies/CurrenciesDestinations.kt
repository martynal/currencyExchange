package com.example.currencies

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface CurrenciesDestination {
    val route: String
}

/**
 * Currencies app navigation destinations
 */
object CurrencyConverter : CurrenciesDestination {
    override val route = "currency_converter"
}

object ChooseCurrency : CurrenciesDestination {
    override val route = "choose_currency"
    const val currencyTypeArg = "currency_type"
    val routeWithArgs = "$route/{$currencyTypeArg}"
    val arguments = listOf(
        navArgument(currencyTypeArg) { type = NavType.StringType }
    )
}
