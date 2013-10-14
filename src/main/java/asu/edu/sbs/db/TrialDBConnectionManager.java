package asu.edu.sbs.db;

import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.User;

@Service
public class TrialDBConnectionManager {

	public User getUser(String userid)
	{
		User user = null;
		
		//TODO: Make database connection and populate User object
		user = new User();
		user.setFirstName("Ram");
		return user;
		
	}
}
