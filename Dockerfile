# Etapa de construção
FROM ubuntu:latest AS builder

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .

# Construir o binário da aplicação
RUN apt-get install maven -y
RUN mvn clean install

# Etapa final
FROM openjdk:21-jdk-slim

EXPOSE 9092

# Copiar o JAR compilado da etapa de construção
COPY --from=builder /target/*.jar app.jar

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
