package asu.edu.sbs.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
}
