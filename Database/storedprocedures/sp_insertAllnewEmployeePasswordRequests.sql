DROP PROCEDURE IF EXISTS sp_insertAllnewEmployeePasswordRequests ;

DELIMITER $$
CREATE PROCEDURE sp_insertAllnewEmployeePasswordRequests(IN userName VARCHAR(100),IN firstName VARCHAR(100),IN lastName VARCHAR(100),IN email VARCHAR(100),IN department VARCHAR(100), IN passwordField VARCHAR(50))
BEGIN
     
 insert into tbl_all_internal_users values (userName,firstName ,lastName,email,department, passwordField, substring_index(user(),'@',1), curdate());
END$$
DELIMITER ;
