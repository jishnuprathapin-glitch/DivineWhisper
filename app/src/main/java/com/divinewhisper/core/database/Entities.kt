package com.divinewhisper.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.divinewhisper.core.model.Source

@Entity(tableName = "verses")
data class VerseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val source: Source,
    val book: String,
    val chapter: Int,
    @ColumnInfo(name = "verse_number") val verseNumber: Int,
    val text: String,
    val tags: String,
    @ColumnInfo(name = "popularity_score") val popularityScore: Double,
    @ColumnInfo(name = "length_bucket") val lengthBucket: String,
    val checksum: String
)

@Entity(tableName = "user_prefs")
data class UserPrefsEntity(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "sources_enabled") val sourcesEnabled: String,
    @ColumnInfo(name = "frequency_per_day") val frequencyPerDay: Int,
    @ColumnInfo(name = "window_start_minutes") val windowStartMinutes: Int,
    @ColumnInfo(name = "window_end_minutes") val windowEndMinutes: Int,
    @ColumnInfo(name = "min_gap_minutes") val minGapMinutes: Int,
    @ColumnInfo(name = "quiet_hours_start") val quietHoursStart: Int?,
    @ColumnInfo(name = "quiet_hours_end") val quietHoursEnd: Int?,
    @ColumnInfo(name = "last_onboarding_version") val lastOnboardingVersion: Int,
    @ColumnInfo(name = "intent_tag") val intentTag: String?
)

@Entity(tableName = "shown_log")
data class ShownLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    @ColumnInfo(name = "verse_id") val verseId: Long,
    val source: Source
)
