FROM eclipse-temurin:17-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} observability.jar
ENTRYPOINT ["java", "-jar", "observability.jar"]