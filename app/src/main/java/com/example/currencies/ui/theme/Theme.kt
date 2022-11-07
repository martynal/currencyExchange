package com.example.currencies.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.White,
    onPrimary = Color.Black,
    secondary = Gray900,
    onSecondary = Color.White,
    background = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = Color.Black,
    onPrimary = Color.White,
    secondary = Gray100,
    onSecondary = Color.Black,
    background = Color.White,
)

@Composable
fun CurrenciesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}