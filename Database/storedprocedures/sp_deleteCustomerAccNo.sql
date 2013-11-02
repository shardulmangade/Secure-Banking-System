DELIMITER $$
CREATE PROCEDURE sp_deleteCustomerAccNo(
in inusername VARCHAR(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN

	IF EXISTS(SELECT 1 FROM tbl_customer_account_no where username = inusername)
	THEN
		DELETE FROM tbl_customer_account_no WHERE tbl_customer_account_no.username = inusername;
	ELSE 
		SET errorMessage = "No such Customer Account Exsists !!!";
	END IF;
	
	
END$$
DELIMITER ;
