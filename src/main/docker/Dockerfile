FROM centos:7

RUN yum update -y && \
yum install -y wget && \
yum install -y java-1.8.0-openjdk-headless && \
yum clean all

VOLUME /tmp

#TODO: Move COPY to one line for all files COPY["file1", "file2", ..., "/"]
COPY wait-for-it.sh /wait-for-it.sh
COPY entrypoint.sh /entrypoint.sh
COPY alarmservice-1.0-SNAPSHOT.jar alarmservice.jar
RUN sh -c 'touch /alarmservice.jar'

ENTRYPOINT ["/bin/bash","./entrypoint.sh"]
