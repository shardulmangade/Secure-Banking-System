package asu.edu.sbs.customer.service;

import java.util.List;

import javax.activity.InvalidActivityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.CustomerDBConnection;
import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.Notification;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class CustomerManager {

	
	
		@Autowired
		private CustomerDBConnection customerdbconnection;
		
		public List<Credit> getAllTransaction(String userName)
		{
			return customerdbconnection.getAllTransaction( userName);		
		}
		
		public List<Notification> getNotifications(String euser)
		{
			return customerdbconnection.getNotifications(euser);			
		}
		
		public void grantAccess(List<String> iusers, String currentEuser) throws BankStorageException
		{
			customerdbconnection.grantAccess(iusers, currentEuser);
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
		
		public String getAccountNumberForCustomer (String userName) throws Exception
		{
			return (customerdbconnection.getAccountNumberForCustomer(userName));
		}
}
