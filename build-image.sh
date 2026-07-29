#!/bin/bash
set -e

#VERSION="1.0"

echo "Building api-service..."
docker build -t api-service:0.1 -f api-service/Dockerfile .

echo "Building scraper-service..."
docker build -t scraper-service:0.1 -f scraper-service/Dockerfile .

echo "Building processor-service..."
docker build -t processor-service:0.1 -f processor-service/Dockerfile .

echo "Building sheets-service..."
docker build -t sheets-service:0.1 -f sheets-service/Dockerfile .

echo "Building eureka-server..."
docker build -t eureka-server:0.1 -f eureka-server/Dockerfile .

echo "Building config-service..."
docker build -t config-server:0.1 -f config-server/Dockerfile .

echo "All images built successfully."