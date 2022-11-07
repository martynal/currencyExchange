package com.example.currencies.design

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencies.R
import com.example.currencies.ui.theme.CurrenciesTheme

@Composable
fun CurrencyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = TextStyle(
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.primary,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.value_hint),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        },
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
    )
}


@Preview
@Composable
fun CurrencyTextField() {
    CurrenciesTheme {
        Surface {
            Column {
                CurrencyTextField(value = "", onValueChange = {}, enabled = true)
                CurrencyTextField(value = "10.11", onValueChange = {}, enabled = true)
            }
        }
    }
}