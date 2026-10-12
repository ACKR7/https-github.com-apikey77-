package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MysticColorScheme = darkColorScheme(
    primary = MysticPrimary,
    onPrimary = MysticOnPrimary,
    primaryContainer = MysticPrimaryContainer,
    onPrimaryContainer = Color(0xFFFDF4FF),
    secondary = MysticGold,
    onSecondary = MysticOnGold,
    secondaryContainer = MysticGoldContainer,
    onSecondaryContainer = Color(0xFFFEF3C7),
    tertiary = MysticIndigo,
    onTertiary = Color.White,
    background = MysticDarkBg,
    onBackground = MysticTextPrimary,
    surface = MysticSurface,
    onSurface = MysticTextPrimary,
    surfaceVariant = MysticSurfaceVariant,
    onSurfaceVariant = MysticTextSecondary,
    outline = MysticBorder
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MysticColorScheme,
        typography = Typography,
        content = content
    )
}
