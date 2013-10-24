create table tbl_delete_request_for_corporatemgmt
(
username varchar(100) not null primary key,
department varchar(100) not null,
approvedelete boolean not null,
createdby varchar(100) not null,
createddate timestamp not null,

)
