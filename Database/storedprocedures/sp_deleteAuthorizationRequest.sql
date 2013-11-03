DROP PROCEDURE IF EXISTS sp_deleteAuthorizationRequest ;

DELIMITER $$
CREATE PROCEDURE sp_deleteAuthorizationRequest(IN inuserName VARCHAR(100))
BEGIN
     
 delete from tbl_delete_request_for_corporatemgmt where tbl_delete_request_for_corporatemgmt.userName = inuserName;
 
END$$
DELIMITER ;