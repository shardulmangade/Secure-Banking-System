DROP PROCEDURE IF EXISTS sp_deleteItPendingRequests ;

DELIMITER $$
CREATE PROCEDURE sp_deleteItPendingRequests(IN userName VARCHAR(100))
BEGIN
     
 delete from tbl_it_pending_user_requests where tbl_it_pending_user_requests.userName = userName;
 
END$$
DELIMITER ;