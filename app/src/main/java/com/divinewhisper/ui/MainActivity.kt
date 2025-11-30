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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.clip
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
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        colors.primary.copy(alpha = 0.2f),
                        colors.background,
                        colors.secondary.copy(alpha = 0.18f)
                    ),
                    start = Alignment.TopStart,
                    end = Alignment.BottomEnd
                )
            )
            .padding(horizontal = 20.dp, vertical = 26.dp)
    ) {
        AuroraLayer(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 60.dp, y = (-40).dp),
            colors = listOf(colors.secondary.copy(alpha = 0.32f), Color.Transparent)
        )
        AuroraLayer(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-70).dp, y = 30.dp),
            colors = listOf(colors.primary.copy(alpha = 0.35f), Color.Transparent)
        )
        AuroraLayer(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = 110.dp),
            colors = listOf(colors.secondary.copy(alpha = 0.2f), Color.Transparent)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeroHeader()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(vertical = 12.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(colors.primary.copy(alpha = 0.08f), Color.Transparent),
                                radius = 520f
                            ),
                            shape = RoundedCornerShape(34.dp)
                        )
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 24.dp, shape = RoundedCornerShape(30.dp))
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    colors.primary.copy(alpha = 0.18f),
                                    colors.secondary.copy(alpha = 0.18f)
                                )
                            ),
                            shape = RoundedCornerShape(30.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.surface.copy(alpha = 0.97f)
                    ),
                    shape = RoundedCornerShape(30.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 26.dp, vertical = 30.dp),
                        verticalArrangement = Arrangement.spacedBy(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HeaderRow()

                        SerenityHighlights()

                        CalmAmbienceCard()

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Divine Whisper",
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Your sanctuary for verses, guided reflection, and gentle reminders that honor your pace.",
                                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp),
                                textAlign = TextAlign.Center,
                                color = colors.onSurface.copy(alpha = 0.78f)
                            )
                        }

                        VerseCard()

                        ReflectionCard()

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
                                    containerColor = colors.secondary.copy(alpha = 0.12f),
                                    contentColor = colors.onSurface
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = Brush.linearGradient(
                                        listOf(colors.secondary.copy(alpha = 0.4f), colors.primary.copy(alpha = 0.34f))
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
}

@Composable
private fun HeroHeader() {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 24.dp, shape = RoundedCornerShape(26.dp)),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface.copy(alpha = 0.95f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            colors.primary.copy(alpha = 0.22f),
                            colors.secondary.copy(alpha = 0.18f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(colors.primary.copy(alpha = 0.24f), colors.secondary.copy(alpha = 0.18f))
                    ),
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(horizontal = 22.dp, vertical = 20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Divine Whisper",
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 30.sp),
                            fontWeight = FontWeight.ExtraBold,
                            color = colors.onSurface
                        )
                        Text(
                            text = "Gentle verses, graceful pacing, beautifully on-device.",
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                            color = colors.onSurface.copy(alpha = 0.8f)
                        )
                    }
                    HighlightPill(text = "Fresh for today")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GradientChip(text = "Offline & private", colors = listOf(colors.primary, colors.secondary))
                    GradientChip(text = "Lightweight prompts", colors = listOf(colors.secondary, colors.primary))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Today's intention",
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.onSurface.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "Move through the day with a slow, steady rhythm.",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = colors.onSurface
                        )
                    }
                    Surface(
                        color = colors.primary.copy(alpha = 0.14f),
                        shape = RoundedCornerShape(50),
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            text = "Calm mode active",
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SerenityHighlights() {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(colors.primary.copy(alpha = 0.12f), colors.secondary.copy(alpha = 0.12f))
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    listOf(colors.primary.copy(alpha = 0.06f), colors.secondary.copy(alpha = 0.04f))
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BadgeLabel(text = "Daily calm")
        GradientChip(text = "Guided focus", colors = listOf(colors.primary, colors.secondary))
        GradientChip(text = "Soft reminders", colors = listOf(colors.secondary, colors.primary))
    }
}

@Composable
private fun CalmAmbienceCard() {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.surface.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Calm ambience",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = colors.onSurface
                    )
                    Text(
                        text = "Gentle tone, softer alerts, serene screens",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.onSurface.copy(alpha = 0.7f)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(50),
                    color = colors.primary.copy(alpha = 0.12f),
                    shadowElevation = 0.dp
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        text = "Live",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.primary
                    )
                }
            }

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50)),
                progress = 0.7f,
                color = colors.primary,
                trackColor = colors.onSurface.copy(alpha = 0.08f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GradientChip(text = "Focus bubble", colors = listOf(colors.primary, colors.secondary))
                HighlightPill(text = "Do not disturb light")
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = colors.secondary.copy(alpha = 0.12f),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    text = "Soft background chimes will wrap reminders in a calm fade.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurface.copy(alpha = 0.82f)
                )
            }
        }
    }
}

@Composable
private fun HeaderRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            BadgeLabel(text = "Calm & clarity")
            HighlightPill(text = "Offline first")
        }

        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            shape = RoundedCornerShape(50),
            tonalElevation = 0.dp
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                text = "Mindful companion",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
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
private fun GradientChip(text: String, colors: List<Color>) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(colors),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
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
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            colors.primary.copy(alpha = 0.16f),
                            colors.secondary.copy(alpha = 0.14f)
                        )
                    ),
                    shape = RoundedCornerShape(22.dp)
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            colors.primary.copy(alpha = 0.25f),
                            colors.onSurface.copy(alpha = 0.06f)
                        )
                    ),
                    shape = RoundedCornerShape(22.dp)
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .width(8.dp)
                                .height(32.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(colors.onPrimary.copy(alpha = 0.8f), Color.Transparent)
                                    ),
                                    shape = RoundedCornerShape(50)
                                )
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "Featured verse",
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.onPrimary
                            )
                            Text(
                                text = "Quiet focus",
                                style = MaterialTheme.typography.labelMedium,
                                color = colors.onPrimary.copy(alpha = 0.78f)
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = colors.onPrimary.copy(alpha = 0.14f),
                        shadowElevation = 0.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            text = "Listen",
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
                    color = colors.onPrimary,
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
                        color = colors.onPrimary
                    )
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = colors.onPrimary.copy(alpha = 0.12f),
                        shadowElevation = 0.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            text = "Saved for reflection",
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.onPrimary.copy(alpha = 0.9f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReflectionCard() {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.secondary.copy(alpha = 0.08f)),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(colors.primary.copy(alpha = 0.6f), colors.secondary.copy(alpha = 0.35f))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reflection prompt",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = colors.onSurface
                )
                Text(
                    text = "2 min",
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.onSurface.copy(alpha = 0.65f)
                )
            }
            Text(
                text = "What moment felt like a quiet gift today? Breathe it in and keep it close.",
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                color = colors.onSurface.copy(alpha = 0.86f)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Transparent,
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(listOf(colors.primary.copy(alpha = 0.4f), colors.secondary.copy(alpha = 0.35f)))
                    ),
                    shadowElevation = 0.dp
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(colors.primary.copy(alpha = 0.16f), colors.secondary.copy(alpha = 0.12f))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
                            text = "Open journal",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = colors.primary.copy(alpha = 0.96f)
                        )
                    }
                }
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Transparent,
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(listOf(colors.secondary.copy(alpha = 0.5f), colors.primary.copy(alpha = 0.25f)))
                    ),
                    shadowElevation = 0.dp
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(colors.secondary.copy(alpha = 0.22f), colors.primary.copy(alpha = 0.12f))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
                            text = "Schedule reminder",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = colors.onSurface.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AuroraLayer(modifier: Modifier = Modifier, colors: List<Color>) {
    Box(
        modifier = modifier
            .width(260.dp)
            .height(260.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = colors,
                    radius = 420f
                )
            )
    )
}
