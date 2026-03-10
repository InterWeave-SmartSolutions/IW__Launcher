@echo off
REM Start Cloudflare Tunnel for IW Portal
REM Tunnels localhost:9090 to your Cloudflare domain
REM
REM Usage: scripts\start_cloudflare_tunnel.bat
REM   Runs in foreground. Use Ctrl+C to stop.
REM   For background: start /min scripts\start_cloudflare_tunnel.bat

echo Starting Cloudflare Tunnel (iw-portal -> localhost:9090)...
echo Press Ctrl+C to stop.
echo.

cloudflared tunnel run --url http://localhost:9090 iw-portal
