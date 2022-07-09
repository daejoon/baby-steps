FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", \
            "-jar", \
            "/app.jar"]