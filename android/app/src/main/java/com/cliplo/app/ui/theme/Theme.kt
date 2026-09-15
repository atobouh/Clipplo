package com.cliplo.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightScheme = lightColorScheme(
    background = CliploColors.PageLight,
    surface = CliploColors.SurfaceLight,
    surfaceVariant = CliploColors.Surface2Light,
    onBackground = CliploColors.InkLight,
    onSurface = CliploColors.InkLight,
    onSurfaceVariant = CliploColors.BodyLight,
    primary = CliploColors.AccentLight,
    onPrimary = CliploColors.OnAccentLight,
    primaryContainer = CliploColors.AccentSoftLight,
    onPrimaryContainer = CliploColors.AccentLight,
    outline = CliploColors.LineStrongLight,
    outlineVariant = CliploColors.LineLight,
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
    onPrimary = CliploColors.OnAccentDark,
    primaryContainer = CliploColors.AccentSoftDark,
    onPrimaryContainer = CliploColors.AccentDark,
    outline = CliploColors.LineStrongDark,
    outlineVariant = CliploColors.LineDark,
    error = CliploColors.Rose,
)

/** Quiet by default: surfaces carry the product, accent is reserved for action. */
@Composable
fun CliploTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalCliplo provides if (darkTheme) DarkPalette else LightPalette) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkScheme else LightScheme,
            typography = CliploTypography,
            shapes = CliploShapes,
            content = content,
        )
    }
}
