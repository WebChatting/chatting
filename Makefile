.PHONY: build run clean

build:
	mvn package -DskipTests

run: clean build
	docker-compose up -d

clean:
	-mvn clean
	-docker-compose down
	-docker rm chatting
	-docker rmi webchatting/chatting
	-docker image prune -f