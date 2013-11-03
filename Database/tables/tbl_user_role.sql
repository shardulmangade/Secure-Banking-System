create table tbl_user_role
(
username varchar(100) not null primary key,
roles varchar(100) not null,
updatedby VARCHAR(100) NOT NULL,
updateddate TIMESTAMP NOT NULL,
createdby varchar(100) not null,
createddate timestamp not null
);

delete from tbl_user_role;
insert into tbl_user_role
values("sjobs.sbs","ROLE_CORPORATE_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

insert into tbl_user_role
values("mmayer.sbs","ROLE_CORPORATE_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

insert into tbl_user_role
values("gborse.sbs","ROLE_CORPORATE_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());







#insert into tbl_user_role
#values("admin","ROLE_EXTERNAL_USER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

#insert into tbl_user_role
#values("euser","ROLE_EXTERNAL_USER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

#insert into tbl_user_role
#values("sysad","ROLE_EXTERNAL_USER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

#insert into tbl_user_role
#values("corporate","ROLE_CORPORATE_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());
#insert into tbl_user_role
#values("corporate2","ROLE_CORPORATE_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());
#insert into tbl_user_role
#values("corporate3","ROLE_CORPORATE_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

#insert into sundevilbank.tbl_user_role 
#values('moheetB','ROLE_TRANSACTION_EMPLOYEE','root',now(),'root',curtime());

#insert into sundevilbank.tbl_user_role 
#values('gborse','ROLE_TRANSACTION_MANAGER','root',now(),'root',curtime());

#insert into tbl_user_role
#values("ashwin","ROLE_SALES_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

#insert into tbl_user_role
#values("auser","ROLE_SALES_EMPLOYEE",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());


