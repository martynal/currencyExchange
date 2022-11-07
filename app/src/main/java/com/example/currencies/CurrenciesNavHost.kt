package com.example.currencies

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.currencies.ui.currencyconverter.CurrencyConverter
import com.example.currencies.ui.currencyconverter.choosecurrency.ChooseCurrency
import com.example.currencies.ui.currencyconverter.choosecurrency.ChooseCurrencyType

@Composable
fun CurrenciesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = CurrencyConverter.route,
        modifier = modifier
    )
    {
        composable(route = CurrencyConverter.route) {
            CurrencyConverter(
                onCurrencyFromClick = {
                    navController.navigateToChooseCurrency(ChooseCurrencyType.SELL)
                },
                onCurrencyToClick = {
                    navController.navigateToChooseCurrency(ChooseCurrencyType.RECEIVE)
                }
            )
        }
        composable(
            route = ChooseCurrency.routeWithArgs,
            arguments = ChooseCurrency.arguments,
        ) {
            ChooseCurrency(navController::popBackStack)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

private fun NavHostController.navigateToChooseCurrency(chooseCurrencyType: ChooseCurrencyType) {
    this.navigateSingleTopTo("${ChooseCurrency.route}/${chooseCurrencyType.name}")
}

