@echo off
REM Compilation script for LocalLoginServlet
REM This script compiles both the error framework and LocalLoginServlet

echo ==========================================
echo Compiling LocalLoginServlet
echo ==========================================
echo.

REM Set paths
set JAVAC="C:\Program Files\Java\jdk-24\bin\javac.exe"
set WEB_INF=web_portal\tomcat\webapps\iw-business-daemon\WEB-INF
set CLASSES=%WEB_INF%\classes
set LIB=%WEB_INF%\lib
set TOMCAT_LIB=web_portal\tomcat\lib

REM Check if javac exists
if not exist %JAVAC% (
    echo ERROR: Java compiler not found at %JAVAC%
    echo Please install JDK or update the JAVAC path in this script
    exit /b 1
)

echo Using Java compiler: %JAVAC%
echo.

REM Create output directories
if not exist "%CLASSES%\com\interweave\error" mkdir "%CLASSES%\com\interweave\error"
if not exist "%CLASSES%\com\interweave\businessDaemon\config" mkdir "%CLASSES%\com\interweave\businessDaemon\config"

echo Step 1: Compiling error framework classes...
%JAVAC% -d "%CLASSES%" ^
    -classpath "%TOMCAT_LIB%\servlet-api.jar" ^
    -source 1.8 -target 1.8 ^
    src\main\java\com\interweave\error\*.java

if %errorlevel% neq 0 (
    echo ERROR: Failed to compile error framework
    exit /b 1
)

echo ✓ Error framework compiled successfully
echo.

echo Step 2: Compiling LocalLoginServlet...
%JAVAC% -d "%CLASSES%" ^
    -classpath "%CLASSES%;%TOMCAT_LIB%\servlet-api.jar;%TOMCAT_LIB%\mysql-connector-java-8.0.33.jar" ^
    -source 1.8 -target 1.8 ^
    %WEB_INF%\src\com\interweave\businessDaemon\config\LocalLoginServlet.java

if %errorlevel% neq 0 (
    echo ERROR: Failed to compile LocalLoginServlet
    exit /b 1
)

echo ✓ LocalLoginServlet compiled successfully
echo.

echo ==========================================
echo COMPILATION SUCCESSFUL
echo ==========================================
echo.
echo Compiled files:
echo   - %CLASSES%\com\interweave\error\*.class
echo   - %CLASSES%\com\interweave\businessDaemon\config\LocalLoginServlet.class
echo   - %CLASSES%\com\interweave\businessDaemon\config\LocalLoginServlet$*.class
echo.
echo Restart Tomcat to load the updated classes:
echo   _internal\stop_webportal.bat
echo   _internal\start_webportal.bat
echo.
