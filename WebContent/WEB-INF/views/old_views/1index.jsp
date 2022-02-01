<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>

	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
<body id="index_body">
<div id="waiting_modal" class="modal fade bd-example-modal-lg" data-backdrop="static" data-keyboard="false" tabindex="-1">
    <div class="modal-dialog modal-sm">
    	<h5 style="color:white">Processing...</h5>
        <div class="modal-content" style="width: 48px">
            <span class="fa fa-spinner fa-spin fa-3x"></span>
        </div>
    </div>
</div>
<%-- <%@ include file="header_with_navigation.jsp" %> --%>
 <div class="page-wrapper chiller-theme toggled">
  <a id="show-sidebar" style="margin-top:5px; background-color: #2E008B; color: #FEFEFE" class="btn btn-sm" href="#">
    <i class="fas fa-bars"></i>
  </a>
  <nav id="sidebar" class="sidebar-wrapper">
    <div class="sidebar-content">
      <div class="sidebar-brand">
        <a href="#"></a>
        <div id="close-sidebar">
          <i class="fas fa-times"></i>
        </div>
      </div>
      <div class="sidebar-header">
<!--         <div class="user-pic">
          <img class="img-responsive img-rounded" src="https://raw.githubusercontent.com/azouaoui-med/pro-sidebar-template/gh-pages/src/img/user.jpg" alt="User picture">
        </div> -->
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
		 		<c:when test="${whichPageToShow == 'select_locations' || whichPageToShow == 'user_profile'}">
		          <li id="user_profile_menu_item" data-toggle="modal" data-target="#waiting_modal">
		            <a href="user_profile">
		              <i class="fas fa-user fa-2x"></i>
		              <span>My Profile</span>
		            </a>
		          </li>		 		
		 		</c:when>
		 		<c:otherwise>
		          <li id="add_consent_menu_item" data-toggle="modal" data-target="#waiting_modal">
		            <a href="add_consent">
		              <i class="fas fa-file fa-2x"></i>
		              <span>Add Consent</span>
		            </a>
		          </li>
		          <li id="search_consent_menu_item" data-toggle="modal" data-target="#waiting_modal">
		            <a href="search_patient">
		              <i class="fas fa-search fa-2x"></i>
		              <span>Search Patient</span>
		            </a>
		          </li>
		          <c:if test="${fn:containsIgnoreCase(consent_access, 'edit') 
		          		|| fn:containsIgnoreCase(consent_draft_access, 'edit')}">
			          <li id="actions_menu_item" data-toggle="modal" data-target="#waiting_modal">
			            <a href="user_actions">
			              <i class="fas fa-tasks fa-2x"></i>
			              <span>Actions </span>
			              <c:if test="${not empty tasks_count}">
				              <c:if test="${tasks_count gt 0}">
					              <span class="badge badge-pill badge-danger">${tasks_count}</span>
				              </c:if>
			              </c:if>
			            </a>
			          </li>
			      </c:if>
		 		</c:otherwise>
		 	</c:choose>
          </li>
        </ul>
      </div>
      <!-- sidebar-menu  -->
    </div>
    <div class="sidebar-footer">
      <a href="logout"><i class="fa fa-power-off"></i> Logout</a>
    </div>    
  </nav>
  <!-- sidebar-wrapper  -->
  <div class="page-content">
     <div class="container-fluid">
 	<c:choose>
<%--   		<c:when test="${whichPageToShow == 'user_profile'}">
 			<%@ include file="user_profile.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'select_locations'}">
 			<%@ include file="select_locations.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'add_consent' || whichPageToShow == 'edit_patient' || whichPageToShow == 'validate_patient'}">
 			<%@ include file="add_consent.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'search_patient'}">
 			<%@ include file="search_patient.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'user_actions'}">
 			<%@ include file="user_actions.jsp" %>
 		</c:when>
 		<c:otherwise>
 			<%@ include file="welcome.jsp" %>
 		</c:otherwise>  --%>
 	</c:choose>
    </div>
  </div>
  <!-- page-content" -->
</div> 
</body>
</html>