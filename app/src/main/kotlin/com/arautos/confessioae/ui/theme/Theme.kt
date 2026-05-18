package com.arautos.confessioae.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

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

private val DarkColorScheme = darkColorScheme(
    primary = GoldDark,
    onPrimary = Color.Black,
    background = DarkBackground,
    onBackground = DarkInk,
    surface = DarkSurface,
    onSurface = DarkInk,
    secondary = DarkInk,
    onSecondary = Color.Black,
    outline = DarkBorder,
    surfaceVariant = DarkAside,
    error = DarkInk,
)

@Composable
fun ConfessioAETheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
