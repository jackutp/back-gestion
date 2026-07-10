ARG MODULE=Gateway-Server
FROM eclipse-temurin:25-jdk AS builder
ARG MODULE=Gateway-Server
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
RUN chmod +x mvnw
RUN ./mvnw -pl ${MODULE} -am dependency:go-offline
COPY . .
RUN ./mvnw clean package -DskipTests -pl ${MODULE} -am

FROM eclipse-temurin:25-jre
WORKDIR /app
ARG MODULE=Gateway-Server
COPY --from=builder /app/${MODULE}/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
