DROP PROCEDURE IF EXISTS sp_deleteEmployeeRequests ;

DELIMITER $$
CREATE PROCEDURE sp_deleteEmployeeRequests(IN userName VARCHAR(100))
BEGIN
     
 delete from tbl_new_employee_request where tbl_new_employee_request.username = 'userName';
END$$
DELIMITER ;

call sp_deleteEmployeeRequests();