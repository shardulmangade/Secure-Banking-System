DROP PROCEDURE IF EXISTS sp_DeleteEmployeeCorporate ;

DELIMITER $$
CREATE PROCEDURE sp_DeleteEmployeeCorporate(IN inuserName VARCHAR(100))
BEGIN
     
 delete from tbl_all_users where tbl_all_users.userName = inuserName;
 delete from tbl_delete_request_for_corporatemgmt where tbl_delete_request_for_corporatemgmt.userName = inuserName;
 
END$$
DELIMITER ;