FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /build

# Copy pom.xml first for better caching
COPY api/pom.xml .
COPY api/.mvn/ .mvn/
COPY api/mvnw .
COPY api/src/ src/

# Build application
RUN chmod +x mvnw && \
  ./mvnw clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /build/target/*.jar app.jar

# Environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV PORT=8080

# Expose port
EXPOSE ${PORT}

# Run application
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT} --server.address=0.0.0.0"]