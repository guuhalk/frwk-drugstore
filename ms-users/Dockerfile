FROM openjdk:8

RUN mkdir -p /frwldrugstore/ms-users/

WORKDIR /frwldrugstore

ENV API_NAME=target/ms-users-1.0.0.jar

COPY ${API_NAME} /frwldrugstore/ms-users/

EXPOSE 8087

ENTRYPOINT [ "java", "-jar", "/frwldrugstore/ms-users/ms-users-1.0.0.jar" ]
