@echo off
setlocal EnableDelayedExpansion
REM ============================================================
REM  SETUP_SHOWCASE.bat
REM  One-click demo showcase setup:
REM    1. Cloudflare Tunnel (Windows Service, auto-start)
REM    2. Tomcat (Scheduled Task, AtStartup, auto-restart)
REM  Requires admin rights - auto-elevates via UAC if needed.
REM ============================================================

for %%i in ("%~dp0..") do set "REPO_ROOT=%%~fi"
set "JAVA_EXE=%REPO_ROOT%\jre\bin\java.exe"
set "CATALINA_HOME=%REPO_ROOT%\web_portal\tomcat"
set "LOGS_DIR=%REPO_ROOT%\logs"
set "TASK_NAME=IWPortal-Tomcat"
set "CF_CONFIG=%USERPROFILE%\.cloudflared\config.yml"

echo.
echo ============================================================
echo   IW Portal - Demo Showcase Setup
echo ============================================================
echo.

REM ---- Step 0: Check / acquire admin rights ------------------
net session >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ADMIN] Not running as administrator. Elevating via UAC...
    echo.
    powershell.exe -NoProfile -ExecutionPolicy Bypass -Command ^
        "Start-Process -FilePath '%~f0' -Verb RunAs"
    exit /b 0
)
echo [ADMIN] Running as administrator. Continuing setup.
echo.

REM ---- Step 1: Cloudflare config -----------------------------
echo [1/5] CLOUDFLARE TUNNEL CONFIG
echo -------------------------------------------------------
if exist "%CF_CONFIG%" (
    echo   config.yml found: %CF_CONFIG%
    echo   Skipping tunnel login/create.
) else (
    echo   No config.yml found at %CF_CONFIG%
    echo   Running setup_cloudflare_tunnel.bat ...
    echo.
    call "%REPO_ROOT%\scripts\setup_cloudflare_tunnel.bat"
    if !ERRORLEVEL! NEQ 0 (
        echo.
        echo   ERROR: Cloudflare tunnel setup failed. Aborting.
        goto :end_error
    )
    echo.
    echo   Cloudflare tunnel configured successfully.
)
echo.

REM ---- Step 2: Cloudflared Windows Service -------------------
echo [2/5] CLOUDFLARED WINDOWS SERVICE
echo -------------------------------------------------------

sc query Cloudflared >nul 2>&1
if !ERRORLEVEL! NEQ 0 (
    echo   Service "Cloudflared" not found. Installing...
    cloudflared service install
    if !ERRORLEVEL! NEQ 0 (
        echo   ERROR: cloudflared service install failed.
        goto :end_error
    )
    echo   Service installed.
) else (
    echo   Service "Cloudflared" already exists.
)

echo   Setting Cloudflared to auto-start (start=auto)...
sc config Cloudflared start=auto
if !ERRORLEVEL! NEQ 0 (
    echo   WARNING: Could not set auto-start on Cloudflared service.
)

REM Check current state and start if not running
for /f "tokens=4 delims=: " %%s in ('sc query Cloudflared ^| findstr /i "STATE"') do (
    set "CF_STATE=%%s"
)
if /i "!CF_STATE!"=="RUNNING" (
    echo   Cloudflared is already RUNNING.
) else (
    echo   Starting Cloudflared service...
    sc start Cloudflared
    if !ERRORLEVEL! NEQ 0 (
        echo   WARNING: sc start returned non-zero. Service may already be starting.
    )
    REM Wait up to 8s for it to reach RUNNING
    set /a _wait=0
    :cf_wait_loop
    if !_wait! GEQ 8 goto :cf_wait_done
    timeout /t 1 /nobreak >nul
    for /f "tokens=4 delims=: " %%s in ('sc query Cloudflared ^| findstr /i "STATE"') do set "CF_STATE=%%s"
    if /i "!CF_STATE!"=="RUNNING" goto :cf_wait_done
    set /a _wait+=1
    goto :cf_wait_loop
    :cf_wait_done
    echo   Cloudflared state: !CF_STATE!
)
echo.

REM ---- Step 3: Tomcat Scheduled Task -------------------------
echo [3/5] TOMCAT SCHEDULED TASK
echo -------------------------------------------------------
echo   Registering task "%TASK_NAME%" via PowerShell...
echo.

REM Build the Java command arguments for Tomcat
set "JAVA_ARGS=-Xverify:none -Xms256m -Xmx512m"
set "JAVA_ARGS=%JAVA_ARGS% -Djava.util.logging.config.file=%CATALINA_HOME%\conf\logging.properties"
set "JAVA_ARGS=%JAVA_ARGS% -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager"
set "JAVA_ARGS=%JAVA_ARGS% -classpath %CATALINA_HOME%\bin\bootstrap.jar;%CATALINA_HOME%\bin\tomcat-juli.jar"
set "JAVA_ARGS=%JAVA_ARGS% -Dcatalina.base=%CATALINA_HOME%"
set "JAVA_ARGS=%JAVA_ARGS% -Dcatalina.home=%CATALINA_HOME%"
set "JAVA_ARGS=%JAVA_ARGS% -Djava.io.tmpdir=%CATALINA_HOME%\temp"
set "JAVA_ARGS=%JAVA_ARGS% org.apache.catalina.startup.Bootstrap start"

powershell.exe -NoProfile -ExecutionPolicy Bypass -Command ^
    "$ErrorActionPreference = 'Stop';" ^
    "try {" ^
    "  $javaExe  = '%REPO_ROOT%\jre\bin\java.exe';" ^
    "  $catHome  = '%REPO_ROOT%\web_portal\tomcat';" ^
    "  $logDir   = '%REPO_ROOT%\logs';" ^
    "  $javaArgs = @(" ^
    "    '-Xverify:none', '-Xms256m', '-Xmx512m'," ^
    "    ('-Djava.util.logging.config.file=' + $catHome + '\conf\logging.properties')," ^
    "    '-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager'," ^
    "    '-classpath'," ^
    "    ($catHome + '\bin\bootstrap.jar;' + $catHome + '\bin\tomcat-juli.jar')," ^
    "    ('-Dcatalina.base=' + $catHome)," ^
    "    ('-Dcatalina.home=' + $catHome)," ^
    "    ('-Djava.io.tmpdir=' + $catHome + '\temp')," ^
    "    'org.apache.catalina.startup.Bootstrap', 'start'" ^
    "  );" ^
    "  $action   = New-ScheduledTaskAction -Execute $javaExe -Argument ($javaArgs -join ' ') -WorkingDirectory $catHome;" ^
    "  $trigger  = New-ScheduledTaskTrigger -AtStartup;" ^
    "  $settings = New-ScheduledTaskSettingsSet -ExecutionTimeLimit ([TimeSpan]::Zero) -RestartCount 10 -RestartInterval (New-TimeSpan -Minutes 1) -MultipleInstances IgnoreNew -StartWhenAvailable;" ^
    "  $principal = New-ScheduledTaskPrincipal -UserId 'SYSTEM' -LogonType ServiceAccount -RunLevel Highest;" ^
    "  $params   = @{" ^
    "    TaskName  = 'IWPortal-Tomcat';" ^
    "    Action    = $action;" ^
    "    Trigger   = $trigger;" ^
    "    Settings  = $settings;" ^
    "    Principal = $principal;" ^
    "    Force     = $true" ^
    "  };" ^
    "  Register-ScheduledTask @params | Out-Null;" ^
    "  Write-Host '  Task registered successfully.';" ^
    "} catch {" ^
    "  Write-Host ('  ERROR: ' + $_);" ^
    "  exit 1" ^
    "}"

if !ERRORLEVEL! NEQ 0 (
    echo.
    echo   ERROR: Failed to register scheduled task. Aborting.
    goto :end_error
)
echo.

REM ---- Step 4: Start Tomcat task immediately -----------------
echo [4/5] STARTING TOMCAT NOW
echo -------------------------------------------------------
echo   Launching task "%TASK_NAME%"...
schtasks /run /tn "%TASK_NAME%"
if !ERRORLEVEL! NEQ 0 (
    echo   WARNING: schtasks /run returned non-zero (task may already be running).
) else (
    echo   Task start requested.
)
echo   Waiting 5 seconds for Tomcat to initialize...
timeout /t 5 /nobreak >nul
echo.

REM ---- Step 5: Status summary --------------------------------
echo [5/5] STATUS SUMMARY
echo -------------------------------------------------------

REM Cloudflared service state
for /f "tokens=4 delims=: " %%s in ('sc query Cloudflared 2^>nul ^| findstr /i "STATE"') do set "CF_STATE=%%s"
if "!CF_STATE!"=="" set "CF_STATE=NOT FOUND"
echo   Cloudflared service : !CF_STATE!

REM Tomcat task state
for /f "tokens=2 delims=:" %%s in ('schtasks /query /tn "%TASK_NAME%" /fo LIST 2^>nul ^| findstr /i "Status"') do (
    set "TC_STATE=%%s"
    set "TC_STATE=!TC_STATE: =!"
)
if "!TC_STATE!"=="" set "TC_STATE=NOT FOUND"
echo   IWPortal-Tomcat task: !TC_STATE!

REM Tomcat HTTP check
set "TC_HTTP=unreachable"
for /f %%c in ('curl -s -o nul -w "%%{http_code}" --max-time 5 http://localhost:9090/iw-business-daemon/api/auth/session 2^>nul') do set "TC_HTTP=%%c"
echo   Tomcat HTTP (9090)  : HTTP !TC_HTTP!

REM Tunnel URL
set "TUNNEL_URL=(not configured)"
if exist "%LOGS_DIR%\cloudflare_tunnel_url.txt" (
    set /p TUNNEL_URL=<"%LOGS_DIR%\cloudflare_tunnel_url.txt"
)
echo   Cloudflare tunnel   : !TUNNEL_URL!
echo   Vercel URL          : https://iw-portal.vercel.app

echo.
echo ============================================================
echo   REMINDER: If vercel.json was updated by tunnel setup,
echo   commit and push it so Vercel re-deploys the proxy route:
echo     git add frontends/iw-portal/vercel.json
echo     git commit -m "chore: update cloudflare tunnel URL"
echo     git push
echo ============================================================
echo.
echo   Setup complete. Both services will auto-start on reboot.
echo.
goto :end_ok

:end_error
echo.
echo   SETUP FAILED. Review errors above.
echo.
pause
exit /b 1

:end_ok
pause
endlocal
