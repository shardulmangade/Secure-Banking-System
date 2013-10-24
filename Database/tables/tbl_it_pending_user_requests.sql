create table tbl_it_pending_user_requests
(
username varchar(100) not null primary key,
firstname varchar(100) not null,
lastname varchar(100) not null,
emailid varchar(100) not null,
accounttype varchar(100) not null,
createdby varchar(100) not null,
createddate timestamp not null
)
