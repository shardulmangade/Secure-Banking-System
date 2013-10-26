DROP PROCEDURE IF EXISTS sp_addNewEmployeeCorporate;

DELIMITER $$
CREATE PROCEDURE sp_addNewEmployeeCorporate(IN userName VARCHAR(100),IN firstName VARCHAR(100),IN lastName VARCHAR(100),IN email VARCHAR(100),IN department VARCHAR(100),IN password VARCHAR(100))
BEGIN
 insert into tbl_all_users 
 values(userName,password, null, null, "ROLE_VALID_USER",firstName,lastName,email,department,substring_index(user(),'@',1), curdate());  
END$$
DELIMITER ;