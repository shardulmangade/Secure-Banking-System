package asu.edu.sbs.db;

public interface DBConstants 
{
	public final static String SP_CALL = "call";
	public final static String ceo1 = "sjobs.sbs";
	public final static String ceo2 = "mmayer.sbs";
	public final static String ceo3 = "gborse.sbs";
	
	
	//Sales Stored Procedures
	public final static String SALES_ALL_USER_REQUESTS = "sp_getAllSalesUserRequests";
	//public final static String SALES_ALL_CUSTOMER_REQUESTS = "sp_getAllSalesCustomerRequests";
	public final static String SALES_ALL_CUSTOMER_REQUESTS = "sp_insertValidCustomer";
	
	
	//Login Stored Procedures
	public final static String LOGIN_GET_USER_ROLE = "sp_getUserRole";
	public final static String LOGIN_GET_LOGIN_USER_ROLE = "sp_getUserLoginRole";
	public final static String GET_OTP = "sp_getOTP";
	public final static String UPDATE_OTP = "sp_updateOTP";
	public final static String UPDATE_PASSWORD = "sp_updatePassowrd";
	public final static String RESET_DB  = "sp_resetDatabase";
	
	//All users Stored Procedures
	public final static String GET_USER_FROM_ALL_USERS_TABLE = "sp_getUser";
	public final static String UPDATE_USER_LOGIN_ROLE = "sp_updateUserLoginRights";
	public final static String UPDATE_USER_ROLE = "sp_updateUserRoles";
	public final static String INSERT_TO_ALL_USERS_TABLE = "sp_insertValidUser";
	
	//Corporate 
	public final static String CORPORATE_GET_ALL_ACTIVE_MANAGERS = "sp_getAllActiveManagers";
	public final static String CORPORATE_DEACTIVATE_MANAGER_BY_CEO1 = "sp_deactivateManagerCEO1";
	public final static String CORPORATE_DEACTIVATE_MANAGER_BY_CEO2 = "sp_deactivateManagerCEO2";
	public final static String CORPORATE_DEACTIVATE_MANAGER_BY_CEO3 = "sp_deactivateManagerCEO3";
	public final static String CORPORATE_GET_PENDING_MANAGER_DEACTIVATE_REQUESTS_CEO1 = "sp_getDeactivateRequestsCEO1";
	public final static String CORPORATE_GET_PENDING_MANAGER_DEACTIVATE_REQUESTS_CEO2 = "sp_getDeactivateRequestsCEO2";
	public final static String CORPORATE_GET_PENDING_MANAGER_DEACTIVATE_REQUESTS_CEO3 = "sp_getDeactivateRequestsCEO3";
	public final static String CORPORATE_DENY_DEACTIVATION_OF_MANAGER = "sp_denyDeactivationOfManager";
	
	public final static String ALL_NEW_EMPLOYEE_REQUESTS = "sp_insertAllnewEmployeeRequests";
	public final static String ALL_NEW_EMPLOYEE_PASSWORD_REQUESTS = "sp_insertAllnewEmployeePasswordRequests";
	public final static String DELETE_EMPLOYEE_REQUESTS = "sp_deleteEmployeeRequests";	
	public final static String INSERT_NEW_EMPLOYEE = "sp_insertNewEmployee";
	public final static String DELETE_EMPLOYEE = "sp_deleteEmployee";
	public final static String UPDATE_EMPLOYEE = "sp_updateEmployee";	
	public final static String INSERT_DELETE_REQUESTS_TO_CORPORATEMGMT = "sp_insertdeleteRequeststoCorporate";
	public final static String GET_DELETE_REQUEST_STATUS = "sp_getdeleteRequestsStatus";
	public final static String GET_ALL_CUSTOMER_TRANSACTIONS = "sp_getCustomerTransaction";
	public final static String INSERT_NEW_USER_CORPORATE= "sp_addNewEmployeeCorporate";
	public final static String DELETE_USER_CORPORATE = "sp_DeleteEmployeeCorporate";
	public final static String IT_ALL_PENDING_USER_REQUESTS = "sp_getAllItPendingUserRequests";
	public final static String INSERT_CUSTOMER_NEW_TRANSACTIONS = "sp_insertCustomerTransactions";
	public final static String VALIDATE_RECIPIENT_USER = "sp_validateRecepientUser";
	public final static String VALIDATE_RECIPIENT_MERCHANT = "sp_validateRecepientMerchant";
	public final static String GET_BALANCE_OF_CUSTOMER = "sp_getbalanceofCustomer";
	public final static String UPDATE_BALANCE_OF_CUSTOMER = "sp_updatebalanceofCustomer";
	public final static String DELETE_IT_PENDING_REQUESTS = "sp_deleteItPendingRequests";
	public final static String GET_ALL_MERCHANT_PENDING_TRANSACTIONS = "sp_getMerchantPendingTransactions";
	public final static String UPDATE_EMPLOYEE_CORPORATE = "sp_updateEmployeeCorporate";
	public final static String DELETE_AUTHORIZATION_REQUEST = "sp_deleteAuthorizationRequest";
	public static final String ALL_PENDING_REQUEST_CORPORATE = "sp_getPendingRequestCorporate";
	public final static String TRANSACTIONS_FOR_REG_EMPLOYEE = "sp_getAllTransactionsForRegEmp";
	public final static String TRANSACTIONS_FOR_TRANSACTION_MANAGER = "sp_getAllTransactionsForManager";
	public final static String USER_FOR_PERMISSION_TRANSACTIONS = "sp_getUsersForPermission";
	public final static String GET_ACCOUNT_NUMBER_CUSTOMER= "sp_getAccountNumberForCustomer";
	public final static String INSERT_TRANSACTION_PERMISSION_REQUESTS = "sp_insertTransactionPermissionRequests";
	public final static String GET_USERS_FOR_REQUESTING_TRANSACTION_PERMISSION = "sp_getUsersForRequestingTransactionPermission";
	public final static String GET_TRANSACTIONS_OF_PERMITTED_USERS = "sp_getTransactionsOfPermittedUsers";
	public final static String GET_TRANSACTIONS_FOR_MANAGER = "sp_getTransactionsForManager";
	public final static String GET_TRANSACTION_NOTIFICATIONS="sp_getTransactionNotifications";
	public final static String GRANT_TRANSACTION_PERMISSION = "sp_grantTransactionPermission";
	public static final String IT_PENDING_USER_REQUEST = "sp_getItPendingUserRequest";
	public static final String INSERT_CUSTOMER_ACC_NO = "sp_insertCustomerAccNo";
	public static final String DELETE_CUSTOMER_ACC_NO = "sp_deleteCustomerAccNo";
	public static final String INSERT_MERCHANT_TRANSACTION = "sp_insertMerchantTransaction";
	public static final String DELETE_PENDING_TRANSACTION_MERCHANT = "sp_deletePendingTransactionMerchant";
	public static final String TRANSFER_CORPORATE = "sp_corporateTransfer";

	
}
