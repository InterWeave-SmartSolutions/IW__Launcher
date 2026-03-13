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

## 6) Model selection across Claude + Codex (required for Claude Code)

This repo has **14 models** available across two tool families: Claude Code subagents (3 tiers) and OpenAI Codex CLI (11 GPT-5 models). Both are approved for use at any tier. Choose the best model for the task — optimizing for cost when possible, but using higher-tier models freely when the task warrants it.

### Claude Code subagents (Agent tool `model` param)

| Model | ID | Use When |
|---|---|---|
| **Haiku 4.5** | `haiku` | File searches, grep/glob, reading files, git ops, running tests, formatting, boilerplate |
| **Sonnet 4.6** | `sonnet` | Code edits, single-file refactors, writing tests, docs, standard bug fixes, code review |
| **Opus 4.6** | `opus` | Multi-file architecture, security-sensitive code, complex debugging, cross-system integration |

### Codex CLI (GPT-5 family, via ChatGPT account)

OpenAI Codex CLI (`codex-cli 0.111.0`) is installed and authenticated via ChatGPT account. Invoked from Claude Code via Bash. All 11 models are approved for use.

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

**Note:** This install uses a ChatGPT account, NOT a separate OpenAI API key. Models like `o4-mini` are API-only and will NOT work.

### Combined decision matrix (14 models)

| Task Type | Primary Pick | Alternative |
|---|---|---|
| File search / grep / git status | Claude **haiku** | — |
| Read & summarize a file | Claude **haiku** | Codex `gpt-5-codex-mini` |
| Quick code explanation | Codex `gpt-5-codex-mini` | Claude **haiku** |
| Standard code edit / bug fix | Claude **sonnet** | Codex `gpt-5.2-codex` |
| Write tests | Claude **sonnet** | Codex `gpt-5.2-codex` |
| Code review (primary) | Claude **sonnet** | Codex `gpt-5.3-codex` |
| Code review (second opinion) | `codex review` (gpt-5.4) | Codex `gpt-5.3-codex` |
| Refactor / code generation | Claude **sonnet** | Codex `gpt-5.3-codex` |
| Frontend development (React/TS) | Codex `gpt-5.4` | Claude **sonnet** |
| Long-running agentic task | Codex `gpt-5.2` or `gpt-5.4` | Claude **opus** |
| Complex multi-file architecture | Claude **opus** | Codex `gpt-5.4` |
| Security-sensitive code | Claude **opus** | Codex `gpt-5.1-codex-max` |

### Decision rules
1. **Use the right model for the task.** Cheap models for simple work, powerful models for complex work.
2. **Higher-tier Codex models are approved.** `gpt-5.4`, `gpt-5.3-codex`, and `gpt-5.1-codex-max` are all fair game when the task benefits from frontier-level reasoning.
3. **Claude haiku for bulk/search operations** — file search, grep, git, test runs. Native tool access avoids API round-trips.
4. **Codex excels at frontend dev and tool calling** — `gpt-5.4` is specifically optimized for these. Prefer it for React/TypeScript generation.
5. **Claude opus for repo-specific reasoning** — this repo's Java/JSP/React cross-system patterns are deeply embedded in Claude's context window. Use opus when the task requires understanding of the full IW_Launcher architecture.
6. **Main conversation model stays as configured by the user** — these rules apply to subagent spawning and Codex CLI invocations only.
7. **Codex CLI uses separate billing** (ChatGPT account tokens). Claude subagents use Anthropic tokens. Consider budget across both when choosing.

### Usage examples
```bash
# Claude subagents
Agent(model="haiku", prompt="Find all files containing 'ApiTokenStore'")
Agent(model="sonnet", prompt="Create the NotificationsPage component")
Agent(model="opus", prompt="Analyze the cross-UI session lifecycle and propose fixes")

# Codex CLI — cheap tasks
codex exec -m gpt-5-codex-mini "describe the purpose of ApiTokenStore.java"
codex exec --sandbox read-only -a never -m gpt-5.1-codex-mini "list all servlet mappings in web.xml"

# Codex CLI — standard code tasks
codex exec -m gpt-5.2-codex "refactor this function to use async/await"
codex exec -m gpt-5.3-codex "write unit tests for LocalLogoutServlet"

# Codex CLI — frontier tasks
codex exec -m gpt-5.4 "analyze the session lifecycle across JSP and React UIs"
codex exec -m gpt-5.1-codex-max "design the OAuth broker integration architecture"

# Codex CLI — code review (uses default gpt-5.4 from ~/.codex/config.toml)
codex review
```

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
