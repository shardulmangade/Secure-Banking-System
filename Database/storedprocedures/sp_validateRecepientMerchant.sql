DROP PROCEDURE IF EXISTS sp_validateRecepientMerchant ;

DELIMITER $$
CREATE PROCEDURE sp_validateRecepientMerchant(IN userName VARCHAR(100))
BEGIN
     
     select * from tbl_all_users where tbl_all_users.username = userName and tbl_all_users.department = 'merchant';
END$$
DELIMITER ;