DROP PROCEDURE IF EXISTS sp_getTransactionsOfPermittedUsers ;

DELIMITER $$
CREATE PROCEDURE sp_getTransactionsOfPermittedUsers()
BEGIN	
select 	tbl_customer_transfer_transaction.fromusername, tbl_customer_transfer_transaction.tousername, 
		tbl_customer_transfer_transaction.amount, tbl_customer_transfer_transaction.createddate 
from tbl_customer_transfer_transaction 
where tbl_customer_transfer_transaction.createdby 
IN (select username from tbl_pending_transaction_permissions) ;

END$$
DELIMITER ;
