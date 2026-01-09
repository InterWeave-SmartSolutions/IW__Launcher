# Build Configuration Guide

This document describes how to build the InterWeave Error Framework using Maven.

## Prerequisites

- **Java Development Kit (JDK) 8 or higher**
  - The bundled JRE at `jre/` can be used for runtime, but JDK is required for compilation
  - Download from: https://adoptopenjdk.net/

- **Apache Maven 3.6+**
  - Download from: https://maven.apache.org/download.cgi
  - Ensure `mvn` is in your PATH

## Project Structure

```
.
├── pom.xml                          # Maven build configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/interweave/
│   │           ├── error/           # Error framework classes
│   │           ├── validation/      # Validation services
│   │           ├── web/             # Web filters
│   │           └── help/            # Help system
│   └── test/
│       └── java/
│           └── com/interweave/
│               ├── error/           # Error framework tests
│               ├── validation/      # Validation tests
│               └── integration/     # Integration tests
├── schemas/                         # XSD schema files
├── docs/errors/                     # Error documentation
└── web_portal/                      # Web application files
```

## Build Commands

### Compile All Code

```bash
mvn compile
```

Compiles all source code in `src/main/java/` to `target/classes/`

### Run Unit Tests

```bash
mvn test
```

Runs all unit tests (excludes integration tests). Test reports generated in `target/surefire-reports/`

### Run All Tests (Including Integration Tests)

```bash
mvn verify
```

Or use the `all-tests` profile:

```bash
mvn test -P all-tests
```

### Package as JAR

```bash
mvn package
```

Creates `target/iw-error-framework-1.0.0.jar` containing all compiled classes.

### Quick Build (Skip Tests)

```bash
mvn package -P skip-tests
```

Or:

```bash
mvn package -DskipTests
```

### Clean Build Artifacts

```bash
mvn clean
```

Removes the `target/` directory.

### Full Clean Build with Tests

```bash
mvn clean verify
```

### Install to Local Maven Repository

```bash
mvn install
```

Installs the JAR to `~/.m2/repository/` for use by other projects.

## Build Profiles

### all-tests

Runs all tests including integration tests:

```bash
mvn test -P all-tests
```

### skip-tests

Builds without running tests:

```bash
mvn package -P skip-tests
```

### generate-docs

Generates Javadoc documentation:

```bash
mvn javadoc:javadoc -P generate-docs
```

Javadocs created in `target/site/apidocs/`

## Dependencies

The build includes the following dependencies:

- **javax.servlet-api 3.1.0** (provided by Tomcat)
- **xalan 2.7.2** - XSLT processing
- **xercesImpl 2.12.2** - XML parsing
- **xml-apis 1.4.01** - XML API interfaces
- **junit 4.13.2** (test scope) - Unit testing

## Deployment to Tomcat

After building, deploy the classes to the web application:

### Option 1: Copy JAR to WEB-INF/lib

```bash
mvn package
cp target/iw-error-framework-1.0.0.jar web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/
```

### Option 2: Copy Classes Directly

```bash
mvn compile
cp -r target/classes/* web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes/
```

### Option 3: Copy to Existing Compiled Servlet Sources

Since the webapp has its own source directory:

```bash
# Copy source files to webapp source directory
cp -r src/main/java/com/interweave/* \
    web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/

# Compile using Tomcat's Jasper compiler or external javac
```

## Test Reports

### Unit Test Reports

After running `mvn test`, view reports:

```
target/surefire-reports/
├── TEST-*.xml                    # XML test results
└── *.txt                         # Text summaries
```

### Integration Test Reports

After running `mvn verify`, view reports:

```
target/failsafe-reports/
├── TEST-*.xml                    # XML test results
└── *.txt                         # Text summaries
```

## IDE Integration

### Eclipse

1. Install m2e (Maven Integration for Eclipse)
2. Import as Maven project: `File > Import > Maven > Existing Maven Projects`
3. Select the root directory containing `pom.xml`

### IntelliJ IDEA

1. Open Project: `File > Open` and select `pom.xml`
2. IntelliJ will auto-detect Maven configuration
3. Wait for dependency downloads to complete

### VS Code

1. Install "Java Extension Pack" and "Maven for Java"
2. Open folder containing `pom.xml`
3. Maven sidebar will appear with build commands

## Continuous Integration

### GitHub Actions Example

```yaml
name: Build and Test

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 8
        uses: actions/setup-java@v2
        with:
          java-version: '8'
          distribution: 'adopt'
      - name: Build with Maven
        run: mvn clean verify
      - name: Upload Test Reports
        uses: actions/upload-artifact@v2
        with:
          name: test-reports
          path: target/surefire-reports/
```

## Troubleshooting

### Compilation Errors

**Issue**: Classes not found during compilation

**Solution**: Ensure all dependencies are downloaded:

```bash
mvn dependency:resolve
mvn dependency:tree  # View dependency tree
```

### Test Failures

**Issue**: Tests fail due to missing resources

**Solution**: Ensure test resources are in `src/test/resources/` and build includes them:

```bash
mvn clean test
```

### Out of Memory During Build

**Issue**: `OutOfMemoryError` during compilation or tests

**Solution**: Increase Maven heap size:

```bash
export MAVEN_OPTS="-Xmx1024m"
mvn clean package
```

### Servlet API Conflicts

**Issue**: Multiple servlet-api JARs causing conflicts

**Solution**: The servlet-api dependency is marked as `provided` scope, so it won't be included in the JAR. Tomcat provides this at runtime.

## Build Output

Successful build output structure:

```
target/
├── classes/                              # Compiled main classes
│   └── com/interweave/
│       ├── error/
│       ├── validation/
│       ├── web/
│       └── help/
├── test-classes/                         # Compiled test classes
├── surefire-reports/                     # Unit test reports
├── failsafe-reports/                     # Integration test reports
└── iw-error-framework-1.0.0.jar         # Packaged JAR
```

## Next Steps

After building:

1. **Deploy to Tomcat**: Copy JAR to `WEB-INF/lib/` or classes to `WEB-INF/classes/`
2. **Restart Tomcat**: `./web_portal/tomcat/bin/shutdown.sh && ./web_portal/tomcat/bin/startup.sh`
3. **Test Error Pages**: Visit `http://localhost:8080/iw-business-daemon/ErrorMessage.jsp`
4. **Check Logs**: Monitor `web_portal/tomcat/logs/catalina.out` for errors

## Additional Resources

- [Maven Documentation](https://maven.apache.org/guides/)
- [Maven POM Reference](https://maven.apache.org/pom.html)
- [JUnit 4 Documentation](https://junit.org/junit4/)
- [Servlet 3.1 Specification](https://javaee.github.io/servlet-spec/)
