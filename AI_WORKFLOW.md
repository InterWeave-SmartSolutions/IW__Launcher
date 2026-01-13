# AI_WORKFLOW.md (Mandatory)
This repository is frequently worked on by multiple AI tools/agents (terminal agents, web agents, IDE agents).

All AI agents that read or modify anything under `IW_Launcher/` MUST follow the workflow below for every session.

## 0) Non‑negotiables
- Do not commit or paste secrets. Never commit `.env`. Treat any database host/user/password as sensitive.
- Do not use/read/reference `InterWoven/` unless the user explicitly requests it (see `CLAUDE.md`).
- Do not delete or overwrite user data (e.g. `workspace/`, `data/`) unless explicitly requested.
- Do not commit to git unless the user explicitly asks.

## 1) Session start checklist (required)
1. Read `CLAUDE.md` and `README.md` in the repo root.
2. Run:
   - `git --no-pager status -sb`
   - `git --no-pager diff` (or `git --no-pager diff --stat`)
3. State (in the chat) what you believe the user is asking for and what you will change.
4. If you will make changes: create/update the session entry in `AI_WORKLOG.md` (see §5).

## 2) Context gathering rules
- Prefer existing project docs/scripts as the source of truth:
  - `README.md`, `BUILD.md`, `_internal/*.bat|*.sh`, `docs/**`, `web_portal/**`, `pom.xml`.
- If something in docs references a file/script, verify it exists and matches the name.
- If the task is non-trivial (multi-step, risk of breaking startup, or touches many files), propose a plan first.

## 3) Implementation rules (how to change things)
- Keep changes small and reviewable.
- Preserve current behavior unless the user requests a behavior change.
- When changing startup/build scripts:
  - Prefer robust discovery (e.g. locate `javac` via PATH/`JAVA_HOME`) over hardcoded machine paths.
  - Avoid assumptions about install locations.
- When changing documentation:
  - Prefer checklists and concrete “copy/paste” commands.
  - Explicitly distinguish “runtime users” vs “developers/building from source”.

## 4) Verification rules (required before saying “done”)
Pick the smallest reasonable verification for the change you made:
- Docs-only change: verify referenced files exist, and the instructions match the repo layout.
- Script change: at minimum run a syntax check / dry run where possible.
- Java/Maven change: run `mvn -q test` or `mvn -q -DskipTests package` (as appropriate).

If verification is not possible in the current environment (e.g. Windows-only behavior), explicitly say what you could not verify and what the user should run on Windows to confirm.

## 4.1 Response accountability (required)
Every AI response in this repo MUST end with a short section titled:
- `What I did (this response)`

It should be 3–10 bullets describing what was actually done (commands run, files read/edited/created, decisions made), without leaking secrets.

## 4.1 Response format (required)
Every AI response in this repo MUST include a short section at the end titled:
- `What I did (this response)`

It should be 3–10 bullets describing what was actually done (commands run, files read/edited/created, decisions made), without leaking secrets.

## 5) Mandatory work cataloging (`AI_WORKLOG.md`)
Every AI work session MUST append an entry to `AI_WORKLOG.md` after changes are made.

Minimum required fields:
- Date (YYYY-MM-DD) and time (with timezone if known)
- Agent/tool identity (e.g. Claude Code, Warp, Cursor, ChatGPT)
- User request (1–3 sentences)
- Actions taken (what was changed and why)
- Files changed/created (list)
- Commands run (high level; do not include secrets)
- Verification performed (tests run / manual checks)
- Follow-ups / known issues

Guidance:
- Keep entries concise.
- Never include secrets (DB passwords, tokens, private URLs).
- If multiple AIs worked in parallel on the same task, produce one consolidated entry.

## 6) Claude Code-specific enhancements (recommended)
This repo is designed to work well with Claude Code using:
- Project slash commands in `.claude/commands/`
- Hooks (PreToolUse/PostToolUse/Stop) to improve verification and logging
- Optional plugin(s) for automated verification loops

### 6.1 Slash commands (project-local)
Project-local commands live in `.claude/commands/` and are shared via git.
Use them for repetitive workflows like updating docs, running verification, and appending to `AI_WORKLOG.md`.

### 6.2 Ralph verification loop plugin ("ralph-wiggum" / "ralph-loop")
Purpose: run an automated “keep going until done” loop with a hard iteration cap, usually via a Stop hook.

Rules for using Ralph in this repo:
- Never start a loop without an explicit user request.
- Always set a hard cap: `--max-iterations N`.
- Prefer small N (5–20) and checkpoint often.
- Use a completion promise only if it is objectively verifiable.
  Good examples: “ALL TESTS PASS”, “DOCS UPDATED + LINKS VERIFIED”.
  Bad examples: “DONE” when there is no verification.
- Each loop iteration must:
  - keep `git status` clean/understood,
  - re-run the chosen verification step(s), and
  - update `AI_WORKLOG.md` at the end of the session.

Suggested setup pattern:
1. Write a short plan first.
2. Identify verification command(s).
3. Then run Ralph with a cap and a clear completion promise.

If the plugin name/command differs by installation, use `/help` in Claude Code to confirm the exact command.
