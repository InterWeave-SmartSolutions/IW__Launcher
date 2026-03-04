param(
    [string]$SampleProjectName = "SF2AuthNet"
)

$ErrorActionPreference = "Stop"

function Write-Step {
    param([string]$Message)
    Write-Host "[runtime-prepare] $Message"
}

function Get-RepoRoot {
    $scriptDir = $PSScriptRoot
    if (-not $scriptDir) {
        $scriptDir = Split-Path -Parent $PSCommandPath
    }
    return (Resolve-Path (Join-Path $scriptDir "..\..")).Path
}

$repoRoot = Get-RepoRoot
$engineSetupScript = Join-Path $repoRoot "scripts\setup\enable_legacy_sample_engine.ps1"
$jaxbCompatScript = Join-Path $repoRoot "scripts\setup\build_legacy_jaxb_compat.ps1"

if (-not (Test-Path $engineSetupScript)) {
    throw "Engine setup script not found: $engineSetupScript"
}
if (-not (Test-Path $jaxbCompatScript)) {
    throw "JAXB compatibility build script not found: $jaxbCompatScript"
}

Write-Step "Preparing legacy workspace/runtime assets"
& $engineSetupScript -SampleProjectName $SampleProjectName

Write-Step "Building legacy JAXB compatibility jar"
& $jaxbCompatScript

Write-Step "Legacy runtime preparation complete"
