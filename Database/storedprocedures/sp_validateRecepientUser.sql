DROP PROCEDURE IF EXISTS sp_validateRecepientUser ;

DELIMITER $$
CREATE PROCEDURE sp_validateRecepientUser(IN userName VARCHAR(100),IN account VARCHAR(10))
BEGIN
     
     select * from tbl_customer_account_no where tbl_customer_account_no.username = userName and tbl_customer_account_no.accountno = account;
END$$
DELIMITER ;