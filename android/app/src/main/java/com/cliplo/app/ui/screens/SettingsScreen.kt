package com.cliplo.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.cliplo.app.ui.theme.LocalCliplo

/** Settings (ui-proto): grouped rows, accent-soft icon tiles, toggles and chevrons. */
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val c = LocalCliplo.current
    var sensitive by rememberSaveable { mutableStateOf(true) }
    var dark by rememberSaveable { mutableStateOf(false) }
    var rightHanded by rememberSaveable { mutableStateOf(true) }

    Column(
        Modifier.fillMaxSize().background(c.page).verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(bottom = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = c.body)
            }
            Text("Settings", style = MaterialTheme.typography.headlineSmall, color = c.ink)
        }

        GroupLabel("Privacy")
        Card {
            ToggleRow(Icons.Filled.Security, "Sensitive filter", "Block secrets, keys & cards", sensitive) { sensitive = it }
        }
        GroupLabel("Appearance")
        Card {
            ToggleRow(Icons.Filled.DarkMode, "Dark mode", "Follows the system by default", dark) { dark = it }
        }
        GroupLabel("Interaction")
        Card {
            ToggleRow(Icons.Filled.PanTool, "Handedness", "Right-handed layout", rightHanded) { rightHanded = it }
        }
        GroupLabel("Data")
        Card {
            ChevronRow(Icons.Filled.Download, "Export library", "Backup to JSON")
            RowDivider()
            ChevronRow(Icons.Filled.Upload, "Import", "Restore from JSON")
        }
    }
}

@Composable
private fun GroupLabel(text: String) {
    val c = LocalCliplo.current
    Text(text.uppercase(), style = MaterialTheme.typography.labelLarge, color = c.faint,
        modifier = Modifier.padding(start = 4.dp, top = 24.dp, bottom = 12.dp))
}

@Composable
private fun Card(content: @Composable ColumnScope.() -> Unit) {
    val c = LocalCliplo.current
    Column(
        Modifier.fillMaxWidth().background(c.surface, RoundedCornerShape(16.dp)).border(1.dp, c.line, RoundedCornerShape(16.dp)),
        content = content,
    )
}

@Composable
private fun RowDivider() {
    val c = LocalCliplo.current
    androidx.compose.material3.HorizontalDivider(color = c.line)
}

@Composable
private fun RowShell(icon: ImageVector, title: String, desc: String, trailing: @Composable () -> Unit, onClick: (() -> Unit)? = null) {
    val c = LocalCliplo.current
    Row(
        Modifier.fillMaxWidth().let { if (onClick != null) it.clickable(onClick = onClick) else it }.padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(36.dp).background(c.accentSoft, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = c.accent, modifier = Modifier.size(19.dp))
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = c.ink)
            Text(desc, style = MaterialTheme.typography.bodySmall, color = c.dim)
        }
        trailing()
    }
}

@Composable
private fun ToggleRow(icon: ImageVector, title: String, desc: String, checked: Boolean, onCheck: (Boolean) -> Unit) {
    val c = LocalCliplo.current
    RowShell(icon, title, desc, trailing = {
        Switch(
            checked = checked,
            onCheckedChange = onCheck,
            colors = SwitchDefaults.colors(
                checkedThumbColor = c.onAccent,
                checkedTrackColor = c.accent,
                uncheckedThumbColor = c.dim,
                uncheckedTrackColor = c.surface2,
            ),
        )
    })
}

@Composable
private fun ChevronRow(icon: ImageVector, title: String, desc: String) {
    val c = LocalCliplo.current
    RowShell(icon, title, desc, trailing = {
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = c.dim)
    }, onClick = { })
}
