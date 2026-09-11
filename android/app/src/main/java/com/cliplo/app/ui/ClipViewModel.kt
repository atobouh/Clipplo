package com.cliplo.app.ui

import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliplo.app.data.BlockReason
import com.cliplo.app.data.CaptureResult
import com.cliplo.app.data.Clip
import com.cliplo.app.data.ClipRepository
import com.cliplo.app.data.ClipType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** Smart collections: derived filters, never auto-created (PRODUCT_UX_DECISIONS). */
enum class SmartCollection(val label: String, val type: ClipType?) {
    ALL("All Clips", null),
    LINKS("Links", ClipType.LINK),
    CONTACTS("Contacts", null),
    CODE("Code", ClipType.CODE),
    IMAGES("Images", ClipType.IMAGE),
    MESSAGES("Messages", ClipType.MESSAGE),
    NOTES("Notes", ClipType.NOTE),
    SENSITIVE("Sensitive", ClipType.SENSITIVE),
    PINNED("Pinned", null),
    FAVORITES("Favorites", null),
}

data class HomeState(
    val clips: List<Clip> = emptyList(),
    val query: String = "",
    val collection: SmartCollection = SmartCollection.ALL,
    val captureEnabled: Boolean = true,
    /** Null = no toast. Undo restores [lastDeleted]. */
    val toast: String? = null,
    val lastDeleted: Clip? = null,
    val blocked: BlockReason? = null,
    val isLoading: Boolean = true,
)

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ClipViewModel(private val repo: ClipRepository) : ViewModel() {

    private val query = MutableStateFlow("")
    private val collection = MutableStateFlow(SmartCollection.ALL)
    private val toast = MutableStateFlow<String?>(null)
    private val lastDeleted = MutableStateFlow<Clip?>(null)
    private val blocked = MutableStateFlow<BlockReason?>(null)
    private val lastSeenClipboard = MutableStateFlow<String?>(null)

    val state: StateFlow<HomeState> = combine(
        query.debounce(150L).distinctUntilChanged(),
        collection,
        toast,
        lastDeleted,
        blocked,
    ) { q, col, t, deleted, block -> Quad(q, col, t, deleted, block) }
        .flatMapLatest { (q, col, t, deleted, block) ->
            val clipsFlow = when {
                q.isNotBlank() -> repo.search(ftsQuery(q))
                col == SmartCollection.PINNED -> repo.pinned()
                col == SmartCollection.FAVORITES -> repo.favorites()
                col.type != null -> repo.byType(col.type)
                col == SmartCollection.CONTACTS ->
                    combine(repo.byType(ClipType.PHONE), repo.byType(ClipType.EMAIL)) { a, b ->
                        (a + b).sortedByDescending { it.createdAt }
                    }
                else -> repo.feed()
            }
            combine(clipsFlow, lastSeenClipboard) { clips, _ ->
                HomeState(
                    clips = clips, query = q, collection = col,
                    toast = t, lastDeleted = deleted, blocked = block,
                    isLoading = false,
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeState())

    fun onQueryChange(value: String) { query.value = value }
    fun onCollectionChange(value: SmartCollection) { collection.value = value }
    fun dismissToast() { toast.value = null; lastDeleted.value = null; blocked.value = null }

    fun capture(rawText: String, sourceApp: String? = null) {
        viewModelScope.launch {
            when (val result = repo.capture(rawText, sourceApp)) {
                is CaptureResult.Captured -> toast.value = "Saved to ${state.value.collection.label}"
                is CaptureResult.Duplicate -> toast.value = "Already saved — moved to top"
                is CaptureResult.Blocked -> blocked.value = result.reason
            }
        }
    }

    /** Foreground-only poll; dedups against the last seen value in-session. */
    fun pollClipboard(context: Context) {
        viewModelScope.launch {
            val manager = context.getSystemService(ClipboardManager::class.java) ?: return@launch
            val text = manager.primaryClip
                ?.takeIf { it.itemCount > 0 }
                ?.getItemAt(0)?.coerceToText(context)?.toString()?.trim()
                ?: return@launch
            if (text.isEmpty() || text == lastSeenClipboard.value) return@launch
            lastSeenClipboard.value = text
            when (val result = repo.capture(text, sourceApp = null)) {
                is CaptureResult.Captured -> toast.value = "Clip captured"
                is CaptureResult.Duplicate -> Unit // silent: already in library
                is CaptureResult.Blocked -> blocked.value = result.reason
            }
        }
    }

    fun copyBack(context: Context, clip: Clip) {
        val manager = context.getSystemService(ClipboardManager::class.java) ?: return
        manager.setText(clip.cleanText)
        viewModelScope.launch { repo.recordReuse(clip.id) }
        toast.value = "Copied"
    }

    fun togglePin(clip: Clip) = viewModelScope.launch { repo.togglePin(clip.id, !clip.isPinned) }
    fun toggleFavorite(clip: Clip) = viewModelScope.launch { repo.toggleFavorite(clip.id, !clip.isFavorite) }

    fun archive(clip: Clip) {
        viewModelScope.launch {
            repo.archive(clip.id, true)
            lastDeleted.value = clip
            toast.value = "Archived"
        }
    }

    fun delete(clip: Clip) {
        viewModelScope.launch {
            repo.delete(clip.id)
            lastDeleted.value = clip
            toast.value = "Deleted"
        }
    }

    fun undo() {
        val clip = lastDeleted.value ?: return
        viewModelScope.launch {
            if (clip.isArchived) repo.archive(clip.id, false)
            else repo.restore(clip)
            dismissToast()
        }
    }

    private fun ftsQuery(raw: String): String {
        // Prefix-match every token; quote-wrap to keep FTS syntax safe.
        return raw.trim().split(Regex("\\s+")).take(8)
            .filter { it.isNotEmpty() }
            .joinToString(" ") { "\"$it\"*" }
            .ifEmpty { "\"\"" }
    }

    private data class Quad(
        val q: String, val col: SmartCollection, val t: String?,
        val deleted: Clip?, val block: BlockReason?,
    )
}
