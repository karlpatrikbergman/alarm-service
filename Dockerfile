FROM openjdk:8-jdk-alpine
VOLUME /tmp
# We should make sure to place higher those files that are less likely to change.
COPY "wait-for-it.sh" "/wait-for-it.sh"
COPY "entrypoint.sh" "/entrypoint.sh"
COPY build/libs/alarmservice-1.0-SNAPSHOT.jar "/alarmservice.jar"

ENTRYPOINT ["/bin/sh","./entrypoint.sh"]