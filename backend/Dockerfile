# syntax=docker/dockerfile:1

# ---- build ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
# cache de dependências: só refaz o download se o pom.xml mudar
COPY pom.xml .
RUN mvn -B -q dependency:go-offline || true
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ---- runtime ----
FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app
RUN useradd --system --no-create-home appuser
COPY --from=build /build/target/*.jar app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
