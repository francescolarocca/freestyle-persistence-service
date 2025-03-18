FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia i file sorgenti e il pom.xml
COPY pom.xml ./
COPY src ./src

# Compila il progetto e genera il JAR
RUN mvn clean package -DskipTests

# Usa una base leggera per eseguire l'applicazione
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia il jar generato dalla fase di build
COPY --from=build /app/target/*.jar app.jar

# Comando per avviare il microservizio
ENTRYPOINT ["java", "-jar", "/app/app.jar"]