package asu.edu.sbs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.Transaction;


@Service
public class TransactionDBManager {
		
	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
	private String dbCommand;
	private Connection connect;
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<String> getUsersForPermission() throws Exception
	{
		//connection = dataSource.getConnection();
		List<String> users =new ArrayList<String>(); 
		try {
			Connection connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.USER_FOR_PERMISSION_TRANSACTIONS + "()";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.execute();

			ResultSet result =  sqlStatement.getResultSet();
			
			if(result.isBeforeFirst())
			{
				String user= new String();
				while(result.next())
				{
					user=result.getString(2);
					users.add(user);
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
		return users;
	}
	
	public List<Transaction> getRegEmpTransactions() throws Exception
	{
		//connection = dataSource.getConnection();
		List<Transaction> transactions =null; 
		try {
			Connection connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.TRANSACTIONS_FOR_REG_EMPLOYEE + "()";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.execute();

			ResultSet result =  sqlStatement.getResultSet();
			
			if(result.isBeforeFirst())
			{
				transactions= new ArrayList<Transaction>();
				while(result.next())
				{
					Transaction tran = new Transaction();
					tran.setTransactionID(result.getString(1));
					tran.setDescription(result.getString(2));
					tran.setTime(result.getString(3));
					transactions.add(tran);
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
		return transactions;
	}
	
	
}
