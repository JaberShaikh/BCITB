<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>BCI Tissue Bank</title>
    
	<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/4.3.1/css/bootstrap.min.css"/>"/>
	<link rel="stylesheet" href="<c:url value="/webjars/bootstrap-select/1.13.8/css/bootstrap-select.min.css"/>"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/index.css"/>"/> 
	<link href="<c:url value="/webjars/font-awesome/5.9.0/css/all.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap-datetimepicker/2.4.4/css/bootstrap-datetimepicker.css"/>"/>   

	<script type="text/javascript" src="<c:url value="/webjars/jquery/1.9.1/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/webjars/bootstrap/4.3.1/js/bootstrap.bundle.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/webjars/bootstrap-select/1.13.8/js/bootstrap-select.min.js"/>"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" ></script>    
	<script type="text/javascript" src="<c:url value="/resources/js/index.js"/>"></script>
    <script src="<c:url value="/webjars/bootstrap-datetimepicker/2.4.4/js/bootstrap-datetimepicker.min.js"/>"></script>
    
	<script type="text/javascript" src="<c:url value="/webjars/datatables/1.10.19/js/jquery.dataTables.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/webjars/datatables/1.10.19/js/dataTables.bootstrap4.min.js"/>"></script>
    
</head>

<body>

<div class="page-wrapper chiller-theme toggled">
  <a id="show-sidebar" style="margin-top:5px; background-color: #2E008B; color: #FEFEFE" class="btn btn-sm" href="#">
    <i class="fas fa-bars"></i>
  </a>
  <nav id="sidebar" class="sidebar-wrapper">
    <div class="sidebar-content">
      <div class="sidebar-brand">
        <a href="home">HOME</a>
        <div id="close-sidebar">
          <i class="fas fa-times"></i>
        </div>
      </div>
      <div class="sidebar-header">
        <div class="user-pic">
          <img class="img-responsive img-rounded" src="https://raw.githubusercontent.com/azouaoui-med/pro-sidebar-template/gh-pages/src/img/user.jpg" alt="User picture">
        </div>
        <div class="user-info">
          <span class="user-name">${user.firstname}
            <strong>${user.surname}</strong>
          </span>
          <span class="user-role">${primaryRole.role_description}</span>
        </div>
      </div>
      <!-- sidebar-header  -->
      <div class="sidebar-menu">
        <ul>
          <li class="header-menu">
            <span>Tools</span>
          </li>
          <li class="sidebar-dropdown">
		 	<c:choose>
		 		<c:when test="${url == '/home' || url == '/save_user' || url == '/user_profile'}">
		            <a href="#">
		              <i class="fas fa-users fa-2x"></i>
		              <span>User Profile</span>
		            </a>
		            <div class="sidebar-submenu">
		              <ul>
		                <li>
		                  <a href="user_profile"><i class="fas fa-user fa-2x"></i> My Profile</a>
		                </li>
		              </ul>
		            </div>
		 		</c:when>
		 		<c:otherwise>
 		            <a href="#">
		              <i class="fas fa-archive fa-2x"></i>
		              <span>Consent</span>
		            </a>
		            <div class="sidebar-submenu">
		              <ul>
		                <li>
		                  <a href="add_consent"><i class="far fa-file fa-2x"></i>Add Consent</a>
		                </li>
		              </ul>
		            </div>
		 		</c:otherwise>
		 	</c:choose>
          </li>
        </ul>
      </div>
      <!-- sidebar-menu  -->
    </div>
    <!-- sidebar-content  -->
    <div class="sidebar-footer">
      <a href="logout"><i class="fa fa-power-off"></i> Logout</a>
    </div>    
  </nav>
  <!-- sidebar-wrapper  -->
  <div class="page-content">
    <div class="container-fluid">
    <%@ include file="header.jsp" %>
 	<br><br>
 	<c:choose>
 		<c:when test="${url == '/save_user' || url == '/user_profile'}">
 			<%@ include file="user_profile.jsp" %>
 		</c:when>
 		<c:when test="${url == '/home'}">
 			<%@ include file="select_locations.jsp" %>
 		</c:when>
 		<c:when test="${url == '/add_consent'}">
 			<%@ include file="add_consent.jsp" %>
 		</c:when>
 		<c:otherwise>
 			<%@ include file="welcome.jsp" %>
 		</c:otherwise>
 	</c:choose>
    </div>
  </div>
  <!-- page-content" -->
</div>
</body>

</body>
</html> --%>