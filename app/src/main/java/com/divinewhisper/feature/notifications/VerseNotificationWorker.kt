package com.divinewhisper.feature.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import com.divinewhisper.R
import com.divinewhisper.core.database.DivineWhisperDatabase
import com.divinewhisper.core.database.ShownLogEntity
import com.divinewhisper.feature.settings.UserPreferencesRepository
import java.time.Duration
import java.util.UUID

class VerseNotificationWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val database = DivineWhisperDatabase.build(appContext)
    private val prefsRepository = UserPreferencesRepository(database.userPrefsDao())
    private val selector = VerseSelector(database.verseDao(), database.shownLogDao())

    override suspend fun doWork(): Result {
        createChannel()
        val prefs = prefsRepository.get()
        val verse = selector.pickVerse(prefs.sourcesEnabled, prefs.intentTag) ?: return Result.success()

        NotificationManagerCompat.from(applicationContext).notify(
            verse.id.toInt(),
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(verse.source.name.lowercase().replaceFirstChar { it.titlecase() })
                .setContentText(verse.text.take(MAX_BODY_LENGTH))
                .setStyle(NotificationCompat.BigTextStyle().bigText(verse.text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        )

        database.shownLogDao().insertLog(
            ShownLogEntity(
                timestamp = System.currentTimeMillis(),
                verseId = verse.id,
                source = verse.source
            )
        )

        return Result.success()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.channel_name)
            val descriptionText = applicationContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "divine_whisper_channel"
        private const val MAX_BODY_LENGTH = 200

        fun scheduleOnce(context: Context, delayMinutes: Long = 0): UUID {
            val request: WorkRequest = OneTimeWorkRequestBuilder<VerseNotificationWorker>()
                .setInitialDelay(Duration.ofMinutes(delayMinutes))
                .addTag(WORK_TAG)
                .build()
            WorkManager.getInstance(context).enqueue(request)
            return request.id
        }
    }
}
