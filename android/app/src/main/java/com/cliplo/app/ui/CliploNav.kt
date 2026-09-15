package com.cliplo.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cliplo.app.data.BlockReason
import com.cliplo.app.ui.screens.HomeScreen
import com.cliplo.app.ui.screens.SettingsScreen

/** ui-proto shell: a single home surface with settings pushed over it — no tab bar. */
@Composable
fun CliploNav(state: HomeState, actions: ClipViewModel) {
    val nav = rememberNavController()
    val snackbar = remember { SnackbarHostState() }

    val toast = state.toast
    LaunchedEffect(toast) {
        if (toast != null) {
            val undone = snackbar.showSnackbar(toast, actionLabel = "Undo") == SnackbarResult.ActionPerformed
            if (undone) actions.undo() else actions.dismissToast()
        }
    }
    val blocked = state.blocked
    LaunchedEffect(blocked) {
        if (blocked != null) {
            snackbar.showSnackbar(
                when (blocked) {
                    BlockReason.PAUSED -> "Capture is paused"
                    BlockReason.EXCLUDED_APP -> "This app is excluded from capture"
                    BlockReason.FREE_LIMIT -> "Free plan keeps 50 clips — upgrade to keep capturing"
                },
            )
            actions.dismissToast()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbar) },
    ) { padding ->
        NavHost(nav, startDestination = "home", modifier = Modifier.padding(padding)) {
            composable("home") {
                HomeScreen(state, actions, onOpenSettings = { nav.navigate("settings") })
            }
            composable("settings") {
                SettingsScreen(onBack = { nav.popBackStack() })
            }
        }
    }
}
