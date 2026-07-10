FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY eureka-server/pom.xml eureka-server/pom.xml

RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw -pl eureka-server -am dependency:go-offline
COPY . .
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package -DskipTests -pl eureka-server -am

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /app/eureka-server/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
