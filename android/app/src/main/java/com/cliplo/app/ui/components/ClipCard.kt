package com.cliplo.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cliplo.app.data.Clip

/** One reusable card system (VISUAL_FEATURES §2): source, preview, type, time, copy. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClipCard(
    clip: Clip,
    onOpen: () -> Unit = {},
    onCopy: () -> Unit,
    onPin: () -> Unit,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(onClick = onOpen, modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = clip.service?.uppercase() ?: clip.type.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = clip.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                )
                Text(
                    text = metaLine(clip),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = onCopy) {
                Icon(Icons.Filled.ContentCopy, contentDescription = "Copy")
            }
        }
        Row(modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)) {
            IconButton(onClick = onPin) {
                Icon(
                    Icons.Filled.PushPin,
                    contentDescription = if (clip.isPinned) "Unpin" else "Pin",
                    tint = if (clip.isPinned) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = onFavorite) {
                Icon(
                    if (clip.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (clip.isFavorite) "Unfavorite" else "Favorite",
                    tint = if (clip.isFavorite) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun metaLine(clip: Clip): String {
    val age = android.text.format.DateUtils.getRelativeTimeSpanString(clip.createdAt).toString()
    val bits = mutableListOf(age)
    clip.sourceApp?.let { bits += it }
    if (clip.copyCount > 0) bits += "×${clip.copyCount}"
    return bits.joinToString(" · ")
}
