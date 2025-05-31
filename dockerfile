# Etapa 1: construir el proyecto con Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copia el proyecto completo
COPY . .

# Construye el JAR (sin ejecutar pruebas)
RUN mvn clean package -DskipTests

# Etapa 2: imagen ligera para ejecutar el JAR
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia el .jar construido desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]