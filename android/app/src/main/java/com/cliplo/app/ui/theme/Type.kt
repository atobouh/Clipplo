package com.cliplo.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * Proto type scale (Sora display / Instrument Sans body). Fonts aren't bundled yet,
 * so this maps the sizes, weights and tight tracking onto the system family —
 * the composition, not the glyphs. Swap FontFamily in here once the faces land.
 */
val CliploTypography = Typography(
    // Brand wordmark + screen titles ("Cliplo", "Settings")
    headlineSmall = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.04).em, lineHeight = 26.sp),
    // Collection heading ("Collections", "Vault")
    titleLarge = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-0.05).em, lineHeight = 24.sp),
    // Card / row titles, vault-strip strong
    titleMedium = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-0.015).em, lineHeight = 20.sp),
    // Clip title in the feed
    titleSmall = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-0.018).em, lineHeight = 21.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, letterSpacing = (-0.006).em, lineHeight = 20.sp),
    bodySmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal, letterSpacing = 0.sp, lineHeight = 16.sp),
    // Section labels ("ALL CLIPS", group headers) — used uppercase
    labelLarge = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.16.em, lineHeight = 14.sp),
    // Catch line above a clip title ("YouTube", "Notes")
    labelMedium = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.sp, lineHeight = 14.sp),
    labelSmall = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.13.em, lineHeight = 13.sp),
)
