package com.cliplo.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClipDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(clip: Clip): Long

    @Update
    suspend fun update(clip: Clip)

    @Query("SELECT * FROM clips WHERE id = :id")
    suspend fun getById(id: Long): Clip?

    @Query("SELECT * FROM clips WHERE canonicalIdentity = :identity LIMIT 1")
    suspend fun findByIdentity(identity: String): Clip?

    @Query("DELETE FROM clips WHERE id = :id")
    suspend fun deleteById(id: Long)

    /** Home feed: pinned first, then most recent. Archived hidden by default. */
    @Query(
        """SELECT * FROM clips WHERE isArchived = 0
           ORDER BY isPinned DESC, createdAt DESC""",
    )
    fun observeFeed(): Flow<List<Clip>>

    @Query("SELECT * FROM clips WHERE isPinned = 1 AND isArchived = 0 ORDER BY createdAt DESC")
    fun observePinned(): Flow<List<Clip>>

    @Query("SELECT * FROM clips WHERE isFavorite = 1 AND isArchived = 0 ORDER BY createdAt DESC")
    fun observeFavorites(): Flow<List<Clip>>

    @Query("SELECT * FROM clips WHERE type = :type AND isArchived = 0 ORDER BY createdAt DESC")
    fun observeByType(type: ClipType): Flow<List<Clip>>

    /** FTS search across title / text / url / source / tags. */
    @Query(
        """SELECT c.* FROM clips c JOIN clips_fts f ON c.id = f.rowid
           WHERE clips_fts MATCH :query AND c.isArchived = 0
           ORDER BY c.isPinned DESC, c.createdAt DESC""",
    )
    fun search(query: String): Flow<List<Clip>>

    @Query("UPDATE clips SET isPinned = :pinned WHERE id = :id")
    suspend fun setPinned(id: Long, pinned: Boolean)

    @Query("UPDATE clips SET isFavorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: Long, favorite: Boolean)

    @Query("UPDATE clips SET isArchived = :archived WHERE id = :id")
    suspend fun setArchived(id: Long, archived: Boolean)

    @Query("UPDATE clips SET copyCount = copyCount + 1, lastReusedAt = :now WHERE id = :id")
    suspend fun recordReuse(id: Long, now: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM clips WHERE isArchived = 0")
    suspend fun activeCount(): Int

    // -- Folders -----------------------------------------------------------

    @Insert
    suspend fun insertFolder(folder: Folder): Long

    @Query("SELECT * FROM folders ORDER BY sortOrder ASC, createdAt ASC")
    fun observeFolders(): Flow<List<Folder>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun assignToFolder(ref: ClipFolderCrossRef)

    @Query("DELETE FROM clip_folder_cross_ref WHERE clipId = :clipId AND folderId = :folderId")
    suspend fun removeFromFolder(clipId: Long, folderId: Long)

    @Query("SELECT c.* FROM clips c INNER JOIN clip_folder_cross_ref r ON r.clipId = c.id WHERE r.folderId = :folderId ORDER BY c.createdAt DESC")
    fun observeFolderClips(folderId: Long): Flow<List<Clip>>

    @Transaction
    suspend fun insertWithFolders(clip: Clip, folderIds: List<Long>): Long {
        val id = insert(clip)
        folderIds.forEach { assignToFolder(ClipFolderCrossRef(id, it)) }
        return id
    }
}
