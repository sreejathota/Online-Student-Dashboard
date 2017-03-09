package com.java.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.java.bean.Communication;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;
import com.java.mail.EmailConfirm;


@Repository
public class OracleStudentDAO implements StudentDAO{
	
	JdbcTemplate template;  
	@Autowired  
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	}  
	
	public List<ScheduleInformation> getCourseInformation() {
		try{
		 return template.query("select c.COURSE_NAME,f.FACULTYNAME,fc.SCHEDULE,fc.SCHEDULEID,fc.seats from courses c,faculty_courses fc,faculty f where fc.facultyid=f.facultyid and fc.courseid=c.course_id",new RowMapper<ScheduleInformation>(){  
		        public ScheduleInformation mapRow(ResultSet rs, int row) throws SQLException {  
		            ScheduleInformation scheduleInformation=new ScheduleInformation();  
		            scheduleInformation.setCourseName(rs.getString(1));  
		            scheduleInformation.setFacultyName((rs.getString(2)));  
		            scheduleInformation.setSchedule((rs.getString(3)));  
		            scheduleInformation.setScheduleId(rs.getString(4));
		            scheduleInformation.setSeats(rs.getInt(5));
		            return scheduleInformation;  
		        }  
		    });  
		}catch (EmptyResultDataAccessException e) {
	           System.err.println(e);
	        }
			return null;
	}
	public List<ScheduleInformation> getEnrolledCourses(int studentId) {
		try{
			 return template.query("select c.COURSE_NAME,f.FACULTYNAME,sc.SCHEDULE,sc.SCHEDULEID from student_courses sc,faculty f,courses c where sc.facultyid=f.facultyid and sc.courseid=c.course_id and studentid="+studentId,new RowMapper<ScheduleInformation>(){  
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
	public String getFacultyName(String scheduleId) {
		String s=null;
		
		try{
			String sql="select f.facultyname from faculty f,faculty_courses fc where f.facultyid=fc.facultyid and fc.scheduleid='"+scheduleId+"'";  
		    s= template.queryForObject(sql, String.class);  
			}
			catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return s;
	}
	public Boolean sendEmail(int studentId) {
		List<ScheduleInformation> enrolled=getEnrolledCourses(studentId);
		String studentMailId=null;
		try{
			String query="select mailid from student_graduate where studentid="+studentId;
			studentMailId= template.queryForObject(query, String.class);  
			
		}
			catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }

		// ApplicationContext context = new FileSystemXmlApplicationContext("spring-servlet.xml");
		
	       // EmailConfirm mailer = (EmailConfirm) context.getBean("mailConfirm");
		String message = "<html> <body><h1>Hello </h1>";
		
	        String data;
		message+="<table style=border:1px solid black;border-collapse:collapse; > <tr><th>Course</th><th>Faculty</th><th>Schedule</th></tr>";
				for(ScheduleInformation ci:enrolled){
					data="<tr><td>"+ci.getCourseName()+"</td>  <td>"+ci.getFacultyName()+"</td>   <td>"+ci.getSchedule()+"</td>  </tr>";
					message=message+data;
				}
				message+=" </body></html>";
				
	     
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
	     EmailConfirm m=(EmailConfirm)appContext.getBean("mailMail"); 
	      
	        m.sendMail(studentMailId,message);  
	        ((ClassPathXmlApplicationContext) appContext).close();
	        //((AbstractApplicationContext)appContext).registerShutdownHook();
	        //((AbstractApplicationContext) appContext).registerShutdownHook();
		return true;
	}
	public String studentEnrol(String scheduleId, Student student) {
		String s=null;
	List<ScheduleInformation> courses = getEnrolledCourses(student.getStudentId());
	//int studentId;
	boolean exists=false;
	String timeCheck="no";
	String sql2="select c.course_name,fc.schedule,fc.seats from faculty_courses fc,courses c where fc.courseid=c.course_id and scheduleid=?";
	ScheduleInformation c=template.queryForObject(sql2, new Object[]{scheduleId},new BeanPropertyRowMapper<ScheduleInformation>(ScheduleInformation.class));  
	//System.out.println("i oStudao"+c.getSeats()+" "+c.getCourseName());
	// template.queryForObject(sql1, new Object[]{login.getStudentId(),login.getPassword()},new BeanPropertyRowMapper<Student>(Student.class));  
		try{
			if(courses.size()<3){
				
				for(ScheduleInformation ci:courses){
					if((scheduleId.equalsIgnoreCase(ci.getScheduleId()))||ci.getCourseName().equalsIgnoreCase(c.getCourseName())){
						exists=true;
						break;
					}else if(c.getSchedule().equalsIgnoreCase(ci.getSchedule())){
						exists=true;
						timeCheck="sameDay";
						break;
					}
				
				}
			if(exists==false){
				 if(c.getSeats()>0){
			String sql="insert into student_courses(studentid,facultyid,courseid,schedule,scheduleid)values("+student.getStudentId()+","+"(select facultyid from faculty_courses where scheduleid='"+scheduleId+"'),"+"(select courseid from faculty_courses where scheduleid='"+scheduleId+"'),"+"(select schedule from faculty_courses where scheduleid='"+scheduleId+"'),"+"'"+scheduleId+"')";    
			int i=template.update(sql);
				if(i>0)
			s="inserted"; 
			}
				 else{
						s="notavailable";
						}
			}else{
				if(timeCheck.equalsIgnoreCase("sameDay"))
					s="sameDay";
				else
					s="alreadyenrolled";
			
			}
			}else{
			s="exceeded";
			}
		}catch (EmptyResultDataAccessException e) {
	           System.err.println(e);
	        }
			return s;
		
	}
	public int courseDrop(String scheduleId, Student student) {
		try{
		 String sql="delete from student_courses where scheduleid='"+scheduleId+"'"+" and studentid='"+student.getStudentId()+"'";  
	    return template.update(sql); 
		}catch (EmptyResultDataAccessException e) {
	           System.err.println(e);
	        }
			return 0;
	}
	public List<ScheduleInformation> getFilteredCourseInformation(int studentId) {
		List<ScheduleInformation> filteredcourseInformations=new ArrayList<ScheduleInformation>();
		List<ScheduleInformation> scheduleInformation=getCourseInformation();
		 List<ScheduleInformation> enrolledCourses=getEnrolledCourses(studentId);
		  //scheduleDay;
		 //String enrolledDay;
		 for(ScheduleInformation ci:scheduleInformation){
			 String  scheduleDay=ci.getSchedule().substring(0, Math.min(ci.getSchedule().length(),2));
			 
	    	   for(ScheduleInformation ec:enrolledCourses){
	    		   
	    		   String  enrolledDay=ec.getSchedule().substring(0, Math.min(ci.getSchedule().length(),2));
	    		  
	    		   if(ci.getScheduleId().equalsIgnoreCase(ec.getScheduleId())){
	    			  ci.setEnrolCheck("enrolled");
	    			  break;
	    		   }
	    		   else{
		    			/*if(scheduleDay.equalsIgnoreCase(enrolledDay)){
		    				
			    			   ci.setEnrolCheck("sameDay");
			    			   
			    		   }else{*/
			    			   ci.setEnrolCheck("notEnrolled");
			    		   //}
		    			
		    		   }
	    	   }
	    	   filteredcourseInformations.add(ci);
		 }
		 //filteredcourseInformations=scheduleInformation;
		/* List<ScheduleInformation> filteredCI=new ArrayList<ScheduleInformation>();
		 for(ScheduleInformation fci:filteredcourseInformations){
			 
			 if(("sameDay"==fci.getEnrolCheck())){
				
				 continue;
			 }
			 filteredCI.add(fci);
		 }
		*/
		//return filteredCI;
		 return filteredcourseInformations;
	}
	public Boolean sendMessageToFaculty(Student student, String scheduleId,Communication comm) {
		String sql="insert into studenttofaculty(studentid,scheduleid,message)values("+student.getStudentId()+",'"+scheduleId+"','"+comm.getMessage()+"')";
		int i=template.update(sql);
		if(i>0)
			return true;
		return false;
	}

	public List<Communication> viewmessages(Student student1) {
		try{
			 return template.query("select f.facultyname,fs.message from facultytostudent fs,faculty f where fs.facultyid=f.facultyid and fs.studentid="+student1.getStudentId(),new RowMapper<Communication>(){  
			        public Communication mapRow(ResultSet rs, int row) throws SQLException {  
			        	Communication messages=new Communication();  
			        	//messages.setStudentId(rs.getInt(1));
			        	messages.setStudentName(rs.getString(1));
			        	messages.setMessage(rs.getString(2));
			            return messages;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
		return null;
	}
	
	
	}
