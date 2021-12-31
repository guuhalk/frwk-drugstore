FROM openjdk:11

RUN mkdir -p /frwldrugstore/ms-orders/

WORKDIR /frwldrugstore

ENV API_NAME=target/ms-orders-1.0.0.jar

COPY ${API_NAME} /frwldrugstore/ms-orders/

EXPOSE 8086

ENTRYPOINT [ "java", "-jar", "/frwldrugstore/ms-orders/ms-orders-1.0.0.jar" ]
