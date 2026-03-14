# 🔧 InterWeave IDE - Tomcat Installation Guide

## Issue: Tomcat Not Found

When you ran `START.bat`, you saw an Apache 404 error because **Tomcat's executable files are missing**. The web_portal/tomcat directory only has configuration files, not the startup scripts.

---

## ✅ SOLUTION: Install Tomcat 9.0.83

### Option 1: Automatic Installation (Recommended) ⚡

1. **Double-click:** `scripts\setup\install_tomcat.bat`
2. **Wait for completion** (may take 5-15 minutes depending on internet speed)
3. The script downloads Tomcat 9.0.83 and extracts it automatically
4. When done, run `START.bat` again

**What happens:**

- Downloads Apache Tomcat 9.0.83 (200MB)
- Extracts files to `web_portal/tomcat/`
- Verifies installation
- Ready to launch!

---

### Option 2: Manual Download (If Automatic Fails)

If `scripts\setup\install_tomcat.bat` doesn't work, download manually:

#### Step 1: Download Tomcat

**URL:** https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.83/bin/apache-tomcat-9.0.83-windows-x64.zip

**Save to:** `C:\IW_Launcher\tomcat-9.0.83.zip`

#### Step 2: Extract

1. Right-click the ZIP file
2. Select "Extract All..."
3. Choose: `C:\IW_Launcher\`
4. Extract

#### Step 3: Move Files

```
From: C:\IW_Launcher\apache-tomcat-9.0.83\bin
To:   C:\IW_Launcher\web_portal\tomcat\bin

From: C:\IW_Launcher\apache-tomcat-9.0.83\lib
To:   C:\IW_Launcher\web_portal\tomcat\lib
```

#### Step 4: Cleanup

1. Delete: `C:\IW_Launcher\apache-tomcat-9.0.83\` folder
2. Delete: `C:\IW_Launcher\tomcat-9.0.83.zip`

#### Step 5: Verify

Check if this file exists:

```
C:\IW_Launcher\web_portal\tomcat\bin\startup.bat
```

If yes, you're ready!

---

## ▶️ After Installation: Launch the App

> **IMPORTANT:** Tomcat must run from **Windows natively** (PowerShell or CMD), not from WSL2.
> WSL2 cannot reach the Supabase database due to IPv6/networking limitations.

Once Tomcat is installed:

```powershell
# From Windows PowerShell — double-click or run:
C:\IW_Launcher\START.bat

# Or start Tomcat directly:
C:\IW_Launcher\web_portal\tomcat\bin\startup.bat
```

**You should see:**

1. ✅ Console window showing "Waiting for server..."
2. ✅ Browser opens to login page (http://localhost:9090/iw-business-daemon/)
3. ✅ Eclipse IDE launches (may take 30-60 seconds)

**Login with:**

- Username: `__iw_admin__`
- Password: `%iwps%`

---

## 🆘 Troubleshooting

### Tomcat still won't start

**Check location:** Verify that this file exists:

```
C:\IW_Launcher\web_portal\tomcat\bin\startup.bat
```

If not, re-run the installation.

### Port 9090 already in use

If another application uses port 9090:

1. Close the other application, OR
2. Change Tomcat port in `web_portal/tomcat/conf/server.xml`
   - Find: `<Connector port="9090"`
   - Change `9090` to `9091` (or any free port)
   - Update browser URL accordingly

### No internet connection?

You can run in **offline mode**:

1. Run `CHANGE_DATABASE.bat`
2. Select option 3: "Local (offline, admin only)"
3. Login with admin account: `__iw_admin__` / `%iwps%`
4. Web portal available but no external data sync

---

## 📊 Installation Status

| Component     | Status            | File                                |
| ------------- | ----------------- | ----------------------------------- |
| IDE           | ✅ Ready          | `iw_ide.exe`                        |
| Java          | ✅ Ready          | `jre/bin/java.exe`                  |
| Configuration | ✅ Ready          | `.env`                              |
| Web App       | ✅ Ready          | `web_portal/tomcat/webapps/`        |
| **Tomcat**    | ⏳ **Installing** | `web_portal/tomcat/bin/startup.bat` |

---

## 💡 Why This Happened

Tomcat executables are large (~200MB) and are typically downloaded separately rather than stored in the repository. The installation script automates this for you.

---

## ✨ Next Steps

1. **Option 1:** Wait for `scripts\setup\install_tomcat.bat` to complete (check if `bin/` directory appears)
2. **Option 2:** Use the manual download instructions above
3. Once Tomcat is installed, run `START.bat`

**Everything else is already configured and ready!** 🎉
