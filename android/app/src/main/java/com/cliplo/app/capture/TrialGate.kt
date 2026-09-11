package com.cliplo.app.capture

import android.content.Context
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.trialPrefs by preferencesDataStore(name = "trial")

/**
 * Seven-day full-feature trial, then the free 50-clip rule
 * (MUST_HAVE free/pro: older clips retained, never silently deleted).
 */
object TrialGate {
    const val TRIAL_DAYS = 7L
    const val MAX_FREE_CLIPS = 50
    private const val DAY_MS = 24L * 60 * 60 * 1000

    private val startedKey = longPreferencesKey("trial_started_at")

    fun startedAt(context: Context): Flow<Long?> =
        context.trialPrefs.data.map { it[startedKey] }

    suspend fun ensureStarted(context: Context, now: Long = System.currentTimeMillis()) {
        context.trialPrefs.edit { prefs ->
            if (!prefs.contains(startedKey)) prefs[startedKey] = now
        }
    }

    suspend fun isTrialActive(context: Context, now: Long = System.currentTimeMillis()): Boolean {
        val started = startedAt(context).first() ?: return true
        return now - started < TRIAL_DAYS * DAY_MS
    }
}
