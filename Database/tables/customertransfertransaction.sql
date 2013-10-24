create table customertransfertransaction
(
fromusername varchar(100) not null,
tousername varchar(100) not null,
fromaccount varchar(100) not null,
toaccount varchar(100) not null,
createdby varchar(100) not null,
createddate timestamp not null
)