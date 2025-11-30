package com.divinewhisper.core.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

enum class Source {
    BIBLE,
    QURAN,
    GITA
}

data class Verse(
    val id: Long,
    val source: Source,
    val book: String,
    val chapter: Int,
    val verseNumber: Int,
    val text: String,
    val tags: List<String> = emptyList(),
    val popularityScore: Double = 0.0,
    val lengthBucket: String = "medium"
)

data class UserPreferences(
    val sourcesEnabled: Set<Source> = emptySet(),
    val frequencyPerDay: Int = 2,
    val windowStart: LocalTime = LocalTime.of(9, 0),
    val windowEnd: LocalTime = LocalTime.of(20, 0),
    val minGapMinutes: Int = 180,
    val quietHours: QuietHours? = null,
    val lastOnboardingVersion: Int = 1,
    val intentTag: String? = null
)

data class QuietHours(
    val start: LocalTime,
    val end: LocalTime
)

data class ShownLog(
    val id: Long = 0,
    val timestamp: LocalDateTime,
    val verseId: Long,
    val source: Source
)

data class NotificationPlan(
    val plannedTimes: List<LocalDateTime>,
    val generatedAt: LocalDate = LocalDate.now()
)
