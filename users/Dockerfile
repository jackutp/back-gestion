FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY users/pom.xml users/pom.xml

RUN chmod +x mvnw
RUN --mount=type=cache,id=maven-cache,target=/root/.m2 ./mvnw -pl users -am dependency:go-offline
COPY . .
RUN --mount=type=cache,id=maven-cache,target=/root/.m2 ./mvnw clean package -DskipTests -pl users -am

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /app/users/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
