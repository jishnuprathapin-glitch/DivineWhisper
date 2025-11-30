package com.divinewhisper.feature.notifications

import android.content.Context
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

        workManager.enqueue(requests)
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

        // If the current time has reached or passed the end of the window,
        // schedule for the next day instead of returning an empty plan.
        val scheduleDate = if (now.toLocalTime().isAfter(windowEnd) || now.toLocalTime() == windowEnd) {
            now.toLocalDate().plusDays(1)
        } else {
            now.toLocalDate()
        }

        var start = LocalDateTime.of(scheduleDate, windowStart)
        val end = LocalDateTime.of(scheduleDate, windowEnd)

        if (now.isAfter(start) && now.isBefore(end)) {
            start = now.plusMinutes(minGapMinutes.toLong())
        }

        val totalMinutes = Duration.between(start, end).toMinutes()
        if (totalMinutes <= 0) return emptyList()

        val interval = (totalMinutes / frequencyPerDay).coerceAtLeast(minGapMinutes.toLong())
        val slots = mutableListOf<LocalDateTime>()
        var cursor = start

        repeat(frequencyPerDay) {
            var candidate = cursor
            val previous = slots.lastOrNull()
            if (previous != null) {
                val nextAllowed = previous.plusMinutes(minGapMinutes.toLong())
                if (candidate.isBefore(nextAllowed)) {
                    candidate = nextAllowed
                }
            }

            candidate = adjustForQuietHours(candidate, quietHours)
            if (candidate == null || candidate.isAfter(end)) return@repeat

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
