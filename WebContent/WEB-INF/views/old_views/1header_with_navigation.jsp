<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<nav class="navbar navbar-icon-top navbar-expand-lg">
  <button class="navbar-toggler navbar-dark" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" 
  		aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active" data-toggle="modal" data-target="#waiting_modal">
        <a class="nav-link" href="home" style="color: #FEFEFE !important;">
          <i class="fa fa-home"></i>
          Home
          <span class="sr-only">(current)</span>
          </a>
      </li>
  	  <c:if test="${whichPageToShow != 'select_locations' && whichPageToShow != 'user_profile'}"> 
	      <li class="nav-item active dropdown">
	        <a class="nav-link" href="#" id="navbarConsentDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color: #FEFEFE !important;">
	          <i class="fa fa-file-contract"></i>
	          Consent
	        </a>
	        <div class="dropdown-menu dropdown-menu-left" aria-labelledby="navbarConsentDropdown" data-toggle="modal" data-target="#waiting_modal">
	          <a class="dropdown-item" href="add_consent">Add Consent</a>
	          <a class="dropdown-item" href="search_patient">Search Consent</a>
	        </div>
	      </li>
          <c:if test="${fn:containsIgnoreCase(consent_access, 'edit') || fn:containsIgnoreCase(consent_draft_access, 'edit')}">
		      <li class="nav-item active" data-toggle="modal" data-target="#waiting_modal">
		        <a class="nav-link" href="user_actions" style="color: #FEFEFE !important;">
		          <i class="fa fa-tasks"></i>
		          Action
		          <span class="sr-only">(current)</span>
	              <c:if test="${not empty tasks_count}">
		              <c:if test="${tasks_count gt 0}">
			              <span class="badge badge-pill badge-danger">${tasks_count}</span>
		              </c:if>
	              </c:if>
		          </a>
		      </li>
	      </c:if>
  	  </c:if> 
    </ul>
<div class="mx-auto">
  <h1 class="text-center" style="font-family: Calibri; font-size:45px;">BCI Tissue Bank Database
  <img class="pull-right img-responsive" src="<c:url value="/resources/images/BCILogoWhite.png"/>" alt="BCI Logo" width="150" height="50">
  </h1>
</div>    
    <ul class="navbar-nav">
      <li class="nav-item active dropdown">
        <a class="nav-link" href="#" id="navbarProfileDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fa fa-user" style="color: #FEFEFE !important;"></i>
          <span class="user-name" style="color: #FEFEFE !important;">${user.firstname} ${user.surname}
            <c:if test="${not empty user_selected_department}">
	            <strong> ${user_selected_department.dept_acronym}</strong>
            </c:if>
          </span>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarProfileDropdown" data-toggle="modal" data-target="#waiting_modal">
 	 		<c:if test="${whichPageToShow == 'select_locations' || whichPageToShow == 'user_profile'}"> 
	          <a class="dropdown-item" href="user_profile">My Profile</a>
	          <div class="dropdown-divider"></div>
 	        </c:if> 
          <a class="dropdown-item" href="logout">Logout</a>
        </div>
      </li>
    </ul>
  </div>
</nav>
</body>
</html>