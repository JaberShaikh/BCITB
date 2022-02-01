<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body onload="reloadData()">
<nav class="navbar navbar-icon-top navbar-expand-lg">
  <button class="navbar-toggler navbar-dark" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" 
  		aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item" data-toggle="modal" data-target="#waiting_modal">
        <a class="nav-link" href="select_department_locations" style="color: #FEFEFE !important;">
          <i class="fa fa-building"></i>
          Department
          <span class="sr-only">(current)</span>
          </a>
      </li>
      <c:choose>
      	<c:when test="${menuToShow == 'ACTION'}">
	      <li class="nav-item" data-toggle="modal" data-target="#waiting_modal">
	        <a class="nav-link" href="user_actions" style="color: #FEFEFE !important;">
	          <i class="fa fa-tasks"></i>
	          Actions
            <span id="user_action_count_badge" class="badge badge-pill badge-danger" style="display:none;"></span>
	        </a>
	      </li>
      	</c:when>
      	<c:otherwise>
	      <li class="nav-item" data-toggle="modal" data-target="#waiting_modal">
	        <a class="nav-link" href="welcome" style="color: #FEFEFE !important;">
	          <i class="fa fa-home"></i>
	          Home
	          <span class="sr-only">(current)</span>
	          </a>
	      </li>
	      <li class="nav-item" data-toggle="modal" data-target="#waiting_modal">
	        <a class="nav-link" href="add_patient" style="color: #FEFEFE !important;">
	          <i class="fa fa-file"></i>
	          Add Consent
	          </a>
	      </li>
	      <li class="nav-item" data-toggle="modal" data-target="#waiting_modal">
	        <a class="nav-link" href="search" style="color: #FEFEFE !important;">
	          <i class="fa fa-binoculars"></i>
	          Search
	          </a>
	      </li>
      	</c:otherwise>
      </c:choose>
    </ul>
<div class="mx-auto">
   <h1 class="text-center" style="font-family: Calibri; font-size:45px;">TESTING WEBSITE
  		<img class="pull-right img-responsive" src="<c:url value="/resources/images/BCILogoWhite.png"/>" alt="BCI Logo" width="150" height="50">
   </h1> 
<%--    <h1 class="text-center" style="font-family: Calibri; font-size:45px;">BCI Tissue Bank Database 
 	  <img class="pull-right img-responsive" src="<c:url value="/resources/images/BCILogoWhite.png"/>" 
			alt="BCI Logo" width="150" height="50"> 
   <br> 
   </h1>  --%>
</div>
    <ul class="navbar-nav">
      <li class="nav-item active dropdown">
        <a class="nav-link" href="#" id="navbarProfileDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fa fa-user" style="color:#FEFEFE !important;"></i>
          <span class="user-name" style="color:#FEFEFE !important;">${user.user_firstname} ${user.user_surname}</span>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarProfileDropdown" data-toggle="modal" data-target="#waiting_modal">
          <a class="dropdown-item" href="user_profile">My Profile</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="logout">Logout</a>
        </div>
      </li>
    </ul>
  </div>
</nav>
<div class="mx-auto">
  <h6 class="font-weight-light text-right">You are logged in as ${primaryRole.role_description} </h6>
</div>
<c:if test="${fn:length(user_selected_department.dept_name) gt 0 && whichPageToShow != 'welcome'}">
	<br>
	<div class="mx-auto">
      <h1 class="font-weight-light text-center">Welcome to ${user_selected_department.dept_name}</h1>
	</div>
</c:if>
</body>
</html>