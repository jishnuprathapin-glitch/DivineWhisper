package com.divinewhisper.feature.notifications

import com.divinewhisper.core.database.ShownLogDao
import com.divinewhisper.core.database.VerseDao
import com.divinewhisper.core.model.Source
import com.divinewhisper.core.model.Verse
import kotlin.random.Random

class VerseSelector(
    private val verseDao: VerseDao,
    private val shownLogDao: ShownLogDao
) {
    suspend fun pickVerse(sources: Set<Source>, intentTag: String?): Verse? {
        if (sources.isEmpty()) return null
        val excludedIds = shownLogDao.recentVerseIds(limit = 50)
        val lengthBuckets = listOf("short", "medium")
        val candidates = verseDao.getEligibleVerses(
            sources = sources.map { it.name },
            excludedIds = excludedIds,
            lengthBuckets = lengthBuckets,
            limit = 25
        )
        val filtered = intentTag?.let { tag ->
            candidates.filter { entity ->
                entity.tags.split(',').any { it.equals(tag, ignoreCase = true) }
            }
        } ?: candidates
        val chosen = (filtered.ifEmpty { candidates }).randomOrNull(Random.Default)
        return chosen?.let { entity ->
            Verse(
                id = entity.id,
                source = entity.source,
                book = entity.book,
                chapter = entity.chapter,
                verseNumber = entity.verseNumber,
                text = entity.text,
                tags = entity.tags.split(',').filter { it.isNotBlank() },
                popularityScore = entity.popularityScore,
                lengthBucket = entity.lengthBucket
            )
        }
    }
}
