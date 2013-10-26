DROP PROCEDURE IF EXISTS sp_insertCustomerTransactions ;

DELIMITER $$
CREATE PROCEDURE sp_insertCustomerTransactions(IN fromuName VARCHAR(100),IN touName VARCHAR(100),IN fromAccount VARCHAR(10),IN toAccount VARCHAR(10),IN amount double )
BEGIN
     
 insert into tbl_customer_transfer_transaction values (fromuName,touName,fromAccount,toAccount,amount,substring_index(user(),'@',1), curdate());
END$$
DELIMITER ;
