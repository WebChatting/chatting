# chatting
一个基于[Chatroom#49](https://github.com/Kanarienvogels/Chatroom/pull/49)的聊天系统。

## Usage
### 1. 根据主机环境修改[配置文件](src/main/resources/application.yml)
### 2. Build and Run
```shell
mvn package
# peek inside
# jar tvf target/chatting-springboot-xxx.jar
# run
# java -jar target/chatting-springboot-xxx.jar
docker build -t sxrekord/chatting .
docker run -d -p 8088:8088 -p 3333:3333 sxrekord/chatting
```

## ToDo
- [x] refactor backend with SpringBoot
- [x] design and use db
  - [x] use Mybatis
  - [x] use Druid
- [x] use @Slf4j replace logger field
- [ ] refactor frontend
- [ ] use Logback replace slf4j
- [ ] use Redis
- [ ] automatically delete files when they expire
- [x] deploy with docker
