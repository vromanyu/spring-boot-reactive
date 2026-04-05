begin;
alter table reactive_users add column user_uuid varchar(255) not null unique;
alter table reactive_users add column first_name varchar(255) not null;
alter table reactive_users add column last_name varchar(255) not null;
alter table reactive_users add column email varchar(255) not null unique;
alter table reactive_users add column password varchar(255) not null;
commit;