@echo off
REM =============================================================================
REM IW_IDE - DATABASE MIGRATION RUNNER (Windows)
REM =============================================================================
REM Reads .env for DB_MODE and connection details, then applies all pending
REM SQL migration files from _internal\sql\ in version order.
REM
REM Usage:
REM   _internal\run-migrations.bat              Apply pending migrations
REM   _internal\run-migrations.bat --dry-run    Show what would be applied
REM =============================================================================

setlocal enabledelayedexpansion
title IW_IDE - Migration Runner

set "SCRIPT_DIR=%~dp0"
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

set "PROJECT_ROOT=%SCRIPT_DIR%\.."
for %%I in ("%PROJECT_ROOT%") do set "PROJECT_ROOT=%%~fI"

set "SQL_DIR=%SCRIPT_DIR%\sql"
set "ENV_FILE=%PROJECT_ROOT%\.env"

REM ---- Parse arguments ----
set "DRY_RUN=0"
if /i "%~1"=="--dry-run" set "DRY_RUN=1"
if /i "%~1"=="--help" goto :show_help
if /i "%~1"=="-h" goto :show_help

echo.
echo  =====================================================================
echo.
echo       IW_IDE - Database Migration Runner
echo.
echo  =====================================================================
echo.

REM ---- Step 1: Load .env ----
if not exist "%ENV_FILE%" (
    echo  [ERROR] .env file not found at: %ENV_FILE%
    echo.
    echo   Run START.bat first, or copy .env.example to .env
    goto :exit_error
)

echo  [Step 1] Loading configuration from .env ...

for /f "tokens=1,* delims==" %%a in ('findstr /B "DB_MODE=" "%ENV_FILE%"') do set "DB_MODE=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_HOST=" "%ENV_FILE%"') do set "ORACLE_DB_HOST=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_PORT=" "%ENV_FILE%"') do set "ORACLE_DB_PORT=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_NAME=" "%ENV_FILE%"') do set "ORACLE_DB_NAME=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_USER=" "%ENV_FILE%"') do set "ORACLE_DB_USER=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_PASSWORD=" "%ENV_FILE%"') do set "ORACLE_DB_PASSWORD=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_HOST=" "%ENV_FILE%"') do set "IW_DB_HOST=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_PORT=" "%ENV_FILE%"') do set "IW_DB_PORT=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_NAME=" "%ENV_FILE%"') do set "IW_DB_NAME=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_USER=" "%ENV_FILE%"') do set "IW_DB_USER=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_PASSWORD=" "%ENV_FILE%"') do set "IW_DB_PASSWORD=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_HOST=" "%ENV_FILE%"') do set "SUPABASE_DB_HOST=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_PORT=" "%ENV_FILE%"') do set "SUPABASE_DB_PORT=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_NAME=" "%ENV_FILE%"') do set "SUPABASE_DB_NAME=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_USER=" "%ENV_FILE%"') do set "SUPABASE_DB_USER=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_PASSWORD=" "%ENV_FILE%"') do set "SUPABASE_DB_PASSWORD=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_SSLMODE=" "%ENV_FILE%"') do set "SUPABASE_DB_SSLMODE=%%b"

if not defined DB_MODE set "DB_MODE=oracle_cloud"
echo   DB_MODE = %DB_MODE%

REM ---- Step 2: Resolve connection details ----
echo.
echo  [Step 2] Resolving database connection ...

set "DB_ENGINE="
set "DB_HOST="
set "DB_PORT="
set "DB_NAME="
set "DB_USER="
set "DB_PASSWORD="

if /i "%DB_MODE%"=="supabase" (
    set "DB_ENGINE=psql"
    if not defined SUPABASE_DB_HOST set "SUPABASE_DB_HOST="
    if not defined SUPABASE_DB_PORT set "SUPABASE_DB_PORT=5432"
    set "DB_HOST=!SUPABASE_DB_HOST!"
    set "DB_PORT=!SUPABASE_DB_PORT!"
    set "DB_NAME=!SUPABASE_DB_NAME!"
    set "DB_USER=!SUPABASE_DB_USER!"
    set "DB_PASSWORD=!SUPABASE_DB_PASSWORD!"
    if not defined SUPABASE_DB_SSLMODE set "SUPABASE_DB_SSLMODE=require"
    set "DB_SSLMODE=!SUPABASE_DB_SSLMODE!"
) else if /i "%DB_MODE%"=="oracle_cloud" (
    set "DB_ENGINE=mysql"
    if not defined ORACLE_DB_HOST set "ORACLE_DB_HOST=129.153.47.225"
    if not defined ORACLE_DB_PORT set "ORACLE_DB_PORT=3306"
    if not defined ORACLE_DB_NAME set "ORACLE_DB_NAME=iw_ide"
    set "DB_HOST=!ORACLE_DB_HOST!"
    set "DB_PORT=!ORACLE_DB_PORT!"
    set "DB_NAME=!ORACLE_DB_NAME!"
    set "DB_USER=!ORACLE_DB_USER!"
    set "DB_PASSWORD=!ORACLE_DB_PASSWORD!"
) else if /i "%DB_MODE%"=="interweave" (
    set "DB_ENGINE=mysql"
    if not defined IW_DB_HOST set "IW_DB_HOST=148.62.63.8"
    if not defined IW_DB_PORT set "IW_DB_PORT=3306"
    if not defined IW_DB_NAME set "IW_DB_NAME=hostedprofiles"
    set "DB_HOST=!IW_DB_HOST!"
    set "DB_PORT=!IW_DB_PORT!"
    set "DB_NAME=!IW_DB_NAME!"
    set "DB_USER=!IW_DB_USER!"
    set "DB_PASSWORD=!IW_DB_PASSWORD!"
) else if /i "%DB_MODE%"=="local" (
    echo   [SKIP] DB_MODE=local -- no database to migrate.
    goto :exit_ok
) else (
    echo   [ERROR] Unknown DB_MODE: %DB_MODE%
    echo   Valid modes: supabase, oracle_cloud, interweave, local
    goto :exit_error
)

echo   Engine:   %DB_ENGINE%
echo   Server:   %DB_HOST%:%DB_PORT%
echo   Database: %DB_NAME%
echo   User:     %DB_USER%

if "%DB_HOST%"=="" goto :missing_creds
if "%DB_USER%"=="" goto :missing_creds
if "%DB_PASSWORD%"=="" goto :missing_creds
goto :creds_ok

:missing_creds
echo.
echo   [ERROR] Missing database credentials for mode '%DB_MODE%'.
echo   Check your .env file.
goto :exit_error

:creds_ok

REM ---- Step 3: Check current schema version ----
echo.
echo  [Step 3] Checking current schema version ...

set "CURRENT_VERSION="

REM Try both column naming conventions depending on engine
if /i "%DB_ENGINE%"=="psql" (
    REM Postgres: try key column first, then setting_key fallback
    for /f "usebackq tokens=*" %%v in (`set "PGPASSWORD=%DB_PASSWORD%" ^& psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -t -A -c "SELECT value FROM settings WHERE key = 'db_schema_version' LIMIT 1;" 2^>nul`) do set "CURRENT_VERSION=%%v"
    if "!CURRENT_VERSION!"=="" (
        for /f "usebackq tokens=*" %%v in (`set "PGPASSWORD=%DB_PASSWORD%" ^& psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -t -A -c "SELECT value FROM settings WHERE key = 'schema_version' LIMIT 1;" 2^>nul`) do set "CURRENT_VERSION=%%v"
    )
    if "!CURRENT_VERSION!"=="" (
        for /f "usebackq tokens=*" %%v in (`set "PGPASSWORD=%DB_PASSWORD%" ^& psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -t -A -c "SELECT setting_value FROM settings WHERE setting_key = 'db_schema_version' LIMIT 1;" 2^>nul`) do set "CURRENT_VERSION=%%v"
    )
    if "!CURRENT_VERSION!"=="" (
        for /f "usebackq tokens=*" %%v in (`set "PGPASSWORD=%DB_PASSWORD%" ^& psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -t -A -c "SELECT setting_value FROM settings WHERE setting_key = 'schema_version' LIMIT 1;" 2^>nul`) do set "CURRENT_VERSION=%%v"
    )
) else (
    REM MySQL: try setting_key column first, then key fallback
    for /f "usebackq tokens=*" %%v in (`mysql -h %DB_HOST% -P %DB_PORT% -u %DB_USER% -p%DB_PASSWORD% -D %DB_NAME% -N -s -e "SELECT setting_value FROM settings WHERE setting_key = 'db_schema_version' LIMIT 1;" 2^>nul`) do set "CURRENT_VERSION=%%v"
    if "!CURRENT_VERSION!"=="" (
        for /f "usebackq tokens=*" %%v in (`mysql -h %DB_HOST% -P %DB_PORT% -u %DB_USER% -p%DB_PASSWORD% -D %DB_NAME% -N -s -e "SELECT setting_value FROM settings WHERE setting_key = 'schema_version' LIMIT 1;" 2^>nul`) do set "CURRENT_VERSION=%%v"
    )
    if "!CURRENT_VERSION!"=="" (
        for /f "usebackq tokens=*" %%v in (`mysql -h %DB_HOST% -P %DB_PORT% -u %DB_USER% -p%DB_PASSWORD% -D %DB_NAME% -N -s -e "SELECT value FROM settings WHERE `key` = 'db_schema_version' LIMIT 1;" 2^>nul`) do set "CURRENT_VERSION=%%v"
    )
    if "!CURRENT_VERSION!"=="" (
        for /f "usebackq tokens=*" %%v in (`mysql -h %DB_HOST% -P %DB_PORT% -u %DB_USER% -p%DB_PASSWORD% -D %DB_NAME% -N -s -e "SELECT value FROM settings WHERE `key` = 'schema_version' LIMIT 1;" 2^>nul`) do set "CURRENT_VERSION=%%v"
    )
)

REM Map version string to migration number
set "CURRENT_VERSION_NUM=0"

if "!CURRENT_VERSION!"=="" (
    echo   [WARN] Could not determine schema version. Assuming version 0.
    set "CURRENT_VERSION_NUM=0"
) else (
    echo   Current schema version: !CURRENT_VERSION!

    if "!CURRENT_VERSION!"=="2.1.0" set "CURRENT_VERSION_NUM=5"
    if "!CURRENT_VERSION!"=="2.0.0" set "CURRENT_VERSION_NUM=2"
    if "!CURRENT_VERSION!"=="2.0"   set "CURRENT_VERSION_NUM=2"
    if "!CURRENT_VERSION!"=="1.0.0" set "CURRENT_VERSION_NUM=1"
)

echo   Effective migration level: !CURRENT_VERSION_NUM!

REM ---- Step 4: Discover pending migrations ----
echo.
echo  [Step 4] Scanning for migration files in %SQL_DIR% ...

if not exist "%SQL_DIR%" (
    echo   [ERROR] SQL directory not found: %SQL_DIR%
    goto :exit_error
)

set "PENDING_COUNT=0"
set "PENDING_LIST="

for %%F in ("%SQL_DIR%\[0-9][0-9][0-9]_*.sql") do (
    set "FNAME=%%~nxF"
    set "FPATH=%%F"

    REM Skip rollback files
    echo !FNAME! | findstr /I "_rollback.sql" >nul 2>&1
    if errorlevel 1 (
        REM Skip test data files
        echo !FNAME! | findstr /I "test_data" >nul 2>&1
        if errorlevel 1 (
            REM Extract version prefix (first 3 chars)
            set "VER_PREFIX=!FNAME:~0,3!"

            REM Remove leading zeros for numeric comparison
            set /a "VER_NUM=1!VER_PREFIX! - 1000"

            if !VER_NUM! GTR !CURRENT_VERSION_NUM! (
                set /a PENDING_COUNT+=1
                set "PENDING_!PENDING_COUNT!=!FPATH!"
                set "PENDING_NAME_!PENDING_COUNT!=!FNAME!"
            )
        )
    )
)

if %PENDING_COUNT%==0 (
    echo.
    echo   [OK] Database is up to date. No pending migrations.
    goto :exit_ok
)

echo   Found %PENDING_COUNT% pending migration(s):
echo.
for /L %%i in (1,1,%PENDING_COUNT%) do (
    echo     !PENDING_NAME_%%i!
)

REM ---- Step 5: Apply migrations (or dry-run) ----
echo.

if "%DRY_RUN%"=="1" (
    echo   [DRY RUN] The following migrations WOULD be applied:
    echo.
    for /L %%i in (1,1,%PENDING_COUNT%) do (
        echo     ^>^>  !PENDING_NAME_%%i!
    )
    echo.
    echo   [DRY RUN] No changes made. Re-run without --dry-run to apply.
    goto :exit_ok
)

echo  [Step 5] Applying migrations ...
echo.

set "APPLIED=0"
set "FAILED=0"

for /L %%i in (1,1,%PENDING_COUNT%) do (
    set "SQL_FILE=!PENDING_%%i!"
    set "SQL_NAME=!PENDING_NAME_%%i!"

    echo   Applying !SQL_NAME! ...

    if /i "%DB_ENGINE%"=="psql" (
        set "PGPASSWORD=%DB_PASSWORD%"
        psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -v ON_ERROR_STOP=1 -f "!SQL_FILE!" >nul 2>&1
    ) else (
        mysql -h %DB_HOST% -P %DB_PORT% -u %DB_USER% -p%DB_PASSWORD% -D %DB_NAME% < "!SQL_FILE!" >nul 2>&1
    )

    if errorlevel 1 (
        echo   [FAILED] !SQL_NAME!
        set /a FAILED+=1
        echo.
        echo   [ERROR] Migration failed: !SQL_NAME!
        echo   Run the SQL file manually for details.
        echo   Stopping migration runner.
        goto :exit_error
    ) else (
        echo   [OK] !SQL_NAME!
        set /a APPLIED+=1
    )
)

REM ---- Summary ----
echo.
echo  =====================================================================
echo.
echo       Migration Complete
echo.
echo  =====================================================================
echo.
echo   Applied: %APPLIED% migration(s)
if %FAILED% GTR 0 echo   Failed:  %FAILED%
echo.
goto :exit_ok

:show_help
echo Usage: %~nx0 [--dry-run]
echo.
echo   --dry-run   Show which migrations would run without applying them
echo   --help      Show this help message
goto :exit_ok

:exit_error
echo.
echo  Press any key to exit...
pause >nul
endlocal
exit /b 1

:exit_ok
echo.
endlocal
exit /b 0
