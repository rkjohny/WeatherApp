# Use an official Maven runtime as a parent image with JDK 21
FROM maven:3.9.8-eclipse-temurin-21

WORKDIR /

ADD target/weatherapp-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD java -jar -Dspring.profiles.active=prod app.jar