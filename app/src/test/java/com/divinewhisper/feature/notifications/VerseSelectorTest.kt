package com.divinewhisper.feature.notifications

import com.divinewhisper.core.database.ShownLogDao
import com.divinewhisper.core.database.VerseDao
import com.divinewhisper.core.database.VerseEntity
import com.divinewhisper.core.model.Source
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class VerseSelectorTest {

    @Test
    fun filtersByIntentTagWhenPresent() = runBlocking {
        val verseWithTag = VerseEntity(
            id = 1,
            source = Source.BIBLE,
            book = "Psalms",
            chapter = 23,
            verseNumber = 1,
            text = "The Lord is my shepherd; I shall not want.",
            tags = "comfort, hope",
            popularityScore = 0.9,
            lengthBucket = "short",
            checksum = "abc"
        )
        val verseWithoutTag = verseWithTag.copy(id = 2, tags = "strength")
        val selector = VerseSelector(
            verseDao = FakeVerseDao(listOf(verseWithTag, verseWithoutTag)),
            shownLogDao = FakeShownLogDao(emptyList())
        )

        val chosen = selector.pickVerse(setOf(Source.BIBLE), intentTag = "Hope")

        assertEquals(1, chosen?.id)
        assertTrue(chosen?.tags?.contains("comfort") == true)
    }

    @Test
    fun returnsNullWhenNoSourcesEnabled() = runBlocking {
        val selector = VerseSelector(
            verseDao = FakeVerseDao(emptyList()),
            shownLogDao = FakeShownLogDao(emptyList())
        )

        val chosen = selector.pickVerse(emptySet(), intentTag = null)

        assertNull(chosen)
    }
}

private class FakeVerseDao(private val verses: List<VerseEntity>) : VerseDao {
    override suspend fun getEligibleVerses(
        sources: List<Source>,
        excludedIds: List<Long>,
        lengthBuckets: List<String>,
        limit: Int
    ): List<VerseEntity> {
        return verses
            .filter { it.source in sources }
            .filter { it.lengthBucket in lengthBuckets }
            .filterNot { it.id in excludedIds }
            .take(limit)
    }
}

private class FakeShownLogDao(private val ids: List<Long>) : ShownLogDao {
    override suspend fun insertLog(log: com.divinewhisper.core.database.ShownLogEntity) {
        // no-op for tests
    }

    override suspend fun recentVerseIds(limit: Int): List<Long> {
        return ids.take(limit)
    }
}
