package com.java.dao;

import java.util.List;
import java.util.Map;

import com.java.bean.Faculty;
import com.java.bean.Login;
import com.java.bean.PasswordDetails;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;

public interface LoginDAO {
	public int register(Student student);
	public Student getStudent(Login login);
	public int getExistingStudent(int studentId);
	public List<ScheduleInformation> getFilteredCourseInformation(int studentId);
	public String getUser(Login login);
	public Faculty getFaculty(Login facultylogin);
	public Map<String,String> getSecurityQuestions();
	public boolean changePassword(PasswordDetails passwordDetails,int studentId);
	public boolean getOldPassword(int studentId);
}
