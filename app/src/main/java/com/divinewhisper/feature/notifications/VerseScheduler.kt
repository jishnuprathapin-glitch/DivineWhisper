package com.divinewhisper.feature.notifications

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.divinewhisper.core.database.DivineWhisperDatabase
import com.divinewhisper.feature.settings.UserPreferencesRepository
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking

object VerseScheduler {
    private const val UNIQUE_WORK_NAME = "divine_whisper_daily_schedule"

    fun initialize(context: Context) {
        seedDailySchedule(context)
    }

    fun seedDailySchedule(context: Context) {
        val database = DivineWhisperDatabase.build(context)
        val prefsRepository = UserPreferencesRepository(database.userPrefsDao())
        val prefs = runBlocking { prefsRepository.get() }
        val times = computeSlots(
            frequencyPerDay = prefs.frequencyPerDay,
            windowStart = prefs.windowStart,
            windowEnd = prefs.windowEnd,
            minGapMinutes = prefs.minGapMinutes
        )
        val requests = times.map { time ->
            val delayMinutes = Duration.between(LocalDateTime.now(), time).toMinutes().coerceAtLeast(0)
            OneTimeWorkRequestBuilder<VerseNotificationWorker>()
                .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
                .addTag(WORK_TAG)
                .build()
        }

        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(WORK_TAG)
        workManager.enqueueUniqueWork(
            UNIQUE_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            requests
        )
    }

    private fun computeSlots(
        frequencyPerDay: Int,
        windowStart: LocalTime,
        windowEnd: LocalTime,
        minGapMinutes: Int
    ): List<LocalDateTime> {
        if (frequencyPerDay <= 0) return emptyList()
        val start = LocalDateTime.of(LocalDate.now(), windowStart)
        val end = LocalDateTime.of(LocalDate.now(), windowEnd)
        val totalMinutes = Duration.between(start, end).toMinutes().coerceAtLeast(0)
        val interval = (totalMinutes / frequencyPerDay).coerceAtLeast(minGapMinutes.toLong())
        return (0 until frequencyPerDay).map { index ->
            val candidate = start.plusMinutes(interval * index)
            if (candidate.isAfter(end)) end.minusMinutes(5) else candidate
        }
    }
}

const val WORK_TAG = "divine_whisper_verse"
