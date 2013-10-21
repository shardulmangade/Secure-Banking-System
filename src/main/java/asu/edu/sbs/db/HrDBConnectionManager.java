package asu.edu.sbs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.PreparedStatement;

import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;

@Service
public class HrDBConnectionManager {

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

public void saveNewEmployeeRequest(String UserName,String firstName,String lastName ,String emailId,String department) throws Exception
{
					
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.ALL_NEW_EMPLOYEE_REQUESTS + "(?,?,?,?,?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.setString(2,firstName );
		sqlstatement.setString(3,lastName );
		sqlstatement.setString(4,emailId );
		sqlstatement.setString(5,department );
		sqlstatement.execute();									
}
	
	public void deleteEmployeeRequest(String UserName) throws Exception
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_EMPLOYEE_REQUESTS + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,UserName );		
		sqlstatement.execute();		
	}
	
	public void addNewHrEmployee(SignUpEmployee employee) throws Exception 
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_NEW_EMPLOYEE + "(?,?,?,?,?,?)" );
		sqlstatement.setString(1,employee.getUserName() );
		sqlstatement.setString(2,employee.getFirstName() );
		sqlstatement.setString(3,employee.getLastName() );
		sqlstatement.setString(4,employee.getEmailId() );
		sqlstatement.setString(5,employee.getDepartment() );
		sqlstatement.setString(6,employee.getDepartment() );
		sqlstatement.execute();
	}
	
	
	public void deleteHrEmployee(String UserName) throws Exception 
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_EMPLOYEE + "(?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.execute();
	}


	public void updateDepartmentOfEmployee(String UserName,String department) throws Exception 
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.UPDATE_EMPLOYEE + "(?,?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.setString(2,department );
		sqlstatement.execute();
	}
}


