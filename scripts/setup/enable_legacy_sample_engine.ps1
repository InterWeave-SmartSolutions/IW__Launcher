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

if (-not (Test-Path $sampleProjectRoot)) {
    throw "Sample project not found: $sampleProjectRoot"
}
if (-not (Test-Path $sampleProjectConfig)) {
    throw "Sample project IM config not found: $sampleProjectConfig"
}
if (-not (Test-Path $sampleTransformationRoot)) {
    throw "Sample transformation server webapp not found: $sampleTransformationRoot"
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
        Write-Step "Importing sample project into workspace"
        Copy-Item -Path $sampleProjectRoot -Destination $workspaceTarget -Recurse
    }
}

if (-not $SkipTransformationServerDeploy) {
    if (Test-Path $transformationTarget) {
        Write-Step "Transformation server webapp already present, skipping deploy: $transformationTarget"
    }
    else {
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
    Save-XmlDocument -Document $targetXml -Path $runtimeConfigPath

    Write-Step "Imported $importedCount runtime flow nodes into $runtimeConfigPath"
}

Write-Step "Legacy sample engine setup complete"
