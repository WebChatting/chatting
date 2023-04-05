@echo off

docker stop redis >NUL 2>&1
docker rm redis >NUL 2>&1
echo Starting Redis container...
docker run --name redis ^
    -p 6379:6379 ^
    -v "%cd\data\db\redis\data:/data" ^
    -d redis redis-server
if errorlevel 1 (
    echo Failed to start Redis container.
    exit /b 1
)

docker stop mysql >NUL 2>&1
docker rm mysql >NUL 2>&1
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
    echo Failed to start MySQL container.
    exit /b 1
)

echo Waiting for 5 seconds...
echo Containers started successfully.
timeout /t 5 /nobreak

mvn clean spring-boot:run