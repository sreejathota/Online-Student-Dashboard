 
 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
       <%! @SuppressWarnings("rawtypes") %> 
<!DOCTYPE html>
<html>
<head>
<title>Student Registration Form</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="shortcut icon" href=http://www.freshdesignweb.com/wp-content/themes/fv28/images/icon.ico />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/registerstyle.css" />">
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/registerstyle2.css" />" >
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/registerstyle3.css" />" >
   <c:url value="/resources/images" var="images" />
    
</head>
<body <%-- BACKGROUND=<img src="${images}/bg.png"/> --%>>
 <div class="container"><div class="freshdesignweb-top"><h1>Registration</h1></div><div class="clr"></div>
  <!-- freshdesignweb top bar -->
            <div class="freshdesignweb-buttom">
                <a href="index.jsp">Home</a>
                <span class="right">
                    <a href="loginform">
                        Login Page
                    </a>
                </span>
                <div class="clr"></div>
            </div><!--/ freshdesignweb top bar -->
        
			<header>
				 <h1><strong>Student Registration Form</strong><span>for enrollment</span></h1>
            </header>     

           <div  class="form">
           
        <form:form action="register" method="post" modelAttribute="student" id="contactform">
        <h4>${exists}</h4>
            		<p class="contact"><label for="name">Student ID</label></p>
                    <form:input path="studentId" id="name" name="name" placeholder="Student ID" required="" tabindex="1" type="text"/>
                    <form:errors path="studentId" />
                    
                    <p class="contact"><label for="username">First Name</label></p> 
    				  <form:input path="firstName" id="fname" name="username" placeholder="firstname" required="" type="text"/>
                    <form:errors path="firstName" />
                    
                     <p class="contact"><label for="username">Last Name</label></p> 
    			<form:input path="lastName" id="username" name="username" placeholder="lastname" required="" tabindex="2" type="text"/>
    			<form:errors path="lastName" />
                
                 <p class="contact"><label for="email">Mail ID</label></p> 
    				  <form:input path="mailId" id="email" name="email" placeholder="Mail ID" required="" type="text"/>
                    <form:errors path="mailId" />
               
               <input class="buttom" name="submit" id="submit" tabindex="5" value="Register" type="submit"> 	 
               
               
        </form:form>
        </div>       
</div>
        
    
</body>
</html>
