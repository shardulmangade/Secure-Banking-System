/*******************************************
Name          : sp_getAllSalesUserRequests

Description   : Used to get all the open user requests

Called By     : UI (SalesDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/13/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getAllSalesUserRequests;

DELIMITER $$
CREATE PROCEDURE sp_getAllSalesUserRequests()
BEGIN
     
 SELECT username, accounttype, firstname, lastname, emailid, dateofBirth
	FROM tbl_sales_user_requests;  

END$$
DELIMITER ;

call sp_getAllSalesUserRequests();