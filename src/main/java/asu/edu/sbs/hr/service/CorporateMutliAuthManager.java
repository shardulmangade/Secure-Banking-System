package asu.edu.sbs.hr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.CorporateMultiAuthDBConnectionManager;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class CorporateMutliAuthManager {

	@Autowired
	private CorporateMultiAuthDBConnectionManager corporatedbConnection;
	
	public List<User> getAllActiveManagers() throws BankStorageException
	{
		return corporatedbConnection.getAllActiveManagers();
	}
}
