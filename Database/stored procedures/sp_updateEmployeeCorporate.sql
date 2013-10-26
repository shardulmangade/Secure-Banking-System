DROP PROCEDURE IF EXISTS sp_updateEmployeeCorporate ;

DELIMITER $$
CREATE PROCEDURE sp_updateEmployeeCorporate(IN userName VARCHAR(100),IN department VARCHAR(100))
BEGIN
     
 update tbl_all_users SET tbl_all_users.department = department where tbl_all_users.username = userName;
END$$
DELIMITER ;