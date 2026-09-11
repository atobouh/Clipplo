package com.cliplo.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightScheme = lightColorScheme(
    background = CliploColors.PageLight,
    surface = CliploColors.SurfaceLight,
    surfaceVariant = CliploColors.Surface2Light,
    onBackground = CliploColors.InkLight,
    onSurface = CliploColors.InkLight,
    onSurfaceVariant = CliploColors.BodyLight,
    primary = CliploColors.AccentLight,
    onPrimary = CliploColors.SurfaceLight,
    error = CliploColors.Rose,
)

private val DarkScheme = darkColorScheme(
    background = CliploColors.PageDark,
    surface = CliploColors.SurfaceDark,
    surfaceVariant = CliploColors.Surface2Dark,
    onBackground = CliploColors.InkDark,
    onSurface = CliploColors.InkDark,
    onSurfaceVariant = CliploColors.BodyDark,
    primary = CliploColors.AccentDark,
    onPrimary = CliploColors.PageDark,
    error = CliploColors.Rose,
)

/** Quiet by default: surfaces carry the product, accent is reserved for action. */
@Composable
fun CliploTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkScheme else LightScheme,
        content = content,
    )
}
