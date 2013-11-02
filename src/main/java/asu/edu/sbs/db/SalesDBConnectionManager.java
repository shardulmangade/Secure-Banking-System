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

import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class SalesDBConnectionManager {

public final static int SUCCESS = 1;
public final static int FAILURE = 0;

private String dbCommand;
private Connection connection;
@Autowired
@Qualifier("dataSource")
private DataSource dataSource;

public DataSource getDataSource() {
	return dataSource;
}

public void setDataSource(DataSource dataSource) {
	this.dataSource = dataSource;
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


	public void deleteEmployeeRequest(String UserName) throws Exception
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_EMPLOYEE_REQUESTS + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,UserName );		
		sqlstatement.execute();		
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
	}
	
	public void addNewSalesEmployee(SignUpEmployee employee) throws Exception 
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_NEW_EMPLOYEE + "(?,?,?,?,?,?)" );
		sqlstatement.setString(1,employee.getUserName() );
		sqlstatement.setString(2,employee.getFirstName() );
		sqlstatement.setString(3,employee.getLastName() );
		sqlstatement.setString(4,employee.getEmailId() );
		sqlstatement.setString(5,employee.getDepartment() );
		sqlstatement.setString(6,employee.getPassword() );
		sqlstatement.execute();
	}
	
	
	public void deleteSalesEmployee(String UserName) throws Exception 
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_EMPLOYEE + "(?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.execute();
		
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
	}


	public void updateDepartmentOfEmployee(String UserName,String department) throws Exception 
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.UPDATE_EMPLOYEE + "(?,?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.setString(2,department );
		sqlstatement.execute();
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
	}
	
	
	public void insertDeleteRequesttoCM(String userName, String department, boolean deleteApprove) throws Exception 
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_DELETE_REQUESTS_TO_CORPORATEMGMT + "(?,?,?)" );
		sqlstatement.setString(1,userName );
		sqlstatement.setString(2,department );
		sqlstatement.setBoolean(3,deleteApprove );
		sqlstatement.execute();
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		
	}
	
	public int getDeleteApprovalStatus(String userName, String department) throws Exception
	{
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
	}


}



