@echo off
echo Compiling Collaborative To-Do List...
if not exist "bin" mkdir bin
javac -d bin -cp "lib\gson-2.10.1.jar" -sourcepath src src\com\todo\Main.java src\com\todo\Simulate.java
echo Compilation complete!
