@echo off
REM =============================================================================
REM Start the bidirectional sync bridge (IDE <-> Web Portal)
REM =============================================================================
REM Runs in the background. Changes in the IDE workspace are automatically
REM synced to the web portal database. Use stop_sync_bridge.bat to stop.
REM =============================================================================

set "SCRIPT_DIR=%~dp0"
start "IW Sync Bridge" /min powershell -NoProfile -ExecutionPolicy Bypass -File "%SCRIPT_DIR%sync_bridge.ps1"
echo  [OK] Sync bridge started (background window)
