/*******************************************
Name          : tbl_sales_user_requests

Description   : Store the details of all new user requests

Called By     : sp_getAllSalesUserRequests

Create By     : Ram Kumar Kumaresan

Modified Date : 10/13/2013

********************************************/
create table tbl_sales_user_requests
(
username varchar(100) not null primary key,
accounttype varchar(100) not null,
firstname varchar(100) not null,
lastname varchar(100) not null,
emailid varchar(100) not null,
dateofBirth datetime,
createdby varchar(100) not null,
createddate timestamp not null
)