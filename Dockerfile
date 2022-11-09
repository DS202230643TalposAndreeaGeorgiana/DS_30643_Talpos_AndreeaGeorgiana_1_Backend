FROM openjdk:18.0.2-jdk-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","backend-1.0-SNAPSHOT.jar"]