package com.java.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.java.bean.Communication;
import com.java.bean.ScheduleInformation;
import com.java.bean.Faculty;
import com.java.bean.FacultyLogin;
import com.java.bean.Student;

@Repository
public class OracleFacultyDAO implements FacultyDAO {
	JdbcTemplate template;  
	@Autowired  
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	}  
	public Faculty getFaculty(FacultyLogin facultylogin) {
		
			try{
			String sql="select * from faculty where facultyid=? and password=?";  
		    return template.queryForObject(sql, new Object[]{facultylogin.getFacultyId(),facultylogin.getPassword()},new BeanPropertyRowMapper<Faculty>(Faculty.class));  
			}
			catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return null;
		}
	public List<ScheduleInformation> getScheduleIds(int facultyId) {
		try{
			 return template.query("select c.COURSE_NAME,f.FACULTYNAME,fc.SCHEDULE,fc.SCHEDULEID from faculty_courses fc,faculty f,courses c where fc.facultyid=f.facultyid and fc.courseid=c.course_id and fc.facultyid="+facultyId,new RowMapper<ScheduleInformation>(){  
			        public ScheduleInformation mapRow(ResultSet rs, int row) throws SQLException {  
			            ScheduleInformation scheduleInformation=new ScheduleInformation();  
			            scheduleInformation.setCourseName(rs.getString(1));  
			            scheduleInformation.setFacultyName((rs.getString(2)));  
			            scheduleInformation.setSchedule((rs.getString(3)));  
			            scheduleInformation.setScheduleId(rs.getString(4));
			            //courseInformation.setSeats(rs.getInt(5));
			            return scheduleInformation;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return null;
		
	}
	public List<Student> viewenrolledstudent(String scheduleId) {
		try{
			 return template.query("select sg.studentid,firstname,lastname,mailid from student_graduate sg,student_courses sc where sc.studentid=sg.studentid and sc.scheduleid='"+scheduleId+"'",new RowMapper<Student>(){  
			        public Student mapRow(ResultSet rs, int row) throws SQLException {  
			            Student enrolledStudent=new Student();  
			            enrolledStudent.setStudentId(rs.getInt(1));  
			            enrolledStudent.setFirstName((rs.getString(2)));  
			            enrolledStudent.setLastName((rs.getString(3)));  
			            enrolledStudent.setMailId(rs.getString(4));
			           
			            return enrolledStudent;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
		return null;
	}
	public List<ScheduleInformation> viewmycourseschedule(Faculty faculty) {
		try{
			 return template.query("select c.COURSE_NAME,f.FACULTYNAME,fc.SCHEDULE,fc.SCHEDULEID from faculty_courses fc,faculty f,courses c where fc.facultyid=f.facultyid and fc.courseid=c.course_id and fc.facultyid="+faculty.getFacultyId(),new RowMapper<ScheduleInformation>(){  
			        public ScheduleInformation mapRow(ResultSet rs, int row) throws SQLException {  
			            ScheduleInformation scheduleInformation=new ScheduleInformation();  
			            scheduleInformation.setCourseName(rs.getString(1));  
			            scheduleInformation.setFacultyName((rs.getString(2)));  
			            scheduleInformation.setSchedule((rs.getString(3)));  
			            scheduleInformation.setScheduleId(rs.getString(4));
			            //courseInformation.setSeats(rs.getInt(5));
			            return scheduleInformation;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return null;
	
	}
	public List<Communication> viewmessages(Faculty faculty) {
		try{
			 return template.query("select sf.studentid,sg.firstname,sg.lastname,sf.scheduleid,sf.message from studenttofaculty sf,student_graduate sg where sf.studentid=sg.studentid and sf.scheduleid in(select scheduleid from faculty_courses where facultyid="+faculty.getFacultyId()+")",new RowMapper<Communication>(){  
			        public Communication mapRow(ResultSet rs, int row) throws SQLException {  
			        	Communication messages=new Communication();  
			        	messages.setStudentId(rs.getInt(1));
			        	messages.setStudentName(rs.getString(3)+" "+rs.getString(2));
			        	messages.setScheduleId(rs.getString(4));
			        	messages.setMessage(rs.getString(5));
			            return messages;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
		return null;
	}
	public Boolean replytoStudent(int studentId,int facultyId, Communication comm) {
		String sql="insert into facultytostudent(facultyid,studentid,message)values("+facultyId+",'"+studentId+"','"+comm.getMessage()+"')";
		int i=template.update(sql);
		if(i>0)
			return true;
		return false;
	}
	public Student getStudentDetails(int studentId) {
		
		try{
			String sql="select * from student_graduate where studentid=?";  
		    return template.queryForObject(sql, new Object[]{studentId},new BeanPropertyRowMapper<Student>(Student.class));  
			}
			catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return null;
	}

}
