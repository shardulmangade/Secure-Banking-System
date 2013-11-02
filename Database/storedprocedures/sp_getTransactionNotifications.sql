DROP PROCEDURE IF EXISTS sp_getTransactionNotifications ;

DELIMITER $$
CREATE PROCEDURE sp_getTransactionNotifications(IN euser varchar(100))
BEGIN	

select tbl_pending_transaction_permissions.permissionrequiredby from tbl_pending_transaction_permissions
where
tbl_pending_transaction_permissions.username = euser
and 
tbl_pending_transaction_permissions.accessgranted = 'no' ;

END$$
DELIMITER ;
