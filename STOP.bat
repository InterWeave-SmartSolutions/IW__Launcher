@echo off
REM =============================================================================
REM IW_IDE - STOP
REM =============================================================================
REM Double-click this to stop the application.
REM =============================================================================

setlocal
title IW_IDE - Stopping...

set "IW_HOME=%~dp0"
if "%IW_HOME:~-1%"=="\" set "IW_HOME=%IW_HOME:~0,-1%"

set "JAVA_HOME=%IW_HOME%\jre"
set "CATALINA_HOME=%IW_HOME%\web_portal\tomcat"

echo.
echo  ==================================================================
echo.
echo       IW_IDE - Stopping Application
echo.
echo  ==================================================================
echo.

if not exist "%CATALINA_HOME%\bin\shutdown.bat" (
    echo  [ERROR] Application not found!
    pause
    exit /b 1
)

echo  Stopping sync bridge...
powershell -NoProfile -ExecutionPolicy Bypass -File "%IW_HOME%\scripts\sync_bridge.ps1" -Stop 2>nul

echo  Stopping web server...
cd /d "%CATALINA_HOME%\bin"
call shutdown.bat >nul 2>&1

echo.
echo  ==================================================================
echo.
echo       Application stopped.
echo.
echo  ==================================================================
echo.

timeout /t 3 >nul
endlocal
