package com.java.dao;

import java.util.List;

import com.java.bean.Communication;
import com.java.bean.ScheduleInformation;
import com.java.bean.Faculty;
import com.java.bean.FacultyLogin;
import com.java.bean.Student;

public interface FacultyDAO {
	public Faculty getFaculty(FacultyLogin facultylogin);
	public List<ScheduleInformation> getScheduleIds(int facultyId);
	public List<Student> viewenrolledstudent(String scheduleId);
	public List<ScheduleInformation> viewmycourseschedule(Faculty faculty);
	public List<Communication> viewmessages(Faculty faculty);
	public Student getStudentDetails(int studentId);
	public Boolean replytoStudent(int studentId,int facultyId, Communication comm);
}
