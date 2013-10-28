/*******************************************
Name          : sp_denyDeactivationOfManager

Description   : Get the deactivate requests for ceo

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/26/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_denyDeactivationOfManager;

DELIMITER $$
CREATE PROCEDURE sp_denyDeactivationOfManager(
in inusername varchar(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN
     
	 IF EXISTS(SELECT 1 FROM tbl_corporate_deactivate_requests WHERE username = inusername)
	 
		THEN 
			
			delete from tbl_corporate_deactivate_requests where username = inusername;
	
	ELSE 
			SET errorMessage = "No such manager deactivation request exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;
