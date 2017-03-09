package com.java.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.java.bean.Student;

@Component
public class StudentRegisterValidator implements Validator {
	String ID_PATTERN = "[0-9]+";
	public boolean supports(Class<?> clazz) {
		
		 return Student.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studentId", "id.blank");
		 Student student = (Student) target;
		 if(student.getStudentId() ==0){
			 
				errors.rejectValue("studentId", "id.blank");
				//System.out.println("iddddddddddddddd");
			}
			else if (student.getStudentId() != 0) {
				  
				    if((String.valueOf(student.getStudentId()).length()!=9)) {
				    	//System.out.println("lengthhhhhhhhhhh");
				    errors.rejectValue("studentId", "id.length");
				   }
				  }
		 //System.out.println("in student register");
	}


}
