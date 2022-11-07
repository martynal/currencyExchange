package com.example.currencies.ui.currencyconverter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencies.R
import com.example.currencies.data.db.model.Balance
import com.example.currencies.design.Header
import com.example.currencies.ui.theme.CurrenciesTheme
import java.math.BigDecimal

@Composable
fun BalancesSection(balancesViewModel: BalancesViewModel = hiltViewModel()) {
    val state = balancesViewModel.state.collectAsState().value
    BalancesSectionContent(state = state)
}

@Composable
fun BalancesSectionContent(state: BalancesState) {
    Column {
        Header(
            text = R.string.my_balances,
        )
        when (state) {
            BalancesState.Loading -> CircularProgressIndicator()
            is BalancesState.Result -> BalancesCarousel(state.balances)
        }

    }
}

@Composable
fun BalancesCarousel(balances: List<Balance>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
    ) {
        items(balances) { balance ->
            BalanceItem(balance)
        }
    }
}


@Composable
fun BalanceItem(balance: Balance) {
    Card(backgroundColor = MaterialTheme.colors.secondary) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(text = balance.value.toPlainString())
            Text(text = balance.currency)
        }
    }
}

@Preview
@Composable
fun BalancesCarouselPreview() {
    CurrenciesTheme {
        Surface {
            BalancesCarousel(
                listOf(
                    Balance(0, "EUR", BigDecimal.valueOf(567.26)),
                    Balance(1, "USD", BigDecimal.valueOf(0.00)),
                    Balance(2, "BGN", BigDecimal.valueOf(12.35)),
                    Balance(3, "PLN", BigDecimal.valueOf(60.00)),
                )
            )
        }
    }
}


@Preview
@Composable
fun BalanceItemPreview() {
    CurrenciesTheme {
        Surface {
            BalanceItem(
                Balance(0, "EUR", BigDecimal.valueOf(567.26)),
            )
        }
    }
}