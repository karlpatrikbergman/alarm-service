FROM centos:7
RUN yum update -y && \
yum install -y wget && \
yum install -y java-1.8.0-openjdk-headless && \
yum clean all
# For info on /tmp see https://spring.io/guides/gs/spring-boot-docker/
VOLUME /tmp
# We should make sure to place higher those files that are less likely to change.
EXPOSE 8080
COPY "wait-for-it.sh" "/wait-for-it.sh"
COPY "entrypoint.sh" "/entrypoint.sh"
COPY build/libs/alarmservice-1.0-SNAPSHOT.jar "/alarmservice.jar"

ENTRYPOINT ["/bin/bash","./entrypoint.sh"]