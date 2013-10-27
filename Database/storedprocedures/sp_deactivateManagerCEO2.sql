/*******************************************
Name          : sp_deactivateManagerCEO2

Description   : Deactivate a manager

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/26/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_deactivateManagerCEO2;

DELIMITER $$
CREATE PROCEDURE sp_deactivateManagerCEO2(
in inusername VARCHAR(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN

	 IF EXISTS(SELECT 1 FROM tbl_all_users u, tbl_user_role r  WHERE u.username = inusername and u.roles = 'ROLE_VALID_USER' and r.username = u.username and r.roles like '%MANAGER%' and r.roles not like '%ROLE_CORPORATE_MANAGER%')
	 
		THEN 

			IF EXISTS(SELECT 1 FROM tbl_corporate_deactivate_requests where username = inusername)
				
				THEN

					update tbl_corporate_deactivate_requests set ceo2 = 1 where username = inusername;

					IF EXISTS(SELECT 1 FROM tbl_corporate_deactivate_requests where username = inusername and ceo1 = 1 and ceo2 = 1 and ceo3 = 1)
					THEN
						update tbl_all_users set roles = "ROLE_INVALID_USER" where username = inusername;
						delete from tbl_corporate_deactivate_requests where username = inusername;
					END IF;

			ELSE
				
				insert into tbl_corporate_deactivate_requests values
				(inusername, 0,1,0);

			END IF;
			
	
	ELSE 
			SET errorMessage = "No such manager exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;
	
	
END$$
DELIMITER ;
