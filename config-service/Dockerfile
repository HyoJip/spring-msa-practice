FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
LABEL authors="hyojip"
COPY apiEncrpytionKey.jks apiEncrpytionKey.jks
COPY build/libs/config-service-0.0.1-SNAPSHOT.jar config-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "config-service-0.0.1-SNAPSHOT.jar"]