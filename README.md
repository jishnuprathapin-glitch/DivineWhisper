# Divine Whisper — Offline Inspirational Verse Notifier

This repository contains a design blueprint for an offline-only Android application that surfaces inspirational verses from three sacred texts (Bible, Qur'an, Bhagavad Gita). The goal is to keep all data on-device, let users subscribe to individual or multiple sources, and deliver well-paced notifications that respect user preferences and attention.

## Objectives
- **Offline-first**: bundle locally verified English translations so notifications never require connectivity.
- **User control**: allow per-source subscriptions, frequency, time windows, and minimum gaps between notifications.
- **Safety & accuracy**: rely on licensed/public-domain translations, checksum validation, and deterministic build steps to avoid transcription errors.
- **Psychology-informed defaults**: gentle cadence (2–3/day), broad waking window (9:00–20:00), and spaced delivery to reduce fatigue.
- **Skippable onboarding**: at most four questions with an immediate "Skip" option to apply safe defaults.

## Repository layout
- `docs/architecture.md` — app architecture, data model, notification engine, and UX guidance.
- `docs/data-validation.md` — sourcing, licensing, and build-time validation steps for verse datasets.
- `docs/onboarding-questionnaire.md` — concise onboarding flow with copy and defaults.

If you want to start coding, use the architecture document as a guide for setting up the Android project (Jetpack Compose + Room + WorkManager) while following the data validation practices in the `docs` folder.
