FROM maven:latest AS builder

WORKDIR /workdir

COPY pom.xml .
COPY src src

# skip tests because tests require MySQL runtime
RUN mvn package -DskipTests

FROM mysql:debian

WORKDIR /workdir

# prepare JRE
RUN apt-get update
RUN apt-get install -y --no-install-recommends --no-install-suggests openjdk-17-jre-headless
RUN rm -rf /var/lib/apt/lists/*

COPY --from=builder /workdir/target/*.jar app.jar
COPY docker/entrypoint.sh .

# prepare MySQL runtime
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=chatting
ENV MYSQL_USER=webchatting
ENV MYSQL_PASSWORD=webchatting

EXPOSE 3333 8088

CMD ./entrypoint.sh
