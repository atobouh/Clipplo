package com.cliplo.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cliplo.app.ui.ClipViewModel
import com.cliplo.app.ui.HomeState
import com.cliplo.app.ui.SmartCollection
import com.cliplo.app.ui.components.CapturePanel
import com.cliplo.app.ui.components.ClipCard
import com.cliplo.app.ui.components.EmptyState
import com.cliplo.app.ui.components.LoadingState

/** Main library: title zone + feed zone + bottom capture panel. */
@Composable
fun HomeScreen(state: HomeState, actions: ClipViewModel) {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize()) {
        Text(
            "Cliplo",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp),
        )
        Text(
            state.collection.label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 20.dp, bottom = 8.dp),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            items(SmartCollection.entries) { col ->
                FilterChip(
                    selected = state.collection == col,
                    onClick = { actions.onCollectionChange(col) },
                    label = { Text(col.label) },
                )
            }
        }
        if (state.isLoading) {
            LoadingState(Modifier.weight(1f))
        } else if (state.clips.isEmpty()) {
            EmptyState(
                title = if (state.query.isBlank()) "Copy once. Use anytime." else "Nothing matched that",
                body = if (state.query.isBlank()) "Anything you copy while Cliplo is open lands here."
                else "Try a different word, source app, or tag.",
                modifier = Modifier.weight(1f),
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                items(state.clips, key = { it.id }) { clip ->
                    ClipCard(
                        clip = clip,
                        onCopy = { actions.copyBack(context, clip) },
                        onPin = { actions.togglePin(clip) },
                        onFavorite = { actions.toggleFavorite(clip) },
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }
        }
        CapturePanel(
            query = state.query,
            onQueryChange = actions::onQueryChange,
            onSubmitCapture = { actions.capture(it) },
        )
    }
}
