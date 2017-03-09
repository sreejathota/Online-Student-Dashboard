package com.java.dao;

import java.util.List;

import com.java.bean.Communication;
import com.java.bean.ScheduleInformation;
import com.java.bean.Login;
import com.java.bean.Student;

public interface StudentDAO {
	
	public String studentEnrol(String scheduleId, Student student);
	public int courseDrop(String scheduleId, Student student);
	public List<ScheduleInformation> getFilteredCourseInformation(int studentId);
	public Boolean sendEmail(int studentId);
	public String getFacultyName(String scheduleId);
	public Boolean sendMessageToFaculty(Student student, String scheduleId,
			Communication comm);
	public List<Communication> viewmessages(Student student1);
}
