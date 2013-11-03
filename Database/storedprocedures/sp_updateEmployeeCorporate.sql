DROP PROCEDURE IF EXISTS sp_updateEmployeeCorporate ;

DELIMITER $$
CREATE PROCEDURE sp_updateEmployeeCorporate(IN inusername VARCHAR(100),IN indepartment VARCHAR(100))
BEGIN
     
 update tbl_all_users SET tbl_all_users.department = indepartment where tbl_all_users.username = inusername;
END$$
DELIMITER ;