@echo off
REM ==============================================================================
REM InterWeave IDE - System Readiness Check
REM This script verifies that all components are available on this computer
REM ==============================================================================

setlocal enabledelayedexpansion

set "IW_HOME=%~dp0"
if "%IW_HOME:~-1%"=="\" set "IW_HOME=%IW_HOME:~0,-1%"

title InterWeave IDE - System Readiness Check

cls
echo.
echo  ======================================================================
echo.
echo       InterWeave IDE - SYSTEM READINESS CHECK
echo.
echo  ======================================================================
echo.

set PASS_COUNT=0
set FAIL_COUNT=0

REM ========== CHECK 1: Java Runtime ==========
echo  [1/9] Checking Java Runtime...
if exist "%IW_HOME%\jre\bin\java.exe" (
    echo    ✓ Bundled Java runtime found
    set /a PASS_COUNT+=1
) else (
    echo    ✗ ERROR: Bundled Java runtime NOT found
    echo      Expected: %IW_HOME%\jre\bin\java.exe
    set /a FAIL_COUNT+=1
)
echo.

REM ========== CHECK 2: IDE Executable ==========
echo  [2/9] Checking IDE Launcher...
if exist "%IW_HOME%\iw_ide.exe" (
    echo    ✓ IDE launcher (iw_ide.exe) found
    set /a PASS_COUNT+=1
) else (
    echo    ✗ ERROR: IDE launcher NOT found
    echo      Expected: %IW_HOME%\iw_ide.exe
    set /a FAIL_COUNT+=1
)
echo.

REM ========== CHECK 3: Tomcat ==========
echo  [3/9] Checking Tomcat Web Server...
if exist "%IW_HOME%\web_portal\tomcat\bin\startup.bat" (
    echo    ✓ Tomcat startup script found
    set /a PASS_COUNT+=1
) else (
    echo    ✗ FAIL: Tomcat startup script NOT found
    echo      Expected: %IW_HOME%\web_portal\tomcat\bin\startup.bat
    set /a FAIL_COUNT+=1
)
echo.

REM ========== CHECK 4: WAR file ==========
echo  [4/9] Checking Web Application (WAR)...
if exist "%IW_HOME%\web_portal\tomcat\webapps\iw-business-daemon.war" (
    echo    ✓ Web application WAR file found
    set /a PASS_COUNT+=1
) else (
    echo    ✗ WARNING: WAR file not found
    echo      Note: WAR may be expanded to directory
    if exist "%IW_HOME%\web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\web.xml" (
        echo    ✓ Web application is deployed/expanded
        set /a PASS_COUNT+=1
    ) else (
        echo    ✗ ERROR: Web application NOT found
        set /a FAIL_COUNT+=1
    )
)
echo.

REM ========== CHECK 5: Database Configuration ==========
echo  [5/9] Checking Database Configuration...
if exist "%IW_HOME%\.env" (
    echo    ✓ Environment configuration (.env) found
    for /f "tokens=2 delims==" %%a in ('findstr /B "DB_MODE=" "%IW_HOME%\.env"') do set "MODE=%%a"
    echo      Database mode: !MODE!
    set /a PASS_COUNT+=1
) else (
    echo    ✗ ERROR: Configuration file (.env) NOT found
    echo      Expected: %IW_HOME%\.env
    set /a FAIL_COUNT+=1
)
echo.

REM ========== CHECK 6: Startup Scripts ==========
echo  [6/9] Checking Startup Scripts...
set SCRIPT_COUNT=0
if exist "%IW_HOME%\START.bat" (
    set /a SCRIPT_COUNT+=1
)
if exist "%IW_HOME%\STOP.bat" (
    set /a SCRIPT_COUNT+=1
)
if exist "%IW_HOME%\CHANGE_DATABASE.bat" (
    set /a SCRIPT_COUNT+=1
)

if %SCRIPT_COUNT% equ 3 (
    echo    ✓ All startup scripts found (%SCRIPT_COUNT%/3)
    set /a PASS_COUNT+=1
) else (
    echo    ✗ WARNING: Only %SCRIPT_COUNT%/3 startup scripts found
)
echo.

REM ========== CHECK 7: Source Code ==========
echo  [7/9] Checking Java Source Code...
if exist "%IW_HOME%\src\main\java\com\interweave" (
    echo    ✓ Java source code found
    set /a PASS_COUNT+=1
) else (
    echo    ✗ ERROR: Java source code NOT found
    echo      Expected: %IW_HOME%\src\main\java\com\interweave
    set /a FAIL_COUNT+=1
)
echo.

REM ========== CHECK 8: Build System ==========
echo  [8/9] Checking Build System...
if exist "%IW_HOME%\pom.xml" (
    echo    ✓ Maven POM file found (pom.xml)
    set /a PASS_COUNT+=1
) else (
    echo    ✗ WARNING: Maven configuration not found
)
echo.

REM ========== CHECK 9: Port Availability ==========
echo  [9/9] Checking Network Port Availability...
netstat -ano | findstr ":8080" >nul 2>&1
if %errorlevel% equ 0 (
    echo    ⚠ Port 8080 is already in use
    echo      (Tomcat may already be running, or another service is using the port)
) else (
    echo    ✓ Port 8080 is available
    set /a PASS_COUNT+=1
)
echo.

REM ========== SUMMARY ==========
echo  ======================================================================
echo.
echo       READINESS CHECK SUMMARY
echo.
echo       ✓ Passed: %PASS_COUNT%
echo       ✗ Failed: %FAIL_COUNT%
echo.

if %FAIL_COUNT% equ 0 (
    echo       STATUS: ✓ READY TO LAUNCH
    echo.
    echo       You can now run: START.bat
    echo.
) else (
    echo       STATUS: ✗ ISSUES FOUND
    echo.
    echo       Please fix the errors above before launching.
    echo.
)

echo  ======================================================================
echo.
pause

endlocal
