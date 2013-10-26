DROP PROCEDURE IF EXISTS sp_updateEmployee ;

DELIMITER $$
CREATE PROCEDURE sp_updateEmployee(IN userName VARCHAR(100),IN department VARCHAR(100))
BEGIN
     
 update tbl_all_internal_users SET tbl_all_internal_users.department = department where tbl_all_internal_users.username = userName;
END$$
DELIMITER ;