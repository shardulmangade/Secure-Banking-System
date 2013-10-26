/*******************************************
Name          : sp_getAllTransactions

Description   : Used to get all the open user requests

Called By     : UI (transactionManager.java)

Create By     : moheetB

Modified Date : 10/13/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getAllTransactions;

DELIMITER $$
CREATE PROCEDURE sp_getAllTransactions()
BEGIN
     
 SELECT * FROM sundevilbank.transactions;  

END$$
DELIMITER ;

call sp_getAllTransactions();