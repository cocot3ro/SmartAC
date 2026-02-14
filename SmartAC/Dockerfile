# -------- Stage 1: Build --------
FROM gradle:latest AS build
WORKDIR /home/gradle/src

COPY . .

# Build solo el módulo 'server'
RUN gradle :server:buildFatJar --no-daemon

# -------- Stage 2: Runtime --------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el fat jar del módulo 'server'
COPY --from=build /home/gradle/src/server/build/libs/*-all.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]