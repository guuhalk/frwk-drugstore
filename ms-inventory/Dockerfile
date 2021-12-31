FROM openjdk:11

RUN mkdir -p /frwldrugstore/ms-inventory/

WORKDIR /frwldrugstore

ENV API_NAME=target/ms-inventory-1.0.0.jar

COPY ${API_NAME} /frwldrugstore/ms-inventory/

EXPOSE 8085

ENTRYPOINT [ "java", "-jar", "/frwldrugstore/ms-inventory/ms-inventory-1.0.0.jar" ]
