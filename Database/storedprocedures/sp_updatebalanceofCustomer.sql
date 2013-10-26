DROP PROCEDURE IF EXISTS sp_updatebalanceofCustomer;

DELIMITER $$
CREATE PROCEDURE sp_updatebalanceofCustomer(IN userName VARCHAR(100), IN balance double )
BEGIN

	update tbl_customer_account_no set tbl_customer_account_no.balance = balance where tbl_customer_account_no.username = userName;
	
END$$
DELIMITER ;