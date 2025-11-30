package com.divinewhisper.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF7569F7),
    onPrimary = Color.White,
    secondary = Color(0xFF48E1CA),
    onSecondary = Color(0xFF04231F),
    background = Color(0xFFF6F7FB),
    onBackground = Color(0xFF0C1324),
    surface = Color(0xFFFBFCFF),
    onSurface = Color(0xFF0C1324)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFBCB0FF),
    onPrimary = Color(0xFF130B2F),
    secondary = Color(0xFF6FE2CE),
    onSecondary = Color(0xFF00201A),
    background = Color(0xFF0C1018),
    onBackground = Color(0xFFE7E9F2),
    surface = Color(0xFF111827),
    onSurface = Color(0xFFE7E9F2)
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
