# chatting

chatting 后端仓库。前端请前往[chatting-vue](https://github.com/WebChatting/chatting-vue)。

## Usage

### 1. 配置数据库

```sql
-- mysql -u root -p
CREATE DATABASE chatting;
CREATE USER 'webchatting'@'localhost' IDENTIFIED BY 'webchatting';
GRANT ALL PRIVILEGES ON chatting.* TO 'webchatting'@'localhost';
quit
```

### 1. 根据主机环境修改[配置文件](src/main/resources/application.yml)

主要是数据库 URL 和账号密码：

```yml
datasource:
  url: jdbc:mysql://localhost:3306/chatting?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true
  username: webchatting
  password: webchatting
```

### 2. Build and Run

```shell
# run application
mvn spring-boot:run

## pack
mvn package
# peek inside
# jar tvf target/chatting-springboot-xxx.jar
# run the jar package
java -jar target/chatting-springboot-xxx.jar

# run with docker
docker build -t sxrekord/chatting .
docker run -d -p 8088:8088 -p 3333:3333 sxrekord/chatting
```

## Optimize
### Database
see [query.sql](src/main/resources/sql/query.sql).

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
