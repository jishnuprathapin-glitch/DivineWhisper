package com.divinewhisper.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.divinewhisper.feature.notifications.VerseScheduler
import com.divinewhisper.ui.theme.DivineWhisperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DivineWhisperTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HomeScreen(onScheduleNow = { VerseScheduler.seedDailySchedule(this) })
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onScheduleNow: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Divine Whisper", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Offline verse notifications", style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onScheduleNow) {
            Text(text = "Schedule today's verses")
        }
    }
}
