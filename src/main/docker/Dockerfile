FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG alarmservice_app
COPY "wait-for-it.sh" "/wait-for-it.sh"
COPY "entrypoint.sh" "/entrypoint.sh"
COPY $alarmservice_app "/alarmservice.jar"

ENTRYPOINT ["/bin/sh","./entrypoint.sh"]
