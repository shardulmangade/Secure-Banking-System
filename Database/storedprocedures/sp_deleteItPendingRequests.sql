DROP PROCEDURE IF EXISTS sp_deleteItPendingRequests ;

DELIMITER $$
CREATE PROCEDURE sp_deleteItPendingRequests(IN userName VARCHAR(100),OUT errorMessage      VARCHAR(100))
BEGIN
	IF EXISTS(SELECT 1 FROM tbl_it_pending_user_requests WHERE tbl_it_pending_user_requests.userName = userName)
	 
		THEN      
 			delete from tbl_it_pending_user_requests where tbl_it_pending_user_requests.userName = userName;

 	ELSE 
			SET errorMessage = "No such user exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;
END$$
DELIMITER ;