
@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
set "SAYIT_HOME=%SCRIPT_DIR%.."
set "JAR=%SAYIT_HOME%\lib\sayit.jar"

if not exist "%JAR%" (
    echo Say It error: sayit.jar not found at "%JAR%"
    exit /b 1
)

java -jar "%JAR%" %*
exit /b %ERRORLEVEL%
