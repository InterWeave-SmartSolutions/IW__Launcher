# AI_WORKFLOW.md (Mandatory)
This repository is frequently worked on by multiple AI tools/agents (terminal agents, web agents, IDE agents).

All AI agents that read or modify anything under `IW_Launcher/` MUST follow the workflow below for every session.

## 0) Non‑negotiables
- Do not commit or paste secrets. Never commit `.env`. Treat any database host/user/password as sensitive.
- Do not use/read/reference `frontends/InterWoven/` application code unless the user explicitly requests it (see `CLAUDE.md`).
- Exception: mirrored legacy manuals under `frontends/InterWoven/docs/IW_Docs/**` may be used when the task needs historical InterWeave context and `docs/ai/INTERWEAVE_PDF_CONTEXT.md` calls them out.
- Do not delete or overwrite user data (e.g. `workspace/`, `data/`) unless explicitly requested.
- Do not commit to git unless the user explicitly asks.

## 1) Session start checklist (required)
1. Read `CLAUDE.md` and `README.md` in the repo root.
2. If the task involves repository navigation, documentation changes, or broad context gathering, also read `docs/README.md` and `docs/ai/README.md`.
3. If the task touches legacy InterWeave workflows, labels, user training, or old integrations, also read `docs/ai/INTERWEAVE_PDF_CONTEXT.md`.
4. Run:
   - `git --no-pager status -sb`
   - `git --no-pager diff` (or `git --no-pager diff --stat`)
5. State (in the chat) what you believe the user is asking for and what you will change.
6. If you will make changes: create/update the session entry in `AI_WORKLOG.md` (see §5).

## 2) Context gathering rules
- Prefer existing project docs/scripts as the source of truth:
  - `README.md`, `docs/README.md`, `docs/ai/README.md`, `docs/development/BUILD.md`, `docs/ai/INTERWEAVE_PDF_CONTEXT.md`, `scripts/*.bat|*.sh`, `docs/**`, `web_portal/**`, `pom.xml`.
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
- If legacy PDF context is used, merge it additively with current docs and current behavior; do not replace current guidance unless the user explicitly asks. See `docs/adr/002-additive-only-changes.md`.

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
- If legacy PDF context materially informed the work, list the specific PDF filenames consulted.

## 6) Model selection for token efficiency (required for Claude Code)

Claude Code supports multiple model tiers via the `model` parameter on Agent tool calls. **Always use the cheapest model that can handle the task.** This reduces token consumption without sacrificing quality where it matters.

### Available models (as of 2026-03)
| Model | ID | Use When |
|---|---|---|
| **Haiku 4.5** | `haiku` | File searches, grep/glob operations, reading files, simple status checks, formatting, boilerplate generation, running tests, git operations |
| **Sonnet 4.6** | `sonnet` | Moderate code edits, single-file refactors, writing tests, documentation updates, standard bug fixes, code review of small changes |
| **Opus 4.6** | `opus` | Complex multi-file architecture, security-sensitive code, intricate debugging, cross-system integration, novel algorithm design |

### Decision rules
1. **Default to the cheapest viable model.** If the subagent task is "search for X" or "read file Y", use `haiku`.
2. **Escalate only when needed.** If a `sonnet` agent produces inadequate results, retry with `opus` — don't start at `opus` by default.
3. **Main conversation model stays as configured by the user** — this guidance applies to *subagent* spawning via the Agent tool's `model` parameter.
4. **Security-sensitive and architecture work always uses `opus`** — session management, auth flows, cryptographic code, cross-system data flows.
5. **Bulk operations prefer `haiku`** — searching multiple files, running test suites, compiling, git status/diff/log.

### Examples
```
# Searching for a class definition → haiku
Agent(model="haiku", prompt="Find all files containing 'ApiTokenStore'")

# Writing a new React page → sonnet
Agent(model="sonnet", prompt="Create the NotificationsPage component")

# Designing auth session invalidation across JSP + React + token store → opus
Agent(model="opus", prompt="Analyze the cross-UI session lifecycle and propose fixes")
```

### Codex CLI as an additional tier

OpenAI Codex CLI (`codex-cli 0.111.0`) is installed and authenticated via ChatGPT account. It can be invoked from Claude Code via Bash for tasks where a different model family may be more token-efficient or offer a useful second opinion.

**Available Codex models (GPT-5 family, via ChatGPT account):**

| Model ID | Tier | Best For |
|---|---|---|
| `gpt-5-codex-mini` | Mini | Cheapest — simple questions, quick lookups, formatting |
| `gpt-5.1-codex-mini` | Mini | Slightly better mini — light code explanations |
| `gpt-5` | Standard | General reasoning, broad world knowledge |
| `gpt-5.1` | Standard | Improved general reasoning |
| `gpt-5.2` | Standard | Professional work, long-running agents |
| `gpt-5-codex` | Codex | Code-optimized — refactors, reviews, generation |
| `gpt-5.1-codex` | Codex | Improved code-optimized |
| `gpt-5.2-codex` | Codex | Frontier agentic coding |
| `gpt-5.3-codex` | Codex | Frontier Codex-optimized agentic coding |
| `gpt-5.1-codex-max` | Max | Deep and fast reasoning for complex code tasks |
| `gpt-5.4` | Frontier | Latest frontier agentic coding (current default) |

**Note:** This install uses a ChatGPT account, NOT a separate OpenAI API key. Models like `o4-mini` are API-only and will NOT work. Only the GPT-5 family models listed above are available.

**Usage from Claude Code:**
```bash
# Non-interactive execution — cheap model for simple questions
codex exec -m gpt-5-codex-mini "describe the purpose of ApiTokenStore.java"

# Standard code task — code-optimized model
codex exec -m gpt-5.2-codex "refactor this function to use async/await"

# Code review (uses default model from ~/.codex/config.toml)
codex review

# With sandbox and auto-approval for safe read-only tasks
codex exec --sandbox read-only -a never -m gpt-5.1-codex-mini "list all servlet mappings in web.xml"

# Frontier model for complex tasks
codex exec -m gpt-5.4 "analyze the session lifecycle across JSP and React UIs"
```

**When to use Codex CLI vs Claude subagents:**
| Task | Preferred Tool | Reason |
|---|---|---|
| File search / grep / git ops | Claude Agent (haiku) | Native tool access, no API hop |
| Quick code explanation | Codex (`gpt-5-codex-mini`) | Cheap GPT-5 tokens for simple questions |
| Code review (second opinion) | `codex review` | Different model perspective |
| Standard code generation | Codex (`gpt-5.2-codex`) | Code-optimized, good balance |
| Complex architecture / security | Claude Agent (opus) | Best reasoning for this repo's patterns |
| Bulk refactoring | Claude Agent (sonnet) or Codex | Either works; pick by token budget |

**Codex model selection rules:**
1. Default to `gpt-5-codex-mini` or `gpt-5.1-codex-mini` for simple read-only queries
2. Use `gpt-5.2-codex` or `gpt-5.3-codex` for standard code generation and refactoring
3. Use `gpt-5.4` or `gpt-5.1-codex-max` only for complex multi-file analysis
4. `codex review` (no model flag) uses the default from `~/.codex/config.toml` (`gpt-5.4`)

**Important:** Codex CLI uses ChatGPT account tokens (separate billing from Claude). Use it when the OpenAI model family offers a cost or capability advantage for the specific task, not as a default.

## 7) Claude Code-specific enhancements (recommended)
This repo is designed to work well with Claude Code using:
- Project slash commands in `.claude/commands/`
- Hooks (PreToolUse/PostToolUse/Stop) to improve verification and logging
- Optional plugin(s) for automated verification loops

### 7.1 Slash commands (project-local)
Project-local commands live in `.claude/commands/` and are shared via git.
Use them for repetitive workflows like updating docs, running verification, and appending to `AI_WORKLOG.md`.

### 7.2 Ralph verification loop plugin ("ralph-wiggum" / "ralph-loop")
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
