package asu.edu.sbs.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import asu.edu.sbs.domain.SignUpUser;

@Component
public class SignUpValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return (SignUpUser.class.isAssignableFrom(arg0));
		
	}

	@Override
	public void validate(Object obj, Errors error) {

		ValidationUtils.rejectIfEmptyOrWhitespace(error, "firstName", "firstName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "lastName", "lastName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "userName", "userName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "emailId", "emailId.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "dateOfBirth", "dateOfBirth.required");		
		
	}

	
}
