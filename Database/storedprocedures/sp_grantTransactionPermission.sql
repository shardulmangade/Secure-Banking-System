

DROP PROCEDURE IF EXISTS sp_grantTransactionPermission ;

DELIMITER $$
CREATE PROCEDURE sp_grantTransactionPermission(IN iuser VARCHAR(100), IN currEuser VARCHAR(100))
BEGIN

update tbl_pending_transaction_permissions set accessgranted = 'yes' where username = currEuser AND permissionrequiredby = iuser ;

END$$
DELIMITER ;