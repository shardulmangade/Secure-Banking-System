package asu.edu.sbs.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class CorporateMultiAuthDBConnectionManager {


	private static final Logger logger = LoggerFactory.getLogger(CorporateMultiAuthDBConnectionManager.class);
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

	public List<User> getAllActiveManagers() throws BankStorageException {
		String dbCommand;
		Connection connection = null;
		List<User> activeManagersList = null;

		try {
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.CORPORATE_GET_ALL_ACTIVE_MANAGERS + "()";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			sqlStatement.execute();
			
			ResultSet result =  sqlStatement.getResultSet();

			if(result.isBeforeFirst())
			{
				activeManagersList = new ArrayList<User>();
				User user = null;
				while(result.next())
				{
					user = new User();
					user.setUsername(result.getString(1));
					user.setFirstName(result.getString(2));
					user.setLastName(result.getString(3));
					user.setEmail(result.getString(4));
					user.setDepartment(result.getString(5));
					user.setRole(result.getString(6));
					user.setCreatedBy(result.getString(7));
					activeManagersList.add(user);
				}
			}
			
		} catch (SQLException e) {
			throw new BankStorageException(e);
		}
		

		return activeManagersList;
	}

}
