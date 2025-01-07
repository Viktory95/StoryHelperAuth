FROM gradle:8.12.0-jdk17-alpine AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build  -x test --no-daemon

FROM openjdk:17

LABEL maintainer="vi"
EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/story-helper-auth.jar

ENTRYPOINT ["java", "-jar","/app/story-helper-auth.jar"]