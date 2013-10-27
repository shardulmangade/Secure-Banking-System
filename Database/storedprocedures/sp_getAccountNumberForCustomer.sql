DROP PROCEDURE IF EXISTS sp_getAccountNumberForCustomer;

DELIMITER $$
CREATE PROCEDURE sp_getAccountNumberForCustomer(IN userName VARCHAR(100))
BEGIN

select accountno from tbl_customer_account_no where username = userName;

END$$
DELIMITER ;