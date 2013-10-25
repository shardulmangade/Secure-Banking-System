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

	
	
	public List<Credit> getAllTransaction(String userName) {
		
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
	}
	
	
	return listCredits;
 }
	
	
	public int insertNewTransaction(Credit credit) throws Exception
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_CUSTOMER_NEW_TRANSACTIONS + "(?,?,?,?,?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,credit.getFromCustomer());
		sqlstatement.setString(2,credit.getToCustomer());
		sqlstatement.setString(3,credit.getFromaccount() );
		sqlstatement.setString(4,credit.getToacccount() );
		sqlstatement.setDouble(5, credit.getAmount());
		sqlstatement.execute();
		return (sqlstatement.getUpdateCount());
	}

	public double getbalanceofCustomer(String username) throws Exception
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_BALANCE_OF_CUSTOMER + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,username);
		ResultSet rs = sqlstatement.executeQuery();
		rs.next();
		return (rs.getDouble("balance"));
	}
	
	
	public int updatebalanceofCustomer(String username,double balance) throws Exception
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.UPDATE_BALANCE_OF_CUSTOMER + "(?,?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,username);
		sqlstatement.setDouble(2,balance);
		sqlstatement.execute();
		return (sqlstatement.getUpdateCount());
	}
	
	
	public boolean validateRecepientUser(String userName,String account) throws Exception 
	{
		connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.VALIDATE_RECIPIENT_USER + "(?,?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,userName);
		sqlstatement.setString(2,account);
		ResultSet rs =sqlstatement.executeQuery();
		return(rs.next());												
	}
}

		

