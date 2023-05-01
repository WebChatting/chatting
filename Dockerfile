FROM openjdk:11-jre-slim

WORKDIR /app

RUN ln -fs /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && dpkg-reconfigure -f noninteractive tzdata

# run `make build` first
COPY target/chatting.jar chatting.jar

EXPOSE 3333 8088

ENTRYPOINT ["java", "-Duser.timezone=GMT+8", "-jar", "chatting.jar"]
