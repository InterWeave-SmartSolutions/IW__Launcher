@echo off
setlocal

set "PROFILE=%~1"
if not defined PROFILE set "PROFILE=Tester1:amagown@interweave.biz"

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0verify_profile_compiler.ps1" -Profile "%PROFILE%"
exit /b %errorlevel%
