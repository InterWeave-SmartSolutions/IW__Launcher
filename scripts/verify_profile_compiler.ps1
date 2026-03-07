param(
    [string]$Profile = "Tester1:amagown@interweave.biz",
    [string]$ExpectationPath = "C:\IW_Launcher\tests\compiler-regression\Tester1.CRM2QB3.expected.properties"
)

function Unescape-JavaProperty([string]$Value) {
    if ($null -eq $Value) {
        return $Value
    }

    $sentinel = [char]1
    $decoded = $Value.Replace('\\\\', [string]$sentinel)
    $decoded = $decoded.Replace('\:', ':')
    $decoded = $decoded.Replace('\=', '=')
    $decoded = $decoded.Replace('\ ', ' ')
    $decoded = $decoded.Replace([string]$sentinel, '\')
    return $decoded
}

function Read-Properties([string]$Path) {
    $map = @{}
    foreach ($line in Get-Content $Path) {
        if (-not $line) { continue }
        if ($line.Trim().StartsWith('#')) { continue }
        $idx = $line.IndexOf('=')
        if ($idx -lt 1) { continue }
        $key = $line.Substring(0, $idx).Trim()
        $value = Unescape-JavaProperty($line.Substring($idx + 1).Trim())
        $map[$key] = $value
    }
    return $map
}

function Require([bool]$Condition, [string]$Message, [System.Collections.Generic.List[string]]$Errors) {
    if (-not $Condition) {
        $Errors.Add($Message)
    }
}

if (-not (Test-Path $ExpectationPath)) {
    Write-Error "Expectation file not found: $ExpectationPath"
    exit 1
}

$expected = Read-Properties $ExpectationPath
$url = "http://localhost:9090/iw-business-daemon/WorkspaceProfileCompilerServlet?action=compileProfile&CurrentProfile=$([uri]::EscapeDataString($Profile))"
$response = Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 30
if ([int]$response.StatusCode -ne 200) {
    Write-Error "Compiler servlet returned status $($response.StatusCode)"
    exit 1
}

$safeProfile = ($Profile -replace '[^A-Za-z0-9._-]+', '_')
$generatedRoot = "C:\IW_Launcher\workspace\GeneratedProfiles\$safeProfile"
$engineConfigPath = Join-Path $generatedRoot "configuration\im\config.xml"
$profilePropertiesPath = Join-Path $generatedRoot "configuration\profile\profile.properties"
$selectionPropertiesPath = Join-Path $generatedRoot "configuration\profile\compiler-selection.properties"

$errors = New-Object 'System.Collections.Generic.List[string]'

Require (Test-Path $engineConfigPath) "Generated engine config missing: $engineConfigPath" $errors
Require (Test-Path $profilePropertiesPath) "Generated profile.properties missing: $profilePropertiesPath" $errors
Require (Test-Path $selectionPropertiesPath) "Generated compiler-selection.properties missing: $selectionPropertiesPath" $errors

if ($errors.Count -gt 0) {
    $errors | ForEach-Object { Write-Error $_ }
    exit 1
}

$profileProps = Read-Properties $profilePropertiesPath
$selectionProps = Read-Properties $selectionPropertiesPath
[xml]$engineXml = Get-Content $engineConfigPath

Require ($profileProps["solution_type"] -eq $expected["solution_type"]) "Expected solution_type=$($expected["solution_type"]), got $($profileProps["solution_type"])" $errors
Require ($profileProps["compiler_module"] -eq $expected["compiler_module"]) "Expected compiler_module=$($expected["compiler_module"]), got $($profileProps["compiler_module"])" $errors
Require ($profileProps["ts_base_url"] -eq $expected["ts_base_url"]) "Expected ts_base_url=$($expected["ts_base_url"]), got $($profileProps["ts_base_url"])" $errors

$activeTransactions = @{}
foreach ($id in ($selectionProps["active_transactions"] -split ',')) { if ($id) { $activeTransactions[$id] = $true } }
$inactiveTransactions = @{}
foreach ($id in ($selectionProps["inactive_transactions"] -split ',')) { if ($id) { $inactiveTransactions[$id] = $true } }
$activeQueries = @{}
foreach ($id in ($selectionProps["active_queries"] -split ',')) { if ($id) { $activeQueries[$id] = $true } }
$inactiveQueries = @{}
foreach ($id in ($selectionProps["inactive_queries"] -split ',')) { if ($id) { $inactiveQueries[$id] = $true } }

foreach ($key in $expected.Keys | Where-Object { $_ -like 'expected_active_transaction*' }) {
    $id = $expected[$key]
    Require ($activeTransactions.ContainsKey($id)) "Expected active transaction missing from selection: $id" $errors
}

foreach ($key in $expected.Keys | Where-Object { $_ -like 'expected_inactive_transaction*' }) {
    $id = $expected[$key]
    Require ($inactiveTransactions.ContainsKey($id)) "Expected inactive transaction missing from selection: $id" $errors
}

foreach ($key in $expected.Keys | Where-Object { $_ -like 'expected_active_query*' }) {
    $id = $expected[$key]
    Require ($activeQueries.ContainsKey($id)) "Expected active query missing from selection: $id" $errors
}

foreach ($key in $expected.Keys | Where-Object { $_ -like 'expected_inactive_query*' }) {
    $id = $expected[$key]
    Require ($inactiveQueries.ContainsKey($id)) "Expected inactive query missing from selection: $id" $errors
}

$sfTxn = $engineXml.SelectSingleNode("//TransactionDescription[@Id='SFTransactions2Auth']")
Require ($null -ne $sfTxn) "SFTransactions2Auth not found in generated engine config" $errors
if ($sfTxn -ne $null) {
    $returnParam = $sfTxn.SelectSingleNode("Parameter[@Name='ReturnString']")
    Require ($null -ne $returnParam) "ReturnString parameter missing for SFTransactions2Auth" $errors
    if ($returnParam -ne $null) {
        $returnValue = $returnParam.GetAttribute("Value")
        Require ($returnValue -eq $expected["expected_return_string"]) "Expected ReturnString=$($expected["expected_return_string"]), got $returnValue" $errors
    }
}

if ($errors.Count -gt 0) {
    $errors | ForEach-Object { Write-Error $_ }
    exit 1
}

$summary = [pscustomobject]@{
    profile = $Profile
    module = $profileProps["compiler_module"]
    active_transactions = ($selectionProps["active_transaction_count"])
    inactive_transactions = ($selectionProps["inactive_transaction_count"])
    active_queries = ($selectionProps["active_query_count"])
    inactive_queries = ($selectionProps["inactive_query_count"])
    generated_root = $generatedRoot
}

$summary | ConvertTo-Json -Compress
