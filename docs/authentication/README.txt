================================================================================
                    IW_IDE AUTHENTICATION OPTIONS
================================================================================

This directory contains documentation and configuration templates for the
authentication methods available in IW_IDE.

FILES IN THIS DIRECTORY
-----------------------
README.txt                          - This file
ADMIN_CREDENTIALS.txt               - Admin account credentials (always works)
HOSTED_DATABASE_SETUP.txt           - InterWeave hosted DB setup info
config.xml.local.template           - Local mode (admin accounts only)
config.xml.oracle_cloud.template    - Oracle Cloud MySQL (your server)
config.xml.hosted.template          - InterWeave hosted MySQL

================================================================================
                         OPTION COMPARISON
================================================================================

+--------+---------------------+------------------+---------------------------+
| Option | Method              | Use Case         | Pros/Cons                 |
+--------+---------------------+------------------+---------------------------+
| 1      | Admin Accounts      | Quick access,    | + No setup required       |
|        | (IsHosted="0")      | development      | + Works immediately       |
|        |                     |                  | - No user management      |
|        |                     |                  | - Single shared account   |
+--------+---------------------+------------------+---------------------------+
| 2      | Supabase Postgres   | Shared team DB   | + Cloud managed           |
|        | (IsHosted="1")      | (recommended)    | + Multi-user support      |
|        |                     |                  | - Requires internet       |
+--------+---------------------+------------------+---------------------------+
| 3      | InterWeave MySQL    | Hosted storage   | + Official IW server      |
|        | (IsHosted="1")      | (future use)     | - External dependency     |
+--------+---------------------+------------------+---------------------------+
| 4      | Oracle Cloud MySQL  | Legacy / optional| + Full control            |
|        | (IsHosted="1")      |                  | - Requires MySQL setup    |
+--------+---------------------+------------------+---------------------------+

================================================================================
                    QUICK START BY OPTION
================================================================================

OPTION 1: ADMIN ACCESS (Immediate, No Database)
-----------------------------------------------
1. Ensure config.xml has IsHosted="0"
   (Use config.xml.local.template)
2. Go to: http://localhost:9090/iw-business-daemon/IWLogin.jsp
3. Username: __iw_admin__
4. Password: %iwps%

OPTION 2: SUPABASE POSTGRES (Recommended)
------------------------------------------
1. Edit .env and set:
   DB_MODE=supabase
   SUPABASE_DB_HOST=db.hpodmkchdzwjtlnxjohf.supabase.co
   SUPABASE_DB_PORT=5432
   SUPABASE_DB_NAME=postgres
   SUPABASE_DB_USER=postgres
   SUPABASE_DB_PASSWORD=<your_password>

2. Run setup script (optional if you use START.bat):
   Windows: SETUP_DB_Windows.bat
   Linux:   ./SETUP_DB_Linux.sh

3. Apply database schema:
   Use Supabase SQL editor to run database/postgres_schema.sql

4. Login with admin or create new users in the database

OPTION 3: INTERWEAVE HOSTED (Requires IW Credentials)
-----------------------------------------------------
1. Contact InterWeave: (800) 671-8692 x 101
2. Obtain: hostedUser, hostedPassword, hostedDBName

3. Edit .env and set:
   DB_MODE=interweave
   IW_DB_HOST=148.62.63.8
   IW_DB_PORT=3306
   IW_DB_NAME=hostedprofiles
   IW_DB_USER=<from_interweave>
   IW_DB_PASSWORD=<from_interweave>

4. Run setup script (or run CHANGE_DATABASE.bat):
   Windows: SETUP_DB_Windows.bat
   Linux:   ./SETUP_DB_Linux.sh

WARNING: InterWeave's MySQL may only accept localhost connections.
         If you get "Host is not allowed to connect" errors, use Supabase.

OPTION 4: ORACLE CLOUD MYSQL (Legacy)
-------------------------------------
1. Edit .env and set:
   DB_MODE=oracle_cloud
   ORACLE_DB_HOST=129.153.47.225
   ORACLE_DB_PORT=3306
   ORACLE_DB_NAME=iw_ide
   ORACLE_DB_USER=iw_admin
   ORACLE_DB_PASSWORD=<your_password>

2. Run setup script:
   Windows: SETUP_DB_Windows.bat
   Linux:   ./SETUP_DB_Linux.sh

3. Apply database schema:
   mysql -h 129.153.47.225 -u iw_admin -p iw_ide < database/mysql_schema.sql

================================================================================
                    SWITCHING BETWEEN MODES
================================================================================

To switch between Supabase, InterWeave, or Oracle Cloud databases:

1. Edit .env file
2. Change DB_MODE to 'supabase', 'interweave', or 'oracle_cloud'
3. Re-run the setup script (SETUP_DB_Windows.bat or ./SETUP_DB_Linux.sh)
4. Restart Tomcat

The setup script will:
- Update context.xml with correct connection settings
- Update config.xml with correct hosted database credentials

================================================================================
                    CURRENT CONFIGURATION STATUS
================================================================================

Current web.xml servlet mapping: LocalLoginServlet (SQL-based)
Current database driver:         org.postgresql.Driver (Supabase) or com.mysql.cj.jdbc.Driver (MySQL)

Key files that control authentication:
  - .env                                    -> Database mode and credentials
  - web_portal/tomcat/conf/context.xml      -> Tomcat connection pool
  - web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/config.xml -> IsHosted flag

================================================================================
                         ARCHITECTURE NOTES
================================================================================

LoginServlet Authentication Flow:
1. Check for admin accounts (__iw_admin__, etc.) -> bypass DB
2. If IsHosted="1", query hosted database (Supabase or MySQL)
3. If IsHosted="0", hosted queries fail (use admin accounts only)
4. On success, redirect to CompanyConfiguration.jsp with CurrentProfile & Solution params

Key Session Attributes:
- userId, userEmail, userName
- companyId, companyName
- solutionType, authToken
- authenticated (boolean)

Key URL Parameters (for JSP pages):
- CurrentProfile: user:company format
- Solution: solution type code (QB, SF, CRM, etc.)
- PortalBrand, PortalSolutions

================================================================================
                         DATABASE SCHEMA
================================================================================

The MySQL schema (database/mysql_schema.sql) includes:

Authentication Tables:
- companies     - Company/organization profiles
- users         - Individual user accounts
- profiles      - Extended user settings (SF, QB credentials)
- company_credentials - Third-party API credentials
- solutions     - Available integration types
- company_solutions - Which solutions each company can use

Application Tables:
- projects      - Project metadata
- pages         - Java page definitions
- forms         - Webform definitions
- transformations - XSLT transformation records
- execution_log - Transformation run history

================================================================================
