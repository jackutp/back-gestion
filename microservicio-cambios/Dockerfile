FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY microservicio-cambios/pom.xml microservicio-cambios/pom.xml

RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw -pl microservicio-cambios -am dependency:go-offline
COPY . .
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package -DskipTests -pl microservicio-cambios -am

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /app/microservicio-cambios/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
