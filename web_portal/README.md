# InterWeave Web Portal (iw-business-daemon)

Self-contained web portal for InterWeave IDE integration.

## Quick Start

### Windows
1. Double-click `start_web_portal.bat`
2. Browser opens automatically to the login page
3. To stop: double-click `stop_web_portal.bat`

### Linux / Mac / WSL
```bash
./start_web_portal.sh
# To stop:
./stop_web_portal.sh
```

## Access URLs

Once started, access the portal at:

| Page | URL |
|------|-----|
| **Login** | http://localhost:9090/iw-business-daemon/IWLogin.jsp |
| Edit Profile | http://localhost:9090/iw-business-daemon/EditProfile.jsp |
| Edit Company | http://localhost:9090/iw-business-daemon/EditCompanyProfile.jsp |
| Company Config | http://localhost:9090/iw-business-daemon/CompanyConfiguration.jsp |
| BD Configurator | http://localhost:9090/iw-business-daemon/BDConfigurator.jsp |
| Registration | http://localhost:9090/iw-business-daemon/Registration.jsp |
| Company Registration | http://localhost:9090/iw-business-daemon/CompanyRegistration.jsp |

## Requirements

### Windows
- **None (runtime).** Uses bundled JRE from `../jre/`.
- If you cloned this repo with Git: you must have **Git LFS** installed and run `git lfs pull` so the bundled Tomcat JARs are actually present.

### Linux / Mac
- Java 8+ installed (`java` in PATH or `JAVA_HOME` set)

## Changing the Port

Default port is **9090**. To change it:

1. Edit `tomcat/conf/server.xml`
2. Find: `<Connector port="9090" ...`
3. Change `9090` to your desired port
4. Restart the server

## Directory Structure

```
web_portal/
├── start_web_portal.bat    # Windows start script (wrapper)
├── stop_web_portal.bat     # Windows stop script (wrapper)
├── start_web_portal.sh     # Linux/Mac/WSL start script (wrapper)
├── stop_web_portal.sh      # Linux/Mac/WSL stop script (wrapper)
├── README.md               # This file
└── tomcat/                 # Apache Tomcat 9.0.83 (bundled)
    ├── bin/                # Tomcat binaries
    ├── conf/               # Configuration (server.xml, etc.)
    ├── logs/               # Server logs
    ├── webapps/            # Deployed applications
    │   └── iw-business-daemon.war
    └── ...
```

Note: the wrapper scripts call into the `_internal/` scripts at the repo root.

## Logs

Server logs are in `tomcat/logs/`:
- `catalina.out` - Main server log
- `localhost.*.log` - Application logs

## Troubleshooting

### Port already in use
```
Error: Address already in use
```
Another application is using port 9090. Either:
- Stop that application, or
- Change this portal's port (see above)

### Java not found (Linux/Mac)
```
Error: JAVA_HOME is not defined
```
Install Java: `sudo apt install openjdk-11-jdk` (Ubuntu/Debian)

### Windows: Script blocked
Right-click → Properties → Unblock, or run as Administrator.
