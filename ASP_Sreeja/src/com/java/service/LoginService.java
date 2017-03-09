package com.java.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.java.bean.Faculty;
import com.java.bean.Login;
import com.java.bean.PasswordDetails;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;
import com.java.bean.Admin;
import com.java.dao.OracleLoginDAO;

@Service
public class LoginService {
	@Autowired
	OracleLoginDAO logindao;
	public int register(Student student){
		return logindao.register(student);
	}
	
	public Student getStudent(Login login) {
		
		return logindao.getStudent(login);
	}
	public int getExistingStudent(int studentId){
		return logindao.getExistingStudent(studentId);
	}
	public List<ScheduleInformation> getFilteredCourseInformation(int studentId) {
		
		return logindao.getFilteredCourseInformation(studentId);
	}
	public String getUser(Login login) {
	
	return logindao.getUser(login);
	}
	public Faculty getFaculty(Login facultylogin) {

		return logindao.getFaculty(facultylogin);
	}

	public List<List<String>> getSecurityQuestions() {
		
		return logindao.getSecurityQuestions();
	}

	public boolean changePassword(PasswordDetails passwordDetails, int studentId) {
		return logindao.changePassword(passwordDetails,studentId);
	}

	public String getOldPassword(int studentId) {
		return logindao.getOldPassword(studentId);
	}

	public Admin getAdmin(Login login) {
		
		return logindao.getAdmin(login);
	}
}
