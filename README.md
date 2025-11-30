# Divine Whisper — Offline Inspirational Verse Notifier

This repository contains a design blueprint for an offline-only Android application that surfaces inspirational verses from three sacred texts (Bible, Qur'an, Bhagavad Gita). The goal is to keep all data on-device, let users subscribe to individual or multiple sources, and deliver well-paced notifications that respect user preferences and attention.

## Objectives
- **Offline-first**: bundle locally verified English translations so notifications never require connectivity.
- **User control**: allow per-source subscriptions, frequency, time windows, and minimum gaps between notifications.
- **Safety & accuracy**: rely on licensed/public-domain translations, checksum validation, and deterministic build steps to avoid transcription errors.
- **Psychology-informed defaults**: gentle cadence (2–3/day), broad waking window (9:00–20:00), and spaced delivery to reduce fatigue.
- **Skippable onboarding**: at most four questions with an immediate "Skip" option to apply safe defaults.

## Quick start for implementers
- Read `docs/architecture.md` to align on the module breakdown (core, notifications, onboarding, design system) and WorkManager strategy.
- Follow `docs/data-validation.md` to package the offline verse database with reproducible scripts and integrity checks.
- Prototype the onboarding flow from `docs/onboarding-questionnaire.md`; keep the skip path visible and opt-out safe.
- Use the provided Android manifest and Gradle configuration as a starting scaffold; add Hilt and Room initialization in `DivineWhisperApp`.

## Delivery principles
- Ship with conservative defaults and surface simple controls to adjust cadence without guilt-driven copy.
- Validate assets on-device at first launch and back off gracefully if checks fail instead of showing partial data.
- Respect accessibility and DND/battery constraints while keeping the experience lightweight.

## Repository layout
- `docs/architecture.md` — app architecture, data model, notification engine, and UX guidance.
- `docs/data-validation.md` — sourcing, licensing, and build-time validation steps for verse datasets.
- `docs/onboarding-questionnaire.md` — concise onboarding flow with copy and defaults.

If you want to start coding, use the architecture document as a guide for setting up the Android project (Jetpack Compose + Room + WorkManager) while following the data validation practices in the `docs` folder.
