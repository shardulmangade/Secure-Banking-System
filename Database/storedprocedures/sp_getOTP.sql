/*******************************************
Name          : sp_getOTP

Description   : Used to get OTP

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/24/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getOTP;

DELIMITER $$
CREATE PROCEDURE sp_getOTP(
IN inusername VARCHAR(100),
OUT errorMessage      VARCHAR(100)
)
BEGIN
     
	 IF EXISTS(SELECT 1 FROM tbl_all_users WHERE username = inusername)
	 
		THEN 
			
			SELECT otp, otpvalidity from tbl_all_users where username = inusername;
	
	ELSE 
			SET errorMessage = "No such user exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;

