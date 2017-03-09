package com.java.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.java.bean.Login;
import com.java.bean.ScheduleInformation;
@Component
public class AdminAddCourseValidator  implements Validator{
	boolean error = false;
	
	public boolean supports(Class<?> clazz) {
		
		 return ScheduleInformation.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "seats", "seats.blank");
      
		ScheduleInformation scheduleInformation = (ScheduleInformation) target;
		if(scheduleInformation.getSeats() ==0){
			errors.rejectValue("seats", "seats.blank");
		}
		else if( (scheduleInformation.getSeats()<30|| scheduleInformation.getSeats()>100)) {
			System.out.println("entered");
			    errors.rejectValue("seats", "seats.length");
			   }
			  
		
		
	}
	

}
