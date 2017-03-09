<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
     <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<title>Students Enrolled</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/studentenrolpage.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/studentenrolpage2.css" />" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-black.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<style type="text/css">
html,body,h1,h2,h3,h4,h5,h6 {font-family: "Roboto", sans-serif}
.w3-sidenav a,.w3-sidenav h4{padding:12px;}
.w3-navbar a{padding-top:12px !important;padding-bottom:12px !important;}

table.myTable { 
  border-collapse: collapse; 
 background-color: #ccc;
  }
table.myTable td, 
table.myTable th { 
 border: 1px solid #8f5a0a; 
  padding:5px; 
  }
 
</style>
<body>

<!-- Navbar -->
<ul class="w3-navbar w3-theme w3-top w3-left-align w3-large" style="z-index:4;">
  <li class="w3-opennav w3-right w3-hide-large">
    <a class="w3-hover-white w3-large w3-theme-l1" href="javascript:void(0)" onclick="w3_open()"><i class="fa fa-bars"></i></a>
  </li>
  <!-- <li><a href="#" class="w3-theme-l1">Home</a></li> -->
   <li class="w3-hide-small"><a href="" class="w3-hover-white"></a></li>
   <!-- <li class="w3-hide-small"><a href="#" class="w3-hover-white">Information</a></li> -->
  <!-- <li class="w3-hide-medium w3-hide-small"><a href="/ASP_Sreeja/logout" class="w3-hover-white">Logout</a></li> -->
  <li class="w3-hide-medium w3-hide-small"><h2 style="text-align:center;margin-left: 350px;display:block;color:#FF6347">Student Enrollment System</h2></li>
</ul>

<!-- Sidenav -->
<nav class="w3-sidenav w3-collapse w3-theme-l5 w3-animate-left" style="z-index:3;width:250px;margin-top:51px;" id="mySidenav">
  <a href="javascript:void(0)" onclick="w3_close()" class="w3-right w3-xlarge w3-padding-large w3-hover-black w3-hide-large" title="close menu">
    <i class="fa fa-remove"></i>
  </a>
  <h4><b>Hi ${faculty.facultyName}</b></h4>
  <a href="/ASP_Sreeja/viewstudents" class="w3-hover-black">View Students to my Courses</a>
  <a href="/ASP_Sreeja/viewmycourseschedule" class="w3-hover-black">View my Course Schedule</a>
  <a href="/ASP_Sreeja/viewmessages" class="w3-hover-black">View Messages</a>
  <a href="/ASP_Sreeja/logout" class="w3-hover-black">Logout</a>
</nav>

<!-- Overlay effect when opening sidenav on small screens -->
<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- Main content: shift it to the right by 250 pixels when the sidenav is visible -->
<div class="w3-main" style="margin-left:250px">

  <div class="w3-row w3-padding-64">
    <div class="w3-twothird w3-container">
    <h1 class="w3-text-teal" style="margin-left:250px">Students Enrolled</h1>
     <p>${nooneenrolled}</p>

<table class="myTable" style="margin-top:35px;margin-left:100px"> 

<tr ><th>Student ID</th><th>Student Name</th><th>Mail ID</th></tr>
   <c:forEach var="enrolledstudent" items="${student}">   
   <tr>  
   <td>${enrolledstudent.studentId}</td>  
   <td>${enrolledstudent.firstName}  ${enrolledstudent.lastName}</td>  
   <td>${enrolledstudent.mailId}</td>  
  
   </tr>  </c:forEach> 
   
   </table>  
    </div>
    
  </div>

  <!-- Pagination -->
  <div class="w3-center w3-padding-64">
    
  </div>

  <footer id="myFooter" style="margin-top:35px">
    <div class="w3-container w3-theme-l2 w3-padding-32">
      <h4>Footer</h4>
    </div>

    <div class="w3-container w3-theme-l1">
      <p>Powered by <a href="http://www.w3schools.com/w3css/default.asp" target="_blank">Sreeja</a></p>
    </div>
  </footer>

<!-- END MAIN -->
</div>

<script>
// Script to open and close the sidenav
function w3_open() {
    document.getElementById("mySidenav").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}
 
function w3_close() {
    document.getElementById("mySidenav").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}
</script>

</body>
</html>

