#!/bin/bash
# =============================================================================
# IW_IDE Web Portal Launcher - LINUX / WSL / macOS
# =============================================================================
# Starts the Tomcat web portal with database connectivity
# =============================================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_DIR="$SCRIPT_DIR/.."
CATALINA_HOME="$BASE_DIR/web_portal/tomcat"

echo ""
echo "=============================================================================="
echo "                   IW_IDE WEB PORTAL - LINUX"
echo "=============================================================================="
echo ""

# Check for Java
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -1)
    echo "Using Java: $JAVA_VERSION"
else
    echo "ERROR: Java not found! Please install Java (JDK 8+)"
    echo "  Ubuntu/Debian: sudo apt install default-jdk"
    echo "  macOS: brew install openjdk"
    exit 1
fi

echo ""
echo "Starting Tomcat Web Portal..."
echo "Catalina Home: $CATALINA_HOME"
echo ""

cd "$CATALINA_HOME"

# Set environment
export CATALINA_HOME="$CATALINA_HOME"
export JRE_HOME="${JAVA_HOME:-/usr}"

# Make scripts executable
chmod +x bin/*.sh

# Start Tomcat
./bin/startup.sh

echo ""
echo "=============================================================================="
echo "Web Portal is starting..."
echo ""
echo "  Main Page:     http://localhost:8080"
echo "  DB Test Page:  http://localhost:8080/db_test.jsp"
echo "  IW Daemon:     http://localhost:8080/iw-business-daemon/"
echo ""
echo "To stop the web portal, run: ./STOP_WEBPORTAL_LINUX.sh"
echo "=============================================================================="
echo ""
