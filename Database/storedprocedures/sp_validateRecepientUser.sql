DROP PROCEDURE IF EXISTS sp_validateRecepientUser ;

DELIMITER $$
CREATE PROCEDURE sp_validateRecepientUser(IN inusername VARCHAR(100),IN inaccount VARCHAR(10))
BEGIN
     
     select * from tbl_customer_account_no where tbl_customer_account_no.username = inusername and tbl_customer_account_no.accountno = inaccount;
END$$
DELIMITER ;