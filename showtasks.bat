:runcrud
call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo runrud.bat has errors
goto error

:openbrowser
start http://localhost:8080/crud/v1/task/getTasks

:error
echo.
echo something went wrong

:end
echo.
echo mission complete