#!/usr/bin/env bash
# =============================================================================
# IW_IDE - DATABASE MIGRATION RUNNER (Linux / WSL / macOS)
# =============================================================================
# Reads .env for DB_MODE and connection details, then applies all pending
# SQL migration files from _internal/sql/ in version order.
#
# Usage:
#   ./_internal/run-migrations.sh              # Apply pending migrations
#   ./_internal/run-migrations.sh --dry-run    # Show what would be applied
#
# Migration files follow the pattern: NNN_description.sql
# Rollback files (NNN_description_rollback.sql) are skipped automatically.
# =============================================================================

set -euo pipefail

# ---- Colors ----
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m'

# ---- Resolve paths ----
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
SQL_DIR="$SCRIPT_DIR/sql"
ENV_FILE="$PROJECT_ROOT/.env"

# ---- Parse arguments ----
DRY_RUN=0
for arg in "$@"; do
    case "$arg" in
        --dry-run)
            DRY_RUN=1
            ;;
        --help|-h)
            echo "Usage: $0 [--dry-run]"
            echo ""
            echo "  --dry-run   Show which migrations would run without applying them"
            echo "  --help      Show this help message"
            exit 0
            ;;
        *)
            echo -e "${RED}[ERROR]${NC} Unknown argument: $arg"
            echo "Usage: $0 [--dry-run]"
            exit 1
            ;;
    esac
done

echo ""
echo -e "${BLUE}=====================================================================${NC}"
echo ""
echo "     IW_IDE - Database Migration Runner"
echo ""
echo -e "${BLUE}=====================================================================${NC}"
echo ""

# ---- Step 1: Load .env ----
if [ ! -f "$ENV_FILE" ]; then
    echo -e "${RED}[ERROR]${NC} .env file not found at: $ENV_FILE"
    echo ""
    echo "  Run START.bat first, or copy .env.example to .env:"
    echo "    cp $PROJECT_ROOT/.env.example $PROJECT_ROOT/.env"
    exit 1
fi

echo -e "${BLUE}[Step 1]${NC} Loading configuration from .env ..."
set -a
source "$ENV_FILE"
set +a

DB_MODE="${DB_MODE:-oracle_cloud}"
echo "  DB_MODE = $DB_MODE"

# ---- Step 2: Resolve connection details based on DB_MODE ----
echo ""
echo -e "${BLUE}[Step 2]${NC} Resolving database connection ..."

DB_ENGINE=""  # "mysql" or "psql"
DB_HOST=""
DB_PORT=""
DB_NAME=""
DB_USER=""
DB_PASSWORD=""

case "$DB_MODE" in
    supabase)
        DB_ENGINE="psql"
        DB_HOST="${SUPABASE_DB_HOST:-}"
        DB_PORT="${SUPABASE_DB_PORT:-5432}"
        DB_NAME="${SUPABASE_DB_NAME:-}"
        DB_USER="${SUPABASE_DB_USER:-}"
        DB_PASSWORD="${SUPABASE_DB_PASSWORD:-}"
        DB_SSLMODE="${SUPABASE_DB_SSLMODE:-require}"
        ;;
    oracle_cloud)
        DB_ENGINE="mysql"
        DB_HOST="${ORACLE_DB_HOST:-129.153.47.225}"
        DB_PORT="${ORACLE_DB_PORT:-3306}"
        DB_NAME="${ORACLE_DB_NAME:-iw_ide}"
        DB_USER="${ORACLE_DB_USER:-}"
        DB_PASSWORD="${ORACLE_DB_PASSWORD:-}"
        ;;
    interweave)
        DB_ENGINE="mysql"
        DB_HOST="${IW_DB_HOST:-148.62.63.8}"
        DB_PORT="${IW_DB_PORT:-3306}"
        DB_NAME="${IW_DB_NAME:-hostedprofiles}"
        DB_USER="${IW_DB_USER:-}"
        DB_PASSWORD="${IW_DB_PASSWORD:-}"
        ;;
    local)
        echo -e "${YELLOW}[SKIP]${NC} DB_MODE=local -- no database to migrate."
        exit 0
        ;;
    *)
        echo -e "${RED}[ERROR]${NC} Unknown DB_MODE: $DB_MODE"
        echo "  Valid modes: supabase, oracle_cloud, interweave, local"
        exit 1
        ;;
esac

echo "  Engine:   $DB_ENGINE"
echo "  Server:   $DB_HOST:$DB_PORT"
echo "  Database: $DB_NAME"
echo "  User:     $DB_USER"

if [ -z "$DB_HOST" ] || [ -z "$DB_USER" ] || [ -z "$DB_PASSWORD" ]; then
    echo ""
    echo -e "${RED}[ERROR]${NC} Missing database credentials for mode '$DB_MODE'."
    echo "  Check your .env file."
    exit 1
fi

# ---- Helper: run a SQL query and return output ----
run_sql() {
    local sql="$1"
    if [ "$DB_ENGINE" = "psql" ]; then
        PGPASSWORD="$DB_PASSWORD" psql \
            -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" \
            --no-psqlrc -t -A -c "$sql" \
            ${DB_SSLMODE:+--set=sslmode="$DB_SSLMODE"} 2>/dev/null || true
    else
        mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" \
            -D "$DB_NAME" -N -s -e "$sql" 2>/dev/null || true
    fi
}

# ---- Helper: run a SQL file ----
run_sql_file() {
    local file="$1"
    if [ "$DB_ENGINE" = "psql" ]; then
        PGPASSWORD="$DB_PASSWORD" psql \
            -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" \
            --no-psqlrc -v ON_ERROR_STOP=1 \
            ${DB_SSLMODE:+--set=sslmode="$DB_SSLMODE"} \
            -f "$file"
    else
        mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" \
            -D "$DB_NAME" < "$file"
    fi
}

# ---- Step 3: Check current schema version ----
echo ""
echo -e "${BLUE}[Step 3]${NC} Checking current schema version ..."

# The settings table has an inconsistency:
#   MySQL schema uses columns: setting_key, setting_value
#   Postgres schema uses columns: key, value
# We also look for two possible key names: db_schema_version AND schema_version
CURRENT_VERSION=""

if [ "$DB_ENGINE" = "psql" ]; then
    # Postgres: try 'key' column first (postgres_schema.sql / 002 migration)
    CURRENT_VERSION=$(run_sql "SELECT value FROM settings WHERE key = 'db_schema_version' LIMIT 1;" 2>/dev/null)
    if [ -z "$CURRENT_VERSION" ]; then
        CURRENT_VERSION=$(run_sql "SELECT value FROM settings WHERE key = 'schema_version' LIMIT 1;" 2>/dev/null)
    fi
    # Fallback: try setting_key column in case Postgres DB was set up with MySQL-style columns
    if [ -z "$CURRENT_VERSION" ]; then
        CURRENT_VERSION=$(run_sql "SELECT setting_value FROM settings WHERE setting_key = 'db_schema_version' LIMIT 1;" 2>/dev/null)
    fi
    if [ -z "$CURRENT_VERSION" ]; then
        CURRENT_VERSION=$(run_sql "SELECT setting_value FROM settings WHERE setting_key = 'schema_version' LIMIT 1;" 2>/dev/null)
    fi
else
    # MySQL: try 'setting_key' column first (mysql_schema.sql / 005 migration)
    CURRENT_VERSION=$(run_sql "SELECT setting_value FROM settings WHERE setting_key = 'db_schema_version' LIMIT 1;" 2>/dev/null)
    if [ -z "$CURRENT_VERSION" ]; then
        CURRENT_VERSION=$(run_sql "SELECT setting_value FROM settings WHERE setting_key = 'schema_version' LIMIT 1;" 2>/dev/null)
    fi
    # Fallback: try key column in case MySQL DB was set up with Postgres-style columns
    if [ -z "$CURRENT_VERSION" ]; then
        CURRENT_VERSION=$(run_sql "SELECT value FROM settings WHERE \`key\` = 'db_schema_version' LIMIT 1;" 2>/dev/null)
    fi
    if [ -z "$CURRENT_VERSION" ]; then
        CURRENT_VERSION=$(run_sql "SELECT value FROM settings WHERE \`key\` = 'schema_version' LIMIT 1;" 2>/dev/null)
    fi
fi

# Strip whitespace
CURRENT_VERSION="$(echo -e "$CURRENT_VERSION" | tr -d '[:space:]')"

if [ -z "$CURRENT_VERSION" ]; then
    echo -e "  ${YELLOW}[WARN]${NC} Could not determine schema version from settings table."
    echo "  Assuming version 0 (no migrations applied)."
    CURRENT_VERSION_NUM=0
else
    echo "  Current schema version: $CURRENT_VERSION"
    # Extract the leading numeric migration prefix from the version string.
    # Versions are semver-like (e.g., 2.0.0 from migration 002, 2.1.0 from 005).
    # We map known versions to migration numbers:
    #   1.0.0 -> baseline (001)
    #   2.0   -> 002
    #   2.0.0 -> 002
    #   2.1.0 -> 005
    # For unknown versions, attempt a best-guess from the major.minor.
    case "$CURRENT_VERSION" in
        2.1.0)  CURRENT_VERSION_NUM=5 ;;
        2.0.0)  CURRENT_VERSION_NUM=2 ;;
        2.0)    CURRENT_VERSION_NUM=2 ;;
        1.0.0)  CURRENT_VERSION_NUM=1 ;;
        *)
            # Try to extract a number if the version is purely numeric
            if [[ "$CURRENT_VERSION" =~ ^[0-9]+$ ]]; then
                CURRENT_VERSION_NUM="$CURRENT_VERSION"
            else
                echo -e "  ${YELLOW}[WARN]${NC} Unrecognized version format '$CURRENT_VERSION'. Assuming migration 0."
                CURRENT_VERSION_NUM=0
            fi
            ;;
    esac
fi

echo "  Effective migration level: $CURRENT_VERSION_NUM"

# ---- Step 4: Discover pending migrations ----
echo ""
echo -e "${BLUE}[Step 4]${NC} Scanning for migration files in $SQL_DIR ..."

if [ ! -d "$SQL_DIR" ]; then
    echo -e "${RED}[ERROR]${NC} SQL directory not found: $SQL_DIR"
    exit 1
fi

# Collect migration files matching NNN_*.sql but NOT *_rollback.sql and NOT test data
PENDING_FILES=()
for sql_file in "$SQL_DIR"/[0-9][0-9][0-9]_*.sql; do
    # Skip if glob matched nothing
    [ -e "$sql_file" ] || continue

    filename="$(basename "$sql_file")"

    # Skip rollback files
    if [[ "$filename" == *_rollback.sql ]]; then
        continue
    fi

    # Skip test data files (not versioned migrations)
    if [[ "$filename" == *test_data* ]]; then
        continue
    fi

    # Extract the version number prefix (e.g., 002 -> 2, 005 -> 5)
    version_prefix="${filename%%_*}"
    version_num=$((10#$version_prefix))  # Force base-10 to handle leading zeros

    if [ "$version_num" -gt "$CURRENT_VERSION_NUM" ]; then
        PENDING_FILES+=("$sql_file")
    fi
done

# Sort by filename (which sorts by version number prefix)
IFS=$'\n' PENDING_FILES=($(sort <<<"${PENDING_FILES[*]}")); unset IFS

if [ ${#PENDING_FILES[@]} -eq 0 ]; then
    echo ""
    echo -e "${GREEN}[OK]${NC} Database is up to date. No pending migrations."
    exit 0
fi

echo "  Found ${#PENDING_FILES[@]} pending migration(s):"
echo ""
for f in "${PENDING_FILES[@]}"; do
    echo "    $(basename "$f")"
done

# ---- Step 5: Apply migrations (or dry-run) ----
echo ""

if [ "$DRY_RUN" -eq 1 ]; then
    echo -e "${CYAN}[DRY RUN]${NC} The following migrations WOULD be applied:"
    echo ""
    for f in "${PENDING_FILES[@]}"; do
        echo -e "    ${CYAN}>>  $(basename "$f")${NC}"
    done
    echo ""
    echo -e "${CYAN}[DRY RUN]${NC} No changes made. Re-run without --dry-run to apply."
    exit 0
fi

echo -e "${BLUE}[Step 5]${NC} Applying migrations ..."
echo ""

APPLIED=0
FAILED=0

for sql_file in "${PENDING_FILES[@]}"; do
    filename="$(basename "$sql_file")"
    echo -n "  Applying $filename ... "

    if run_sql_file "$sql_file" > /dev/null 2>&1; then
        echo -e "${GREEN}OK${NC}"
        APPLIED=$((APPLIED + 1))
    else
        echo -e "${RED}FAILED${NC}"
        FAILED=$((FAILED + 1))
        echo ""
        echo -e "${RED}[ERROR]${NC} Migration failed: $filename"
        echo "  Run manually for details:"
        if [ "$DB_ENGINE" = "psql" ]; then
            echo "    PGPASSWORD=\$SUPABASE_DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f $sql_file"
        else
            echo "    mysql -h $DB_HOST -P $DB_PORT -u $DB_USER -p -D $DB_NAME < $sql_file"
        fi
        echo ""
        echo "  Stopping migration runner. Fix the issue and re-run."
        exit 1
    fi
done

# ---- Summary ----
echo ""
echo -e "${GREEN}=====================================================================${NC}"
echo ""
echo "     Migration Complete"
echo ""
echo -e "${GREEN}=====================================================================${NC}"
echo ""
echo "  Applied: $APPLIED migration(s)"
if [ "$FAILED" -gt 0 ]; then
    echo -e "  Failed:  ${RED}$FAILED${NC}"
fi
echo ""

# Re-check version after migration
NEW_VERSION=""
if [ "$DB_ENGINE" = "psql" ]; then
    NEW_VERSION=$(run_sql "SELECT value FROM settings WHERE key = 'db_schema_version' LIMIT 1;" 2>/dev/null)
    if [ -z "$NEW_VERSION" ]; then
        NEW_VERSION=$(run_sql "SELECT value FROM settings WHERE key = 'schema_version' LIMIT 1;" 2>/dev/null)
    fi
    if [ -z "$NEW_VERSION" ]; then
        NEW_VERSION=$(run_sql "SELECT setting_value FROM settings WHERE setting_key = 'db_schema_version' LIMIT 1;" 2>/dev/null)
    fi
else
    NEW_VERSION=$(run_sql "SELECT setting_value FROM settings WHERE setting_key = 'db_schema_version' LIMIT 1;" 2>/dev/null)
    if [ -z "$NEW_VERSION" ]; then
        NEW_VERSION=$(run_sql "SELECT setting_value FROM settings WHERE setting_key = 'schema_version' LIMIT 1;" 2>/dev/null)
    fi
fi

NEW_VERSION="$(echo -e "$NEW_VERSION" | tr -d '[:space:]')"

if [ -n "$NEW_VERSION" ]; then
    echo "  Schema version is now: $NEW_VERSION"
fi
echo ""
