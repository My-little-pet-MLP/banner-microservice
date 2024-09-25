FROM wurstmeister/kafka:latest

ENV KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
ENV KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT
ENV KAFKA_CREATE_TOPICS=banners:1:1
ENV KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT
ENV KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
ENV KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092

EXPOSE 9092

# Copia um script de inicialização para o container (opcional)
# COPY start-kafka.sh /usr/bin/start-kafka.sh
# RUN chmod +x /usr/bin/start-kafka.sh

CMD ["/opt/kafka/bin/kafka-server-start.sh", "/opt/kafka/config/server.properties"]

