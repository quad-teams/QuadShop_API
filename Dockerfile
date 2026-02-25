FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY . .
RUN ./gradlew clean build

ENTRYPOINT ["java", "-jar", "build/libs/QuadShop_API-0.0.1-SNAPSHOT.jar"]