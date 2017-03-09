package com.java.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.java.bean.Communication;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;
import com.java.service.StudentService;
@Controller
@SessionAttributes(value = {"student","scheduleIdforfaculty","enrolledCourses","messages"}, types = {Student.class,String.class,ScheduleInformation.class,Communication.class})
public class StudentController {
	 @Autowired  
	    StudentService ss;
	
	 @RequestMapping(value="/studentlogin")  
	 public ModelAndView studentlogin(Model model) {
		// Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		Student sessionStudent= (Student) model.asMap().get("student");
		@SuppressWarnings("unchecked")
		//List<ScheduleInformation> sessionfilteredcourseInformation= ss.getFilteredCourseInformation(sessionStudent.getStudentId());
		List<ScheduleInformation> sessionfilteredcourseInformation= (List<ScheduleInformation>) model.asMap().get("filteredcourseInformations");
		 ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("studentenrol");
	        modelAndView.addObject("student", sessionStudent);
	        modelAndView.addObject("filteredcourseInformations", sessionfilteredcourseInformation);
	        return modelAndView;
	 }
	 
	 @RequestMapping(value="/gobackstudentenroll")  
	    public ModelAndView goBackStudentEnroll(@ModelAttribute("student") Student student){  
	       
	       List<ScheduleInformation> filteredcourseInformation=ss.getFilteredCourseInformation(student.getStudentId());
	       ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("studentenrol");
	       
	        modelAndView.addObject("filteredcourseInformations", filteredcourseInformation);
	        
	        return modelAndView;
	       
	    } 
	 
	
	 @RequestMapping(value="/enrolcourse/{scheduleId}")  
	 public ModelAndView studentenroll(@PathVariable String scheduleId,@ModelAttribute("student") Student student1){  
		String updated=ss.studentEnrol(scheduleId,student1);
		 List<ScheduleInformation> filteredcourseInformation=ss.getFilteredCourseInformation(student1.getStudentId());
		 
		 ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("studentenrol");
	       // modelAndView.addObject("student", student1);
	        modelAndView.addObject("filteredcourseInformations", filteredcourseInformation);
	        modelAndView.addObject("updatedscheduleId", scheduleId);
	        if("notavailable".equalsIgnoreCase(updated))
	        modelAndView.addObject("message", "Sorry... seats not available");
	        else if("alreadyenrolled".equalsIgnoreCase(updated))
		        modelAndView.addObject("message", "you have already enrolled to this course");
	        else if("sameDay".equalsIgnoreCase(updated))
		        modelAndView.addObject("message", "Sorry!! You have enrolled to another course at this time");
	        else if("exceeded".equalsIgnoreCase(updated))
		        modelAndView.addObject("message", "you have enrolled to 3 courses");
	        else if("inserted".equalsIgnoreCase(updated)){
		        modelAndView.addObject("message", "sucessfully enrolled to the course");
	        modelAndView.addObject("updated", "inserted");
	        }
		    return modelAndView;
		  
	    }  
	 @RequestMapping(value="/dropcourse/{scheduleId}")  
	    public ModelAndView coursedrop(@PathVariable String scheduleId,@ModelAttribute("student") Student student1){  
		
		int updated=ss.courseDrop(scheduleId,student1);
		 List<ScheduleInformation> filteredCourseInformation=ss.getFilteredCourseInformation(student1.getStudentId());
		 ModelAndView modelAndView = new ModelAndView();
	      modelAndView.setViewName("studentenrol");
	    //  modelAndView.addObject("student", student1);
	      modelAndView.addObject("filteredcourseInformations", filteredCourseInformation);
	      modelAndView.addObject("updatedscheduleId", scheduleId);
	      if(updated>0)
	      modelAndView.addObject("message", "Course dropped successfully");
		    return modelAndView;
		  
	    }  
	 @RequestMapping(value="/contactfaculty/{scheduleId}")  
	    public ModelAndView contactFaculty(@PathVariable String scheduleId,@ModelAttribute("student") Student student){  
		 
		String faculty=ss.getFacultyName(scheduleId);
		//Communication c=new Communication();
		 ModelAndView modelAndView = new ModelAndView("scontactfaculty","c",new Communication());
		 //return new ModelAndView("SpringMVC_TextAreaExample","ta",new TextAreaBean());
	      modelAndView.setViewName("scontactfaculty");
	      modelAndView.addObject("scheduleId", scheduleId);
	      modelAndView.addObject("faculty", faculty);
	     // modelAndView.addObject("c", c);
	     // modelAndView.addObject("scontactfaculty", new Communication());
	      return modelAndView;  
		  
	    }
	 @RequestMapping(value="/sendmessage/{scheduleId}")  
	    public ModelAndView sendmessage(@PathVariable String scheduleId,@ModelAttribute("student") Student student,@ModelAttribute("c") Communication comm){  
		 Boolean b=ss.sendMessageToFaculty(student,scheduleId,comm);
		 ModelAndView modelAndView = new ModelAndView();
	      modelAndView.setViewName("sviewmyenrollment");
	      if(b==true)
	     modelAndView.addObject("communicated", "Message successfully sent");
	      else
	    	  modelAndView.addObject("communicated", "Unable to send Message");
		    return modelAndView;
		  
	    }
	 @RequestMapping(value="/viewmyenrollment")  
	    public ModelAndView viewmyenrollment(@ModelAttribute("student") Student student1,
	            final Model model){  
		
		 List<ScheduleInformation> enrolledCourses=ss.getEnrolledCourses(student1.getStudentId());
		
		 ModelAndView modelAndView = new ModelAndView();
	      modelAndView.setViewName("sviewmyenrollment");
	     // modelAndView.addObject("student", student1);
	      modelAndView.addObject("enrolledCourses", enrolledCourses);
	    if(!(enrolledCourses.size()>0))
	      modelAndView.addObject("information", "You have not enrolled any courses");
		    return modelAndView;
	    }  
	    @RequestMapping(value="/viewmymessages")  
	    public String viewmymessages(@ModelAttribute("student") Student student1,
	            final Model model){  
	    	List<Communication> messages=ss.viewmessages(student1);
	    	if(messages==null)
	    		model.addAttribute("noMessages", "You have no messages from faculty");
	    	model.addAttribute("messages", messages);
		
		    return "sViewMessages";
	    }  
	    @RequestMapping(value="/viewMessageInPage/{studentName}")  
	    public String viewMessageInPage(@PathVariable String studentName,@ModelAttribute("student") Student student1,
	            final Model model,@ModelAttribute("messages") List<Communication> messages){  
	    	for(Communication message:messages){
	    		if(message.getStudentName().equalsIgnoreCase(studentName)){
	    			model.addAttribute("message", message);
	    		    return "sViewMessageInPage";
	    		}
	    	}
	    	return null;
	
	    }
	 @RequestMapping(value="/emailconfirmation")  
	    public ModelAndView emailConfirmation(@ModelAttribute("student") Student student){  
	       
		 //List<CourseInformation> enrolledCourses=ss.getEnrolledCourses(student.getStudentId());
		 Boolean b=ss.sendEmail(student.getStudentId());
	       ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("redirect:/viewmyenrollment");
	        if(b==true)
	        modelAndView.addObject("confirmation", "Email sent sucessfully");
	        else
		    modelAndView.addObject("confirmation", "Unable to send Email");

	        return modelAndView;
	       
	    } 
	   
	   
}
