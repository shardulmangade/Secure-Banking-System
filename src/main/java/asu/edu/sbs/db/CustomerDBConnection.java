package asu.edu.sbs.db;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.MerchantCredit;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankStorageException;
import asu.edu.sbs.login.service.OneTimePassword;
import asu.edu.sbs.domain.Notification;
import asu.edu.sbs.domain.Transaction;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankStorageException;


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
	
	public void grantAccess(List<String> iusers, String currentEuser) throws BankStorageException
	{
		String dbCommand = null;
		Connection connection = null;

		try{
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL+" "+DBConstants.GRANT_TRANSACTION_PERMISSION + "(?,?)";
			for(String iuser:iusers){
				try
				{
					CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
					sqlStatement.setString(1,iuser);
					sqlStatement.setString(2, currentEuser);
					sqlStatement.execute();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
	
			}
		}
		catch(SQLException e){
			throw new BankStorageException(e);
		}
		catch(Exception e){
			throw new BankStorageException(e);
		}
		finally
		{
			try {
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Notification> getNotifications(String euser)
	{
		List<Notification> notifications= null;
		String dbCommand = null;
		Connection connection = null;
		try 
		{
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL+" "+DBConstants.GET_TRANSACTION_NOTIFICATIONS + "(?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,euser);
			sqlStatement.execute();

			ResultSet result =  sqlStatement.getResultSet();

			if(result.isBeforeFirst())
			{
				notifications= new ArrayList<Notification>();
				while(result.next())
				{
					Notification notification = new Notification();
					notification.setRequestedBy(result.getString(1));
					notifications.add(notification);
				}
			}
		}
		catch(SQLException sqex)
		{
			sqex.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	return notifications;
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
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_CUSTOMER_NEW_TRANSACTIONS + "(?,?,?,?,?,?,?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,credit.getFromCustomer());
		sqlstatement.setString(2,credit.getToCustomer());
		sqlstatement.setString(3,credit.getFromaccount() );
		sqlstatement.setString(4,credit.getToacccount() );
		sqlstatement.setDouble(5, credit.getAmount());
		//saving the encrypted request
		sqlstatement.setBytes(6, credit.getPublicKey());
		sqlstatement.setString(7, credit.getSignedRequest());
		sqlstatement.execute();
		int updateCount = sqlstatement.getUpdateCount();
		if (connection!=null)
			connection.close();
		return (updateCount);
		
	}

	public double getbalanceofCustomer(String username) throws Exception
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_BALANCE_OF_CUSTOMER + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,username);
		ResultSet rs = sqlstatement.executeQuery();
		rs.next();
		double balance = rs.getDouble("balance");
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

	/**
	 * method to insert merchant trasaction into database
	 * This method is used for PKI scenario
	 * @param credit
	 * @return
	 */
	public boolean insertMerchantTransaction(MerchantCredit credit) throws Exception {
		boolean result = true;
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_MERCHANT_TRANSACTION + "(?,?,?,?,?,?,?,?)" );
		System.out.println("\n"+sqlstatement);
		
		sqlstatement.setString(1,credit.getTransactionID());
		sqlstatement.setString(2,credit.getFromusername());
		sqlstatement.setString(3,credit.getFromaccount() );
		sqlstatement.setBytes(4,credit.getPublicKey());
		sqlstatement.setString(5,credit.getSignedRequest());
		sqlstatement.setString(6,credit.getTomerchantname() );
		sqlstatement.setString(7, credit.getTomerchantaccount());
		sqlstatement.setDouble(8, credit.getAmount());
		sqlstatement.execute();
		int updateCount = sqlstatement.getUpdateCount();
		if (connection!=null)
			connection.close();
		if(updateCount == 0)
			result = false;
		return result;
	}
	
	/**
	 * This method uses merchant credit to update database
	 * @param credit
	 * @return
	 * @throws Exception
	 */
	public int insertNewTransaction(MerchantCredit credit) throws Exception
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_CUSTOMER_NEW_TRANSACTIONS + "(?,?,?,?,?,?,?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,credit.getFromusername());
		sqlstatement.setString(2,credit.getTomerchantname());
		sqlstatement.setString(3,credit.getFromaccount());
		sqlstatement.setString(4,credit.getTomerchantaccount());
		sqlstatement.setDouble(5, credit.getAmount());
		//saving the encrypted request
		sqlstatement.setBytes(6, credit.getPublicKey());
		sqlstatement.setString(7, credit.getSignedRequest());
		sqlstatement.execute();
		int updateCount = sqlstatement.getUpdateCount();
		if (connection!=null)
			connection.close();
		return (updateCount);
		
	}
	

}

		

