FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY build/libs/*.jar app.jar

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["/wait-for-it.sh", "db-users-api:5432", "--timeout=60", "--strict", "--", "java", "-jar", "app.jar"]

EXPOSE 8081

#ENTRYPOINT ["java", "-jar", "app.jar"]
