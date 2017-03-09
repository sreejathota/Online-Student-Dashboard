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

import com.java.bean.Faculty;
import com.java.bean.Login;
import com.java.bean.PasswordDetails;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;
import com.java.bean.Admin;
import com.java.mail.EmailConfirm;

@Repository
public class OracleLoginDAO {
	JdbcTemplate template;  
	@Autowired  
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	}  
	public int register(Student student){ 
		String studentMailId=null;
		
		String first=student.getFirstName().substring(0, Math.min(student.getFirstName().length(), 3));
		String second=student.getLastName().substring(Math.max(0, student.getLastName().length() - 3), student.getLastName().length());
		String sId=String.valueOf(student.getStudentId());
		String third=sId.substring(Math.max(0, sId.length() - 3), sId.length());
		String password=first+second+third;
		studentMailId=student.getMailId();
		String message = "<html> <body><h1>Your default password is "+password+"</h1>";
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
	     EmailConfirm m=(EmailConfirm)appContext.getBean("mailMail"); 
	      
	        m.sendMail(studentMailId,message);  
	        ((ClassPathXmlApplicationContext) appContext).close();
	     
		try{
	    String sql="insert into student_graduate(studentid,firstname,lastname,mailid,password) values('"+student.getStudentId()+"','"+student.getFirstName()+"','"+student.getLastName()+"','"+student.getMailId()+"','"+password+"')";    
	    return template.update(sql);  
		}catch (EmptyResultDataAccessException e) {
           System.err.println(e);
        }
		return 0;
	}
	
	public Student getStudent(Login login) {
		try{
		String sql="select * from student_graduate where studentid=? and password=?";  
	    return template.queryForObject(sql, new Object[]{login.getId(),login.getPassword()},new BeanPropertyRowMapper<Student>(Student.class));  
		}
		catch (EmptyResultDataAccessException e) {
	           System.err.println(e);
	        }
			return null;
	}
	public Admin getAdmin(Login login) {
		try{
			String sql="select * from admin where adminid=? and password=?";  
		    return template.queryForObject(sql, new Object[]{login.getId(),login.getPassword()},new BeanPropertyRowMapper<Admin>(Admin.class));  
			}
			catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
		return null;
	}
	public int getExistingStudent(int studentId) {
		 int i=0;
		try{
		String sql="select studentid from student_graduate where studentid=?";  
	   i= template.queryForObject(sql, new Object[]{studentId},Integer.class);  
		}
		catch (EmptyResultDataAccessException e) {
	           System.err.println(e);
	        }
			return i;
	}
	public List<ScheduleInformation> getFilteredCourseInformation(int studentId) {

		List<ScheduleInformation> filteredcourseInformations=new ArrayList<ScheduleInformation>();
		List<ScheduleInformation> scheduleInformation=getCourseInformation();
		 List<ScheduleInformation> enrolledCourses=getEnrolledCourses(studentId);
		 String scheduleDay;
		 String enrolledDay;
		 for(ScheduleInformation ci:scheduleInformation){
			  scheduleDay=ci.getSchedule().substring(0,  2);
			 
	    	   for(ScheduleInformation ec:enrolledCourses){
	    		   enrolledDay=ec.getSchedule().substring(0, 2);
	    		  
	    		   if(ci.getScheduleId().equalsIgnoreCase(ec.getScheduleId())){
	    			  ci.setEnrolCheck("enrolled");
	    			  //filteredcourseInformations.add(ci);
	    			   break;
	    		   }
	    		 else{
	    			 /*if(scheduleDay.equalsIgnoreCase(enrolledDay)){
	    				System.out.println("sch  "+scheduleDay);
	    				System.out.println("enr  "+enrolledDay);
		    			   ci.setEnrolCheck("sameDay");
		    			   System.out.println("xxxx");
		    			   
		    		   }else{*/
		    			   ci.setEnrolCheck("notEnrolled");
		    		  // }
	    			 //filteredcourseInformations.add(ci);
	    		   }
	    	   }
	    	   filteredcourseInformations.add(ci);
	    	  
		 }
		 //filteredcourseInformations=scheduleInformation;
		/*List<ScheduleInformation> filteredCI=new ArrayList<ScheduleInformation>();
		 for(ScheduleInformation fci:filteredcourseInformations){
			 if(("sameDay"==fci.getEnrolCheck())){
				 System.out.println("pkpk");
				 continue;
			 }
			 filteredCI.add(fci);
		 }
		
		return filteredCI;*/
		return filteredcourseInformations;
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
	public List<ScheduleInformation> getCourseInformation() {
	try{
	 return template.query("select c.COURSE_NAME,f.FACULTYNAME,fc.SCHEDULE,fc.SCHEDULEID,fc.seats from faculty_courses fc,faculty f,courses c where fc.facultyid=f.facultyid and fc.courseid=c.course_id",new RowMapper<ScheduleInformation>(){  
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
	public String getUser(Login login) {
		String user=null;
		int result=0;
		try{
			String sql="select count(*) from admin where adminid=?";  
		    result=template.queryForObject(sql, new Object[]{login.getId()},Integer.class);  
		    if(result>0){
		    	user="admin";
		    }else{
			String sql1="select count(*) from faculty where facultyid=?";  
		    result=template.queryForObject(sql1, new Object[]{login.getId()},Integer.class);  
		    if(result>0){
		    	user="faculty";
		    }else{
		    String sql2="select count(*) from student_graduate where studentid=?";  
			result=template.queryForObject(sql2, new Object[]{login.getId()},Integer.class);  
			if(result>0){
				user="student";
			}
		    }
		   }
		}
			catch (EmptyResultDataAccessException e) {
		          return user;
		        }
		return user;
	}
	public Faculty getFaculty(Login facultylogin) {
		Faculty f=null;
		try{
		String sql="select * from faculty where facultyid=? and password=?";  
	    f=template.queryForObject(sql, new Object[]{facultylogin.getId(),facultylogin.getPassword()},new BeanPropertyRowMapper<Faculty>(Faculty.class));  
		}
		catch (EmptyResultDataAccessException e) {
	           System.err.println(e);
	        }
		System.out.println("my fac"+f);
		
			return f;
	}
	public List<List<String>> getSecurityQuestions() {
		try{
			 return template.query("select * from securityquestions",new RowMapper<List<String>>(){  
			        public List<String> mapRow(ResultSet rs, int row) throws SQLException {  
			        	List<String> secQuestions=new ArrayList<String>();  
			        	secQuestions.add(rs.getString(1));
			        	secQuestions.add(rs.getString(2));
			             return secQuestions;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return null;
	   // return template.queryForList("select * from securityquestions");
	}
	/*public Map<String,String> getSecurityQuestions() {
		try{
		template.queryForMap("select * from securityquestions", new ResultSetExtractor<Map<String,String>>(){
		    public Map<String,String> extractData(ResultSet rs) throws SQLException,DataAccessException {
		        HashMap<String,String> mapRet= new HashMap<String,String>();
		        while(rs.next()){
		            mapRet.put(rs.getString(1),rs.getString(2));
		        }
		        return mapRet;
		    }
		});
		}catch (EmptyResultDataAccessException e) {
	           System.err.println(e);
	        }
			return null;
		}*/
	public boolean changePassword(PasswordDetails passwordDetails, int studentId) {
		String sql="insert into passwordsecurity(studentid,question1,answer1,question2,answer2) values('"+studentId+"','"+passwordDetails.getSecurityQuestion1()+"','"+passwordDetails.getSecurityAnswer1()+"','"+passwordDetails.getSecurityQuestion2()+"','"+passwordDetails.getSecurityAnswer2()+"')";
		int i=template.update(sql);
		String sql1="update student_graduate set password='"+passwordDetails.getPassword()+"'"+" where studentid='"+studentId+"'";
		int j=template.update(sql1);
		if(i>0&&j>0)
			return true;
		return false;
		
	}
	public String getOldPassword(int studentId) {
		
		try{
			String sql="select password from student_graduate where studentid=?";  
		    return template.queryForObject(sql, new Object[]{studentId},new BeanPropertyRowMapper<String>(String.class));  
			}
			catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return null;
		
	}
	
}
