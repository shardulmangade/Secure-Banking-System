package asu.edu.sbs.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activity.InvalidActivityException;
import javax.sql.DataSource;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.MerchantCredit;
import asu.edu.sbs.domain.MerchantTransaction;
import asu.edu.sbs.exception.BankStorageException;

import com.mysql.jdbc.PreparedStatement;

@Service
public class MerchantDBConnectionManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	@Autowired
	private CustomerDBConnection customerdbconnection;
	

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<MerchantTransaction> getAllPendingTransaction(String userName) {
		List<MerchantTransaction> listTransactions = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_ALL_MERCHANT_PENDING_TRANSACTIONS + "(?)" );
			sqlstatement.setString(1,userName );		
			ResultSet rs = sqlstatement.executeQuery();
			listTransactions = null; 		
		
			listTransactions = new ArrayList <MerchantTransaction>();
			while(rs.next())
			{
				MerchantTransaction transaction = new MerchantTransaction();
				transaction.setFromaccount(rs.getString("fromaccount"));
				transaction.setToMerchantacccount(rs.getString("tomerchantaccount"));
				transaction.setFromCustomer(rs.getString("fromusername"));
				transaction.setToMerchant(rs.getString("tomerchantname"));
				transaction.setTransactionId(rs.getString("transactionid"));
				transaction.setCertificate(rs.getBlob("signedrequest"));
				transaction.setAmount(rs.getDouble("amount"));
				listTransactions.add(transaction);
			}
			
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			e.printStackTrace();
		}finally
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
		return listTransactions;
	}

	public ArrayList<MerchantCredit> getPendingMerchantTransaction(String userName) {
		
		ArrayList<MerchantCredit> listTransactions = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_ALL_MERCHANT_PENDING_TRANSACTIONS + "(?)" );
			sqlstatement.setString(1,userName );		
			ResultSet rs = sqlstatement.executeQuery();
		
			listTransactions = new ArrayList <MerchantCredit>();
			while(rs.next())
			{
				MerchantCredit transaction = new MerchantCredit();
				transaction.setFromaccount(rs.getString("fromaccount"));
				transaction.setTomerchantaccount(rs.getString("tomerchantaccount"));
				transaction.setFromusername(rs.getString("fromusername"));
				transaction.setTomerchantname(rs.getString("tomerchantname"));
				transaction.setTransactionID(rs.getString("transactionid"));
				transaction.setSignedRequest(rs.getString("signedrequest"));
				transaction.setAmount(rs.getDouble("amount"));
				transaction.setPublicKey(rs.getBytes("publicKey"));
				listTransactions.add(transaction);
			}
			
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			e.printStackTrace();
		}finally
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
		
		return listTransactions;
	}
	
	/**
	 * this method handles the transaction and changes balacne of merchant and 
	 * regular external custoemer. This method is always called after the signature is verified.
	 */
	public void insertNewTransaction(MerchantCredit credit) throws BankStorageException
	{
		Connection connection = null;
		try{
			connection = dataSource.getConnection();
			double fromCustomerBalance = customerdbconnection.getbalanceofCustomer(credit.getFromusername());
			double toCustomerBalance = customerdbconnection.getbalanceofCustomer(credit.getTomerchantname());
			double newfromBalance=0,newtobalance=0;
			if (fromCustomerBalance > 0)
			{
				newfromBalance= fromCustomerBalance-credit.getAmount();				
				if(newfromBalance >=0) {
					newtobalance = toCustomerBalance +credit.getAmount(); 
					System.out.println("newfromBalance"+newfromBalance);
					System.out.println("newtobalance"+ newtobalance);
					System.out.println("fromuser"+credit.getFromusername());
					System.out.println("touser "+credit.getTomerchantname());
					customerdbconnection.updatebalanceofCustomer(credit.getFromusername(),newfromBalance);
					customerdbconnection.updatebalanceofCustomer(credit.getTomerchantname(),newtobalance);			
					int booleanResult = customerdbconnection.insertNewTransaction(credit);
					if(booleanResult == 0){
						throw new BankStorageException();
					}
					
				}
			} 		
		}catch (Exception e) {
			throw new BankStorageException(e);
		}finally
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
	}

	
	
	public int deletePendingMerchantTransaction(String transactionID)throws BankStorageException {
		Connection connection = null;
		try{
			connection = dataSource.getConnection();
			PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_PENDING_TRANSACTION_MERCHANT + "(?)" );
			sqlstatement.setString(1, transactionID );		
			sqlstatement.execute();
			int updateCount = sqlstatement.getUpdateCount();
			return updateCount;
		}catch (SQLException e) {
			throw new BankStorageException(e);
		}finally
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
	} 
	
	
}
