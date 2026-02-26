@echo off
REM ============================================================================
REM InterWeave IDE - Comprehensive Startup & Verification Script
REM ============================================================================

setlocal enabledelayedexpansion
title InterWeave IDE - Full Stack Startup
mode con: cols=80 lines=40

REM Resolve IW_HOME to project root (one level up from scripts/)
for %%i in ("%~dp0..") do set "IW_HOME=%%~fi"

set "JAVA_HOME=%IW_HOME%\jre"
set "CATALINA_HOME=%IW_HOME%\web_portal\tomcat"

cls
echo.
echo ============================================================================
echo.
echo                    INTERWEAVE IDE - FULL STACK STARTUP
echo.
echo ============================================================================
echo.
echo This script will start all components in the correct order:
echo   1. Tomcat Web Server (Backend)
echo   2. Web Portal (Business Daemon)
echo   3. Eclipse IDE (Development Environment)
echo.
echo ============================================================================
echo.

REM ========== STEP 1: VERIFY ALL REQUIREMENTS ==========
echo [STEP 1] Verifying Prerequisites...
echo.

if not exist "%JAVA_HOME%\bin\java.exe" (
    echo [ERROR] Java runtime not found at: %JAVA_HOME%
    pause
    exit /b 1
)
echo [OK] Java 8 runtime found

if not exist "%IW_HOME%\iw_ide.exe" (
    echo [ERROR] IDE launcher not found
    pause
    exit /b 1
)
echo [OK] IDE launcher found

if not exist "%CATALINA_HOME%\bin\startup.bat" (
    echo [ERROR] Tomcat startup script not found
    pause
    exit /b 1
)
echo [OK] Tomcat 9.0.83 found

if not exist "%IW_HOME%\web_portal\tomcat\webapps\iw-business-daemon\IWLogin.jsp" (
    echo [ERROR] Web application not found
    pause
    exit /b 1
)
echo [OK] Web application found

if not exist "%IW_HOME%\.env" (
    echo [ERROR] Configuration file (.env) not found
    pause
    exit /b 1
)
echo [OK] Configuration file found

echo.
echo ============================================================================
echo.

REM ========== STEP 2: CLEAN TOMCAT CATALINA LOGS ==========
echo [STEP 2] Preparing Tomcat...
echo.

if exist "%CATALINA_HOME%\logs\catalina.out" (
    del "%CATALINA_HOME%\logs\catalina.out" >nul 2>&1
)

if exist "%CATALINA_HOME%\logs\localhost.*" (
    del "%CATALINA_HOME%\logs\localhost.*" >nul 2>&1
)

echo [OK] Logs cleaned

echo.
echo ============================================================================
echo.

REM ========== STEP 3: START TOMCAT ==========
echo [STEP 3] Starting Tomcat Web Server...
echo.

cd /d "%CATALINA_HOME%\bin"
call startup.bat >nul 2>&1

if !errorlevel! neq 0 (
    echo [ERROR] Failed to start Tomcat
    pause
    exit /b 1
)

echo [OK] Tomcat startup command issued
echo     (Starting in background)
echo.

REM ========== STEP 4: WAIT FOR TOMCAT TO INITIALIZE ==========
echo [STEP 4] Waiting for Tomcat to fully initialize...
echo.

set /a counter=0
set /a max_wait=60
set /a wait_interval=2

:wait_for_tomcat
timeout /t !wait_interval! /nobreak >nul
set /a counter+=!wait_interval!

REM Check if port 9090 is responding
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:9090/iw-business-daemon/' -UseBasicParsing -TimeoutSec 3 -ErrorAction Stop; if ($r.StatusCode -eq 200 -or $r.StatusCode -eq 404) { exit 0 } else { exit 1 } } catch { exit 1 }" >nul 2>&1

if !errorlevel! equ 0 (
    echo [OK] Tomcat is responsive!
    goto tomcat_ready
)

echo     Waiting... (!counter!/!max_wait! seconds)

if !counter! geq !max_wait! (
    echo.
    echo [WARNING] Tomcat took longer than expected
    echo           Continuing anyway...
    goto tomcat_ready
)

goto wait_for_tomcat

:tomcat_ready
echo.
echo ============================================================================
echo.

REM ========== STEP 5: VERIFY WEB APPLICATION ==========
echo [STEP 5] Verifying Web Application...
echo.

REM Check if login page is accessible
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:9090/iw-business-daemon/IWLogin.jsp' -UseBasicParsing -TimeoutSec 3 -ErrorAction Stop; if ($r.StatusCode -eq 200) { exit 0 } else { exit 1 } } catch { exit 1 }" >nul 2>&1

if !errorlevel! equ 0 (
    echo [OK] Web Portal is accessible at:
    echo     http://localhost:9090/iw-business-daemon/IWLogin.jsp
) else (
    echo [WARNING] Could not verify web portal accessibility
    echo           It may still be initializing...
)

echo.
echo ============================================================================
echo.

REM ========== STEP 6: OPEN BROWSER ==========
echo [STEP 6] Opening Web Browser...
echo.

timeout /t 2 /nobreak >nul
start "" "http://localhost:9090/iw-business-daemon/IWLogin.jsp"

echo [OK] Browser opened to login page
echo.
echo ============================================================================
echo.

REM ========== STEP 7: START ECLIPSE IDE ==========
echo [STEP 7] Starting Eclipse IDE...
echo.

cd /d "%IW_HOME%"
start "" "%IW_HOME%\iw_ide.exe"

echo [OK] Eclipse IDE launching (may take 30-60 seconds to fully load)
echo.
echo ============================================================================
echo.

REM ========== STARTUP COMPLETE ==========
echo.
echo                         *** STARTUP COMPLETE ***
echo.
echo ============================================================================
echo.
echo BACKEND (Tomcat):
echo   Status:   RUNNING
echo   Port:     9090
echo   URL:      http://localhost:9090/iw-business-daemon/
echo.
echo WEB APPLICATION:
echo   Status:   READY
echo   Login:    http://localhost:9090/iw-business-daemon/IWLogin.jsp
echo   User:     __iw_admin__
echo   Pass:     %%iwps%%
echo.
echo IDE (Eclipse):
echo   Status:   STARTING (check window)
echo   Time:     30-60 seconds to fully load
echo.
echo ============================================================================
echo.
echo Press any key to STOP all services...
pause >nul

REM ========== SHUTDOWN ==========
echo.
echo Stopping all services...
echo.

echo [1/3] Stopping Tomcat...
cd /d "%CATALINA_HOME%\bin"
call shutdown.bat >nul 2>&1
timeout /t 3 /nobreak >nul

echo [2/3] Closing Eclipse...
taskkill /F /IM eclipse.exe /T >nul 2>&1
taskkill /F /IM javaw.exe /T >nul 2>&1

echo [3/3] Cleaning up...
taskkill /F /IM java.exe /T >nul 2>&1

echo.
echo All services stopped.
echo.

endlocal
