DROP PROCEDURE IF EXISTS sp_getbalanceofCustomer;

DELIMITER $$
CREATE PROCEDURE sp_getbalanceofCustomer(IN inuserName VARCHAR(100))
BEGIN

select balance from tbl_customer_account_no where tbl_customer_account_no.username = inuserName ;
END$$
DELIMITER ;