#!/bin/bash

docker network create listtick-network 2>/dev/null || true

docker pull postgres:17

docker stop listtick-db 2>/dev/null || true
docker rm listtick-db 2>/dev/null || true

echo "Creating postgres container..."
docker run -d \
  --name listtick-db \
  --network listtick-network \
  -p 5432:5432 \
  -e POSTGRES_PASSWORD=admin \
  postgres:17

echo "Waiting for postgres to start..."
until docker exec listtick-db pg_isready -U postgres > /dev/null 2>&1; do
  sleep 1
done

echo "Creating databases..."
docker exec listtick-db psql -U postgres -c "CREATE DATABASE account_settings"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE bucket_list"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE note"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE notification"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE shopping_list"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE task"
docker exec listtick-db psql -U postgres -c "CREATE DATABASE keycloak"

echo "Database setup complete!"