package com.cliplo.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cliplo.app.ui.ClipViewModel
import com.cliplo.app.ui.HomeState
import com.cliplo.app.ui.SmartCollection
import com.cliplo.app.ui.components.CapturePanel
import com.cliplo.app.ui.components.EmptyState
import com.cliplo.app.ui.components.FeedRow
import com.cliplo.app.ui.components.LoadingState
import com.cliplo.app.ui.theme.LocalCliplo

private enum class HomeView { LIST, FOLDERS }

/** Single-screen shell (ui-proto): topbar + feed/folders + floating capture bar. */
@Composable
fun HomeScreen(state: HomeState, actions: ClipViewModel, onOpenSettings: () -> Unit) {
    val c = LocalCliplo.current
    var view by rememberSaveable { mutableStateOf(HomeView.LIST) }

    Box(Modifier.fillMaxSize().background(c.page)) {
        Column(Modifier.fillMaxSize()) {
            TopBar(view, onView = { view = it }, onSettings = onOpenSettings)
            Box(Modifier.weight(1f).fillMaxWidth()) {
                when (view) {
                    HomeView.LIST -> ListFeed(state, actions)
                    HomeView.FOLDERS -> FoldersGrid(
                        state = state,
                        onOpen = { actions.onCollectionChange(it); view = HomeView.LIST },
                        onNewFolder = { },
                    )
                }
            }
        }
        CapturePanel(
            query = state.query,
            onQueryChange = actions::onQueryChange,
            onSubmitCapture = { actions.capture(it) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 16.dp),
        )
    }
}

@Composable
private fun TopBar(view: HomeView, onView: (HomeView) -> Unit, onSettings: () -> Unit) {
    val c = LocalCliplo.current
    Row(
        Modifier.fillMaxWidth().padding(start = 20.dp, end = 12.dp, top = 22.dp, bottom = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text("Cliplo", style = MaterialTheme.typography.headlineSmall, color = c.ink)
            Text("your reusable memory", style = MaterialTheme.typography.bodySmall, color = c.dim)
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            ViewToggle(view, onView)
            IconButton(onClick = onSettings) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = c.dim)
            }
        }
    }
}

@Composable
private fun ViewToggle(view: HomeView, onView: (HomeView) -> Unit) {
    val c = LocalCliplo.current
    Row(
        Modifier.background(c.surface2, RoundedCornerShape(11.dp)).padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        ToggleCell(Icons.AutoMirrored.Filled.List, view == HomeView.LIST) { onView(HomeView.LIST) }
        ToggleCell(Icons.Filled.GridView, view == HomeView.FOLDERS) { onView(HomeView.FOLDERS) }
    }
}

@Composable
private fun ToggleCell(icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    val c = LocalCliplo.current
    Box(
        Modifier
            .size(width = 34.dp, height = 32.dp)
            .background(if (selected) c.surface else androidx.compose.ui.graphics.Color.Transparent, RoundedCornerShape(9.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = if (selected) c.ink else c.dim, modifier = Modifier.size(17.dp))
    }
}

@Composable
private fun ListFeed(state: HomeState, actions: ClipViewModel) {
    val c = LocalCliplo.current
    val context = LocalContext.current
    when {
        state.isLoading -> LoadingState()
        state.clips.isEmpty() -> Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
            VaultStrip()
            SectionLabel(state.collection)
            EmptyState(
                title = if (state.query.isBlank()) "Copy once. Use anytime." else "Nothing matched that",
                body = if (state.query.isBlank()) "Anything you copy while Cliplo is open lands here."
                else "Try a different word, source app, or tag.",
            )
        }
        else -> LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 0.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            item { VaultStrip() }
            item { SectionLabel(state.collection) }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(c.surface, RoundedCornerShape(20.dp))
                        .border(1.dp, c.line, RoundedCornerShape(20.dp)),
                ) {
                    state.clips.forEachIndexed { i, clip ->
                        FeedRow(
                            clip = clip,
                            onCopy = { actions.copyBack(context, clip) },
                            onPin = { actions.togglePin(clip) },
                        )
                        if (i != state.clips.lastIndex) HorizontalDivider(color = c.line)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(collection: SmartCollection) {
    val c = LocalCliplo.current
    Text(
        collection.label.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = c.faint,
        modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 12.dp),
    )
}

@Composable
private fun VaultStrip() {
    val c = LocalCliplo.current
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp)
            .background(c.surface, RoundedCornerShape(16.dp))
            .border(1.dp, c.accentSoft, RoundedCornerShape(16.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(40.dp).background(c.accentSoft, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) { Icon(Icons.Filled.VpnKey, contentDescription = null, tint = c.accent, modifier = Modifier.size(20.dp)) }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("Vault", style = MaterialTheme.typography.titleMedium, color = c.ink)
            Text("Protected clips, kept private", style = MaterialTheme.typography.bodySmall, color = c.dim)
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = c.faint, modifier = Modifier.size(20.dp))
    }
}
