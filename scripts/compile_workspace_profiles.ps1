param(
    [ValidateSet("compileAll", "compileProfile")]
    [string]$Action = "compileAll",
    [string]$Profile = ""
)

$base = "http://localhost:9090/iw-business-daemon/WorkspaceProfileCompilerServlet"
$qs = @("action=$([uri]::EscapeDataString($Action))")

if ($Profile) {
    $qs += "CurrentProfile=$([uri]::EscapeDataString($Profile))"
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
