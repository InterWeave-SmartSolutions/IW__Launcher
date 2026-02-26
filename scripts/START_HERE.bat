@echo off
REM =============================================================================
REM IW_IDE MAIN LAUNCHER - DOUBLE-CLICK THIS TO START
REM =============================================================================
REM This starts the Web Portal, waits for it to be ready, then opens the browser.
REM =============================================================================

setlocal
title IW_IDE - Starting...
mode con: cols=75 lines=35

set "IW_HOME=%~dp0"
if "%IW_HOME:~-1%"=="\" set "IW_HOME=%IW_HOME:~0,-1%"

set "JAVA_HOME=%IW_HOME%\jre"
set "JRE_HOME=%IW_HOME%\jre"
set "CATALINA_HOME=%IW_HOME%\web_portal\tomcat"

cls
echo.
echo  =====================================================================
echo.
echo       IW_IDE - InterWeave Integration Platform
echo.
echo  =====================================================================
echo.
echo   Starting up... please wait.
echo.
echo  =====================================================================
echo.

REM Check if first-time setup is needed
if not exist ".env" (
    echo  [!] First time setup detected.
    echo.
    echo      Please run SETUP_DB_Windows.bat first to configure
    echo      your database connection.
    echo.
    echo  =====================================================================
    echo.
    echo  Press any key to exit...
    pause >nul
    exit /b 1
)

REM Verify JRE exists
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo  [ERROR] Java runtime not found!
    echo.
    echo          Expected location: %JAVA_HOME%
    echo.
    pause
    exit /b 1
)

REM Verify Tomcat exists
if not exist "%CATALINA_HOME%\bin\catalina.bat" (
    echo  [ERROR] Web server not found!
    echo.
    echo          Expected location: %CATALINA_HOME%
    echo.
    pause
    exit /b 1
)

echo  [1/3] Starting Web Portal...
cd /d "%CATALINA_HOME%\bin"
call startup.bat >nul 2>&1

echo.
echo  [2/3] Waiting for server to be ready...
echo.
set /a counter=0
set /a max_wait=30

:wait_loop
timeout /t 2 /nobreak >nul
set /a counter+=1

powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:9090/' -UseBasicParsing -TimeoutSec 2; exit 0 } catch { exit 1 }" >nul 2>&1
if %errorlevel%==0 goto server_ready

if %counter% geq %max_wait% (
    echo        Server is taking longer than expected...
    goto open_browser
)

echo        Waiting... (%counter%/%max_wait%)
goto wait_loop

:server_ready
echo.
echo  [3/3] Server ready! Opening browser...

:open_browser
start "" "http://localhost:9090/iw-business-daemon/IWLogin.jsp"

REM Launch IDE if it exists
cd /d "%IW_HOME%"
if exist "%IW_HOME%\iw_ide.exe" (
    echo.
    echo        Starting IDE...
    start "" "%IW_HOME%\iw_ide.exe"
)

echo.
echo  =====================================================================
echo.
echo       IW_IDE IS RUNNING
echo.
echo  =====================================================================
echo.
echo   Web Portal: http://localhost:9090/iw-business-daemon/IWLogin.jsp
echo.
echo   Login Credentials:
echo      Username: __iw_admin__
echo      Password: %%iwps%%
echo.
echo  ---------------------------------------------------------------------
echo   To stop, run: stop_webportal.bat
echo   Or close this window.
echo  =====================================================================
echo.
echo  Press any key to STOP the server and exit...
pause >nul

echo.
echo  Stopping server...
cd /d "%CATALINA_HOME%\bin"
call shutdown.bat >nul 2>&1
echo  Server stopped.
timeout /t 2 >nul

endlocal
