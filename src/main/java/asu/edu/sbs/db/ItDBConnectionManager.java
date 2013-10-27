package asu.edu.sbs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activity.InvalidActivityException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.PreparedStatement;

import asu.edu.sbs.domain.User;

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
					user.setUsername(result.getString(1));
					user.setAccountType(result.getString(2));
					user.setFirstName(result.getString(3));
					user.setLastName(result.getString(4));
					user.setEmail(result.getString(5));
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
	
	public User getPendingUserRequest(String userName)
	{
		String dbCommand;
		List<User> listUsers = null;
		Connection connection = null;
		User user = null;
		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.IT_ALL_PENDING_USER_REQUESTS + "(?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,userName );
			sqlStatement.execute();


			ResultSet result =  sqlStatement.getResultSet();
			if(result.isBeforeFirst())
			{
				user = new User();
				user.setUsername(result.getString(1));
				user.setAccountType(result.getString(2));
				user.setFirstName(result.getString(3));
				user.setLastName(result.getString(4));
				user.setEmail(result.getString(5));
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
	
	public void deleteItPendingRequest(String UserName) throws Exception
	{
		Connection connection =null;
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_IT_PENDING_REQUESTS + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,UserName );		
		sqlstatement.execute();
		if(connection!=null)
			connection.close();
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
	}

	public void deleteItEmployee(String UserName) throws Exception 
	{
		Connection connection =null;
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_EMPLOYEE + "(?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.execute();
		if(connection!=null)
			connection.close();	
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
	}

	public void updateDepartmentOfEmployee(String UserName,String department) throws Exception 
	{
		Connection connection =null;
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.UPDATE_EMPLOYEE + "(?,?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.setString(2,department );
		sqlstatement.execute();
		if(connection!=null)
			connection.close();
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
	}
	
	
	public void insertDeleteRequesttoCM(String userName, String department, boolean deleteApprove) throws Exception 
	{
		Connection connection =null;
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_DELETE_REQUESTS_TO_CORPORATEMGMT + "(?,?,?)" );
		sqlstatement.setString(1,userName );
		sqlstatement.setString(2,department );
		sqlstatement.setBoolean(3,deleteApprove );
		sqlstatement.execute();
		if(connection!=null)
			connection.close();
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		
	}
	
	public int getDeleteApprovalStatus(String userName, String department) throws Exception
	{
		Connection connection =null;
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_DELETE_REQUEST_STATUS + "(?,?)" );
		sqlstatement.setString(1,userName );
		sqlstatement.setString(2,department );		
		ResultSet rs = sqlstatement.executeQuery();
		if(connection!=null)
			connection.close();
		if(rs.next())
		{
			if(rs.getBoolean("approvedelete"))
				return 1;
			else 
				return 0;			
		}else {
			return -1;
		}
	}
}
