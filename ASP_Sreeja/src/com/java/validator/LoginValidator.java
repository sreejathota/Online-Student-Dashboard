package com.java.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.java.bean.Login;
@Component
public class LoginValidator implements Validator{
	boolean error = false;
	// private Pattern pattern;
	 //private Matcher matcher;
	 String ID_PATTERN = "[0-9]+";
	public boolean supports(Class<?> clazz) {
		
		 return Login.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.blank");
       // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password");
        Login log = (Login) target;
		if(log.getId() ==0){
			errors.rejectValue("id", "id.blank");
		}// input string conatains numeric values only
		else if (log.getId() != 0) {
			  // pattern = Pattern.compile(ID_PATTERN);
			  // matcher = pattern.matcher(String.valueOf(log.getId()));
			   /*if (!matcher.matches()) {
			    errors.rejectValue("id", "id.notanumber");
			   }*/

			// input string can not exceed that a limit
			    if(! (String.valueOf(log.getId()).length()==9)) {
			    errors.rejectValue("id", "id.length");
			   }
			  }
		
		
	}
	/*public boolean loginValidate(Object target, Errors errors) {
		Login l=(Login) target;
		 if(l.getId()==0){
			 errors.rejectValue("id", "error.id");
			 error = true;
		 }
		 return error;
	}*/

}
