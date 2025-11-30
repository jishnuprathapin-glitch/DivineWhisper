package com.divinewhisper.feature.notifications

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.divinewhisper.core.database.DivineWhisperDatabase
import com.divinewhisper.core.model.QuietHours
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
        val database = DivineWhisperDatabase.getInstance(context)
        val prefsRepository = UserPreferencesRepository(database.userPrefsDao())
        val prefs = runBlocking { prefsRepository.get() }
        val now = LocalDateTime.now()
        val times = computeSlots(
            frequencyPerDay = prefs.frequencyPerDay,
            windowStart = prefs.windowStart,
            windowEnd = prefs.windowEnd,
            minGapMinutes = prefs.minGapMinutes,
            quietHours = prefs.quietHours,
            now = now
        )

        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(WORK_TAG)
        workManager.cancelUniqueWork(UNIQUE_WORK_NAME)

        if (times.isEmpty()) return

        val requests = times.map { time ->
            val delayMinutes = Duration.between(now, time)
                .toMinutes()
                .coerceAtLeast(0)
            OneTimeWorkRequestBuilder<VerseNotificationWorker>()
                .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
                .addTag(WORK_TAG)
                .build()
        }

        val uniqueWork = workManager.beginUniqueWork(
            UNIQUE_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            requests.first()
        )

        val continuation = requests
            .drop(1)
            .fold(uniqueWork) { chain, request -> chain.then(request) }

        continuation.enqueue()
    }

    internal fun computeSlots(
        frequencyPerDay: Int,
        windowStart: LocalTime,
        windowEnd: LocalTime,
        minGapMinutes: Int,
        quietHours: QuietHours?,
        now: LocalDateTime = LocalDateTime.now()
    ): List<LocalDateTime> {
        if (frequencyPerDay <= 0) return emptyList()

        val scheduleDate = if (now.toLocalTime().isAfter(windowEnd) || now.toLocalTime() == windowEnd) {
            now.toLocalDate().plusDays(1)
        } else {
            now.toLocalDate()
        }

        var start = LocalDateTime.of(scheduleDate, windowStart)
        var end = LocalDateTime.of(scheduleDate, windowEnd)

        if (now.isAfter(start) && now.isBefore(end)) {
            start = maxOf(start, now.plusMinutes(minGapMinutes.toLong()))
        }

        if (!start.isBefore(end)) {
            start = LocalDateTime.of(scheduleDate.plusDays(1), windowStart)
            end = LocalDateTime.of(scheduleDate.plusDays(1), windowEnd)
        }

        val totalMinutes = Duration.between(start, end).toMinutes()
        if (totalMinutes <= 0) return emptyList()

        val interval = (totalMinutes / frequencyPerDay)
            .coerceAtLeast(minGapMinutes.toLong())
            .coerceAtLeast(1)

        val slots = mutableListOf<LocalDateTime>()
        var cursor = start

        while (slots.size < frequencyPerDay && !cursor.isAfter(end)) {
            val previous = slots.lastOrNull()
            val minAllowed = previous?.plusMinutes(minGapMinutes.toLong()) ?: cursor
            var candidate = maxOf(cursor, minAllowed)

            candidate = adjustForQuietHours(candidate, quietHours)

            if (candidate == null || candidate.isAfter(end)) break

            slots.add(candidate)
            cursor = candidate.plusMinutes(interval)
        }

        return slots.filter { it.isAfter(now) }
    }

    private fun adjustForQuietHours(
        candidate: LocalDateTime,
        quietHours: QuietHours?
    ): LocalDateTime? {
        if (quietHours == null) return candidate

        val quietStart = LocalDateTime.of(candidate.toLocalDate(), quietHours.start)
        val quietEnd = if (quietHours.end.isAfter(quietHours.start)) {
            LocalDateTime.of(candidate.toLocalDate(), quietHours.end)
        } else {
            LocalDateTime.of(candidate.toLocalDate().plusDays(1), quietHours.end)
        }

        return if (!candidate.isBefore(quietStart) && candidate.isBefore(quietEnd)) {
            quietEnd
        } else {
            candidate
        }
    }
}

const val WORK_TAG = "divine_whisper_verse"
