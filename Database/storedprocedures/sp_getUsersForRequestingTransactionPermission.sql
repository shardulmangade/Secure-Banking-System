DROP PROCEDURE IF EXISTS sp_getUsersForRequestingTransactionPermission ;

DELIMITER $$
CREATE PROCEDURE sp_getUsersForRequestingTransactionPermission( )
BEGIN
(select username from tbl_all_users 
where 
(	(tbl_all_users.department = 'customer' or tbl_all_users.department = 'merchant')
	AND
	tbl_all_users.roles = 'ROLE_VALID_USER'
	and
	tbl_all_users.username not in (select username from tbl_pending_transaction_permissions where accessgranted='yes')
)) ;
END$$
DELIMITER ;
