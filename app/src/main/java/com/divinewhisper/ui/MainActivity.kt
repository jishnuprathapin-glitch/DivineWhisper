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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
                .padding(horizontal = 20.dp, vertical = 26.dp)
    ) {
        GlowLayer(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 90.dp, y = (-52).dp),
            colors = listOf(colors.secondary.copy(alpha = 0.28f), Color.Transparent)
        )
        GlowLayer(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-88).dp, y = 90.dp),
            colors = listOf(colors.primary.copy(alpha = 0.28f), Color.Transparent)
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 22.dp, shape = RoundedCornerShape(28.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface.copy(alpha = 0.96f)
                ),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp, vertical = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BadgeLabel(text = "Calm & clarity")
                        HighlightPill(text = "Offline first")
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Divine Whisper",
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Your daily sanctuary for verses, reflection prompts, and gentle reminders that honor your pace.",
                            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp),
                            textAlign = TextAlign.Center,
                            color = colors.onSurface.copy(alpha = 0.78f)
                        )
                    }

                    VerseCard()

                    Divider(color = colors.onSurface.copy(alpha = 0.08f))

                    MetricsRow()

                    CalmRoutine()

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

                        OutlinedButton(
                            modifier = Modifier.weight(1f),
                            onClick = { /* TODO: hook into preview */ },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = colors.secondary.copy(alpha = 0.14f),
                                contentColor = colors.onSurface
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = Brush.linearGradient(
                                    listOf(colors.secondary.copy(alpha = 0.5f), colors.primary.copy(alpha = 0.3f))
                                )
                            )
                        ) {
                            Text(text = "Preview today's verse")
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
private fun MetricsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MetricCard(
            modifier = Modifier.weight(1f),
            label = "Days with calm",
            value = "14",
            hint = "Steady streak"
        )
        MetricCard(
            modifier = Modifier.weight(1f),
            label = "Reminders",
            value = "3x daily",
            hint = "Tailored to your rhythm"
        )
    }
}

@Composable
private fun MetricCard(modifier: Modifier = Modifier, label: String, value: String, hint: String) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        tonalElevation = 2.dp,
        shadowElevation = 0.dp,
        color = colors.secondary.copy(alpha = 0.16f)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = colors.onSurface.copy(alpha = 0.72f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = colors.onSurface
            )
            Text(
                text = hint,
                style = MaterialTheme.typography.labelMedium,
                color = colors.onSurface.copy(alpha = 0.68f)
            )
        }
    }
}

@Composable
private fun CalmRoutine() {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Today's gentle routine",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = colors.onSurface
            )
            HighlightPill(text = "Mindful pace")
        }

        RoutineItem(
            title = "Morning reflection",
            subtitle = "Ground yourself before the day begins",
            badge = "2 min",
            accent = colors.primary.copy(alpha = 0.65f)
        )
        RoutineItem(
            title = "Afternoon gratitude",
            subtitle = "A quiet pause to recenter",
            badge = "Gentle ping",
            accent = colors.secondary.copy(alpha = 0.7f)
        )
        RoutineItem(
            title = "Evening unwind",
            subtitle = "Slow down with a verse that soothes",
            badge = "Wind down",
            accent = colors.primary.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun RoutineItem(title: String, subtitle: String, badge: String, accent: Color) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(12.dp)
                .height(12.dp)
                .background(
                    brush = Brush.radialGradient(listOf(accent, Color.Transparent)),
                    shape = RoundedCornerShape(50)
                )
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = colors.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurface.copy(alpha = 0.72f)
            )
        }
        Surface(
            shape = RoundedCornerShape(50),
            color = accent.copy(alpha = 0.12f),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                text = badge,
                style = MaterialTheme.typography.labelLarge,
                color = colors.onSurface
            )
        }
    }
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
        shape = RoundedCornerShape(22.dp),
        tonalElevation = 3.dp,
        shadowElevation = 0.dp,
        color = colors.primary.copy(alpha = 0.08f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Featured verse",
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.onSurface.copy(alpha = 0.68f)
                )
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                listOf(colors.secondary.copy(alpha = 0.6f), colors.primary.copy(alpha = 0.45f))
                            ),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Quiet Focus",
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.onPrimary
                    )
                }
            }
            Text(
                text = "\"Be still, and know that I am with you.\"",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 19.sp,
                    lineHeight = 28.sp,
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
                    color = colors.onSurface.copy(alpha = 0.7f),
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
