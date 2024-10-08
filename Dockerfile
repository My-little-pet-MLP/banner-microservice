FROM ubuntu:latest AS builder

RUN apt-get update && apt-get install -y openjdk-21-jdk maven

WORKDIR /app

COPY . .

RUN mvn clean install

FROM ubuntu:latest

COPY --from=builder /app/target/*.jar /app/app.jar

WORKDIR /app

EXPOSE 9092

ENTRYPOINT ["java", "-jar", "app.jar"]
