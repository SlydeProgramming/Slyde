
@echo off

:: Run the first command and check for error
call ./gradlew run --quiet --no-daemon --args="./test.sly" 2>nul
if ERRORLEVEL 1 (
    echo Gradle failed. Stopping script.
    exit /b 1
)

:: If it succeeded, continue
call clang ./out.ll -Wno-override-module
if ERRORLEVEL 1 (
    echo Clang failed. Stopping script.
    exit /b 1
)

call a.exe