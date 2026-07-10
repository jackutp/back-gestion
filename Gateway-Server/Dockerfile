FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY Gateway-Server/pom.xml Gateway-Server/pom.xml

RUN chmod +x mvnw
RUN --mount=type=cache,id=maven-cache,target=/root/.m2 ./mvnw -pl Gateway-Server -am dependency:go-offline
COPY . .
RUN --mount=type=cache,id=maven-cache,target=/root/.m2 ./mvnw clean package -DskipTests -pl Gateway-Server -am

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /app/Gateway-Server/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
