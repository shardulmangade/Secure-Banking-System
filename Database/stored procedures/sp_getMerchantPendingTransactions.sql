/*******************************************
Name          : sp_getMerchantPendingTransactions

Description   : Used to get all the pending merchant transaction requests by the merchant

Called By     : Merchant (MerchantDBConnectionManager.java)

Create By     : Shardul Mangade

Modified Date : 10/25/2013

Note: Should we use merchantName or merchantAccount to retrieve?!

********************************************/
DROP PROCEDURE IF EXISTS sp_getMerchantPendingTransactions;

DELIMITER $$
CREATE PROCEDURE sp_getMerchantPendingTransactions(IN merchantName VARCHAR(100))
BEGIN

 SELECT fromusername, fromaccount, certificate, amount
	FROM tbl_merchant_pending_transactions WHERE tbl_merchant_pending_transactions.tomerchantname = merchantName;  

END$$