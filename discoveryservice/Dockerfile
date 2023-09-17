FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
LABEL authors="hyojip"
COPY build/libs/discoveryservice-0.0.1-SNAPSHOT.jar discoveryservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "discoverservice-0.0.1-SNAPSHOT.jar"]