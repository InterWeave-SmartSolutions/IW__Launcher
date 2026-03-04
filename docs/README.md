# Documentation Map

This directory is the main navigation layer for repository documentation.

Use this file as the entry point before drilling into individual folders. It is designed to keep documentation easier to browse for both human users and AI agents without moving or rewriting legacy material.

## Start Here By Audience

- Runtime users:
  Start with the root `README.md`, then use `docs/setup/` and `docs/SYSTEM_READY.md`.
- Developers:
  Start with `docs/development/DEVELOPER_ONBOARDING.md`, then `docs/development/BUILD.md`, `docs/development/API.md`, `docs/development/LOCAL_SERVLETS.md`, and `docs/development/ENGINE_SYNC_MAP.md`.
- AI agents:
  Start with `docs/ai/README.md`, then follow `docs/ai/AI_WORKFLOW.md`.
- Reviewers and maintainers:
  Use `docs/adr/` for design decisions and `docs/testing/` for validation references.

## Folder Map

- `docs/README.md`
  Top-level directory map and document placement rules.
- `docs/ai/`
  AI workflow, worklog, session notes, and historical PDF context.
- `docs/adr/`
  Architectural decision records. Use these when a repo-wide rule affects implementation.
- `docs/assa-specs/`
  ASSA-related specifications and imported business documents.
- `docs/authentication/`
  Authentication-specific notes and supporting references.
- `docs/development/`
  Developer onboarding, build instructions, API notes, contribution guidance, and implementation references.
- `docs/errors/`
  Error code and troubleshooting references.
- `docs/legacy-pdfs/`
  Canonical historical PDF manuals and imported vendor references.
- `docs/security/`
  Security policy, operational security, and credential management guidance.
- `docs/setup/`
  Installation and environment setup instructions.
- `docs/testing/`
  Test plans, regression references, and validation checklists.
- `docs/tutorials/`
  Tutorial-style learning material and training walkthroughs.

## What Belongs In The Docs Root

Keep the root of `docs/` shallow and deliberate.

Allowed in `docs/` root:

- Navigation files such as this index.
- Repo-wide status files such as `SYSTEM_READY.md`.
- Imported top-level business artifacts that have not been normalized yet.

Prefer not to add new topic-specific files directly in `docs/` root. Place them in the nearest topical folder instead.

## Placement Rules For New Docs

- Put AI-only process documents in `docs/ai/`.
- Put engineering implementation and onboarding material in `docs/development/`.
- Put end-user setup instructions in `docs/setup/`.
- Put tests, validation notes, and QA artifacts in `docs/testing/`.
- Put security-sensitive process docs in `docs/security/`.
- Put historical imported PDFs in `docs/legacy-pdfs/` and treat them as reference material, not current source of truth.

If a document serves multiple audiences, place it in the most operationally relevant folder and link to it from indexes instead of duplicating content.

## Naming And Readability Rules

- Prefer Markdown (`.md`) for all new documentation unless a binary format is required by the user.
- Use clear, descriptive filenames. Prefer `TOPIC_NAME.md` or `Descriptive-Title.md` over vague names.
- Use a `README.md` file as the landing page for any documentation area that grows beyond a few files.
- Keep logs append-only when the file is explicitly a log (`AI_WORKLOG.md`, dated session logs, audit records).
- Mark historical, legacy, or imported material clearly so readers know it may not match current implementation.
- Link to canonical docs instead of copying the same instructions into multiple places.

## Legacy And Imported Files

The `docs/` tree contains a mix of active Markdown docs, imported PDFs, and a few imported office documents.

- Treat Markdown files as the preferred editable documentation format.
- Treat PDFs and office documents as historical or third-party artifacts unless a Markdown file explicitly promotes them to current process guidance.
- When converting legacy guidance into current docs, preserve the original file and add the new Markdown doc alongside it.

## Recommended Navigation Order

For a new person or agent entering the repo:

1. Read the root `README.md`.
2. Read this file (`docs/README.md`).
3. Go to the audience-specific folder:
   `docs/ai/`, `docs/development/`, `docs/setup/`, or `docs/tutorials/`.
4. Use topic-specific documents only after the relevant index or landing page.
