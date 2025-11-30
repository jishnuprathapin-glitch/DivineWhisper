package com.divinewhisper.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.divinewhisper.core.model.Source

@Database(
    entities = [VerseEntity::class, UserPrefsEntity::class, ShownLogEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(SourceConverters::class)
abstract class DivineWhisperDatabase : RoomDatabase() {
    abstract fun verseDao(): VerseDao
    abstract fun userPrefsDao(): UserPrefsDao
    abstract fun shownLogDao(): ShownLogDao

    companion object {
        fun build(context: Context): DivineWhisperDatabase {
            return Room.databaseBuilder(
                context,
                DivineWhisperDatabase::class.java,
                "divine_whisper.db"
            ).createFromAsset("database/divine_whisper.db").build()
        }
    }
}

class SourceConverters {
    @androidx.room.TypeConverter
    fun toSource(value: String): Source = Source.valueOf(value)

    @androidx.room.TypeConverter
    fun fromSource(source: Source): String = source.name
}
