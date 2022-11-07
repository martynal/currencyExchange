package com.example.currencies.ui.currencyconverter.choosecurrency

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencies.R

enum class ChooseCurrencyType {
    SELL, RECEIVE,
}

@Composable
fun ChooseCurrency(
    popBackStack: () -> Unit,
    chooseCurrencyViewModel: ChooseCurrencyViewModel = hiltViewModel(),
) {
    val uiState = chooseCurrencyViewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.choose_currency), modifier = Modifier
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },

                )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            when (uiState) {
                ChooseCurrencyState.Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                is ChooseCurrencyState.Result -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp)
                ) {
                    items(uiState.rates) { currencyRate ->
                        Card(
                            backgroundColor = MaterialTheme.colors.secondary,
                            modifier = Modifier
                                .clickable(onClick = {
                                    chooseCurrencyViewModel.setCurrency(currencyRate)
                                    popBackStack()
                                })
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 16.dp)
                            ) {
                                Text(text = currencyRate.name)
                            }
                        }
                    }
                }
            }
        }
    }
}