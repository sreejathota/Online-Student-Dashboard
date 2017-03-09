package com.java.bean;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ScheduleInformation {

	private String facultyName;
	@NotEmpty@Size(min=4, max=30) 
	private String courseName;
	private String schedule;
	private String scheduleId;
	private int seats;
	private String enrolCheck;
	
	
	@Override
	public String toString() {
		return "CourseInformation [facultyName=" + facultyName
				+ ", courseName=" + courseName + ", schedule=" + schedule
				+ ", scheduleId=" + scheduleId + ", seats=" + seats
				+ ", enrolCheck=" + enrolCheck + "]";
	}
	public String getEnrolCheck() {
		return enrolCheck;
	}
	public void setEnrolCheck(String enrolCheck) {
		this.enrolCheck = enrolCheck;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
}
