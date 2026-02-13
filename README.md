# InterWeave IDE (IW_IDE)

Enterprise data integration platform for creating synchronization workflows between business applications.

## InterWoven (concept snapshot)
The `InterWoven/` directory (when present) is an imported concept/prototype for future improvements to the IDE launcher and Java form web pages.

IMPORTANT: Do not use, read, or reference anything in `InterWoven/` unless the user explicitly asks you to.

---

## How to Use (3 Simple Scripts)

| Script | What It Does |
|--------|--------------|
| `START.bat` | **Start the application** - Double-click this! |
| `STOP.bat` | **Stop the application** |
| `CHANGE_DATABASE.bat` | Change database connection (optional) |

That's it! Just double-click `START.bat` to begin.

---

## Getting This Working on a New Windows Machine (Important)

### Option A (Recommended): Use a ZIP / Release Artifact
If you have a pre-built ZIP that contains `IW_Launcher/` (including `jre/` and `web_portal/tomcat/`), you should be able to run without installing Java/Tomcat/Maven.

### Option B: Clone the repo with Git (Developers)
This repository stores several **binary files** via **Git LFS** (Large File Storage) — including `*.exe` and many `*.jar` files.

If you clone without Git LFS, Windows will download tiny placeholder “pointer” files instead of the real binaries, and the app will fail to start.

1. Install **Git for Windows**
2. Install **Git LFS**
3. In the repo folder, run:
   - `git lfs install`
   - `git lfs pull`

### Quick “sanity check” (did LFS download correctly?)
Verify these exist and are NOT tiny files:
- `jre\bin\java.exe`
- `web_portal\tomcat\bin\catalina.bat`
- `web_portal\tomcat\lib\catalina.jar` (should be MBs, not a small text file)

---

## Do I Need to Install Java / Tomcat / Maven?

### Just running the app (most users)
- **No.** This launcher uses the bundled runtime in `jre/` and the bundled Tomcat in `web_portal/tomcat/`.

### Building / modifying code (developers)
Install these on Windows:
- **JDK 8+** (required for `javac`)
- **Apache Maven 3.6+** (required for `mvn`)

You only need Apache Ant if you plan to rebuild Tomcat itself (normally you do not).

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
