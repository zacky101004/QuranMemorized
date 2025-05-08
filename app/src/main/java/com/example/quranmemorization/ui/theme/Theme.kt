package com.example.quranmemorization.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = IslamicGreen,
    secondary = IslamicGold,
    background = IslamicWhite,
    surface = IslamicWhite,
    onPrimary = IslamicWhite,
    onSecondary = IslamicDark,
    onBackground = IslamicDark,
    onSurface = IslamicDark
)

@Composable
fun QuranMemorizationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}