@echo off
REM ============================================================
REM  Cloudflare Tunnel Setup for IW Portal
REM  Replaces localtunnel with a stable, password-free tunnel
REM ============================================================
REM
REM  PREREQUISITES:
REM    1. A Cloudflare account (free tier works)
REM    2. A domain added to Cloudflare DNS
REM
REM  This script installs cloudflared and walks you through setup.
REM  After setup, the tunnel URL never changes.
REM ============================================================

echo.
echo === Cloudflare Tunnel Setup for IW Portal ===
echo.

REM Step 1: Check if cloudflared is installed
where cloudflared >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [1/5] Installing cloudflared via winget...
    winget install --id Cloudflare.cloudflared --accept-source-agreements --accept-package-agreements
    if %ERRORLEVEL% NEQ 0 (
        echo ERROR: winget install failed. Download manually from:
        echo   https://developers.cloudflare.com/cloudflare-one/connections/connect-networks/downloads/
        exit /b 1
    )
) else (
    echo [1/5] cloudflared already installed.
)

echo.
echo [2/5] Authenticating with Cloudflare...
echo   A browser window will open. Log in and authorize cloudflared.
cloudflared tunnel login
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Authentication failed.
    exit /b 1
)

echo.
echo [3/5] Creating tunnel "iw-portal"...
cloudflared tunnel create iw-portal
if %ERRORLEVEL% NEQ 0 (
    echo NOTE: Tunnel may already exist. Continuing...
)

echo.
echo [4/5] MANUAL STEP REQUIRED:
echo.
echo   Run the following command, replacing YOUR-DOMAIN.com with your actual domain:
echo.
echo     cloudflared tunnel route dns iw-portal iw-portal.YOUR-DOMAIN.com
echo.
echo   This creates a CNAME record pointing to your tunnel.
echo.
pause

echo.
echo [5/5] Setup complete! To start the tunnel, run:
echo.
echo     scripts\start_cloudflare_tunnel.bat
echo.
echo   Then update frontends/iw-portal/vercel.json to use your new URL:
echo     "destination": "https://iw-portal.YOUR-DOMAIN.com/iw-business-daemon/:path*"
echo.
echo   Redeploy to Vercel and the URL will never change again.
echo.
