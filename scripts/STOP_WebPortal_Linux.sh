#!/bin/bash
# =============================================================================
# IW_IDE Web Portal Shutdown - LINUX / WSL / macOS
# =============================================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_DIR="$SCRIPT_DIR/.."
CATALINA_HOME="$BASE_DIR/web_portal/tomcat"

echo "Stopping IW_IDE Web Portal..."

cd "$CATALINA_HOME"
export CATALINA_HOME="$CATALINA_HOME"
export JRE_HOME="${JAVA_HOME:-/usr}"

./bin/shutdown.sh

echo ""
echo "Web Portal stopped."
