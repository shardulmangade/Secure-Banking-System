/*******************************************
Name          : sp_updatePassowrd

Description   : Used to update password

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 11/01/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updatePassowrd;

DELIMITER $$
CREATE PROCEDURE sp_updatePassowrd(
IN inusername VARCHAR(100),
IN inpassword VARCHAR(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN
     
	 IF EXISTS(SELECT 1 FROM tbl_all_users WHERE username = inusername and roles = "ROLE_VALID_USER")
	 
		THEN 
			
			update tbl_all_users set password = inpassword
			where username = inusername;
	
	ELSE 
			SET errorMessage = "No such valid user exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;