# Data sourcing & validation for offline verse delivery

The app should ship with **locally stored English translations** of at least **20k verses per source** (Bible, Qur'an, Bhagavad Gita). Accuracy is critical; this guide outlines sourcing, licensing, preprocessing, and integrity checks.

## Sourcing & licensing
- Choose **public-domain or properly licensed** translations. Verify terms explicitly before bundling.
  - *Bible*: World English Bible (public domain) is a good default; confirm verse segmentation matches your schema.
  - *Qur'an*: permissive/public-domain translations are rare; obtain a license when required and keep the license file in the repo.
  - *Bhagavad Gita*: select a public-domain translation or procure a license; ensure chapter/verse numbering matches the manifest.
- Preserve attribution and licensing documents alongside the raw texts used to build the database.

## Preprocessing pipeline (suggested)
1. **Normalize text**: NFC normalization, consistent quotation marks, remove trailing spaces.
2. **Canonical manifests**: For each source, store a manifest (`source`, `book`, `chapter`, `verse_number`, `hash(text)`).
3. **Tagging**: Add `tags` such as `hope`, `gratitude`, `perseverance`, `reflection`, `short`, `medium` to support flexible selection.
4. **Length bucket**: bucket verses as `short`/`medium`/`long` to bias notifications toward concise content.
5. **Popularity score**: optional heuristic (e.g., curated list) to weight sampling.
6. **Export to SQLite**: build a prepackaged Room database asset with three tables (one per source or a unified table with a `source` column).
7. **Diffable outputs**: emit stable, sorted CSV/JSON alongside the SQLite asset so reviewers can diff content changes in PRs.
8. **Reproducible scripts**: pin Python package versions (requirements.txt) or Gradle tool versions; run scripts inside a container to avoid locale drift.

## Integrity checks (automated)
- **Row counts**: assert ≥20,000 verses per source before publishing.
- **Hash verification**: recompute hashes of every verse and compare to the manifest; fail the build on mismatch.
- **Schema validation**: run `PRAGMA integrity_check` on the packaged SQLite file.
- **Sampling test**: pick random verses per source; ensure hashes and metadata match manifests.
- **Deterministic build**: make preprocessing scripts idempotent and commit lockfiles/versions of tooling.
- **Duplicate detection**: assert that (`source`, `book`, `chapter`, `verse_number`) is unique and that verse text is not duplicated across nearby references unless the canonical text requires it.
- **Audit trail**: retain a build log artifact that records manifest versions, script versions, and hash summaries per source.

## Delivery integrity at runtime
- Store a small checksum of the packaged database and validate it on first launch; if invalid, pause scheduling and prompt the user to reinstall.
- Keep all verse processing on-device; do not fetch or mutate verse text at runtime to avoid drift.

## File layout suggestion
```
raw/
  bible/...
  quran/...
  gita/...
manifests/
  bible_manifest.csv
  quran_manifest.csv
  gita_manifest.csv
scripts/
  preprocess.py (or Kotlin/JVM)  # produces packaged_db.db and manifest hashes
  diff_manifest.py  # emits per-source diffs and verifies duplicate/row-count rules
assets/
  packaged_db.db
LICENSES/
  bible_license.txt
  quran_license.txt
  gita_license.txt
```
