package com.example.currencies.ui.currencyconverter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencies.R
import com.example.currencies.design.CurrencyTextField
import com.example.currencies.design.Header
import com.example.currencies.design.PrimaryButton

@Composable
fun CurrencyExchange(
    currencyExchangeViewModel: CurrencyExchangeViewModel = hiltViewModel(),
    onCurrencyFromClick: () -> Unit,
    onCurrencyToClick: () -> Unit,
) {
    val state = currencyExchangeViewModel.currencyExchangeState.collectAsState().value
    val isConverting = currencyExchangeViewModel.isConverting.collectAsState().value
    val openDialog = currencyExchangeViewModel.openDialog

    CurrencyExchangeContent(
        state,
        currencyExchangeViewModel.sellValue,
        isConverting,
        openDialog,
        currencyExchangeViewModel::closeDialog,
        currencyExchangeViewModel::updateSellValue,
        currencyExchangeViewModel::exchange,
        onCurrencyFromClick,
        onCurrencyToClick,
    )
}

@Composable
fun CurrencyExchangeContent(
    state: CurrencyExchangeState,
    sellValue: String,
    isConverting: Boolean,
    openDialog: Boolean,
    onCloseDialog: () -> Unit,
    updateSellValue: (String) -> Unit,
    exchange: () -> Unit,
    onCurrencyFromClick: () -> Unit,
    onCurrencyToClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Header(
            text = R.string.currency_exchange,
        )
        when (state) {
            CurrencyExchangeState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )
            is CurrencyExchangeState.Result -> {
                if (openDialog) {
                    ExchangeDialog(
                        onCloseDialog = onCloseDialog,
                        sellCurrency = state.currencyToSell?.name,
                        sellValue = sellValue,
                        receiveCurrency = state.currencyToReceive?.name,
                        receiveValue = state.receiveValue,
                        commissionFee = state.commissionFee,
                    )
                }
                CurrencyExchangeSellRow(
                    currencyName = state.currencyToSell?.name,
                    onCurrencyFromClick = onCurrencyFromClick,
                    value = sellValue,
                    onValueChange = updateSellValue,
                    enabled = !isConverting
                )
                Spacer(modifier = Modifier.height(8.dp))
                CurrencyExchangeReceiveRow(
                    currencyName = state.currencyToReceive?.name,
                    onCurrencyToClick = onCurrencyToClick,
                    value = state.receiveValue,
                    enabled = !isConverting
                )
                PrimaryButton(
                    onClick = exchange,
                    text = R.string.submit,
                    loading = isConverting,
                    enabled = state.isExchangePossible,
                    modifier = Modifier.padding(24.dp)
                )
            }
            CurrencyExchangeState.Error -> Unit
        }
    }

}

@Composable
fun CurrencyExchangeSellRow(
    currencyName: String?,
    onCurrencyFromClick: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            )
    ) {
        CurrencyExchangeRow(
            icon = R.drawable.ic_sell,
            text = R.string.sell,
            currencyName = currencyName,
            onClick = onCurrencyFromClick,
            enabled = enabled
        ) {
            CurrencyTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                modifier = Modifier
                    .weight(1f, false)
            )
        }
    }
}

@Composable
fun CurrencyExchangeReceiveRow(
    currencyName: String?,
    onCurrencyToClick: () -> Unit,
    value: String?,
    enabled: Boolean,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
            )
    ) {
        CurrencyExchangeRow(
            icon = R.drawable.ic_receive,
            text = R.string.receive,
            currencyName = currencyName,
            onClick = onCurrencyToClick,
            enabled = enabled
        ) {
            Text(
                text = value ?: stringResource(id = R.string.value_hint),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .weight(1f, false)
            )
        }
    }
}

@Composable
fun CurrencyExchangeRow(
    @DrawableRes icon: Int,
    @StringRes text: Int,
    currencyName: String?,
    onClick: () -> Unit,
    enabled: Boolean,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(20.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = text),
            tint = MaterialTheme.colors.background,
            modifier = Modifier
                .padding(end = 10.dp)
                .background(MaterialTheme.colors.onBackground, shape = CircleShape)
                .padding(5.dp)
        )
        Text(text = stringResource(id = text))
        content()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(enabled = enabled, onClick = onClick)
        ) {
            Text(
                text = currencyName ?: stringResource(id = R.string.select),
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = stringResource(id = R.string.more)
            )
        }
    }
}