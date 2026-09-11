package com.cliplo.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cliplo.app.ui.ClipViewModel
import com.cliplo.app.ui.HomeState
import com.cliplo.app.ui.SmartCollection
import com.cliplo.app.ui.components.EmptyState

/**
 * Collections is the organization space (PRODUCT_UX_DECISIONS):
 * smart + custom side by side, with quiet suggestion slot.
 */
@Composable
fun CollectionsScreen(state: HomeState, actions: ClipViewModel) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Collections", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Cliplo suggests — you decide. Nothing is grouped without confirmation.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        if (state.clips.isEmpty()) {
            EmptyState("No collections yet", "Copy a few things and smart groups will appear here.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                items(SmartCollection.entries) { col ->
                    Card(
                        onClick = { actions.onCollectionChange(col) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            col.label,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
            }
        }
    }
}
