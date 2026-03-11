@echo off
setlocal EnableDelayedExpansion
REM ============================================================
REM  quickstart_tunnel.bat
REM  Starts a Cloudflare Quick Tunnel (no auth/login needed),
REM  captures the stable trycloudflare.com URL, auto-patches
REM  vercel.json, commits, and pushes — so Vercel re-deploys
REM  the proxy to the live tunnel.
REM
REM  Also starts Tomcat if port 9090 is not already responding.
REM
REM  Usage: scripts\quickstart_tunnel.bat
REM  Keep this window open — the tunnel stays alive while it runs.
REM ============================================================

set "REPO_ROOT=C:\IW_IDE\IW_Launcher"
set "JAVA_EXE=%REPO_ROOT%\jre\bin\java.exe"
set "CATALINA_HOME=%REPO_ROOT%\web_portal\tomcat"
set "LOGS_DIR=%REPO_ROOT%\logs"
set "VERCEL_JSON=%REPO_ROOT%\frontends\iw-portal\vercel.json"

echo.
echo ============================================================
echo   IW Portal - Quick Tunnel Starter
echo ============================================================
echo.

REM ---- Step 1: Check / start Tomcat --------------------------
echo [1/4] Checking Tomcat on port 9090...
curl -s -o nul -w "%%{http_code}" --max-time 3 http://localhost:9090/iw-business-daemon/ 2>nul | findstr /r "2[0-9][0-9] 3[0-9][0-9]" >nul
if !ERRORLEVEL! EQU 0 (
    echo   Tomcat already running on port 9090.
) else (
    echo   Tomcat not detected. Starting Tomcat in background...
    if not exist "%LOGS_DIR%" mkdir "%LOGS_DIR%"
    start "IW-Tomcat" /min "%JAVA_EXE%" ^
        -Xverify:none -Xms256m -Xmx512m ^
        -Djava.util.logging.config.file="%CATALINA_HOME%\conf\logging.properties" ^
        -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager ^
        -classpath "%CATALINA_HOME%\bin\bootstrap.jar;%CATALINA_HOME%\bin\tomcat-juli.jar" ^
        -Dcatalina.base="%CATALINA_HOME%" ^
        -Dcatalina.home="%CATALINA_HOME%" ^
        -Djava.io.tmpdir="%CATALINA_HOME%\temp" ^
        org.apache.catalina.startup.Bootstrap start
    echo   Tomcat starting (allow ~20s to be fully up)...
)
echo.

REM ---- Step 2: Start quick tunnel and capture URL ------------
echo [2/4] Starting Cloudflare Quick Tunnel...
echo   (No Cloudflare account needed - uses trycloudflare.com)
echo.

REM Start cloudflared in background and pipe output to a temp file
set "TUNNEL_LOG=%LOGS_DIR%\quick_tunnel.log"
if not exist "%LOGS_DIR%" mkdir "%LOGS_DIR%"
if exist "%TUNNEL_LOG%" del /f "%TUNNEL_LOG%"

REM Start cloudflared as a background process writing to log
start "CF-QuickTunnel" /min cmd /c "cloudflared tunnel --url http://localhost:9090 > "%TUNNEL_LOG%" 2>&1"

REM Poll the log file for the trycloudflare.com URL (up to 30s)
echo   Waiting for tunnel URL...
set "TUNNEL_URL="
set /a _wait=0
:url_wait_loop
if !_wait! GEQ 30 (
    echo   ERROR: Tunnel URL not found after 30 seconds.
    goto :end_error
)
timeout /t 1 /nobreak >nul

REM Search for trycloudflare.com in the log
if exist "%TUNNEL_LOG%" (
    for /f "tokens=*" %%L in ('findstr /i "trycloudflare.com" "%TUNNEL_LOG%" 2^>nul') do (
        REM Extract URL using PowerShell regex
        for /f "delims=" %%U in ('powershell -NoProfile -Command "$m = [regex]::Match('%%L','https?://[a-z0-9\-]+\.trycloudflare\.com'); if($m.Success){$m.Value}"') do (
            if not "%%U"=="" set "TUNNEL_URL=%%U"
        )
    )
)
if not "!TUNNEL_URL!"=="" goto :url_found
set /a _wait+=1
goto :url_wait_loop

:url_found
echo.
echo   Tunnel URL: !TUNNEL_URL!
if exist "%LOGS_DIR%\cloudflare_tunnel_url.txt" del "%LOGS_DIR%\cloudflare_tunnel_url.txt"
echo !TUNNEL_URL! > "%LOGS_DIR%\cloudflare_tunnel_url.txt"
echo.

REM ---- Step 3: Patch vercel.json and push --------------------
echo [3/4] Updating vercel.json and pushing to Vercel...
powershell.exe -NoProfile -ExecutionPolicy Bypass -Command ^
    "$ErrorActionPreference = 'Stop';" ^
    "try {" ^
    "  $vj = Get-Content '%VERCEL_JSON%' -Raw | ConvertFrom-Json;" ^
    "  $vj.rewrites[0].destination = '!TUNNEL_URL!/iw-business-daemon/:path*';" ^
    "  $vj | ConvertTo-Json -Depth 10 | Set-Content '%VERCEL_JSON%' -Encoding utf8 -Force;" ^
    "  Write-Host '  vercel.json updated.';" ^
    "  Set-Location '%REPO_ROOT%';" ^
    "  git add 'frontends/iw-portal/vercel.json';" ^
    "  git commit -m 'chore: update Vercel proxy to active quick tunnel';" ^
    "  git push origin main;" ^
    "  Write-Host '  Pushed to main. Vercel will redeploy in ~30s.';" ^
    "} catch { Write-Host ('ERROR: ' + $_); exit 1 }"

if !ERRORLEVEL! NEQ 0 (
    echo   ERROR: Failed to update vercel.json or push.
    goto :end_error
)
echo.

REM ---- Step 4: Summary ---------------------------------------
echo [4/4] STATUS
echo -------------------------------------------------------
echo   Tunnel URL : !TUNNEL_URL!
echo   Vercel URL : https://iw-portal.vercel.app
echo   Tomcat     : http://localhost:9090
echo.
echo   Vercel is rebuilding (~30s). Once done, login will work.
echo.
echo   IMPORTANT: Keep this window open!
echo   Closing it will stop the tunnel and break the demo.
echo   To keep it alive permanently, run:
echo     scripts\SETUP_SHOWCASE.bat  (Cloudflare named tunnel as Windows Service)
echo.
echo ============================================================
echo   Tunnel running. Press Ctrl+C to stop.
echo ============================================================
echo.

REM Keep window alive while tunnel runs in background
:keep_alive
timeout /t 30 /nobreak >nul

REM Quick health check every 30s
set "TC_HTTP=0"
for /f %%c in ('curl -s -o nul -w "%%{http_code}" --max-time 3 http://localhost:9090/iw-business-daemon/api/auth/session 2^>nul') do set "TC_HTTP=%%c"
echo [%TIME%] Tomcat: HTTP !TC_HTTP! ^| Tunnel: !TUNNEL_URL!
goto :keep_alive

:end_error
echo.
echo   FAILED. Check output above.
echo.
pause
endlocal
