package asu.edu.sbs.db;

public interface DBConstants 
{
	public final static String SP_CALL = "call";
	
	public final static String SALES_ALL_USER_REQUESTS = "sp_getAllSalesUserRequests";
	public final static String ALL_NEW_EMPLOYEE_REQUESTS = "sp_insertAllnewEmployeeRequests";
	public final static String ALL_NEW_EMPLOYEE_PASSWORD_REQUESTS = "sp_insertAllnewEmployeePasswordRequests";
	public final static String DELETE_EMPLOYEE_REQUESTS = "sp_deleteEmployeeRequests";	
	public final static String INSERT_NEW_EMPLOYEE = "sp_insertNewEmployee";
	public final static String DELETE_EMPLOYEE = "sp_deleteEmployee";
	public final static String UPDATE_EMPLOYEE = "sp_updateEmployee";	
	public final static String INSERT_DELETE_REQUESTS_TO_CORPORATEMGMT = "sp_insertdeleteRequeststoCorporate";
	public final static String GET_DELETE_REQUEST_STATUS = "sp_getdeleteRequestsStatus";
	
}
