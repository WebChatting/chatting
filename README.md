# chatting
一个基于[Chatroom#49](https://github.com/Kanarienvogels/Chatroom/pull/49)的聊天系统。

## Usage
### 1. 根据主机环境修改[配置文件](src/main/resources/application.yml)
### 2. 先后执行 [chatting.sql](chatting.sql) 和 [init.sql](init.sql)
### 3. Build and Run
```shell
mvn package
# peek inside
# jar tvf target/chatting-springboot-xxx.jar
java -jar target/chatting-springboot-xxx.jar
```

## ToDo
- [x] refactor backend with SpringBoot
- [ ] design and use db
  - [x] use Mybatis
  - [ ] use Druid
- [x] use @Slf4j replace logger field
- [ ] refactor frontend
- [ ] use Logback replace slf4j
- [ ] use Redis
- [ ] automatically delete files when they expire
- [ ] deploy with docker
