package com.cliplo.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Fts4
import androidx.room.Index
import androidx.room.PrimaryKey

/** Clip types from MUST_HAVE_FEATURES.md smart-organization + FEATURES.md classification. */
enum class ClipType {
    LINK, PHONE, EMAIL, ADDRESS, CODE, IMAGE,
    MESSAGE, NOTE, OTP, SENSITIVE,
}

/** Background enrichment state. Capture never waits on the network. */
enum class EnrichStatus { PENDING, READY, FAILED }

@Entity(
    tableName = "clips",
    indices = [
        Index("canonicalIdentity", unique = false),
        Index("type"),
        Index("createdAt"),
    ],
)
data class Clip(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    /** Original value, never silently destroyed. */
    val rawText: String,
    /** Cleaned display value (tracking params stripped for links). */
    val cleanText: String,
    /** Dedup key: canonical URL for links, trimmed text hash otherwise. */
    val canonicalIdentity: String,
    val title: String,
    val url: String? = null,
    val service: String? = null,
    val type: ClipType = ClipType.NOTE,
    val sourceApp: String? = null,
    val tagsCsv: String = "",
    val isPinned: Boolean = false,
    val isFavorite: Boolean = false,
    val isArchived: Boolean = false,
    val isSensitive: Boolean = false,
    val enrichStatus: EnrichStatus = EnrichStatus.READY,
    val copyCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val lastReusedAt: Long = System.currentTimeMillis(),
)

/** Full-text index over the searchable clip surface. */
@Fts4(contentEntity = Clip::class)
@Entity(tableName = "clips_fts")
data class ClipFts(
    @PrimaryKey @ColumnInfo(name = "rowid") val rowId: Long,
    val title: String,
    val cleanText: String,
    val url: String?,
    val sourceApp: String?,
    val tagsCsv: String,
)

@Entity(tableName = "folders")
data class Folder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    /** Hex color string, e.g. "#287D6B". */
    val colorHex: String = "#287D6B",
    val iconKey: String = "folder",
    val sortOrder: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
)

@Entity(
    tableName = "clip_folder_cross_ref",
    primaryKeys = ["clipId", "folderId"],
    foreignKeys = [
        ForeignKey(entity = Clip::class, parentColumns = ["id"], childColumns = ["clipId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Folder::class, parentColumns = ["id"], childColumns = ["folderId"], onDelete = ForeignKey.CASCADE),
    ],
)
data class ClipFolderCrossRef(
    val clipId: Long,
    val folderId: Long,
)
