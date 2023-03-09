FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} zepe-backend.jar
ENTRYPOINT ["java","-jar","zepe-backend.jar"]
EXPOSE 9790
