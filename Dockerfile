FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app

# Copia i file sorgenti e il pom.xml
COPY pom.xml .
COPY src ./src

# Compila il progetto e genera il JAR
RUN mvn clean package -DskipTests


# Comando per avviare il microservizio
ENTRYPOINT ["java", "-jar", "/app/target/micro-0.0.1-SNAPSHOT.jar"]