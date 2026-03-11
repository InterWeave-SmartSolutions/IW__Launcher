@echo off
setlocal EnableDelayedExpansion
REM ============================================================
REM  Cloudflare Tunnel Setup for IW Portal
REM  - No domain purchase required (uses <uuid>.cfargotunnel.com)
REM  - Auto-patches frontends/iw-portal/vercel.json
REM  - Run once; then use scripts\start_cloudflare_tunnel.bat
REM ============================================================

echo.
echo ========================================
echo  IW Portal - Cloudflare Tunnel Setup
echo ========================================
echo.

REM Step 1: Verify cloudflared
where cloudflared >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [1/4] Installing cloudflared via winget...
    winget install --id Cloudflare.cloudflared --accept-source-agreements --accept-package-agreements
    if !ERRORLEVEL! NEQ 0 (
        echo.
        echo ERROR: Install failed. Download from:
        echo   https://developers.cloudflare.com/cloudflare-one/connections/connect-networks/downloads/
        exit /b 1
    )
) else (
    echo [1/4] cloudflared detected.
)

echo.
echo [2/4] Authenticating with Cloudflare...
echo   A browser window will open - log in with your Cloudflare account.
echo   After login, return here.
echo.
cloudflared tunnel login
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Authentication failed. Try again.
    exit /b 1
)
echo   Auth successful.

echo.
echo [3/4] Creating tunnel "iw-portal" (safe to re-run if it already exists)...
cloudflared tunnel create iw-portal >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo   Tunnel may already exist - checking info...
)

echo.
echo [4/4] Configuring tunnel and updating vercel.json...
powershell.exe -NoProfile -ExecutionPolicy Bypass -Command ^
    "$ErrorActionPreference = 'Stop';" ^
    "try {" ^
    "  $raw = cloudflared tunnel info iw-portal 2>&1 | Out-String;" ^
    "  $m = [Regex]::Match($raw, '[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}');" ^
    "  if (-not $m.Success) { throw 'Could not find UUID in tunnel info output.' };" ^
    "  $uuid = $m.Value;" ^
    "  $tunnelUrl = 'https://' + $uuid + '.cfargotunnel.com';" ^
    "  Write-Host '  Tunnel UUID : ' $uuid;" ^
    "  Write-Host '  Tunnel URL  : ' $tunnelUrl;" ^
    "  $cfDir = $env:USERPROFILE + '\.cloudflared';" ^
    "  $credsFile = $cfDir + '\' + $uuid + '.json';" ^
    "  $configYml = 'tunnel: ' + $uuid + [Environment]::NewLine + 'credentials-file: ' + $credsFile + [Environment]::NewLine + 'ingress:' + [Environment]::NewLine + '  - service: http://localhost:9090';" ^
    "  $configYml | Out-File -FilePath ($cfDir + '\config.yml') -Encoding utf8 -Force;" ^
    "  Write-Host '  Config written to ' ($cfDir + '\config.yml');" ^
    "  $repoRoot = (Get-Item (Split-Path $PSScriptRoot -Parent)).FullName;" ^
    "  $vercelPath = $repoRoot + '\frontends\iw-portal\vercel.json';" ^
    "  $vercel = Get-Content $vercelPath -Raw | ConvertFrom-Json;" ^
    "  $vercel.rewrites[0].destination = $tunnelUrl + '/iw-business-daemon/:path*';" ^
    "  $vercel | ConvertTo-Json -Depth 10 | Set-Content $vercelPath -Encoding utf8 -Force;" ^
    "  Write-Host '  vercel.json updated with Cloudflare URL.';" ^
    "  Write-Host '';" ^
    "  Write-Host 'Setup complete! Next steps:';" ^
    "  Write-Host '  1. Start tunnel  : scripts\start_cloudflare_tunnel.bat';" ^
    "  Write-Host '  2. Commit change : git add frontends/iw-portal/vercel.json && git commit -m chore: switch-to-cloudflare-tunnel';" ^
    "  Write-Host '  3. Push to main  : git push  (Vercel auto-redeploys)';" ^
    "  Write-Host '  4. Done! URL is  : ' $tunnelUrl;" ^
    "  [System.IO.File]::WriteAllText($repoRoot + '\logs\cloudflare_tunnel_url.txt', $tunnelUrl);" ^
    "} catch {" ^
    "  Write-Host 'ERROR: ' $_;" ^
    "  exit 1" ^
    "}"

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Setup encountered an error. Check output above.
    exit /b 1
)

echo.
pause
endlocal
