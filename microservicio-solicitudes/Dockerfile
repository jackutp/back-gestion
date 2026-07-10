FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY microservicio-solicitudes/pom.xml microservicio-solicitudes/pom.xml

RUN chmod +x mvnw
RUN --mount=type=cache,id=cache-maven,target=/root/.m2 ./mvnw -pl microservicio-solicitudes -am dependency:go-offline
COPY . .
RUN --mount=type=cache,id=cache-maven,target=/root/.m2 ./mvnw clean package -DskipTests -pl microservicio-solicitudes -am

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /app/microservicio-solicitudes/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
