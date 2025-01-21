@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM check if input.txt exists
if not exist input.txt (
    echo ********** ERROR: input.txt not found **********
    exit /b 1
)

REM check if EXPECTED.TXT exists
if not exist EXPECTED.TXT (
    echo ********** ERROR: EXPECTED.TXT not found **********
    exit /b 1
)

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin Hirono < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
IF ERRORLEVEL 1 (
    echo ********** TEST FAILED **********
    exit /b 1
) ELSE (
    echo ********** TEST PASSED **********
)
