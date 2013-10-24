DROP PROCEDURE IF EXISTS sp_insertNewEmployee ;

DELIMITER $$
CREATE PROCEDURE sp_insertNewEmployee(IN userName VARCHAR(100),IN firstName VARCHAR(100),IN lastName VARCHAR(100),IN email VARCHAR(100),IN department VARCHAR(100),IN password VARCHAR(100))
BEGIN
     
 insert into tbl_all_internal_users values (userName,firstName ,lastName,email,department,password ,substring_index(user(),'@',1), curdate());
END$$
DELIMITER ;