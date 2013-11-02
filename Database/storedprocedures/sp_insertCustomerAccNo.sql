/******************************************
Create By     : Shardul Mangade

Modified Date : 10/27/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_insertCustomerAccNo;

DELIMITER $$
CREATE PROCEDURE sp_insertCustomerAccNo(
IN userName VARCHAR(100),
IN accountno varchar(10),
IN balance DOUBLE,
IN createdby VARCHAR(100),
OUT errorMessage      VARCHAR(50)
)
BEGIN
     
 -- check if the workspace exists
/*	 IF EXISTS(SELECT 1 FROM tbl_customer_account_no WHERE username = userName)
		THEN 
			SET errorMessage = "No such user exists. This action has been logged. Please don't try to hack into the system !!!";		
		 
		ELSE 
*/
						insert into tbl_customer_account_no values (userName,accountno,balance,createdby,curdate());

/*	END IF;*/

END$$
DELIMITER ;