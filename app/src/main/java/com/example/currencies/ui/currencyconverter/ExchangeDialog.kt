package com.example.currencies.ui.currencyconverter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.currencies.R
import com.example.currencies.design.PrimaryButton

@Composable
fun ExchangeDialog(
    onCloseDialog: () -> Unit,
    sellCurrency: String?,
    sellValue: String,
    receiveCurrency: String?,
    receiveValue: String?,
    commissionFee: String,
) {
    Dialog(
        onDismissRequest = onCloseDialog,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(32.dp))
                .background(MaterialTheme.colors.secondary)
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.dialog_title),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            if (sellCurrency != null && receiveValue != null && receiveCurrency != null) {
                Text(
                    text = stringResource(
                        id = R.string.dialog_description,
                        sellValue,
                        sellCurrency,
                        receiveValue,
                        receiveCurrency,
                        commissionFee,
                        sellCurrency,
                    ),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
            PrimaryButton(
                onClick = onCloseDialog,
                text = R.string.dialog_button,
            )
        }
    }
}