
package asu.edu.sbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.TrialDBConnectionManager;
import asu.edu.sbs.domain.User;

@Service
public class TrialUserManager {

	@Autowired
	private TrialDBConnectionManager dbconnectionManager;
	
	public User getUser(String userid)
	{
		User user = null;
		user = dbconnectionManager.getUser(userid);
		if(user !=null)
		{		
			//TODO: Perform any bussiness logic on the user object from database
		}
		return user;
	}
}
