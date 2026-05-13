package com.arautos.confessioae.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Gold,
    onPrimary = Color.White,
    background = LightBeige,
    onBackground = Ink,
    surface = Surface,
    onSurface = Ink,
    secondary = Ink,
    onSecondary = Color.White,
    outline = Border,
    surfaceVariant = Aside,
    error = Ink,
)

@Composable
fun ConfessioAETheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
