#!/usr/bin/env bash

function delete_alarmservice_container_and_image {
    docker rm -f alarmservice_container
    docker rmi -f localhost:5000/alarmservice
    echo ""
    docker ps
    docker images
}
delete_alarmservice_container_and_image

