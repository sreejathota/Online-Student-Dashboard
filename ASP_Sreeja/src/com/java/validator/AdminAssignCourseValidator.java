package com.java.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.java.bean.ScheduleInformation;
@Component
public class AdminAssignCourseValidator  implements Validator{
	boolean error = false;
	
	public boolean supports(Class<?> clazz) {
		
		 return ScheduleInformation.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		ScheduleInformation scheduleInformation = (ScheduleInformation) target;
		 if(scheduleInformation.getCourseName().equalsIgnoreCase("NONE")){
				
				errors.rejectValue("courseName", "scheduleInformation.courseName.required");
			}
		 if(scheduleInformation.getSchedule().equalsIgnoreCase("NONE")){
				
				errors.rejectValue("schedule", "scheduleInformation.schedule.required");
			}
	}
}
