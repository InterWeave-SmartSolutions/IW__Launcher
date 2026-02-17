#!/bin/bash
# Compilation script for LocalLoginServlet
# This script compiles both the error framework and LocalLoginServlet

set -e  # Exit on error

echo "=========================================="
echo "Compiling LocalLoginServlet"
echo "=========================================="
echo ""

# Set paths (relative to project root, one level up from scripts/)
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
IW_HOME="$(cd "$SCRIPT_DIR/.." && pwd)"
JAVAC="javac"
WEB_INF="$IW_HOME/web_portal/tomcat/webapps/iw-business-daemon/WEB-INF"
CLASSES="$WEB_INF/classes"
TOMCAT_LIB="$IW_HOME/web_portal/tomcat/lib"

# Check if javac exists
if ! command -v javac &> /dev/null; then
    echo "ERROR: Java compiler (javac) not found in PATH"
    echo "Please install JDK and ensure javac is in your PATH"
    exit 1
fi

echo "Using Java compiler: $(which javac)"
echo ""

# Create output directories
mkdir -p "$CLASSES/com/interweave/error"
mkdir -p "$CLASSES/com/interweave/businessDaemon/config"

echo "Step 1: Compiling error framework classes..."
javac -d "$CLASSES" \
    -classpath "$TOMCAT_LIB/servlet-api.jar" \
    -source 1.8 -target 1.8 \
    "$IW_HOME/src/main/java/com/interweave/error/"*.java

echo "✓ Error framework compiled successfully"
echo ""

echo "Step 2: Compiling LocalLoginServlet..."
javac -d "$CLASSES" \
    -classpath "$CLASSES:$TOMCAT_LIB/servlet-api.jar:$TOMCAT_LIB/mysql-connector-java-8.0.33.jar" \
    -source 1.8 -target 1.8 \
    "$WEB_INF/src/com/interweave/businessDaemon/config/LocalLoginServlet.java"

echo "✓ LocalLoginServlet compiled successfully"
echo ""

echo "=========================================="
echo "COMPILATION SUCCESSFUL"
echo "=========================================="
echo ""
echo "Compiled files:"
echo "  - $CLASSES/com/interweave/error/*.class"
echo "  - $CLASSES/com/interweave/businessDaemon/config/LocalLoginServlet.class"
echo "  - $CLASSES/com/interweave/businessDaemon/config/LocalLoginServlet\$*.class"
echo ""
echo "Restart Tomcat to load the updated classes:"
echo "  scripts/stop_webportal.bat"
echo "  scripts/start_webportal.bat"
echo ""
