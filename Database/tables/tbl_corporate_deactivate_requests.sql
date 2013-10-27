create table tbl_corporate_deactivate_requests
(
username varchar(100) not null primary key,
ceo1 boolean not null default 0,
ceo2 boolean not null default 0,
ceo3 boolean not null default 0
);