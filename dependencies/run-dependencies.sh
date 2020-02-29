#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit

docker-compose -f docker-compose-postgres.yml down -v
docker-compose -f docker-compose-postgres.yml up -d

cd - || exit
