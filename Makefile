.PHONY: build run clean

build:
    # Replace service name
    sed -i 's/host: redis/host: 127.0.0.1/g; s/mysql:3306/localhost:3306/g' src/main/resources/application.yml
	mvn package -DskipTests
    # Restore
    sed -i 's/host: 127.0.0.1/host: redis/g; s/localhost:3306/mysql:3306/g' src/main/resources/application.yml

run: clean build
	docker-compose up -d

clean:
	-mvn clean
	-docker-compose down
	-docker rm chatting
	-docker rmi webchatting/chatting
	-docker image prune -f