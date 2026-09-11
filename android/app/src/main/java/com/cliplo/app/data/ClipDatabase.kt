package com.cliplo.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Clip::class, ClipFts::class, Folder::class, ClipFolderCrossRef::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class ClipDatabase : RoomDatabase() {

    abstract fun clips(): ClipDao

    companion object {
        @Volatile private var instance: ClipDatabase? = null

        fun get(context: Context): ClipDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ClipDatabase::class.java,
                    "cliplo.db",
                ).build().also { instance = it }
            }
    }
}
