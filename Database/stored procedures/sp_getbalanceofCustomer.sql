DROP PROCEDURE IF EXISTS sp_getbalanceofCustomer;

DELIMITER $$
CREATE PROCEDURE sp_getbalanceofCustomer(IN userName VARCHAR(100))
BEGIN

select balance from tbl_customer_account_no where tbl_customer_account_no.username = userName ;
END$$
DELIMITER ;