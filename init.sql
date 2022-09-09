use chatting;

-- init user
insert into user (`username`, `password`, `avatar_path`) values ('member001', '001', 'static/img/avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member002', '002', 'static/img/avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member003', '003', 'static/img/avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member004', '004', 'static/img/avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member005', '005', 'static/img/avatar/default_user_avatar.jpg');

-- init group
-- 1. create group (the group is a keyword)
insert into `group` (`group_name`, `avatar_path`) values ('group001', 'static/img/avatar/default_group_avatar.jpg');
-- 2. add user to group
insert into relation (`request_id`, `accept_id`, `type`) values (501, 101, 1);
insert into relation (`request_id`, `accept_id`, `type`) values (502, 101, 1);
insert into relation (`request_id`, `accept_id`, `type`) values (503, 101, 1);
insert into relation (`request_id`, `accept_id`, `type`) values (504, 101, 1);
insert into relation (`request_id`, `accept_id`, `type`) values (505, 101, 1);

-- build user relation
insert into relation (`request_id`, `accept_id`, `type`) values (501, 102, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (501, 103, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (501, 104, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (501, 105, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (502, 103, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (502, 104, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (502, 105, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (503, 104, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (503, 105, 0);
insert into relation (`request_id`, `accept_id`, `type`) values (504, 105, 0);








