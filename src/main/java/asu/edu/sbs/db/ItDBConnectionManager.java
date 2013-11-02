package asu.edu.sbs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.activity.InvalidActivityException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.PreparedStatement;

import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class ItDBConnectionManager {

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

	public List<User> getAllPendingUserRequests()
	{
		String dbCommand;
		List<User> listUsers = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.IT_ALL_PENDING_USER_REQUESTS;
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.execute();


			ResultSet result =  sqlStatement.getResultSet();
			if(result.isBeforeFirst())
			{
				listUsers = new ArrayList<User>();
				User user = null;
				while(result.next())
				{
					user = new User();
					user.setUsername(result.getString("username"));
					user.setDepartment(result.getString("department"));
					user.setFirstName(result.getString("firstname"));
					user.setLastName(result.getString("lastname"));
					user.setEmail(result.getString("emailid"));
					user.setSsn(result.getString("ssn"));
					//user.setDateOfBirth(result.getString(6));
					listUsers.add(user);
				}
			}	
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			//throw new BankStorageException(e);
			e.printStackTrace();
		}
		finally
		{
			if(connection != null)
			{
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return listUsers;
	}

	public int saveNewEmployeeRequest(User user, String insertedbyUsername) throws Exception
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
	
	
	public User getPendingUserRequest(String userName)
	{
		String dbCommand;
		List<User> listUsers = null;
		Connection connection = null;
		User user = null;
		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.IT_PENDING_USER_REQUEST + "(?)";
			PreparedStatement sqlStatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.IT_PENDING_USER_REQUEST + "(?)" );
			/*CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");*/
			sqlStatement.setString(1,userName );
			System.out.println(sqlStatement.toString());
			sqlStatement.execute();


			ResultSet result =  sqlStatement.getResultSet();
			System.out.println("New!!!!!!!!!!!!!1");


			if(result.next())
/*			if(result.isBeforeFirst())*/
			{
				System.out.println("User name is " + result.getString(1));
				user = new User();
				user.setUsername(result.getString("username"));
				user.setDepartment(result.getString("department"));
				user.setFirstName(result.getString("firstname"));
				user.setLastName(result.getString("lastname"));
				user.setEmail(result.getString("emailid"));
				/*user.setSsn(result.getString("ssn"));*/
				//ToDo Fetch SSN
			}	
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			//throw new BankStorageException(e);
			e.printStackTrace();
		}
		finally
		{
			if(connection != null)
			{
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return user;
	}
	
	public void deleteItPendingRequest(String UserName) throws BankStorageException
	{
		Connection connection =null;
		String dbCommand;
		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.DELETE_IT_PENDING_REQUESTS + "(?,?)" ;
			
			CallableStatement sqlstatement = connection.prepareCall("{"+dbCommand+"}");
			System.out.println("\n"+sqlstatement);
			//ToDo: Add Output Parameter
			sqlstatement.setString(1,UserName );
			sqlstatement.registerOutParameter(2,Types.VARCHAR);			
			sqlstatement.execute();
			
			String sOutErrorValue = sqlstatement.getString(2);
			if(sOutErrorValue!=null)
				throw new BankStorageException(sOutErrorValue);
			
		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
			{
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void insertCustomerAccNo(String UserName,String accountNo,Double balance,String createdBy) throws Exception
	{
		String dbCommand;
		Connection connection =null;
		connection = dataSource.getConnection();
		
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.INSERT_CUSTOMER_ACC_NO + "(?,?,?,?,?)";
		CallableStatement sqlstatement = connection.prepareCall("{"+dbCommand+"}");
		
/*		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_CUSTOMER_ACC_NO + "(?,?,?,?,?)");
*/		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,UserName );
		sqlstatement.setString(2,accountNo );
		sqlstatement.setDouble(3,balance);
		sqlstatement.setString(4,createdBy);
		sqlstatement.registerOutParameter(5,Types.VARCHAR);
		sqlstatement.execute();

		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		if(connection!=null)
			connection.close();
	}	
	
	
	public int deleteCustomerAccNo(String userName) throws BankStorageException
	{
		String dbCommand;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.DELETE_CUSTOMER_ACC_NO + "(?,?,?)";
			CallableStatement sqlstatement = connection.prepareCall("{"+dbCommand+"}");
			sqlstatement.setString(1,userName);			
			sqlstatement.registerOutParameter(2, Types.VARCHAR);
			sqlstatement.execute();
			
			String output = sqlstatement.getString(2);
			if(output == null)
				return SUCCESS;		
			else
				throw new BankStorageException(output);
			
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			throw new BankStorageException(e);
		}
		finally
		{
			if(connection != null)
			{
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	
	
	
	public void deleteItEmployee(String UserName) throws Exception 
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_EMPLOYEE + "(?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.execute();
		
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		if (connection!=null)
			connection.close();
	}


	public void updateDepartmentOfEmployee(String UserName,String department) throws Exception 
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.UPDATE_EMPLOYEE + "(?,?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.setString(2,department );
		sqlstatement.execute();
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		if (connection!=null)
			connection.close();
	}
	
	
	public void insertDeleteRequesttoCM(String userName, String department, boolean deleteApprove) throws Exception 
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_DELETE_REQUESTS_TO_CORPORATEMGMT + "(?,?,?)" );
		sqlstatement.setString(1,userName );
		sqlstatement.setString(2,department );
		sqlstatement.setBoolean(3,deleteApprove );
		sqlstatement.execute();
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		if (connection!=null)
			connection.close();
	}
	
	public int getDeleteApprovalStatus(String userName, String department) throws BankStorageException 
	{
		Connection connection = null;
		try {
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_DELETE_REQUEST_STATUS + "(?,?)" );
		sqlstatement.setString(1,userName );
		sqlstatement.setString(2,department );		
		ResultSet rs = sqlstatement.executeQuery();
		
		if(rs.next())
		{
			if(rs.getBoolean("approvedelete"))
				return 1;
			else 
				return 0;			
		}else {
			return -1;
		}
		} catch(Exception e)
		{
			try {
				throw new BankStorageException(e);
			} catch (BankStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			if (connection!=null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return -1;
	}
	

}
