@echo off
REM ============================================================================
REM IW_IDE Web Portal Shutdown for Windows
REM Stops the Tomcat server
REM ============================================================================

setlocal

echo.
echo ============================================================
echo                IW_IDE Web Portal Shutdown
echo ============================================================
echo.

REM Get the directory where this script is located (_internal)
set "SCRIPT_DIR=%~dp0"

REM Remove trailing backslash
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

REM Go up one directory to get IW_HOME (parent of _internal)
for %%i in ("%SCRIPT_DIR%\..") do set "IW_HOME=%%~fi"

REM Set paths
set "JAVA_HOME=%IW_HOME%\jre"
set "CATALINA_HOME=%IW_HOME%\web_portal\tomcat"

REM Verify paths exist
if not exist "%CATALINA_HOME%\bin\shutdown.bat" (
    echo ERROR: Tomcat not found at:
    echo        %CATALINA_HOME%
    echo.
    pause
    exit /b 1
)

echo Stopping Tomcat...
echo.

REM Stop Tomcat
cd /d "%CATALINA_HOME%\bin"
call shutdown.bat

if errorlevel 1 (
    echo.
    echo Note: Shutdown command completed.
    echo       If Tomcat was not running, this is normal.
)

echo.
echo ============================================================
echo Tomcat shutdown initiated.
echo The server will stop within a few seconds.
echo ============================================================
echo.

endlocal
