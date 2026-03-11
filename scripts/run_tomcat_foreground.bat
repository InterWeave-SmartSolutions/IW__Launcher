@echo off
for %%i in ("%~dp0..") do set "IW_ROOT=%%~fi"
set "JAVA_HOME=%IW_ROOT%\jre"
set "JRE_HOME=%IW_ROOT%\jre"
set "CATALINA_HOME=%IW_ROOT%\web_portal\tomcat"
set "CATALINA_OUT=%CATALINA_HOME%\logs\catalina.out"

echo Starting Tomcat (foreground)...
echo JAVA_HOME=%JAVA_HOME%
echo CATALINA_HOME=%CATALINA_HOME%

cd /d "%CATALINA_HOME%\bin"
call catalina.bat run
