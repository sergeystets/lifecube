FROM openjdk:8-jdk-alpine
ARG version=0.0.1-SNAPSHOT
COPY backend/target/backend-${version}.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
