package com.cliplo.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cliplo.app.R
import com.cliplo.app.data.ClipType
import com.cliplo.app.ui.HomeState
import com.cliplo.app.ui.SmartCollection
import com.cliplo.app.ui.theme.LocalCliplo

private data class CollectionCard(
    val label: String,
    val drawable: Int,
    val target: SmartCollection,
    val tintLight: Color,
    val count: (HomeState) -> Int,
)

private val CARDS = listOf(
    CollectionCard("Recent", R.drawable.col_recent, SmartCollection.ALL, Color(0xFFEEF5EE)) { it.clips.size },
    CollectionCard("Links", R.drawable.col_links, SmartCollection.LINKS, Color(0xFFF4F0E6)) { it.countOf(ClipType.LINK) },
    CollectionCard("Messages", R.drawable.col_messages, SmartCollection.MESSAGES, Color(0xFFF2EDE7)) { it.countOf(ClipType.MESSAGE) },
    CollectionCard("Code", R.drawable.col_code, SmartCollection.CODE, Color(0xFFF3ECE9)) { it.countOf(ClipType.CODE) },
    CollectionCard("People & places", R.drawable.col_people, SmartCollection.CONTACTS, Color(0xFFEEF1E9)) {
        it.countOf(ClipType.PHONE) + it.countOf(ClipType.EMAIL) + it.countOf(ClipType.ADDRESS)
    },
    CollectionCard("Images", R.drawable.col_images, SmartCollection.IMAGES, Color(0xFFF0F2E8)) { it.countOf(ClipType.IMAGE) },
    CollectionCard("Vault", R.drawable.col_vault, SmartCollection.SENSITIVE, Color(0xFFE9EFEC)) { it.countOf(ClipType.SENSITIVE) },
)

private fun HomeState.countOf(type: ClipType) = clips.count { it.type == type }

/**
 * Collections is the organization space (PRODUCT_UX_DECISIONS): illustrated smart
 * collections in a two-up grid. Tapping one opens its clips in the feed.
 */
@Composable
fun FoldersGrid(
    state: HomeState,
    onOpen: (SmartCollection) -> Unit,
    onNewFolder: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalCliplo.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 4.dp, bottom = 96.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(span = { GridItemSpan(2) }) {
            Column(Modifier.padding(bottom = 4.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    "ORGANIZED FOR REUSE",
                    style = MaterialTheme.typography.labelSmall,
                    color = c.accent,
                )
                Text("Collections", style = MaterialTheme.typography.titleLarge, color = c.ink)
            }
        }
        items(CARDS.size) { i ->
            val card = CARDS[i]
            val tint = if (c.isDark) c.surface else card.tintLight
            Box(
                Modifier
                    .height(152.dp)
                    .background(tint, RoundedCornerShape(18.dp))
                    .border(1.dp, c.line, RoundedCornerShape(18.dp))
                    .clickable { onOpen(card.target) }
                    .padding(14.dp),
            ) {
                Image(
                    painter = painterResource(card.drawable),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.TopStart).size(64.dp),
                )
                Icon(
                    if (card.target == SmartCollection.SENSITIVE) Icons.Filled.Lock else Icons.Filled.NorthEast,
                    contentDescription = null,
                    tint = c.faint,
                    modifier = Modifier.align(Alignment.TopEnd).size(16.dp),
                )
                Column(Modifier.align(Alignment.BottomStart), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        card.label,
                        style = MaterialTheme.typography.titleMedium,
                        color = c.ink,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(subtitle(card, state), style = MaterialTheme.typography.bodySmall, color = c.dim)
                }
            }
        }
        item {
            Box(
                Modifier
                    .height(152.dp)
                    .border(1.dp, c.lineStrong, RoundedCornerShape(18.dp))
                    .clickable { onNewFolder() }
                    .padding(14.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        Modifier.size(40.dp).border(1.dp, c.lineStrong, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center,
                    ) { Icon(Icons.Filled.Add, contentDescription = null, tint = c.accent, modifier = Modifier.size(21.dp)) }
                    Text("New folder", style = MaterialTheme.typography.titleMedium, color = c.ink)
                    Text("Make your own", style = MaterialTheme.typography.bodySmall, color = c.dim)
                }
            }
        }
    }
}

private fun subtitle(card: CollectionCard, state: HomeState): String {
    if (card.target == SmartCollection.SENSITIVE) return "Protected clips"
    val n = card.count(state)
    return "$n clip${if (n == 1) "" else "s"}"
}
