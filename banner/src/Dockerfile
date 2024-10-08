# Etapa de construção
FROM ubuntu:latest AS builder

RUN apt-get update
RUN apt-get install -y openjdk-21-jdk -y
# Copiar todos os arquivos do projeto para o diretório de trabalho
COPY . .

RUN apt-get install maven -y
# Construir o binário da aplicação (JAR)
RUN mvn clean install

# Etapa final
FROM openjdk:21-jdk-slim

# Expor a porta
EXPOSE 9092

# Copiar o JAR compilado da etapa de construção
COPY --from=builder target/banner-0.0.1-SNAPSHOT.jar app.jar

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]