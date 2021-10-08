#soa-container:base
FROM harbor.kuaisuwang.com.cn/system/jdk8:6.1
MAINTAINER ever@iplas.com.cn

# Setting Envirnoment
ENV CONTAINER_HOME /dapeng-container
ENV PATH $CONTAINER_HOME:$PATH

RUN mkdir -p "$CONTAINER_HOME"

COPY target/dapeng-container /dapeng-container

WORKDIR "$CONTAINER_HOME/bin"

RUN chmod +x *.sh
