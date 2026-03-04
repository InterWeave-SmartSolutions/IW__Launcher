# AI Documentation Map

This folder contains AI-specific operating instructions, logs, and session context.

Use this file as the landing page for AI work in this repository.

## Read Order

1. `CLAUDE.md`
2. Root `README.md`
3. `docs/README.md`
4. `docs/ai/README.md`
5. `docs/ai/AI_WORKFLOW.md`

If the task depends on legacy InterWeave behavior, terminology, workflows, or historical vendor integrations, also read `docs/ai/INTERWEAVE_PDF_CONTEXT.md`.

## File Purposes

- `AI_WORKFLOW.md`
  Mandatory operating rules for every AI session in this repo.
- `AI_WORKLOG.md`
  Append-only record of AI-assisted work. Update it after changes.
- `INTERWEAVE_PDF_CONTEXT.md`
  Approved legacy PDF corpus and rules for using it as additive historical context.
- `SESSION_LOG_YYYY-MM-DD.md`
  Dated session transcripts or handoff notes. These are supporting records, not the primary workflow spec.
- `*_NOTES.md`
  Task-specific or investigation-specific notes captured for future reference.

## How To Use This Folder

- Start with the workflow, not the logs.
- Use the worklog to understand recent changes and avoid duplicating effort.
- Use dated session logs only when they are directly relevant to the current task.
- Treat `INTERWEAVE_PDF_CONTEXT.md` as a supplement to current docs and current code, not a replacement.

## Organization Rules

- Add new AI process rules to `AI_WORKFLOW.md` only when they should apply broadly across sessions.
- Add new session history to `AI_WORKLOG.md` as append-only entries.
- Put large task transcripts or investigations in separate dated files instead of bloating `AI_WORKFLOW.md`.
- Prefer concise summaries with links back to canonical docs rather than repeating the same instructions.

## Fast Navigation

- Need the mandatory rules: open `AI_WORKFLOW.md`.
- Need to understand what changed recently: open `AI_WORKLOG.md`.
- Need historical InterWeave context from the legacy manuals: open `INTERWEAVE_PDF_CONTEXT.md`.
- Need prior task details: open the relevant dated session log or notes file.
