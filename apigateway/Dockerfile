FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
LABEL authors="hyojip"
COPY build/libs/apigateway-0.0.1-SNAPSHOT.jar apigateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "apigateway-0.0.1-SNAPSHOT.jar"]