package asu.edu.sbs.domain;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class PasswordChangeValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		 return arg0.isAssignableFrom(PasswordChange.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "newPassword", "Password field Cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "confirmNewPassword", "You have to confirm your password");
		PasswordChange pwd = (PasswordChange)obj;
		
		if(!pwd.getNewPassword().equals(pwd.getConfirmNewPassword()))
		{			
			err.reject("Passwords do not match");
		}
	}

}
