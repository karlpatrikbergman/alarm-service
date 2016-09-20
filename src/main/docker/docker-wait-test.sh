#!/bin/bash
# TODO: Create Gradle task instead
#
# Script for testing wait-for-it.sh:
# - Run ./gradlew clean build -x test
# - Start MariaDB container
# - Provide assigned ip address and ip number as arguments to wait-for-it.sh

set -o nounset

cp ././../../../build/libs/alarm-service-1.0-SNAPSHOT.jar alarm-service-1.0-SNAPSHOT.jar

IMAGE=alarmservice_image
CONTAINER=alarmservice_container

docker rm ${CONTAINER} -f
docker rmi ${IMAGE}
docker build -t ${IMAGE} .
docker run --entrypoint ./wait-for-it.sh --name ${CONTAINER} ${IMAGE} --host=172.17.0.2 --port=3306 --timeout=5 --strict -- ./entrypoint.sh
