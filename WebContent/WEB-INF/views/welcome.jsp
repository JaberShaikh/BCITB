<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style>
 body {
  background: no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  background-size: cover;
  -o-background-size: cover;
} 
</style>
</head>
 <body id="welcome_body"> 
	<div class="container">
	  <div class="card border-0 shadow my-5" style="color: #2E008B">
	    <div class="card-body p-5">
	      <h1 class="font-weight-light">Welcome to ${user_selected_department.dept_name}</h1>
	      <p class="lead">${user_selected_department.dept_description}</p>
	    </div>
	  </div>
	</div>
</body>
</html>