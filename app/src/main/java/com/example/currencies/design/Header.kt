package com.example.currencies.design

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencies.R
import com.example.currencies.ui.theme.CurrenciesTheme

@Composable
fun Header(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    ProvideTextStyle(value = MaterialTheme.typography.h6) {
        Text(
            text = stringResource(id = text),
            modifier = modifier
                .padding(24.dp)
        )
    }
}


@Preview
@Composable
fun HeaderPreview() {
    CurrenciesTheme {
        Surface {
            Header(text = R.string.currency_exchange)
        }
    }
}