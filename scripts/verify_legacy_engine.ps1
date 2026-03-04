param(
    [string]$SampleProjectName = "SF2AuthNet"
)

$ErrorActionPreference = "Stop"

function Write-Check {
    param([string]$Name, [string]$Value)
    Write-Host ("[engine-verify] {0}: {1}" -f $Name, $Value)
}

function Get-RepoRoot {
    $scriptDir = $PSScriptRoot
    if (-not $scriptDir) {
        $scriptDir = Split-Path -Parent $PSCommandPath
    }
    return (Resolve-Path (Join-Path $scriptDir "..")).Path
}

$repoRoot = Get-RepoRoot
$workspaceTarget = Join-Path $repoRoot "workspace\$SampleProjectName"
$runtimeConfigPath = Join-Path $repoRoot "web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\config.xml"
$transformationRoot = Join-Path $repoRoot "web_portal\tomcat\webapps\iwtransformationserver"
$transformationWebXml = Join-Path $transformationRoot "WEB-INF\web.xml"
$jaxbCompatJar = Join-Path $repoRoot "web_portal\tomcat\lib\interweave-jaxb-compat.jar"

Write-Check "workspace_project_exists" (Test-Path $workspaceTarget)
Write-Check "workspace_project_file" (Test-Path (Join-Path $workspaceTarget ".project"))
Write-Check "runtime_config_exists" (Test-Path $runtimeConfigPath)
Write-Check "transformation_webapp_exists" (Test-Path $transformationRoot)
Write-Check "transformation_webxml_exists" (Test-Path $transformationWebXml)
Write-Check "legacy_jaxb_compat_exists" (Test-Path $jaxbCompatJar)

if (Test-Path $runtimeConfigPath) {
    [xml]$runtimeXml = Get-Content -Path $runtimeConfigPath -Raw
    $transactionCount = @($runtimeXml.SelectNodes("/BusinessDaemonConfiguration/TransactionDescription")).Count
    $queryCount = @($runtimeXml.SelectNodes("/BusinessDaemonConfiguration/Query")).Count
    Write-Check "transaction_description_count" $transactionCount
    Write-Check "query_count" $queryCount
}

try {
    $rootResp = Invoke-WebRequest -Uri "http://localhost:9090/" -UseBasicParsing -TimeoutSec 5
    Write-Check "tomcat_root_status" $rootResp.StatusCode
}
catch {
    Write-Check "tomcat_root_status" "unreachable"
}

try {
    $loginResp = Invoke-WebRequest -Uri "http://localhost:9090/iw-business-daemon/IWLogin.jsp" -UseBasicParsing -TimeoutSec 5
    Write-Check "iw_business_daemon_status" $loginResp.StatusCode
}
catch {
    Write-Check "iw_business_daemon_status" "unreachable"
}

try {
    $tsResp = Invoke-WebRequest -Uri "http://localhost:9090/iwtransformationserver/index" -UseBasicParsing -TimeoutSec 5
    Write-Check "iwtransformationserver_index_status" $tsResp.StatusCode
}
catch {
    if ($_.Exception.Response -and $_.Exception.Response.StatusCode) {
        Write-Check "iwtransformationserver_index_status" ([int]$_.Exception.Response.StatusCode)
    }
    else {
        Write-Check "iwtransformationserver_index_status" "unreachable"
    }
}

try {
    $transformResp = Invoke-WebRequest -Uri "http://localhost:9090/iwtransformationserver/transform" -UseBasicParsing -TimeoutSec 5
    Write-Check "iwtransformationserver_transform_status" $transformResp.StatusCode
}
catch {
    if ($_.Exception.Response -and $_.Exception.Response.StatusCode) {
        Write-Check "iwtransformationserver_transform_status" ([int]$_.Exception.Response.StatusCode)
    }
    else {
        Write-Check "iwtransformationserver_transform_status" "unreachable"
    }
}
