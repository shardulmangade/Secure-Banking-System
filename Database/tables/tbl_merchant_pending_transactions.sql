/*******************************************
Name          : tbl_merchant_pending_transactions

Description   : Used to get all the pending user requests

Created By     : Shardul Mangade

Edited By		: Apurv Patki

Notes: Shall we keep the merchant account number public viewable by customer.
        Right now assuming that the account number will be accessible and automatically entered.

********************************************/
create table tbl_merchant_pending_transactions
(
transactionID varchar(10) not null,
fromusername varchar(100) not null,
fromaccount varchar(100) not null,
publickey varchar(200) not null,
signedrequest BLOB not null,
tomerchantname varchar(100) not null,
tomerchantaccount varchar(100) not null,
amount DOUBLE not null,
createdby varchar(100) not null,
createddate timestamp not null
)