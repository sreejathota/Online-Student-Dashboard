package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.bean.Communication;
import com.java.bean.ScheduleInformation;
import com.java.bean.Login;
import com.java.bean.Student;
import com.java.dao.OracleStudentDAO;

@Service
public class StudentService {
	@Autowired
	OracleStudentDAO studentdao;
	
	public String studentEnrol(String scheduleId, Student student) {
		
		return studentdao.studentEnrol(scheduleId,student);
	}
	public int courseDrop(String scheduleId, Student student) {
		
		return studentdao.courseDrop(scheduleId,student);
	}
	public List<ScheduleInformation> getEnrolledCourses(int studentId) {
		
		return studentdao.getEnrolledCourses(studentId);
	}
	public List<ScheduleInformation> getFilteredCourseInformation(int studentId) {
		
		return studentdao.getFilteredCourseInformation(studentId);
	}
	public Boolean sendEmail(int studentId) {
		
		return studentdao.sendEmail(studentId);
	}
	public String getFacultyName(String scheduleId) {
		
		return studentdao.getFacultyName(scheduleId);
	}
	public Boolean sendMessageToFaculty(Student student, String scheduleId,
			Communication comm) {
		
		return studentdao.sendMessageToFaculty(student,scheduleId,comm);
	}
	public List<Communication> viewmessages(Student student1) {
		
		return studentdao.viewmessages(student1);
	}
}
