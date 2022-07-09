#!/bin/sh

echo 'stop docker-compose'
docker-compose stop -t 30

echo 'build docker-compose images'
docker-compose build --force-rm

echo 'run docker-compose'
docker-compose up -d
