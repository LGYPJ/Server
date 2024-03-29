FROM openjdk:17-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=build/libs/Garamgaebi-Server-0.0.1-SNAPSHOT.jar
VOLUME ["/var/log"]
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Duser.timzone=Asia/Seoul","/app.jar"]