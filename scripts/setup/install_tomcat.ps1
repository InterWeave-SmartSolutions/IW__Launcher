# InterWeave IDE - Tomcat 9.0.83 Setup Script
# Run this to download and install Tomcat

$IWHome = Split-Path -Parent $MyInvocation.MyCommand.Path
$TomcatZip = Join-Path $IWHome "tomcat-9.0.83.zip"
$TomcatURL = "https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.83/bin/apache-tomcat-9.0.83-windows-x64.zip"
$TomcatWebPortal = Join-Path $IWHome "web_portal\tomcat"

Write-Host ""
Write-Host "  ===================================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "       InterWeave IDE - Tomcat 9.0.83 Installation" -ForegroundColor Cyan
Write-Host ""
Write-Host "  ===================================================================" -ForegroundColor Cyan
Write-Host ""

# Check if zip already exists
if (Test-Path $TomcatZip) {
    Write-Host "  [OK] Found tomcat-9.0.83.zip" -ForegroundColor Green
    Write-Host ""
} else {
    Write-Host "  Downloading Tomcat 9.0.83..." -ForegroundColor Yellow
    Write-Host "  Source: $TomcatURL" -ForegroundColor Gray
    Write-Host ""
    
    try {
        [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
        $ProgressPreference = 'Continue'
        Invoke-WebRequest -Uri $TomcatURL -OutFile $TomcatZip -UseBasicParsing
        Write-Host "  [OK] Downloaded successfully!" -ForegroundColor Green
        Write-Host ""
    }
    catch {
        Write-Host "  [ERROR] Download failed!" -ForegroundColor Red
        Write-Host "  Error: $_" -ForegroundColor Red
        Write-Host ""
        Write-Host "  Alternative: Download manually from:" -ForegroundColor Yellow
        Write-Host "    https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.83/bin/apache-tomcat-9.0.83-windows-x64.zip" -ForegroundColor Gray
        Write-Host ""
        Read-Host "  Press Enter to exit"
        exit 1
    }
}

# Extract zip file
Write-Host "  Extracting Tomcat..." -ForegroundColor Yellow
try {
    Expand-Archive -Path $TomcatZip -DestinationPath $IWHome -Force
    Write-Host "  [OK] Extracted successfully!" -ForegroundColor Green
    Write-Host ""
}
catch {
    Write-Host "  [ERROR] Extraction failed!" -ForegroundColor Red
    Write-Host "  Error: $_" -ForegroundColor Red
    Read-Host "  Press Enter to exit"
    exit 1
}

# Move Tomcat files
$ApacheTomcat = Join-Path $IWHome "apache-tomcat-9.0.83"
if (Test-Path $ApacheTomcat) {
    Write-Host "  Moving Tomcat files to web_portal\tomcat..." -ForegroundColor Yellow
    
    try {
        # Remove existing bin and lib directories
        if (Test-Path "$TomcatWebPortal\bin") {
            Remove-Item -Path "$TomcatWebPortal\bin" -Recurse -Force
        }
        if (Test-Path "$TomcatWebPortal\lib") {
            Remove-Item -Path "$TomcatWebPortal\lib" -Recurse -Force
        }
        
        # Move bin and lib from extracted archive
        Move-Item -Path "$ApacheTomcat\bin" -Destination "$TomcatWebPortal\bin" -Force
        Move-Item -Path "$ApacheTomcat\lib" -Destination "$TomcatWebPortal\lib" -Force
        
        # Move any other important files
        Copy-Item -Path "$ApacheTomcat\*" -Destination "$TomcatWebPortal\" -Recurse -Force -Exclude @("bin", "lib", "webapps", "work", "conf")
        
        # Clean up
        Remove-Item -Path $ApacheTomcat -Recurse -Force
        Remove-Item -Path $TomcatZip -Force
        
        Write-Host "  [OK] Setup complete!" -ForegroundColor Green
        Write-Host ""
        Write-Host "  ✓ Tomcat 9.0.83 is ready" -ForegroundColor Green
        Write-Host "  ✓ Web portal configured" -ForegroundColor Green
        Write-Host ""
        Write-Host "  Next: Run START.bat to launch the application" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "  ===================================================================" -ForegroundColor Cyan
        Write-Host ""
        
    }
    catch {
        Write-Host "  [ERROR] Failed to move files!" -ForegroundColor Red
        Write-Host "  Error: $_" -ForegroundColor Red
        Read-Host "  Press Enter to exit"
        exit 1
    }
} else {
    Write-Host "  [ERROR] Extracted directory not found!" -ForegroundColor Red
    Read-Host "  Press Enter to exit"
    exit 1
}

Read-Host "  Press Enter to exit"
