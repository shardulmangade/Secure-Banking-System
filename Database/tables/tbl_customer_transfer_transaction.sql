create table tbl_customer_transfer_transaction
(
fromusername varchar(100) not null,
tousername varchar(100) not null,
fromaccount varchar(100) not null,
toaccount varchar(100) not null,
amount DOUBLE not null,
createdby varchar(100) not null,
createddate timestamp not null
)