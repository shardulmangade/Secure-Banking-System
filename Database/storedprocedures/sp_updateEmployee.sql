DROP PROCEDURE IF EXISTS sp_updateEmployee ;

DELIMITER $$
CREATE PROCEDURE sp_updateEmployee(IN inusername VARCHAR(100),IN indepartment VARCHAR(100))
BEGIN
     
 update tbl_all_internal_users SET tbl_all_internal_users.department = indepartment where tbl_all_internal_users.username = inusername;
END$$
DELIMITER ;