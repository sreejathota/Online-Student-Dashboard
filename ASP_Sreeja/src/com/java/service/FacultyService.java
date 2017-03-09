package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.bean.Communication;
import com.java.bean.ScheduleInformation;
import com.java.bean.Faculty;
import com.java.bean.FacultyLogin;
import com.java.bean.Student;
import com.java.dao.OracleFacultyDAO;

@Service
public class FacultyService {
	@Autowired
	OracleFacultyDAO facultydao;
	public Faculty getFaculty(FacultyLogin facultylogin) {

		return facultydao.getFaculty(facultylogin);
	}
	public List<ScheduleInformation> getScheduleIds(int facultyId) {
		// TODO Auto-generated method stub
		return facultydao.getScheduleIds(facultyId);
	}
	public List<Student> viewenrolledstudent(String scheduleId) {
		// TODO Auto-generated method stub
		return facultydao.viewenrolledstudent(scheduleId);
	}
	public List<ScheduleInformation> viewmycourseschedule(Faculty faculty) {
		// TODO Auto-generated method stub
		return facultydao.viewmycourseschedule(faculty);
	}
	public List<Communication> viewmessages(Faculty faculty) {
		// TODO Auto-generated method stub
		return facultydao.viewmessages(faculty);
	}
	public Boolean replytoStudent(int studentId, int facultyId, Communication comm) {
		// TODO Auto-generated method stub
		return facultydao.replytoStudent(studentId,facultyId,comm);
	}
	public Student getStudentDetails(int studentId) {
		// TODO Auto-generated method stub
		return facultydao.getStudentDetails(studentId);
	}

}
