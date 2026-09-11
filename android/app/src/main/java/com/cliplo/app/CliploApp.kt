package com.cliplo.app

import android.app.Application
import com.cliplo.app.capture.CapturePrefs
import com.cliplo.app.data.ClipDatabase
import com.cliplo.app.data.ClipRepository

/** Manual service locator. Keeps Night One free of a DI framework. */
class CliploApp : Application() {

    val database: ClipDatabase by lazy { ClipDatabase.get(this) }

    val capturePrefs: CapturePrefs by lazy { CapturePrefs(this) }

    val repository: ClipRepository by lazy {
        ClipRepository(
            db = database,
            context = this,
            prefs = capturePrefs,
        )
    }
}
