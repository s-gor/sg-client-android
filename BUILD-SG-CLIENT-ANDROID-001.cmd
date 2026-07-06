@echo off
setlocal EnableExtensions
chcp 65001 >nul
title SG Client Android 001 - Local Build

cd /d "%~dp0"

where gradle.exe >nul 2>nul
if errorlevel 1 (
  echo.
  echo Gradle is not installed in PATH.
  echo Use the GitHub Actions build for SG-CLIENT-ANDROID-001.
  echo.
  start "" "https://github.com/s-gor/sg-client-android/actions"
  pause
  exit /b 1
)

if not defined JAVA_HOME (
  echo ERROR: JAVA_HOME is not set. Use JDK 17.
  pause
  exit /b 1
)

gradle --no-daemon testDebugUnitTest assembleDebug
if errorlevel 1 (
  echo.
  echo BUILD FAILED
  pause
  exit /b 1
)

copy /y "app\build\outputs\apk\debug\app-debug.apk" "SG-CLIENT-ANDROID-001-debug.apk" >nul
if errorlevel 1 (
  echo ERROR: APK was built but could not be copied.
  pause
  exit /b 1
)

echo.
echo BUILD SUCCESSFUL
echo %CD%\SG-CLIENT-ANDROID-001-debug.apk
echo.
pause
