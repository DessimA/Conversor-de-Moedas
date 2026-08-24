FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn clean package -q -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/conversor-moedas-1.0-SNAPSHOT.jar .
CMD ["java", "-jar", "conversor-moedas-1.0-SNAPSHOT.jar"]
