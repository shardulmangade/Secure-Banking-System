/*******************************************
Name          : sp_updateOTP

Description   : Used to insert OTP

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/24/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateOTP;

DELIMITER $$
CREATE PROCEDURE sp_updateOTP(
IN inusername VARCHAR(100),
IN inotp VARCHAR(100),
IN inotpvalidity timestamp,
OUT errorMessage      VARCHAR(50)
)
BEGIN
     
 -- check if the workspace exists
	 IF EXISTS(SELECT 1 FROM tbl_all_users WHERE username = inusername)
	 
		THEN 
			
			update tbl_all_users set otp = inotp, otpvalidity = inotpvalidiy 
			where username = inusername;
	
	ELSE 
			SET errorMessage = "No such user exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;