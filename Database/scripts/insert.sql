use sundevilbank;

insert into tbl_sales_user_requests values
('username001','External User','Rob','Stark','ramk@asu.edu',null, substring_index(user(),'@',1), curdate());
insert into tbl_sales_user_requests values
('username002','External User','Dexter','Morgan','ramk@asu.edu',null, substring_index(user(),'@',1), curdate())
insert into tbl_sales_user_requests values
('username003','External User','Sheldon','Cooper','ramk@asu.edu',curdate(), substring_index(user(),'@',1), curdate())