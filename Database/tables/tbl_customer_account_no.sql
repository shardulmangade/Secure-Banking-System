drop table tbl_customer_account_no;
create table tbl_customer_account_no
(
username varchar(100) not null primary key,
accountno varchar(10) not null,
balance double not null,
createdby varchar(100) not null,
createddate timestamp not null
);