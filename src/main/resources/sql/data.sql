use chatting;

-- init user
insert into user (`username`, `password`, `avatar_path`) values ('member001', '001', 'avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member002', '002', 'avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member003', '003', 'avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member004', '004', 'avatar/default_user_avatar.jpg');
insert into user (`username`, `password`, `avatar_path`) values ('member005', '005', 'avatar/default_user_avatar.jpg');

-- init group
-- 1. create group (the group is a keyword)
insert into `group` (`group_name`, `avatar_path`, `owner`) values ('group001', 'avatar/default_group_avatar.jpg', 501);
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

-- insert text message
insert into text_content (`content`) values ('hi');
insert into text_content (`content`) values ('hello');
insert into text_content (`content`) values ("I'm rekord");
insert into text_content (`content`) values ("I'm KangKang");
insert into text_content (`content`) values ("hi, I'm member001");
insert into text_content (`content`) values ("hi, I'm member002");

insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (501, 502, 0, 0, 1001);
insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (502, 501, 0, 0, 1002);
insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (501, 502, 0, 0, 1003);
insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (502, 501, 0, 0, 1004);
insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (501, 101, 1, 0, 1005);
insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (502, 101, 1, 0, 1006);


-- insert file message
insert into file_content (`name`, `size`, `path`) values ('中文成绩单.pdf', '165.6KB', 'UploadFile/b88cbb8786604ea6bcba0be61743de5e.pdf');
insert into file_content (`name`, `size`, `path`) values ('英文成绩单.pdf', '165.6KB', 'UploadFile/b88cbb8786604ea6bcba0be61743de5e.pdf');

insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (501, 502, 0, 1, 1001);
insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (502, 501, 0, 1, 1002);
insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (501, 101, 1, 1, 1001);
insert into message (`from_id`, `to_id`, `type`, `content_type`, `content_id`) values (502, 101, 1, 1, 1002);









