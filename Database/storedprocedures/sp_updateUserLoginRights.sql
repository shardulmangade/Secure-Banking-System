/*******************************************
Name          : sp_updateUserLoginRights

Description   : Used to update user login rights

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/24/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateUserLoginRights;

DELIMITER $$
CREATE PROCEDURE sp_updateUserLoginRights(
IN inusername VARCHAR(100),
IN newrole VARCHAR(100),
IN inupdatedby VARCHAR(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN
     
	 IF EXISTS(SELECT 1 FROM tbl_all_users WHERE username = inusername)
	 
		THEN 
			
			update tbl_all_users set roles = newrole, updatedby = inupdatedby, updateddate = curtime()
			where username = inusername;
	
	ELSE 
			SET errorMessage = "No such user exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;