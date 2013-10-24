DROP PROCEDURE IF EXISTS sp_getCustomerTransaction;

DELIMITER $$
CREATE PROCEDURE sp_getCustomerTransaction(IN userName VARCHAR(100) )
BEGIN

select * from customertransfertransaction where customertransfertransaction.fromusername = userName or customertransfertransaction.tousername = userName;
END$$
DELIMITER ;