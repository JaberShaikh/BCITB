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

  	<script type="text/javascript" src="<c:url value="/webjars/jquery/1.9.1/jquery.min.js"/>"></script>  
	<script type="text/javascript" src="<c:url value="/webjars/bootstrap/4.3.1/js/bootstrap.bundle.min.js"/>"></script> 
	<script type="text/javascript" src="<c:url value="/webjars/bootstrap-select/1.13.8/js/bootstrap-select.min.js"/>"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" ></script>    
	<script type="text/javascript" src="<c:url value="/resources/js/index.js"/>"></script>

	<link rel="stylesheet" href="<c:url value="/webjars/datatables/1.10.19/css/jquery.dataTables.min.css"/>"/>
	<link rel="stylesheet" href="<c:url value="/webjars/datatables-buttons/1.7.0/css/buttons.dataTables.min.css"/>"/>

	<script type="text/javascript" src="<c:url value="/webjars/datatables/1.10.19/js/jquery.dataTables.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/webjars/datatables-buttons/1.7.0/js/dataTables.buttons.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/webjars/jszip/3.6.0/dist/jszip.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/webjars/pdfmake/0.1.17/build/pdfmake.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/webjars/pdfmake/0.1.17/build/vfs_fonts.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/webjars/datatables-buttons/1.7.0/js/buttons.html5.min.js"/>"></script>

<script type="text/javascript">
 	var lastActiveTime = new Date().getTime();
	$(document).ready(function() {
	    $('body *').bind('click mousemove keypress scroll resize', function() {
	       lastActiveTime = new Date().getTime();
	       });
	       setInterval(checkIdleTime, 30000); // 30 sec
	});
	
	function checkIdleTime(){
	 	var diff = new Date().getTime() - lastActiveTime;
	    if(diff > 480000){ // 8 min of inactivity
	        window.location.href = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/')) + "/timed_out";
	    }
	} 
</script>
</head>
<body id="index_body" onload="reloadData()">
<div id="waiting_modal" class="modal my_waiting_modal fade bd-example-modal-lg" data-backdrop="static" data-keyboard="false" tabindex="-1">
    <div id="waiting_modal_body" class="modal-dialog modal-sm">
        <div class="modal-content" style="width: 48px">
	    	<h5 style="color:white">Processing...</h5>
	    	<br>
            <span class="fa fa-spinner fa-spin fa-4x" style="color:white"></span>
        </div>
    </div>
</div>
<%@ include file="header_with_navigation.jsp" %>
 <div class="page-wrapper chiller-theme toggled">
  <div class="page-content">
  <div id="previous_search_result_page_div"></div>
  <div class="container-fluid">
 	<c:choose>
  		<c:when test="${whichPageToShow == 'user_profile'}">
 			<%@ include file="user_profile.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'select_department_locations'}">
 			<%@ include file="select_department_locations.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'add_patient'}">
 			<%@ include file="add_patient.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'add_consent'}">
 			<%@ include file="add_consent.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'add_infection_risk'}">
 			<%@ include file="add_infection_risk.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'search'}">
 			<%@ include file="search.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'user_actions'}">
 			<%@ include file="user_actions.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'pat_con_ir_val'}">
 			<%@ include file="pat_con_ir_val.jsp" %>
 		</c:when>
 		<c:when test="${whichPageToShow == 'pat_ir'}">
 			<%@ include file="pat_ir.jsp" %>
 		</c:when>
 		<c:otherwise>
 			<%@ include file="welcome.jsp" %>
 		</c:otherwise> 
 	</c:choose>
  </div>
  </div>
 </div>
<div class="modal fade my_document_preview_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> 
  <div class="modal-dialog modal-fluid modal-notify modal-info modal-xl modal-dialog-centered" role="document">
    <!--Content-->
    <div class="modal-content" style="height:800px;">
      <!--Header-->
      <div class="modal-header">
        <p id="my_document_preview_header" class="heading lead">Modal PDF</p>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true" class="white-text">&times;</span>
        </button>
      </div>
      <!--Body-->
      <div class="modal-body" id="pdf_doc_modal_body">
      </div>
      <!--Footer-->
      <div class="modal-footer justify-content-center">
        <a type="button" id='dismiss_btn' class="btn btn-outline-info waves-effect"><i class="fas fa-times ml-1"></i> Dismiss</a>
        <a type="button" id='yes_btn' class="btn btn-outline-info waves-effect"><i class="fas fa-check-circle"></i> Yes</a>
        <a type="button" id='no_btn' class="btn btn-outline-info waves-effect"><i class="fas fa-times ml-1"></i> No</a>
      </div>
    </div>
    <!--/.Content-->
  </div>  
</div>  
  <input type="hidden" name="user_id" id="user_id" value="${user.user_id}"/>
  <input type="hidden" name="patient_dob" id="patient_dob" value="${session_patient.date_of_birth}"/>
  <input type="hidden" name="whichPageToShow" id="whichPageToShow" value="${whichPageToShow}"/>
  <input type="hidden" name="consent_access" id="consent_access" value="${consent_access}"/>
  <input type="hidden" name="export_search_result" id="export_search_result" value="${export_search_result}"/>
  <input type="hidden" name="consent_withdrawn_access" id="consent_withdrawn_access" value="${consent_withdrawn_access}"/>
  <input type="hidden" name="consent_validate_access" id="consent_validate_access" value="${consent_validate_access}"/>
  <input type="hidden" name="whichDepartment" id="whichDepartment" value="${user_selected_department.dept_acronym}"/>
  <input type="hidden" name="timeline" id="timeline" value="${timeline}"/>
  <input type="hidden" name="basic_search_options" id="basic_search_options" value="${basic_search_options}"/>
  <input type="hidden" name="advanced_search_sql_script" id="advanced_search_sql_script" value="${advanced_search_sql_script}"/>
  <input type="hidden" name="advanced_search_selected_columns" id="advanced_search_selected_columns" value="${advanced_search_selected_columns}"/>
</body>
</html>