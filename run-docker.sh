#!/bin/bash

CONTAINER_NAME="my-spring-container"
IMAGE_NAME="my-spring-app"

echo "Stopping and removing previous container (if exists)..."
docker stop $CONTAINER_NAME 2>/dev/null && docker rm $CONTAINER_NAME 2>/dev/null

echo "Building Spring Boot application..."
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "Maven build failed! Exiting..."
    exit 1
fi

echo "Building Docker image..."
docker build -t $IMAGE_NAME .

if [ $? -ne 0 ]; then
    echo "Docker build failed! Exiting..."
    exit 1
fi

echo "Running Docker container..."
docker run -d -p 8081:8081 --name $CONTAINER_NAME $IMAGE_NAME

echo "Container is now running on http://localhost:8081"
