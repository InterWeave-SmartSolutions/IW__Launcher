@echo off
REM Debug wrapper for START.bat — captures output and keeps window open
setlocal enabledelayedexpansion
REM Resolve project root (one level up from scripts/)
for %%i in ("%~dp0..") do set "IW_HOME=%%~fi"
cd /d "%IW_HOME%"
echo === RUN_START_DEBUG: Starting START.bat (debug mode) ===
echo Log file: "%IW_HOME%\start_debug.log"
echo. > "%IW_HOME%\start_debug.log"
echo START TIME: %DATE% %TIME% >> "%IW_HOME%\start_debug.log"
echo. >> "%IW_HOME%\start_debug.log"
REM Run START.bat and capture output
call "%IW_HOME%\START.bat" >> "%IW_HOME%\start_debug.log" 2>&1
echo. >> "%IW_HOME%\start_debug.log"
echo EXIT CODE: %ERRORLEVEL% >> "%IW_HOME%\start_debug.log"
echo END TIME: %DATE% %TIME% >> "%IW_HOME%\start_debug.log"
echo.
echo === OUTPUT (last 200 lines) ===
more +0 "%IW_HOME%\start_debug.log"
echo.
echo === End of debug run. Press any key to close. ===
pause
endlocal
