@echo off
REM =============================================================================
REM IW_IDE DATABASE SETUP - WINDOWS
REM =============================================================================
REM Run this FIRST before using the IDE. Sets up database connection.
REM =============================================================================

setlocal enabledelayedexpansion
title IW_IDE - Database Setup
mode con: cols=75 lines=40

set "SCRIPT_DIR=%~dp0"
cd /d "%SCRIPT_DIR%"

echo.
echo  =====================================================================
echo.
echo       IW_IDE DATABASE SETUP
echo.
echo  =====================================================================
echo.
echo   This script configures your database connection.
echo   Run this ONCE before using the IDE.
echo.
echo  =====================================================================
echo.

REM === Check/Create .env file ===
echo  [Step 1 of 3] Checking configuration...
echo.

if not exist ".env" (
    if exist ".env.example" (
        echo    Creating .env from template...
        copy ".env.example" ".env" >nul
        echo    [OK] Configuration file created
        echo.
        echo    IMPORTANT: Opening .env for you to review.
        echo    Make sure the database credentials are correct!
        echo.
        notepad ".env"
        echo.
        echo    Press any key after saving .env to continue...
        pause >nul
    ) else (
        echo    [ERROR] .env.example not found!
        echo.
        pause
        exit /b 1
    )
) else (
    echo    [OK] Configuration file exists
)

REM === Load environment variables ===
for /f "tokens=1,* delims==" %%a in ('findstr /B "DB_MODE=" ".env"') do set "DB_MODE=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_HOST=" ".env"') do set "SUPABASE_DB_HOST=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_PORT=" ".env"') do set "SUPABASE_DB_PORT=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_NAME=" ".env"') do set "SUPABASE_DB_NAME=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_USER=" ".env"') do set "SUPABASE_DB_USER=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_PASSWORD=" ".env"') do set "SUPABASE_DB_PASSWORD=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_HOST=" ".env"') do set "ORACLE_DB_HOST=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_PORT=" ".env"') do set "ORACLE_DB_PORT=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_NAME=" ".env"') do set "ORACLE_DB_NAME=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_USER=" ".env"') do set "ORACLE_DB_USER=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_PASSWORD=" ".env"') do set "ORACLE_DB_PASSWORD=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_HOST=" ".env"') do set "IW_DB_HOST=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_PORT=" ".env"') do set "IW_DB_PORT=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_NAME=" ".env"') do set "IW_DB_NAME=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_USER=" ".env"') do set "IW_DB_USER=%%b"
for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_PASSWORD=" ".env"') do set "IW_DB_PASSWORD=%%b"

if not defined DB_MODE set "DB_MODE=supabase"

REM === Set database variables based on mode ===
echo.
echo  [Step 2 of 3] Configuring database connection...
echo.

if /i "%DB_MODE%"=="supabase" (
    echo    Mode: SUPABASE (Postgres)
    if not defined SUPABASE_DB_HOST set "SUPABASE_DB_HOST=db.hpodmkchdzwjtlnxjohf.supabase.co"
    if not defined SUPABASE_DB_PORT set "SUPABASE_DB_PORT=5432"
    if not defined SUPABASE_DB_NAME set "SUPABASE_DB_NAME=postgres"
    set "DB_HOST=!SUPABASE_DB_HOST!"
    set "DB_PORT=!SUPABASE_DB_PORT!"
    set "DB_NAME=!SUPABASE_DB_NAME!"
    set "DB_USER=!SUPABASE_DB_USER!"
    set "DB_PASSWORD=!SUPABASE_DB_PASSWORD!"
    set "CONFIG_TEMPLATE=%SCRIPT_DIR%docs\authentication\config.xml.supabase.template"
) else if /i "%DB_MODE%"=="interweave" (
    echo    Mode: INTERWEAVE SERVER
    if not defined IW_DB_HOST set "IW_DB_HOST=148.62.63.8"
    if not defined IW_DB_PORT set "IW_DB_PORT=3306"
    if not defined IW_DB_NAME set "IW_DB_NAME=hostedprofiles"
    set "DB_HOST=!IW_DB_HOST!"
    set "DB_PORT=!IW_DB_PORT!"
    set "DB_NAME=!IW_DB_NAME!"
    set "DB_USER=!IW_DB_USER!"
    set "DB_PASSWORD=!IW_DB_PASSWORD!"
    set "CONFIG_TEMPLATE=%SCRIPT_DIR%docs\authentication\config.xml.hosted.template"
) else if /i "%DB_MODE%"=="local" (
    echo    Mode: OFFLINE ^(no database^)
    set "DB_HOST=localhost"
    set "DB_PORT=3306"
    set "DB_NAME=none"
    set "DB_USER=none"
    set "DB_PASSWORD=none"
    set "CONFIG_TEMPLATE=%SCRIPT_DIR%docs\authentication\config.xml.local.template"
) else if /i "%DB_MODE%"=="oracle_cloud" (
    echo    Mode: LEGACY SELF-MANAGED MYSQL ^(historical alias^)
    if not defined ORACLE_DB_HOST set "ORACLE_DB_HOST=129.153.47.225"
    if not defined ORACLE_DB_PORT set "ORACLE_DB_PORT=3306"
    if not defined ORACLE_DB_NAME set "ORACLE_DB_NAME=iw_ide"
    set "DB_HOST=!ORACLE_DB_HOST!"
    set "DB_PORT=!ORACLE_DB_PORT!"
    set "DB_NAME=!ORACLE_DB_NAME!"
    set "DB_USER=!ORACLE_DB_USER!"
    set "DB_PASSWORD=!ORACLE_DB_PASSWORD!"
    set "CONFIG_TEMPLATE=%SCRIPT_DIR%docs\authentication\config.xml.oracle_cloud.template"
) else (
    echo    [ERROR] Unsupported DB_MODE: %DB_MODE%
    echo    Supported modes: supabase, interweave, local
    echo    Legacy compatibility alias: oracle_cloud
    pause
    exit /b 1
)

echo.
echo    Server:   %DB_HOST%:%DB_PORT%
echo    Database: %DB_NAME%
echo    User:     %DB_USER%

if /i not "%DB_MODE%"=="local" (
    if "%DB_USER%"=="" (
        echo.
        echo    [ERROR] Database user is missing in .env
        pause
        exit /b 1
    )
    if "%DB_PASSWORD%"=="" (
        echo.
        echo    [ERROR] Database password is missing in .env
        pause
        exit /b 1
    )
)

if /i "%DB_MODE%"=="supabase" (
    if /i "%DB_PASSWORD%"=="YOUR_SUPABASE_PASSWORD_HERE" (
        echo.
        echo    [ERROR] Supabase password placeholder detected in .env
        echo    Edit .env and re-run this script.
        pause
        exit /b 1
    )
)

REM === Configure Tomcat context.xml ===
echo.
echo  [Step 3 of 3] Applying configuration...
echo.

if /i "%DB_MODE%"=="supabase" (
    set "CONTEXT_TEMPLATE=%SCRIPT_DIR%web_portal\tomcat\conf\context.xml.postgres"
) else (
    set "CONTEXT_TEMPLATE=%SCRIPT_DIR%web_portal\tomcat\conf\context.xml.mysql"
)
set "CONTEXT_FILE=%SCRIPT_DIR%web_portal\tomcat\conf\context.xml"
set "BD_CONFIG=%SCRIPT_DIR%web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\config.xml"

if /i not "%DB_MODE%"=="local" (
    if exist "%CONTEXT_TEMPLATE%" (
        powershell -Command "$c = Get-Content '%CONTEXT_TEMPLATE%' -Raw; $c = $c -replace '__DB_HOST__', '%DB_HOST%'; $c = $c -replace '__DB_PORT__', '%DB_PORT%'; $c = $c -replace '__DB_NAME__', '%DB_NAME%'; $c = $c -replace '__DB_USER__', '%DB_USER%'; $c = $c -replace '__DB_PASSWORD__', '%DB_PASSWORD%'; Set-Content '%CONTEXT_FILE%' -Value $c"
        echo    [OK] Tomcat database connection configured
    ) else (
        echo    [WARN] context.xml.mysql template not found
    )
)

REM Configure Business Daemon config.xml
if exist "%CONFIG_TEMPLATE%" (
    powershell -Command "$c = Get-Content '%CONFIG_TEMPLATE%' -Raw; $c = $c -replace 'YOUR_ORACLE_PASSWORD_HERE', '%DB_PASSWORD%'; $c = $c -replace '__LEGACY_MYSQL_HOST__', '%DB_HOST%'; $c = $c -replace '__LEGACY_MYSQL_PORT__', '%DB_PORT%'; $c = $c -replace '__LEGACY_MYSQL_DB_NAME__', '%DB_NAME%'; $c = $c -replace '__LEGACY_MYSQL_USER__', '%DB_USER%'; $c = $c -replace '__LEGACY_MYSQL_PASSWORD__', '%DB_PASSWORD%'; $c = $c -replace 'YOUR_IW_USERNAME', '%DB_USER%'; $c = $c -replace 'YOUR_IW_PASSWORD', '%DB_PASSWORD%'; Set-Content '%BD_CONFIG%' -Value $c"
    echo    [OK] Login system configured
) else (
    echo    [WARN] Config template not found: %CONFIG_TEMPLATE%
)

REM Check JDBC driver
if /i "%DB_MODE%"=="supabase" (
    dir /b "%SCRIPT_DIR%web_portal\tomcat\lib\postgresql-*.jar" >nul 2>&1
    if errorlevel 1 (
        echo    [WARN] PostgreSQL driver missing from tomcat/lib/
    ) else (
        echo    [OK] PostgreSQL driver present
    )
) else if /i "%DB_MODE%"=="interweave" (
    if exist "%SCRIPT_DIR%web_portal\tomcat\lib\mysql-connector-java-8.0.33.jar" (
        echo    [OK] MySQL driver present
    ) else (
        echo    [WARN] MySQL driver missing from tomcat/lib/
    )
) else if /i "%DB_MODE%"=="oracle_cloud" (
    if exist "%SCRIPT_DIR%web_portal\tomcat\lib\mysql-connector-java-8.0.33.jar" (
        echo    [OK] MySQL driver present
    ) else (
        echo    [WARN] MySQL driver missing from tomcat/lib/
    )
)

echo.
echo  =====================================================================
echo.
echo       SETUP COMPLETE!
echo.
echo  =====================================================================
echo.
echo   Your IW_IDE is now configured.
echo.
echo   Database Mode: %DB_MODE%
if /i "%DB_MODE%"=="local" (
    echo   Login with:    __iw_admin__ / %%iwps%%
) else (
    echo   Server:        %DB_HOST%
)
echo.
echo  ---------------------------------------------------------------------
echo   NEXT STEPS:
echo  ---------------------------------------------------------------------
echo.
echo   1. Double-click START.bat in the repo root to launch everything
echo.
echo   OR use individual scripts:
echo      - start_webportal.bat  = Start web server only
echo      - start_ide.bat        = Start IDE only
echo      - ..\CHANGE_DATABASE.bat  = Change database connection
echo.
echo  =====================================================================
echo.
choice /c YN /m "  Start the application now"
if errorlevel 2 goto :done
if errorlevel 1 goto :start_now

:start_now
echo.
echo  Starting...
start "" "%SCRIPT_DIR%..\START.bat"
goto :done

:done
echo.
echo  Press any key to close...
pause >nul
endlocal
