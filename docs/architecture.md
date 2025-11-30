# Architecture & Experience Blueprint

This document describes how to build **Divine Whisper**, an offline Android app that sends inspirational verses from the Bible, Qur'an, and Bhagavad Gita. It emphasizes accuracy, user agency, and attention-friendly delivery.

## Platform & stack
- **Language**: Kotlin
- **UI**: Jetpack Compose (or XML if preferred)
- **Persistence**: Room over a prepackaged SQLite database asset containing vetted verse texts and metadata.
- **Scheduling**: WorkManager for periodic work; exact alarms (AlarmManager) only if truly needed for precise delivery windows.
- **Min API**: 24+ recommended to leverage modern scheduling and notification APIs.
- **DI**: Hilt for wiring repositories, DAOs, workers, and UI use-cases with scoping that respects background work lifecycles.

### Suggested module/layer split
- **core/model**: enums (`Source`), data classes (preferences, verse summaries), and date/time helpers.
- **core/database**: Room setup, DAOs, and the packaged asset installer/validator.
- **feature/notifications**: workers, notification builder, quiet-hours checks, and logging.
- **feature/settings**: UI screens for cadence, sources, and quiet hours plus `UserPrefs` use-cases.
- **feature/onboarding**: the 4-step flow with persistent state that can be resumed if backgrounded.
- **design-system**: typography, color tokens, and reusable components (verse card, slider rows, time pickers).
- **app**: entry-point app class to install the bundled database on first launch, register notification channels, and seed default preferences.

### App boot process (offline-first)
1. `DivineWhisperApp` runs `DatabaseInstaller` to copy the packaged asset to app storage if not present and performs a quick hash check.
2. Register a `NotificationChannel` for verse delivery and a quieter channel for informational nudges.
3. Load `UserPrefs` from Room or DataStore; if missing, hydrate with conservative defaults (2/day, 09:00–20:00, 3h gap, no sources enabled).
4. Kick off a `WorkManager` enqueue to seed the day's deliveries based on the latest preferences.

### Navigation & UI composition
- Use a single-activity, multi-screen Compose setup with `NavHost` destinations for Home, Settings, Verse Detail, and Onboarding.
- Keep UI state in `ViewModel`s backed by flows from repositories; expose `UiState` sealed classes for loading, content, and error states.
- Provide a compact Home screen card showing the next scheduled delivery window, last verse received, and a quick “Send one now” CTA for testing.
- Surface accessibility affordances (content descriptions, large tap targets) and respect dynamic type scaling in text components.

## Data model (Room entities)
- `VerseEntity`: `id`, `source` (`BIBLE`, `QURAN`, `GITA`), `book`, `chapter`, `verse_number`, `text`, `tags`, `popularity_score`, `length_bucket`.
- `UserPrefsEntity`: `id=0`, `sources_enabled` (bitmask or JSON), `frequency_per_day`, `window_start_minutes`, `window_end_minutes`, `min_gap_minutes`, `quiet_hours` (optional), `last_onboarding_version`.
- `ShownLogEntity`: `id`, `timestamp`, `verse_id`, `source` (to avoid immediate repeats and for local analytics).

### DAO sketch
- `VerseDao.getEligibleVerses(sources, excludedIds, maxLengthBucket, tags)`
- `ShownLogDao.insertLog(log)`
- `UserPrefsDao.getPrefs()` / `updatePrefs()`

## Notification scheduling
1. On app start or settings change, compute candidate times for the day within the user's delivery window respecting `min_gap_minutes`.
2. Enqueue one `OneTimeWorkRequest` per slot via WorkManager with a unique name to avoid duplicates.
3. Each worker:
   - Reads current preferences and recent history (e.g., last 30 verse IDs).
   - Selects a verse: filter by enabled sources, exclude recent IDs, prefer shorter-to-medium length, sample with a light weight on `popularity_score`, then randomize.
   - Logs the selection, posts the notification, and schedules a replacement if constraints changed.
   - If the window has elapsed (device was off), post a single make-up notification only when the user opted into "catch-ups"; otherwise drop quietly.
4. On device reboot, listen for `BOOT_COMPLETED` and re-seed the day's work.
5. Respect **Do Not Disturb** and battery optimizations; surface a non-blocking prompt if delivery is restricted and back off automatically.

```kotlin
fun pickVerse(
    sources: Set<Source>,
    log: ShownLogDao,
    verseDao: VerseDao,
    prefs: UserPrefs
): VerseEntity? {
    val recentIds = log.lastShownIds(limit = 30)
    val candidates = verseDao.getEligibleVerses(
        sources = sources,
        excludedIds = recentIds,
        maxLengthBucket = prefs.maxLengthBucket,
        tags = prefs.intentTag
    )
    return candidates.weightedSample { it.popularityScore }
}
```

Use `Constraints` in `WorkRequest` to honor battery saver and network conditions (even though content is offline) so the scheduler behaves politely alongside other apps.

### Defaults (psychology-informed)
- Frequency: **2–3 per day** (balanced). Start at 2 if onboarding skipped.
- Delivery window: **09:00–20:00** local time.
- Minimum gap: **≥180 minutes** to prevent clustering.
- Cool-down: do not repeat a verse within **14 days**; expand window if inventory is low.

## Onboarding (skippable, ≤4 steps)
1. **Sources**: Bible / Qur'an / Bhagavad Gita (multi-select). Default: none until chosen.
2. **Delivery window**: Morning / Afternoon / Evening / Custom (start/end time pickers).
3. **Frequency**: Light (1–2/day), Balanced (3/day), Frequent (5/day). Map to `frequency_per_day` and `min_gap_minutes` presets.
4. **Intent tag (optional)**: e.g., Hope, Gratitude, Perseverance to bias verse selection.
- A **Skip** button is visible from the first screen; skipping applies the conservative defaults listed above.

## UX principles
- Keep notifications concise: title = source, body = verse text, action = “Open” and “Reduce frequency”.
- Provide a **Snooze/remind tomorrow** action inside the app to respect user autonomy.
- Include dark mode and gentle color palette; avoid heavy iconography to keep focus on text.
- Avoid streaks or pressure mechanics; instead, occasional gratitude prompts inside the app (never on every notification).
- Keep accessibility in mind: minimum 14sp body text, semantic titles, and high-contrast color pairs for notification accents and in-app surfaces.
- Add a lightweight “Why this verse?” footer in the detail view explaining source and tags to reinforce trust without breaking flow.

## Settings & controls
- Per-source toggles with a summary of current cadence (e.g., "3/day between 9a–8p, min gap 3h").
- Frequency slider or presets; min gap slider; delivery window pickers.
- Quiet hours and Do Not Disturb compliance; fall back gracefully if exact alarms are denied.

## Local analytics (on-device only)
- Track opens vs. dismisses to nudge users with respectful prompts (e.g., "Want fewer notifications?").
- No network calls; all data remains local.

## Extensibility
- Add new sources by extending the `source` enum and providing a new table in the prepackaged DB.
- Add optional tag-based recommendation (e.g., "morning encouragement" vs. "evening reflection") using weighted sampling over `tags`.
- Expose a clean API around verse retrieval so that future features (e.g., bookmarks, search) can reuse filtering and logging logic.

## Build-time safeguards (high level)
- Include checksums for each verse row to detect corruption.
- Validate verse counts per source (≥20k each) against canonical manifests before shipping.
- Run automated smoke tests to pick a random verse from each source and verify hash matches the manifest.
