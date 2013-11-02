/*******************************************
Name          : sp_getItPendingUserRequest

Description   : Used to get all the pending user requests

Called By     : UI (ItDBConnectionManager.java)

Create By     : Shardul Mangade

Modified Date : 10/27/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getItPendingUserRequest;

DELIMITER $$
CREATE PROCEDURE sp_getItPendingUserRequest(IN userName VARCHAR(100))
BEGIN
     
 SELECT username, department, firstname, lastname, emailid
	FROM tbl_it_pending_user_requests WHERE tbl_it_pending_user_requests.username=userName;  

END$$
DELIMITER ;