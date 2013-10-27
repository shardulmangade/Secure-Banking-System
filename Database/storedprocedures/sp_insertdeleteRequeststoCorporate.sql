DROP PROCEDURE IF EXISTS sp_insertdeleteRequeststoCorporate ;

DELIMITER $$
CREATE PROCEDURE sp_insertdeleteRequeststoCorporate(IN userName VARCHAR(100),IN department VARCHAR(100),IN deleteapprove boolean )
BEGIN
     
 insert into tbl_delete_request_for_corporatemgmt values (userName,department,deleteapprove ,substring_index(user(),'@',1), curdate());
END$$
DELIMITER ;
