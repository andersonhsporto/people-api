FROM maven:3.8.6-eclipse-temurin-19-alpine AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests


FROM eclipse-temurin:19-jdk-alpine
RUN mkdir /app
COPY --from=build /project/target/people-api-1.0-SNAPSHOT.jar /app/people-api-1.0-SNAPSHOT.jar
WORKDIR /app
CMD "java" "-jar" "people-api-1.0-SNAPSHOT.jar"