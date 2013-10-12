package asu.edu.sbs.service;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import asu.edu.sbs.domain.SignUpUser;

public class SignUpValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		SignUpUser.class.isAssignableFrom(arg0);
		return false;
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
