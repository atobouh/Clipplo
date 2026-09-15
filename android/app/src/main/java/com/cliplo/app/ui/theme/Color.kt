package com.cliplo.app.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/** Cliplo tokens from ui-proto (mineral / ink / paper). Light is the reference scene. */
object CliploColors {
    val PageLight = Color(0xFFF3F2ED)
    val SurfaceLight = Color(0xFFFFFEFA)
    val Surface2Light = Color(0xFFEEEEE8)
    val InkLight = Color(0xFF111719)
    val BodyLight = Color(0xFF596366)
    val DimLight = Color(0xFF858E90)
    val FaintLight = Color(0xFFAAB1B0)
    val LineLight = Color(0x17161B1D)
    val LineStrongLight = Color(0x29161B1D)
    val AccentLight = Color(0xFF287D6B)
    val AccentStrongLight = Color(0xFF176454)
    val AccentSoftLight = Color(0x1C287D6B)
    val OnAccentLight = Color(0xFFFFFFFF)

    val PageDark = Color(0xFF151918)
    val SurfaceDark = Color(0xFF202624)
    val Surface2Dark = Color(0xFF282F2D)
    val InkDark = Color(0xFFF1F4EF)
    val BodyDark = Color(0xFFBDC8C2)
    val DimDark = Color(0xFF8C9993)
    val FaintDark = Color(0xFF69766F)
    val LineDark = Color(0x1AFFFFFF)
    val LineStrongDark = Color(0x2BFFFFFF)
    val AccentDark = Color(0xFF77C8AD)
    val AccentStrongDark = Color(0xFF58AF92)
    val AccentSoftDark = Color(0x2177C8AD)
    val OnAccentDark = Color(0xFF10251E)

    val Rose = Color(0xFFCE5F5E)
    val Amber = Color(0xFFB77729)
}

/**
 * Exact design tokens Material's ColorScheme slots don't expose (line, faint,
 * accentSoft, onAccent). Components read these directly for proto-faithful surfaces.
 */
data class CliploPalette(
    val page: Color,
    val surface: Color,
    val surface2: Color,
    val ink: Color,
    val body: Color,
    val dim: Color,
    val faint: Color,
    val line: Color,
    val lineStrong: Color,
    val accent: Color,
    val accentStrong: Color,
    val accentSoft: Color,
    val onAccent: Color,
    val rose: Color,
    val amber: Color,
    val isDark: Boolean,
)

val LightPalette = CliploPalette(
    page = CliploColors.PageLight, surface = CliploColors.SurfaceLight, surface2 = CliploColors.Surface2Light,
    ink = CliploColors.InkLight, body = CliploColors.BodyLight, dim = CliploColors.DimLight, faint = CliploColors.FaintLight,
    line = CliploColors.LineLight, lineStrong = CliploColors.LineStrongLight,
    accent = CliploColors.AccentLight, accentStrong = CliploColors.AccentStrongLight,
    accentSoft = CliploColors.AccentSoftLight, onAccent = CliploColors.OnAccentLight,
    rose = CliploColors.Rose, amber = CliploColors.Amber, isDark = false,
)

val DarkPalette = CliploPalette(
    page = CliploColors.PageDark, surface = CliploColors.SurfaceDark, surface2 = CliploColors.Surface2Dark,
    ink = CliploColors.InkDark, body = CliploColors.BodyDark, dim = CliploColors.DimDark, faint = CliploColors.FaintDark,
    line = CliploColors.LineDark, lineStrong = CliploColors.LineStrongDark,
    accent = CliploColors.AccentDark, accentStrong = CliploColors.AccentStrongDark,
    accentSoft = CliploColors.AccentSoftDark, onAccent = CliploColors.OnAccentDark,
    rose = CliploColors.Rose, amber = CliploColors.Amber, isDark = true,
)

val LocalCliplo = staticCompositionLocalOf { LightPalette }
