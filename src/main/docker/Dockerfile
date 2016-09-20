FROM centos:7

RUN yum update -y && \
yum install -y wget && \
yum install -y java-1.8.0-openjdk-headless && \
yum clean all

VOLUME /tmp

COPY wait-for-it.sh /wait-for-it.sh
COPY entrypoint.sh /entrypoint.sh
COPY alarm-service-1.0-SNAPSHOT.jar alarm-service.jar
RUN sh -c 'touch /alarm-service.jar'

ENTRYPOINT ["/bin/bash","./entrypoint.sh"]
