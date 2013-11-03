create table tbl_it_pending_user_requests
(
username varchar(100) not null primary key,
firstname varchar(100) not null,
lastname varchar(100) not null,
emailid varchar(100) not null,
department varchar(100) not null,
ssn varchar(10) null,
createdby varchar(100) not null,
createddate timestamp not null
)

insert into tbl_it_pending_user_requests 
values("anu","Ashwin", "krishna","n.ashwinkrishna@gmail.com","customer","1234563212",substring_index(user(),'@',1), curdate());

insert into tbl_it_pending_user_requests 
values("apu","ash", "ram","n.ashwinkrishna@gmail.com","customer","1234563212",substring_index(user(),'@',1), curdate());

insert into tbl_it_pending_user_requests 
values("alu","kit", "merchantlast","n.ashwinkrishna@gmail.com","merchant","1111111111",substring_index(user(),'@',1), curdate());
