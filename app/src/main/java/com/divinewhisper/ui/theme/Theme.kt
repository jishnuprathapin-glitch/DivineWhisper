package com.divinewhisper.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightColors = lightColorScheme(
    primary = Color(0xFF5E60CE),
    onPrimary = Color.White,
    secondary = Color(0xFF64C4ED),
    onSecondary = Color(0xFF0D1B2A),
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF0D1B2A)
)

private val darkColors = darkColorScheme(
    primary = Color(0xFF9BA3EB),
    onPrimary = Color(0xFF0D1B2A),
    secondary = Color(0xFF89C2D9),
    onSecondary = Color(0xFF0D1B2A),
    background = Color(0xFF0D1B2A),
    onBackground = Color(0xFFE0E1DD)
)

@Composable
fun DivineWhisperTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkColors else lightColors,
        typography = Typography,
        content = content
    )
}
