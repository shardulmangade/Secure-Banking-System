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
}

		

