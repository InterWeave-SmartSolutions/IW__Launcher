@echo off
REM ============================================================================
REM InterWeave Web Portal - Windows Wrapper
REM Calls the canonical launcher in ..\scripts\start_webportal.bat
REM ============================================================================

setlocal
set "SCRIPT_DIR=%~dp0"
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

for %%i in ("%SCRIPT_DIR%\..") do set "IW_HOME=%%~fi"

call "%IW_HOME%\scripts\start_webportal.bat"
endlocal
