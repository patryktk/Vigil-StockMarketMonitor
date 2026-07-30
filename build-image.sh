#!/bin/bash
set -euo pipefail

VERSION="${1:-0.1}"

SERVICES=(
#    api-service
    scraper-service
#    processor-service
    sheets-service
    eureka-server
    config-server
)

for service in "${SERVICES[@]}"; do
(
    echo "Building $service..."
    docker build \
        -t "$service:$VERSION" \
        -f "$service/Dockerfile" \
        .
) &
done

wait

echo "All images built successfully."