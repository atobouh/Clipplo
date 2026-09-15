package com.cliplo.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cliplo.app.ui.theme.LocalCliplo

/**
 * Floating capture / control bar (ui-proto .capture): mineral rounded surface pinned
 * to the bottom. The accent mode tile leads; the field doubles as paste-capture and search.
 */
@Composable
fun CapturePanel(
    query: String,
    onQueryChange: (String) -> Unit,
    onSubmitCapture: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = LocalCliplo.current
    val searching = query.isNotBlank()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(18.dp, RoundedCornerShape(19.dp), clip = false)
            .background(c.surface, RoundedCornerShape(19.dp))
            .border(1.dp, c.lineStrong, RoundedCornerShape(19.dp))
            .heightIn(min = 58.dp)
            .padding(horizontal = 11.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(40.dp).background(c.accentSoft, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                if (searching) Icons.Filled.Search else Icons.Filled.ContentPaste,
                contentDescription = null,
                tint = c.accent,
                modifier = Modifier.size(20.dp),
            )
        }
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (query.isEmpty()) {
                Text(
                    "Paste to capture — or type to search",
                    style = MaterialTheme.typography.bodyMedium,
                    color = c.faint,
                )
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = c.ink, fontSize = 14.sp),
                cursorBrush = androidx.compose.ui.graphics.SolidColor(c.accent),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (query.isNotBlank()) {
                        onSubmitCapture(query)
                        onQueryChange("")
                    }
                }),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
