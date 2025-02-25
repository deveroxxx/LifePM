#!/bin/bash

echo "Building Spring Boot application..."
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "Maven build failed! Exiting..."
    exit 1
fi

docker-compose -f docker-compose.yml -f docker-compose.override.yml --env-file .env.local.docker up -d --build
