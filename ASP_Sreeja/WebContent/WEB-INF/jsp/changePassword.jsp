<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%! @SuppressWarnings("rawtypes") %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Change Password</title>

</head>

<body>
<form:form action="changepasswordsuccess" method="post" modelAttribute="passworddetails">
				
   <h1>Change Password</h1>
   <p>${oldPasswordError}</p>
		<table>
		
        <tr><td><form:input path="oldPassword" id="oldPassword" placeholder="Old Password" autofocus="autofocus" required="required"/> </td>    
        <td><form:errors path="oldPassword" /></td> </tr>
        <tr><td><form:password path="password" id="password" placeholder="Enter Password" required="required"/></td>
        <td><form:errors path="password" /></td></tr>
        <tr><td><form:password path="confirmPassword" id="confirmPassword" placeholder="Confirm Password" required="required"/></td>
        <td><form:errors path="confirmPassword" /></td></tr>
        <tr><td><form:select path="securityQuestion1">
					  <form:option value="NONE" label="--- Select Security Question 1---" />
					  <form:options items="${q1}" />
				       </form:select></td>
				 <td><form:errors path="securityQuestion1" /></td></tr>
	  <tr><td><form:input path="securityAnswer1" id="securityAnswer1" placeholder="Answer" autofocus="autofocus" required="required"/></td> 
        <td><form:errors path="securityAnswer1" /> 	</td></tr>       
	  <tr><td><form:select path="securityQuestion2">
					  <form:option value="NONE" label="--- Select Security Question 2---" />
					  <form:options items="${q2}" />
				       </form:select></td>
				     <td>  <form:errors path="securityQuestion2" /></td></tr>
	  <tr><td><form:input path="securityAnswer2" id="securityAnswer2" placeholder="Answer" autofocus="autofocus" required="required"/>    </td> 
       <td> <form:errors path="securityAnswer2" /> </td></tr>
   
       <tr><td> <input type="submit" id="submit" value="Change Password"></td>
        <td><a href="">Forgot your password?</a></td><td><a href="registerform">Student Registration</a></td></tr>
   
       <tr><td><a href="index.jsp" id="back">Back to Home</a></td></tr>
       </table>
    </form:form>
</body>
</html>
