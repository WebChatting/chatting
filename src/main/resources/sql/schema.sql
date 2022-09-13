set foreign_key_checks = 0;

-- create chatting database
drop database if exists chatting;
create database chatting;
-- enter chatting 
use chatting;

-- create user table
create table if not exists `user` (
    `user_id` bigint unsigned not null auto_increment comment '用户ID',
    `username` varchar(20) unique not null comment '用户账号',
    `password` varchar(16) not null comment '用户密码',
    `avatar_path` varchar(255) not null comment '用户头像存储路径',
    primary key (`user_id`) using btree,
    unique index (`username`) using btree
) ENGINE=InnoDB auto_increment=501 DEFAULT charset=utf8;

-- create message table
create table if not exists `message` (
	`message_id` bigint unsigned not null auto_increment comment '消息ID',
    `from_id` bigint unsigned not null comment '发送方用户ID',
    `to_id` bigint unsigned not null comment '接收方用户ID',
    `type` int not null comment '消息类型',
    `content_type` int not null comment '消息内容类型',
    `content_id` bigint unsigned not null comment '消息ID',
    primary key (`message_id`) using btree,
    constraint FK_MESSAGE_FROM_ID foreign key(`from_id`) references user(`user_id`) on delete cascade,
    constraint FK_MESSAGE_TO_ID foreign key(`to_id`) references user(`user_id`) on delete cascade
) ENGINE=InnoDB DEFAULT charset=utf8;

-- create group table
create table if not exists `group` (
	`group_id` bigint unsigned not null auto_increment comment '群组ID',
    `group_name` varchar(20) not null comment '群组名',
    `avatar_path` varchar(266) not null comment '群组头像存储路径',
    `owner` bigint unsigned not null comment '创建者ID',
    primary key (`group_id`) using btree,
    constraint FK_GROUP_OWNER foreign key(`owner`) references user(`user_id`) on delete cascade
) ENGINE=InnoDB auto_increment=101 DEFAULT charset=utf8;

-- create relation table
create table if not exists `relation` (
	`id` bigint unsigned not null auto_increment comment '关系ID',
    `request_id` bigint unsigned not null comment '建立关系时的请求方用户ID',
    `accept_id` bigint unsigned not null comment '建立关系时的接受方用户ID和群组ID',
    `type` int not null comment '关系类型，好友/群友 关系',
    primary key (`id`) using btree,
    constraint FK_RELATION_REQUEST_ID foreign key(`request_id`) references user(`user_id`) on delete cascade
) ENGINE=InnoDB DEFAULT charset=utf8;

create table if not exists `text_content` (
	`id` bigint unsigned not null auto_increment comment '文本消息ID',
    `content` varchar(1024) not null comment '文本消息',
    primary key (`id`) using btree
) engine=InnoDB auto_increment=1001 default charset=utf8;

create table if not exists `file_content` (
	`id` bigint unsigned not null auto_increment comment '文本消息ID',
    `name` varchar(255) not null comment '文件名',
    `size` varchar(12) not null comment '文件大小（带单位）',
    `path` varchar(255) not null comment '文件存储路径',
    primary key (`id`) using btree
) engine=InnoDB auto_increment=1001 default charset=utf8;

set foreign_key_checks = 1;