create table tbl_pending_transaction_permissions
(
username varchar(100) not null primary key,
accessgranted varchar(3),
permissionrequiredby varchar(100) not null,
permissionrequireddate timestamp not null
);



