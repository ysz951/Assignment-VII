FROM openjdk:8-jdk-alpine
ADD target/contest-1.0-SNAPSHOT.jar contest-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD java -jar contest-1.0-SNAPSHOT.jar
