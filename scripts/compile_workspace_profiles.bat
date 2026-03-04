@echo off
setlocal

set "ACTION=%~1"
set "PROFILE=%~2"

if not defined ACTION set "ACTION=compileAll"

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0compile_workspace_profiles.ps1" -Action "%ACTION%" -Profile "%PROFILE%"
exit /b %errorlevel%
