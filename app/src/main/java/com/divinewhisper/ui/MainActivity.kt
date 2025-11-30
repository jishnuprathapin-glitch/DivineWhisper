package com.divinewhisper.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.divinewhisper.ui.theme.DivineWhisperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DivineWhisperTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    IntroScreen()
                }
            }
        }
    }
}

@Composable
private fun IntroScreen() {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colors.primary.copy(alpha = 0.22f),
                        colors.background,
                        colors.secondary.copy(alpha = 0.18f)
                    )
                )
            )
            .padding(24.dp)
    ) {
        GlowLayer(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 64.dp, y = (-48).dp),
            colors = listOf(colors.secondary.copy(alpha = 0.25f), Color.Transparent)
        )
        GlowLayer(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-72).dp, y = 80.dp),
            colors = listOf(colors.primary.copy(alpha = 0.22f), Color.Transparent)
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 18.dp, shape = RoundedCornerShape(26.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface.copy(alpha = 0.94f)
                ),
                shape = RoundedCornerShape(26.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BadgeLabel(text = "Calm & clarity")
                        Text(
                            text = "Offline first",
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Divine Whisper",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Your daily sanctuary for verses, reflection prompts, and gentle reminders that honor your pace.",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = colors.onSurface.copy(alpha = 0.8f)
                        )
                    }

                    VerseCard()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HighlightPill(text = "Curated verses")
                        HighlightPill(text = "Smart reminders")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HighlightPill(text = "Offline ready")
                        HighlightPill(text = "Personal pace")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { /* TODO: hook into onboarding */ },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.primary,
                                contentColor = colors.onPrimary
                            )
                        ) {
                            Text(text = "Begin your journey")
                        }

                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            tonalElevation = 2.dp,
                            shadowElevation = 0.dp,
                            color = colors.secondary.copy(alpha = 0.18f),
                            contentColor = colors.onSecondary
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Preview today's verse",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = colors.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HighlightPill(text: String) {
    AssistChip(
        onClick = { /* Decorative */ },
        label = { Text(text = text, style = MaterialTheme.typography.labelLarge) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            labelColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(12.dp),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)
        )
    )
}

@Composable
private fun BadgeLabel(text: String) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = colors.primary.copy(alpha = 0.35f),
                shape = RoundedCornerShape(50)
            )
            .background(
                color = colors.primary.copy(alpha = 0.08f),
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(10.dp)
                .height(10.dp)
                .background(
                    brush = Brush.radialGradient(
                        listOf(colors.primary, colors.secondary.copy(alpha = 0.6f)),
                        radius = 20f
                    ),
                    shape = RoundedCornerShape(50)
                )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = colors.onSurface.copy(alpha = 0.82f)
        )
    }
}

@Composable
private fun VerseCard() {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 2.dp,
        shadowElevation = 0.dp,
        color = colors.surfaceVariant.copy(alpha = 0.35f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Featured verse",
                style = MaterialTheme.typography.labelLarge,
                color = colors.onSurface.copy(alpha = 0.65f)
            )
            Text(
                text = "\"Be still, and know that I am with you.\"",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = colors.onSurface,
                textAlign = TextAlign.Start
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Psalm 46:10",
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.primary.copy(alpha = 0.9f)
                )
                Text(
                    text = "Saved for reflection",
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.onSurface.copy(alpha = 0.65f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun GlowLayer(modifier: Modifier = Modifier, colors: List<Color>) {
    Box(
        modifier = modifier
            .width(260.dp)
            .height(260.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = colors,
                    radius = 380f
                )
            )
    )
}
