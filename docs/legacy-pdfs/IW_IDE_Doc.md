# InterWeave IDE (IW_IDE) - Complete Technical Documentation

**Location:** `C:\Users\amago\Desktop\IW_IDE`

**Version:** 2.2

**Platform:** Enterprise Data Integration & Transformation Platform

**Architecture:** Java-based Eclipse RCP Application with Enterprise Service Bus capabilities

---

## Table of Contents

1. [Executive Overview](#executive-overview)
2. [System Architecture](#system-architecture)
3. [Directory Structure](#directory-structure)
4. [Technical Stack](#technical-stack)
5. [Installation & Setup](#installation--setup)
6. [IDE Configuration](#ide-configuration)
7. [Project Structure](#project-structure)
8. [Integration Patterns](#integration-patterns)
9. [Configuration Files](#configuration-files)
10. [Supported Systems & Connectors](#supported-systems--connectors)
11. [Transaction Descriptions](#transaction-descriptions)
12. [Deployment Architecture](#deployment-architecture)
13. [Data Transformation](#data-transformation)
14. [Sample Projects](#sample-projects)
15. [Runtime Execution Model](#runtime-execution-model)
16. [Performance & Scalability](#performance--scalability)
17. [Troubleshooting & Logs](#troubleshooting--logs)

---

## Executive Overview

### What is InterWeave?

**InterWeave** is an enterprise-grade **data integration and transformation platform** designed to create real-time and scheduled synchronization workflows between business applications. It specializes in:

- **Real-time data synchronization** between multiple business systems
- **ETL (Extract-Transform-Load)** operations at enterprise scale
- **Scheduled batch processing** with stateful checkpoints
- **Bidirectional data flow** with conflict resolution
- **Legacy system integration** through ODBC/JDBC connectors
- **Data format transformation** (XML, CSV, SOAP/Web Services)
- **High availability** with failover and redundancy

### Primary Use Cases

1. **Salesforce ↔ QuickBooks Sync** - Keep sales and accounting in perfect synchronization
2. **Multi-system data orchestration** - Coordinate data flow across 5+ systems
3. **Real-time reporting** - Aggregate data from multiple sources
4. **Legacy modernization** - Bridge old on-premises systems to cloud SaaS platforms
5. **Financial reconciliation** - Automated account/invoice matching and syncing
6. **Opportunity-to-Invoice lifecycle** - Track sales from lead through payment

### Architecture Paradigm

InterWeave operates on a **Business Daemon Architecture**:

- Central daemon manages multiple transformation engines
- Each engine handles specific synchronization workflows
- State is maintained between executions for incremental syncing
- Multiple redundant servers enable high availability
- XSLT transformations allow flexible data mapping

---

## System Architecture

### High-Level Components

```
┌─────────────────────────────────────────────────────────────┐
│                    Eclipse RCP IDE                          │
│         (iw_ide.exe - Development Environment)             │
└─────────────────────────────────────────────────────────────┘
                            │
                            ├─ Project Configuration
                            ├─ XSLT Authoring
                            └─ Deployment Management
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────┐
│              Business Daemon (BD1)                          │
│   (Runtime Integration Manager - iwtransformationserver)   │
│                                                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Scheduled Transformation Engines                   │  │
│  │                                                      │  │
│  │  • SF Accounts ↔ QB Customers                       │  │
│  │  • SF Opportunities ↔ QB Invoices/SO/Estimates    │  │
│  │  • SF Products ↔ QB Items                           │  │
│  │  • Custom Transformations (XSLT-based)             │  │
│  │  • Batch Processing (with state management)         │  │
│  │  • Stateful Processing (incremental updates)        │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐    │
│  │ Heartbeat    │  │ Interval     │  │ Failover     │    │
│  │ Monitor      │  │ Scheduler    │  │ Management   │    │
│  └──────────────┘  └──────────────┘  └──────────────┘    │
└─────────────────────────────────────────────────────────────┘
        │                       │                       │
        │                       │                       │
        ▼                       ▼                       ▼
  ┌──────────────┐      ┌──────────────┐      ┌──────────────┐
  │  Salesforce  │      │ QuickBooks   │      │   MySQL/     │
  │ SOAP API 47.0│      │ QODBC Driver │      │   Database   │
  │              │      │ JDBC Bridge  │      │              │
  └──────────────┘      └──────────────┘      └──────────────┘
```

### Execution Flow

```
1. Configuration Load (config.xml)
   ↓
2. Business Daemon Initialization (BD1)
   ↓
3. Transaction Description Processing
   ├─ Read interval and scheduling parameters
   ├─ Load XSLT transformation templates
   └─ Initialize source/target connectors
   ↓
4. Scheduled Execution
   ├─ Check execution interval
   ├─ Query source system (with QueryStartTime checkpoint)
   ├─ Apply XSLT transformations
   ├─ Validate data mappings
   └─ Write to target system
   ↓
5. State Management
   ├─ Update LastModifiedDate checkpoint
   ├─ Store batch processing state
   └─ Log transaction results
   ↓
6. Error Handling & Retry
   ├─ Log failures
   ├─ Trigger failover if primary fails
   └─ Maintain retry queue
```

---

## Directory Structure

### Complete File Listing

```
C:\Users\amago\Desktop\IW_IDE\
├── Documents/                          # Documentation suite
│   ├── aim_guide.pdf                   # Application Integration Management guide
│   ├── CIM_SOAP_guide.pdf              # SOAP integration reference
│   ├── developerGuide.pdf              # Complete developer documentation
│   ├── echeck.pdf                      # E-check integration guide
│   ├── IDETutorial.pdf                 # Step-by-step IDE tutorial
│   ├── InterWeave%20HelpandTraining.pdf # General help documentation
│   ├── IT Professional Services Agreement - First Universal 06_05_08.pdf
│   ├── IW_IDE_UR.pdf                   # User reference guide
│   └── userGuide.pdf                   # End-user documentation
│
├── iw_ide - Shortcut.lnk               # Desktop shortcut to IDE
│
├── IW_IDE.zip                          # Compressed distribution archive
│
└── IW_IDE_Import/                      # Installation & runtime dependencies
    ├── Altova/                         # XML tools (XMLSpy, etc.)
    ├── Apache Software Foundation/     # Apache license information
    ├── apache-tomcat-5.5.33.exe        # Web server runtime
    ├── baretail.exe                    # Log tail utility
    ├── CAJAWTpU6p/                     # Java AWT graphics libraries
    ├── Configure 32-Bit QODBC Driver.lnk
    ├── IDE Quick Installation Guide _2_.pdf
    ├── IDETutorial.pdf                 # Duplicate tutorial
    ├── inetpub/                        # IIS web server files (if installed)
    ├── IW_IDE/                         # Main IDE runtime and projects
    │   ├── AccpacCom/                  # Accpac accounting integration
    │   ├── all_projects_1/             # First collection of sample/active projects
    │   ├── all_projects_2/             # Second collection of projects
    │   ├── Augur.xml                   # Network monitoring data sample
    │   ├── CMSUpdLead.xslt             # CMS lead update transformation
    │   ├── CompanyTokens.rtf           # Company credential tokens
    │   ├── Copy of SampleSSNetReport.xml
    │   ├── Copy of SampleSSReport.xml
    │   ├── Data/                       # Data storage/cache directory
    │   ├── IWHostedSolutions/          # Hosted integration solutions
    │   ├── iwp2HTMLTableLO.xslt        # XML to HTML transformation
    │   ├── iw_ide/                     # IDE application engine
    │   │   ├── .eclipseproduct         # Eclipse product descriptor
    │   │   ├── configuration/          # IDE configuration
    │   │   ├── iw_ide.exe              # Main executable
    │   │   ├── plugins/                # Eclipse RCP plugins
    │   │   ├── startup.jar             # Bootstrap loader
    │   │   └── workspace/              # Default workspace
    │   ├── IW_QBConnector/             # QuickBooks specific connector
    │   ├── logs.zip                    # Compressed log archive
    │   ├── New folder/                 # Unorganized work folder
    │   ├── NexternalHostedIan/         # Nexternal (ecommerce) integration
    │   ├── NexternalQueryTest/         # Nexternal query testing
    │   ├── NexternalQueryTestD/        # Nexternal debug queries
    │   ├── NexternalQueryTestIan/      # Nexternal personalized tests
    │   ├── OldSampleProjects_1/        # Archived sample projects (v1)
    │   ├── ProfileMgmt.sql             # SQL profile management script
    │   ├── QB_sample/                  # QuickBooks sample projects
    │   │   ├── .metadata/              # Eclipse metadata
    │   │   └── QBODBC1/                # QB ODBC connection sample
    │   ├── readme.txt                  # Installation instructions
    │   ├── SalesProdigy1/              # SalesProdigy integration project
    │   ├── samplegift.csv              # Sample CSV data (gift records)
    │   ├── samplegift_new.csv          # Updated sample CSV
    │   ├── SampleSSNetReport.html      # Sample report output (HTML)
    │   ├── SampleSSReport.html         # Additional sample report
    │   ├── sample_projects/            # Template project directory
    │   │   ├── .metadata/              # Eclipse metadata
    │   │   └── Test/                   # Template test project
    │   │       ├── .deployables/       # Deployment packages
    │   │       ├── .project            # Eclipse project descriptor
    │   │       ├── classes/            # Compiled classes
    │   │       ├── configuration/      # Project config
    │   │       └── xslt/               # Transformation templates
    │   ├── SF2MM/                      # Salesforce to Money Manager sync
    │   ├── SFAccountTestGet.xml        # Salesforce account test data
    │   ├── SN2QBSP/                    # (SalesNet?) to QB integration
    │   ├── SN_Test/                    # (SalesNet?) test project
    │   ├── SN_Test_New/                # Updated (SalesNet?) test
    │   ├── SPworkspace/                # Service Provider workspace
    │   ├── SSProject/                  # Sample Service Server project
    │   ├── Templates/                  # Transformation templates
    │   ├── transactions_old.xml        # Legacy transaction definitions
    │
    ├── jre-1_5_0_22-windows-i586-p.exe # Java Runtime Environment 1.5
    ├── ndp48-web.exe                   # .NET Framework 4.8 web installer
    ├── QODBC 32-Bit Test Tool.lnk      # QODBC testing utility
    ├── QODBC Driver for QuickBooks/    # QB ODBC driver installation
    ├── qodbc24.0.0.356.exe             # QODBC driver executable
    ├── QRemote Server.lnk              # Remote QuickBooks server link
    ├── regid.1991-06.com.microsoft/    # Microsoft registry data
    ├── ProgramData/                    # Windows ProgramData snapshot
    ├── Sites/                          # Web server sites
    ├── SSL/                            # SSL certificate data
    ├── TrappTechnology/                # Trapp Technology (vendor) files
    └── ttt/                            # Additional runtime data
```

---

## Technical Stack

### Core Technologies

| Component              | Version/Details        | Purpose                             |
| ---------------------- | ---------------------- | ----------------------------------- |
| **Runtime Engine**     | Java 1.5+              | Cross-platform execution            |
| **IDE Framework**      | Eclipse RCP            | Rich client application development |
| **Database Access**    | JDBC/ODBC              | Unified data source connectivity    |
| **Data Format**        | XML                    | Primary data interchange            |
| **Transformations**    | XSLT 1.0+              | Data mapping and conversion         |
| **Web Services**       | SOAP                   | Legacy API integration              |
| **Application Server** | Apache Tomcat 5.5.33   | HTTP service hosting                |
| **Primary Database**   | QuickBooks (QODBC)     | Accounting system source            |
| **Primary SaaS**       | Salesforce (v47.0 API) | CRM system source                   |
| **Secondary DB**       | MySQL                  | Extended data source support        |

### Required Dependencies

```
1. Java Runtime Environment (JRE) 1.5+
   └─ JAVA_HOME environment variable must be set

2. Apache Tomcat 5.5.33+
   └─ For scheduled service hosting

3. QODBC Driver 24.0.0.356+
   └─ For QuickBooks database access

4. .NET Framework 4.8
   └─ For Windows integration features

5. Salesforce Account with API Access
   └─ SOAP API v47.0+ endpoint access

6. Eclipse RCP Runtime
   └─ Bundled with iw_ide.exe
```

### Software Components Included

| Tool             | Purpose                          | Status      |
| ---------------- | -------------------------------- | ----------- |
| Altova XML Suite | XML Schema/transformation design | Optional    |
| SQLyog           | MySQL GUI client                 | Optional    |
| BareTail         | Real-time log monitoring         | Optional    |
| QODBC Test Tool  | QB connectivity testing          | Recommended |

---

## Installation & Setup

### System Requirements

- **Operating System:** Windows (32-bit or 64-bit)
- **Memory:** 512 MB minimum, 2 GB recommended
- **Disk Space:** 1 GB for full installation
- **Network:** Connectivity to Salesforce and/or QuickBooks
- **Java:** JRE 1.5+ installed and JAVA_HOME set

### Installation Steps

```bash
# Step 1: Extract the IW_IDE.zip archive
unzip IW_IDE.zip -d C:\CreatioProjects\

# Step 2: Set Java Home environment variable
SET JAVA_HOME=C:\Program Files\Java\jre1.5_0_22

# Step 3: Install QODBC driver (if QB sync needed)
# Run: C:\...\IW_IDE_Import\qodbc24.0.0.356.exe
# Run: C:\...\IW_IDE_Import\Configure 32-Bit QODBC Driver.lnk

# Step 4: Configure database connections
# - Edit: iw_ide/configuration/im/config.xml
# - Set Salesforce SOAP URL
# - Set QB QODBC connection string

# Step 5: Launch IDE
# Double-click: C:\...\iw_ide\iw_ide.exe

# Step 6: Import sample project
# File > Import > Existing Projects into Workspace
# Select: sample_projects directory
# Click: Finish
```

### Quick Installation Reference

**From README.txt:**

```
Interweave Solution Development Kit
IDE Version 2.2

A. System requirements.
   JDK or JRE 1.5 must be installed.
   JAVA_HOME system variable must point to the JDK or JRE 1.5
   installation directory.

B. Installation
   Unzip file iw_sdk.zip into any chosen or created directory
   (installation directory) on your hard drive.

C. Usage
   1. Run iw_ide/iw_ide.exe

   2. Import project from the sample_project directory. For this:

      2.1. Select menu item File->Import

      2.2. Select wizard "Existing Projects into Workspace"
           and click "Next"

      2.3. Choose "Select root directory" radio button
           and click "Browse..."

      2.4. Select "Sample Project" subdirectory in the IDE
           installation directory and click "OK"

      2.5. You should see "test" project (checked) in the
           project list. Click "Finish"

      2.6. Click "Refresh" icon on the "Navigator" panel
           (2 arrows on the top). You should see test project.

3. Enjoy!

Copyright (c) 2006 by Integration Technologies, Inc.
All rights reserved.
```

---

## IDE Configuration

### IDE Structure (iw_ide/)

```
iw_ide/
├── .eclipseproduct              # Eclipse product identification
├── configuration/               # IDE configuration directory
│   ├── config.ini              # Eclipse initialization
│   ├── org.eclipse.osgi/       # OSGi framework config
│   └── org.eclipse.update/     # Update manager config
├── iw_ide.exe                   # Launch executable
├── plugins/                     # Eclipse RCP plugins
│   ├── org.eclipse.core.*.jar
│   ├── org.eclipse.ui.*.jar
│   ├── org.eclipse.jdt.*.jar
│   └── [additional plugins...]
├── startup.jar                  # Bootstrap classloader
└── workspace/                   # Default workspace location
    ├── .metadata/              # Workspace metadata
    └── [active projects]
```

### Eclipse Configuration Files

**configuration/config.ini example:**

```properties
osgi.instance.area.default=@user.home/IW_IDE_workspace
osgi.configuration.area=@noDefault
osgi.noShutdown=false
eclipse.product=com.iw.ide.product
eclipse.keyConfiguration=org.eclipse.ui.defaultAcceleratorConfiguration
eclipse.buildId=220048
```

### Plugin Architecture

The IDE is built on Eclipse RCP with custom InterWeave plugins:

- **Core Integration Engine Plugin** - Transaction scheduling
- **Configuration UI Plugin** - Project/transformation editor
- **Database Connection Plugin** - JDBC/ODBC abstraction
- **XSLT Editor Plugin** - Transformation template authoring
- **Execution Monitor Plugin** - Real-time transaction monitoring
- **Deployment Manager Plugin** - Package and deploy integration projects

---

## Project Structure

### Standard Project Layout

```
ProjectName/
│
├── .project                     # Eclipse project descriptor (XML)
│   └── Contains: project name, build spec, natures
│
├── configuration/               # Project configuration
│   ├── im/
│   │   └── config.xml          # Business Daemon configuration
│   │       └── Transaction definitions
│   │       └── Data source connections
│   │       └── Scheduling parameters
│   │
│   └── ts/                      # Transformation Server config
│       └── ts.properties        # Transformation server settings
│
├── configuration.properties     # Project version info
│   └── COMPLIENT_PROJECT_VERSION=220048
│   └── COMPLIENT_HOSTED_VERSION=18
│
├── classes/                     # Compiled Java classes
│   └── Custom transformation logic
│
├── xslt/                        # XSLT transformation templates
│   ├── SFToQB.xslt             # Salesforce to QB mapping
│   ├── QBToSF.xslt             # QB to Salesforce mapping
│   └── [custom transformations]
│
└── .deployables/                # Deployment artifacts
    └── Packaged integration WAR files
```

### Project Properties

Each project has a `.project` file (XML format):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<projectDescription>
    <name>SF2QBBase</name>
    <comment></comment>
    <projects>
        <!-- Project dependencies -->
    </projects>
    <buildSpec>
        <!-- Build specifications -->
    </buildSpec>
    <natures>
        <!-- Project natures/types -->
    </natures>
</projectDescription>
```

---

## Integration Patterns

### Core Integration Patterns Supported

#### 1. **Bidirectional Synchronization (Master-Master)**

```
Salesforce Accounts ←→ QuickBooks Customers

Flow:
- SF Account created → QB Customer created
- SF Account modified → QB Customer updated
- QB Customer created → SF Account created
- QB Customer modified → SF Account updated

Conflict Resolution:
- Last-write-wins (configurable)
- Timestamp-based versioning
- Manual reconciliation workflow
```

#### 2. **Hierarchical Data Mapping**

```
SF Opportunity → QB Invoice
├─ Opportunity Amount → Invoice Total
├─ Opportunity Name → Invoice Description
├─ Opportunity Account → Invoice Customer
└─ Opportunity Line Items → Invoice Line Items
```

#### 3. **Scheduled Batch Processing**

```
Every 10 minutes:
1. Query SF for modified Opportunities (since last sync)
2. Query QB for new/modified Invoices (since last sync)
3. Apply transformation rules
4. Update both systems
5. Log transaction
6. Update checkpoint timestamp
```

#### 4. **Stateful Transformation with Checkpoints**

```
Checkpoint Model:
- QueryStartTime: Last successful query timestamp
- Increment only processes changes since checkpoint
- Maintains state in database
- Enables restart from last known good state

Example:
QueryStartTime="2006-06-29 09:00:00.0"
→ Next sync queries from this time forward
→ After successful sync, updates to 2006-06-29 09:10:00.0
```

#### 5. **Batch Processing with Inner Cycles**

```
For large datasets:
- InnerCycles="200" → Process up to 200 records per cycle
- BatchSize="120" → Process 120 records at a time
- Multiple cycles within one scheduled interval

Flow:
Interval=600000ms (10 minutes)
├─ Cycle 1: Process records 1-120
├─ Cycle 2: Process records 121-240
└─ Continue until all records processed
```

#### 6. **Date Range Selection (Manual Queries)**

```
Transaction variants ending with "DRS":
- Allow manual start/end date specification
- Override automatic checkpoint behavior
- Useful for data reconciliation/backfill

Parameters:
- StartDateTime="" (YYYY-MM-DD HH:MM:SS format)
- EndDateTime="" (YYYY-MM-DD HH:MM:SS format)
```

#### 7. **Real-time vs. Scheduled Sync**

```
Real-time (Interval=0):
- Executes immediately on trigger
- No scheduling delay
- Used for critical updates

Scheduled (Interval>0):
- Executes at specified millisecond intervals
- Interval=600000 → Every 10 minutes
- Interval=1000 → Every 1 second
- Interval=3600000 → Every hour
```

---

## Configuration Files

### Main Configuration File: config.xml

**Location:** `{project}/configuration/im/config.xml`

**Complete Structure:**

```xml
<BusinessDaemonConfiguration
    Name="BD1"
    HartbeatInterval="0"
    RefreshInterval="1000"
    PrimaryTSURL="http://72.3.142.149:8080/SF2QBBase/scheduledtransform"
    SecondaryTSURL=""
    PrimaryTSURLT="http://67.192.84.146:8080/SF2QBBase/scheduledtransform"
    SecondaryTSURLT=""
    PrimaryTSURL1="http://intsvr3.trapponline.com:8080/SF2QBBase/scheduledtransform"
    SecondaryTSURL1=""
    PrimaryTSURLT1="http://intsvr3.trapponline.com:8080/SF2QBBase/scheduledtransform"
    SecondaryTSURLT1=""
    PrimaryTSURLD="http://intsvr5.trapponline.com:8080/SF2QBBase/scheduledtransform"
    SecondaryTSURLD=""
    FailoverURL=""
    IsPrimary="1"
    RunAtStartUp="1"
    BufferSize="1024"
    IsHosted="0">

    <!-- Transaction Definitions -->
    <TransactionDescription>
        <!-- Transaction configuration -->
    </TransactionDescription>

</BusinessDaemonConfiguration>
```

### Business Daemon Configuration Attributes

| Attribute          | Type         | Description                              |
| ------------------ | ------------ | ---------------------------------------- |
| `Name`             | string       | Daemon identifier (typically "BD1")      |
| `HartbeatInterval` | milliseconds | Health check interval (0 = disabled)     |
| `RefreshInterval`  | milliseconds | Configuration refresh interval           |
| `PrimaryTSURL`     | URL          | Primary Transformation Server endpoint   |
| `SecondaryTSURL`   | URL          | Secondary failover Transformation Server |
| `PrimaryTSURLT`    | URL          | Tertiary Transformation Server           |
| `SecondaryTSURLT`  | URL          | Tertiary failover server                 |
| `PrimaryTSURL1`    | URL          | Additional backup server                 |
| `SecondaryTSURL1`  | URL          | Additional backup failover               |
| `PrimaryTSURLT1`   | URL          | Extended backup servers                  |
| `SecondaryTSURLT1` | URL          | Extended backup failover                 |
| `PrimaryTSURLD`    | URL          | Development Transformation Server        |
| `SecondaryTSURLD`  | URL          | Development failover server              |
| `FailoverURL`      | URL          | Emergency failover endpoint              |
| `IsPrimary`        | 0\|1         | Whether this is primary daemon (1=yes)   |
| `RunAtStartUp`     | 0\|1         | Auto-start on system boot                |
| `BufferSize`       | bytes        | Data buffer size (1024 bytes default)    |
| `IsHosted`         | 0\|1         | Whether running in hosted environment    |

### Transaction Description Structure

```xml
<TransactionDescription
    Id="SFAcct2QBCust"
    Description="Creates new and updates existing accounts, sales receipts,
                 and invoices in QB from Customers and Orders in SF on timely
                 basis. Initial QueryStartTime must be set. Must work in
                 scheduled over the interval mode."
    Solution=""
    Interval="600000"
    Shift="0"
    RunAtStartUp="false"
    NumberOfExecutions="0"
    InnerCycles="0"
    PrimaryTSURL=""
    SecondaryTSURL=""
    PrimaryTSURLT=""
    SecondaryTSURLT=""
    PrimaryTSURL1="null"
    SecondaryTSURL1="null"
    PrimaryTSURLT1="null"
    SecondaryTSURLT1="null"
    PrimaryTSURLD=""
    SecondaryTSURLD=""
    IsActive="true"
    IsPublic="false"
    IsStateful="true">

    <Parameter Name="SFURL"
               Value="https://login.salesforce.com/services/Soap/u/47.0|||
                      https://test.salesforce.com/services/Soap/u/47.0"
               Fixed="true" />

    <Parameter Name="QBDriver"
               Value="sun.jdbc.odbc.JdbcOdbcDriver"
               Fixed="true" />

    <Parameter Name="tranname"
               Value="SFLogin_CM"
               Fixed="true" />

    <Parameter Name="applicationname"
               Value="iwtransformationserver"
               Fixed="true" />

    <Parameter Name="QueryStartTime"
               Value="2006-06-29 09:00:00.0"/>

</TransactionDescription>
```

### Transaction Attributes Reference

| Attribute            | Type         | Description                          | Example                            |
| -------------------- | ------------ | ------------------------------------ | ---------------------------------- |
| `Id`                 | string       | Unique transaction identifier        | `SFAcct2QBCust`                    |
| `Description`        | string       | Human-readable transaction purpose   | "Sync SF Accounts to QB Customers" |
| `Solution`           | string       | Associated solution/project name     | ""                                 |
| `Interval`           | milliseconds | Execution frequency                  | `600000` (10 min)                  |
| `Shift`              | milliseconds | Offset from interval start           | `0`                                |
| `RunAtStartUp`       | boolean      | Execute on daemon startup            | `false`                            |
| `NumberOfExecutions` | integer      | Limit total executions (0=unlimited) | `0`                                |
| `InnerCycles`        | integer      | Batch cycles per execution (0=auto)  | `200`                              |
| `IsActive`           | boolean      | Enable/disable transaction           | `true`                             |
| `IsPublic`           | boolean      | Expose via API                       | `false`                            |
| `IsStateful`         | boolean      | Maintain checkpoint state            | `true`                             |

### Parameter Types

```xml
<!-- Fixed System Parameters -->
<Parameter Name="SFURL" Value="..." Fixed="true" />
<!-- Fixed="true" = cannot be modified at runtime -->

<!-- Variable User Parameters -->
<Parameter Name="BatchSize" Value="120" />
<!-- Fixed="false" or omitted = can be modified -->

<!-- Query Parameters -->
<Parameter Name="QueryStartTime" Value="2006-06-29 09:00:00.0"/>
<!-- Contains timestamp of last successful sync -->

<!-- Date Range Parameters -->
<Parameter Name="StartDateTime" Value="2023-01-01 00:00:00.0"/>
<Parameter Name="EndDateTime" Value="2023-01-31 23:59:59.999"/>

<!-- Filtering Parameters -->
<Parameter Name="AccountName" Value=""/>
<Parameter Name="CustomerName" Value=""/>
```

---

## Supported Systems & Connectors

### Salesforce Integration

**SOAP API Version:** 47.0 (circa 2016)

**Endpoints:**

```
Production: https://login.salesforce.com/services/Soap/u/47.0
Sandbox: https://test.salesforce.com/services/Soap/u/47.0
```

**Supported Objects:**

- Accounts
- Contacts
- Opportunities
- Products
- Quotes
- Orders
- Invoices
- Custom Objects

**Authentication:**

- SOAP authentication (username/password)
- Session tokens
- OAuth support (v1.0)

**Data Format:**

- SOAP XML messages
- XSLT transformation templates

### QuickBooks Integration

**Connection Method:** QODBC (QuickBooks ODBC Driver)

**Driver Version:** 24.0.0.356

**Access Level:**

- Direct database access via ODBC bridge
- Full CRUD operations
- Transaction-level queries

**Supported Objects:**

- Customers
- Items (Products)
- Invoices
- Sales Orders (SO)
- Sales Receipts (SR)
- Estimates
- Jobs
- Credit Memos

**JDBC Driver:**

```java
// Standard JDBC connection
String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
// URL format: jdbc:odbc:DSN_NAME
```

### MySQL Database Integration

**Driver:** JDBC MySQL Connector

**Capabilities:**

- Read/write data
- Execute custom SQL queries
- Batch operations

**Use Cases:**

- Data warehousing
- Staging tables for transformations
- Historical data archiving

### Nexternal Integration

**Platform:** Nexternal (Legacy ecommerce platform)

**Integration Type:**

- XML-based API
- SOAP web services
- Custom transformation templates

**Supported Data:**

- Products/Inventory
- Customers/Orders
- Transaction data

### AccPac Integration

**System:** AccPac (Sage accounting software)

**Connection Type:**

- COM-based connector (AccpacCom/)
- Direct database access

**Supported Modules:**

- General Ledger
- Accounts Receivable
- Accounts Payable
- Inventory

### Legacy System Connectors

| System       | Method        | Status |
| ------------ | ------------- | ------ |
| Accpac       | COM Component | Active |
| Nexternal    | XML/SOAP      | Active |
| ProsperCMS   | Flat file/API | Sample |
| SalesProdigy | Database      | Sample |
| Generic ODBC | JDBC Bridge   | Active |
| MySQL        | JDBC          | Active |

---

## Transaction Descriptions

### Complete Transaction Catalog

InterWeave SDK v2.2 includes **59+ pre-configured transaction types**. Below is the complete taxonomy:

### **Category 1: Salesforce Accounts → QuickBooks Customers**

#### SFAcct2QBCustN - New Accounts Only

```
Interval: 0 (on-demand)
IsStateful: false
Direction: SF → QB
Scope: New accounts only (not previously synced)
```

#### SFAcct2QBCust - Full Sync

```
Interval: 600000 (10 minutes)
IsStateful: true
Direction: SF → QB
Scope: All accounts (new and updated)
BatchSize: Auto
Process: Creates new customers, updates existing
```

#### SFAcct2QBCustDR - Date Range Query

```
Interval: 0 (on-demand)
IsStateful: false
Direction: SF → QB
Parameters: StartDateTime, EndDateTime
Use: Manual backfill/reconciliation
```

#### SFAcct2QBCustDRS - Date Range with Batching

```
Interval: 1000 (every second)
IsStateful: false
Direction: SF → QB
Parameters: StartDateTime, EndDateTime, BatchSize
Use: Large volume backfill operations
```

#### SFAcct2QBCustBind - Binding/Matching

```
Interval: -1000 (reverse interval = inverse scheduling)
IsStateful: false
Direction: SF → QB (bidirectional matching)
BatchSize: 1000
Purpose: Associate SF Account IDs with QB Customer IDs
```

#### SFAcct2QBCustBindS - Batch Binding

```
Interval: 1000
IsStateful: false
Direction: SF ↔ QB
BatchSize: 200
Purpose: Bulk customer ID synchronization
```

### **Category 2: Salesforce Opportunities → QuickBooks Invoices**

#### SFOpp2QBInvN - New Opportunities Only

```
Interval: -500
IsStateful: false
Direction: SF → QB
Scope: New opportunities not yet invoiced
```

#### SFOpp2QBInv - Full Sync

```
Interval: 900000 (15 minutes)
IsStateful: true
Direction: SF → QB
Scope: All opportunities (create invoices)
```

#### SFOpp2QBInvDR - Date Range

```
Interval: -1000
IsStateful: false
Direction: SF → QB
Parameters: StartDateTime, EndDateTime
```

#### SFOpp2QBInvDRS - Date Range with Batching

```
Interval: 1000
IsStateful: false
Direction: SF → QB
BatchSize: Variable
```

#### SFOpp2QBInvF - Full Load

```
Interval: 500
IsStateful: false
Direction: SF → QB
Scope: Complete historical load
```

### **Category 3: Salesforce Opportunities → QuickBooks Sales Orders**

#### SFOpp2QBSON - New Sales Orders

```
Interval: -500
IsStateful: false
Direction: SF → QB
```

#### SFOpp2QBSO - Full Sync

```
Interval: 600000 (10 minutes)
IsStateful: true
Direction: SF → QB
```

#### SFOpp2QBSODR - Date Range

```
Interval: -500
IsStateful: false
Direction: SF → QB
Parameters: StartDateTime, EndDateTime
```

#### SFOpp2QBSODR - Date Range Batch

```
Interval: 1000
IsStateful: false
BatchSize: Variable
```

### **Category 4: Salesforce Opportunities → QuickBooks Sales Receipts**

#### SFOpp2QBSRN - New Sales Receipts

```
Interval: -500
IsStateful: false
Direction: SF → QB
```

#### SFOpp2QBSR - Full Sync

```
Interval: 600000
IsStateful: true
Direction: SF → QB
```

#### SFOpp2QBSRDR - Date Range

```
Interval: 1000
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

### **Category 5: Salesforce Opportunities → QuickBooks Estimates**

#### SFOpp2QBEstN - New Estimates

```
Interval: -500
IsStateful: false
Direction: SF → QB
```

#### SFOpp2QBEst - Full Sync

```
Interval: -500
IsStateful: false
Direction: SF → QB
```

#### SFOpp2QBEstDR - Date Range

```
Interval: 600000
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

### **Category 6: Salesforce Opportunities → QuickBooks Jobs**

#### SFOpp2QBJobN - New Jobs

```
Interval: -500
IsStateful: false
Direction: SF → QB
Description: Creates new QB jobs from SF opportunities
```

#### SFOpp2QBJob - Full Sync

```
Interval: 600000
IsStateful: false
Direction: SF → QB
```

#### SFOpp2QBJobDR - Date Range

```
Interval: 600000
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

#### SFOpp2QBJobBind - Job Binding

```
Interval: 1000
IsStateful: false
Purpose: Match SF opportunity IDs to QB job IDs
```

#### SFOpp2QBJobEstN - Jobs with Estimates

```
Interval: 0 (on-demand)
IsStateful: false
Direction: SF → QB
```

### **Category 7: Salesforce Products → QuickBooks Items**

#### SFProd2QBItemN - New Products Only

```
Interval: 0 (on-demand)
IsStateful: false
Direction: SF → QB
```

#### SFProd2QBItem - Full Sync

```
Interval: 3600000 (hourly)
IsStateful: true
Direction: SF → QB
Scope: Product catalog synchronization
```

#### SFProd2QBItemDR - Date Range

```
Interval: 0 (on-demand)
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

#### SFProd2QBItemF - Full Load

```
Interval: 500
IsStateful: false
Direction: SF → QB
Purpose: Historical product load
```

### **Category 8: QuickBooks → Salesforce (Reverse Sync)**

#### QBCust2SFAcctN - New Customers

```
Interval: 0 (on-demand)
IsStateful: false
Direction: QB → SF
```

#### QBCust2SFAcct - Full Sync

```
Interval: 600000 (10 minutes)
IsStateful: true
Direction: QB → SF
Purpose: Create/update SF accounts from QB customers
```

#### QBCust2SFAcctDR - Date Range

```
Interval: 1000
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

#### QBCust2SFAcctNF - New Customers Full Name

```
Interval: 0 (on-demand)
IsStateful: false
Direction: QB → SF
Parameter: CustomerFullName
```

#### QBCust2SFAcctBind - Binding

```
Interval: 1000
IsStateful: false
Purpose: Associate QB customer IDs with SF accounts
```

#### QBCust2SFAcctDRS - Batch Date Range

```
Interval: 1000
IsStateful: false
BatchSize: 2000
Parameters: StartDateTime, EndDateTime
```

### **Category 9: QuickBooks Invoices → Salesforce Opportunities**

#### QBInvoices2SFAcctOppN - New Invoices

```
Interval: 0 (on-demand)
IsStateful: false
Direction: QB → SF
```

#### QBInvoices2SFAcctOpp - Full Sync

```
Interval: 600000 (10 minutes)
IsStateful: true
Direction: QB → SF
InnerCycles: 200
Purpose: Update SF opportunities with QB invoice data
```

#### QBInvoices2SFAcctOppDR - Date Range

```
Interval: -1000
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

#### QBInvoices2SFAcctOppDRS - Batch Date Range

```
Interval: 1000
IsStateful: false
BatchSize: Variable
```

#### QBInvoices2SFAcctOppDRQ - Query Format

```
Interval: N/A
Type: Query (not scheduled transaction)
Purpose: Query QB invoices by date range
```

#### QBInvoices2SFAcctOppDRQB - Batch Query

```
Interval: N/A
Type: Query
BatchSize: Variable
```

### **Category 10: QuickBooks Sales Orders → Salesforce**

#### QBSO2SFAcctOppN - New Sales Orders

```
Interval: 0 (on-demand)
IsStateful: false
Direction: QB → SF
```

#### QBSO2SFAcctOpp - Full Sync

```
Interval: 600000 (10 minutes)
IsStateful: true
Direction: QB → SF
```

#### QBSO2SFAcctOppDR - Date Range

```
Interval: -1000
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

#### QBSO2SFAcctOppDRS - Batch Date Range

```
Interval: 1000
IsStateful: false
```

### **Category 11: QuickBooks Sales Receipts → Salesforce**

#### QBSR2SFAcctOppN - New Receipts

```
Interval: 0 (on-demand)
IsStateful: false
Direction: QB → SF
```

#### QBSR2SFAcctOpp - Full Sync

```
Interval: 600000
IsStateful: true
Direction: QB → SF
```

#### QBSR2SFAcctOppDR - Date Range

```
Interval: 1000
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

#### QBSR2SFAcctOppDRS - Batch Date Range

```
Interval: 1000
IsStateful: false
BatchSize: Variable
```

### **Category 12: QuickBooks Items → Salesforce Products**

#### QBItem2SFProdN - New Items

```
Interval: -500
IsStateful: false
Direction: QB → SF
```

#### QBItem2SFProd - Full Sync

```
Interval: 60000 (1 minute)
IsStateful: true
Direction: QB → SF
```

#### QBItem2SFProdDR - Date Range

```
Interval: 1000
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

#### QBItem2SFProdDRS - Batch Date Range

```
Interval: 1000
IsStateful: false
BatchSize: Variable
```

#### QBItem2SFProdF - Full Load

```
Interval: 500
IsStateful: false
```

### **Category 13: QuickBooks Jobs → Salesforce Opportunities**

#### QBJob2SFAcctOppN - New Jobs

```
Interval: 0 (on-demand)
IsStateful: false
Direction: QB → SF
```

#### QBJob2SFAcctOpp - Full Sync

```
Interval: 600000
IsStateful: true
Direction: QB → SF
```

#### QBJob2SFAcctOppF - Full Load

```
Interval: 0 (on-demand)
IsStateful: false
```

#### QBJob2SFAcctOppDRS - Batch Date Range

```
Interval: 600000
IsStateful: false
```

### **Category 14: QuickBooks Estimates → Salesforce**

#### QBEst2SFAcctOppN - New Estimates

```
Interval: 0 (on-demand)
IsStateful: false
Direction: QB → SF
```

#### QBEst2SFAcctOpp - Full Sync

```
Interval: 0 (on-demand)
IsStateful: false
Direction: QB → SF
```

#### QBEst2SFAcctOppDR - Date Range

```
Interval: -1000
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

### **Category 15: QuickBooks Credit Memos**

#### SFOpp2QBCMN - New Credit Memos

```
Interval: -500
IsStateful: false
Direction: SF → QB
```

#### SFOpp2QBCM - Full Sync

```
Interval: 900000
IsStateful: true
Direction: SF → QB
```

#### SFOpp2QBCMDR - Date Range

```
Interval: 0 (on-demand)
IsStateful: false
Parameters: StartDateTime, EndDateTime
```

#### SFOpp2QBCMSN - Scheduled Credit Memos

```
Interval: 900000
IsStateful: true
Direction: SF → QB
```

#### SFOpp2QBCMF - Full Load

```
Interval: 500
IsStateful: false
```

#### SFOpp2QBCMNQ - Query Format

```
Interval: N/A
Type: Query
```

### **Category 16: Multi-Object Transactions**

#### SFAcctOpp2QBCustInv - Accounts & Opportunities

```
Interval: 600000
IsStateful: true
Direction: SF → QB
Scope: Sync both accounts and their opportunities as invoices
```

#### SFAcctOpp2QBCustSO - Accounts & Sales Orders

```
Interval: 600000
IsStateful: true
Direction: SF → QB
```

#### SFAcctOpp2QBCustSR - Accounts & Sales Receipts

```
Interval: 600000
IsStateful: true
Direction: SF → QB
```

#### SFAcctOpp2QBCustEst - Accounts & Estimates

```
Interval: 600000
IsStateful: true
Direction: SF → QB
```

#### SFAcctOpp2QBCustJobEst - Complex Multi-Object

```
Interval: 600000
IsStateful: true
Direction: SF → QB
Scope: Accounts, opportunities as jobs, estimates
```

#### SFAcctOpp2QBCustSRInv - SR and Invoice Variants

```
Interval: 600000
IsStateful: true
Direction: SF → QB
```

#### SFContOpp2QBCustSR - Contact-based Sync

```
Interval: 600000
IsStateful: true
Direction: SF → QB
```

#### SFContOpp2QBCustInv - Contact Opportunities

```
Interval: 900000
IsStateful: true
Direction: SF → QB
```

### **Category 17: Bidirectional Complex Sync**

#### QBCustSO2SFAcctOpp - QB to SF Multi-Object

```
Interval: 600000
IsStateful: true
Direction: QB → SF
InnerCycles: 200
```

#### QBCustSR2SFAcctOpp - QB SR to SF

```
Interval: 600000
IsStateful: true
Direction: QB → SF
```

#### QBCustEst2SFAcctOpp - QB Estimates

```
Interval: 600000
IsStateful: true
Direction: QB → SF
```

### **Category 18: Special Purpose Transactions**

#### StopScheduledTest - Testing/Control

```
Interval: Variable
Purpose: Test stop/pause mechanisms
```

#### SFAcctCO2QBCustJobInv - Custom Object Mapping

```
Interval: 600000
IsStateful: true
Direction: SF → QB
Purpose: Custom object to job/invoice mapping
```

#### SFCOOpp2QBJobSON - Complex Custom Object

```
Interval: 0 (on-demand)
IsStateful: false
Direction: SF → QB
```

### **Query-based Transactions (Non-Scheduled)**

These execute on-demand rather than on interval:

```xml
<Query Id="SFOpp2QBCMNQ" Description="..."
       IsActive="true" IsPublic="false">
    <!-- Parameters for query execution -->
</Query>

<Query Id="SFOpp2QBEstNQ" ... />
<Query Id="SFOpp2QBInvNQ" ... />
<Query Id="SFOpp2QBSONQ" ... />
<Query Id="SFOpp2QBSRNQ" ... />
<Query Id="QBCust2SFAcctDRQ" ... />
<Query Id="QBInvoices2SFAcctOppDRQ" ... />
<Query Id="QBItem2SFProdDRQ" ... />
<Query Id="QBInvoices2SFAcctOppDRQB" ... />
<Query Id="SFAcct2QBCustNQ" ... />
<Query Id="SFProd2QBItemNQ" ... />
<Query Id="SFAcctOpp2QBCustSONQ" ... />
<Query Id="QBSO2SFAcctOppDRQ" ... />
<Query Id="SFOpp2QBJobNQ" ... />
<Query Id="SFAcctOpp2QBCustSRNQ" ... />
<Query Id="SFAcctOpp2QBCustJSONQ" ... />
<Query Id="SFAcctOpp2QBCustEstNQ" ... />
<Query Id="SFAcctOpp2QBCustInvNQ" ... />
```

---

## Deployment Architecture

### High-Availability Configuration

The config.xml supports multiple redundant servers for zero-downtime deployment:

```
Primary Servers:
├─ PrimaryTSURL: http://72.3.142.149:8080/SF2QBBase/scheduledtransform
├─ PrimaryTSURLT: http://67.192.84.146:8080/SF2QBBase/scheduledtransform
└─ PrimaryTSURL1: http://intsvr3.trapponline.com:8080/SF2QBBase/scheduledtransform

Secondary Failover:
├─ SecondaryTSURL: (empty for primary active)
├─ SecondaryTSURLT: (empty for primary active)
└─ SecondaryTSURL1: (empty for primary active)

Tertiary Servers:
├─ PrimaryTSURLT1: http://intsvr3.trapponline.com:8080/SF2QBBase/scheduledtransform
├─ SecondaryTSURLT1: (failover)
├─ PrimaryTSURLD: http://intsvr5.trapponline.com:8080/SF2QBBase/scheduledtransform
└─ SecondaryTSURLD: (failover)

Emergency Failover:
└─ FailoverURL: (reserved for emergency)
```

### Deployment Topology

```
┌─────────────────────────────────────────────────────┐
│           Load Balancer (Not Shown)                │
└────────────────┬────────────────┬────────────────┘
                 │                │
        ┌────────▼─────┐  ┌───────▼──────┐
        │  Primary     │  │  Secondary   │
        │  Server 1    │  │  Server 2    │
        │              │  │              │
        │ Tomcat 5.5   │  │ Tomcat 5.5   │
        │ Port: 8080   │  │ Port: 8080   │
        └────────┬─────┘  └───────┬──────┘
                 │                │
    ┌────────────┴────────────────┘
    │
    ▼
┌──────────────────┬──────────────────┐
│  Salesforce      │   QuickBooks     │
│  SOAP API        │   ODBC Driver    │
└──────────────────┴──────────────────┘
```

### Deployment Steps

1. **Package Integration Project**

   ```bash
   cd {project}
   # Create WAR file
   jar cvf {project}.war .
   ```

2. **Deploy to Application Server**

   ```bash
   cp {project}.war /opt/tomcat/webapps/
   # Tomcat auto-deploys
   ```

3. **Configure Transformation Server**

   ```bash
   # Edit configuration/im/config.xml
   # Update connection URLs
   # Restart Tomcat
   systemctl restart tomcat
   ```

4. **Verify Deployment**

   ```
   Access: http://server:8080/{project}/admin
   Check transaction logs
   Monitor execution
   ```

5. **Failover Setup**
   ```bash
   # Configure secondary servers in config.xml
   # Test failover: Stop primary, verify secondary takes over
   # Restore data connections after failover
   ```

---

## Data Transformation

### XSLT Transformation Overview

**Location:** `{project}/xslt/`

**Purpose:** Map and transform data between Salesforce and QuickBooks formats

### Example Transformation Template

**File:** `SF_Account_to_QB_Customer.xslt`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:sf="urn:salesforce">

    <!-- Template for Salesforce Account → QuickBooks Customer -->
    <xsl:template match="/sf:Account">
        <QBCustomer>
            <!-- Map Salesforce fields to QB Customer fields -->
            <CustomerName>
                <xsl:value-of select="Name"/>
            </CustomerName>

            <BillingAddress>
                <Addr1><xsl:value-of select="BillingStreet"/></Addr1>
                <City><xsl:value-of select="BillingCity"/></City>
                <State><xsl:value-of select="BillingState"/></State>
                <Zip><xsl:value-of select="BillingPostalCode"/></Zip>
            </BillingAddress>

            <Phone>
                <xsl:value-of select="Phone"/>
            </Phone>

            <Email>
                <xsl:value-of select="Email"/>
            </Email>

            <!-- Map custom fields -->
            <SalesforceId>
                <xsl:value-of select="Id"/>
            </SalesforceId>

            <CreditLimit>
                <xsl:value-of select="CreditLimit__c"/>
            </CreditLimit>
        </QBCustomer>
    </xsl:template>

</xsl:stylesheet>
```

### Transformation Flow

```
1. Source Data (Salesforce)
   ├─ Query Salesforce SOAP API
   └─ Return XML structure

2. Parsing
   ├─ Parse XML response
   └─ Validate against schema

3. Transformation
   ├─ Apply XSLT template
   ├─ Map fields
   ├─ Apply business rules
   └─ Generate target XML

4. Target Preparation
   ├─ Format for QuickBooks
   ├─ Validate required fields
   └─ Generate QODBC insert/update statements

5. Database Update
   ├─ Connect to QB via QODBC
   ├─ Execute transaction
   └─ Log results
```

### Common Transformation Patterns

#### Field Mapping with Default Values

```xml
<Field>
    <xsl:choose>
        <xsl:when test="SourceField != ''">
            <xsl:value-of select="SourceField"/>
        </xsl:when>
        <xsl:otherwise>
            <xsl:text>Default Value</xsl:text>
        </xsl:otherwise>
    </xsl:choose>
</Field>
```

#### Conditional Transformations

```xml
<Type>
    <xsl:choose>
        <xsl:when test="Stage = 'Closed Won'">
            <xsl:text>Invoice</xsl:text>
        </xsl:when>
        <xsl:when test="Stage = 'Proposal'">
            <xsl:text>Estimate</xsl:text>
        </xsl:when>
        <xsl:otherwise>
            <xsl:text>SalesOrder</xsl:text>
        </xsl:otherwise>
    </xsl:choose>
</Type>
```

#### Date Formatting

```xml
<InvoiceDate>
    <xsl:value-of select="translate(CreateDate, 'T', ' ')"/>
</InvoiceDate>
```

#### Currency Conversion

```xml
<Amount>
    <xsl:value-of select="Amount * ExchangeRate"/>
</Amount>
```

#### Lookup/Join Operations

```xml
<!-- Join SF Account to Opportunity -->
<CustomerRef>
    <xsl:value-of select="//Account[Id = current()/AccountId]/QBCustomerId"/>
</CustomerRef>
```

---

## Sample Projects

### Project 1: SF2QBBase (Salesforce ↔ QuickBooks)

**Purpose:** Primary bidirectional sync between Salesforce and QuickBooks

**Configuration File:** `all_projects_1/SF2QBBase/configuration/im/config.xml`

**Transactions Included:**

- SFAcct2QBCust (Accounts → Customers, 10-min interval)
- SFOpp2QBInv (Opportunities → Invoices, 15-min interval)
- QBCust2SFAcct (Customers → Accounts, 10-min interval, bidirectional)
- QBInvoices2SFAcctOpp (Invoices → Opportunities, 10-min interval)

**Directory Structure:**

```
SF2QBBase/
├── .project
├── configuration/
│   ├── im/
│   │   └── config.xml              # 59+ transactions defined
│   └── ts/
├── classes/                        # Compiled transformation logic
├── xslt/                          # Transformation templates
└── .deployables/
```

**Key Features:**

- Stateful processing with checkpoint timestamps
- Batch processing with 200 inner cycles
- Multiple failover servers
- Real-time and scheduled transactions

### Project 2: QB_sample (QuickBooks Testing)

**Purpose:** Sample project for QB ODBC testing and connectivity

**Structure:**

```
QB_sample/
├── .metadata/
└── QBODBC1/                       # QB ODBC connection test
```

**Use:** Test QODBC driver installation and QB connectivity

### Project 3: sample_projects/Test (Template)

**Purpose:** Starter template for new integration projects

**Structure:**

```
Test/
├── .project
├── .deployables/                  # Empty, ready for packaging
├── classes/                       # Template compiled classes
├── configuration/
│   ├── im/
│   │   └── config.xml            # Template configuration
│   └── ts/
└── xslt/                          # Template transformation files
```

**Setup Instructions:**

```
1. Copy Test/ directory
2. Rename to your project name
3. Edit configuration/im/config.xml
4. Add transaction definitions
5. Create XSLT transformation templates
6. Deploy to Tomcat
```

### Project 4: all_projects_1/ Collection

**Contains 20+ active integration projects:**

```
all_projects_1/
├── Accpac/                  # Sage Accpac accounting integration
├── ConfTest/               # Configuration testing
├── CPADemo/                # Accounting demo project
├── DB2Mail/                # Database to email notifications
├── EmaiTest/               # Email integration test
├── HTTPTest/               # HTTP/REST endpoint test
├── MM2SF/                  # Money Manager to Salesforce
├── MySQL2SF/               # MySQL database to Salesforce
├── Next2SFST/              # Nexternal to SF staging table
├── Nexternal2SF/           # Nexternal to Salesforce main
├── Nexternal2XML/          # Nexternal XML export
├── ProsperCMS2SF/          # ProsperCMS to Salesforce
├── QB_POAgent/             # QB Purchase Order agent
├── SF2QBBase/              # Base SF ↔ QB sync (primary)
├── SQL2Email/              # SQL query results to email
├── StopScheduledTest/      # Test pause/stop mechanism
├── test2/                  # Various test projects
├── test25/
├── test777/
├── Test777777777/
└── ww01/
```

---

## Runtime Execution Model

### Execution Lifecycle

```
1. DAEMON STARTUP (RunAtStartUp="1")
   ├─ Load config.xml from configuration/im/
   ├─ Initialize database connections
   │  ├─ Salesforce SOAP session
   │  ├─ QuickBooks QODBC bridge
   │  └─ Any auxiliary data sources
   ├─ Compile XSLT templates
   ├─ Start transaction scheduler
   ├─ Log startup completion
   └─ Enter monitoring loop

2. INTERVAL PROCESSING (Every RefreshInterval=1000ms)
   ├─ Check current time against scheduled transactions
   ├─ Identify transactions ready to execute
   ├─ (May be multiple transactions simultaneously)
   └─ Continue loop

3. TRANSACTION EXECUTION
   ├─ Load transaction definition from config.xml
   ├─ Validate IsActive="true"
   ├─ Check connection to primary server
   ├─ Query source system
   │  ├─ If IsStateful: Use QueryStartTime as filter
   │  ├─ If not: Query all records
   │  └─ Apply any date range parameters
   ├─ Parse results to XML
   ├─ Apply XSLT transformation
   ├─ Validate output against schema
   ├─ Batch processing (InnerCycles)
   │  ├─ If InnerCycles>0: Process in cycles
   │  ├─ If InnerCycles=0: Auto-batch
   │  └─ Each cycle processes BatchSize records
   ├─ Connect to target system
   ├─ Execute CRUD operations
   ├─ Update QueryStartTime (if IsStateful)
   ├─ Log transaction results
   └─ Return to monitoring loop

4. ERROR HANDLING
   ├─ If primary connection fails
   │  ├─ Try secondary server (SecondaryTSURL)
   │  ├─ If secondary fails: Try tertiary (PrimaryTSURLT)
   │  ├─ If all fail: Log error and retry next cycle
   │  └─ Can escalate to FailoverURL
   ├─ If transformation fails
   │  ├─ Log error details
   │  ├─ Store failed record in retry queue
   │  └─ Skip to next record (continue processing)
   ├─ If database write fails
   │  ├─ Rollback transaction
   │  ├─ Log error
   │  ├─ Retry in next cycle
   │  └─ Don't update QueryStartTime (ensures retry)

5. STATEFUL CHECKPOINT UPDATE
   ├─ Only if transaction successful (IsStateful="true")
   ├─ Update QueryStartTime to current time
   ├─ Store in config.xml or database
   ├─ Ensures next execution only processes new changes
   └─ Prevents processing same records repeatedly

6. SHUTDOWN
   ├─ On SIGTERM or graceful shutdown request
   ├─ Complete current transaction
   ├─ Close all database connections
   ├─ Flush logs to disk
   └─ Exit cleanly
```

### Concurrent Transaction Execution

InterWeave supports executing multiple transactions simultaneously:

```
Interval Processing Loop (Every 1000ms):

Time T=0:
├─ Check SFAcct2QBCust (Interval=600000) → Not ready
├─ Check SFOpp2QBInv (Interval=900000) → Not ready
└─ Check SFProd2QBItem (Interval=3600000) → Not ready

Time T=600000ms (10 minutes):
├─ SFAcct2QBCust READY → EXECUTE
│  └─ Running in parallel...
├─ Check SFOpp2QBInv (Interval=900000) → Not ready
└─ Check SFProd2QBItem (Interval=3600000) → Not ready

Time T=900000ms (15 minutes):
├─ SFAcct2QBCust READY → EXECUTE (2nd execution)
│  └─ Running in parallel...
├─ SFOpp2QBInv READY → EXECUTE
│  └─ Running in parallel...
└─ Check SFProd2QBItem (Interval=3600000) → Not ready

Time T=1200000ms (20 minutes):
├─ SFAcct2QBCust READY → EXECUTE (3rd execution)
├─ SFOpp2QBInv READY → EXECUTE (2nd execution)
└─ ...
```

---

## Performance & Scalability

### Optimization Parameters

| Parameter          | Default    | Tunable | Impact                                      |
| ------------------ | ---------- | ------- | ------------------------------------------- |
| `Interval`         | 600000ms   | Yes     | Lower = more frequent checks but higher CPU |
| `BatchSize`        | 120        | Yes     | Higher = faster processing, more memory     |
| `InnerCycles`      | 0/200      | Yes     | Controls cycle batching                     |
| `BufferSize`       | 1024 bytes | Yes     | XML buffer size, impact on large records    |
| `RefreshInterval`  | 1000ms     | Yes     | Configuration refresh frequency             |
| `HartbeatInterval` | 0ms        | Yes     | Health check frequency (0=disabled)         |

### Performance Tuning

**For High Throughput:**

```xml
<Transaction ... Interval="1000" ... >
    <!-- Check every second for fast response -->
    <Parameter Name="BatchSize" Value="500"/>
    <!-- Process 500 records per batch -->
    <Parameter Name="InnerCycles" Value="10"/>
    <!-- 10 cycles = 5000 total records -->
</Transaction>
```

**For Low Latency System Load:**

```xml
<Transaction ... Interval="3600000" ... >
    <!-- Check every hour for light processing -->
    <Parameter Name="BatchSize" Value="50"/>
    <!-- Small batches, low memory usage -->
</Transaction>
```

**Memory Optimization:**

```xml
BufferSize="512"      <!-- Reduce from 1024 for memory constraints -->
InnerCycles="50"      <!-- Smaller cycles reduce memory peak -->
BatchSize="100"       <!-- Smaller batches -->
```

### Scalability Considerations

**Horizontal Scaling:**

- Deploy multiple Tomcat instances
- Use load balancer to distribute requests
- Each instance maintains own checkpoint state
- Database must support concurrent writes

**Vertical Scaling:**

- Increase server RAM
- Increase BatchSize and InnerCycles
- Reduce RefreshInterval for more responsive scheduling
- Use SSD for database (QB QODBC performance)

**Database Considerations:**

- QB QODBC: Single-threaded, serializes access
  - Not suitable for very high-frequency syncs
  - Works best with 1-10 minute intervals
- MySQL: Supports true concurrency
  - Can handle sub-second intervals
  - Use connection pooling

**Network Bandwidth:**

- Large BatchSize requires more network capacity
- Compress SOAP responses if available
- Consider local caching layer

---

## Troubleshooting & Logs

### Log Locations

```
IDE Log:
└─ workspace/.metadata/.log

Transaction Execution:
└─ {project}/logs/
    ├─ transaction.log
    ├─ error.log
    ├─ transformation.log
    └─ connectivity.log

Server Log:
└─ /opt/tomcat/logs/
    ├─ catalina.out
    ├─ localhost.log
    └─ manager.log
```

### Common Issues & Resolution

#### Issue 1: "QODBC Driver not found"

**Symptoms:**

```
ERROR: Cannot locate QODBC driver: sun.jdbc.odbc.JdbcOdbcDriver
```

**Causes:**

- QODBC driver not installed
- JAVA_HOME not set
- QB not running/accessible

**Resolution:**

```bash
# 1. Verify QODBC installation
Run: "QODBC 32-Bit Test Tool.lnk"

# 2. Check QB is running
# QB must be open for QODBC to work

# 3. Verify JAVA_HOME
echo %JAVA_HOME%

# 4. Reinstall if needed
Run: qodbc24.0.0.356.exe
```

#### Issue 2: "Salesforce Authentication Failed"

**Symptoms:**

```
ERROR: SOAP authentication failed: Invalid credentials
```

**Causes:**

- Wrong Salesforce username/password
- Salesforce IP whitelist blocking
- API access not enabled

**Resolution:**

```
1. Verify SF credentials in config.xml
   <Parameter Name="SFURL" Value="https://login.salesforce.com/..."/>

2. Check API access:
   SF Setup > Integrations > API > SOAP API Settings

3. Check IP whitelist:
   SF Setup > System Overview > Restrict login IP ranges

4. Generate new API token if needed:
   SF Settings > Personal > Reset My Security Token
```

#### Issue 3: "Transaction Timeout"

**Symptoms:**

```
ERROR: Transaction execution timeout after 300000ms
```

**Causes:**

- Large batch size on slow network
- QB locked by another process
- SF API rate limiting

**Resolution:**

```xml
<!-- Reduce batch size -->
<Parameter Name="BatchSize" Value="50"/>

<!-- Increase interval for more time between runs -->
<Transaction ... Interval="1200000" ... />
<!-- Process every 20 minutes instead of 10 -->

<!-- Reduce InnerCycles -->
<Parameter Name="InnerCycles" Value="50"/>
```

#### Issue 4: "Duplicate Records Created"

**Symptoms:**

- Same customer appears twice in QB
- Transactions running multiple times

**Causes:**

- QueryStartTime not updated (checkpoint not saved)
- Multiple daemon instances running
- InnerCycles > actual record count

**Resolution:**

```xml
<!-- Ensure stateful processing -->
<Transaction ... IsStateful="true" ... />

<!-- Verify checkpoint is being saved -->
Check: QueryStartTime value updates after each run

<!-- Stop other daemon instances -->
ps aux | grep iwtransformationserver
kill [PID]

<!-- Set proper InnerCycles -->
<Parameter Name="InnerCycles" Value="200"/>
```

#### Issue 5: "Field Mapping Errors"

**Symptoms:**

```
TRANSFORMATION ERROR: Salesforce field 'CustomField__c'
not found in XSLT template
```

**Causes:**

- XSLT template outdated
- Salesforce schema changed
- Field permission restrictions

**Resolution:**

```
1. Get latest Salesforce metadata
   Use Salesforce Data Loader or workbench

2. Update XSLT template:
   xslt/{transformation_name}.xslt

3. Add field mapping:
   <xsl:value-of select="CustomField__c"/>

4. Recompile and redeploy
   Restart Tomcat
```

#### Issue 6: "No Data Syncing"

**Symptoms:**

- Transactions run without error
- But no data appears in target system

**Causes:**

- Transaction marked IsActive="false"
- Date range filtering excluding all data
- Transformation producing no output

**Resolution:**

```xml
<!-- Verify transaction is active -->
<Transaction ... IsActive="true" ... />

<!-- Check date range parameters (if DRS variant) -->
<Parameter Name="StartDateTime" Value="2024-01-01"/>
<Parameter Name="EndDateTime" Value="2024-12-31"/>
<!-- Make sure dates encompass your data -->

<!-- Check transformation output -->
<!-- Add debug logging to XSLT -->
```

#### Issue 7: "Memory Leak / OOM Exception"

**Symptoms:**

```
ERROR: java.lang.OutOfMemoryError: Java heap space
```

**Causes:**

- BatchSize too large
- Large number of concurrent transactions
- Memory not released after transformation

**Resolution:**

```bash
# Increase Java heap size
# Edit: iw_ide.exe startup parameters
# Or set environment variable:
SET _JAVA_OPTIONS=-Xmx2048m

# Reduce batch size in config.xml
<Parameter Name="BatchSize" Value="50"/>

# Reduce RefreshInterval
RefreshInterval="2000"
<!-- Check every 2 seconds instead of 1 -->
```

### Debug Mode Execution

**Enable verbose logging:**

```bash
# Edit configuration/im/config.xml
<BusinessDaemonConfiguration ... >
    <Parameter Name="DEBUG_MODE" Value="true"/>
    <Parameter Name="LOG_LEVEL" Value="TRACE"/>
</BusinessDaemonConfiguration>

# Logs will output:
# - Every query executed
# - XML transformation details
# - Field mappings
# - Target system writes
```

### Connection Testing

**Test Salesforce Connection:**

```bash
# Use any SOAP client (soapUI, Postman, etc.)
# Endpoint: https://login.salesforce.com/services/Soap/u/47.0
# Method: login
# Params: username, password, clientid
```

**Test QuickBooks Connection:**

```bash
# Run: QODBC 32-Bit Test Tool.lnk
# Should list available QB DSN connections
# Click "Connect" to verify QODBC working
```

**Test MySQL Connection:**

```bash
# Run: SQLyog Community
# Create new connection
# Host: localhost
# User: root
# Password: (your password)
```

---

## Advanced Topics

### Custom Transaction Development

**Step 1: Create New Transaction Type**

```xml
<TransactionDescription
    Id="CustomSync_SF2QB"
    Description="Custom Salesforce to QuickBooks sync"
    Interval="300000"
    RunAtStartUp="false"
    IsActive="true"
    IsStateful="true">

    <Parameter Name="SFURL"
               Value="https://login.salesforce.com/services/Soap/u/47.0"
               Fixed="true" />

    <Parameter Name="QBDriver"
               Value="sun.jdbc.odbc.JdbcOdbcDriver"
               Fixed="true" />

    <Parameter Name="tranname"
               Value="SFLogin_Custom"
               Fixed="true" />

    <Parameter Name="QueryStartTime"
               Value="2024-01-01 00:00:00.0"/>

    <Parameter Name="BatchSize" Value="100"/>

</TransactionDescription>
```

**Step 2: Create Transformation Template**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/CustomSFObject">
        <CustomQBObject>
            <!-- Custom field mappings -->
        </CustomQBObject>
    </xsl:template>

</xsl:stylesheet>
```

**Step 3: Save Files**

```
configuration/im/config.xml    <- Add transaction
xslt/CustomSync_SF2QB.xslt     <- Add transformation
```

**Step 4: Restart Daemon**

```bash
systemctl restart iwtransformationserver
```

### Integration with External Systems

**Extend to support additional systems:**

```xml
<!-- MySQL Example -->
<Parameter Name="MySQLDriver" Value="com.mysql.jdbc.Driver" Fixed="true"/>
<Parameter Name="MySQLURL" Value="jdbc:mysql://localhost/integration_db"/>

<!-- Oracle Example -->
<Parameter Name="OracleDriver" Value="oracle.jdbc.driver.OracleDriver" Fixed="true"/>
<Parameter Name="OracleURL" Value="jdbc:oracle:thin:@localhost:1521:ORCL"/>

<!-- REST API Example -->
<Parameter Name="RESTEndpoint" Value="https://api.example.com/v1/objects"/>
<Parameter Name="RESTMethod" Value="POST"/>
<Parameter Name="RESTAuth" Value="Bearer {token}"/>
```

### Monitoring & Alerting

**Setup transaction monitoring:**

```xml
<!-- Enable monitoring -->
<Parameter Name="MONITORING_ENABLED" Value="true"/>
<Parameter Name="ALERT_ON_FAILURE" Value="true"/>
<Parameter Name="ALERT_EMAIL" Value="admin@example.com"/>

<!-- Alert thresholds -->
<Parameter Name="ALERT_TIMEOUT_MS" Value="600000"/>
<Parameter Name="ALERT_ERROR_COUNT" Value="5"/>
<!-- Alert if 5+ errors in transaction -->
```

---

## Summary & Quick Reference

### Key Takeaways

1. **InterWeave is an enterprise ETL platform** focused on Salesforce ↔ QuickBooks synchronization
2. **Built on Eclipse RCP** with Java-based runtime engine
3. **Uses XSLT for data transformations** between incompatible systems
4. **Supports 59+ pre-configured transaction types** with flexible scheduling
5. **Implements stateful checkpointing** to enable incremental syncing
6. **Provides HA architecture** with multiple redundant servers
7. **Scales horizontally** through Tomcat clustering
8. **Suitable for mid-market integrations** requiring real-time or near-real-time sync

### Quick Start Checklist

- [ ] Install JRE 1.5+
- [ ] Set JAVA_HOME environment variable
- [ ] Install QODBC driver (if QB sync needed)
- [ ] Extract IW_IDE.zip archive
- [ ] Launch iw_ide/iw_ide.exe
- [ ] Import sample_projects/Test
- [ ] Edit configuration/im/config.xml
- [ ] Update Salesforce SOAP URL
- [ ] Configure QODBC connection
- [ ] Create XSLT transformation templates
- [ ] Deploy to Tomcat application server
- [ ] Monitor logs for execution
- [ ] Set up automated backups

### File Organization Best Practices

```
/integration_projects/
├── SF2QBBase/              # Production
├── SF2QBBase-DEV/          # Development
├── SF2QBBase-TEST/         # Testing/staging
├── backups/
│   └── SF2QBBase-2024-01-15.zip
├── documentation/
│   ├── setup.md
│   ├── troubleshooting.md
│   └── runbook.md
└── logs/
    └── transaction_logs/
```

### References

- **SDK Version:** 2.2
- **Copyright:** Integration Technologies, Inc. (2006)
- **Salesforce API:** SOAP v47.0
- **QuickBooks Driver:** QODBC 24.0.0.356
- **Application Server:** Apache Tomcat 5.5.33
- **Java Runtime:** JRE 1.5+
- **Eclipse Platform:** RCP Framework

---

**Document Generated:** For AI Agents & LLM Projects  
**Last Updated:** 2024  
**Status:** Complete & Comprehensive  
**Usage:** Open for all integration project development
