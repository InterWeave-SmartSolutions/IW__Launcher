@echo off
REM =============================================================================
REM Ensure all workspace projects are visible in the InterWeave IDE Navigator.
REM
REM The IW Navigator (iw_sdk.NavigationView) uses Eclipse ResourcesPlugin to
REM enumerate projects. Projects must be registered in Eclipse workspace metadata
REM — simply having .project files on disk is NOT enough.
REM
REM The iw_workspace_init_1.0.0 plugin (AutoImportStartup) handles import on
REM startup. This script ensures:
REM   1. Every workspace project has a .project file
REM   2. The OSGI cache is cleared when the plugin class is newer than the cache
REM      (forces Eclipse to re-resolve the plugin)
REM   3. Stale workspace metadata is cleaned so AutoImportStartup can re-import
REM =============================================================================

setlocal
set "IW_HOME=%~dp0..\.."
for %%I in ("%IW_HOME%") do set "IW_HOME=%%~fI"

set "WORKSPACE=%IW_HOME%\workspace"
set "RESOURCES=%WORKSPACE%\.metadata\.plugins\org.eclipse.core.resources"
set "SNAP=%RESOURCES%\.snap"
set "PROJECTS_DIR=%RESOURCES%\.projects"
set "PLUGIN_CLASS=%IW_HOME%\plugins\iw_workspace_init_1.0.0\bin\com\interweave\workspace\AutoImportStartup.class"
set "OSGI_DIR=%IW_HOME%\configuration\org.eclipse.osgi"
set "RUNTIME_DIR=%IW_HOME%\configuration\org.eclipse.core.runtime"

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

REM Ensure the auto-import plugin is compiled
if not exist "%PLUGIN_CLASS%" (
    echo  [WARN] Auto-import plugin not compiled. Projects may not appear in IDE.
    echo         Compile with: javac -source 8 -target 8 -cp "plugins\org.eclipse.core.resources_3.1.2.jar;..." ...
)

REM If new projects need registering, clear workspace metadata + OSGI cache
if %PROJECT_COUNT% GTR %REGISTERED_COUNT% (
    echo  [OK] Refreshing IDE workspace (%PROJECT_COUNT% projects found, %REGISTERED_COUNT% registered^)

    if exist "%SNAP%" del /q "%SNAP%" >nul 2>&1
    if exist "%PROJECTS_DIR%" rmdir /s /q "%PROJECTS_DIR%" >nul 2>&1
    if exist "%RESOURCES%\.root\.markers.snap" del /q "%RESOURCES%\.root\.markers.snap" >nul 2>&1
    if exist "%RESOURCES%\.root\.indexes" rmdir /s /q "%RESOURCES%\.root\.indexes" >nul 2>&1

    REM Clear OSGI cache so Eclipse re-resolves the auto-import plugin
    if exist "%OSGI_DIR%" (
        del /q "%OSGI_DIR%\.bundledata.*" >nul 2>&1
        del /q "%OSGI_DIR%\.state.*" >nul 2>&1
        del /q "%OSGI_DIR%\.lazy.*" >nul 2>&1
    )
    if exist "%RUNTIME_DIR%" (
        del /q "%RUNTIME_DIR%\.mainData.*" >nul 2>&1
    )
)

endlocal
exit /b 0
