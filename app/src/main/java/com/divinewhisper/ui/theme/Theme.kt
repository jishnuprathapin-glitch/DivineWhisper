package com.divinewhisper.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF6B4EFF),
    onPrimary = Color.White,
    secondary = Color(0xFF5AD1B5),
    onSecondary = Color.Black,
    background = Color(0xFFF5F5F9),
    onBackground = Color(0xFF1B1B1F)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFB6A8FF),
    onPrimary = Color(0xFF1C0F4E),
    secondary = Color(0xFF7CE7CF),
    onSecondary = Color(0xFF00382C),
    background = Color(0xFF0F1014),
    onBackground = Color(0xFFE6E1E5)
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
