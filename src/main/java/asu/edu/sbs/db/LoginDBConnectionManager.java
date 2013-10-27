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
import asu.edu.sbs.domain.User;
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

		try {
			Connection connection = dataSource.getConnection();
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

		return otp;
	}

	public int updateOTP(String username, OneTimePassword otp) throws BankStorageException
	{
		//Insert the OTP for the user in the database
		String dbCommand, sOutErrorValue;

		try {
			Connection connection = dataSource.getConnection();
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
		return SUCCESS;
	}

	public String getRole(String username) throws BankStorageException
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

		return IBankRoles.ROLE_INVALID_USER;
	}

	public User getUser(String username) throws BankStorageException
	{
		String dbCommand;
		User user = null;

		try {
			Connection connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_USER + "(?,?)";
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

		return user;
	}



}
