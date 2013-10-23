package asu.edu.sbs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.IBankRoles;

@Service
public class LoginDBConnectionManager {

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

	public String getOTP(String username)
	{
		//TODO: Fetch the OTP for the user from the Database
		return "123";
	}

	public int insertNewOTP(String username, String otp)
	{
		//TODO: Insert the OTP for the user in the database
		return SUCCESS;
	}

	public String getRole(String username)
	{
		String dbCommand;

		try {
			Connection connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.LOGIN_GET_USER_ROLE + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,username);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);

			sqlStatement.execute();

			String output = sqlStatement.getString(2);
			System.out.println("Output from database: "+output);

			ResultSet rs = sqlStatement.getResultSet();

			//Iterate through each row returned by the database
			while(rs.next())
			{	
				System.out.println("___________________"+rs.getString(1));
			}		
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			e.printStackTrace();
		}

		return IBankRoles.ROLE_EXTERNAL_USER;
	}


}
