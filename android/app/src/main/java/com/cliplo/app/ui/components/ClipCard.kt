package com.cliplo.app.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cliplo.app.data.Clip
import com.cliplo.app.data.ClipType
import com.cliplo.app.ui.theme.LocalCliplo

/**
 * Feed row (ui-proto .feed-section .clip): small accent tile, catch / title / meta.
 * No visible buttons — tap copies the clip back, long-press pins it.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedRow(
    clip: Clip,
    onCopy: () -> Unit,
    onPin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalCliplo.current
    Row(
        modifier = modifier
            .combinedClickable(onClick = onCopy, onLongClick = onPin)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(32.dp).background(c.accentSoft, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(clipTypeIcon(clip.type), contentDescription = null, tint = c.accent, modifier = Modifier.size(16.dp))
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(catchLine(clip), style = androidx.compose.material3.MaterialTheme.typography.labelMedium, color = c.dim)
            Text(
                clip.title,
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                color = c.ink,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(metaLine(clip), style = androidx.compose.material3.MaterialTheme.typography.bodySmall, color = c.dim)
        }
    }
}

private fun catchLine(clip: Clip): String =
    clip.service?.replaceFirstChar { it.uppercase() }
        ?: when (clip.type) {
            ClipType.LINK -> "Link"
            ClipType.PHONE, ClipType.EMAIL -> "Contact"
            ClipType.CODE -> "Code"
            ClipType.IMAGE -> "Image"
            ClipType.MESSAGE -> "Message"
            ClipType.ADDRESS -> "Address"
            ClipType.OTP -> "Code"
            ClipType.SENSITIVE -> "Protected"
            ClipType.NOTE -> "Note"
        }

private fun metaLine(clip: Clip): String {
    val age = android.text.format.DateUtils.getRelativeTimeSpanString(clip.createdAt).toString()
    val bits = mutableListOf(age)
    clip.sourceApp?.let { bits += it }
    if (clip.copyCount > 0) bits += "reused ×${clip.copyCount}"
    return bits.joinToString(" · ")
}

fun clipTypeIcon(type: ClipType): ImageVector = when (type) {
    ClipType.LINK -> Icons.Filled.Link
    ClipType.PHONE -> Icons.Filled.Call
    ClipType.EMAIL -> Icons.Filled.Email
    ClipType.ADDRESS -> Icons.Filled.Place
    ClipType.CODE -> Icons.Filled.Code
    ClipType.IMAGE -> Icons.Filled.Image
    ClipType.MESSAGE -> Icons.AutoMirrored.Filled.Message
    ClipType.NOTE -> Icons.Filled.Notes
    ClipType.OTP -> Icons.Filled.Password
    ClipType.SENSITIVE -> Icons.Filled.Lock
}
