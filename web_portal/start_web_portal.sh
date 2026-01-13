#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
IW_HOME="$(cd "$SCRIPT_DIR/.." && pwd)"

exec "$IW_HOME/_internal/START_WebPortal_Linux.sh"
