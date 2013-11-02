/*******************************************
Name          : sp_getUserLoginRole

Description   : Used to get user role

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/22/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getUserLoginRole;

DELIMITER $$
CREATE PROCEDURE sp_getUserLoginRole(
IN inusername VARCHAR(100)
)
BEGIN
			
			SELECT roles from tbl_all_users where username = inusername;

END$$
DELIMITER ;