package com.java.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.bean.Admin;
import com.java.bean.Faculty;
import com.java.bean.Login;
import com.java.bean.PasswordDetails;
import com.java.bean.ScheduleInformation;
import com.java.bean.Student;
import com.java.service.LoginService;
import com.java.service.StudentService;
import com.java.validator.ChangePasswordValidator;
import com.java.validator.LoginValidator;
import com.java.validator.StudentRegisterValidator;

@Controller
@SessionAttributes(value = {"q1","q2","studentId"}, types = {String.class,String.class,Integer.class})
public class LoginController {
	@Autowired  
    LoginService ls;
	@Autowired  
    StudentService ss;
	@Autowired
	LoginValidator lv;
	 @Autowired
		StudentRegisterValidator srv;
	 @Autowired
		ChangePasswordValidator cpv;
	 

	@RequestMapping("/loginform")  
    public ModelAndView loginform(){  
        return new ModelAndView("Login","login",new Login());  
    } 
	@RequestMapping("/index")  
    public String index(){  
        return "index";  
    } 
	 @RequestMapping("/registerform")  
	    public ModelAndView registerform(){  
	        return new ModelAndView("registerform","student",new Student());  
	    } 
	 @RequestMapping(value="/register",method = RequestMethod.POST)  
	  public String register(@Valid@ModelAttribute("student") Student student,BindingResult result,Model model){  
	    	srv.validate(student, result);
			if (result.hasErrors()) {
			    return "/registerform";
			    }
			int sId=ls.getExistingStudent(student.getStudentId());
			if(sId!=0){
				model.addAttribute("exists", "Student already registered");
				return "/registerform";
				  
				
			}else{
				ls.register(student);
			}
	      //ModelAndView modelAndView=new ModelAndView();
	       // model.addAttribute("studentId",student.getStudentId());
	       // return "redirect:/changepassword";
	        return "redirect:/loginform";
	    } 
	 
	/* @RequestMapping("/changepasswordsuccess")  
	    public String changepasswordsuccess(@Valid@ModelAttribute("passworddetails") PasswordDetails passwordDetails,@ModelAttribute("studentId") int studentId,BindingResult result,final Model model){  
	    	
		 boolean b=false;
		 cpv.validate(passwordDetails, result);
	    	
			if (result.hasErrors()) {
			
			    return "changePassword";
			    }
			String oldPassword=ls.getOldPassword(studentId);
			System.out.println("i am"+oldPassword);
			
			if(oldPassword.equals(passwordDetails.getOldPassword())){
				b=ls.changePassword(passwordDetails,studentId); 
				if(b==true)
			        return "redirect:/loginform";
				else{
					model.addAttribute("oldPasswordError","Unable to change password");
					return "changePassword";
				}
			}
	        else{
	        	 model.addAttribute("oldPasswordError","Your old password is invalid");
	        	 return "changePassword";
	        }
	      
	    } 
	 @RequestMapping("/changepassword")  
	 //@ModelAttribute("q1")
	    public ModelAndView changePassword(@ModelAttribute("studentId") int studentId){  
		 List<List<String>> sQ= ls.getSecurityQuestions();
		 List<String> q1=new ArrayList<String>();
		 List<String> q2=new ArrayList<String>();
		  for(List<String> secQues:sQ){
			  if("Q1".equalsIgnoreCase(secQues.get(0)))
			  q1.add(secQues.get(1));
			  else if("Q2".equalsIgnoreCase(secQues.get(0))){
			  q2.add(secQues.get(1));
			  }
		  }
		 
		  ModelAndView modelAndView=new ModelAndView("changePassword","passworddetails",new PasswordDetails());
		  modelAndView.addObject("q1", q1);
		  modelAndView.addObject("q2", q2);
		  modelAndView.addObject("studentId", studentId);
		 return modelAndView;
	    } */
	    
	@RequestMapping("/logout")  
    public String logout(Model model,SessionStatus status){ 
		model.asMap().clear();
		status.setComplete();
        return "redirect:/loginform";  
    } 
	 @RequestMapping(value="/login",method = RequestMethod.POST)  
	    public String login(@Valid@ModelAttribute Login login,BindingResult result,
	     final Model model, RedirectAttributes redirectAttributes){ 
		 String page="redirect:/loginform";
		lv.validate(login, result);
		if (result.hasErrors()) {
		 /*if (b) {*/
		    return "/Login";
		    }
		   String user=ls.getUser(login);
		   if(user==null){
			   model.addAttribute("err", "Please enter valid User ID");
			   return "/Login";
		   }else if("admin".equalsIgnoreCase(user)){
			Admin admin=ls.getAdmin(login);
			if(admin==null){
				   model.addAttribute("err","Invalid UserID or Password");
				   page="/Login";
			   }else{
			   redirectAttributes.addFlashAttribute("admin", admin);
			   page="redirect:/adminController/adminlogin";
			   }
		   }else if("faculty".equalsIgnoreCase(user)){
			   Faculty faculty=ls.getFaculty(login);
			   if(faculty==null){
				   model.addAttribute("err","Invalid UserID or Password");
				   page="/Login";
			   }else{
			   redirectAttributes.addFlashAttribute("faculty", faculty);
			   page="redirect:facultylogin";
			   }
		   }
		   else if("student".equalsIgnoreCase(user)){
			   Student student1=ls.getStudent(login);
			   if(student1==null){
				   model.addAttribute("error","Invalid UserID or Password");
				   page="/Login";
			   }else{
		       List<ScheduleInformation> filteredcourseInformation=ls.getFilteredCourseInformation(login.getId());
		        redirectAttributes.addFlashAttribute("student", student1);
		        redirectAttributes.addFlashAttribute("filteredcourseInformations", filteredcourseInformation);
		        page="redirect:studentlogin";
			   }
		       // modelAndView.addObject("student", student1);
		        //modelAndView.addObject("filteredcourseInformations", filteredcourseInformation);
		        //modelAndView.addObject("enrolledCourses", enrolledCourses);
		   }
	       
	        return page;
	       
	    }  
	
}
