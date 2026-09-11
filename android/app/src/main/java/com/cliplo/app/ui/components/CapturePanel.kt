package com.cliplo.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Bottom capture / control panel (VISUAL_FEATURES §1, §7):
 * one clear mode — paste-to-capture input doubles as search entry.
 */
@Composable
fun CapturePanel(
    query: String,
    onQueryChange: (String) -> Unit,
    onSubmitCapture: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Paste something to capture — or type to search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotBlank()) {
                IconButton(onClick = {
                    onSubmitCapture(query)
                    onQueryChange("")
                }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Capture and clear")
                }
            }
        },
        singleLine = true,
    )
}
