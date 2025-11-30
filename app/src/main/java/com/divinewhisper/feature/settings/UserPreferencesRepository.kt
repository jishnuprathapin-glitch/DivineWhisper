package com.divinewhisper.feature.settings

import com.divinewhisper.core.database.UserPrefsDao
import com.divinewhisper.core.database.UserPrefsEntity
import com.divinewhisper.core.model.QuietHours
import com.divinewhisper.core.model.Source
import com.divinewhisper.core.model.UserPreferences
import java.time.LocalTime

class UserPreferencesRepository(private val prefsDao: UserPrefsDao) {
    suspend fun get(): UserPreferences {
        val entity = prefsDao.getPrefs()
        return entity?.toDomain() ?: defaultPrefs()
    }

    suspend fun update(prefs: UserPreferences) {
        prefsDao.updatePrefs(prefs.toEntity())
    }

    private fun defaultPrefs(): UserPreferences {
        return UserPreferences(
            sourcesEnabled = Source.values().toSet()
        )
    }
}

private fun UserPrefsEntity.toDomain(): UserPreferences {
    val enabledSources = sourcesEnabled.split(',').filter { it.isNotBlank() }.mapNotNull {
        runCatching { Source.valueOf(it) }.getOrNull()
    }.toSet()
    val quiet = if (quietHoursStart != null && quietHoursEnd != null) {
        QuietHours(minutesToLocalTime(quietHoursStart), minutesToLocalTime(quietHoursEnd))
    } else {
        null
    }
    return UserPreferences(
        sourcesEnabled = enabledSources,
        frequencyPerDay = frequencyPerDay,
        windowStart = minutesToLocalTime(windowStartMinutes),
        windowEnd = minutesToLocalTime(windowEndMinutes),
        minGapMinutes = minGapMinutes,
        quietHours = quiet,
        lastOnboardingVersion = lastOnboardingVersion,
        intentTag = intentTag
    )
}

private fun UserPreferences.toEntity(): UserPrefsEntity {
    val quietStart = quietHours?.start?.let { it.hour * 60 + it.minute }
    val quietEnd = quietHours?.end?.let { it.hour * 60 + it.minute }
    return UserPrefsEntity(
        sourcesEnabled = sourcesEnabled.joinToString(",") { it.name },
        frequencyPerDay = frequencyPerDay,
        windowStartMinutes = windowStart.hour * 60 + windowStart.minute,
        windowEndMinutes = windowEnd.hour * 60 + windowEnd.minute,
        minGapMinutes = minGapMinutes,
        quietHoursStart = quietStart,
        quietHoursEnd = quietEnd,
        lastOnboardingVersion = lastOnboardingVersion,
        intentTag = intentTag
    )
}

private fun minutesToLocalTime(minutes: Int): LocalTime {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return LocalTime.of(hours, remainingMinutes)
}
