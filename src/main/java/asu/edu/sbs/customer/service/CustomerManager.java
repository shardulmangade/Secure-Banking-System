package asu.edu.sbs.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.CustomerDBConnection;
import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.User;

@Service
public class CustomerManager {

	
	
		@Autowired
		private CustomerDBConnection customerdbconnection;
		
		public List<Credit> getAllTransaction(String userName)
		{
			return customerdbconnection.getAllTransaction( userName);		
		}	
}
