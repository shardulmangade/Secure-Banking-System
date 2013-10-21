package asu.edu.sbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.ConnectionEstablishment;
import asu.edu.sbs.domain.User;

@Service
public class OTPManage {
	 @Autowired
     private ConnectionEstablishment connection;
     
     public List<User> getAllUserRequests()
     {
             return connection.getAllUserRequests();                
     }

}
