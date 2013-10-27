package asu.edu.sbs.merchant.service;

import java.util.List;

import javax.activity.InvalidActivityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.CustomerDBConnection;
import asu.edu.sbs.db.MerchantDBConnectionManager;
import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.MerchantTransaction;

@Service
public class MerchantManager {
	
	@Autowired
	private MerchantDBConnectionManager merchantdbconnection;
	@Autowired
	private CustomerDBConnection customerdbconnection;
	
	public List<Credit> getAllTransaction(String userName)
	{
		return customerdbconnection.getAllTransaction( userName);		
	}
	
	
	public int insertNewTransaction(Credit credit) throws Exception
	{			
		double fromCustomerBalance = customerdbconnection.getbalanceofCustomer(credit.getFromCustomer());
		double toCustomerBalance = customerdbconnection.getbalanceofCustomer(credit.getToCustomer());
		double newfromBalance=0,newtobalance=0;
		if (fromCustomerBalance > 0)
		{
			newfromBalance= fromCustomerBalance-credit.getAmount();
			
			
			if(newfromBalance >=0) {
				newtobalance = toCustomerBalance +credit.getAmount(); 
				System.out.println("newfromBalance"+newfromBalance);
				System.out.println("newtobalance"+ newtobalance);
				System.out.println("fromuser"+credit.getFromCustomer());
				System.out.println("touser "+credit.getToCustomer());
				customerdbconnection.updatebalanceofCustomer(credit.getFromCustomer(),newfromBalance);
				customerdbconnection.updatebalanceofCustomer(credit.getToCustomer(),newtobalance);
				return (customerdbconnection.insertNewTransaction(credit));
			}else {
				throw new InvalidActivityException();
			}
		} else
		{
			throw new InvalidActivityException();
		}			
	}
	
	public boolean validateRecepientUser(String userName,String account) throws Exception
	{
		return (customerdbconnection.validateRecepientUser(userName,account));
	}
	public List<MerchantTransaction> getAllPendingTransaction(String merchantName)
	{
		return merchantdbconnection.getAllPendingTransaction(merchantName);		
	}

}
