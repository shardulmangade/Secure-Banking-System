DROP PROCEDURE IF EXISTS sp_insertValidCustomer;

DELIMITER $$
CREATE PROCEDURE sp_insertValidCustomer(
in inusername varchar(100),
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

			IF NOT EXISTS(SELECT 1 FROM tbl_it_pending_user_requests WHERE username = inusername)
			
				THEN 
			  
					insert into tbl_it_pending_user_requests values
					(inusername, infirstname, inlastname, inemailid, indepartment, inssn, increatedby, curtime());
			ELSE

				SET errorMessage = "A valid user already exists. This action has been logged. Please don't try to hack into the system !!!";

			END IF;
	
	ELSE 
			SET errorMessage = "A valid user already exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;
		
END$$
DELIMITER ;


