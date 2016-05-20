@echo off
goto compile

:compile
echo [Yigo-Weixin] ################################################ mvn compile ################################################
call mvn compile -Dmaven.test.skip=true
goto package

:package
echo [Yigo-Weixin] ################################################ mvn package ################################################
call mvn package -Dmaven.test.skip=true  

pause