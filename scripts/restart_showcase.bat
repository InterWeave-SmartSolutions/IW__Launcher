@echo off
setlocal EnableDelayedExpansion
REM ============================================================
REM  restart_showcase.bat
REM  Restarts both showcase services:
REM    1. Cloudflared Windows Service
REM    2. IWPortal-Tomcat Scheduled Task
REM  Requires admin rights - auto-elevates via UAC if needed.
REM ============================================================

for %%i in ("%~dp0..") do set "REPO_ROOT=%%~fi"
set "TASK_NAME=IWPortal-Tomcat"

echo.
echo ============================================================
echo   IW Portal - Showcase Restart
echo ============================================================
echo.

REM ---- Check / acquire admin rights --------------------------
net session >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ADMIN] Not running as administrator. Elevating via UAC...
    echo.
    powershell.exe -NoProfile -ExecutionPolicy Bypass -Command ^
        "Start-Process -FilePath '%~f0' -Verb RunAs"
    exit /b 0
)
echo [ADMIN] Running as administrator. Continuing.
echo.

REM ---- Step 1: Stop Cloudflared service ----------------------
echo [1/4] STOPPING Cloudflared service
echo -------------------------------------------------------
sc query Cloudflared >nul 2>&1
if !ERRORLEVEL! NEQ 0 (
    echo   WARNING: Service "Cloudflared" not found. Skipping stop.
) else (
    for /f "tokens=4 delims=: " %%s in ('sc query Cloudflared 2^>nul ^| findstr /i "STATE"') do set "CF_STATE=%%s"
    if /i "!CF_STATE!"=="STOPPED" (
        echo   Cloudflared is already STOPPED.
    ) else (
        echo   Sending stop signal to Cloudflared...
        sc stop Cloudflared
        REM Wait up to 10s for STOPPED state
        set /a _wait=0
        :cf_stop_loop
        if !_wait! GEQ 10 (
            echo   WARNING: Cloudflared did not stop within 10 seconds. Current state: !CF_STATE!
            goto :cf_stop_done
        )
        timeout /t 1 /nobreak >nul
        for /f "tokens=4 delims=: " %%s in ('sc query Cloudflared 2^>nul ^| findstr /i "STATE"') do set "CF_STATE=%%s"
        if /i "!CF_STATE!"=="STOPPED" goto :cf_stop_done
        set /a _wait+=1
        goto :cf_stop_loop
        :cf_stop_done
        echo   Cloudflared state: !CF_STATE!
    )
)
echo.

REM ---- Step 2: Stop Tomcat task + kill lingering java.exe ----
echo [2/4] STOPPING Tomcat (task + java.exe)
echo -------------------------------------------------------
schtasks /query /tn "%TASK_NAME%" >nul 2>&1
if !ERRORLEVEL! NEQ 0 (
    echo   WARNING: Task "%TASK_NAME%" not found. Skipping task stop.
) else (
    echo   Ending scheduled task "%TASK_NAME%"...
    schtasks /end /tn "%TASK_NAME%" >nul 2>&1
    echo   Task end signal sent.
)

echo   Killing any lingering java.exe processes running Tomcat bootstrap...
powershell.exe -NoProfile -ExecutionPolicy Bypass -Command ^
    "$ErrorActionPreference = 'SilentlyContinue';" ^
    "Get-Process -Name java -ErrorAction SilentlyContinue |" ^
    "Where-Object { try { (Get-WmiObject Win32_Process -Filter ('ProcessId=' + $_.Id)).CommandLine -match 'bootstrap\.jar' } catch { $false } } |" ^
    "ForEach-Object {" ^
    "  Write-Host ('  Killing java.exe PID ' + $_.Id + ' ...');" ^
    "  Stop-Process -Id $_.Id -Force -ErrorAction SilentlyContinue" ^
    "}"
echo   Done killing Tomcat java.exe processes (if any).
echo.

REM ---- Step 3: Start Cloudflared service ---------------------
echo [3/4] STARTING Cloudflared service
echo -------------------------------------------------------
sc query Cloudflared >nul 2>&1
if !ERRORLEVEL! NEQ 0 (
    echo   WARNING: Cloudflared service not found. Run SETUP_SHOWCASE.bat first.
) else (
    echo   Starting Cloudflared...
    sc start Cloudflared
    if !ERRORLEVEL! NEQ 0 (
        echo   WARNING: sc start returned non-zero (may already be starting).
    )
    REM Wait up to 8s for RUNNING
    set /a _wait=0
    :cf_start_loop
    if !_wait! GEQ 8 goto :cf_start_done
    timeout /t 1 /nobreak >nul
    for /f "tokens=4 delims=: " %%s in ('sc query Cloudflared 2^>nul ^| findstr /i "STATE"') do set "CF_STATE=%%s"
    if /i "!CF_STATE!"=="RUNNING" goto :cf_start_done
    set /a _wait+=1
    goto :cf_start_loop
    :cf_start_done
    echo   Cloudflared state: !CF_STATE!
)
echo.

REM ---- Step 4: Start Tomcat task -----------------------------
echo [4/4] STARTING Tomcat task
echo -------------------------------------------------------
schtasks /query /tn "%TASK_NAME%" >nul 2>&1
if !ERRORLEVEL! NEQ 0 (
    echo   WARNING: Task "%TASK_NAME%" not found. Run SETUP_SHOWCASE.bat first.
) else (
    echo   Starting scheduled task "%TASK_NAME%"...
    schtasks /run /tn "%TASK_NAME%"
    if !ERRORLEVEL! NEQ 0 (
        echo   WARNING: schtasks /run returned non-zero (task may already be running).
    ) else (
        echo   Task start requested.
    )
)
echo.

REM ---- Wait + run status check --------------------------------
echo   Waiting 5 seconds for services to initialize...
timeout /t 5 /nobreak >nul
echo.

echo ============================================================
echo   Running status_showcase.bat for final status...
echo ============================================================
echo.
call "%REPO_ROOT%\scripts\status_showcase.bat"

endlocal
