package asu.edu.sbs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.IBankRoles;
import asu.edu.sbs.login.service.OneTimePassword;

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

	public OneTimePassword getOTP(String username)
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
						//This catch should never be executed. Application logic should make sure of that
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			e.printStackTrace();
		}

		return otp;
	}

	public int updateOTP(String username, OneTimePassword otp)
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
				System.out.println("Error occurred during OTP insertion....");
				return FAILURE;
				//TODO: Throw custom exception
			}

		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			e.printStackTrace();
			return FAILURE;
		}
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
				if(rs.getString(1) != null && !rs.getString(1).equals(""))
					return rs.getString(1);
			}		
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			e.printStackTrace();
		}

		return IBankRoles.ROLE_INVALID_USER;
	}
	
//	public OneTimePassword getOTP(String username)
//	{
//		String dbCommand;
//		OneTimePassword otp = null;
//
//		try {
//			Connection connection = dataSource.getConnection();
//			dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_OTP + "(?,?)";
//			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
//			sqlStatement.setString(1,username);
//			sqlStatement.registerOutParameter(2, Types.VARCHAR);
//
//			sqlStatement.execute();
//
//			ResultSet rs = sqlStatement.getResultSet();
//
//			//Iterate through each row returned by the database
//			while(rs.next())
//			{				
//				if(rs.getString(1)!=null)
//				{
//					otp = new OneTimePassword();
//					otp.setPassword(rs.getString(1));
//					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//					try {
//						otp.setExpirationTime(format.parse(rs.getString(2).substring(0, 19)));
//					} catch (ParseException e) {
//						//This catch should never be executed. Application logic should make sure of that
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}			
//		} catch (SQLException e) {
//			// TODO Use our application specific custom exception
//			e.printStackTrace();
//		}
//
//		return otp;
//	}



}
