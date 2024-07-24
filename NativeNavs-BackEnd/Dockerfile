
FROM openjdk:17

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo Asia/seoul > /etc/timezone

ARG JAR_FILE=build/libs/NativeNavs-BackEnd-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} NativeNavs-BackEnd.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","-Duser.timezone=Asia/Seoul","top", "/NativeNavs-BackEnd.jar"]



