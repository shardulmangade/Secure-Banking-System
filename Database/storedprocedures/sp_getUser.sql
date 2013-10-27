/*******************************************
Name          : sp_getUser

Description   : Used to get User details

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/26/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getUser;

DELIMITER $$
CREATE PROCEDURE sp_getUser(
IN inusername VARCHAR(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN
     

	 IF EXISTS(SELECT 1 FROM tbl_all_users WHERE username = inusername)
	 
		THEN 
			
			SELECT username, firstname, lastname, emailid, department, ssn, createdby, createddate from tbl_all_users where username = inusername;
	
	ELSE 
			SET errorMessage = "No such user exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;

