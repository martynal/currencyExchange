package com.example.currencies.ui.currencyconverter

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencies.R
import com.example.currencies.data.db.model.Conversion
import com.example.currencies.design.Header
import com.example.currencies.ui.theme.CurrenciesTheme
import java.math.BigDecimal
import java.util.*

@Composable
fun RecentHistory(recentHistoryViewModel: RecentHistoryViewModel = hiltViewModel()) {

    val state = recentHistoryViewModel.state.collectAsState().value

    LazyColumn {
        item {
            Header(
                text = R.string.recent_history,
            )
        }
        when (state) {
            RecentHistoryState.Loading -> item { CircularProgressIndicator() }
            is RecentHistoryState.Result ->
                items(state.rates) { conversion ->
                    ConversionItem(conversion)
                }
        }
    }
}

@Composable
fun ConversionItem(conversion: Conversion) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.conversion_from,
                conversion.sellValue.toPlainString(),
                conversion.sellCurrency,
                conversion.receiveValue.toPlainString(),
                conversion.receiveCurrency,
            )
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_conversion),
            contentDescription = stringResource(id = R.string.currency_exchange),
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        Text(
            text = stringResource(
                id = R.string.conversion_to,
                conversion.sellValue.toPlainString(),
                conversion.sellCurrency,
                conversion.receiveValue.toPlainString(),
                conversion.receiveCurrency,
            )
        )
    }
}

@Preview
@Composable
fun ConversionItemPreview() {
    CurrenciesTheme {
        Surface {
            ConversionItem(
                conversion = Conversion(
                    "EUR",
                    BigDecimal.ONE,
                    "DOL",
                    BigDecimal.TEN,
                    BigDecimal.valueOf(1.4),
                    Calendar.getInstance().timeInMillis
                )
            )
        }
    }
}