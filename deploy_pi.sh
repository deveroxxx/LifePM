#!/bin/bash

PI_USER="janos"
PI_HOST="192.168.0.17"
PI_TARGET_DIR="/home/$PI_USER/life_pm"
SSH_KEY="~/.ssh/id_rsa_pi"

echo "Building Spring Boot application..."
./mvnw clean package -DskipTests

# Check if the build was successful
if [ $? -ne 0 ]; then
    echo "Maven build failed! Exiting..."
    exit 1
fi

echo "Creating target directory on Raspberry Pi..."
ssh -i "$SSH_KEY" "$PI_USER@$PI_HOST" "mkdir -p $PI_TARGET_DIR"

echo "Copying files to Raspberry Pi..."
scp -r target docker-compose.yml docker-compose.override.pi.yml .env.pi.docker Dockerfile.pi "$PI_USER@$PI_HOST:$PI_TARGET_DIR/"

#echo "Deploying on Raspberry Pi..."
#ssh -i "$SSH_KEY" "$PI_USER@$PI_HOST" << EOF
#    cd $PI_TARGET_DIR
#    docker-compose -f docker-compose.yml -f docker-compose.override.pi.yml --env-file .env.local.docker up -d --build
#EOF

echo "Deployment completed successfully!"
