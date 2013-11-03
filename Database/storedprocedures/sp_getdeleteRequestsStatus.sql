DROP PROCEDURE IF EXISTS sp_getdeleteRequestsStatus;

DELIMITER $$
CREATE PROCEDURE sp_getdeleteRequestsStatus(IN inuserName VARCHAR(100),IN indepartment VARCHAR(100) )
BEGIN
     
 select approvedelete from tbl_delete_request_for_corporatemgmt where tbl_delete_request_for_corporatemgmt.username = inuserName and tbl_delete_request_for_corporatemgmt.department = indepartment;
END$$
DELIMITER ;
