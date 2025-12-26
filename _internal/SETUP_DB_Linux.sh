#!/bin/bash
# =============================================================================
# IW_IDE DATABASE SETUP - LINUX / WSL / macOS
# =============================================================================
# Run this FIRST before using the IDE. Sets up MySQL database connection.
# =============================================================================

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo ""
echo -e "${BLUE}====================================================================="
echo ""
echo "     IW_IDE DATABASE SETUP"
echo ""
echo "=====================================================================${NC}"
echo ""
echo "  This script configures your database connection."
echo "  Run this ONCE before using the IDE."
echo ""
echo "====================================================================="
echo ""

# === Step 1: Check/Create .env file ===
echo -e "${BLUE}[Step 1 of 3]${NC} Checking configuration..."
echo ""

if [ ! -f ".env" ]; then
    if [ -f ".env.example" ]; then
        echo "   Creating .env from template..."
        cp ".env.example" ".env"
        echo -e "   ${GREEN}[OK]${NC} Configuration file created"
        echo ""
        echo -e "   ${YELLOW}IMPORTANT:${NC} Please edit .env with your database credentials:"
        echo "       $SCRIPT_DIR/.env"
        echo ""
        echo "   Then run this script again."
        exit 0
    else
        echo -e "   ${RED}[ERROR]${NC} .env.example not found!"
        exit 1
    fi
else
    echo -e "   ${GREEN}[OK]${NC} Configuration file exists"
fi

# === Load environment variables ===
set -a
source ".env"
set +a

# === Step 2: Configure database connection ===
echo ""
echo -e "${BLUE}[Step 2 of 3]${NC} Configuring database connection..."
echo ""

DB_MODE="${DB_MODE:-oracle_cloud}"

if [ "$DB_MODE" = "interweave" ]; then
    echo "   Mode: INTERWEAVE SERVER"
    DB_HOST="${IW_DB_HOST:-148.62.63.8}"
    DB_PORT="${IW_DB_PORT:-3306}"
    DB_NAME="${IW_DB_NAME:-hostedprofiles}"
    DB_USER="${IW_DB_USER}"
    DB_PASSWORD="${IW_DB_PASSWORD}"
    CONFIG_TEMPLATE="$SCRIPT_DIR/docs/authentication/config.xml.hosted.template"
elif [ "$DB_MODE" = "local" ]; then
    echo "   Mode: OFFLINE (no database)"
    DB_HOST="localhost"
    DB_PORT="3306"
    DB_NAME="none"
    DB_USER="none"
    DB_PASSWORD="none"
    CONFIG_TEMPLATE="$SCRIPT_DIR/docs/authentication/config.xml.local.template"
else
    echo "   Mode: YOUR SERVER (Oracle Cloud)"
    DB_HOST="${ORACLE_DB_HOST:-129.153.47.225}"
    DB_PORT="${ORACLE_DB_PORT:-3306}"
    DB_NAME="${ORACLE_DB_NAME:-iw_ide}"
    DB_USER="${ORACLE_DB_USER}"
    DB_PASSWORD="${ORACLE_DB_PASSWORD}"
    CONFIG_TEMPLATE="$SCRIPT_DIR/docs/authentication/config.xml.oracle_cloud.template"
fi

echo ""
echo "   Server:   $DB_HOST:$DB_PORT"
echo "   Database: $DB_NAME"
echo "   User:     $DB_USER"

# Validate required settings
if [ "$DB_MODE" != "local" ]; then
    if [ -z "$DB_USER" ]; then
        echo -e "   ${RED}[ERROR]${NC} DB_USER not set. Check .env file."
        exit 1
    fi
    if [ -z "$DB_PASSWORD" ]; then
        echo -e "   ${RED}[ERROR]${NC} DB_PASSWORD not set. Check .env file."
        exit 1
    fi
fi

# === Step 3: Apply configuration ===
echo ""
echo -e "${BLUE}[Step 3 of 3]${NC} Applying configuration..."
echo ""

CONTEXT_TEMPLATE="$SCRIPT_DIR/web_portal/tomcat/conf/context.xml.mysql"
CONTEXT_FILE="$SCRIPT_DIR/web_portal/tomcat/conf/context.xml"
BD_CONFIG="$SCRIPT_DIR/web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/config.xml"

# Configure Tomcat context.xml
if [ "$DB_MODE" != "local" ] && [ -f "$CONTEXT_TEMPLATE" ]; then
    sed -e "s/__DB_HOST__/$DB_HOST/g" \
        -e "s/__DB_PORT__/$DB_PORT/g" \
        -e "s/__DB_NAME__/$DB_NAME/g" \
        -e "s/__DB_USER__/$DB_USER/g" \
        -e "s/__DB_PASSWORD__/$DB_PASSWORD/g" \
        "$CONTEXT_TEMPLATE" > "$CONTEXT_FILE"
    echo -e "   ${GREEN}[OK]${NC} Tomcat database connection configured"
fi

# Configure Business Daemon config.xml
if [ -f "$CONFIG_TEMPLATE" ]; then
    sed -e "s/YOUR_ORACLE_PASSWORD_HERE/$DB_PASSWORD/g" \
        -e "s/YOUR_IW_USERNAME/$DB_USER/g" \
        -e "s/YOUR_IW_PASSWORD/$DB_PASSWORD/g" \
        "$CONFIG_TEMPLATE" > "$BD_CONFIG"
    echo -e "   ${GREEN}[OK]${NC} Login system configured"
else
    echo -e "   ${YELLOW}[WARN]${NC} Config template not found"
fi

# Check MySQL driver
if [ -f "$SCRIPT_DIR/web_portal/tomcat/lib/mysql-connector-java-8.0.33.jar" ]; then
    echo -e "   ${GREEN}[OK]${NC} MySQL driver present"
else
    echo -e "   ${YELLOW}[WARN]${NC} MySQL driver missing from tomcat/lib/"
fi

# Make Linux scripts executable
chmod +x "$SCRIPT_DIR/scripts/START_WebPortal_Linux.sh" 2>/dev/null || true
chmod +x "$SCRIPT_DIR/scripts/STOP_WebPortal_Linux.sh" 2>/dev/null || true

echo ""
echo -e "${GREEN}====================================================================="
echo ""
echo "     SETUP COMPLETE!"
echo ""
echo "=====================================================================${NC}"
echo ""
echo "  Your IW_IDE is now configured."
echo ""
echo "  Database Mode: $DB_MODE"
if [ "$DB_MODE" = "local" ]; then
    echo "  Login with:    __iw_admin__ / %iwps%"
else
    echo "  Server:        $DB_HOST"
fi
echo ""
echo "---------------------------------------------------------------------"
echo "  NEXT STEPS:"
echo "---------------------------------------------------------------------"
echo ""
echo "  Start Web Portal:  ./scripts/START_WebPortal_Linux.sh"
echo "  Stop Web Portal:   ./scripts/STOP_WebPortal_Linux.sh"
echo ""
echo "  Note: The IDE (iw_ide.exe) requires Windows."
echo ""
echo "====================================================================="
echo ""

# Ask to start web portal
echo -e "${BLUE}Start the Web Portal now? (y/n)${NC}"
read -r response

if [[ "$response" =~ ^[Yy]$ ]]; then
    echo ""
    echo "Starting Web Portal..."
    ./scripts/START_WebPortal_Linux.sh &

    echo ""
    echo "Web Portal is starting. Please wait a moment."
    echo ""
    echo "  Login Page:  http://localhost:8080/iw-business-daemon/IWLogin.jsp"
    echo ""
    echo "  Admin Login:"
    echo "    Username: __iw_admin__"
    echo "    Password: %iwps%"
    echo ""

    # Try to open browser
    sleep 3
    if command -v xdg-open &> /dev/null; then
        xdg-open http://localhost:8080/iw-business-daemon/IWLogin.jsp 2>/dev/null || true
    elif command -v open &> /dev/null; then
        open http://localhost:8080/iw-business-daemon/IWLogin.jsp 2>/dev/null || true
    fi
fi

echo ""
echo "Press Enter to exit..."
read -r
