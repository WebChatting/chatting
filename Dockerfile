FROM openjdk:11-jre-slim

WORKDIR /app

# run `make build` first
COPY target/chatting.jar chatting.jar

EXPOSE 3333 8088

ENTRYPOINT ["java", "-jar", "chatting.jar"]
