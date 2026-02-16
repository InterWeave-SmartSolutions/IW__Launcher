@echo off
REM ============================================================================
REM Install Tomcat 9.0.83 for InterWeave IDE
REM ============================================================================

echo.
echo  ==================================================================
echo.
echo       InterWeave IDE - Tomcat Installation Helper
echo.
echo  ==================================================================
echo.

REM Get the directory where this script is located
set "SCRIPT_DIR=%~dp0"
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

REM Run PowerShell script to install Tomcat
powershell -NoProfile -ExecutionPolicy Bypass -File "%SCRIPT_DIR%\INSTALL_TOMCAT.ps1"

if %errorlevel% equ 0 (
    echo.
    echo  SUCCESS! Tomcat is ready. Now run: START.bat
    echo.
) else (
    echo.
    echo  Installation failed. Please check the error message above.
    echo.
)

pause
