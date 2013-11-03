DROP PROCEDURE IF EXISTS sp_getAccountNumberForCustomer;

DELIMITER $$
CREATE PROCEDURE sp_getAccountNumberForCustomer(IN inuserName VARCHAR(100))
BEGIN

select accountno from tbl_customer_account_no where username = inuserName;

END$$
DELIMITER ;