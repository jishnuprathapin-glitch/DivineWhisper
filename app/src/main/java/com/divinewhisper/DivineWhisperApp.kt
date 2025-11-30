package com.divinewhisper

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager

/**
 * Application entry point used for initializing lazy singletons and
 * preparing WorkManager for background scheduling. The current setup
 * keeps configuration minimal while providing a hook for future
 * diagnostics or custom workers.
 */
class DivineWhisperApp : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        // Placeholders for future setup (e.g., Hilt, database install)
        // The WorkManager initialization is delegated to Configuration.Provider.
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}
