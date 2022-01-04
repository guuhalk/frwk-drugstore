FROM openjdk:8

RUN mkdir -p /frwldrugstore/ms-drugstores/

WORKDIR /frwldrugstore

ENV API_NAME=target/ms-drugstores-1.0.0.jar

COPY ${API_NAME} /frwldrugstore/ms-drugstores/

EXPOSE 8083

ENTRYPOINT [ "java", "-jar", "/frwldrugstore/ms-drugstores/ms-drugstores-1.0.0.jar" ]
