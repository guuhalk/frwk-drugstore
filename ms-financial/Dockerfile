FROM openjdk:11

RUN mkdir -p /frwldrugstore/ms-financial/

WORKDIR /frwldrugstore

ENV API_NAME=target/ms-financial-1.0.0.jar

COPY ${API_NAME} /frwldrugstore/ms-financial/

EXPOSE 8084

ENTRYPOINT [ "java", "-jar", "/frwldrugstore/ms-financial/ms-financial-1.0.0.jar" ]
