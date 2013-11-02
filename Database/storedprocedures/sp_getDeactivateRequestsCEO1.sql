/*******************************************
Name          : sp_getDeactivateRequestsCEO1

Description   : Get the deactivate requests for ceo

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/26/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getDeactivateRequestsCEO1;

DELIMITER $$
CREATE PROCEDURE sp_getDeactivateRequestsCEO1()
BEGIN

	SELECT m.username, m.firstname, m.lastname, m.emailid, m.department, r.roles, m.createdby, m.createddate
	from tbl_all_users m, tbl_corporate_deactivate_requests c, tbl_user_role r
	WHERE c.ceo1 = 0 and
	c.username = m.username and
	c.username = r.username;
	
	
	
END$$
DELIMITER ;
