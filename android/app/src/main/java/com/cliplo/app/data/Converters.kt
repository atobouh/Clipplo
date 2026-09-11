package com.cliplo.app.data

import androidx.room.TypeConverter

/** Room converters for Night One enums. */
class Converters {

    @TypeConverter
    fun clipTypeToString(type: ClipType): String = type.name

    @TypeConverter
    fun stringToClipType(value: String): ClipType =
        runCatching { ClipType.valueOf(value) }.getOrDefault(ClipType.NOTE)

    @TypeConverter
    fun enrichToString(status: EnrichStatus): String = status.name

    @TypeConverter
    fun stringToEnrich(value: String): EnrichStatus =
        runCatching { EnrichStatus.valueOf(value) }.getOrDefault(EnrichStatus.READY)
}
