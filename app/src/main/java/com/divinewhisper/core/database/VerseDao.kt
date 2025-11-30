package com.divinewhisper.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.divinewhisper.core.model.Source

@Dao
interface VerseDao {
    @Query(
        """
        SELECT * FROM verses
        WHERE source IN (:sources)
        AND length_bucket IN (:lengthBuckets)
        AND id NOT IN (:excludedIds)
        ORDER BY popularity_score DESC, RANDOM()
        LIMIT :limit
        """
    )
    suspend fun getEligibleVerses(
        sources: List<Source>,
        excludedIds: List<Long>,
        lengthBuckets: List<String>,
        limit: Int
    ): List<VerseEntity>
}

@Dao
interface UserPrefsDao {
    @Query("SELECT * FROM user_prefs WHERE id = 0")
    suspend fun getPrefs(): UserPrefsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePrefs(prefs: UserPrefsEntity)
}

@Dao
interface ShownLogDao {
    @Insert
    suspend fun insertLog(log: ShownLogEntity)

    @Query("SELECT verse_id FROM shown_log ORDER BY timestamp DESC LIMIT :limit")
    suspend fun recentVerseIds(limit: Int): List<Long>
}
