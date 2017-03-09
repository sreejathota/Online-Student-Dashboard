
package com.java.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.java.bean.Login;
import com.java.bean.PasswordDetails;

@Component
public class ChangePasswordValidator implements Validator {
	//String ID_PATTERN = "[0-9]+";
	public boolean supports(Class<?> clazz) {
		
		 return PasswordDetails.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "valid.password");
		
		        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "valid.confirmPassword");
		 PasswordDetails passwordDetails = (PasswordDetails) target;
		 
			 if(passwordDetails.getSecurityQuestion1().equalsIgnoreCase("NONE")){
				
				errors.rejectValue("securityQuestion1", "passwordDetails.securityQuestion1.required");
			}
			else if(!(passwordDetails.getPassword().equalsIgnoreCase(passwordDetails.getConfirmPassword()))){
				System.out.println("xxxxxxxxxxxx"+passwordDetails.getPassword());
		    	System.out.println("yyyyyyyyyyyyy"+passwordDetails.getConfirmPassword());
		    	System.out.println("zzzzzzzzzzzzzzzz"+passwordDetails.getSecurityQuestion1());
				//errors.rejectValue("confirmPassword", "confirmPassword.equal");
		    	errors.rejectValue("password", "passwordDetails.password.notmatch");
		    	
			}
			
			else if("NONE".equals(passwordDetails.getSecurityQuestion2())){
				System.out.println("sq2");
				errors.rejectValue("securityQuestion2", "passwordDetails.securityQuestion2.required");
			}
	}


}
