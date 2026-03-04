@echo off
setlocal

set "ACTION=%~1"
set "PROFILE=%~2"
set "PROJECT=%~3"

if not defined ACTION set "ACTION=exportAll"

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0sync_workspace_profiles.ps1" -Action "%ACTION%" -Profile "%PROFILE%" -Project "%PROJECT%"
exit /b %errorlevel%
