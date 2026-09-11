package com.cliplo.app.capture

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefs by preferencesDataStore(name = "capture")

/**
 * Capture controls from FEATURES.md privacy + MUST_HAVE reliability:
 * pause switch, per-app exclusion list, capture status.
 */
class CapturePrefs(private val context: Context) {

    private val enabledKey = booleanPreferencesKey("capture_enabled")
    private val excludedKey = stringSetPreferencesKey("excluded_apps")

    val enabled: Flow<Boolean> =
        context.prefs.data.map { it[enabledKey] ?: true }

    val excludedApps: Flow<Set<String>> =
        context.prefs.data.map { it[excludedKey].orEmpty() }

    suspend fun setEnabled(enabled: Boolean) {
        context.prefs.edit { it[enabledKey] = enabled }
    }

    suspend fun setExcluded(packageNames: Set<String>) {
        context.prefs.edit { it[excludedKey] = packageNames }
    }
}
