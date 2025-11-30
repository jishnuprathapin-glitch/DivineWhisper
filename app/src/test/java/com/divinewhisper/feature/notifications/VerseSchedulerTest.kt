package com.divinewhisper.feature.notifications

import com.divinewhisper.core.model.QuietHours
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VerseSchedulerTest {

    @Test
    fun returnsSlotsAfterNowWithMinGapRespected() {
        val now = LocalDateTime.of(2024, 1, 1, 10, 0)

        val slots = VerseScheduler.computeSlots(
            frequencyPerDay = 2,
            windowStart = LocalTime.of(9, 0),
            windowEnd = LocalTime.of(12, 0),
            minGapMinutes = 60,
            quietHours = null,
            now = now
        )

        assertEquals(2, slots.size)
        assertTrue(slots.all { it.isAfter(now) })
        assertTrue(
            Duration.between(slots[0], slots[1]).toMinutes() >= 60
        )
        assertEquals(LocalDate.of(2024, 1, 1), slots.first().toLocalDate())
    }

    @Test
    fun shiftsSlotsOutOfQuietHours() {
        val now = LocalDateTime.of(2024, 1, 1, 11, 0)

        val slots = VerseScheduler.computeSlots(
            frequencyPerDay = 3,
            windowStart = LocalTime.of(12, 0),
            windowEnd = LocalTime.of(18, 0),
            minGapMinutes = 60,
            quietHours = QuietHours(
                start = LocalTime.of(13, 0),
                end = LocalTime.of(15, 0)
            ),
            now = now
        )

        assertEquals(3, slots.size)
        assertEquals(LocalDateTime.of(2024, 1, 1, 12, 0), slots[0])
        assertEquals(LocalDateTime.of(2024, 1, 1, 15, 0), slots[1])
        assertTrue(slots.last().toLocalTime() <= LocalTime.of(18, 0))
    }

    @Test
    fun schedulesNextDayWhenWindowHasPassed() {
        val now = LocalDateTime.of(2024, 1, 1, 21, 0)

        val slots = VerseScheduler.computeSlots(
            frequencyPerDay = 1,
            windowStart = LocalTime.of(9, 0),
            windowEnd = LocalTime.of(20, 0),
            minGapMinutes = 60,
            quietHours = null,
            now = now
        )

        assertEquals(1, slots.size)
        assertEquals(LocalDate.of(2024, 1, 2), slots.first().toLocalDate())
        assertEquals(LocalTime.of(9, 0), slots.first().toLocalTime())
    }

    @Test
    fun schedulesNextDayWhenAtWindowEnd() {
        val now = LocalDateTime.of(2024, 1, 1, 20, 0)

        val slots = VerseScheduler.computeSlots(
            frequencyPerDay = 1,
            windowStart = LocalTime.of(9, 0),
            windowEnd = LocalTime.of(20, 0),
            minGapMinutes = 60,
            quietHours = null,
            now = now
        )

        assertEquals(1, slots.size)
        assertEquals(LocalDate.of(2024, 1, 2), slots.first().toLocalDate())
        assertEquals(LocalTime.of(9, 0), slots.first().toLocalTime())
    }
}
