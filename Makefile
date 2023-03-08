.PHONY: build run clean

build:
	docker build -t webchatting/chatting .

run: clean build
	docker run -d -p 3333:3333 -p 8088:8088 --name chatting webchatting/chatting

clean:
	-docker stop chatting
	-docker rm chatting
	-docker rmi webchatting/chatting
