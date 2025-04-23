#!/bin/bash

# Create the docker network if it doesn't exist
docker network create listtick-network 2>/dev/null || true

# Pull postgres version 17 image
docker pull postgres:17

# Stop and remove existing container if it exists
docker stop listtick-db 2>/dev/null || true
docker rm listtick-db 2>/dev/null || true

# Run the postgres container
echo "Creating postgres container..."
docker run -d \
  --name listtick-db \
  --network listtick-network \
  -p 5432:5432 \
  -e POSTGRES_PASSWORD=admin \
  postgres:17

# Wait for postgres to be ready
echo "Waiting for postgres to start..."
sleep 5

# Create all databases
echo "Creating databases..."
docker exec listtick-db psql -U postgres -c "CREATE DATABASE account"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE bucket_list"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE note"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE notification"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE shopping_list"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE task"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE keycloak"

# Update the docker-compose.yaml to use the container name instead of localhost
echo "Database setup complete!"