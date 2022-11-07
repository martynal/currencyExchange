package com.example.currencies.design

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencies.R
import com.example.currencies.ui.theme.CurrenciesTheme

@Composable
fun PrimaryButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    Box {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .alpha(if (loading) 0f else 1f)
        ) {

            ProvideTextStyle(value = MaterialTheme.typography.button) {
                Text(
                    text = stringResource(id = text),
                )
            }
        }
        if (loading) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier
                    .size(MaterialTheme.typography.button.fontSize.value.dp)
                    .align(Alignment.Center),
            )
        }
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    CurrenciesTheme {
        Surface {
            Column {
                PrimaryButton(text = R.string.submit, onClick = {})
                PrimaryButton(text = R.string.submit, onClick = {}, loading = true)
            }
        }
    }
}
