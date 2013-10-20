create table tbl_all_internal_users
(
username varchar(100) not null primary key,
firstname varchar(100) not null,
lastname varchar(100) not null,
emailid varchar(100) not null,
department varchar(100) not null;
createdby varchar(100) not null,
createddate timestamp not null
)