@echo off

set path=%~d0%~p0

:start
     
"%path%pngcrush.exe" -rem alla -brute -reduce %1 dest.png
move /y dest.png %1

shift
if NOT x%1==x goto start

pause