---
description: Capture current status and highlight common portability blockers (Git LFS, missing binaries, ports)
---
# /iw-status
Run these and summarize findings:

1. `git --no-pager status -sb`
2. `git --no-pager diff --stat`

Then check for common portability blockers:
- Git LFS pointers instead of binaries (Tomcat jars, exe)
- Missing bundled runtime/server files (`jre/`, `web_portal/tomcat/`)
- Port conflicts (8080)

Do not reference `InterWoven/` unless the user explicitly asks.
