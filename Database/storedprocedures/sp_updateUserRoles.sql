/*******************************************
Name          : sp_updateUserRoles

Description   : Used to update user roles of any valid user

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/24/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateUserRoles;

DELIMITER $$
CREATE PROCEDURE sp_updateUserRoles(
IN inusername VARCHAR(100),
IN olddepartment VARCHAR(100),
IN newdepartmentname VARCHAR(100),
IN newrole VARCHAR(100),
IN inupdatedby VARCHAR(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN
     
	 IF EXISTS(SELECT 1 FROM tbl_all_users WHERE username = inusername and roles = "ROLE_VALID_USER" and department = olddepartment)
	 
		THEN 

			IF EXISTS(SELECT 1 FROM tbl_user_role WHERE username = inusername)
			
				THEN 
					
					update tbl_user_role set roles = newrole, updatedby = inupdatedby, updateddate = curtime()
					where username = inusername;

					update tbl_all_users set department = newdepartmentname where username = inusername;

			ELSE

				SET errorMessage = "No such valid user exists. This action has been logged. Please don't try to hack into the system !!!";

			END IF;
	
	ELSE 
			SET errorMessage = "No such valid user exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;

