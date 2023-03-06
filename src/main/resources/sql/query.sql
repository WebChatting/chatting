use chatting;

/*
联系人模块
*/

-- 根据id查询所有好友
select request_id from relation
where type = 0 and status = 1 and accept_id = 501
union
select accept_id from relation
where type = 0 and status = 1 and request_id = 501;

-- 根据id查询所有创建的群组
select * from `group`
where owner_id = 501;

-- 根据id查询所有加入的群组
select * from `group`
where owner_id = (
	select accept_id from relation
	where type = 1 and status = 1 and request_id = 503
);



/*
搜索模块
*/

-- 根据昵称搜索好友
select * from user
where username like '%member%';

-- 根据昵称搜索群组
select * from `group`
where name like '%group%';

-- 添加好友
insert into relation (`request_id`, `accept_id`, `type`, `status`) values (501, 504, 0, 0);

-- 添加群组
insert into relation (`request_id`, `accept_id`, `type`, `status`) values (505, 501, 1, 0);



/*
验证模块
*/

-- 查询所有我发起的好友验证信息
select * from relation
where type = 0 and request_id = 501;

-- 查询所有我收到的好友验证信息
select * from relation
where type = 0 and accept_id = 501;

-- 查询所有我发起的群组验证信息
select * from relation
where type = 1 and request_id = 501;

-- 查询所有我收到的群组验证信息
select * from relation
where type = 1 and accept_id = 501;



/*
更多模块（工具栏）
*/

-- 更新用户信息
update user
set username = 'test', `password` = 'test', avatarPath = 'default'
where username = 'test1';

-- 创建群组
insert into `group` (`name`, `owner_id`, `avatar_path`) values ('group1', '502', 'avatar/default_group_avatar.jpg');