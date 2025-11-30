package com.divinewhisper.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF7F5DFF),
    onPrimary = Color.White,
    secondary = Color(0xFF4CD6C0),
    onSecondary = Color(0xFF0A1F26),
    background = Color(0xFFF7F7FB),
    onBackground = Color(0xFF0F172A)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFC3B5FF),
    onPrimary = Color(0xFF1A103D),
    secondary = Color(0xFF88E7D6),
    onSecondary = Color(0xFF002D25),
    background = Color(0xFF0E1118),
    onBackground = Color(0xFFE5E7EB)
)

@Composable
fun DivineWhisperTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
