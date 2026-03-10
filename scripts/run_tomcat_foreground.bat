@echo off
set "JAVA_HOME=C:\IW_IDE\IW_Launcher\jre"
set "JRE_HOME=C:\IW_IDE\IW_Launcher\jre"
set "CATALINA_HOME=C:\IW_IDE\IW_Launcher\web_portal\tomcat"
set "CATALINA_OUT=%CATALINA_HOME%\logs\catalina.out"

echo Starting Tomcat (foreground)...
echo JAVA_HOME=%JAVA_HOME%
echo CATALINA_HOME=%CATALINA_HOME%

cd /d "%CATALINA_HOME%\bin"
call catalina.bat run
