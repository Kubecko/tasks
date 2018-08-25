call gradlew build
if "%ERRORLEVEL%" == "0" goto runcrudrun
echo.
echo GRADLEW BUILD has error - breaking work
goto fail

:runcrudrun
call C:\Users\user\Documents\Development\Projects\tasks\runcrud.bat
if "%ERRORLEVEL%" == "0" goto runmychrome
echo Cannot open runcrud
goto fail

:runmychrome
call C:\Program Files (x86)\Google\Chrome\Application\chrome.exe
if "%ERRORLEVEL%" == "0" goto localhostgettasks

:localhostgettasks
start http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.