package asu.edu.sbs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.IBankRoles;
import asu.edu.sbs.domain.IDepartments;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankStorageException;
import asu.edu.sbs.login.service.OneTimePassword;

@Service
public class LoginDBConnectionManager {

	private static final Logger logger = LoggerFactory.getLogger(LoginDBConnectionManager.class);
	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public OneTimePassword getOTP(String username) throws BankStorageException
	{
		String dbCommand;
		OneTimePassword otp = null;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_OTP + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,username);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);

			sqlStatement.execute();

			String sOutErrorValue = sqlStatement.getString(2);
			if(sOutErrorValue == null)
			{
				ResultSet rs = sqlStatement.getResultSet();

				//Iterate through each row returned by the database
				while(rs.next())
				{				
					if(rs.getString(1)!=null)
					{
						otp = new OneTimePassword();
						otp.setPassword(rs.getString(1));
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						try {
							otp.setExpirationTime(format.parse(rs.getString(2).substring(0, 19)));
						} catch (ParseException e) {
							throw new BankStorageException(e);
						}
					}
				}
			}
			else
			{
				logger.error("getOTP error for user <<"+username+">> "+sOutErrorValue);
				throw new BankStorageException(sOutErrorValue);
			}
		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {

				}
		}

		return otp;
	}

	public int updateOTP(String username, OneTimePassword otp) throws BankStorageException
	{
		//Insert the OTP for the user in the database
		String dbCommand, sOutErrorValue;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_OTP + "(?,?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,username);
			sqlStatement.setString(2,otp.getPassword());
			if(otp.getExpirationTime()!=null)
				sqlStatement.setTimestamp(3, new java.sql.Timestamp(otp.getExpirationTime().getTime()));
			else
				sqlStatement.setTimestamp(3,null);	
			sqlStatement.registerOutParameter(4, Types.VARCHAR);

			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(4);

			//SQL exception has occurred			
			if(sOutErrorValue != null)
			{
				throw new BankStorageException(sOutErrorValue);
			}

		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {

				}
		}
		return SUCCESS;
	}

	public String getRole(String username) throws BankStorageException
	{
		String dbCommand;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.LOGIN_GET_USER_ROLE + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,username);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);

			sqlStatement.execute();

			String output = sqlStatement.getString(2);
			if(output == null)
			{
				ResultSet rs = sqlStatement.getResultSet();

				//Iterate through each row returned by the database
				while(rs.next())
				{	
					if(rs.getString(1) != null && !rs.getString(1).equals(""))
						return rs.getString(1);
				}}		
		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {

				}
		}

		return IBankRoles.ROLE_INVALID_USER;
	}

	/**
	 * 
	 * Return the user object with data in all the fields
	 * This method will return null if the user does not exist in the table.
	 *  
	 * @param username
	 * @return
	 * @throws BankStorageException
	 */
	public User getUser(String username) throws BankStorageException
	{
		String dbCommand;
		User user = null;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_USER_FROM_ALL_USERS_TABLE + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,username);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);

			sqlStatement.execute();
			String output = sqlStatement.getString(2);
			if(output != null)
			{
				logger.info("A request for user <<+"+username+">> returned no value from database" );
			}
			ResultSet rs = sqlStatement.getResultSet();

			//Iterate through each row returned by the database
			while(rs.next())
			{				
				user = new User();
				user.setUsername(rs.getString(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setDepartment(rs.getString(5));
				user.setSsn(rs.getString(6));
				user.setCreatedBy(rs.getString(7));
				user.setCreatedDate(rs.getString(8));
			}			
		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {

				}
		}		

		return user;
	}

	/**
	 * IMPORTANT: This is for changing user login rights. If you want to change role use updateUserRole().
	 * 
	 * @param canLogin					TRUE - if you want the user to login. FALSE - if you want to deactivate the user.
	 * @param username					The user whose login right is to be changed
	 * @param updatedbyName				The user who requests the change
	 * @throws BankStorageException
	 */
	public int updateUserLoginRights(Boolean canLogin, String username, String updatedbyName) throws BankStorageException
	{
		String dbCommand;
		Connection connection = null;
		String role = null;

		if(canLogin)
			role = IBankRoles.ROLE_VALID_USER;
		else
			role = IBankRoles.ROLE_INVALID_USER;

		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_USER_LOGIN_ROLE + "(?,?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,username);
			sqlStatement.setString(2,role);
			sqlStatement.setString(3,updatedbyName);
			sqlStatement.registerOutParameter(4, Types.VARCHAR);

			sqlStatement.execute();
			String output = sqlStatement.getString(4);
			if(output == null)
				return SUCCESS;		
			else
				throw new BankStorageException(output);
		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {

				}
		}			
	}

	/**
	 * Used to transfer a person or change his role.
	 * 
	 * @param newRole				Must be a final string from {@link IBankRoles}. NOT NULL.
	 * @param newDepartmentName		New department name to which the user is transferred. Must be a final string from {@link IDepartments}. NOT NULL.
	 * @param username
	 * @param updatedbyName
	 * @return
	 * @throws BankStorageException
	 * @throws BankAccessException 
	 */
	public int updateUserRole(String newRole,String olddepartment, String newDepartmentName, String username, String updatedbyName) throws BankStorageException, BankAccessException
	{
		if(newRole == null || newDepartmentName == null)
			throw new BankAccessException();

		String dbCommand;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_USER_ROLE + "(?,?,?,?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,username);
			sqlStatement.setString(2,olddepartment);
			sqlStatement.setString(3,newDepartmentName);
			sqlStatement.setString(4,newRole);
			sqlStatement.setString(5,updatedbyName);
			sqlStatement.registerOutParameter(6, Types.VARCHAR);

			sqlStatement.execute();
			String output = sqlStatement.getString(6);
			if(output == null)
				return SUCCESS;		
			else
				throw new BankStorageException(output);
		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {

				}
		}	
	}

	/**
	 * Insert a new user with his/her first time password.
	 * IMPORTANT: The role of the user has to be set in the User object.
	 * 
	 * @param user
	 * @param firstTimePassword
	 * @param insertedbyUsername
	 * @return
	 * @throws BankStorageException
	 * @throws BankAccessException 
	 */
	public int insertValidUser(User user, String firstTimePassword, String insertedbyUsername) throws BankStorageException, BankAccessException
	{
		String dbCommand;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.INSERT_TO_ALL_USERS_TABLE + "(?,?,?,?,?,?,?,?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,user.getUsername());
			sqlStatement.setString(2,firstTimePassword);
			sqlStatement.setString(3,user.getRole());

			if(user.getFirstName() != null)
				sqlStatement.setString(4,user.getFirstName());
			else
				sqlStatement.setString(4,"Not provided");

			if(user.getLastName() != null)
				sqlStatement.setString(5,user.getLastName());
			else
				sqlStatement.setString(5,"Not provided");

			if(user.getEmail() != null)
				sqlStatement.setString(6,user.getEmail());
			else
				sqlStatement.setString(6,"diging.momo@gmail.com");

			if(user.getDepartment() != null)
				sqlStatement.setString(7,user.getDepartment());
			else
				sqlStatement.setString(7,null);

			if(user.getSsn() != null)
				sqlStatement.setString(8,user.getSsn());
			else
				sqlStatement.setString(8,null);
			
			sqlStatement.setString(9,insertedbyUsername);
			sqlStatement.registerOutParameter(10, Types.VARCHAR);

			sqlStatement.execute();
			String output = sqlStatement.getString(10);
			if(output == null)
				return SUCCESS;		
			else
				throw new BankAccessException(output);
		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {

				}
		}	
	}

	public int insertValidCustomer(User user, String insertedbyUsername) throws BankStorageException
	{
		String dbCommand;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.SALES_ALL_CUSTOMER_REQUESTS + "(?,?,?,?,?,?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,user.getUsername());
			if(user.getFirstName() != null)
				sqlStatement.setString(2,user.getFirstName());
			else
				sqlStatement.setString(2,"Not provided");

			if(user.getLastName() != null)
				sqlStatement.setString(3,user.getLastName());
			else
				sqlStatement.setString(3,"Not provided");

			if(user.getEmail() != null)
				sqlStatement.setString(4,user.getEmail());
			else
				sqlStatement.setString(4,"diging.momo@gmail.com");

			if(user.getDepartment() != null)
				sqlStatement.setString(5,user.getDepartment());
			else
				sqlStatement.setString(5,null);

			if(user.getSsn() != null)
				sqlStatement.setString(6,user.getSsn());
			else
				sqlStatement.setString(6,null);
			
			sqlStatement.setString(7,insertedbyUsername);
			sqlStatement.registerOutParameter(8, Types.VARCHAR);

			sqlStatement.execute();
			String output = sqlStatement.getString(8);
			if(output == null)
				return SUCCESS;		
			else
				throw new BankStorageException(output);
		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {

				}
		}	
	}

	public String getRoleTobechanged(String department, String role) {
		
		System.out.println("newrole "+role);
		System.out.println("dept "+department);
		
		if (department.equals("TM"))
		{
			if(role.equals("manager"))
				return "ROLE_TRANSACTION_MANAGER";
			else if (role.equals("employee"))
				return "ROLE_TRANSACTION_EMPLOYEE";
		} else if (department.equals("HR"))
		{
			if(role.equals("manager"))
				return "ROLE_HR_MANAGER";
			else if (role.equals("employee"))
				return "ROLE_HR_EMPLOYEE";
		} else if (department.equals("IT"))
		{
			if(role.equals("manager"))
				return "ROLE_IT_MANAGER";
			else if (role.equals("employee"))
				return "ROLE_IT_EMPLOYEE";
		} else if (department.equals("CM"))
		{
			if(role.equals("manager"))
				return "ROLE_COPRPORATE_MANAGER";
			else if (role.equals("employee"))
				return "ROLE_COPRPORATE_EMPLOYEE";
		} else if (department.equals("sales"))
		{
			if(role.equals("manager"))
				return "ROLE_SALES_MANAGER";
			else if (role.equals("employee"))
				return "ROLE_SALES_EMPLOYEE";
		}
		
		return null;
	}

	

}
