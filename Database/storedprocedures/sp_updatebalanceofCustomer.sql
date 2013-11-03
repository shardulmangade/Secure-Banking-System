DROP PROCEDURE IF EXISTS sp_updatebalanceofCustomer;

DELIMITER $$
CREATE PROCEDURE sp_updatebalanceofCustomer(IN inusername VARCHAR(100), IN inbalance double )
BEGIN

	update tbl_customer_account_no set tbl_customer_account_no.balance = inbalance where tbl_customer_account_no.username = inusername;
	
END$$
DELIMITER ;