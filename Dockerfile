FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

ARG MODULE_PATH

# Copy the specific executable snapshot JAR (excluding -sources or .original)
COPY ${MODULE_PATH}/target/*-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]