# Build the React frontend
FROM node:24-alpine AS frontend-build
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm ci
COPY frontend/ ./
RUN npm run build

# Build the Spring Boot application and bundle the frontend
FROM maven:3.9.6-eclipse-temurin-8 AS backend-build
WORKDIR /app/backend
COPY backend/pom.xml ./
RUN mvn dependency:go-offline -B
COPY backend/src ./src
COPY --from=frontend-build /app/frontend/dist ./src/main/resources/static
RUN mvn clean package -DskipTests -B

# Run as a non-root user in a small Java image
FROM eclipse-temurin:8-jre-alpine
WORKDIR /app
RUN addgroup -S dan && adduser -S dan -G dan && mkdir -p /app/data && chown -R dan:dan /app
COPY --from=backend-build --chown=dan:dan /app/backend/target/mediflow-api-0.0.1-SNAPSHOT.jar app.jar
USER dan
EXPOSE 8080
ENTRYPOINT ["java","-XX:+UseContainerSupport","-jar","/app/app.jar"]
