DROP PROCEDURE IF EXISTS sp_validateRecepientMerchant ;

DELIMITER $$
CREATE PROCEDURE sp_validateRecepientMerchant(IN inusername VARCHAR(100))
BEGIN
     
     select * from tbl_all_users where tbl_all_users.username = inusername and tbl_all_users.department = 'merchant';
END$$
DELIMITER ;