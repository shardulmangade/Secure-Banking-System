drop table tbl_all_users;
create table tbl_all_users
(
username varchar(100) not null primary key,
password varchar(100) not null,
otp varchar(100),
otpvalidity timestamp, 
roles varchar(100) not null,
firstname varchar(100) not null,
lastname varchar(100) not null,
emailid varchar(100) not null,
department varchar(100) not null,
createdby varchar(100) not null,
createddate timestamp not null
);

delete from tbl_all_users;
#password is admin
insert into tbl_all_users 
values("admin","21232f297a57a5a743894a0e4a801fc3", "123", CURRENT_TIMESTAMP(), "ROLE_VALID_USER","dexter","morgan","ramk@asu.edu","",substring_index(user(),'@',1), curdate());

#password is euser
insert into tbl_all_users 
values("euser","3960edfad5d748670763a3ded95af414", "abc", CURRENT_TIMESTAMP(), "ROLE_VALID_USER","dexter","morgan","ramk@asu.edu","",substring_index(user(),'@',1), curdate());