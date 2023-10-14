FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY ./docker/*.jar /app/app.jar

WORKDIR /app

ENTRYPOINT ["java", "-jar", "app.jar"]