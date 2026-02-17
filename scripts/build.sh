#!/bin/bash
# Build script for InterWeave Error Framework (Linux/Mac)
# This script compiles Java sources and runs tests using Maven

set -e  # Exit on error

echo "=========================================="
echo "InterWeave Error Framework Build Script"
echo "=========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven not found in PATH"
    echo ""
    echo "Please install Apache Maven from: https://maven.apache.org/download.cgi"
    echo "And ensure 'mvn' is in your PATH environment variable"
    echo ""
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java not found in PATH"
    echo ""
    # Try to use bundled JRE
    if [ -d "../jre" ]; then
        echo "Using bundled JRE..."
        export JAVA_HOME="$(cd "$(dirname "$0")/.." && pwd)/jre"
        export PATH="$JAVA_HOME/bin:$PATH"
    else
        echo "Please install Java Development Kit (JDK) 8 or higher"
        exit 1
    fi
fi

echo "Maven version:"
mvn --version
echo ""

# Parse command line arguments
BUILD_TARGET="package"
SKIP_TESTS=false
CLEAN=false

for arg in "$@"; do
    case $arg in
        clean)
            CLEAN=true
            ;;
        test)
            BUILD_TARGET="test"
            ;;
        verify)
            BUILD_TARGET="verify"
            ;;
        install)
            BUILD_TARGET="install"
            ;;
        skip-tests|-DskipTests)
            SKIP_TESTS=true
            ;;
        *)
            echo "Unknown argument: $arg"
            echo "Usage: $0 [clean] [test|verify|install|package] [skip-tests]"
            exit 1
            ;;
    esac
done

# Build Maven command
MVN_CMD="mvn"

if [ "$CLEAN" = true ]; then
    MVN_CMD="$MVN_CMD clean"
fi

MVN_CMD="$MVN_CMD $BUILD_TARGET"

if [ "$SKIP_TESTS" = true ]; then
    MVN_CMD="$MVN_CMD -DskipTests"
fi

echo ""
echo "Executing: $MVN_CMD"
echo ""

# Run Maven build
$MVN_CMD

echo ""
echo "=========================================="
echo "BUILD SUCCESSFUL"
echo "=========================================="
echo ""
echo "Build output: target/iw-error-framework-1.0.0.jar"
echo "Test reports: target/surefire-reports/"
echo ""

# Ask if user wants to deploy to Tomcat
read -p "Deploy to Tomcat? (y/n): " DEPLOY

if [ "$DEPLOY" = "y" ] || [ "$DEPLOY" = "Y" ]; then
    echo ""
    echo "Deploying to Tomcat..."

    WEBAPP_LIB="../web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib"

    if [ ! -d "$WEBAPP_LIB" ]; then
        echo "Creating $WEBAPP_LIB..."
        mkdir -p "$WEBAPP_LIB"
    fi

    cp -v ../target/iw-error-framework-1.0.0.jar "$WEBAPP_LIB/"

    echo ""
    echo "Deployment successful!"
    echo ""
    echo "Restart Tomcat to load the new classes:"
    echo "  scripts/stop_webportal.bat"
    echo "  scripts/start_webportal.bat"
fi

echo ""
