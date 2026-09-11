package com.cliplo.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cliplo.app.ui.CliploNav
import com.cliplo.app.ui.ClipViewModel
import com.cliplo.app.ui.theme.CliploTheme

class MainActivity : ComponentActivity() {

    private val vm: ClipViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val app = application as CliploApp
                return ClipViewModel(app.repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleShareIntent(intent)
        setContent {
            CliploTheme {
                val state by vm.state.collectAsState()
                CliploNav(state = state, actions = vm)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleShareIntent(intent)
    }

    /** Share-target entry: capture shared text without blocking the sender. */
    private fun handleShareIntent(intent: Intent?) {
        if (intent?.action != Intent.ACTION_SEND) return
        val shared = intent.getCharSequenceExtra(Intent.EXTRA_TEXT)?.toString()
            ?: return
        vm.capture(shared, sourceApp = null)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // OS rule (Android 10+): clipboard is only readable while focused.
        // The watcher runs here — never in the background.
        if (hasFocus) vm.pollClipboard(this)
    }
}
