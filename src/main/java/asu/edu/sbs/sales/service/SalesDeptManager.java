package asu.edu.sbs.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.domain.User;

@Service
public class SalesDeptManager {

	@Autowired
	private SalesDBConnectionManager salesdbconnection;
	
	public List<User> getAllUserRequests()
	{
		return salesdbconnection.getAllUserRequests();		
	}
}
