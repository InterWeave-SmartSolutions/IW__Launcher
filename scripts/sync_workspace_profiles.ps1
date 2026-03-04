param(
    [ValidateSet("exportAll", "exportProfile", "importProfile")]
    [string]$Action = "exportAll",
    [string]$Profile = "",
    [string]$Project = ""
)

$base = "http://localhost:9090/iw-business-daemon/WorkspaceProfileSyncServlet"
$qs = @("action=$([uri]::EscapeDataString($Action))")

if ($Profile) {
    $qs += "CurrentProfile=$([uri]::EscapeDataString($Profile))"
}

if ($Project) {
    $qs += "project=$([uri]::EscapeDataString($Project))"
}

$url = $base + "?" + ($qs -join "&")

try {
    $response = Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 30
    $response.Content
} catch {
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $body = $reader.ReadToEnd()
        if ($body) {
            Write-Error $body
        } else {
            Write-Error $_.Exception.Message
        }
    } else {
        Write-Error $_.Exception.Message
    }
    exit 1
}
