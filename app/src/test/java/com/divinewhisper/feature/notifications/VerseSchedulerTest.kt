package com.divinewhisper.feature.notifications

import com.divinewhisper.core.model.QuietHours
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VerseSchedulerTest {
    @Test
    fun `schedules next day when min gap pushes past window`() {
        val now = LocalDateTime.of(2024, 6, 1, 21, 0)

        val slots = VerseScheduler.computeSlots(
            frequencyPerDay = 2,
            windowStart = LocalTime.of(9, 0),
            windowEnd = LocalTime.of(22, 0),
            minGapMinutes = 180,
            quietHours = null,
            now = now
        )

        assertEquals(2, slots.size)
        assertEquals(LocalDate.of(2024, 6, 2), slots.first().toLocalDate())
        assertEquals(LocalTime.of(9, 0), slots.first().toLocalTime())
    }

    @Test
    fun `quiet hours push slot forward without exceeding window`() {
        val now = LocalDateTime.of(2024, 6, 1, 8, 0)

        val slots = VerseScheduler.computeSlots(
            frequencyPerDay = 3,
            windowStart = LocalTime.of(9, 0),
            windowEnd = LocalTime.of(18, 0),
            minGapMinutes = 120,
            quietHours = QuietHours(
                start = LocalTime.of(12, 0),
                end = LocalTime.of(13, 0)
            ),
            now = now
        )

        assertEquals(listOf(9, 13, 16), slots.map { it.hour })
        assertTrue(slots.none { it.toLocalTime().isAfter(LocalTime.of(18, 0)) })
    }

    @Test
    fun `stops scheduling when quiet hours exceed window end`() {
        val now = LocalDateTime.of(2024, 6, 1, 8, 0)

        val slots = VerseScheduler.computeSlots(
            frequencyPerDay = 5,
            windowStart = LocalTime.of(9, 0),
            windowEnd = LocalTime.of(18, 0),
            minGapMinutes = 120,
            quietHours = QuietHours(
                start = LocalTime.of(15, 0),
                end = LocalTime.of(23, 0)
            ),
            now = now
        )

        assertEquals(3, slots.size)
        assertTrue(slots.all { it.toLocalTime().isBefore(LocalTime.of(15, 0)) })
    }
}
