DROP PROCEDURE IF EXISTS sp_getTransactionsOfPermittedUsers ;

DELIMITER $$
CREATE PROCEDURE sp_getTransactionsOfPermittedUsers(IN internaluser varchar(100))
BEGIN	
select 	a.fromusername, a.tousername, 
		a.amount, a.createddate 
from tbl_customer_transfer_transaction a,  tbl_pending_transaction_permissions b
where a.createdby = b.username and b.permissionrequiredby=internaluser and b.accessgranted = 'yes' ;

END$$
DELIMITER ;
