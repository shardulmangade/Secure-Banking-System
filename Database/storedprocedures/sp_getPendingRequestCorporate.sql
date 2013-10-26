
DROP PROCEDURE IF EXISTS sp_getPendingRequestCorporate;

DELIMITER $$
CREATE PROCEDURE sp_getPendingRequestCorporate()
BEGIN
     
 SELECT username, department FROM tbl_delete_request_for_corporatemgmt;  

END$$
DELIMITER ;