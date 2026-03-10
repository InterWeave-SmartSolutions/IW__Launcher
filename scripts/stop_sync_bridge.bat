@echo off
REM =============================================================================
REM Stop the bidirectional sync bridge
REM =============================================================================

set "SCRIPT_DIR=%~dp0"
powershell -NoProfile -ExecutionPolicy Bypass -File "%SCRIPT_DIR%sync_bridge.ps1" -Stop
