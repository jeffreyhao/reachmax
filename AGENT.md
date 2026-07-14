# AGENT.md

This file provides guidance to agent when working with code in this repository.

## Project Overview

This is an android app.

## Build Commands

```bash
# Clean build
./gradlew clean

# Build a package
./gradlew assembleReachMaxRelease
./gradlew assembleReachMaxDevelop

```

## Architecture

### Layer Hierarchy (top to bottom)
1. **app** - Main application module with shared business logic
4. **layer_base** - Foundation libraries (network, API, utilities, UI components)

### Dependency Rule
Each layer depends on all layers below it. Same-layer dependencies use interface/implementation separation.

### Key Directories
- `app/src/main/java/com/benefit/` - Main business logic
- `layer_base/{module}/` - Shared base modules
- `buildSrc/` - Gradle build configuration (Groovy/Java)

## Build Configuration

- **Gradle**: 7.6.3
- **Android Gradle Plugin**: 7.4.2
- **Kotlin**: 2.2.0
- **compileSdk**: 34
- **targetSdk**: 35
- **minSdk**: 24
- **JDK**: 17

### Build Types
- `debug` - Debuggable, no minification
- `release` - Production with R8 minification
- `develop` - Test server with debug logging
- `publish` - Production with debug enabled

## Key Technologies

- **Architecture**: MVP pattern with ViewBinding/DataBinding
- **Language**: Java(primary) + Kotlin(It's best not to use) mixed codebase
- **DI**: Manual dependency injection via ConfigModule
- **Network**: Retrofit + OkHttp
- **Database**: DBFlow
