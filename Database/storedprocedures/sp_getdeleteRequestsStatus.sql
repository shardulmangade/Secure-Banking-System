DROP PROCEDURE IF EXISTS sp_getdeleteRequestsStatus;

DELIMITER $$
CREATE PROCEDURE sp_getdeleteRequestsStatus(IN userName VARCHAR(100),IN department VARCHAR(100) )
BEGIN
     
 select approvedelete from tbl_delete_request_for_corporatemgmt where tbl_delete_request_for_corporatemgmt.username = userName and tbl_delete_request_for_corporatemgmt.department = department;
END$$
DELIMITER ;
