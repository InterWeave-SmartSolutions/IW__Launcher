@echo off
REM ============================================================================
REM Setup Tomcat 9.0.83 for InterWeave IDE
REM ============================================================================

echo.
echo  ==================================================================
echo.
echo       InterWeave IDE - Tomcat Installation
echo.
echo  ==================================================================
echo.

setlocal enabledelayedexpansion
cd /d "%~dp0"

REM Check if already downloaded
if exist "tomcat-9.0.83.zip" (
    echo  Found tomcat-9.0.83.zip, extracting...
    echo.
    
    REM Extract
    powershell -Command "Expand-Archive -Path 'tomcat-9.0.83.zip' -DestinationPath '.' -Force" 2>nul
    
    if exist "apache-tomcat-9.0.83" (
        echo  [OK] Extracted successfully
        echo.
        echo  Moving files to web_portal\tomcat...
        rmdir /s /q "web_portal\tomcat\bin" 2>nul
        rmdir /s /q "web_portal\tomcat\lib" 2>nul
        move "apache-tomcat-9.0.83\bin" "web_portal\tomcat\bin" >nul 2>&1
        move "apache-tomcat-9.0.83\lib" "web_portal\tomcat\lib" >nul 2>&1
        move "apache-tomcat-9.0.83\*.* " "web_portal\tomcat\" >nul 2>&1
        rmdir /s /q "apache-tomcat-9.0.83" 2>nul
        del "tomcat-9.0.83.zip"
        
        echo  [OK] Setup complete!
        echo.
        echo  Tomcat 9.0.83 is ready. You can now run START.bat
        echo.
        pause
    ) else (
        echo  [ERROR] Extraction failed!
        pause
        exit /b 1
    )
) else (
    echo  [ERROR] tomcat-9.0.83.zip not found!
    echo.
    echo  Download it first:
    echo.
    echo  Run this PowerShell command:
    echo.
    echo  cd C:\IW_Launcher
    echo  [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    echo  (New-Object System.Net.WebClient).DownloadFile(
    echo    'https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.83/bin/apache-tomcat-9.0.83-windows-x64.zip',
    echo    'tomcat-9.0.83.zip'
    echo  )
    echo.
    pause
    exit /b 1
)
