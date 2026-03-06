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

$jaxbSourceJar = Join-Path $repoRoot "web_portal\tomcat\webapps\iwtransformationserver\WEB-INF\lib\jaxb-rt-1.0-ea.jar"
$jaxbDestJar = Join-Path $repoRoot "web_portal\tomcat\lib\interweave-jaxb-compat.jar"

if ((Test-Path $jaxbSourceJar) -or (Test-Path $jaxbDestJar)) {
    Write-Step "Building legacy JAXB compatibility jar"
    & $jaxbCompatScript
}
else {
    Write-Step "JAXB source jar not present and compat jar not built - skipping (portal functions without it)"
}

Write-Step "Legacy runtime preparation complete"
