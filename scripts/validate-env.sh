#!/usr/bin/env bash
# =============================================================================
# IW_IDE - ENVIRONMENT VALIDATION (Linux / WSL / macOS)
# =============================================================================
# Validates the .env configuration file and tests database connectivity.
#
# Usage:
#   ./_internal/validate-env.sh
#
# Exit codes:
#   0 = All checks passed
#   1 = One or more checks failed
# =============================================================================

set -euo pipefail

# ---- Colors ----
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# ---- Resolve paths ----
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
ENV_FILE="$PROJECT_ROOT/.env"

PASS_COUNT=0
FAIL_COUNT=0
WARN_COUNT=0

pass() {
    echo -e "  ${GREEN}[PASS]${NC} $1"
    PASS_COUNT=$((PASS_COUNT + 1))
}

fail() {
    echo -e "  ${RED}[FAIL]${NC} $1"
    FAIL_COUNT=$((FAIL_COUNT + 1))
}

warn() {
    echo -e "  ${YELLOW}[WARN]${NC} $1"
    WARN_COUNT=$((WARN_COUNT + 1))
}

echo ""
echo -e "${BLUE}=====================================================================${NC}"
echo ""
echo "     IW_IDE - Environment Validation"
echo ""
echo -e "${BLUE}=====================================================================${NC}"
echo ""

# ============================================================
# CHECK 1: .env file exists
# ============================================================
echo -e "${BLUE}[Check 1]${NC} .env file existence"

if [ -f "$ENV_FILE" ]; then
    pass ".env file exists at $ENV_FILE"
else
    fail ".env file not found at $ENV_FILE"
    echo ""
    echo "  Suggestion: Run START.bat to auto-create .env, or manually:"
    echo "    cp $PROJECT_ROOT/.env.example $PROJECT_ROOT/.env"
    echo ""
    echo -e "${RED}Cannot continue without .env file.${NC}"
    echo ""
    echo "  Results: 0 passed, 1 failed"
    exit 1
fi

# Load environment
set -a
source "$ENV_FILE"
set +a

# ============================================================
# CHECK 2: DB_MODE is valid
# ============================================================
echo ""
echo -e "${BLUE}[Check 2]${NC} DB_MODE validation"

DB_MODE="${DB_MODE:-}"

if [ -z "$DB_MODE" ]; then
    fail "DB_MODE is not set in .env"
else
    case "$DB_MODE" in
        supabase|oracle_cloud|interweave|local)
            pass "DB_MODE='$DB_MODE' is valid"
            ;;
        *)
            fail "DB_MODE='$DB_MODE' is not a recognized value"
            echo "         Valid values: supabase, oracle_cloud, interweave, local"
            ;;
    esac
fi

# ============================================================
# CHECK 3: Required variables for the selected mode
# ============================================================
echo ""
echo -e "${BLUE}[Check 3]${NC} Required variables for mode '$DB_MODE'"

check_var() {
    local var_name="$1"
    local var_value="${!var_name:-}"
    if [ -z "$var_value" ]; then
        fail "$var_name is not set or empty"
    else
        pass "$var_name is set"
    fi
}

check_var_warn() {
    local var_name="$1"
    local var_value="${!var_name:-}"
    if [ -z "$var_value" ]; then
        warn "$var_name is not set (optional)"
    else
        pass "$var_name is set"
    fi
}

case "$DB_MODE" in
    supabase)
        check_var "SUPABASE_DB_HOST"
        check_var "SUPABASE_DB_PORT"
        check_var "SUPABASE_DB_NAME"
        check_var "SUPABASE_DB_USER"
        check_var "SUPABASE_DB_PASSWORD"
        check_var_warn "SUPABASE_DB_SSLMODE"
        ;;
    oracle_cloud)
        check_var "ORACLE_DB_HOST"
        check_var "ORACLE_DB_PORT"
        check_var "ORACLE_DB_NAME"
        check_var "ORACLE_DB_USER"
        check_var "ORACLE_DB_PASSWORD"
        ;;
    interweave)
        check_var "IW_DB_HOST"
        check_var "IW_DB_PORT"
        check_var "IW_DB_NAME"
        check_var "IW_DB_USER"
        check_var "IW_DB_PASSWORD"
        ;;
    local)
        pass "No database variables required for local mode"
        ;;
esac

# ============================================================
# CHECK 4: CLI tool availability
# ============================================================
echo ""
echo -e "${BLUE}[Check 4]${NC} Database CLI tool availability"

case "$DB_MODE" in
    supabase)
        if command -v psql &>/dev/null; then
            PSQL_VER="$(psql --version 2>/dev/null | head -1)"
            pass "psql is available ($PSQL_VER)"
        else
            fail "psql (PostgreSQL client) is not installed or not in PATH"
            echo "         Install with: sudo apt install postgresql-client"
        fi
        ;;
    oracle_cloud|interweave)
        if command -v mysql &>/dev/null; then
            MYSQL_VER="$(mysql --version 2>/dev/null | head -1)"
            pass "mysql client is available ($MYSQL_VER)"
        else
            fail "mysql client is not installed or not in PATH"
            echo "         Install with: sudo apt install mysql-client"
        fi
        ;;
    local)
        pass "No CLI tool required for local mode"
        ;;
esac

# ============================================================
# CHECK 5: Database connectivity
# ============================================================
echo ""
echo -e "${BLUE}[Check 5]${NC} Database connectivity test"

if [ "$DB_MODE" = "local" ]; then
    pass "Skipped -- local mode has no database connection"
else
    # Resolve connection variables
    DB_HOST=""
    DB_PORT=""
    DB_NAME=""
    DB_USER=""
    DB_PASSWORD=""

    case "$DB_MODE" in
        supabase)
            DB_HOST="${SUPABASE_DB_HOST:-}"
            DB_PORT="${SUPABASE_DB_PORT:-5432}"
            DB_NAME="${SUPABASE_DB_NAME:-}"
            DB_USER="${SUPABASE_DB_USER:-}"
            DB_PASSWORD="${SUPABASE_DB_PASSWORD:-}"
            DB_SSLMODE="${SUPABASE_DB_SSLMODE:-require}"
            ;;
        oracle_cloud)
            DB_HOST="${ORACLE_DB_HOST:-129.153.47.225}"
            DB_PORT="${ORACLE_DB_PORT:-3306}"
            DB_NAME="${ORACLE_DB_NAME:-iw_ide}"
            DB_USER="${ORACLE_DB_USER:-}"
            DB_PASSWORD="${ORACLE_DB_PASSWORD:-}"
            ;;
        interweave)
            DB_HOST="${IW_DB_HOST:-148.62.63.8}"
            DB_PORT="${IW_DB_PORT:-3306}"
            DB_NAME="${IW_DB_NAME:-hostedprofiles}"
            DB_USER="${IW_DB_USER:-}"
            DB_PASSWORD="${IW_DB_PASSWORD:-}"
            ;;
    esac

    if [ -z "$DB_HOST" ] || [ -z "$DB_USER" ] || [ -z "$DB_PASSWORD" ]; then
        fail "Cannot test connectivity -- missing credentials (see Check 3)"
    else
        echo "  Testing connection to $DB_HOST:$DB_PORT/$DB_NAME ..."

        CONNECT_OK=0

        if [ "$DB_MODE" = "supabase" ]; then
            if command -v psql &>/dev/null; then
                RESULT=$(PGPASSWORD="$DB_PASSWORD" psql \
                    -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" \
                    --no-psqlrc -t -A \
                    ${DB_SSLMODE:+--set=sslmode="$DB_SSLMODE"} \
                    -c "SELECT 1;" 2>&1) && CONNECT_OK=1
            else
                warn "Cannot test -- psql not installed"
            fi
        else
            if command -v mysql &>/dev/null; then
                RESULT=$(mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" \
                    -D "$DB_NAME" -N -s -e "SELECT 1;" 2>&1) && CONNECT_OK=1
            else
                warn "Cannot test -- mysql client not installed"
            fi
        fi

        if [ "$CONNECT_OK" -eq 1 ]; then
            pass "Database connection successful"

            # Bonus: check if settings table exists
            echo ""
            echo -e "${BLUE}[Check 5b]${NC} Settings table existence"

            SETTINGS_OK=0
            if [ "$DB_MODE" = "supabase" ]; then
                PGPASSWORD="$DB_PASSWORD" psql \
                    -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" \
                    --no-psqlrc -t -A \
                    ${DB_SSLMODE:+--set=sslmode="$DB_SSLMODE"} \
                    -c "SELECT 1 FROM settings LIMIT 1;" >/dev/null 2>&1 && SETTINGS_OK=1
            else
                mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" \
                    -D "$DB_NAME" -N -s -e "SELECT 1 FROM settings LIMIT 1;" >/dev/null 2>&1 && SETTINGS_OK=1
            fi

            if [ "$SETTINGS_OK" -eq 1 ]; then
                pass "Settings table exists"
            else
                warn "Settings table not found (base schema may not be applied yet)"
            fi
        else
            fail "Database connection failed"
            echo "         Error: $RESULT"
        fi
    fi
fi

# ============================================================
# SUMMARY
# ============================================================
echo ""
echo -e "${BLUE}=====================================================================${NC}"
echo ""
echo "     Validation Summary"
echo ""
echo -e "${BLUE}=====================================================================${NC}"
echo ""
echo -e "  ${GREEN}Passed:${NC}   $PASS_COUNT"
if [ "$WARN_COUNT" -gt 0 ]; then
    echo -e "  ${YELLOW}Warnings:${NC} $WARN_COUNT"
fi
if [ "$FAIL_COUNT" -gt 0 ]; then
    echo -e "  ${RED}Failed:${NC}   $FAIL_COUNT"
fi
echo ""

if [ "$FAIL_COUNT" -gt 0 ]; then
    echo -e "  ${RED}RESULT: FAIL${NC} -- $FAIL_COUNT check(s) did not pass."
    echo ""
    exit 1
else
    echo -e "  ${GREEN}RESULT: PASS${NC} -- All checks passed."
    echo ""
    exit 0
fi
