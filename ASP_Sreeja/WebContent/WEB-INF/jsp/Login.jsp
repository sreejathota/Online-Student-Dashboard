<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%! @SuppressWarnings("rawtypes") %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login Page</title>
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<body>
<form:form action="login" method="post" modelAttribute="login" id="login" >
				
    <h1>Log In</h1>
    <fieldset id="inputs">
       <h4>${err}</h4>
       <%-- <h4>${invaliduserid }</h4> --%>
        <form:input path="id" id="username" placeholder="UserId" autofocus="autofocus" required="required"/>     
        <form:errors path="id" /> 
        <form:password path="password" id="password" placeholder="Password" required="required"/>
        <form:errors path="password" />
    </fieldset>
    <fieldset id="actions">
        <input type="submit" id="submit" value="Log in">
       <a href="registerform">Student Registration</a>
    </fieldset>
       <a href="index.jsp" id="back">Back to Home</a>
    </form:form>


</body>
</html>
