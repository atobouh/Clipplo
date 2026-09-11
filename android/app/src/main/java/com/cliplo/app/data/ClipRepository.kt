package com.cliplo.app.data

import android.content.ClipboardManager
import android.content.Context
import com.cliplo.app.capture.CapturePrefs
import com.cliplo.app.capture.TrialGate
import com.cliplo.app.pipeline.Classify
import com.cliplo.app.pipeline.Normalize
import com.cliplo.app.pipeline.identifyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

sealed interface CaptureResult {
    data class Captured(val clip: Clip) : CaptureResult
    data class Duplicate(val clip: Clip) : CaptureResult
    data class Blocked(val reason: BlockReason) : CaptureResult
}

enum class BlockReason { PAUSED, EXCLUDED_APP, FREE_LIMIT }

/**
 * Capture orchestrator: capture → normalize → identify → classify → store.
 * Never waits on the network; links save instantly, enrichment stays pending.
 */
class ClipRepository(
    private val db: ClipDatabase,
    private val context: Context,
    private val prefs: CapturePrefs,
) {
    private val dao get() = db.clips()

    fun feed(): Flow<List<Clip>> = dao.observeFeed()
    fun pinned(): Flow<List<Clip>> = dao.observePinned()
    fun favorites(): Flow<List<Clip>> = dao.observeFavorites()
    fun byType(type: ClipType): Flow<List<Clip>> = dao.observeByType(type)
    fun search(query: String): Flow<List<Clip>> = dao.search(query)
    fun folders() = dao.observeFolders()
    fun folderClips(folderId: Long) = dao.observeFolderClips(folderId)

    suspend fun capture(rawText: String, sourceApp: String?): CaptureResult {
        val text = rawText.trim()
        if (text.isEmpty()) return CaptureResult.Blocked(BlockReason.PAUSED)
        if (prefs.enabled.first().not()) return CaptureResult.Blocked(BlockReason.PAUSED)
        if (sourceApp != null && prefs.excludedApps.first().contains(sourceApp)) {
            return CaptureResult.Blocked(BlockReason.EXCLUDED_APP)
        }

        val normalized = Normalize.run(text)
        dao.findByIdentity(normalized.identity)?.let { existing ->
            dao.recordReuse(existing.id)
            return CaptureResult.Duplicate(existing)
        }

        if (!TrialGate.isTrialActive(context)) {
            if (dao.activeCount() >= TrialGate.MAX_FREE_CLIPS) {
                return CaptureResult.Blocked(BlockReason.FREE_LIMIT)
            }
        }

        val service = identifyService(normalized.url)
        val classification = Classify.run(normalized.displayText, normalized.url, service)
        val clip = Clip(
            rawText = text,
            cleanText = normalized.displayText,
            canonicalIdentity = normalized.identity,
            title = classification.title,
            url = normalized.url,
            service = service?.key,
            type = classification.type,
            sourceApp = sourceApp,
            tagsCsv = classification.tags.joinToString(","),
            isSensitive = classification.sensitive,
        )
        val id = dao.insert(clip)
        return CaptureResult.Captured((dao.getById(id) ?: clip))
    }

    /** Foreground clipboard poll. Call only while the app is focused (OS rule). */
    suspend fun pollClipboard(clipboard: ClipboardManager, sourceApp: String? = null): CaptureResult? {
        val text = clipboard.primaryClip
            ?.takeIf { it.itemCount > 0 }
            ?.getItemAt(0)?.coerceToText(context)?.toString()
            ?: return null
        return capture(text, sourceApp)
    }

    suspend fun togglePin(id: Long, pinned: Boolean) = dao.setPinned(id, pinned)
    suspend fun toggleFavorite(id: Long, favorite: Boolean) = dao.setFavorite(id, favorite)
    suspend fun archive(id: Long, archived: Boolean) = dao.setArchived(id, archived)
    suspend fun delete(id: Long) = dao.deleteById(id)
    suspend fun restore(clip: Clip) = dao.insert(clip.copy(id = 0))
    suspend fun recordReuse(id: Long) = dao.recordReuse(id)

    suspend fun createFolder(name: String, colorHex: String = "#287D6B"): Long =
        dao.insertFolder(Folder(name = name.trim(), colorHex = colorHex))

    suspend fun moveToFolder(clipId: Long, folderId: Long) =
        dao.assignToFolder(ClipFolderCrossRef(clipId, folderId))
}
