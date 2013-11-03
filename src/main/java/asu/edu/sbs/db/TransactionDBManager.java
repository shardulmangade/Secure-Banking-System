package asu.edu.sbs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.activity.InvalidActivityException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.PreparedStatement;

import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.Transaction;
import asu.edu.sbs.exception.BankStorageException;


@Service
public class TransactionDBManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
	private String dbCommand;
	//private Connection connect;
	//private Connection connection;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	

	public void makeUsersActive(List<String> users, String requestedBy) throws BankStorageException
	{
		System.out.println("****** these users are just made active by the regular employee");


		Connection connection = null;
		try{

			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL+" "+DBConstants.INSERT_TRANSACTION_PERMISSION_REQUESTS + "(?,?)";
			for(String user:users){
				try
				{
					CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
					sqlStatement.setString(1,user);
					sqlStatement.setString(2, requestedBy);
					sqlStatement.execute();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				//
				
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

	public List<String> getUsersForPermission() throws Exception
	{
		//connection = dataSource.getConnection();
		Connection connection= null;
		List<String> users =new ArrayList<String>(); 
		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_USERS_FOR_REQUESTING_TRANSACTION_PERMISSION + "()";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.execute();

			ResultSet result =  sqlStatement.getResultSet();

			if(result.isBeforeFirst())
			{
				String user= new String();
				while(result.next())
				{
					user=result.getString(1);
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
		return users;
	}

	public List<Transaction> getRegEmpTransactions(String grantedTo) throws Exception
	{
		//connection = dataSource.getConnection();
		List<Transaction> transactions =null; 
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_TRANSACTIONS_OF_PERMITTED_USERS + "(?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,grantedTo);
			sqlStatement.execute();

			ResultSet result =  sqlStatement.getResultSet();

			if(result.isBeforeFirst())
			{
				transactions= new ArrayList<Transaction>();
				while(result.next())
				{
					Transaction tran = new Transaction();
					tran.setFromuser(result.getString(1));
					tran.setTouser(result.getString(2));
					tran.setAmount(result.getString(3).toString());
					tran.setCertificate(result.getString(5));
					//tran.setTimestamp("this moment");
					tran.setTimestamp(result.getString(4));
					
					
					
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
		return transactions;
	}

	public List<Transaction> getTransactionsForManager() throws Exception
	{
		//connection = dataSource.getConnection();
		List<Transaction> transactions =null; 
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_TRANSACTIONS_FOR_MANAGER + "()";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.execute();

			ResultSet result =  sqlStatement.getResultSet();

			if(result.isBeforeFirst())
			{
				transactions= new ArrayList<Transaction>();
				while(result.next())
				{
					Transaction tran = new Transaction();
					tran.setFromuser(result.getString(1));
					tran.setTouser(result.getString(2));
					tran.setAmount(result.getString(3).toString());
					tran.setCertificate(result.getString(5));
					//tran.setTimestamp("this moment");
					//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					tran.setTimestamp(result.getString(4));
					
					
					
					
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
		return transactions;
	}

	//*******************

	public void saveNewEmployeeRequest(String UserName,String firstName,String lastName ,String emailId,String department) throws Exception
	{

		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.ALL_NEW_EMPLOYEE_REQUESTS + "(?,?,?,?,?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.setString(2,firstName );
		sqlstatement.setString(3,lastName );
		sqlstatement.setString(4,emailId );
		sqlstatement.setString(5,department );
		sqlstatement.execute();
		connection.close();
	}

	public void deleteEmployeeRequest(String UserName) throws Exception
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_EMPLOYEE_REQUESTS + "(?)" );
		System.out.println("\n"+sqlstatement);
		sqlstatement.setString(1,UserName );		
		sqlstatement.execute();		
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		connection.close();
	}

	public void addNewTransEmployee(SignUpEmployee employee) throws Exception 
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_NEW_EMPLOYEE + "(?,?,?,?,?,?)" );
		sqlstatement.setString(1,employee.getUserName() );
		sqlstatement.setString(2,employee.getFirstName() );
		sqlstatement.setString(3,employee.getLastName() );
		sqlstatement.setString(4,employee.getEmailId() );
		sqlstatement.setString(5,employee.getDepartment() );
		sqlstatement.setString(6,employee.getPassword() );
		sqlstatement.execute();
		connection.close();
	}


	public void deleteTransEmployee(String UserName) throws Exception 
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.DELETE_EMPLOYEE + "(?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.execute();

		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		connection.close();
	}


	public void updateDepartmentOfEmployee(String UserName,String department) throws Exception 
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.UPDATE_EMPLOYEE + "(?,?)" );
		sqlstatement.setString(1,UserName );
		sqlstatement.setString(2,department );
		sqlstatement.execute();
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		connection.close();
	}


	public void insertDeleteRequesttoCM(String userName, String department, boolean deleteApprove) throws Exception 
	{
		Connection connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.INSERT_DELETE_REQUESTS_TO_CORPORATEMGMT + "(?,?,?)" );
		sqlstatement.setString(1,userName );
		sqlstatement.setString(2,department );
		sqlstatement.setBoolean(3,deleteApprove );
		sqlstatement.execute();
		if (sqlstatement.getUpdateCount()==0)
			throw new InvalidActivityException();
		connection.close();
	}

	public int getDeleteApprovalStatus(String userName, String department) throws Exception
	{
		Connection connection = null;
		try{
		 connection = dataSource.getConnection();
		PreparedStatement sqlstatement = (PreparedStatement) connection.prepareStatement(DBConstants.SP_CALL + " " + DBConstants.GET_DELETE_REQUEST_STATUS + "(?,?)" );
		sqlstatement.setString(1,userName );
		sqlstatement.setString(2,department );		
		ResultSet rs = sqlstatement.executeQuery();
		
		if(rs.next())
		{
			if(rs.getBoolean("approvedelete"))
				return 1;
			else 
				return 0;			
		}else {
			return -1;
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			
			try{
			if(connection!=null)
				connection.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		return -1;
	}

	//*******************





}
