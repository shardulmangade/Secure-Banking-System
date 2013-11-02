/*******************************************
Name          : sp_getAllItPendingUserRequests

Description   : Used to get all the pending user requests

Called By     : UI (ItDBConnectionManager.java)

Create By     : Shardul Mangade

Modified Date : 10/18/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getAllItPendingUserRequests;

DELIMITER $$
CREATE PROCEDURE sp_getAllItPendingUserRequests()
BEGIN
     
 SELECT username, department, firstname, lastname, emailid, ssn
	FROM tbl_it_pending_user_requests;  

END$$
DELIMITER ;