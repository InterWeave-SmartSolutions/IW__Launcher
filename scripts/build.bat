@echo off
REM Build script for InterWeave Error Framework (Windows)
REM This script compiles Java sources and runs tests using Maven

echo ==========================================
echo InterWeave Error Framework Build Script
echo ==========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven not found in PATH
    echo.
    echo Please install Apache Maven from: https://maven.apache.org/download.cgi
    echo And ensure 'mvn' is in your PATH environment variable
    echo.
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java not found in PATH
    echo.
    echo Using bundled JRE...
    set JAVA_HOME=%~dp0..\jre
    set PATH=%JAVA_HOME%\bin;%PATH%
)

echo Maven version:
call mvn --version
echo.

REM Parse command line arguments
set BUILD_TARGET=package
set SKIP_TESTS=false
set CLEAN=false

:parse_args
if "%~1"=="" goto end_parse
if /i "%~1"=="clean" set CLEAN=true
if /i "%~1"=="test" set BUILD_TARGET=test
if /i "%~1"=="verify" set BUILD_TARGET=verify
if /i "%~1"=="install" set BUILD_TARGET=install
if /i "%~1"=="skip-tests" set SKIP_TESTS=true
if /i "%~1"=="-DskipTests" set SKIP_TESTS=true
shift
goto parse_args
:end_parse

REM Build Maven command
set MVN_CMD=mvn

if "%CLEAN%"=="true" (
    set MVN_CMD=%MVN_CMD% clean
)

set MVN_CMD=%MVN_CMD% %BUILD_TARGET%

if "%SKIP_TESTS%"=="true" (
    set MVN_CMD=%MVN_CMD% -DskipTests
)

echo.
echo Executing: %MVN_CMD%
echo.

REM Run Maven build
call %MVN_CMD%

if %errorlevel% neq 0 (
    echo.
    echo ==========================================
    echo BUILD FAILED
    echo ==========================================
    pause
    exit /b %errorlevel%
)

echo.
echo ==========================================
echo BUILD SUCCESSFUL
echo ==========================================
echo.
echo Build output: target\iw-error-framework-1.0.0.jar
echo Test reports: target\surefire-reports\
echo.

REM Ask if user wants to deploy to Tomcat
set /p DEPLOY="Deploy to Tomcat? (y/n): "
if /i "%DEPLOY%"=="y" (
    echo.
    echo Deploying to Tomcat...

    set WEBAPP_LIB=..\web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\lib

    if not exist "%WEBAPP_LIB%" (
        echo Creating %WEBAPP_LIB%...
        mkdir "%WEBAPP_LIB%"
    )

    copy /Y ..\target\iw-error-framework-1.0.0.jar "%WEBAPP_LIB%\"

    if %errorlevel% equ 0 (
        echo.
        echo Deployment successful!
        echo.
        echo Restart Tomcat to load the new classes:
        echo   scripts\stop_webportal.bat
        echo   scripts\start_webportal.bat
    ) else (
        echo.
        echo Deployment failed!
    )
)

echo.
pause
