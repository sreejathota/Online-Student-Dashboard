<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty Login</title>
</head>
<body>
 
      <div align="center">
        <form:form action="facultylogin" method="post">
            <table border="0">
                <tr>
                    <td colspan="2" align="center"><h2>Faculty Login</h2></td>
                </tr>
                <tr>
                    <td>Faculty ID</td>
                    <td><form:input path="facultyId" /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><form:password path="password" /></td>
                </tr>
                
               
                <tr>
                <td><a href="facultyregisterform">Click here to Register</a> </td>
                    <td colspan="2" align="center"><input type="submit" value="Login" /></td>
                </tr>
            </table>
        </form:form>
    </div>

</body>
</html>