@echo off

set ACTION=%1

if /i "%ACTION%"=="--help" (
    echo Usage: local-deploy.bat [--run-application ^| --test ^| --docker-compose] [--help]
    echo.
    echo Options:
    echo  --run-application   Starts the Spring Boot application after starting the Redis and MySQL containers
    echo  --test              Runs the tests using Maven after starting the Redis and MySQL containers
    echo  --docker-compose    Starts the entire project using Docker Compose file
    echo  --help              Displays this help message
    exit /b
)

if /i "%ACTION%"=="--docker-compose" (
	call :stopContainer redis
	call :stopContainer mysql
	REM Replace service name
	powershell -Command "(Get-Content -Encoding utf8 src\main\resources\application.yml) -replace 'host: 127.0.0.1','host: redis' -replace 'localhost:3306','mysql:3306' | Set-Content -Encoding utf8 src\main\resources\application.yml"
	mvn clean package -DskipTests
	REM Restore
	git restore src\main\resources\application.yml
	docker-compose down
	call :stopContainer chatting
	docker rmi "webchatting/chatting"
	docker build -t webchatting/chatting ..
	docker-compose up
)

call :stopContainer redis
echo Starting Redis container...
docker run --name redis ^
    -p 6379:6379 ^
    -v "%cd\data\db\redis\data:/data" ^
    -d redis redis-server
if errorlevel 1 (
    call :outputErrorMsg redis
    exit /b 1
)

call :stopContainer mysql
echo Starting MySQL container...
docker run --name mysql ^
    -p 3306:3306 ^
    -v "%cd\data\db\mysql:/var/lib/mysql" ^
    -e MYSQL_ROOT_PASSWORD=root ^
    -e MYSQL_ALLOW_EMPTY_PASSWORD=no ^
    -e MYSQL_DATABASE=chatting ^
    -e MYSQL_USER=webchatting ^
    -e MYSQL_PASSWORD=webchatting ^
    -d mysql ^
    --default-authentication-plugin=mysql_native_password
if errorlevel 1 (
    call :outputErrorMsg mysql
    call :stopContainer redis
    call :stopContainer mysql
    exit /b 1
)

echo Containers started successfully.

if /i "%ACTION%"=="--run-application" (
	call :wait 5
	mvn clean spring-boot:run
) else if /i "%ACTION%"=="--test" (
	call :wait 5
	mvn clean test
)
exit /b

:stopContainer
echo Stopping %1 container...
docker stop "%1" >NUL 2>&1
docker rm "%1" >NUL 2>&1
exit /b

:outputErrorMsg
SET "string=%~1"
SET "firstLetter=%string:~0,1%"
SET "remainingLetters=%string:~1%"
SET "capitalized=%firstLetter:~0,1%%firstLetter:~1,1%^%remainingLetters%"
echo echo Failed to start %capitalized% container.
exit /b

:wait
echo Waiting for %1 seconds...
timeout /t %1 /nobreak
exit /b
