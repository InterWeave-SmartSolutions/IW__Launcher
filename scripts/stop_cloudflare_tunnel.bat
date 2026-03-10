@echo off
REM Stop Cloudflare Tunnel
REM Kills any running cloudflared processes

echo Stopping Cloudflare Tunnel...
taskkill /IM cloudflared.exe /F >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Tunnel stopped.
) else (
    echo No tunnel process found.
)
