param(
    [string]$SourceJar = "",
    [string]$DestinationJar = ""
)

$ErrorActionPreference = "Stop"

function Write-Step {
    param([string]$Message)
    Write-Host "[jaxb-compat] $Message"
}

function Get-RepoRoot {
    $scriptDir = $PSScriptRoot
    if (-not $scriptDir) {
        $scriptDir = Split-Path -Parent $PSCommandPath
    }
    return (Resolve-Path (Join-Path $scriptDir "..\..")).Path
}

$repoRoot = Get-RepoRoot

if (-not $SourceJar) {
    $SourceJar = Join-Path $repoRoot "web_portal\tomcat\webapps\iwtransformationserver\WEB-INF\lib\jaxb-rt-1.0-ea.jar"
}
if (-not $DestinationJar) {
    $DestinationJar = Join-Path $repoRoot "web_portal\tomcat\lib\interweave-jaxb-compat.jar"
}

if (-not (Test-Path $SourceJar)) {
    throw "Legacy JAXB runtime not found: $SourceJar"
}

Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem

$destinationDir = Split-Path -Parent $DestinationJar
if (-not (Test-Path $destinationDir)) {
    New-Item -ItemType Directory -Path $destinationDir | Out-Null
}

if (Test-Path $DestinationJar) {
    try {
        Remove-Item $DestinationJar -Force
    }
    catch [System.IO.IOException] {
        $existingFile = Get-Item $DestinationJar -ErrorAction SilentlyContinue
        if ($existingFile -and $existingFile.Length -gt 0) {
            Write-Step "Compatibility jar is in use; reusing existing file at $($existingFile.FullName)"
            Write-Step "Jar size: $($existingFile.Length) bytes"
            return
        }

        throw
    }
}

$sourceArchive = [System.IO.Compression.ZipFile]::OpenRead($SourceJar)
$destinationArchive = [System.IO.Compression.ZipFile]::Open($DestinationJar, [System.IO.Compression.ZipArchiveMode]::Create)

$copied = 0
try {
    foreach ($entry in $sourceArchive.Entries) {
        if (
            $entry.FullName.StartsWith("javax/xml/bind/") -or
            $entry.FullName.StartsWith("javax/xml/marshal/") -or
            $entry.FullName.StartsWith("com/sun/xml/sp/")
        ) {
            $newEntry = $destinationArchive.CreateEntry($entry.FullName, [System.IO.Compression.CompressionLevel]::Optimal)
            $sourceStream = $entry.Open()
            $destinationStream = $newEntry.Open()
            try {
                $sourceStream.CopyTo($destinationStream)
            }
            finally {
                $destinationStream.Dispose()
                $sourceStream.Dispose()
            }
            $copied++
        }
    }
}
finally {
    $destinationArchive.Dispose()
    $sourceArchive.Dispose()
}

if ($copied -eq 0) {
    throw "No legacy JAXB classes were copied from $SourceJar"
}

$fileInfo = Get-Item $DestinationJar
Write-Step "Built compatibility jar with $copied classes at $($fileInfo.FullName)"
Write-Step "Jar size: $($fileInfo.Length) bytes"
