FROM openjdk:11

RUN mkdir -p /frwldrugstore/api-gateway/

WORKDIR /frwldrugstore

COPY ./target/api-gateway-1.0.0.jar /frwldrugstore/api-gateway/

EXPOSE 8082

ENTRYPOINT [ "java", "-jar", "/frwldrugstore/api-gateway/api-gateway-1.0.0.jar" ]
