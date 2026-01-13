---
description: Use the Ralph loop plugin safely (iteration caps + verification + completion promise)
---
# /iw-ralph
This command is guidance for running the Ralph loop plugin safely.

Rules:
- Only use when the user explicitly requests a long-running loop.
- Always set `--max-iterations`.
- Define verification before looping.
- Define a completion promise that matches verification.

Suggested pattern:
1. Decide verification:
   - Docs-only: verify referenced files exist + instructions match repo layout
   - Code changes: run repo tests (or the most relevant build step)
2. Choose a completion promise string tied to verification.
3. Run the loop.

Example (adjust per plugin install):
- `/ralph-loop:ralph-loop --max-iterations 10 --completion-promise "DOCS UPDATED + LINKS VERIFIED"`

If your install uses different command names, run `/help` and search for “ralph”.
