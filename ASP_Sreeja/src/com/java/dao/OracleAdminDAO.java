package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.java.bean.Faculty;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;
@Repository
public class OracleAdminDAO implements AdminDAO {
	JdbcTemplate template;  
	@Autowired  
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	}
	public List<Faculty> getAllfaculty() {
		
		try{
			 return template.query("select facultyid,facultyname,mailid from faculty where facultyid<>0 order by facultyname",new RowMapper<Faculty>(){  
			        public Faculty mapRow(ResultSet rs, int row) throws SQLException {  
			            Faculty f=new Faculty();  
			            f.setFacultyId(rs.getInt(1));  
			            f.setFacultyName(rs.getString(2));  
			            f.setMailId(rs.getString(3));  
			           
			            return f;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return null;
	}
	public int addCourse(ScheduleInformation course) {
		try{
			
		    final String sql="insert into courses(course_type,course_name,course_id) values('graduate'"+",'"+course.getCourseName()+"','CIS'||to_char(courseid_seq.NEXTVAL,'FM0999'))";    
		   /* Map<String,Object> bind = new HashMap<String, Object>(3);
	        bind.put("courseName", "graduate");
	        SqlParameterSource paramSource = new MapSqlParameterSource(bind);
	        KeyHolder keyHolder = new GeneratedKeyHolder();
	       // String[] columnNames = new String[] {"courseName"};
	        
	        int execute= this.template.update(sql, paramSource, keyHolder);
	        Map<String,Object> keys = keyHolder.getKeys();
	        String insertedCourseID = ((String)keys.get("courseName"));*/
	       /* Map<String,Object> keys = keyHolder.getKeys();
		    
		   // GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		  //  MapSqlParameterSource parameters = new MapSqlParameterSource();
		    
		    //NamedParameterJdbcTemplate tpl = new NamedParameterJdbcTemplate(getDataSource());
		   // String sql1 = "Insert into TABLE (ID,COL1,...) values (ID.NEXTVAL,:COL1,...)";
		   // GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		   // MapSqlParameterSource parameters = new MapSqlParameterSource();
		     
		   //return  template.update(sql, parameters, keyHolder, new String[]{"course_id"});
		    //long id = keyHolder.getKey().longValue();*/
		    KeyHolder keyHolder = new GeneratedKeyHolder();
	    	int execute=template.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement pst =
	    	                con.prepareStatement(sql, new String[] {"course_id"});
	    	           
	    	            return pst;
	    	        }
	    	    },
	    	    keyHolder);
	    	Map<String,Object> keys = keyHolder.getKeys();
	    	String insertedCourseID =String.valueOf(keys.values());
	    	insertedCourseID = insertedCourseID.substring(1, insertedCourseID.length() - 1);
	    	
	    	//int execute= template.update(sql);  
		   // String insertedCourseID = String.valueOf(keyHolder.getKey().longValue());
	    	String sql1="insert into faculty_courses(facultyid,courseid,schedule,scheduleid,seats) values(0,'"+insertedCourseID+"','TBA','S'||to_char(scheduleid_seq.NEXTVAL,'FM099'),"+course.getSeats()+")"; 
		    int execute1=template.update(sql1);
	    	
		    if(execute>0 && execute1>0)
		   return execute;
			}catch (EmptyResultDataAccessException e) {
	           System.err.println(e);
	        }
			return 0;
		
	}
	public int addFaculty(Faculty faculty) {
		HashMap<String,String> schedules = new HashMap<String, String>();
		List<String> scheduleValues=new ArrayList<String>();
		int increment=1;
		int countUpdate=0;
		String password=null;
		int s=0;
		try{
			String sql="select facultyid from faculty where facultyid=?";  
		   s= template.queryForObject(sql, new Object[]{faculty.getFacultyId()},Integer.class);  
		   return -1;
			}
			catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		           try{
		   			
						String first=faculty.getFacultyName().substring(0, Math.min(faculty.getFacultyName().length(), 4));
						String fId=String.valueOf(faculty.getFacultyId());
						String third=fId.substring(Math.max(0, fId.length() - 3), fId.length());
						 password=first+third;
						String scheduleID=faculty.getFacultyName().substring(0, Math.min(faculty.getFacultyName().length(), 2));
						
						for(String scheduleItem:faculty.getSchedules()){
							if(scheduleItem!=null){
								scheduleValues.add(scheduleItem);
							}
						 }
						
					
						for (String val : scheduleValues) {
							schedules.put(scheduleID+increment, val);
							increment++;
						}
						
						
						 String sql="insert into faculty(facultyid,facultyname,mailid,password) values('"+faculty.getFacultyId()+"','"+faculty.getFacultyName()+"','"+faculty.getMailId()+"','"+password+"')";    
						    int execute1= template.update(sql);
						    String sql2=null;
						    for(String eachItem: schedules.keySet()){
							     sql2="insert into faculty_schedule(facultyid,schedule,sid) values('"+faculty.getFacultyId()+"','"+schedules.get(eachItem)+"','"+eachItem+"')";
							     int execute2=template.update(sql2);
							     if(execute2>0)
							    	 countUpdate++;
						    }
						    	if(execute1>0 && countUpdate>0)        
						    		return 5;
							}catch (EmptyResultDataAccessException exp) {
					           System.err.println(exp);
					           return 0;
					        }catch(Exception ex){
					        	System.err.println(ex);
					        	return 0;
					        }
			return 0;
		        }
	
	}  
	public List<ScheduleInformation> getCourseInformation() {
		try{
		 return template.query("select c.COURSE_NAME,f.FACULTYNAME,fc.SCHEDULE,fc.SCHEDULEID,fc.seats from faculty_courses fc,faculty f,courses c where fc.facultyid=f.facultyid and fc.courseid=c.course_id order by c.COURSE_NAME",new RowMapper<ScheduleInformation>(){  
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
	public List<Student> viewStudents() {
		try{
			 return template.query("select studentid,firstname,lastname,mailid from student_graduate order by firstname",new RowMapper<Student>(){  
			        public Student mapRow(ResultSet rs, int row) throws SQLException {  
			            Student student=new Student();  
			            student.setStudentId(rs.getInt(1));  
			            student.setFirstName(rs.getString(2));  
			            student.setLastName(rs.getString(3));  
			            student.setMailId(rs.getString(4));
			            
			            return student;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
		return null;
	}
	public List<ScheduleInformation> getAvailableSchedules(int facultyId) {
		try{
			/*select distinct fs.schedule,fs.sid from faculty_schedule fs where 
			fs.schedule not in(select schedule from faculty_courses where facultyid='100159753') and facultyid='100159753'*/
			 return template.query("select distinct fs.schedule,fs.sid from faculty_schedule fs where fs.schedule not in(select schedule from faculty_courses where facultyid='"+facultyId+"')"+ 
			 		"and fs.facultyid='"+facultyId+"'",new RowMapper<ScheduleInformation>(){  
			        public ScheduleInformation mapRow(ResultSet rs, int row) throws SQLException {  
			            ScheduleInformation scheduleInformation=new ScheduleInformation();  
			           	scheduleInformation.setSchedule((rs.getString(1)));  
			            scheduleInformation.setScheduleId(rs.getString(2));
			            
			            return scheduleInformation;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return null;
	}
	public List<String> getCourses() {
		try{
			 return template.query("select course_name from courses",new RowMapper<String>(){  
			        public String mapRow(ResultSet rs, int row) throws SQLException {  
			            String course=rs.getString(1);  
			           
			            return course;  
			        }  
			    });  
			}catch (EmptyResultDataAccessException e) {
		           System.err.println(e);
		        }
				return null;
	}
	public int assignCoursetoFaculty(String courseName, String schedule,
			int facultyId) {
		int assign=0;
		try{
			String sql="select c.course_id from courses c,faculty_courses fc where c.course_id=fc.courseid and c.course_name=? and fc.schedule='TBA'";
			//String courseId=template.queryForObject(sql, new Object[]{},new BeanPropertyRowMapper<String>(String.class));  
			
			String courseId = (String)template.queryForObject(
					sql, new Object[] { courseName }, String.class);
			
			if(courseId!=null){
				
				String updateStatement = " UPDATE faculty_courses"
                        + " SET facultyid=?, schedule=? "
                        + " WHERE courseid=?";
				 assign=template.update(updateStatement, new Object[] {facultyId, schedule,courseId});
				
			}
			}catch (EmptyResultDataAccessException e) {
				try{
					
					
					String sql2="select distinct fc.seats from courses c,faculty_courses fc where c.course_id=fc.courseid and c.course_name=?";
					int seats=template.queryForObject(sql2, new Object[]{courseName},Integer.class);  
					String sql="select course_id from courses where course_name=?";
					
					String courseId = (String)template.queryForObject(
							sql, new Object[] { courseName }, String.class);
					
					
						
					List<String>	scheduleExists=template.query("select schedule from faculty_courses where facultyid='"+facultyId+"' and courseid='"+courseId+"' and schedule='"+schedule+"'",new RowMapper<String>(){  
					        public String mapRow(ResultSet rs, int row) throws SQLException {  
					            String schedulefromDB=rs.getString(1);  
					           
					            return schedulefromDB;  
					        }  
					    });  
						/*String checkQuery="select schedule from faculty_courses where facultyid='"+facultyId+"' and courseid='"+courseId+"'";
						String scheduleExists = (String)template.queryForObject(
								checkQuery, new Object[] {}, String.class);*/
						System.out.println("scheduleExists"+scheduleExists.size());

					if(scheduleExists.size()==0){
						String insertStatement="insert into faculty_courses(facultyid,courseid,schedule,scheduleid,seats) values('"+facultyId+"','"+courseId+"','"+schedule+"','S'||to_char(scheduleid_seq.NEXTVAL,'FM099'),"+seats+")";    
					    assign= template.update(insertStatement); 
					}
						 
				
				}catch(EmptyResultDataAccessException exception) {
					System.err.println(exception);
				}
				
	           
	        }
	        	return assign;
	      
		
	}
	
}
