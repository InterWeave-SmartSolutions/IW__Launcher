@echo off
REM =============================================================================
REM Ensure all workspace projects are visible in the InterWeave IDE Navigator.
REM
REM Eclipse 3.1 only shows projects registered in .snap metadata. New projects
REM added to workspace/ (by sync, compiler, etc.) won't appear until imported.
REM
REM This script deletes the stale .snap so Eclipse rebuilds its project registry
REM by scanning workspace/ for .project files on next launch.
REM =============================================================================

setlocal
set "IW_HOME=%~dp0..\.."
for %%I in ("%IW_HOME%") do set "IW_HOME=%%~fI"

set "WORKSPACE=%IW_HOME%\workspace"
set "RESOURCES=%WORKSPACE%\.metadata\.plugins\org.eclipse.core.resources"
set "SNAP=%RESOURCES%\.snap"
set "PROJECTS_DIR=%RESOURCES%\.projects"

REM Count .project files in workspace (skip infrastructure dirs)
set /a PROJECT_COUNT=0
for /d %%D in ("%WORKSPACE%\*") do (
    if "%%~nxD"=="GeneratedProfiles" goto :skip_count_%%D
    if "%%~nxD"=="IW_Runtime_Sync" goto :skip_count_%%D
    if exist "%%D\.project" set /a PROJECT_COUNT+=1
    :skip_count_%%D
)

REM Count already-registered projects in Eclipse metadata
set /a REGISTERED_COUNT=0
if exist "%PROJECTS_DIR%" (
    for /d %%D in ("%PROJECTS_DIR%\*") do (
        set /a REGISTERED_COUNT+=1
    )
)

REM If all projects are already registered, skip reset
if %PROJECT_COUNT% LEQ %REGISTERED_COUNT% (
    exit /b 0
)

REM New projects found — reset .snap so Eclipse rediscovers everything
echo  [OK] Refreshing IDE workspace (%PROJECT_COUNT% projects found, %REGISTERED_COUNT% registered)

if exist "%SNAP%" (
    del /q "%SNAP%" >nul 2>&1
)

REM Also remove per-project metadata dirs so Eclipse rebuilds cleanly
if exist "%PROJECTS_DIR%" (
    rmdir /s /q "%PROJECTS_DIR%" >nul 2>&1
)

REM Remove root markers/indexes that reference old project set
if exist "%RESOURCES%\.root\.markers.snap" del /q "%RESOURCES%\.root\.markers.snap" >nul 2>&1
if exist "%RESOURCES%\.root\.indexes" rmdir /s /q "%RESOURCES%\.root\.indexes" >nul 2>&1

endlocal
exit /b 0
