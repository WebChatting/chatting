# chatting
chatting 后端仓库。前端请前往[chatting-vue](https://github.com/WebChatting/chatting-vue)。

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
- [x] refactor frontend, see [chatting-vue](https://github.com/WebChatting/chatting-vue)
- [ ] use Logback replace slf4j
- [ ] use Redis
- [ ] automatically delete files when they expire
- [x] deploy with docker
