package asu.edu.sbs.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.domain.IBankRoles;
import asu.edu.sbs.exception.BankDeactivatedException;

@Aspect
@Component
public class LoggingAspect {

	@Autowired
	private LoginDBConnectionManager dbconnection; 
	
	@Around("within(asu.edu.sbs.web..*)")
	public Object logBefore(ProceedingJoinPoint joinPoint) throws Throwable {

		//retrieve the logged in User name
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		String dbLoginRole = dbconnection.getLoginRole(username);
//		System.out.println("Aspect Role fetched from database: "+dbLoginRole);
//		if(dbLoginRole == null || dbLoginRole.equals(IBankRoles.ROLE_INVALID_USER) || dbLoginRole.equals("null"))
		if(dbLoginRole.equals(IBankRoles.ROLE_INVALID_USER))
		{
			//Automatically logout the user
			SecurityContextHolder.clearContext();
			throw new BankDeactivatedException("Looks like someone deactivated your account after you logged in. Your only option now is to logout securely !");
		}
			
		Object obj = joinPoint.proceed();
		return obj;

	}

}