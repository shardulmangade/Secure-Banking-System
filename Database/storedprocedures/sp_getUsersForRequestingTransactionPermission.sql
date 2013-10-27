DROP PROCEDURE IF EXISTS sp_getUsersForRequestingTransactionPermission ;

DELIMITER $$
CREATE PROCEDURE sp_getUsersForRequestingTransactionPermission( )
BEGIN
select username from tbl_all_users where tbl_all_users.department = 'customer' or tbl_all_users.department = 'merchant';
END$$
DELIMITER ;
