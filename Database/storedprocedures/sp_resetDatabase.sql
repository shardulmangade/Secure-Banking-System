DROP PROCEDURE IF EXISTS sp_resetDatabase;

DELIMITER $$
CREATE PROCEDURE sp_resetDatabase()
BEGIN
     
	delete from tbl_all_users;
	delete from tbl_corporate_deactivate_requests;
	delete from tbl_customer_account_no;
	delete from tbl_customer_transfer_transaction;
	delete from tbl_delete_request_for_corporatemgmt;
	delete from tbl_it_pending_user_requests;
	delete from tbl_merchant_pending_transactions;
	delete from tbl_new_employee_request;
	delete from tbl_pending_transaction_permissions;
	delete from tbl_sales_user_requests;
	delete from tbl_user_role;

	insert into tbl_all_users 
	values("sjobs.sbs","c0f7462e710b1d039abeb881262511ca", null, null, "ROLE_VALID_USER","Steve","Jobs","sjobs.sbs@rediffmail.com","corporate",null,substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

	insert into tbl_all_users 
	values("mmayer.sbs","4476e0b60ee4eca7426b705d71f90433", null, null, "ROLE_VALID_USER","Marissa","Mayer","mmayer.sbs@rediffmail.com","corporate",null,substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

	insert into tbl_all_users 
	values("gborse.sbs","829bb1de928252991e2e5dbcf40cf2e3", null, null, "ROLE_VALID_USER","Girish","Borse","gborse.sbs@rediffmail.com","corporate",null,substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

	insert into tbl_user_role
	values("sjobs.sbs","ROLE_CORPORATE_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

	insert into tbl_user_role
	values("mmayer.sbs","ROLE_CORPORATE_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

	insert into tbl_user_role
	values("gborse.sbs","ROLE_CORPORATE_MANAGER",substring_index(user(),'@',1), curdate(),substring_index(user(),'@',1), curdate());

END$$
DELIMITER ;