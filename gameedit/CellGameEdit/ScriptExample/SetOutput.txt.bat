rem <OUTPUT>     		.\output\SetOutput.bat
rem <IMAGE OUTPUT>		.\output\set\
rem <IMAGE TYPE>		png
rem <IMAGE TILE>		false
rem <IMAGE GROUP>		false

javac SetOutput.java -d . -J-Xmx512m
java -cp . SetOutput -Xmx512m
del *.class

#<RESOURCE>#<END RESOURCE>
#<LEVEL>#<END LEVEL>
#<COMMAND>#<END COMMAND>