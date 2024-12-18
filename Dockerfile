FROM gradle:8-jdk21 AS build

WORKDIR /app
COPY . /app/
RUN gradle clean build -x test

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
