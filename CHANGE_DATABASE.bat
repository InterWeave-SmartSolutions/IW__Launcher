@echo off
REM =============================================================================
REM IW_IDE DATABASE SWITCHER
REM =============================================================================
REM Double-click this file to switch between database connections
REM =============================================================================

setlocal enabledelayedexpansion
title IW_IDE - Switch Database Connection
mode con: cols=70 lines=35

set "SCRIPT_DIR=%~dp0"
cd /d "%SCRIPT_DIR%"

REM Check if .env exists
if not exist ".env" (
    echo.
    echo  ERROR: .env file not found!
    echo.
    echo  Please run SETUP_DB_Windows.bat first.
    echo.
    pause
    exit /b 1
)

:menu
cls
echo.
echo  ====================================================================
echo.
echo       IW_IDE DATABASE CONNECTION SWITCHER
echo.
echo  ====================================================================
echo.

REM Read current DB_MODE from .env using findstr
set "CURRENT_MODE=unknown"
for /f "tokens=2 delims==" %%a in ('findstr /B "DB_MODE=" ".env"') do set "CURRENT_MODE=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "ORACLE_DB_HOST=" ".env"') do set "ORACLE_HOST=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "IW_DB_HOST=" ".env"') do set "IW_HOST=%%a"

if not defined ORACLE_HOST set "ORACLE_HOST=129.153.47.225"
if not defined IW_HOST set "IW_HOST=148.62.63.8"

REM Display current connection with clear visual
echo    CURRENTLY CONNECTED TO:
echo.
if /i "%CURRENT_MODE%"=="oracle_cloud" (
    echo      -------------------------------------------------------
    echo      ^|                                                     ^|
    echo      ^|   [ACTIVE]  YOUR SERVER  ^(Oracle Cloud^)             ^|
    echo      ^|             %ORACLE_HOST%                       ^|
    echo      ^|                                                     ^|
    echo      -------------------------------------------------------
) else if /i "%CURRENT_MODE%"=="interweave" (
    echo      -------------------------------------------------------
    echo      ^|                                                     ^|
    echo      ^|   [ACTIVE]  INTERWEAVE SERVER                       ^|
    echo      ^|             %IW_HOST%                          ^|
    echo      ^|                                                     ^|
    echo      -------------------------------------------------------
) else if /i "%CURRENT_MODE%"=="local" (
    echo      -------------------------------------------------------
    echo      ^|                                                     ^|
    echo      ^|   [ACTIVE]  OFFLINE MODE  ^(No Database^)             ^|
    echo      ^|             Admin login only                        ^|
    echo      ^|                                                     ^|
    echo      -------------------------------------------------------
) else (
    echo      -------------------------------------------------------
    echo      ^|                                                     ^|
    echo      ^|   [NOT SET]  Please select a connection below       ^|
    echo      ^|                                                     ^|
    echo      -------------------------------------------------------
)

echo.
echo  ====================================================================
echo    CHOOSE A CONNECTION:
echo  ====================================================================
echo.
echo    1.  YOUR SERVER ^(Oracle Cloud^)
echo        Best for development and testing
echo.
echo    2.  INTERWEAVE SERVER
echo        Connect to InterWeave's server ^(may not work externally^)
echo.
echo    3.  OFFLINE MODE
echo        Work without database ^(admin login only^)
echo.
echo  --------------------------------------------------------------------
echo    OTHER OPTIONS:
echo  --------------------------------------------------------------------
echo.
echo    4.  APPLY CHANGES ^(Restart Web Server^)
echo.
echo    5.  EXIT
echo.
echo  ====================================================================
echo.

choice /c 12345 /n /m "  Enter your choice (1-5): "

if errorlevel 5 goto quit
if errorlevel 4 goto restart
if errorlevel 3 goto switch_local
if errorlevel 2 goto switch_interweave
if errorlevel 1 goto switch_oracle
goto menu

:switch_oracle
cls
echo.
echo  ====================================================================
echo    SWITCHING TO: YOUR SERVER ^(Oracle Cloud^)
echo  ====================================================================
echo.
echo    Please wait...
echo.

REM Update DB_MODE in .env
powershell -Command "(Get-Content '.env') -replace '^DB_MODE=.*', 'DB_MODE=oracle_cloud' | Set-Content '.env'"
if errorlevel 1 (
    echo    [ERROR] Failed to update configuration
    pause
    goto menu
)
echo    [OK] Configuration updated
echo.

REM Load credentials from .env
for /f "tokens=2 delims==" %%a in ('findstr /B "ORACLE_DB_HOST=" ".env"') do set "DB_HOST=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "ORACLE_DB_PORT=" ".env"') do set "DB_PORT=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "ORACLE_DB_NAME=" ".env"') do set "DB_NAME=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "ORACLE_DB_USER=" ".env"') do set "DB_USER=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "ORACLE_DB_PASSWORD=" ".env"') do set "DB_PASSWORD=%%a"

if not defined DB_HOST set "DB_HOST=129.153.47.225"
if not defined DB_PORT set "DB_PORT=3306"
if not defined DB_NAME set "DB_NAME=iw_ide"

REM Update context.xml
set "CONTEXT_TEMPLATE=%SCRIPT_DIR%web_portal\tomcat\conf\context.xml.mysql"
set "CONTEXT_FILE=%SCRIPT_DIR%web_portal\tomcat\conf\context.xml"
if exist "%CONTEXT_TEMPLATE%" (
    powershell -Command "$c = Get-Content '%CONTEXT_TEMPLATE%' -Raw; $c = $c -replace '__DB_HOST__', '%DB_HOST%'; $c = $c -replace '__DB_PORT__', '%DB_PORT%'; $c = $c -replace '__DB_NAME__', '%DB_NAME%'; $c = $c -replace '__DB_USER__', '%DB_USER%'; $c = $c -replace '__DB_PASSWORD__', '%DB_PASSWORD%'; Set-Content '%CONTEXT_FILE%' -Value $c"
    echo    [OK] Server connection configured
) else (
    echo    [SKIP] context.xml.mysql template not found
)

REM Update Business Daemon config.xml
set "CONFIG_TEMPLATE=%SCRIPT_DIR%docs\authentication\config.xml.oracle_cloud.template"
set "BD_CONFIG=%SCRIPT_DIR%web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\config.xml"
if exist "%CONFIG_TEMPLATE%" (
    powershell -Command "$c = Get-Content '%CONFIG_TEMPLATE%' -Raw; $c = $c -replace 'YOUR_ORACLE_PASSWORD_HERE', '%DB_PASSWORD%'; Set-Content '%BD_CONFIG%' -Value $c"
    echo    [OK] Login system configured
) else (
    echo    [SKIP] config.xml.oracle_cloud.template not found
)

echo.
echo  ====================================================================
echo    SUCCESS!
echo  ====================================================================
echo.
echo    You are now set to connect to YOUR SERVER.
echo.
echo    Server Address: %DB_HOST%
echo.
echo    IMPORTANT: You must restart the web server for
echo               changes to take effect.
echo.
echo  ====================================================================
echo.
echo    Press any key to go back to menu...
pause >nul
goto menu

:switch_interweave
cls
echo.
echo  ====================================================================
echo    SWITCHING TO: INTERWEAVE SERVER
echo  ====================================================================
echo.
echo    NOTE: InterWeave's server may block external connections.
echo          If you cannot log in after switching, try YOUR SERVER.
echo.
echo    Please wait...
echo.

REM Update DB_MODE in .env
powershell -Command "(Get-Content '.env') -replace '^DB_MODE=.*', 'DB_MODE=interweave' | Set-Content '.env'"
if errorlevel 1 (
    echo    [ERROR] Failed to update configuration
    pause
    goto menu
)
echo    [OK] Configuration updated
echo.

REM Load credentials from .env
for /f "tokens=2 delims==" %%a in ('findstr /B "IW_DB_HOST=" ".env"') do set "DB_HOST=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "IW_DB_PORT=" ".env"') do set "DB_PORT=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "IW_DB_NAME=" ".env"') do set "DB_NAME=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "IW_DB_USER=" ".env"') do set "DB_USER=%%a"
for /f "tokens=2 delims==" %%a in ('findstr /B "IW_DB_PASSWORD=" ".env"') do set "DB_PASSWORD=%%a"

if not defined DB_HOST set "DB_HOST=148.62.63.8"
if not defined DB_PORT set "DB_PORT=3306"
if not defined DB_NAME set "DB_NAME=hostedprofiles"

REM Update context.xml
set "CONTEXT_TEMPLATE=%SCRIPT_DIR%web_portal\tomcat\conf\context.xml.mysql"
set "CONTEXT_FILE=%SCRIPT_DIR%web_portal\tomcat\conf\context.xml"
if exist "%CONTEXT_TEMPLATE%" (
    powershell -Command "$c = Get-Content '%CONTEXT_TEMPLATE%' -Raw; $c = $c -replace '__DB_HOST__', '%DB_HOST%'; $c = $c -replace '__DB_PORT__', '%DB_PORT%'; $c = $c -replace '__DB_NAME__', '%DB_NAME%'; $c = $c -replace '__DB_USER__', '%DB_USER%'; $c = $c -replace '__DB_PASSWORD__', '%DB_PASSWORD%'; Set-Content '%CONTEXT_FILE%' -Value $c"
    echo    [OK] Server connection configured
) else (
    echo    [SKIP] context.xml.mysql template not found
)

REM Update Business Daemon config.xml
set "CONFIG_TEMPLATE=%SCRIPT_DIR%docs\authentication\config.xml.hosted.template"
set "BD_CONFIG=%SCRIPT_DIR%web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\config.xml"
if exist "%CONFIG_TEMPLATE%" (
    powershell -Command "$c = Get-Content '%CONFIG_TEMPLATE%' -Raw; $c = $c -replace 'YOUR_IW_USERNAME', '%DB_USER%'; $c = $c -replace 'YOUR_IW_PASSWORD', '%DB_PASSWORD%'; Set-Content '%BD_CONFIG%' -Value $c"
    echo    [OK] Login system configured
) else (
    echo    [SKIP] config.xml.hosted.template not found
)

echo.
echo  ====================================================================
echo    SUCCESS!
echo  ====================================================================
echo.
echo    You are now set to connect to INTERWEAVE SERVER.
echo.
echo    Server Address: %DB_HOST%
echo.
echo    IMPORTANT: You must restart the web server for
echo               changes to take effect.
echo.
echo    If login fails, come back here and choose YOUR SERVER instead.
echo.
echo  ====================================================================
echo.
echo    Press any key to go back to menu...
pause >nul
goto menu

:switch_local
cls
echo.
echo  ====================================================================
echo    SWITCHING TO: OFFLINE MODE
echo  ====================================================================
echo.
echo    This mode works without any database connection.
echo    Only the admin account will work:
echo.
echo        Username:  __iw_admin__
echo        Password:  %%iwps%%
echo.
echo    Please wait...
echo.

REM Update DB_MODE in .env
powershell -Command "(Get-Content '.env') -replace '^DB_MODE=.*', 'DB_MODE=local' | Set-Content '.env'"
if errorlevel 1 (
    echo    [ERROR] Failed to update configuration
    pause
    goto menu
)
echo    [OK] Configuration updated
echo.

REM Update Business Daemon config.xml to IsHosted=0
set "CONFIG_TEMPLATE=%SCRIPT_DIR%docs\authentication\config.xml.local.template"
set "BD_CONFIG=%SCRIPT_DIR%web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\config.xml"
if exist "%CONFIG_TEMPLATE%" (
    copy /y "%CONFIG_TEMPLATE%" "%BD_CONFIG%" >nul
    echo    [OK] Login system configured for offline mode
) else (
    echo    [SKIP] config.xml.local.template not found
)

echo.
echo  ====================================================================
echo    SUCCESS!
echo  ====================================================================
echo.
echo    You are now set to OFFLINE MODE ^(no database^).
echo.
echo    Login with:
echo        Username:  __iw_admin__
echo        Password:  %%iwps%%
echo.
echo    IMPORTANT: You must restart the web server for
echo               changes to take effect.
echo.
echo  ====================================================================
echo.
echo    Press any key to go back to menu...
pause >nul
goto menu

:restart
cls
echo.
echo  ====================================================================
echo    RESTARTING WEB SERVER
echo  ====================================================================
echo.
echo    This will apply your changes.
echo.
echo    Step 1: Stopping current server...
if exist "%SCRIPT_DIR%stop_webportal.bat" (
    call "%SCRIPT_DIR%stop_webportal.bat" 2>nul
    echo            Done.
) else (
    echo            stop_webportal.bat not found - skipping
)
echo.
echo    Step 2: Waiting for shutdown...
timeout /t 3 /nobreak >nul
echo            Done.
echo.
echo    Step 3: Starting server...
if exist "%SCRIPT_DIR%start_webportal.bat" (
    start "" cmd /c "%SCRIPT_DIR%start_webportal.bat"
    echo            Started!
) else (
    echo            start_webportal.bat not found!
)
echo.
echo  ====================================================================
echo    WEB SERVER IS RESTARTING
echo  ====================================================================
echo.
echo    A browser window will open automatically when ready.
echo.
echo    If the browser doesn't open, go to:
echo        http://localhost:8080/iw-business-daemon/IWLogin.jsp
echo.
echo  ====================================================================
echo.
echo    Press any key to return to menu...
pause >nul
goto menu

:quit
cls
echo.
echo  ====================================================================
echo    GOODBYE!
echo  ====================================================================
echo.
echo    Remember: If you made changes, restart the web server
echo              for them to take effect.
echo.
echo  ====================================================================
echo.
timeout /t 2 /nobreak >nul
endlocal
exit /b 0
