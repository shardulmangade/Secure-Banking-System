DROP PROCEDURE IF EXISTS sp_insertAllnewEmployeeRequests ;

DELIMITER $$
CREATE PROCEDURE sp_insertAllnewEmployeeRequests(IN userName VARCHAR(100),IN firstName VARCHAR(100),IN lastName VARCHAR(100),IN email VARCHAR(100),IN department VARCHAR(100))
BEGIN
     
 insert into tbl_new_employee_request values (userName,firstName ,lastName,email,department, substring_index(user(),'@',1), curdate());
END$$
DELIMITER ;