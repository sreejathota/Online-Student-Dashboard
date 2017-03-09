<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <body>
        <h1>Please login</h1>
        
        <form:form method="post" action="loginaction">
            <table>
                <tr>
                    <td><form:label path="username">Please enter username</form:label></td>
                    <td><form:input path="username" /></td>
                    <td><font color="red"><form:errors path="username"/></font></td>
                </tr>
                <tr>
                    <td><form:label path="password">Please enter password</form:label></td>
                    <td><form:password path="password" /></td>
                    <td><font color="red"><form:errors path="password"/></font></td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Login"/>
                    </td>
                    <td>
                        <input type="reset" value="Reset"/>
                    </td>
                </tr>
            </table>
        </form:form>

    </body>
</html>