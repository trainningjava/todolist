FROM openjdk:17-jdk-slim

EXPOSE 8080

RUN ls

COPY ./docker/*.jar /app/app.jar
WORKDIR /app
RUN ls

ENTRYPOINT ["java", "-jar", "app.jar"]