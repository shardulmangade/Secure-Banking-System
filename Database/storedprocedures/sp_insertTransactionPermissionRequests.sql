DROP PROCEDURE IF EXISTS sp_insertTransactionPermissionRequests ;

DELIMITER $$
CREATE PROCEDURE sp_insertTransactionPermissionRequests(IN inusername VARCHAR(100), IN permissionRequestedBy VARCHAR(100))
BEGIN
     
insert into tbl_pending_transaction_permissions values (inusername, 'no', permissionRequestedBy, curdate());
END$$
DELIMITER ;