DROP PROCEDURE IF EXISTS sp_getCustomerTransaction;

DELIMITER $$
CREATE PROCEDURE sp_getCustomerTransaction(IN inuserName VARCHAR(100) )
BEGIN

select * from tbl_customer_transfer_transaction where tbl_customer_transfer_transaction.fromusername = inuserName or tbl_customer_transfer_transaction.tousername = inuserName;
END$$
DELIMITER ;