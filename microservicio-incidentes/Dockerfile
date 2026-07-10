FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY microservicio-incidentes/pom.xml microservicio-incidentes/pom.xml

RUN chmod +x mvnw
RUN --mount=type=cache,id=maven-cache,target=/root/.m2 ./mvnw -pl microservicio-incidentes -am dependency:go-offline
COPY . .
RUN --mount=type=cache,id=maven-cache,target=/root/.m2 ./mvnw clean package -DskipTests -pl microservicio-incidentes -am

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /app/microservicio-incidentes/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
