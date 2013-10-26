package asu.edu.sbs.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.MerchantTransaction;

import com.mysql.jdbc.PreparedStatement;

@Service
public class MerchantDBConnectionManager {

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
	
	public List<MerchantTransaction> getAllPendingTransaction(String userName) {
		List<MerchantTransaction> listTransactions = null;
		try {
			Connection connection = dataSource.getConnection();
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
				transaction.setCertificate(rs.getString("certificate"));				
				transaction.setAmount(rs.getDouble("amount"));
				listTransactions.add(transaction);
			}
			
		} catch (SQLException e) {
			// TODO Use our application specific custom exception
			e.printStackTrace();
		}
	
	
	return listTransactions;
 }
}
