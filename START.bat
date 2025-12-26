@echo off
REM =============================================================================
REM IW_IDE - START
REM =============================================================================
REM Double-click this to start the application.
REM First time? It will set up everything automatically.
REM =============================================================================

setlocal enabledelayedexpansion
title IW_IDE
mode con: cols=70 lines=30

set "IW_HOME=%~dp0"
if "%IW_HOME:~-1%"=="\" set "IW_HOME=%IW_HOME:~0,-1%"

set "JAVA_HOME=%IW_HOME%\jre"
set "JRE_HOME=%IW_HOME%\jre"
set "CATALINA_HOME=%IW_HOME%\web_portal\tomcat"

cls
echo.
echo  ==================================================================
echo.
echo       IW_IDE - InterWeave Integration Platform
echo.
echo  ==================================================================
echo.

REM ========== FIRST TIME SETUP (if needed) ==========
if not exist "%IW_HOME%\.env" (
    echo  First time setup - please wait...
    echo.

    if exist "%IW_HOME%\.env.example" (
        copy "%IW_HOME%\.env.example" "%IW_HOME%\.env" >nul
        echo  [OK] Configuration created
    ) else (
        echo  [ERROR] Configuration template missing!
        pause
        exit /b 1
    )

    REM Load defaults and configure
    for /f "tokens=1,* delims==" %%a in ('findstr /B "DB_MODE=" "%IW_HOME%\.env"') do set "DB_MODE=%%b"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_HOST=" "%IW_HOME%\.env"') do set "ORACLE_DB_HOST=%%b"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_PORT=" "%IW_HOME%\.env"') do set "ORACLE_DB_PORT=%%b"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_NAME=" "%IW_HOME%\.env"') do set "ORACLE_DB_NAME=%%b"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_USER=" "%IW_HOME%\.env"') do set "ORACLE_DB_USER=%%b"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "ORACLE_DB_PASSWORD=" "%IW_HOME%\.env"') do set "ORACLE_DB_PASSWORD=%%b"

    if not defined DB_MODE set "DB_MODE=oracle_cloud"
    if not defined ORACLE_DB_HOST set "ORACLE_DB_HOST=129.153.47.225"
    if not defined ORACLE_DB_PORT set "ORACLE_DB_PORT=3306"
    if not defined ORACLE_DB_NAME set "ORACLE_DB_NAME=iw_ide"

    set "DB_HOST=!ORACLE_DB_HOST!"
    set "DB_PORT=!ORACLE_DB_PORT!"
    set "DB_NAME=!ORACLE_DB_NAME!"
    set "DB_USER=!ORACLE_DB_USER!"
    set "DB_PASSWORD=!ORACLE_DB_PASSWORD!"

    REM Configure Tomcat
    set "CONTEXT_TEMPLATE=%IW_HOME%\web_portal\tomcat\conf\context.xml.mysql"
    set "CONTEXT_FILE=%IW_HOME%\web_portal\tomcat\conf\context.xml"

    if exist "!CONTEXT_TEMPLATE!" (
        powershell -Command "$c = Get-Content '!CONTEXT_TEMPLATE!' -Raw; $c = $c -replace '__DB_HOST__', '!DB_HOST!'; $c = $c -replace '__DB_PORT__', '!DB_PORT!'; $c = $c -replace '__DB_NAME__', '!DB_NAME!'; $c = $c -replace '__DB_USER__', '!DB_USER!'; $c = $c -replace '__DB_PASSWORD__', '!DB_PASSWORD!'; Set-Content '!CONTEXT_FILE!' -Value $c" 2>nul
        echo  [OK] Database configured
    )

    REM Configure Business Daemon
    set "CONFIG_TEMPLATE=%IW_HOME%\docs\authentication\config.xml.oracle_cloud.template"
    set "BD_CONFIG=%IW_HOME%\web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\config.xml"

    if exist "!CONFIG_TEMPLATE!" (
        powershell -Command "$c = Get-Content '!CONFIG_TEMPLATE!' -Raw; $c = $c -replace 'YOUR_ORACLE_PASSWORD_HERE', '!DB_PASSWORD!'; Set-Content '!BD_CONFIG!' -Value $c" 2>nul
        echo  [OK] Login system configured
    )

    echo.
    echo  Setup complete! Starting application...
    echo.
    timeout /t 2 >nul
)

REM ========== VERIFY REQUIREMENTS ==========
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo  [ERROR] Java not found!
    echo.
    pause
    exit /b 1
)

if not exist "%CATALINA_HOME%\bin\catalina.bat" (
    echo  [ERROR] Web server not found!
    echo.
    pause
    exit /b 1
)

REM ========== START APPLICATION ==========
echo  Starting...
echo.

cd /d "%CATALINA_HOME%\bin"
call startup.bat >nul 2>&1

echo  Waiting for server...
echo.
set /a counter=0
set /a max_wait=30

:wait_loop
timeout /t 2 /nobreak >nul
set /a counter+=1

powershell -Command "try { Invoke-WebRequest -Uri 'http://localhost:8080/' -UseBasicParsing -TimeoutSec 2 | Out-Null; exit 0 } catch { exit 1 }" >nul 2>&1
if %errorlevel%==0 goto server_ready

if %counter% geq %max_wait% goto open_browser

echo     (%counter%/%max_wait%)
goto wait_loop

:server_ready
echo  Server ready!
echo.

:open_browser
start "" "http://localhost:8080/iw-business-daemon/IWLogin.jsp"

REM Launch IDE
cd /d "%IW_HOME%"
if exist "%IW_HOME%\iw_ide.exe" (
    start "" "%IW_HOME%\iw_ide.exe"
)

echo  ==================================================================
echo.
echo       IW_IDE IS RUNNING
echo.
echo  ==================================================================
echo.
echo   Browser opened to login page.
echo.
echo   Login:
echo      Username: __iw_admin__
echo      Password: %%iwps%%
echo.
echo  ------------------------------------------------------------------
echo   To stop: Run STOP.bat or close this window
echo  ==================================================================
echo.
echo  Press any key to STOP and exit...
pause >nul

echo.
echo  Stopping...
cd /d "%CATALINA_HOME%\bin"
call shutdown.bat >nul 2>&1
echo  Stopped.
timeout /t 2 >nul

endlocal
