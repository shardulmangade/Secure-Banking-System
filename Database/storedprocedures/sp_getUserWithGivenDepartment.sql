DROP PROCEDURE  IF EXISTS sp_getUserWithGivenDepartment;

DELIMITER $$
CREATE PROCEDURE sp_getUserWithGivenDepartment(
IN inusername VARCHAR(100),
IN indepartment VARCHAR(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN
     

	 IF EXISTS(SELECT 1 FROM tbl_all_users WHERE username = inusername and department = indepartment)
	 
		THEN 
			
			SELECT username, firstname, lastname, emailid, department, ssn, createdby, createddate from tbl_all_users where username = inusername and department = indepartment;
	
	ELSE 
			SET errorMessage = "No such user exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;

