/*******************************************
Name          : sp_getUserRole

Description   : Used to get user role

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/22/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getUserRole;

DELIMITER $$
CREATE PROCEDURE sp_getUserRole(
IN inusername VARCHAR(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN
     
 -- check if the user exists
	 IF EXISTS(SELECT 1 FROM tbl_user_role WHERE username = inusername)
	 
		THEN 
			
			SELECT roles from tbl_user_role where username = inusername;
	
	ELSE 
			SET errorMessage = "No such user exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;