DROP PROCEDURE IF EXISTS sp_deletePendingTransactionMerchant ;

DELIMITER $$
CREATE PROCEDURE sp_deletePendingTransactionMerchant(IN transactionID VARCHAR(100))
BEGIN
     
 delete from tbl_merchant_pending_transactions where tbl_merchant_pending_transactions.transactionID = transactionID;
 
END$$
DELIMITER ;