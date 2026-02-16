@echo off
REM Debug wrapper for START.bat — captures output and keeps window open
setlocal enabledelayedexpansion
cd /d "%~dp0"
echo === RUN_START_DEBUG: Starting START.bat (debug mode) ===
echo Log file: "%~dp0start_debug.log"
echo. > "%~dp0start_debug.log"
echo START TIME: %DATE% %TIME% >> "%~dp0start_debug.log"
echo. >> "%~dp0start_debug.log"
REM Run START.bat and capture output
call "%~dp0START.bat" >> "%~dp0start_debug.log" 2>&1
echo. >> "%~dp0start_debug.log"
echo EXIT CODE: %ERRORLEVEL% >> "%~dp0start_debug.log"
echo END TIME: %DATE% %TIME% >> "%~dp0start_debug.log"
echo.
echo === OUTPUT (last 200 lines) ===
more +0 "%~dp0start_debug.log"
echo.
echo === End of debug run. Press any key to close. ===
pause
endlocal
