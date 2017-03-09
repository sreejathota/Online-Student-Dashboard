package com.java.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.java.bean.Communication;
import com.java.bean.ScheduleInformation;
import com.java.bean.Faculty;
import com.java.bean.Student;

import com.java.service.FacultyService;

@Controller
@SessionAttributes(value = {"faculty"}, types = {Faculty.class})
public class FacultyController {
	@Autowired  
    FacultyService fs;
	
	 @RequestMapping(value="/facultylogin")  
	 public ModelAndView facultylogin(Model model) {
		// Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		Faculty sessionFaculty= (Faculty) model.asMap().get("faculty");
		
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.setViewName("facultypage");
	     modelAndView.addObject("faculty", sessionFaculty);
	     
	     return modelAndView;
	 }
	 
	 
	 @RequestMapping("/viewstudents")  
	    public ModelAndView viewstudents(@ModelAttribute("faculty") Faculty faculty){  
	       List<ScheduleInformation> scheduleIds=fs.getScheduleIds(faculty.getFacultyId());
	       ModelAndView modelAndView = new ModelAndView();
	       modelAndView.setViewName("fviewcourses");
		     
	        modelAndView.addObject("scheduleIds", scheduleIds);
	        return modelAndView;  
	    } 
	 
	 @RequestMapping(value="/viewenrolledstudent/{scheduleId}")  
	    public ModelAndView viewenrolledstudent(@PathVariable String scheduleId){  
		
		 List<Student> student=fs.viewenrolledstudent(scheduleId);
		
		 ModelAndView modelAndView = new ModelAndView();
	      modelAndView.setViewName("fviewstudents");
	    
	      modelAndView.addObject("student", student);
	      if(!(student.size()>0))
	    	 modelAndView.addObject("nooneenrolled", "No students enrolled in this course");
		    return modelAndView;
		  
	    }  
	 @RequestMapping(value="/reply/{studentId}")  
	    public ModelAndView replytoStudent(@PathVariable int studentId,@ModelAttribute("faculty") Faculty faculty,@ModelAttribute Communication comm){  
		
		 Boolean b=fs.replytoStudent(studentId,faculty.getFacultyId(),comm);
		 List<Communication> messages=fs.viewmessages(faculty);
			
		
	     
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.addObject("messages", messages);
	      modelAndView.setViewName("fviewmessages");
	      if(b==true)
	 	     modelAndView.addObject("communicated", "Message successfully sent");
	 	      else
	 	    	  modelAndView.addObject("communicated", "Unable to send Message");
	 		    return modelAndView;
	    /*  modelAndView.addObject("student", student);
	      if(!(student.size()>0))
	    	 modelAndView.addObject("nooneenrolled", "No students enrolled in this course");*/
		    
		  
	    } //replypage
	 @RequestMapping(value="/replypage/{studentId}")  
	    public ModelAndView replyPage(@PathVariable int studentId,@ModelAttribute("faculty") Faculty faculty){  
		
		Student student=fs.getStudentDetails(studentId);
		ModelAndView modelAndView = new ModelAndView("freplystudents","c",new Communication());
	    modelAndView.addObject("student", student);
	      
		    return modelAndView;
		  
	    } 
	 @RequestMapping(value="/viewmycourseschedule")  
	    public ModelAndView viewmycourseschedule(@ModelAttribute("faculty") Faculty faculty){  
		
		 List<ScheduleInformation> courseschedule=fs.viewmycourseschedule(faculty);
		
		 ModelAndView modelAndView = new ModelAndView();
	      modelAndView.setViewName("fviewcourseschedule");
	    if(courseschedule.size()>0)
	      modelAndView.addObject("courseschedule", courseschedule);
	    else
	    	 modelAndView.addObject("nocourses", "No Courses assigned to you this semester");
		    return modelAndView;
		  
	    } 
	 @RequestMapping(value="/viewmessages")  
	    public ModelAndView viewmessages(@ModelAttribute("faculty") Faculty faculty){  
		
		 List<Communication> messages=fs.viewmessages(faculty);
		
		 ModelAndView modelAndView = new ModelAndView();
	      modelAndView.setViewName("fviewmessages");
	      modelAndView.addObject("messages", messages);
	   /* if(courseschedule.size()>0)
	      modelAndView.addObject("courseschedule", courseschedule);
	    else
	    	 modelAndView.addObject("message", "No Courses assigned to you this semester");
		    return modelAndView;*/
		  return modelAndView;
	    }
	
}
