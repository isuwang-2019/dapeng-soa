#soa-container:base
FROM docker.oa.isuwang.com:5000/system/jdk8:5.0
MAINTAINER ever@iplas.com.cn

# Setting Envirnoment
ENV CONTAINER_HOME /dapeng-container
ENV PATH $CONTAINER_HOME:$PATH

RUN mkdir -p "$CONTAINER_HOME"

COPY target/dapeng-container /dapeng-container

WORKDIR "$CONTAINER_HOME/bin"

COPY fluent-bit/etc /opt/fluent-bit/etc

RUN chmod +x *.sh
