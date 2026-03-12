# quickstart_tunnel.ps1
# Starts Cloudflare Quick Tunnel (no auth needed), captures the
# trycloudflare.com URL, patches vercel.json, commits + pushes.
# Also starts Tomcat if not already running on port 9090.

$repoRoot   = Split-Path -Parent $PSScriptRoot
$javaExe    = "$repoRoot\jre\bin\java.exe"
$catHome    = "$repoRoot\web_portal\tomcat"
$logsDir    = "$repoRoot\logs"
$vercelJson = "$repoRoot\frontends\iw-portal\vercel.json"
$tunnelLog  = "$logsDir\quick_tunnel.log"

if (-not (Test-Path $logsDir)) { New-Item -ItemType Directory -Path $logsDir | Out-Null }

Write-Host ""
Write-Host "============================================================"
Write-Host "  IW Portal - Quick Tunnel Starter"
Write-Host "============================================================"
Write-Host ""

# ── Step 1: Check / start Tomcat ─────────────────────────────────────
Write-Host "[1/4] Checking Tomcat on port 9090..."
$tomcatUp = $false
try {
    $null = Invoke-WebRequest -Uri "http://localhost:9090/iw-business-daemon/" `
        -TimeoutSec 3 -UseBasicParsing -ErrorAction Stop
    $tomcatUp = $true
} catch { }

if ($tomcatUp) {
    Write-Host "  Tomcat is already running."
} else {
    Write-Host "  Tomcat not detected. Starting in background..."
    $javaArgs = @(
        "-Xverify:none", "-Xms256m", "-Xmx512m",
        "-Djava.util.logging.config.file=$catHome\conf\logging.properties",
        "-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager",
        "-classpath", "$catHome\bin\bootstrap.jar;$catHome\bin\tomcat-juli.jar",
        "-Dcatalina.base=$catHome",
        "-Dcatalina.home=$catHome",
        "-Djava.io.tmpdir=$catHome\temp",
        "org.apache.catalina.startup.Bootstrap", "start"
    )
    Start-Process -FilePath $javaExe -ArgumentList $javaArgs -WindowStyle Minimized
    Write-Host "  Tomcat starting — allow ~20s before it accepts requests."
}
Write-Host ""

# ── Step 2: Start Cloudflare Quick Tunnel ────────────────────────────
Write-Host "[2/4] Starting Cloudflare Quick Tunnel (no account needed)..."
Write-Host ""

if (Test-Path $tunnelLog)  { Remove-Item $tunnelLog -Force }
$stdoutLog = "$logsDir\quick_tunnel_stdout.log"
if (Test-Path $stdoutLog) { Remove-Item $stdoutLog -Force }

# cloudflared logs the URL to stderr
$cfProc = Start-Process -FilePath "npx" `
    -ArgumentList "cloudflared", "tunnel", "--url", "http://localhost:9090" `
    -RedirectStandardError  $tunnelLog `
    -RedirectStandardOutput $stdoutLog `
    -WindowStyle Hidden -PassThru

Write-Host "  Waiting for tunnel URL (up to 30s)..."
$tunnelUrl = $null
for ($i = 0; $i -lt 30; $i++) {
    Start-Sleep -Seconds 1
    if (Test-Path $tunnelLog) {
        $content = Get-Content $tunnelLog -Raw -ErrorAction SilentlyContinue
        $m = [regex]::Match($content, 'https?://[a-z0-9\-]+\.trycloudflare\.com')
        if ($m.Success) { $tunnelUrl = $m.Value; break }
    }
}

if (-not $tunnelUrl) {
    Write-Host "ERROR: Tunnel URL not found after 30s. Last log output:"
    if (Test-Path $tunnelLog) { Get-Content $tunnelLog | Select-Object -Last 15 | ForEach-Object { Write-Host "  $_" } }
    exit 1
}

Write-Host "  Got URL: $tunnelUrl"
Set-Content -Path "$logsDir\cloudflare_tunnel_url.txt" -Value $tunnelUrl -Encoding utf8
Write-Host ""

# ── Step 3: Patch vercel.json and push ───────────────────────────────
Write-Host "[3/4] Updating vercel.json and pushing to GitHub/Vercel..."
$vj = Get-Content $vercelJson -Raw | ConvertFrom-Json
$vj.rewrites[0].destination = "$tunnelUrl/iw-business-daemon/:path*"
$vj | ConvertTo-Json -Depth 10 | Set-Content $vercelJson -Encoding utf8 -Force
Write-Host "  vercel.json patched."

Push-Location $repoRoot
try {
    git add "frontends/iw-portal/vercel.json"
    git commit -m "chore: update Vercel proxy to active quick tunnel"
    git push origin main
    Write-Host "  Pushed to main. Vercel will rebuild in ~30s."
} finally {
    Pop-Location
}
Write-Host ""

# ── Step 4: Summary + health-check keepalive ─────────────────────────
Write-Host "[4/4] ALL SYSTEMS GO"
Write-Host "-------------------------------------------------------"
Write-Host "  Tunnel URL  : $tunnelUrl"
Write-Host "  Vercel URL  : https://iw-portal.vercel.app"
Write-Host "  Tomcat      : http://localhost:9090"
Write-Host ""
Write-Host "  KEEP THIS WINDOW OPEN — closing it kills the tunnel."
Write-Host "  For a permanent auto-start, run: scripts\SETUP_SHOWCASE.bat"
Write-Host "============================================================"
Write-Host ""

# Auto-restart tunnel if it dies; heartbeat every 30s
while ($true) {
    Start-Sleep -Seconds 30

    # Tomcat health
    $tcStatus = "down"
    try {
        $r = Invoke-WebRequest -Uri "http://localhost:9090/iw-business-daemon/api/auth/session" `
            -TimeoutSec 3 -UseBasicParsing -ErrorAction Stop
        $tcStatus = $r.StatusCode
    } catch { }

    # Tunnel health
    $cfStatus = if (-not $cfProc.HasExited) { "OK" } else { "STOPPED" }

    Write-Host "$((Get-Date).ToString('[HH:mm:ss]')) Tomcat=$tcStatus  Tunnel=$cfStatus  $tunnelUrl"

    # Auto-restart tunnel if it crashed
    if ($cfProc.HasExited) {
        Write-Host "  Tunnel exited — restarting..."
        Remove-Item $tunnelLog -Force -ErrorAction SilentlyContinue
        $cfProc = Start-Process -FilePath "npx" `
            -ArgumentList "cloudflared", "tunnel", "--url", "http://localhost:9090" `
            -RedirectStandardError  $tunnelLog `
            -RedirectStandardOutput $stdoutLog `
            -WindowStyle Hidden -PassThru
        Write-Host "  Tunnel restarted (URL may change — re-run script to update Vercel)."
    }
}
