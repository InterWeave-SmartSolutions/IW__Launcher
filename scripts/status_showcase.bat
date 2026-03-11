@echo off
setlocal EnableDelayedExpansion
REM ============================================================
REM  status_showcase.bat
REM  Quick health check for IW Portal demo showcase.
REM  No admin rights required.
REM ============================================================

set "LOGS_DIR=C:\IW_IDE\IW_Launcher\logs"
set "TASK_NAME=IWPortal-Tomcat"

echo.
echo ============================================================
echo   IW Portal - Showcase Status Check
echo ============================================================
echo.

REM ---- Cloudflared Windows Service ---------------------------
echo [1/4] Cloudflared Service
echo -------------------------------------------------------
sc query Cloudflared 2>nul
if !ERRORLEVEL! NEQ 0 (
    echo   WARNING: Service "Cloudflared" not found.
    echo   Run scripts\SETUP_SHOWCASE.bat (as admin) to install.
) else (
    for /f "tokens=4 delims=: " %%s in ('sc query Cloudflared 2^>nul ^| findstr /i "STATE"') do set "CF_STATE=%%s"
)
echo.

REM ---- Tomcat Scheduled Task ---------------------------------
echo [2/4] IWPortal-Tomcat Scheduled Task
echo -------------------------------------------------------
schtasks /query /tn "%TASK_NAME%" /fo LIST 2>nul
if !ERRORLEVEL! NEQ 0 (
    echo   WARNING: Task "%TASK_NAME%" not found.
    echo   Run scripts\SETUP_SHOWCASE.bat (as admin) to register.
    set "TC_STATE=NOT FOUND"
) else (
    for /f "tokens=2 delims=:" %%s in ('schtasks /query /tn "%TASK_NAME%" /fo LIST 2^>nul ^| findstr /i "Status"') do (
        set "TC_STATE=%%s"
        set "TC_STATE=!TC_STATE: =!"
    )
)
echo.

REM ---- Tomcat HTTP check -------------------------------------
echo [3/4] Tomcat HTTP Health Check
echo -------------------------------------------------------
echo   Checking http://localhost:9090/iw-business-daemon/api/auth/session ...
set "TC_HTTP=unreachable"
for /f %%c in ('curl -s -o nul -w "%%{http_code}" --max-time 8 http://localhost:9090/iw-business-daemon/api/auth/session 2^>nul') do set "TC_HTTP=%%c"
echo   HTTP status: !TC_HTTP!
if "!TC_HTTP!"=="200" (
    echo   Tomcat is responding normally.
) else if "!TC_HTTP!"=="unreachable" (
    echo   WARNING: Tomcat is not reachable. Check if task is running.
) else (
    echo   Tomcat returned HTTP !TC_HTTP!. May still be starting up.
)
echo.

REM ---- Tunnel / Vercel URLs ----------------------------------
echo [4/4] Public URLs
echo -------------------------------------------------------
set "TUNNEL_URL=(not configured - run SETUP_SHOWCASE.bat)"
if exist "%LOGS_DIR%\cloudflare_tunnel_url.txt" (
    set /p TUNNEL_URL=<"%LOGS_DIR%\cloudflare_tunnel_url.txt"
)
echo   Cloudflare tunnel URL : !TUNNEL_URL!
echo   Vercel deploy URL     : https://iw-portal.vercel.app
echo.

REM ---- Summary table -----------------------------------------
if "!CF_STATE!"=="" (
    for /f "tokens=4 delims=: " %%s in ('sc query Cloudflared 2^>nul ^| findstr /i "STATE"') do set "CF_STATE=%%s"
)
if "!CF_STATE!"=="" set "CF_STATE=NOT FOUND"
if "!TC_STATE!"=="" set "TC_STATE=NOT FOUND"

echo ============================================================
echo   SUMMARY
echo ============================================================
echo   Service / Component        Status
echo   --------------------------  ----------------
echo   Cloudflared (Windows Svc)   !CF_STATE!
echo   IWPortal-Tomcat (Task)       !TC_STATE!
echo   Tomcat HTTP :9090            HTTP !TC_HTTP!
echo   Cloudflare tunnel URL        !TUNNEL_URL!
echo   Vercel URL                   https://iw-portal.vercel.app
echo ============================================================
echo.

endlocal
