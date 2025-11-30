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
        val excludedIds = shownLogDao.recentVerseIds(limit = 50).ifEmpty { listOf(-1L) }
        val lengthBuckets = listOf("short", "medium", "long")
        val candidates = verseDao.getEligibleVerses(
            sources = sources.toList(),
            excludedIds = excludedIds,
            lengthBuckets = lengthBuckets,
            limit = 25
        )
        val normalizedTag = intentTag?.trim()?.takeIf { it.isNotEmpty() }?.lowercase()
        val filtered = normalizedTag?.let { tag ->
            candidates.filter { entity ->
                parseTags(entity.tags).any { it.equals(tag, ignoreCase = true) }
            }
        } ?: candidates
        val chosen = (filtered.ifEmpty { candidates }).randomOrNull(Random.Default)
        return chosen?.let { entity ->
            val parsedTags = parseTags(entity.tags)
            Verse(
                id = entity.id,
                source = entity.source,
                book = entity.book,
                chapter = entity.chapter,
                verseNumber = entity.verseNumber,
                text = entity.text,
                tags = parsedTags,
                popularityScore = entity.popularityScore,
                lengthBucket = entity.lengthBucket
            )
        }
    }
}

private fun parseTags(raw: String): List<String> = raw.split(',')
    .map { it.trim() }
    .filter { it.isNotBlank() }
