FROM openjdk:8-jdk-slim-stretch
ENV SPRING_PROFILES_ACTIVE=production
VOLUME ["/tmp"]
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} clipping-server.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/clipping-server.jar"]
