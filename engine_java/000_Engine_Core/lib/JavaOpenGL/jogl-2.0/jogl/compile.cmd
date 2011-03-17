@echo off
rem parms:
rem 1: mingw.home
rem 2-5: parameters to ant
rem      2: rootrel.build
rem      3: antlr.jar
rem      4: gluegen.user.properties.file
rem      5: user.properties.file
rem      6: win32.c.compiler

set MINGW_BIN=%1%\bin
set PATH=%MINGW_BIN%;%PATH%

cd make
ant all "-Drootrel.build=%2" "-Dantlr.jar=%3" "-Dgluegen.user.properties.file=%4" "-Duser.properties.file=%5" "-Dwin32.c.compiler=%6" -Dbuild.noarchives=true
exit %ERRORLEVEL%
