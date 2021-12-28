FROM openjdk

RUN mkdir -p /frwldrugstore/apigateway/

WORKDIR /frwldrugstore

ENV API_NAME=api-gateway-1.0.0.jar

COPY ${API_NAME} /frwldrugstore/apigateway/

EXPOSE 8082

ENTRYPOINT [ "java", "-jar", "/frwldrugstore/apigateway/api-gateway-1.0.0.jar" ]