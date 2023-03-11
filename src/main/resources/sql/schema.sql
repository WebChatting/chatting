set foreign_key_checks = 0;

-- create chatting database
drop database if exists chatting;
create database chatting;
-- enter chatting
use chatting;

-- create user table
create table if not exists `user` (
    `id` bigint unsigned not null auto_increment comment '用户ID',
    `username` varchar(16) unique not null comment '用户账号',
    `password` varchar(16) not null comment '用户密码',
    `avatar_path` varchar(255) not null comment '用户头像存储路径',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key `pk_id` (`id`),
    unique key `uk_username` (`username`)
) ENGINE=InnoDB auto_increment=501 DEFAULT charset=utf8mb4;

-- create group table
create table if not exists `group` (
	`id` bigint unsigned not null auto_increment comment '群组ID',
    `name` varchar(16) unique not null comment '群组名',
    `owner_id` bigint unsigned not null comment '创建者ID',
    `avatar_path` varchar(255) not null comment '群组头像存储路径',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key `pk_id` (`id`),
    unique key `uk_name` (`name`),
    constraint `fk_owner_id` foreign key (`owner_id`) references `user` (`id`) on delete cascade
) ENGINE=InnoDB auto_increment=101 DEFAULT charset=utf8mb4;

-- create message table
create table if not exists `message` (
	`id` bigint unsigned not null auto_increment comment '消息ID',
    `from_id` bigint unsigned not null comment '发送方用户ID',
    `to_id` bigint unsigned not null comment '接收方用户ID',
    `type` tinyint unsigned not null comment '消息类型(0 ~ 255)，私聊(0)/群聊(1)消息',
    `content_type` tinyint unsigned not null comment '消息内容类型，文本(0)/图片(1)/文件(2)',
    `content_id` bigint unsigned not null comment '消息内容ID',
    `create_time` timestamp default current_timestamp comment '消息发送时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key `pk_id` (`id`),
    constraint `fk_message_from_id` foreign key (`from_id`) references `user` (`id`) on delete cascade
) ENGINE=InnoDB DEFAULT charset=utf8mb4;


-- create relation table
create table if not exists `relation` (
	`id` bigint unsigned not null auto_increment comment '关系ID',
    `request_id` bigint unsigned not null comment '建立关系时的请求方用户ID',
    `accept_id` bigint unsigned not null comment '建立关系时的接受方用户ID和群组ID',
    `type` tinyint unsigned not null comment '关系类型，好友(0)/群友(1) 关系',
    `status` tinyint unsigned not null comment '关系状态，等待建立关系(0)/已建立关系(1)/拒绝建立关系(2)/删除已建立关系(3)',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key `pk_id` (`id`),
    constraint `fk_request_id` foreign key (`request_id`) references `user` (`id`) on delete cascade,
    constraint `fk_accept_id` foreign key (`accept_id`) references `user` (`id`) on delete cascade
) ENGINE=InnoDB default charset=utf8mb4;

create table if not exists `text_content` (
	`id` bigint unsigned not null auto_increment comment '文本消息ID',
    `content` varchar(1024) not null comment '文本消息',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key `pk_id` (`id`)
) engine=InnoDB auto_increment=1001 default charset=utf8mb4;

create table if not exists `file_content` (
	`id` bigint unsigned not null auto_increment comment '文本消息ID',
    `name` varchar(255) not null comment '文件名',
    `size` varchar(12) not null comment '文件大小（带单位）',
    `path` varchar(255) not null comment '文件存储路径',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key `pk_id` (`id`)
) engine=InnoDB auto_increment=1001 default charset=utf8mb4;

create table if not exists `image_content` (
	`id` bigint unsigned not null auto_increment comment '图片消息ID',
    `path` varchar(255) not null comment '图片存储路径',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key `pk_id` (`id`)
) engine=InnoDB auto_increment=1001 default charset=utf8mb4;

create table if not exists `remark` (
	`id` bigint unsigned not null auto_increment comment '备注ID',
	`from_id` bigint unsigned not null comment '备注方用户ID',
    `to_id` bigint unsigned not null comment '被备注方用户ID',
    `type` tinyint unsigned not null comment '备注类型，好友(0)/群友(1) 备注',
    `remark` varchar(16) not null comment '备注内容',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key `pk_id` (`id`),
    constraint `fk_remark_from_id` foreign key (`from_id`) references `user` (`id`) on delete cascade
) engine=InnoDB auto_increment=501 default charset=utf8mb4 comment="备注表";

set foreign_key_checks = 1;
