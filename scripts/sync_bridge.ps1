# =============================================================================
# IW_IDE Bidirectional Sync Bridge
# =============================================================================
# Watches workspace directories for changes made by the IDE and automatically
# syncs them back to the web portal database via WorkspaceProfileSyncServlet.
#
# Also watches for portal-generated files (GeneratedProfiles/, IW_Runtime_Sync/)
# and logs when the portal pushes new content so developers know their IDE
# workspace has been updated.
#
# Usage:
#   powershell -ExecutionPolicy Bypass -File scripts\sync_bridge.ps1
#   powershell -ExecutionPolicy Bypass -File scripts\sync_bridge.ps1 -PortalUrl http://localhost:9090
#   powershell -ExecutionPolicy Bypass -File scripts\sync_bridge.ps1 -DebounceSec 3
#   powershell -ExecutionPolicy Bypass -File scripts\sync_bridge.ps1 -Stop
#
# Zero dependencies beyond Windows PowerShell 5.1+ (built into Windows 10/11).
# =============================================================================

param(
    [string]$PortalUrl = "http://localhost:9090",
    [int]$DebounceSec = 2,
    [switch]$Stop,
    [switch]$Quiet
)

$ErrorActionPreference = "Continue"

# Resolve IW_HOME (two levels up from scripts/)
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$IW_HOME = Split-Path -Parent $ScriptDir
$WorkspaceRoot = Join-Path $IW_HOME "workspace"
$LogsDir = Join-Path $IW_HOME "logs"
$LogFile = Join-Path $LogsDir "sync_bridge.log"
$PidFile = Join-Path $LogsDir "sync_bridge.pid"
$SyncServletBase = "$PortalUrl/iw-business-daemon/WorkspaceProfileSyncServlet"
$CompileServletBase = "$PortalUrl/iw-business-daemon/WorkspaceProfileCompilerServlet"

# Ensure logs directory exists
if (-not (Test-Path $LogsDir)) { New-Item -ItemType Directory -Path $LogsDir -Force | Out-Null }

# =============================================================================
# STOP mode: kill existing bridge process
# =============================================================================
if ($Stop) {
    if (Test-Path $PidFile) {
        $savedPid = Get-Content $PidFile -ErrorAction SilentlyContinue
        if ($savedPid) {
            try {
                Stop-Process -Id $savedPid -Force -ErrorAction SilentlyContinue
                Write-Host "  [OK] Sync bridge stopped (PID $savedPid)"
            } catch {
                Write-Host "  [OK] Sync bridge was not running"
            }
        }
        Remove-Item $PidFile -Force -ErrorAction SilentlyContinue
    } else {
        Write-Host "  [OK] Sync bridge was not running"
    }
    exit 0
}

# =============================================================================
# Check prerequisites
# =============================================================================
if (-not (Test-Path $WorkspaceRoot)) {
    Write-Error "Workspace not found: $WorkspaceRoot"
    exit 1
}

# Kill any existing sync bridge
if (Test-Path $PidFile) {
    $oldPid = Get-Content $PidFile -ErrorAction SilentlyContinue
    if ($oldPid) {
        Stop-Process -Id $oldPid -Force -ErrorAction SilentlyContinue 2>$null
    }
    Remove-Item $PidFile -Force -ErrorAction SilentlyContinue
}

# Write our PID
$currentPid = [System.Diagnostics.Process]::GetCurrentProcess().Id
$currentPid | Set-Content $PidFile

# =============================================================================
# Logging
# =============================================================================
function Write-Log {
    param([string]$Message, [string]$Level = "INFO")
    $ts = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $line = "[$ts] [$Level] $Message"
    Add-Content -Path $LogFile -Value $line
    if (-not $Quiet) {
        switch ($Level) {
            "ERROR" { Write-Host "  [!] $Message" -ForegroundColor Red }
            "WARN"  { Write-Host "  [!] $Message" -ForegroundColor Yellow }
            "SYNC"  { Write-Host "  [>>] $Message" -ForegroundColor Cyan }
            default { Write-Host "  $Message" }
        }
    }
}

# =============================================================================
# Profile name resolution from workspace paths
# =============================================================================
# Maps workspace project directories to profile names by reading runtime_profiles/
function Get-ProfilesForProject {
    param([string]$ProjectName)

    $rtDir = "$WorkspaceRoot\$ProjectName\configuration\runtime_profiles"
    if (-not (Test-Path $rtDir)) { return @() }

    $profiles = @()
    Get-ChildItem -Path $rtDir -Filter "*.properties" | ForEach-Object {
        $props = @{}
        Get-Content $_.FullName | ForEach-Object {
            if ($_ -match "^([^#=]+)=(.*)$") {
                $props[$Matches[1].Trim()] = $Matches[2].Trim()
            }
        }
        if ($props["profile_name"]) {
            # Java .properties escapes colons as \: — unescape
            $profiles += $props["profile_name"] -replace '\\:', ':'
        }
    }
    return $profiles
}

# =============================================================================
# Sync actions
# =============================================================================
function Invoke-ImportProfile {
    param([string]$ProfileName, [string]$ProjectName)

    $url = "$SyncServletBase`?action=importProfile"
    $url += "&CurrentProfile=$([uri]::EscapeDataString($ProfileName))"
    if ($ProjectName) {
        $url += "&project=$([uri]::EscapeDataString($ProjectName))"
    }

    try {
        $response = Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 15
        Write-Log "IDE -> Portal: imported '$ProfileName' from '$ProjectName'" "SYNC"
        return $true
    } catch {
        Write-Log "Import failed for '$ProfileName': $($_.Exception.Message)" "ERROR"
        return $false
    }
}

function Invoke-CompileProfile {
    param([string]$ProfileName)

    $url = "$CompileServletBase`?action=compileProfile"
    $url += "&CurrentProfile=$([uri]::EscapeDataString($ProfileName))"

    try {
        Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 20 | Out-Null
        Write-Log "Recompiled overlay for '$ProfileName'" "SYNC"
        return $true
    } catch {
        Write-Log "Compile failed for '$ProfileName': $($_.Exception.Message)" "WARN"
        return $false
    }
}

function Invoke-ExportAll {
    try {
        Invoke-WebRequest -Uri "$SyncServletBase`?action=exportAll" -UseBasicParsing -TimeoutSec 15 | Out-Null
        Write-Log "Portal -> IDE: exported all profiles to workspace" "SYNC"
        return $true
    } catch {
        Write-Log "Export failed: $($_.Exception.Message)" "ERROR"
        return $false
    }
}

# =============================================================================
# Determine which directories to watch
# =============================================================================
# IDE-authored project directories (exclude generated/sync dirs)
$excludeDirs = @("GeneratedProfiles", "IW_Runtime_Sync", ".metadata")

$ideProjectDirs = Get-ChildItem -Path $WorkspaceRoot -Directory |
    Where-Object { $_.Name -notin $excludeDirs -and -not $_.Name.StartsWith(".") } |
    Select-Object -ExpandProperty Name

Write-Log "Sync bridge starting" "INFO"
Write-Log "Workspace: $WorkspaceRoot" "INFO"
Write-Log "Portal: $PortalUrl" "INFO"
Write-Log "Debounce: ${DebounceSec}s" "INFO"
Write-Log "Watching IDE projects: $($ideProjectDirs -join ', ')" "INFO"

if (-not $Quiet) {
    Write-Host ""
    Write-Host "  =================================================================="
    Write-Host "  Sync Bridge Active" -ForegroundColor Green
    Write-Host "  =================================================================="
    Write-Host ""
    Write-Host "  Watching for IDE changes in:"
    foreach ($d in $ideProjectDirs) {
        Write-Host "    - workspace\$d\" -ForegroundColor White
    }
    Write-Host ""
    Write-Host "  Changes will sync to portal at $PortalUrl"
    Write-Host "  Press Ctrl+C to stop."
    Write-Host ""
}

# =============================================================================
# Polling-based file watcher (reliable across all PowerShell hosting modes)
# =============================================================================
# Snapshots file timestamps, compares on each tick. More reliable than
# FileSystemWatcher events which can miss changes in background/job contexts.

# Collect all watched files across IDE projects
function Get-WatchedFileStates {
    $states = @{}
    foreach ($project in $ideProjectDirs) {
        $base = "$WorkspaceRoot\$project"

        # configuration/im/config.xml
        $f = "$base\configuration\im\config.xml"
        if (Test-Path $f) { $states[$f] = (Get-Item $f).LastWriteTime }

        # configuration/ts/config.xml
        $f = "$base\configuration\ts\config.xml"
        if (Test-Path $f) { $states[$f] = (Get-Item $f).LastWriteTime }

        # configuration/runtime_profiles/*.xml and *.properties
        $rtDir = "$base\configuration\runtime_profiles"
        if (Test-Path $rtDir) {
            Get-ChildItem -Path $rtDir -File -ErrorAction SilentlyContinue | Where-Object {
                $_.Extension -eq ".xml" -or $_.Extension -eq ".properties"
            } | ForEach-Object { $states[$_.FullName] = $_.LastWriteTime }
        }

        # xslt/**/*.xslt and xslt/**/*.xml (recursive)
        $xsltDir = "$base\xslt"
        if (Test-Path $xsltDir) {
            Get-ChildItem -Path $xsltDir -Recurse -File -ErrorAction SilentlyContinue | Where-Object {
                $_.Extension -eq ".xslt" -or $_.Extension -eq ".xml"
            } | ForEach-Object { $states[$_.FullName] = $_.LastWriteTime }
        }
    }
    return $states
}

# Take initial snapshot
$lastStates = Get-WatchedFileStates

# =============================================================================
# Main loop: poll for changes, debounce, and sync
# =============================================================================
$pendingProjects = @{}
$lastChangeTime = [DateTime]::MinValue

try {
    while ($true) {
        Start-Sleep -Seconds 1

        # Poll for file changes
        $currentStates = Get-WatchedFileStates
        $changedProjects = @{}

        # Check for modified or new files
        foreach ($file in $currentStates.Keys) {
            if (-not $lastStates.ContainsKey($file) -or $lastStates[$file] -ne $currentStates[$file]) {
                $relPath = $file.Substring($WorkspaceRoot.Length).TrimStart("\")
                $project = ($relPath -split "\\")[0]
                $changedProjects[$project] = $true
            }
        }

        # Check for deleted files
        foreach ($file in $lastStates.Keys) {
            if (-not $currentStates.ContainsKey($file)) {
                $relPath = $file.Substring($WorkspaceRoot.Length).TrimStart("\")
                $project = ($relPath -split "\\")[0]
                $changedProjects[$project] = $true
            }
        }

        $lastStates = $currentStates

        # Accumulate changed projects for debouncing
        if ($changedProjects.Count -gt 0) {
            foreach ($p in $changedProjects.Keys) {
                $pendingProjects[$p] = $true
            }
            $lastChangeTime = [DateTime]::Now
        }

        # If we have pending changes and enough quiet time, sync
        if ($pendingProjects.Count -gt 0) {
            $elapsed = ([DateTime]::Now - $lastChangeTime).TotalSeconds
            if ($elapsed -ge $DebounceSec) {
                $projectsToSync = @($pendingProjects.Keys)
                $pendingProjects = @{}

                foreach ($project in $projectsToSync) {
                    Write-Log "Change detected in '$project', syncing..." "INFO"

                    try {
                        $profiles = @(Get-ProfilesForProject -ProjectName $project)
                        if ($profiles.Count -eq 0) {
                            Write-Log "No runtime profiles found for '$project' - skipping import" "WARN"
                            continue
                        }

                        foreach ($profile in $profiles) {
                            $imported = Invoke-ImportProfile -ProfileName $profile -ProjectName $project
                            if ($imported) {
                                Invoke-CompileProfile -ProfileName $profile
                            }
                        }
                    } catch {
                        Write-Log "Sync failed for '$project': $($_.Exception.Message) at $($_.InvocationInfo.ScriptLineNumber)" "ERROR"
                    }
                }

                # Cooldown: wait for compile writes to settle, then re-snapshot
                # This prevents re-triggering on our own sync/compile output
                Start-Sleep -Seconds 3
                $lastStates = Get-WatchedFileStates
                $lastChangeTime = [DateTime]::MinValue
            }
        }
    }
} finally {
    Write-Log "Sync bridge stopping" "INFO"
    if (Test-Path $PidFile) { Remove-Item $PidFile -Force -ErrorAction SilentlyContinue }
}
