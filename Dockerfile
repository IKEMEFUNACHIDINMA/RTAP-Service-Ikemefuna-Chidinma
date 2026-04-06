# Stage 1: Build the application using actively maintained Maven image
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application using actively maintained JRE image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Force the port and cap the memory so Render's free tier doesn't kill the app
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT java -Xmx256m -Dserver.port=$PORT -jar app.jar
