package asu.edu.sbs.transaction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.Transaction;
import asu.edu.sbs.db.TransactionDBManager;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionDBManager transactionDBManager;
	
	public List<Transaction> getRegEmpTransactions() throws Exception
	{
		//transactionDBManager = new TransactionDBManager();
		return transactionDBManager.getRegEmpTransactions(); 
	}
	
	public List<String> getUsersForPermission() throws Exception
	{
		return transactionDBManager.getUsersForPermission();
		
	}
	
	public void makeUsersActive(List<String> users)
	{
		transactionDBManager.makeUsersActive(users);
	}
}
