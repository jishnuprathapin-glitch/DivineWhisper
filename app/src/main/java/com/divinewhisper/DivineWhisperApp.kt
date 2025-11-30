package com.divinewhisper

import android.app.Application
import androidx.work.Configuration
import com.divinewhisper.feature.notifications.VerseScheduler

class DivineWhisperApp : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        VerseScheduler.initialize(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }
}
