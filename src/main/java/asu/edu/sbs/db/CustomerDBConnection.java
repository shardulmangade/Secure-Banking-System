package asu.edu.sbs.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.User;

import com.mysql.jdbc.PreparedStatement;

@Service
public class CustomerDBConnection {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
//	private String dbCommand;
//	private Connection connection;
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	
	public List<Credit> getAllTransaction(String userName) {
		Connection connection = null;
		List<Credit> listCredits = null;
		try {
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_ALL_CUSTOMER_TRANSACTIONS + "(?)" );
		sqlstatement.setString(1,userName );		
		ResultSet rs = sqlstatement.executeQuery();
		listCredits = null; 		
		
			listCredits = new ArrayList <Credit>();
			while(rs.next())
			{
				Credit credit = new Credit();
				credit.setFromaccount(rs.getString("fromaccount"));
				credit.setToacccount(rs.getString("toaccount"));
				credit.setFromCustomer(rs.getString("fromusername"));
				credit.setToCustomer(rs.getString("tousername"));
				credit.setAmount(rs.getDouble("amount"));
				listCredits.add(credit);
			}
			
	} catch (SQLException e) {
		// TODO Use our application specific custom exception
		e.printStackTrace();
	} finally{
		if (connection!=null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	return listCredits;
 }
	
	
	public int insertNewTransaction(Credit credit) throws Exception
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_CUSTOMER_NEW_TRANSACTIONS + "(?,?,?,?,?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,credit.getFromCustomer());
		sqlstatement.setString(2,credit.getToCustomer());
		sqlstatement.setString(3,credit.getFromaccount() );
		sqlstatement.setString(4,credit.getToacccount() );
		sqlstatement.setDouble(5, credit.getAmount());
		//saving the encrypted request
		sqlstatement.setBytes(6, credit.getSignedRequest());
		sqlstatement.execute();
		int updateCount = sqlstatement.getUpdateCount();
		if (connection!=null)
			connection.close();
		return (updateCount);
		
	}

	public double getbalanceofCustomer(String username) throws Exception
	{
		Connection connection = dataSource.getConnection();
		double balance=0.0;
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_BALANCE_OF_CUSTOMER + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,username);
		ResultSet rs = sqlstatement.executeQuery();
		if (rs.next())
			balance = rs.getDouble("balance");
		if (connection!=null)
			connection.close();
		return (balance);
	}
	
	
	public int updatebalanceofCustomer(String username,double balance) throws Exception
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.UPDATE_BALANCE_OF_CUSTOMER + "(?,?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,username);
		sqlstatement.setDouble(2,balance);
		sqlstatement.execute();
		int updateCount=sqlstatement.getUpdateCount();
		if (connection!=null)
			connection.close();
		return (updateCount);
	}
	
	
	public boolean validateRecepientUser(String userName,String account) throws Exception 
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.VALIDATE_RECIPIENT_USER + "(?,?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,userName);
		sqlstatement.setString(2,account);
		ResultSet rs =sqlstatement.executeQuery();
		
		boolean flag = rs.next();
		if (connection!=null)
			connection.close();
		return flag;
		
	}
	
	public String getAccountNumberForCustomer(String userName) throws Exception
	{
				
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_ACCOUNT_NUMBER_CUSTOMER + "(?)" );		
		sqlstatement.setString(1,userName);
		ResultSet rs =sqlstatement.executeQuery();
		rs.next();
		String accountNumber = rs.getString("accountno");
		if (connection!=null)
			connection.close();		
		return (accountNumber);
	}

	/**
	 * Checks if the merchant is merchant and if he/she is present in the database
	 * 
	 */
	public boolean validateMerchant(String userName) throws Exception {
		
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.VALIDATE_RECIPIENT_MERCHANT + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,userName);
		ResultSet rs =sqlstatement.executeQuery();
		
		boolean flag = rs.next();
		if (connection!=null)
			connection.close();
		return flag;
	}

	/**
	 * Database handler for getting the account number for merchant
	 * @param name
	 * @return
	 */
	public String getAccountNumberForMerchant(String name) throws Exception {
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_ACCOUNT_NUMBER_CUSTOMER + "(?)" );		
		sqlstatement.setString(1,name);
		ResultSet rs =sqlstatement.executeQuery();
		rs.next();
		String accountNumber = rs.getString("accountno");
		if (connection!=null)
			connection.close();		
		return accountNumber;
	}
}

		

