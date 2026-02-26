@echo off
REM ============================================================================
REM IW_IDE Web Portal Launcher for Windows
REM Starts Tomcat server using the bundled JRE
REM ============================================================================

echo.
echo ============================================================
echo                IW_IDE Web Portal Startup
echo ============================================================
echo.

REM Get the directory where this script is located (_internal)
set "SCRIPT_DIR=%~dp0"

REM Remove trailing backslash
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

REM Go up one directory to get IW_HOME (parent of _internal)
for %%i in ("%SCRIPT_DIR%\..") do set "IW_HOME=%%~fi"

REM Set paths
set "JAVA_HOME=%IW_HOME%\jre"
set "JRE_HOME=%IW_HOME%\jre"
set "CATALINA_HOME=%IW_HOME%\web_portal\tomcat"

echo IW_IDE Home:     %IW_HOME%
echo.

REM Verify JRE exists
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo ERROR: Bundled JRE not found at:
    echo        %JAVA_HOME%
    echo.
    echo Please ensure the 'jre' folder exists in:
    echo        %IW_HOME%
    echo.
    echo Press any key to exit...
    pause >nul
    exit /b 1
)

REM Verify Tomcat exists
if not exist "%CATALINA_HOME%\bin\catalina.bat" (
    echo ERROR: Tomcat not found at:
    echo        %CATALINA_HOME%
    echo.
    echo Press any key to exit...
    pause >nul
    exit /b 1
)

echo Using Java from: %JAVA_HOME%
echo Tomcat home:     %CATALINA_HOME%
echo.

REM Test that Java works
echo Testing Java...
"%JAVA_HOME%\bin\java.exe" -version
if errorlevel 1 (
    echo ERROR: Java test failed!
    echo.
    echo Press any key to exit...
    pause >nul
    exit /b 1
)
echo.
echo Java OK!
echo.

echo ============================================================
echo Starting Tomcat...
echo.
echo The browser will open automatically once the server is ready.
echo.
echo Press Ctrl+C to stop Tomcat when done.
echo ============================================================
echo.

REM Start Tomcat in background
cd /d "%CATALINA_HOME%\bin"
call startup.bat

REM Wait for Tomcat to start (check every 2 seconds, up to 60 seconds)
echo.
echo Waiting for Tomcat to start...
set /a counter=0
set /a max_wait=30

:wait_loop
timeout /t 2 /nobreak >nul
set /a counter+=1

REM Check if Tomcat is responding
powershell -Command "try { $response = Invoke-WebRequest -Uri 'http://localhost:9090/' -UseBasicParsing -TimeoutSec 2; exit 0 } catch { exit 1 }" >nul 2>&1
if %errorlevel%==0 goto server_ready

if %counter% geq %max_wait% (
    echo.
    echo WARNING: Tomcat is taking longer than expected to start.
    echo Check the logs at: %CATALINA_HOME%\logs\
    echo.
    echo Opening browser anyway...
    goto open_browser
)

echo   Waiting... (%counter%/%max_wait%)
goto wait_loop

:server_ready
echo.
echo ============================================================
echo Tomcat is ready!
echo ============================================================
echo.

:open_browser
echo Opening browser to login page...
start "" "http://localhost:9090/iw-business-daemon/IWLogin.jsp"

echo.
echo ============================================================
echo Web Portal is running!
echo.
echo Login URL: http://localhost:9090/iw-business-daemon/IWLogin.jsp
echo.
echo Admin Login:
echo   Username: __iw_admin__
echo   Password: %%iwps%%
echo.
echo To stop the server, run: stop_webportal.bat
echo Or close this window (will also stop Tomcat)
echo ============================================================
echo.
echo Press any key to stop Tomcat and exit...
pause >nul

REM Stop Tomcat when user presses a key
echo.
echo Stopping Tomcat...
call shutdown.bat
echo Tomcat stopped.
timeout /t 2 >nul
