# Etapa de construção
FROM ubuntu:latest AS builder

# Atualizar pacotes e instalar JDK e Maven
RUN apt-get update && apt-get install -y openjdk-21-jdk maven

# Definir o diretório de trabalho
WORKDIR /app

# Copiar todos os arquivos do projeto para o diretório de trabalho
COPY . .

# Construir o binário da aplicação (JAR)
RUN mvn clean install

# Etapa final
FROM openjdk:21-jdk-slim

# Definir o diretório de trabalho na etapa final
WORKDIR /app

# Expor a porta
EXPOSE 9092

# Copiar o JAR compilado da etapa de construção
COPY --from=builder /app/target/*.jar app.jar

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

