DROP PROCEDURE IF EXISTS sp_insertMerchantTransaction;

DELIMITER $$
CREATE PROCEDURE sp_insertMerchantTransaction(transactionID VARCHAR(10), fromuName VARCHAR(100),IN fromAccount VARCHAR(10),IN publicKey BLOB,IN signedrequest VARCHAR(500), IN tomerchantname VARCHAR(100),IN toAccount VARCHAR(10),IN amount double )
BEGIN
     
 insert into tbl_merchant_pending_transactions values (transactionID, fromuName,fromAccount,publicKey,signedrequest,tomerchantname,toAccount,amount ,substring_index(user(),'@',1), curdate());
END$$
DELIMITER ;