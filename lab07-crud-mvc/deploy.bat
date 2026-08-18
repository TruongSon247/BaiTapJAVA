@echo off
set CATALINA_HOME=S:\Downloads\Tomcat10
set CATALINA_BASE=S:\Downloads\Tomcat10

echo ===== Dang dung Tomcat =====
call "%CATALINA_HOME%\bin\shutdown.bat"
timeout /t 5 /nobreak

echo ===== Dang xoa cache JSP (work) =====
if exist "%CATALINA_HOME%\work" rmdir /s /q "%CATALINA_HOME%\work"

echo ===== Dang build project =====
cd /d "S:\VSCode\HocJava\lab07-crud-mvc"
call mvn clean package

echo ===== Dang xoa ban cu trong Tomcat =====
if exist "%CATALINA_HOME%\webapps\lab07-crud-mvc.war" del /q "%CATALINA_HOME%\webapps\lab07-crud-mvc.war"
if exist "%CATALINA_HOME%\webapps\lab07-crud-mvc" rmdir /s /q "%CATALINA_HOME%\webapps\lab07-crud-mvc"

echo ===== Dang copy ban moi vao Tomcat =====
copy "S:\VSCode\HocJava\lab07-crud-mvc\target\lab07-crud-mvc.war" "%CATALINA_HOME%\webapps\"

echo ===== Dang khoi dong lai Tomcat =====
call "%CATALINA_HOME%\bin\startup.bat"

echo ===== HOAN TAT! Doi vai giay roi vao trinh duyet kiem tra =====
pause