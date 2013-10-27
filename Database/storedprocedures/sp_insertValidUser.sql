/*******************************************
Name          : sp_insertValidUser

Description   : Used to insert a valid user

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/24/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_insertValidUser;

DELIMITER $$
CREATE PROCEDURE sp_insertValidUser(
in inusername varchar(100),
in inpassword varchar(100),
in inroles varchar(100),
in infirstname varchar(100),
in inlastname varchar(100),
in inemailid varchar(100),
in indepartment varchar(100),
in inssn varchar(10),
in increatedby varchar(100),
OUT errorMessage  varchar(200)
)
BEGIN
     
	 IF NOT EXISTS(SELECT 1 FROM tbl_all_users WHERE username = inusername)
	 
		THEN 

			IF NOT EXISTS(SELECT 1 FROM tbl_user_role WHERE username = inusername)
			
				THEN 
					
					insert into tbl_all_users values
					(inusername, inpassword, null, null, 'ROLE_VALID_USER', infirstname, inlastname, inemailid, indepartment, inssn, increatedby, curtime(), increatedby, curtime());

					insert into tbl_user_role values
					(inusername, inroles, increatedby, curtime(), increatedby, curtime());

			ELSE

				SET errorMessage = "A valid user already exists. This action has been logged. Please don't try to hack into the system !!!";

			END IF;
	
	ELSE 
			SET errorMessage = "A valid user already exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;


