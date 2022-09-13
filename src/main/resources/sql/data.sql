use chatting;

-- init user
insert into user (`username`, `password`, `avatar_path`) values ('member001', '001', '/avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member002', '002', '/avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member003', '003', '/avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member004', '004', '/avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member005', '005', '/avatar/default_user_avatar.jpg');

-- init group
-- 1. create group (the group is a keyword)
insert into `group` (`group_name`, `avatar_path`, `owner`) values ('group001', '/avatar/default_group_avatar.jpg', 501);
-- 2. add user to group
insert into relation (`request_id`, `accept_id`, `type`) values (501, 101, 1);
insert into relation (`request_id`, `accept_id`, `type`) values (502, 101, 1);
insert into relation (`request_id`, `accept_id`, `type`) values (503, 101, 1);
insert into relation (`request_id`, `accept_id`, `type`) values (504, 101, 1);
insert into relation (`request_id`, `accept_id`, `type`) values (505, 101, 1);

-- build user relation
insert into relation (`request_id`, `accept_id`, `type`) values (501, 502, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (501, 503, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (501, 504, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (501, 505, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (502, 503, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (502, 504, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (502, 505, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (503, 504, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (503, 505, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (504, 505, 0);








