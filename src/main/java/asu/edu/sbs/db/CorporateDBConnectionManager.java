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

import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;

import com.mysql.jdbc.PreparedStatement;
@Service
public class CorporateDBConnectionManager {


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
	
	public void saveNewEmployeeRequest(SignUpEmployee employee) throws Exception
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_NEW_USER_CORPORATE + "(?,?,?,?,?,?)" );
		sqlstatement.setString(1,employee.getUserName() );
		sqlstatement.setString(2,employee.getFirstName() );
		sqlstatement.setString(3,employee.getLastName() );
		sqlstatement.setString(4,employee.getEmailId() );
		sqlstatement.setString(5,employee.getDepartment());
		sqlstatement.setString(6,employee.getPassword());
		sqlstatement.execute();									
	}
	
	public void deleteEmployeeRequest(String UserName) throws Exception
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_AUTHORIZATION_REQUEST + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,UserName );		
		sqlstatement.execute();		
	}	
	
	public void updateDepartmentOfEmployee(String UserName,String department) throws Exception 
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.UPDATE_EMPLOYEE_CORPORATE + "(?,?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.setString(2,department );
		sqlstatement.execute();
	}

	public List<SignUpEmployee> getAllPendingUserRequests() {
		String dbCommand;
		List<SignUpEmployee> listUsers = null;

		try {
			Connection connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.ALL_PENDING_REQUEST_CORPORATE + "()";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.execute();


			ResultSet result =  sqlStatement.getResultSet();
			if(result.isBeforeFirst())
			{
				listUsers = new ArrayList<SignUpEmployee>();
				SignUpEmployee user = null;
				while(result.next())
				{
					user = new SignUpEmployee();
					user.setUserName(result.getString(1));
					user.setDepartment(result.getString(2));
					//user.setDateOfBirth(result.getString(6));
					listUsers.add(user);
				}
			}	
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			e.printStackTrace();
		}
		
		return listUsers;
	}

	public void deleteEmployee(String username) throws Exception {
		
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_USER_CORPORATE + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,username );		
		sqlstatement.execute();	
		
	}
	
}
