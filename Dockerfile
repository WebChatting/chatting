FROM mysql:debian

WORKDIR /workdir

# prepare JRE
RUN apt-get update
RUN apt-get install -y --no-install-recommends --no-install-suggests openjdk-17-jre-headless
RUN rm -rf /var/lib/apt/lists/*

# run `make build` first
COPY target/*.jar app.jar
COPY docker/entrypoint.sh .

# prepare MySQL runtime
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=chatting
ENV MYSQL_USER=webchatting
ENV MYSQL_PASSWORD=webchatting

EXPOSE 3333 8088

CMD ./entrypoint.sh
