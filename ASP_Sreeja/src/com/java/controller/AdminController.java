package com.java.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.support.PagedListHolder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.java.bean.Admin;
import com.java.bean.Communication;
import com.java.bean.Faculty;
import com.java.bean.PasswordDetails;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;
import com.java.service.AdminService;
import com.java.validator.AdminAddCourseValidator;
import com.java.validator.AdminAddFacultyValidator;
import com.java.validator.AdminAssignCourseValidator;


@Controller
@SessionAttributes(value = {"admin","facultyId","scheduleValues"}, types = {Admin.class,String.class,String.class})
@RequestMapping("/adminController")
public class AdminController {
	 @Autowired  
	    AdminService as;
	 @Autowired
		AdminAddCourseValidator adminValidator1;
	 @Autowired
		AdminAddFacultyValidator adminValidator2;
	 @Autowired
		AdminAssignCourseValidator adminValidator3;
	 @RequestMapping(value="/adminlogin")  
	 public ModelAndView adminlogin(Model model) {
		// Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		Admin sessionAdmin= (Admin) model.asMap().get("admin");
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.setViewName("adminPage");
	     modelAndView.addObject("admin", sessionAdmin);
	     return modelAndView;
	 }
	 
	 @RequestMapping(value="/addCoursePage")  
	 public ModelAndView addCourseByAdmin() {
		// List<String> allFaculty=as.getAllFaculty();
		 ModelAndView modelAndView=new ModelAndView("addCoursePage","course",new ScheduleInformation());
		 //modelAndView.addObject("facultyNames", allFaculty);
		 return modelAndView;  
	 }
	 
	 
	 @RequestMapping(value="/addCourseSuccess",method = RequestMethod.POST)  
	 public String addCourseSuccess(@Valid@ModelAttribute("course") ScheduleInformation course,BindingResult result,Model model) {
		
		 adminValidator1.validate(course, result);
		 if (result.hasErrors()) {
			    return "/addCoursePage";
			    }
		 int added=as.addCourse(course);
		if(added>0){
			model.addAttribute("course", "Course added successfully");

			return "addCoursePage";
		}else{
			model.addAttribute("course", "Sorry!! unable to add the course");
			return "addCoursePage";
		}
		 
	 }
	 @RequestMapping(value="/addfaculty")  
	 public ModelAndView addFacultyByAdmin() {
		 ModelAndView modelAndView=new ModelAndView("addFacultyPage","addFaculty",new Faculty());
		 List<String> scheduleValues=new ArrayList<String>();
			scheduleValues.add("M 9:00-11:00");
			scheduleValues.add("T 6:00-9:00");
			scheduleValues.add("W 9:00-11:00");
			scheduleValues.add("Th 6:00-9:00");
			scheduleValues.add("Th 1:00-3:45");
			modelAndView.addObject("scheduleValues", scheduleValues);
		 return modelAndView;
	 }
	 @RequestMapping(value="/addFacultySuccess")  
	 public String addFacultySuccess(@Valid@ModelAttribute("addFaculty") Faculty addFaculty,BindingResult result,Model model) {
		 adminValidator2.validate(addFaculty, result);
		 if (result.hasErrors()) {
			    return "addFacultyPage";
			    }
		 
		int added=as.addFaculty(addFaculty);
		if(added>0){
			
				model.addAttribute("faculty", "Faculty added successfully");
				//model.addAttribute("scheduleValues", scheduleValues);
				return "addFacultyPage";
		
		}else{
			if(added==-1){
				model.addAttribute("faculty", "Sorry!! Faculty already added");
			}else{
				model.addAttribute("faculty", "Sorry!! unable to add the faculty");

			}
			return "addFacultyPage";
		}
		 
	 }
	 
	 @RequestMapping(value="/viewfaculty")  
	 public ModelAndView viewFacultyByAdmin(@RequestParam(required = false) Integer page) {
		 List<Faculty> facultyDetails=as.getAllFaculty();
		 
		 ModelAndView modelAndView=new ModelAndView("viewFacultyPage");
		 //modelAndView.clear();
		 PagedListHolder<Faculty> pagedListHolder = new PagedListHolder<Faculty>(facultyDetails);
		 pagedListHolder.setPageSize(6);
		 pagedListHolder.resort();
	     modelAndView.addObject("maxPages", pagedListHolder.getPageCount());
	     if(page==null || page < 1 || page > pagedListHolder.getPageCount())page=1;

	        modelAndView.addObject("page", page);
	        if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
	            pagedListHolder.setPage(0);
	            modelAndView.addObject("displayFaculty", pagedListHolder.getPageList());
	        }
	        else if(page <= pagedListHolder.getPageCount()) {
	            pagedListHolder.setPage(page-1);
	            modelAndView.addObject("displayFaculty", pagedListHolder.getPageList());
	        }

		 
		// modelAndView.addObject("displayFaculty", facultyDetails);
		 return modelAndView;
	 }
	 
	 @RequestMapping(value="/assignCourse/{facultyId}")  
	    public ModelAndView assignCourse(@PathVariable int facultyId,Model model){  
		 List<ScheduleInformation> availableSchedule= as.getAvailableSchedules(facultyId);
		 List<String> courses=as.getCourses();
		if(availableSchedule.size()>0){
			 ModelAndView modelAndView = new ModelAndView("assignCourseToFaculty","assignCourse",new ScheduleInformation());
				modelAndView.addObject("facultyId", facultyId);
			      modelAndView.addObject("availableSchedule", availableSchedule);
			      modelAndView.addObject("courses", courses);
			      return modelAndView;
		}
		else{
			 ModelAndView modelAndView = new ModelAndView("redirect:/adminController/viewfaculty");
			 modelAndView.addObject("errormessage", "No schedule available for this faculty");
			return modelAndView;
		}
	      
	    }
	 @RequestMapping(value="/assignCourseSuccess")  
	    public String assignCourseSuccess(@Valid@ModelAttribute("assignCourse") ScheduleInformation assignCourse,
	    		BindingResult result,@ModelAttribute("facultyId") int facultyId,Model model){  
		 adminValidator3.validate(assignCourse, result);
		 List<ScheduleInformation> availableSchedule= as.getAvailableSchedules(facultyId);
		 List<String> courses=as.getCourses();
		 model.addAttribute("availableSchedule", availableSchedule);
			model.addAttribute("courses", courses);
		 if (result.hasErrors()) {
			    return "assignCourseToFaculty";
			    }
		 int assign= as.assignCoursetoFaculty(assignCourse.getCourseName(),assignCourse.getSchedule(),facultyId);
		 
		 if(assign>0){
		
			model.addAttribute("assign", "Course assigned successfully");
			List<ScheduleInformation> availableSchedule1= as.getAvailableSchedules(facultyId);
			model.addAttribute("availableSchedule", availableSchedule1);
			if(availableSchedule.size()>0)
			return "assignCourseToFaculty";
			else{
				 //ModelAndView modelAndView = new ModelAndView("/viewfaculty");
				
				return "redirect:viewfaculty";
			}
		}

		else{
			model.addAttribute("assign", "Sorry!! unable to assign the course");
			
		      return "assignCourseToFaculty"; 
		}
			 
	      
	    }
	 @RequestMapping(value="/viewcourseinformation")  
	 public ModelAndView viewCourseInformation(@RequestParam(required = false) Integer page) {
		 List<ScheduleInformation> courseDetails=as.getCourseInformation();
		 ModelAndView modelAndView=new ModelAndView("viewCoursePage");
		 PagedListHolder<ScheduleInformation> pagedListHolder = new PagedListHolder<ScheduleInformation>(courseDetails);
		 pagedListHolder.setPageSize(10);
		 pagedListHolder.resort();
	     modelAndView.addObject("maxPages", pagedListHolder.getPageCount());
	     if(page==null || page < 1 || page > pagedListHolder.getPageCount())page=1;

	        modelAndView.addObject("page", page);
	        if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
	            pagedListHolder.setPage(0);
	            modelAndView.addObject("displayCourses", pagedListHolder.getPageList());
	        }
	        else if(page <= pagedListHolder.getPageCount()) {
	            pagedListHolder.setPage(page-1);
	            modelAndView.addObject("displayCourses", pagedListHolder.getPageList());
	        }

		 
		// modelAndView.addObject("displayCourses", courseDetails);
		 return modelAndView;
	 }
	 @RequestMapping(value="/viewstudents")  
	 public ModelAndView viewStudentsByAdmin(@RequestParam(required = false) Integer page) {
		 List<Student> studentDetails=as.viewStudents();
		 ModelAndView modelAndView=new ModelAndView("viewStudentsPage");
		 PagedListHolder<Student> pagedListHolder = new PagedListHolder<Student>(studentDetails);
		 pagedListHolder.setPageSize(10);
		 pagedListHolder.resort();
	     modelAndView.addObject("maxPages", pagedListHolder.getPageCount());
	     if(page==null || page < 1 || page > pagedListHolder.getPageCount())page=1;

	        modelAndView.addObject("page", page);
	        if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
	            pagedListHolder.setPage(0);
	            modelAndView.addObject("displayStudent", pagedListHolder.getPageList());
	        }
	        else if(page <= pagedListHolder.getPageCount()) {
	            pagedListHolder.setPage(page-1);
	            modelAndView.addObject("displayStudent", pagedListHolder.getPageList());
	        }
		 
		 return modelAndView;
	 }
}
