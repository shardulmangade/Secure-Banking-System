create table tbl_all_users
(
username varchar(100) not null primary key,
password varchar(100) not null,
otp varchar(100) DEFAULT NULL,
otpvalidity timestamp NULL, 
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
values("admin","21232f297a57a5a743894a0e4a801fc3", null, null, "ROLE_VALID_USER","dexter","morgan","ramkumar007@gmail.com","",substring_index(user(),'@',1), curdate());

#password is euser
insert into tbl_all_users 
values("euser","3960edfad5d748670763a3ded95af414", null, null, "ROLE_VALID_USER","dexter","morgan","ramkumar007@gmail.com","",substring_index(user(),'@',1), curdate());

#password is sysad
insert into tbl_all_users 
values("sysad","a3fe41c36274fa31157d64bd152c8eeb", null, null, "ROLE_VALID_USER","Balin","Fundin","shardul27@gmail.com","",substring_index(user(),'@',1), curdate());
