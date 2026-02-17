@echo off
REM =============================================================================
REM IW_IDE - ENVIRONMENT VALIDATION (Windows)
REM =============================================================================
REM Validates the .env configuration file and tests database connectivity.
REM
REM Usage:
REM   _internal\validate-env.bat
REM
REM Exit codes:
REM   0 = All checks passed
REM   1 = One or more checks failed
REM =============================================================================

setlocal enabledelayedexpansion
title IW_IDE - Environment Validation

set "SCRIPT_DIR=%~dp0"
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

set "PROJECT_ROOT=%SCRIPT_DIR%\.."
for %%I in ("%PROJECT_ROOT%") do set "PROJECT_ROOT=%%~fI"

set "ENV_FILE=%PROJECT_ROOT%\.env"

set "PASS_COUNT=0"
set "FAIL_COUNT=0"
set "WARN_COUNT=0"

echo.
echo  =====================================================================
echo.
echo       IW_IDE - Environment Validation
echo.
echo  =====================================================================
echo.

REM ============================================================
REM CHECK 1: .env file exists
REM ============================================================
echo  [Check 1] .env file existence

if exist "%ENV_FILE%" (
    echo   [PASS] .env file exists
    set /a PASS_COUNT+=1
) else (
    echo   [FAIL] .env file not found at: %ENV_FILE%
    set /a FAIL_COUNT+=1
    echo.
    echo   Suggestion: Run START.bat to auto-create .env, or manually copy:
    echo     copy "%PROJECT_ROOT%\.env.example" "%PROJECT_ROOT%\.env"
    echo.
    echo   Cannot continue without .env file.
    echo.
    echo   Results: 0 passed, 1 failed
    goto :exit_error
)

REM ---- Load environment variables ----
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

REM ============================================================
REM CHECK 2: DB_MODE is valid
REM ============================================================
echo.
echo  [Check 2] DB_MODE validation

if not defined DB_MODE (
    echo   [FAIL] DB_MODE is not set in .env
    set /a FAIL_COUNT+=1
    goto :check3
)

if /i "%DB_MODE%"=="supabase" goto :valid_mode
if /i "%DB_MODE%"=="oracle_cloud" goto :valid_mode
if /i "%DB_MODE%"=="interweave" goto :valid_mode
if /i "%DB_MODE%"=="local" goto :valid_mode

echo   [FAIL] DB_MODE='%DB_MODE%' is not a recognized value
echo          Valid values: supabase, oracle_cloud, interweave, local
set /a FAIL_COUNT+=1
goto :check3

:valid_mode
echo   [PASS] DB_MODE='%DB_MODE%' is valid
set /a PASS_COUNT+=1

REM ============================================================
REM CHECK 3: Required variables for the selected mode
REM ============================================================
:check3
echo.
echo  [Check 3] Required variables for mode '%DB_MODE%'

if /i "%DB_MODE%"=="supabase" (
    call :check_var SUPABASE_DB_HOST
    call :check_var SUPABASE_DB_PORT
    call :check_var SUPABASE_DB_NAME
    call :check_var SUPABASE_DB_USER
    call :check_var SUPABASE_DB_PASSWORD
    call :check_var_warn SUPABASE_DB_SSLMODE
) else if /i "%DB_MODE%"=="oracle_cloud" (
    call :check_var ORACLE_DB_HOST
    call :check_var ORACLE_DB_PORT
    call :check_var ORACLE_DB_NAME
    call :check_var ORACLE_DB_USER
    call :check_var ORACLE_DB_PASSWORD
) else if /i "%DB_MODE%"=="interweave" (
    call :check_var IW_DB_HOST
    call :check_var IW_DB_PORT
    call :check_var IW_DB_NAME
    call :check_var IW_DB_USER
    call :check_var IW_DB_PASSWORD
) else if /i "%DB_MODE%"=="local" (
    echo   [PASS] No database variables required for local mode
    set /a PASS_COUNT+=1
)

REM ============================================================
REM CHECK 4: CLI tool availability
REM ============================================================
echo.
echo  [Check 4] Database CLI tool availability

if /i "%DB_MODE%"=="supabase" (
    where psql >nul 2>&1
    if !errorlevel!==0 (
        echo   [PASS] psql is available
        set /a PASS_COUNT+=1
    ) else (
        echo   [FAIL] psql ^(PostgreSQL client^) is not installed or not in PATH
        echo          Download from: https://www.postgresql.org/download/
        set /a FAIL_COUNT+=1
    )
) else if /i "%DB_MODE%"=="oracle_cloud" (
    where mysql >nul 2>&1
    if !errorlevel!==0 (
        echo   [PASS] mysql client is available
        set /a PASS_COUNT+=1
    ) else (
        echo   [FAIL] mysql client is not installed or not in PATH
        echo          Download from: https://dev.mysql.com/downloads/
        set /a FAIL_COUNT+=1
    )
) else if /i "%DB_MODE%"=="interweave" (
    where mysql >nul 2>&1
    if !errorlevel!==0 (
        echo   [PASS] mysql client is available
        set /a PASS_COUNT+=1
    ) else (
        echo   [FAIL] mysql client is not installed or not in PATH
        echo          Download from: https://dev.mysql.com/downloads/
        set /a FAIL_COUNT+=1
    )
) else if /i "%DB_MODE%"=="local" (
    echo   [PASS] No CLI tool required for local mode
    set /a PASS_COUNT+=1
)

REM ============================================================
REM CHECK 5: Database connectivity
REM ============================================================
echo.
echo  [Check 5] Database connectivity test

if /i "%DB_MODE%"=="local" (
    echo   [PASS] Skipped -- local mode has no database connection
    set /a PASS_COUNT+=1
    goto :summary
)

REM Resolve connection variables
set "DB_HOST="
set "DB_PORT="
set "DB_NAME="
set "DB_USER="
set "DB_PASSWORD="

if /i "%DB_MODE%"=="supabase" (
    set "DB_HOST=!SUPABASE_DB_HOST!"
    set "DB_PORT=!SUPABASE_DB_PORT!"
    set "DB_NAME=!SUPABASE_DB_NAME!"
    set "DB_USER=!SUPABASE_DB_USER!"
    set "DB_PASSWORD=!SUPABASE_DB_PASSWORD!"
) else if /i "%DB_MODE%"=="oracle_cloud" (
    if not defined ORACLE_DB_HOST set "ORACLE_DB_HOST=129.153.47.225"
    if not defined ORACLE_DB_PORT set "ORACLE_DB_PORT=3306"
    if not defined ORACLE_DB_NAME set "ORACLE_DB_NAME=iw_ide"
    set "DB_HOST=!ORACLE_DB_HOST!"
    set "DB_PORT=!ORACLE_DB_PORT!"
    set "DB_NAME=!ORACLE_DB_NAME!"
    set "DB_USER=!ORACLE_DB_USER!"
    set "DB_PASSWORD=!ORACLE_DB_PASSWORD!"
) else if /i "%DB_MODE%"=="interweave" (
    if not defined IW_DB_HOST set "IW_DB_HOST=148.62.63.8"
    if not defined IW_DB_PORT set "IW_DB_PORT=3306"
    if not defined IW_DB_NAME set "IW_DB_NAME=hostedprofiles"
    set "DB_HOST=!IW_DB_HOST!"
    set "DB_PORT=!IW_DB_PORT!"
    set "DB_NAME=!IW_DB_NAME!"
    set "DB_USER=!IW_DB_USER!"
    set "DB_PASSWORD=!IW_DB_PASSWORD!"
)

if "!DB_HOST!"=="" goto :skip_connect
if "!DB_USER!"=="" goto :skip_connect
if "!DB_PASSWORD!"=="" goto :skip_connect

echo   Testing connection to !DB_HOST!:!DB_PORT!/!DB_NAME! ...

if /i "%DB_MODE%"=="supabase" (
    set "PGPASSWORD=!DB_PASSWORD!"
    psql -h !DB_HOST! -p !DB_PORT! -U !DB_USER! -d !DB_NAME! -t -A -c "SELECT 1;" >nul 2>&1
    if !errorlevel!==0 (
        echo   [PASS] Database connection successful
        set /a PASS_COUNT+=1
    ) else (
        echo   [FAIL] Database connection failed
        set /a FAIL_COUNT+=1
    )
) else (
    mysql -h !DB_HOST! -P !DB_PORT! -u !DB_USER! -p!DB_PASSWORD! -D !DB_NAME! -N -s -e "SELECT 1;" >nul 2>&1
    if !errorlevel!==0 (
        echo   [PASS] Database connection successful
        set /a PASS_COUNT+=1
    ) else (
        echo   [FAIL] Database connection failed
        set /a FAIL_COUNT+=1
    )
)
goto :summary

:skip_connect
echo   [FAIL] Cannot test connectivity -- missing credentials (see Check 3)
set /a FAIL_COUNT+=1

REM ============================================================
REM SUMMARY
REM ============================================================
:summary
echo.
echo  =====================================================================
echo.
echo       Validation Summary
echo.
echo  =====================================================================
echo.
echo   Passed:   %PASS_COUNT%
if %WARN_COUNT% GTR 0 echo   Warnings: %WARN_COUNT%
if %FAIL_COUNT% GTR 0 echo   Failed:   %FAIL_COUNT%
echo.

if %FAIL_COUNT% GTR 0 (
    echo   RESULT: FAIL -- %FAIL_COUNT% check(s) did not pass.
    echo.
    goto :exit_error
) else (
    echo   RESULT: PASS -- All checks passed.
    echo.
    goto :exit_ok
)

REM ============================================================
REM SUBROUTINES
REM ============================================================

:check_var
REM Check that an environment variable is set and non-empty
set "VAR_NAME=%~1"
if not defined %VAR_NAME% (
    echo   [FAIL] %VAR_NAME% is not set or empty
    set /a FAIL_COUNT+=1
) else (
    call set "VAR_VALUE=%%%VAR_NAME%%%"
    if "!VAR_VALUE!"=="" (
        echo   [FAIL] %VAR_NAME% is not set or empty
        set /a FAIL_COUNT+=1
    ) else (
        echo   [PASS] %VAR_NAME% is set
        set /a PASS_COUNT+=1
    )
)
goto :eof

:check_var_warn
REM Check a variable but only warn if missing (optional variable)
set "VAR_NAME=%~1"
if not defined %VAR_NAME% (
    echo   [WARN] %VAR_NAME% is not set (optional)
    set /a WARN_COUNT+=1
) else (
    call set "VAR_VALUE=%%%VAR_NAME%%%"
    if "!VAR_VALUE!"=="" (
        echo   [WARN] %VAR_NAME% is not set (optional)
        set /a WARN_COUNT+=1
    ) else (
        echo   [PASS] %VAR_NAME% is set
        set /a PASS_COUNT+=1
    )
)
goto :eof

:exit_error
echo  Press any key to exit...
pause >nul
endlocal
exit /b 1

:exit_ok
endlocal
exit /b 0
