DROP PROCEDURE IF EXISTS sp_getTransactionsForManager ;

DELIMITER $$
CREATE PROCEDURE sp_getTransactionsForManager()
BEGIN	
select 	tbl_customer_transfer_transaction.fromusername, tbl_customer_transfer_transaction.tousername,
		tbl_customer_transfer_transaction.amount, tbl_customer_transfer_transaction.createddate,  tbl_customer_transfer_transaction.encryptedrequest
from tbl_customer_transfer_transaction ;
END$$
DELIMITER ;