DROP PROCEDURE IF EXISTS sp_deleteEmployee ;

DELIMITER $$
CREATE PROCEDURE sp_deleteEmployee(IN userName VARCHAR(100))
BEGIN
     
 delete from tbl_all_internal_users where tbl_all_internal_users.userName = userName;
 
END$$
DELIMITER ;