@echo off
REM Compilation script for LocalLoginServlet
REM This script compiles both the error framework and LocalLoginServlet

echo ==========================================
echo Compiling LocalLoginServlet
echo ==========================================
echo.

REM Set paths
set "WEB_INF=web_portal\tomcat\webapps\iw-business-daemon\WEB-INF"
set "CLASSES=%WEB_INF%\classes"
set "LIB=%WEB_INF%\lib"
set "TOMCAT_LIB=web_portal\tomcat\lib"

REM Locate javac (JDK required)
set "JAVAC="
for /f "delims=" %%J in ('where javac 2^>nul') do (
    set "JAVAC=%%J"
    goto :javac_found
)
:javac_found

if not defined JAVAC (
    if defined JAVA_HOME if exist "%JAVA_HOME%\bin\javac.exe" set "JAVAC=%JAVA_HOME%\bin\javac.exe"
)

if not defined JAVAC (
    echo ERROR: Java compiler (javac) not found.
    echo.
    echo To compile servlets you must install a JDK (Java 8+ recommended for this codebase).
    echo Then ensure either:
    echo   - javac is on PATH, OR
    echo   - JAVA_HOME is set to your JDK directory
    echo.
    exit /b 1
)

echo Using Java compiler: "%JAVAC%"
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
