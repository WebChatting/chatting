.PHONY: build docker-build run clean

build:
	mvn package -DskipTests

docker-build:
	docker build -t webchatting/chatting .

run: clean build
	docker run -d -p 3333:3333 -p 8088:8088 --name chatting webchatting/chatting

clean:
	-mvn clean
	-docker stop chatting
	-docker rm chatting
	-docker rmi webchatting/chatting
