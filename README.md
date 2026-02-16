# InterWeave IDE (IW_IDE)

Enterprise data integration platform for creating synchronization workflows between business applications.

---

## How to Use (3 Simple Scripts)

| Script | What It Does |
|--------|--------------|
| `START.bat` | **Start the application** - Double-click this! |
| `STOP.bat` | **Stop the application** |
| `CHANGE_DATABASE.bat` | Change database connection (optional) |

That's it! Just double-click `START.bat` to begin.

---

## First Time?

1. Double-click `START.bat`
2. Wait for browser to open
3. Login with:
   - **Username:** `__iw_admin__`
   - **Password:** `%iwps%`

The first time you run `START.bat`, it automatically configures everything for you.

---

## When You're Done

Either:
- Press any key in the black window to stop, OR
- Double-click `STOP.bat`

---

## Need to Change Database?

Double-click `CHANGE_DATABASE.bat` to switch between:
- **Your Server** (Oracle Cloud) - Recommended
- **InterWeave Server** - May be blocked
- **Offline Mode** - No database needed

---

## Troubleshooting

### Nothing happens when I click START.bat
- Right-click → Run as Administrator

### Browser doesn't open
- Wait 30 seconds, then go to: http://localhost:8080/iw-business-daemon/IWLogin.jsp

### Can't login
- Username: `__iw_admin__`
- Password: `%iwps%`

### Need to reset everything
1. Delete the `.env` file
2. Run `START.bat` again

---

## Building / Developer Setup (Windows)

The app runtime ships with a bundled Java runtime in `jre/` (so end-users typically do **not** need to install Java just to run `START.bat`).

However, **building** Java components and running the Windows build scripts requires:
- A **JDK** (recommended: **Temurin JDK 8**) so `javac` is available
- **Apache Maven** so `mvn` is available on your **Windows** `PATH`

Important:
- `build.bat` checks for Maven using `where mvn`, so having Maven only inside WSL/Linux will **not** satisfy the Windows script.

### Install prerequisites via winget (recommended)
Open **Windows Terminal → PowerShell** and run:

```powershell
winget source update

winget install -e --id EclipseAdoptium.Temurin.8.JDK --accept-package-agreements --accept-source-agreements
winget install -e --id Apache.Maven --accept-package-agreements --accept-source-agreements
```

Close/reopen the terminal, then verify:

```powershell
java -version
javac -version
mvn -v
where.exe mvn
```

### Alternative: Chocolatey (choco)
If you prefer Chocolatey:

```powershell
choco install -y temurin8jdk maven
```

### Alternative: Scoop
If you prefer Scoop:

```powershell
scoop bucket add java
scoop install temurin8-jdk maven
```

### Build command
From the repository root, you can build with Maven:

```powershell
mvn -DskipTests package
```

If you need Maven to **skip compiling tests** as well, use:

```powershell
mvn -Dmaven.test.skip=true package
```

## For Technical Users

Advanced scripts are in the `_internal/` folder:
- `SETUP_DB_Windows.bat` - Manual database setup
- `SETUP_DB_Linux.sh` - Linux/Mac setup
- `start_webportal.bat` - Start web server only
- `start_ide.bat` - Start IDE only
- `stop_webportal.bat` - Stop web server only

---

## Directory Structure

```
IW_Launcher/
├── START.bat              # Start application
├── STOP.bat               # Stop application
├── CHANGE_DATABASE.bat    # Switch database
├── iw_ide.exe             # IDE program
├── .env                   # Your settings (auto-created)
│
├── _internal/             # Technical scripts (don't touch)
├── database/              # Database schemas
├── docs/                  # Documentation
├── jre/                   # Java runtime
├── web_portal/            # Web server
└── workspace/             # Your projects
```

---

## Known Issues

### Custom User Accounts Not Working (TODO)
- The demo user (`demo@sample.com`) was added to the database but login fails
- The compiled `LoginServlet.class` uses a proprietary authentication method
- **Root cause:** The original LoginServlet expects a specific password format/hash that differs from our database schema
- **Workaround:** Use the admin account (`__iw_admin__` / `%iwps%`) for now
- **Future fix:** Either decompile and modify LoginServlet, or create a replacement servlet

### Database Accounts
| Account | Email | Password | Status |
|---------|-------|----------|--------|
| Admin | `__iw_admin__` | `%iwps%` | **WORKING** |
| Demo | `demo@sample.com` | `demo123` | NOT WORKING (auth issue) |

---

## Version History

| Date | Change |
|------|--------|
| 2025-12-10 | **Oracle Cloud MySQL** - Remote database fully configured |
| 2025-12-10 | **Simplified to 3 scripts** - START, STOP, CHANGE_DATABASE |
| 2025-12-10 | **Auto-setup** - START.bat now handles first-time configuration |
| 2025-12-10 | **MySQL Migration** - Using MySQL database |
| 2025-12-04 | Initial Windows portability release |

---

*Technical documentation available in `docs/` folder.*
