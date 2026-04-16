@echo off
setlocal enabledelayedexpansion

echo Stopping containers...
docker-compose down -v || exit /b 1

echo Starting containers...
docker-compose up -d || exit /b 1

echo Cleaning Maven...
call mvn clean || exit /b 1

echo Running Spring Boot application...
call mvn spring-boot:run || exit /b 1

pause