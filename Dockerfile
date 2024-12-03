FROM eclipse-temurin:23-jdk

ARG app-dir=/app

ARG port=4000
WORKDIR ${app-dir}

COPY .mvn .mvn
COPY src src
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .

RUN ./mvnw.cmd clean package -Dmaven.tests.skip=true

ENV SERVER_PORT 4000

EXPOSE ${SERVER_PORT}

#run app using ENTRYPOINT or CMD

ENTRYPOINT java -jar target/vttp5a_ssf_day15l-0.0.1-SNAPSHOT.jar
#change the jar name