package com.cliplo.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cliplo.app.data.BlockReason
import com.cliplo.app.ui.screens.CollectionsScreen
import com.cliplo.app.ui.screens.HomeScreen
import com.cliplo.app.ui.screens.SettingsScreen

private data class Tab(val route: String, val label: String, val icon: ImageVector)

private val TABS = listOf(
    Tab("home", "Clips", Icons.Filled.Home),
    Tab("collections", "Collections", Icons.Filled.Collections),
    Tab("settings", "Settings", Icons.Filled.Settings),
)

/** Three-zone composition: title / feed / bottom capture panel (VISUAL_FEATURES §1). */
@Composable
fun CliploNav(state: HomeState, actions: ClipViewModel) {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val route = backStack?.destination?.route ?: "home"
    val snackbar = remember { SnackbarHostState() }

    val toast = state.toast
    LaunchedEffect(toast) {
        if (toast != null) {
            val undone = snackbar.showSnackbar(toast, actionLabel = "Undo") ==
                androidx.compose.material3.SnackbarResult.ActionPerformed
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
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            NavigationBar {
                TABS.forEach { tab ->
                    NavigationBarItem(
                        selected = route == tab.route,
                        onClick = { if (route != tab.route) nav.navigate(tab.route) },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                    )
                }
            }
        },
    ) { padding ->
        NavHost(nav, startDestination = "home", modifier = Modifier.padding(padding)) {
            composable("home") { HomeScreen(state, actions) }
            composable("collections") { CollectionsScreen(state, actions) }
            composable("settings") { SettingsScreen() }
        }
    }
}
