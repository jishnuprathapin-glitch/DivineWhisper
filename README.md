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

## Step-by-step build and deployment (easy mode)

The following steps are written for someone who has never built an Android app before.

### What you need first
1) A computer with at least 8 GB of free disk space.
2) [Android Studio](https://developer.android.com/studio) installed (includes Java and the Android SDK).
3) A phone or tablet running Android 8.0 (Oreo) or later, plus a USB cable.

### Download the project
1) Open Android Studio.
2) On the welcome screen, click **Get from VCS**.
3) Paste the repository address (for example, `https://github.com/<your-org>/DivineWhisper.git`) and click **Clone**.
4) Wait for Android Studio to finish syncing; it may download Gradle components the first time.

### Build the app inside Android Studio (no command line required)
1) In the top menu, select **Build > Make Project**.
2) Watch the status bar; when it says **Build Successful**, the app is ready.
3) Android Studio will produce a debug APK at `app/build/outputs/apk/debug/app-debug.apk`.

### Install the app on a device with Android Studio
1) On your Android device, enable **Developer options** and turn on **USB debugging** (Settings → About phone → tap **Build number** 7 times → go back, open **Developer options**, toggle **USB debugging**).
2) Connect the device to your computer with a USB cable and accept the trust prompt on the device.
3) In Android Studio, click the device selector in the toolbar, choose your device, then click **Run** (green ► icon).
4) Android Studio will build and install the app automatically. Look for "Install successfully finished" in the status bar.

### (Optional) Build and install from the command line
If you prefer a single copy-paste approach:
1) Open a terminal inside the project folder.
2) Run `./gradlew assembleDebug` (macOS/Linux) or `gradlew.bat assembleDebug` (Windows). The first run downloads Gradle, so it may take a few minutes.
3) After it finishes, the APK will be at `app/build/outputs/apk/debug/app-debug.apk`.
4) To install over USB, enable **USB debugging** on the device, connect it, and run `adb install -r app/build/outputs/apk/debug/app-debug.apk`.

### Tips for a smooth install
- If Windows asks about drivers, install the OEM USB driver for your phone brand.
- If `adb` is not found on the command line, open **Android Studio > More Actions > SDK Manager > SDK Tools** and check **Android SDK Platform-Tools**, then re-open the terminal from Android Studio (**View > Tool Windows > Terminal**).
- If installation fails, ensure the device is unlocked and has at least 100 MB of free space.

## Delivery principles
- Ship with conservative defaults and surface simple controls to adjust cadence without guilt-driven copy.
- Validate assets on-device at first launch and back off gracefully if checks fail instead of showing partial data.
- Respect accessibility and DND/battery constraints while keeping the experience lightweight.

## Repository layout
- `docs/architecture.md` — app architecture, data model, notification engine, and UX guidance.
- `docs/data-validation.md` — sourcing, licensing, and build-time validation steps for verse datasets.
- `docs/onboarding-questionnaire.md` — concise onboarding flow with copy and defaults.

If you want to start coding, use the architecture document as a guide for setting up the Android project (Jetpack Compose + Room + WorkManager) while following the data validation practices in the `docs` folder.
