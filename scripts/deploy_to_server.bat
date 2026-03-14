@echo off
REM ============================================================================
REM Deploy IW_Launcher to a remote Windows server
REM
REM Usage:  deploy_to_server.bat \\SERVER\D$\IW_Launcher
REM         deploy_to_server.bat D:\IW_Launcher  (local path for same-machine)
REM
REM This script copies only the files needed to run IW_Launcher on a target
REM machine, excluding development-only files (.git, node_modules, docs/ai, etc).
REM It does NOT modify any production services (Tomcat 5.5, stunnel, IIS).
REM ============================================================================

echo.
echo  ==================================================================
echo       InterWeave IDE - Production Server Deployment
echo  ==================================================================
echo.

if "%~1"=="" (
    echo  ERROR: No target path specified.
    echo.
    echo  Usage:  deploy_to_server.bat ^<target-path^>
    echo.
    echo  Examples:
    echo    deploy_to_server.bat \\107525-UVS13\D$\IW_Launcher
    echo    deploy_to_server.bat D:\IW_Launcher
    echo.
    exit /b 1
)

set "TARGET=%~1"
set "SOURCE=%~dp0.."

echo  Source:  %SOURCE%
echo  Target:  %TARGET%
echo.

REM Safety check: refuse to deploy over production Tomcat
if exist "%TARGET%\..\Tomcat 5.5" (
    echo  ERROR: Target appears to be inside a production Tomcat directory.
    echo  Refusing to deploy. Choose a separate directory.
    exit /b 1
)

REM Check source has required files
if not exist "%SOURCE%\START.bat" (
    echo  ERROR: START.bat not found in source. Is this an IW_Launcher repo?
    exit /b 1
)
if not exist "%SOURCE%\jre\bin\java.exe" (
    echo  WARNING: Bundled JRE not found. You will need to install it on the target.
)

echo  Deploying IW_Launcher to %TARGET% ...
echo.

REM Create target directory
if not exist "%TARGET%" mkdir "%TARGET%"

REM Core runtime (required)
echo  [1/8] Copying JRE...
robocopy "%SOURCE%\jre" "%TARGET%\jre" /E /NFL /NDL /NJH /NJS /NC /NS >nul

echo  [2/8] Copying web portal...
robocopy "%SOURCE%\web_portal" "%TARGET%\web_portal" /E /NFL /NDL /NJH /NJS /NC /NS /XD "logs" >nul

echo  [3/8] Copying workspace...
robocopy "%SOURCE%\workspace" "%TARGET%\workspace" /E /NFL /NDL /NJH /NJS /NC /NS /XD ".metadata" >nul

echo  [4/8] Copying scripts...
robocopy "%SOURCE%\scripts" "%TARGET%\scripts" /E /NFL /NDL /NJH /NJS /NC /NS >nul

echo  [5/8] Copying launcher scripts...
copy /Y "%SOURCE%\START.bat" "%TARGET%\START.bat" >nul
copy /Y "%SOURCE%\STOP.bat" "%TARGET%\STOP.bat" >nul
if exist "%SOURCE%\CHANGE_DATABASE.bat" copy /Y "%SOURCE%\CHANGE_DATABASE.bat" "%TARGET%\CHANGE_DATABASE.bat" >nul

echo  [6/8] Copying database schemas...
robocopy "%SOURCE%\database" "%TARGET%\database" /E /NFL /NDL /NJH /NJS /NC /NS >nul

echo  [7/8] Copying plugins...
if exist "%SOURCE%\plugins" robocopy "%SOURCE%\plugins" "%TARGET%\plugins" /E /NFL /NDL /NJH /NJS /NC /NS >nul

echo  [8/8] Copying config templates...
if not exist "%TARGET%\docs\authentication" mkdir "%TARGET%\docs\authentication"
copy /Y "%SOURCE%\docs\authentication\*.template" "%TARGET%\docs\authentication\" >nul 2>&1
if exist "%SOURCE%\docs\authentication\config.xml.standalone.template" copy /Y "%SOURCE%\docs\authentication\config.xml.standalone.template" "%TARGET%\docs\authentication\" >nul

REM Create .env.example if not present
if exist "%SOURCE%\.env.example" (
    copy /Y "%SOURCE%\.env.example" "%TARGET%\.env.example" >nul
)

echo.
echo  ==================================================================
echo  Deployment complete!
echo  ==================================================================
echo.
echo  Target: %TARGET%
echo.
echo  Next steps on the server:
echo    1. cd %TARGET%
echo    2. copy .env.example .env
echo    3. Edit .env with database credentials
echo    4. Run START.bat
echo.
echo  Verify:
echo    - http://localhost:9090/iw-business-daemon/  (login page)
echo    - http://localhost:9090/iw-portal/            (React dashboard)
echo.
echo  IMPORTANT: This does NOT affect production (Tomcat 5.5 on port 8080).
echo.
