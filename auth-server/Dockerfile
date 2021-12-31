FROM openjdk:11

RUN mkdir -p /frwldrugstore/auth-server/

WORKDIR /frwldrugstore

ENV API_NAME=target/auth-server-1.0.0.jar

COPY ${API_NAME} /frwldrugstore/auth-server/

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "/frwldrugstore/auth-server/auth-server-1.0.0.jar" ]
