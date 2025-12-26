@echo off
REM ============================================================================
REM IW_IDE Portable Launcher for Windows
REM This script launches the IDE using the bundled JRE
REM ============================================================================

REM Get the _internal directory
set "SCRIPT_DIR=%~dp0"
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

REM Go up one directory to IW_HOME
for %%i in ("%SCRIPT_DIR%\..") do set "IW_HOME=%%~fi"

REM Change to IW_HOME (where iw_ide.exe is located)
REM This is required because iw_ide.exe looks for jre\bin\javaw.exe relative to CWD
cd /d "%IW_HOME%"

REM Set JAVA_HOME and PATH
set "JAVA_HOME=%IW_HOME%\jre"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Launch the IDE
start "" "%IW_HOME%\iw_ide.exe"
