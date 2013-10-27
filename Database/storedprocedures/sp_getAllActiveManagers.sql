/*******************************************
Name          : sp_getAllActiveManagers

Description   : Used to get all active managers of the bank

Called By     : UI (LoginDBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 10/26/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getAllActiveManagers;

DELIMITER $$
CREATE PROCEDURE sp_getAllActiveManagers()
BEGIN
	
SELECT m.username, m.firstname, m.lastname, m.emailid, m.department, r.roles, m.createdby, m.createddate 
from tbl_all_users m, tbl_user_role r 
where m.roles = 'ROLE_VALID_USER' and
r.roles not like '%ROLE_CORPORATE_MANAGER%' and
m.username not in (select username from tbl_corporate_deactivate_requests) and 
r.roles like '%MANAGER%'and
m.username = r.username;

	
END$$
DELIMITER ;
