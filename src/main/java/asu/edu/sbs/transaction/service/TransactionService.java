package asu.edu.sbs.transaction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.Transaction;
import asu.edu.sbs.db.TransactionDBManager;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionDBManager transactionDBManager;
	
	public List<Transaction> getRegEmpTransactions(String grantedTo) throws Exception
	{
		return transactionDBManager.getRegEmpTransactions(grantedTo); 
	}
	
	public List<String> getUsersForPermission() throws Exception
	{
		return transactionDBManager.getUsersForPermission();
		
	}
	
	public void makeUsersActive(List<String> users, String requestedBy) throws BankStorageException
	{
		try{
		transactionDBManager.makeUsersActive(users, requestedBy);
		}
		catch(Exception e)
		{
			throw new BankStorageException(e);
		}
	}
}
