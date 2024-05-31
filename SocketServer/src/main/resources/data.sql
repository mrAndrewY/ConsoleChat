create schema if not exists chats;
create table if not exists chats.users (id  bigserial primary key, name varchar (50), Password char(60));
create table if not exists chats.chatroom (id  bigserial primary key, name varchar(50), owner char (50));
create table if not exists chats.messages (id  bigserial primary key, chatroomId bigint, senderId bigint, text varchar(1000) );



select id from chats.users order by id desc limit 1;