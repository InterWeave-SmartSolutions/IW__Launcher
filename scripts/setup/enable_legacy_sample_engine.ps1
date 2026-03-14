param(
    [string]$SampleProjectName = "SF2AuthNet",
    [switch]$SkipWorkspaceImport,
    [switch]$SkipTransformationServerDeploy,
    [switch]$SkipRuntimeConfigMerge
)

$ErrorActionPreference = "Stop"

function Write-Step {
    param([string]$Message)
    Write-Host "[engine-setup] $Message"
}

function Get-RepoRoot {
    $scriptDir = $PSScriptRoot
    if (-not $scriptDir) {
        $scriptDir = Split-Path -Parent $PSCommandPath
    }
    return (Resolve-Path (Join-Path $scriptDir "..\..")).Path
}

function Import-XmlChildren {
    param(
        [xml]$TargetDocument,
        [System.Xml.XmlNode]$TargetRoot,
        [System.Xml.XmlNode]$SourceRoot
    )

    $existing = @($TargetRoot.SelectNodes("./TransactionDescription | ./Query"))
    foreach ($node in $existing) {
        [void]$TargetRoot.RemoveChild($node)
    }

    $count = 0
    foreach ($sourceNode in @($SourceRoot.SelectNodes("./TransactionDescription | ./Query"))) {
        $imported = $TargetDocument.ImportNode($sourceNode, $true)
        [void]$TargetRoot.AppendChild($imported)
        $count++
    }

    return $count
}

function Save-XmlDocument {
    param(
        [xml]$Document,
        [string]$Path
    )

    $settings = New-Object System.Xml.XmlWriterSettings
    $settings.Indent = $true
    $settings.Encoding = New-Object System.Text.UTF8Encoding($false)

    $writer = [System.Xml.XmlWriter]::Create($Path, $settings)
    try {
        $Document.Save($writer)
    }
    finally {
        $writer.Dispose()
    }
}

$repoRoot = Get-RepoRoot
$importRoot = Join-Path $repoRoot "frontends\InterWoven\docs\IW_Docs\IW_IDE\IW_IDE_Import"
$sampleProjectRoot = Join-Path $importRoot "IW_IDE\all_projects_2\$SampleProjectName"
$sampleProjectConfig = Join-Path $sampleProjectRoot "configuration\im\config.xml"
$sampleTransformationRoot = Join-Path $importRoot "Apache Software Foundation\Tomcat 5.5\webapps\$SampleProjectName"

$workspaceTarget = Join-Path $repoRoot "workspace\$SampleProjectName"
$transformationTarget = Join-Path $repoRoot "web_portal\tomcat\webapps\iwtransformationserver"
$runtimeConfigPath = Join-Path $repoRoot "web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\config.xml"

# Determine whether the InterWoven source directory is available.
# When cloned on a different machine the submodule/directory may be empty.
# Fall back to the already-imported workspace project if targets exist.
$hasInterWovenSource = (Test-Path $sampleProjectRoot) -and (Test-Path $sampleProjectConfig)

if (-not $hasInterWovenSource) {
    $workspaceConfigFallback = Join-Path $workspaceTarget "configuration\im\config.xml"

    if ((Test-Path $workspaceTarget) -and (Test-Path $transformationTarget) -and (Test-Path $workspaceConfigFallback)) {
        Write-Step "InterWoven source not present - using workspace fallback for '$SampleProjectName'"
        $sampleProjectRoot = $workspaceTarget
        $sampleProjectConfig = $workspaceConfigFallback
        $SkipWorkspaceImport = $true
        $SkipTransformationServerDeploy = $true
    }
    else {
        throw "Sample project not found at InterWoven source ($sampleProjectRoot) and workspace fallback is incomplete"
    }
}

if (-not (Test-Path $runtimeConfigPath)) {
    throw "Runtime Business Daemon config not found: $runtimeConfigPath"
}

Write-Step "Using sample project '$SampleProjectName'"

if (-not $SkipWorkspaceImport) {
    if (Test-Path $workspaceTarget) {
        Write-Step "Workspace project already present, skipping import: $workspaceTarget"
    }
    else {
        if (-not (Test-Path $sampleTransformationRoot)) {
            throw "Sample transformation server webapp not found: $sampleTransformationRoot"
        }
        Write-Step "Importing sample project into workspace"
        Copy-Item -Path $sampleProjectRoot -Destination $workspaceTarget -Recurse
    }
}

if (-not $SkipTransformationServerDeploy) {
    if (Test-Path $transformationTarget) {
        Write-Step "Transformation server webapp already present, skipping deploy: $transformationTarget"
    }
    else {
        if (-not (Test-Path $sampleTransformationRoot)) {
            throw "Sample transformation server webapp not found: $sampleTransformationRoot"
        }
        Write-Step "Deploying legacy transformation server webapp to /iwtransformationserver"
        Copy-Item -Path $sampleTransformationRoot -Destination $transformationTarget -Recurse
    }
}

if (-not $SkipRuntimeConfigMerge) {
    Write-Step "Merging TransactionDescription and Query nodes into live Business Daemon config"

    [xml]$targetXml = Get-Content -Path $runtimeConfigPath -Raw
    [xml]$sourceXml = Get-Content -Path $sampleProjectConfig -Raw

    $targetRoot = $targetXml.SelectSingleNode("/BusinessDaemonConfiguration")
    $sourceRoot = $sourceXml.SelectSingleNode("/BusinessDaemonConfiguration")

    if (-not $targetRoot) {
        throw "Target config does not contain BusinessDaemonConfiguration root"
    }
    if (-not $sourceRoot) {
        throw "Source config does not contain BusinessDaemonConfiguration root"
    }

    $importedCount = Import-XmlChildren -TargetDocument $targetXml -TargetRoot $targetRoot -SourceRoot $sourceRoot

    # Also import flows from other workspace projects that have im/config.xml
    $workspaceRoot = Join-Path $repoRoot "workspace"
    $excludeDirs = @($SampleProjectName, "GeneratedProfiles", "IW_Runtime_Sync", ".metadata", "Templates")
    foreach ($projectDir in (Get-ChildItem -Path $workspaceRoot -Directory -ErrorAction SilentlyContinue)) {
        if ($excludeDirs -contains $projectDir.Name) { continue }
        $otherConfig = Join-Path $projectDir.FullName "configuration\im\config.xml"
        if (Test-Path $otherConfig) {
            try {
                [xml]$otherXml = Get-Content -Path $otherConfig -Raw
                $otherRoot = $otherXml.SelectSingleNode("/BusinessDaemonConfiguration")
                if ($otherRoot) {
                    $otherCount = 0
                    foreach ($node in @($otherRoot.SelectNodes("./TransactionDescription | ./Query"))) {
                        $existingId = $node.GetAttribute("Id")
                        # Skip if a flow with the same Id already exists
                        $dup = $targetRoot.SelectSingleNode("*[@Id='$existingId']")
                        if (-not $dup) {
                            $imported = $targetXml.ImportNode($node, $true)
                            [void]$targetRoot.AppendChild($imported)
                            $otherCount++
                            $importedCount++
                        }
                    }
                    if ($otherCount -gt 0) {
                        Write-Step "Imported $otherCount additional flow nodes from $($projectDir.Name)"
                    }
                }
            }
            catch {
                Write-Step "Warning: could not parse $otherConfig - $_"
            }
        }
    }

    Save-XmlDocument -Document $targetXml -Path $runtimeConfigPath

    Write-Step "Imported $importedCount runtime flow nodes into $runtimeConfigPath"
}

Write-Step "Legacy sample engine setup complete"
