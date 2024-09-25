# Usando uma imagem do OpenJDK
FROM openjdk:17-jdk-slim

# Diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR para o diretório de trabalho
COPY target/banner.jar app.jar

# Expõe a porta que sua aplicação Spring Boot está usando
EXPOSE 9092

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]
