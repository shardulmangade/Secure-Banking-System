package asu.edu.sbs.aspects;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.domain.IBankRoles;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankDeactivatedException;

@Aspect
@Component
public class LoggingAspect {

	@Autowired
	private LoginDBConnectionManager dbconnection; 
	
	@Before("within(asu.edu.sbs.web..*)")
	public void logBefore(JoinPoint joinPoint) throws BankDeactivatedException {

//		System.out.println("logBefore() is running!");
//		System.out.println("hijacked : " + joinPoint.getSignature().getName());
//		System.out.println("******");
//		//retrieve the logged in User name
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String username = authentication.getName();
//		String dbLoginRole = dbconnection.getLoginRole(username);
//		System.out.println("Role fetched from db: "+dbLoginRole);
//		
//		if(dbLoginRole.equals(IBankRoles.ROLE_INVALID_USER))
//		{
//			//Automatically logout the user
//			SecurityContextHolder.clearContext();
//			throw new BankDeactivatedException();
//		}
//		System.out.println("******"); 

	}

}