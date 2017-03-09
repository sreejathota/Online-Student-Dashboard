package com.java.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.java.bean.Faculty;

@Component
public class AdminAddFacultyValidator  implements Validator{
	boolean error = false;
	
	public boolean supports(Class<?> clazz) {
		
		 return Faculty.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		Faculty faculty = (Faculty) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "facultyId", "id.blank");
		
		 if(faculty.getFacultyId() ==0){
			 
				errors.rejectValue("facultyId", "id.blank");
				
			}
			else if (faculty.getFacultyId()!= 0) {
				  
				    if((String.valueOf(faculty.getFacultyId()).length()!=9)) {
				    	
				    errors.rejectValue("facultyId", "id.length");
				   }
				  }
	}
	

}
