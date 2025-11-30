package com.divinewhisper.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.divinewhisper.core.model.Source
import kotlin.jvm.Volatile

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
        @Volatile
        private var instance: DivineWhisperDatabase? = null

        fun getInstance(context: Context): DivineWhisperDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context.applicationContext).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DivineWhisperDatabase {
            val databaseName = "divine_whisper.db"
            val assetPath = "database/$databaseName"

            val builder = Room.databaseBuilder(
                context,
                DivineWhisperDatabase::class.java,
                databaseName
            )

            val hasBundledDb = runCatching {
                context.assets.open(assetPath).close()
                true
            }.getOrDefault(false)

            val configuredBuilder = if (hasBundledDb) {
                builder.createFromAsset(assetPath)
            } else {
                builder
            }

            return configuredBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

class SourceConverters {
    @androidx.room.TypeConverter
    fun toSource(value: String): Source = Source.valueOf(value)

    @androidx.room.TypeConverter
    fun fromSource(source: Source): String = source.name
}
