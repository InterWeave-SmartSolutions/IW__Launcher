@echo off
REM ============================================================
REM  Start Cloudflare Tunnel for IW Portal
REM  Prerequisites: run scripts\setup_cloudflare_tunnel.bat first
REM ============================================================
REM  Usage:
REM    Foreground : scripts\start_cloudflare_tunnel.bat
REM    Background : start /min scripts\start_cloudflare_tunnel.bat
REM  Logs written to: logs\cloudflare_tunnel.log
REM ============================================================

if not exist "%USERPROFILE%\.cloudflared\config.yml" (
    echo ERROR: Cloudflare tunnel not configured.
    echo Run scripts\setup_cloudflare_tunnel.bat first.
    exit /b 1
)

if exist "logs\cloudflare_tunnel_url.txt" (
    set /p TUNNEL_URL=<logs\cloudflare_tunnel_url.txt
    echo Tunnel URL: !TUNNEL_URL!
)

echo Starting Cloudflare Tunnel  [iw-portal ^<-^> localhost:9090]
echo Logs: logs\cloudflare_tunnel.log
echo Press Ctrl+C to stop.
echo.

cloudflared tunnel run iw-portal >> logs\cloudflare_tunnel.log 2>&1
