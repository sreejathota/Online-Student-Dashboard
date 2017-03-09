package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.bean.Faculty;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;
import com.java.dao.AdminDAO;
@Service
public class AdminService {
	@Autowired
	AdminDAO admindao;

	public List<Faculty> getAllFaculty() {
		
		return admindao.getAllfaculty();
	}

	public int addCourse(ScheduleInformation course) {
		
		return admindao.addCourse(course);
	}

	public int addFaculty(Faculty faculty) {
		return admindao.addFaculty(faculty);
	}
	public List<ScheduleInformation> getCourseInformation() {
		return admindao.getCourseInformation();
	}

	public List<Student> viewStudents() {
		return admindao.viewStudents();
	}

	public List<ScheduleInformation> getAvailableSchedules(int facultyId) {
		return admindao.getAvailableSchedules(facultyId);
	}

	public List<String> getCourses() {
		return admindao.getCourses();
	}

	public int assignCoursetoFaculty(String courseName, String scheduleId,
			int facultyId) {
		
		return admindao.assignCoursetoFaculty(courseName,scheduleId,facultyId);
	}

}
