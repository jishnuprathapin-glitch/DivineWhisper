# Onboarding questionnaire (skippable, max 4 questions)

This flow gathers only the essentials needed to schedule respectful, personalized notifications. A **Skip** button is always available and applies conservative defaults.

## Flow
1. **Choose sources** (multi-select) — "Bible", "Qur'an", "Bhagavad Gita".
   - Copy: "Which sources would you like to receive inspiration from?"
2. **Delivery window** — presets (Morning 7–11, Afternoon 12–16, Evening 17–21, Custom start/end time).
   - Copy: "When should we send them?"
3. **Frequency** — Light (1–2/day), Balanced (3/day), Frequent (5/day). Show the implied min gap (e.g., 3h for Balanced).
   - Copy: "How often feels right for you?"
4. **Intent tag (optional)** — Hope, Gratitude, Perseverance, Reflection.
   - Copy: "Pick a theme to guide what you see" (optional).

If skipped: enable no sources until the user opts in; set delivery window 09:00–20:00, frequency 2/day, min gap 3h.

## UX notes
- Show progress (e.g., 1/4) to reduce uncertainty.
- Keep controls simple (toggles, radio buttons, time pickers); avoid text entry.
- Provide short helper text explaining that all content lives on-device and can be changed anytime in Settings.
- After completion, summarize choices and offer quick actions: "Edit sources", "Adjust times", "Reduce frequency".
- If notifications are disabled system-wide, surface a small inline prompt that links to settings rather than blocking progress.
- Use friendly defaults in widgets (e.g., pre-select Balanced cadence but leave sources unchecked) so skipping never enrolls the user without consent.
- Keep copy short, empathetic, and non-judgmental; acknowledge that users can change their mind anytime.
