package com.java.dao;

import java.util.List;

import com.java.bean.Faculty;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;

public interface AdminDAO {

	public List<Faculty> getAllfaculty();

	public int addCourse(ScheduleInformation course);

	public int addFaculty(Faculty faculty);
	public List<ScheduleInformation> getCourseInformation();

	public List<Student> viewStudents();

	public List<ScheduleInformation> getAvailableSchedules(int facultyId);

	public List<String> getCourses();

	public int assignCoursetoFaculty(String courseName, String scheduleId,
			int facultyId);

}
