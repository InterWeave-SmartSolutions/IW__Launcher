@echo off
REM =============================================================================
REM IW_IDE - START
REM =============================================================================
REM Double-click this to start the application.
REM First time? It will set up everything automatically.
REM =============================================================================

setlocal EnableExtensions DisableDelayedExpansion
title IW_IDE
mode con: cols=70 lines=30

set "IW_HOME=%~dp0"
if "%IW_HOME:~-1%"=="\" set "IW_HOME=%IW_HOME:~0,-1%"

set "JAVA_HOME=%IW_HOME%\jre"
set "JRE_HOME=%IW_HOME%\jre"
set "CATALINA_HOME=%IW_HOME%\web_portal\tomcat"
set "FIRST_RUN=0"

cls
echo.
echo  ==================================================================
echo.
echo       IW_IDE - InterWeave Integration Platform
echo.
echo  ==================================================================
echo.

REM ========== FIRST TIME SETUP (if needed) ==========
if not exist "%IW_HOME%\.env" (
    echo  First time setup - please wait...
    echo.

    if exist "%IW_HOME%\.env.example" (
        copy "%IW_HOME%\.env.example" "%IW_HOME%\.env" >nul
        echo  [OK] Configuration created
        set "FIRST_RUN=1"
    ) else (
        echo  [ERROR] Configuration template missing!
        pause
        exit /b 1
    )
)

REM ========== CONFIGURE DATABASE (every run) ==========
call :configure_db
if errorlevel 1 exit /b 1

if "%FIRST_RUN%"=="1" (
    echo.
    echo  Setup complete! Starting application...
    echo.
    timeout /t 2 >nul
)

REM ========== VERIFY REQUIREMENTS ==========
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo  [ERROR] Java runtime not found!
    echo.
    echo  Expected: %JAVA_HOME%\bin\java.exe
    echo  Expected: %JAVA_HOME%\bin\javaw.exe
    echo.
    echo  The IDE launcher ^(iw_ide.exe^) requires a full Windows JRE under the local jre\ folder.
    echo  Fix options:
    echo    1^) Extract a Windows x86 ^(32-bit^) JRE 8 into this repo's jre\ folder so it contains bin\java.exe and bin\javaw.exe
    echo    2^) Install Java 8 ^(32-bit^) on this machine and update iw_ide.ini to point -vm to its javaw.exe
    echo.
    pause
    exit /b 1
)

for %%F in ("%JAVA_HOME%\bin\java.exe") do set "JAVA_SIZE=%%~zF"
if defined JAVA_SIZE if %JAVA_SIZE% LSS 100000 (
    echo  [ERROR] Java runtime appears to be a Git LFS placeholder.
    echo  Please run: git lfs install ^&^& git lfs pull
    pause
    exit /b 1
)

if not exist "%CATALINA_HOME%\bin\catalina.bat" (
    echo  [ERROR] Web server not found!
    echo.
    echo  Install Tomcat with:
    echo    scripts\setup\install_tomcat.bat
    echo.
    pause
    exit /b 1
)

for %%F in ("%CATALINA_HOME%\lib\catalina.jar") do set "CATALINA_JAR_SIZE=%%~zF"
if defined CATALINA_JAR_SIZE if %CATALINA_JAR_SIZE% LSS 100000 (
    echo  [ERROR] Tomcat libraries appear to be missing or Git LFS placeholders.
    echo  Please run: git lfs install ^&^& git lfs pull
    pause
    exit /b 1
)

REM ========== PREPARE LEGACY RUNTIME ========== 
echo  Preparing legacy runtime...
powershell -NoProfile -ExecutionPolicy Bypass -File "%IW_HOME%\scripts\setup\prepare_legacy_runtime.ps1"
if errorlevel 1 (
    echo  [ERROR] Legacy runtime preparation failed.
    pause
    exit /b 1
)

REM ========== START APPLICATION ========== 
echo  Starting...
echo.

cd /d "%CATALINA_HOME%\bin"
call startup.bat >nul 2>&1

echo  Waiting for server...
echo.
set /a counter=0
set /a max_wait=60

:wait_loop
timeout /t 2 /nobreak >nul
set /a counter+=1

powershell -Command "try { Invoke-WebRequest -Uri 'http://localhost:9090/iw-business-daemon/IWLogin.jsp' -UseBasicParsing -TimeoutSec 2 | Out-Null; exit 0 } catch { exit 1 }" >nul 2>&1
if %errorlevel%==0 goto server_ready

if %counter% geq %max_wait% goto open_browser

echo     (%counter%/%max_wait%)
goto wait_loop

:server_ready
echo  Server ready!
echo.
echo  Syncing workspace profile mirrors...
powershell -Command "try { Invoke-WebRequest -Uri 'http://localhost:9090/iw-business-daemon/WorkspaceProfileSyncServlet?action=exportAll' -UseBasicParsing -TimeoutSec 10 | Out-Null; exit 0 } catch { exit 0 }" >nul 2>&1
echo  Compiling generated profile overlays...
powershell -Command "try { Invoke-WebRequest -Uri 'http://localhost:9090/iw-business-daemon/WorkspaceProfileCompilerServlet?action=compileAll' -UseBasicParsing -TimeoutSec 20 | Out-Null; exit 0 } catch { exit 0 }" >nul 2>&1
echo.

:open_browser
start "" "http://localhost:9090/iw-portal/"

REM Ensure all workspace projects are visible in IDE Navigator
call "%IW_HOME%\scripts\setup\ensure_workspace_projects.bat"

REM Launch IDE
cd /d "%IW_HOME%"
if exist "%IW_HOME%\iw_ide.exe" (
    start "" "%IW_HOME%\iw_ide.exe"
)

REM Start bidirectional sync bridge (IDE <-> Web Portal)
echo  Starting sync bridge...
start "IW Sync Bridge" /min powershell -NoProfile -ExecutionPolicy Bypass -File "%IW_HOME%\scripts\sync_bridge.ps1" -Quiet

echo  ==================================================================
echo.
echo       IW_IDE IS RUNNING
echo.
echo  ==================================================================
echo.
echo   Browser opened to login page.
echo.
echo   Login:
echo      Username: __iw_admin__
echo      Password: %%iwps%%
echo.
echo  ------------------------------------------------------------------
echo   Sync bridge: IDE changes auto-sync to portal
echo   To stop: Run STOP.bat
echo  ==================================================================
echo.
echo  Launcher complete. This window can be closed.
timeout /t 3 >nul

endlocal
exit /b 0

REM ============================================================================
REM CONFIGURATION HELPERS
REM ============================================================================
:configure_db
    REM Load DB_MODE from .env
    for /f "tokens=1,* delims==" %%a in ('findstr /B "DB_MODE=" "%IW_HOME%\.env"') do set "DB_MODE=%%b"
    if not defined DB_MODE set "DB_MODE=supabase"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "TS_MODE=" "%IW_HOME%\.env"') do set "TS_MODE=%%b"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "TS_BASE_LOCAL=" "%IW_HOME%\.env"') do set "TS_BASE_LOCAL=%%b"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "TS_BASE_LEGACY=" "%IW_HOME%\.env"') do set "TS_BASE_LEGACY=%%b"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "TS_FAILOVER_LOCAL=" "%IW_HOME%\.env"') do set "TS_FAILOVER_LOCAL=%%b"
    for /f "tokens=1,* delims==" %%a in ('findstr /B "TS_FAILOVER_LEGACY=" "%IW_HOME%\.env"') do set "TS_FAILOVER_LEGACY=%%b"
    if not defined TS_MODE set "TS_MODE=local"
    if not defined TS_BASE_LOCAL set "TS_BASE_LOCAL=http://localhost:9090/iwtransformationserver"
    if not defined TS_BASE_LEGACY set "TS_BASE_LEGACY=http://iw0.interweave.biz:9090/iwtransformationserver"
    if not defined TS_FAILOVER_LOCAL set "TS_FAILOVER_LOCAL="
    if not defined TS_FAILOVER_LEGACY set "TS_FAILOVER_LEGACY=http://iw0.interweave.biz:8080/iw-business-daemon/failover"

    if /i "%TS_MODE%"=="legacy" (
        set "TS_BASE_URL=%TS_BASE_LEGACY%"
        set "TS_FAILOVER_URL=%TS_FAILOVER_LEGACY%"
    ) else if /i "%TS_MODE%"=="local" (
        set "TS_BASE_URL=%TS_BASE_LOCAL%"
        set "TS_FAILOVER_URL=%TS_FAILOVER_LOCAL%"
    ) else (
        echo  [ERROR] Unknown TS_MODE: %TS_MODE%
        echo  Valid values: local, legacy
        pause
        exit /b 1
    )

    set "CONTEXT_FILE=%IW_HOME%\web_portal\tomcat\conf\context.xml"
    set "BD_CONFIG=%IW_HOME%\web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\config.xml"

    if /i "%DB_MODE%"=="supabase" (
        REM ---------- SUPABASE - Postgres ----------
        for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_HOST=" "%IW_HOME%\.env"') do set "DB_HOST=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_PORT=" "%IW_HOME%\.env"') do set "DB_PORT=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_NAME=" "%IW_HOME%\.env"') do set "DB_NAME=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_USER=" "%IW_HOME%\.env"') do set "DB_USER=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_DB_PASSWORD=" "%IW_HOME%\.env"') do set "DB_PASSWORD=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_POOLER_HOST=" "%IW_HOME%\.env"') do set "POOLER_HOST=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_POOLER_PORT=" "%IW_HOME%\.env"') do set "POOLER_PORT=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "SUPABASE_POOLER_USER=" "%IW_HOME%\.env"') do set "POOLER_USER=%%b"
        if not defined DB_HOST set "DB_HOST=db.hpodmkchdzwjtlnxjohf.supabase.co"
        if not defined DB_PORT set "DB_PORT=5432"
        if not defined DB_NAME set "DB_NAME=postgres"
        if not defined POOLER_PORT set "POOLER_PORT=6543"

        set "CONTEXT_TEMPLATE=%IW_HOME%\web_portal\tomcat\conf\context.xml.postgres"
        set "CONFIG_TEMPLATE=%IW_HOME%\docs\authentication\config.xml.supabase.template"
        set "PASSWORD_KEY=SUPABASE_DB_PASSWORD"
    ) else if /i "%DB_MODE%"=="interweave" (
        REM ---------- INTERWEAVE - MySQL ----------
        for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_HOST=" "%IW_HOME%\.env"') do set "DB_HOST=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_PORT=" "%IW_HOME%\.env"') do set "DB_PORT=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_NAME=" "%IW_HOME%\.env"') do set "DB_NAME=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_USER=" "%IW_HOME%\.env"') do set "DB_USER=%%b"
        for /f "tokens=1,* delims==" %%a in ('findstr /B "IW_DB_PASSWORD=" "%IW_HOME%\.env"') do set "DB_PASSWORD=%%b"
        if not defined DB_HOST set "DB_HOST=***********"
        if not defined DB_PORT set "DB_PORT=3306"
        if not defined DB_NAME set "DB_NAME=hostedprofiles"

        set "CONTEXT_TEMPLATE=%IW_HOME%\web_portal\tomcat\conf\context.xml.mysql"
        set "CONFIG_TEMPLATE=%IW_HOME%\docs\authentication\config.xml.hosted.template"
        set "PASSWORD_KEY=IW_DB_PASSWORD"
    ) else if /i "%DB_MODE%"=="local" (
        REM ---------- LOCAL / OFFLINE ----------
        set "CONFIG_TEMPLATE=%IW_HOME%\docs\authentication\config.xml.local.template"
        set "CONTEXT_TEMPLATE="
        set "DB_USER="
        set "DB_PASSWORD="
        set "PASSWORD_KEY="
    ) else (
        echo  [ERROR] Unknown DB_MODE: %DB_MODE%
        echo  Valid values: supabase, interweave, local
        pause
        exit /b 1
    )

    REM Validate required credentials
    if /i not "%DB_MODE%"=="local" (
        if not defined DB_USER (
            echo  [ERROR] Missing DB user for %DB_MODE%. Edit .env and re-run START.bat
            pause
            exit /b 1
        )
        if not defined DB_PASSWORD (
            echo  [ERROR] Missing DB password for %DB_MODE%. Edit .env and re-run START.bat
            pause
            exit /b 1
        )
    )
    if /i "%DB_MODE%"=="supabase" (
        findstr /B /C:"SUPABASE_DB_PASSWORD=YOUR_SUPABASE_PASSWORD_HERE" "%IW_HOME%\.env" >nul 2>&1
        if not errorlevel 1 (
            echo  [ERROR] Supabase password placeholder detected. Edit .env and re-run START.bat
            pause
            exit /b 1
        )
    )

    REM Supabase direct host may be IPv6-only. If unreachable, fall back to pooler.
    if /i "%DB_MODE%"=="supabase" call :check_supabase_connectivity
    if errorlevel 1 exit /b 1

    REM Verify JDBC driver presence
    if /i "%DB_MODE%"=="supabase" (
        dir /b "%CATALINA_HOME%\lib\postgresql-*.jar" >nul 2>&1
        if errorlevel 1 (
            echo  [ERROR] PostgreSQL JDBC driver missing in web_portal\tomcat\lib
            echo          Expected: postgresql-*.jar
            pause
            exit /b 1
        )
    ) else if /i "%DB_MODE%"=="interweave" (
        dir /b "%CATALINA_HOME%\lib\mysql-connector-java-*.jar" >nul 2>&1
        if errorlevel 1 (
            echo  [ERROR] MySQL JDBC driver missing in web_portal\tomcat\lib
            echo          Expected: mysql-connector-java-*.jar
            pause
            exit /b 1
        )
    )

    REM Configure Tomcat context.xml (skip for local/offline mode)
    if defined CONTEXT_TEMPLATE if exist "%CONTEXT_TEMPLATE%" call :render_context

    REM Configure Business Daemon config.xml
    if exist "%CONFIG_TEMPLATE%" call :render_business_config

    exit /b 0

:render_context
    setlocal DisableDelayedExpansion
    set "IW_RENDER_TEMPLATE=%CONTEXT_TEMPLATE%"
    set "IW_RENDER_OUT=%CONTEXT_FILE%"
    set "IW_RENDER_DB_HOST=%DB_HOST%"
    set "IW_RENDER_DB_PORT=%DB_PORT%"
    set "IW_RENDER_DB_NAME=%DB_NAME%"
    set "IW_RENDER_DB_USER=%DB_USER%"
    set "IW_RENDER_ENV_FILE=%IW_HOME%\.env"
    set "IW_RENDER_PASSWORD_KEY=%PASSWORD_KEY%"
    powershell -NoProfile -Command "$pwdKey = $env:IW_RENDER_PASSWORD_KEY; $pw = ''; if ($pwdKey) { $prefix = $pwdKey + '='; $line = Get-Content $env:IW_RENDER_ENV_FILE | Where-Object { $_.StartsWith($prefix) } | Select-Object -First 1; if ($line) { $pw = $line.Substring($prefix.Length) } }; $c = Get-Content $env:IW_RENDER_TEMPLATE -Raw; $c = $c.Replace('__DB_HOST__', $env:IW_RENDER_DB_HOST); $c = $c.Replace('__DB_PORT__', $env:IW_RENDER_DB_PORT); $c = $c.Replace('__DB_NAME__', $env:IW_RENDER_DB_NAME); $c = $c.Replace('__DB_USER__', $env:IW_RENDER_DB_USER); $c = $c.Replace('__DB_PASSWORD__', $pw); $c = $c.Replace('__SUPABASE_HOST__', $env:IW_RENDER_DB_HOST); $c = $c.Replace('__SUPABASE_PORT__', $env:IW_RENDER_DB_PORT); $c = $c.Replace('__SUPABASE_DB_NAME__', $env:IW_RENDER_DB_NAME); $c = $c.Replace('__SUPABASE_USER__', $env:IW_RENDER_DB_USER); $c = $c.Replace('__SUPABASE_PASSWORD__', $pw); Set-Content $env:IW_RENDER_OUT -Value $c" 2>nul
    endlocal
    echo  [OK] Database configured (%DB_MODE%)
    exit /b 0

:render_business_config
    setlocal DisableDelayedExpansion
    set "IW_RENDER_TEMPLATE=%CONFIG_TEMPLATE%"
    set "IW_RENDER_OUT=%BD_CONFIG%"
    set "IW_RENDER_DB_HOST=%DB_HOST%"
    set "IW_RENDER_DB_PORT=%DB_PORT%"
    set "IW_RENDER_DB_NAME=%DB_NAME%"
    set "IW_RENDER_DB_USER=%DB_USER%"
    set "IW_RENDER_ENV_FILE=%IW_HOME%\.env"
    set "IW_RENDER_PASSWORD_KEY=%PASSWORD_KEY%"
    set "IW_RENDER_TS_BASE=%TS_BASE_URL%"
    set "IW_RENDER_FAILOVER=%TS_FAILOVER_URL%"
    powershell -NoProfile -Command "$pwdKey = $env:IW_RENDER_PASSWORD_KEY; $pw = ''; if ($pwdKey) { $prefix = $pwdKey + '='; $line = Get-Content $env:IW_RENDER_ENV_FILE | Where-Object { $_.StartsWith($prefix) } | Select-Object -First 1; if ($line) { $pw = $line.Substring($prefix.Length) } }; $c = Get-Content $env:IW_RENDER_TEMPLATE -Raw; $c = $c.Replace('__DB_HOST__', $env:IW_RENDER_DB_HOST); $c = $c.Replace('__DB_PORT__', $env:IW_RENDER_DB_PORT); $c = $c.Replace('__DB_NAME__', $env:IW_RENDER_DB_NAME); $c = $c.Replace('__DB_USER__', $env:IW_RENDER_DB_USER); $c = $c.Replace('__DB_PASSWORD__', $pw); $c = $c.Replace('__SUPABASE_HOST__', $env:IW_RENDER_DB_HOST); $c = $c.Replace('__SUPABASE_PORT__', $env:IW_RENDER_DB_PORT); $c = $c.Replace('__SUPABASE_DB_NAME__', $env:IW_RENDER_DB_NAME); $c = $c.Replace('__SUPABASE_USER__', $env:IW_RENDER_DB_USER); $c = $c.Replace('__SUPABASE_PASSWORD__', $pw); $c = $c.Replace('__TS_BASE_URL__', $env:IW_RENDER_TS_BASE); $c = $c.Replace('__FAILOVER_URL__', $env:IW_RENDER_FAILOVER); $c = $c.Replace('YOUR_ORACLE_PASSWORD_HERE', $pw); $c = $c.Replace('YOUR_IW_USERNAME', $env:IW_RENDER_DB_USER); $c = $c.Replace('YOUR_IW_PASSWORD', $pw); Set-Content $env:IW_RENDER_OUT -Value $c" 2>nul
    endlocal
    echo  [OK] Login system configured
    echo  [OK] Transformation endpoints configured (%TS_MODE%)
    exit /b 0

:check_supabase_connectivity
    REM Strategy: test pooler first (IPv4, works on most networks), then direct.
    REM Uses a 4-second TCP socket timeout to avoid long waits.
    if not defined POOLER_HOST goto :check_direct
    if not defined POOLER_USER goto :check_direct

    REM --- Try pooler first (fast path for most users) ---
    echo  Checking Supabase pooler...
    set "PS_TEST_HOST=%POOLER_HOST%"
    set "PS_TEST_PORT=%POOLER_PORT%"
    set "CONN_REACHABLE="
    for /f %%i in ('powershell -NoProfile -Command "try { $c = New-Object System.Net.Sockets.TcpClient; $r = $c.BeginConnect($env:PS_TEST_HOST, [int]$env:PS_TEST_PORT, $null, $null); $ok = $r.AsyncWaitHandle.WaitOne(4000); $c.Close(); if ($ok) { 'True' } else { 'False' } } catch { 'False' }" 2^>nul') do set "CONN_REACHABLE=%%i"
    if /i "%CONN_REACHABLE%"=="True" goto :use_pooler
    goto :check_direct

:use_pooler
    echo  [OK] Supabase pooler reachable
    set "DB_HOST=%POOLER_HOST%"
    set "DB_PORT=%POOLER_PORT%"
    set "DB_USER=%POOLER_USER%"
    exit /b 0

:check_direct
    echo  Checking Supabase direct host...
    set "PS_TEST_HOST=%DB_HOST%"
    set "PS_TEST_PORT=%DB_PORT%"
    set "CONN_REACHABLE="
    for /f %%i in ('powershell -NoProfile -Command "try { $c = New-Object System.Net.Sockets.TcpClient; $r = $c.BeginConnect($env:PS_TEST_HOST, [int]$env:PS_TEST_PORT, $null, $null); $ok = $r.AsyncWaitHandle.WaitOne(4000); $c.Close(); if ($ok) { 'True' } else { 'False' } } catch { 'False' }" 2^>nul') do set "CONN_REACHABLE=%%i"
    if /i "%CONN_REACHABLE%"=="True" goto :use_direct
    goto :check_failed

:use_direct
    echo  [OK] Supabase direct host reachable
    exit /b 0

:check_failed
    echo.
    echo  [ERROR] Cannot reach Supabase database.
    echo.
    echo          Tried:
    echo            - Pooler:  %POOLER_HOST%:%POOLER_PORT%
    echo            - Direct:  %DB_HOST%:%DB_PORT%
    echo.
    echo          Possible causes:
    echo            - No internet connection
    echo            - Firewall blocking outbound PostgreSQL ports
    echo            - VPN/proxy interference
    echo.
    echo          To skip this check, set DB_MODE=local in .env
    echo.
    pause
    exit /b 1
