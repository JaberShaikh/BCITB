<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<sec:csrfMetaTags/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<form:form name="action_form" method="POST" action="save_action" modelAttribute="action" autocomplete="off">
<body onload="reloadData()">
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
	<div class="container">
		<div class="row">
             <span class="anchor"></span>
              <div class="card card-outline-secondary">
                  <div class="card-header" style="color: #FEFEFE">
                      <h3 class="mb-0">Action</h3>
              </div>
           <div class="card-body">
			 <table class="table table-bordered"> 
			  <tbody>
			    <tr>
			      <td style="width:600px;">
					  <div id="prev_user_action_list_div" class="shadow-lg rounded form-group row row-bottom-margin">
			 		    <label for="prev_user_action_list" id="prev_user_action_list_label" class="col-sm-6 col-form-label text-left">Select action to view notes</label> 
					    <div class="col-sm-10 col-md-10">
					    	<ul id="prev_user_action_list" style="list-style-type: none; font-size: 12px;">
					    	</ul>
			         	</div>
			          </div> 
					  <div id="prev_user_action_notes_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="prev_user_action_notes" class="col-sm-5 col-form-label text-left">Previous Action Notes</label>
					    <div class="col-sm-6 col-md-6">
			               <form:textarea id="prev_user_action_notes" name="prev_user_action_notes" path=""
			               		maxlength="200" rows="5" class="form-control form-control-sm floatlabel avoid_pid_data" readonly="true"></form:textarea>
					    </div>
					  </div>
			      </td>
			      <td style="width:600px;">
			      	 <c:if test="${not empty infectionRisksAlert}">
					  <div class="alert alert-danger ml-2" role="alert">
					  		${infectionRisksAlert}
					  </div>	          
			      	 </c:if>
					  <div id="action_type_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="action_type" class="col-sm-5 col-form-label text-left">Patient/Consent Action to send <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
					      <form:select id="action_type" name="action_type" path="action_type" 
					      		class="browser-default custom-select custom-select-sm" onchange="hideAndShowContainer(this)">
					      </form:select>
			              <label id="action_type-validation" style="color:red;display:none;"></label> 
					    </div>
					    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Select action type">
							<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
								<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
					    </span>
					  </div>
					  <div id="action_notes_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
					    <label for="user_selected_action_notes" class="col-sm-5 col-form-label text-left">Action Notes <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
			               <form:textarea id="user_selected_action_notes" name="user_selected_action_notes" path="user_selected_action_notes" 
			               		onblur="uploadFormDataToSessionObjects('ACTION',this)"
			               		maxlength="200" rows="5" class="form-control form-control-sm floatlabel avoid_pid_data"></form:textarea>
			               <label id="action_notes-validation" style="color:red; display: none;"></label>
					    </div>
					    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
					    	data-content="Type in notes for the next user who will be working on this consent">
							<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
								<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
					    </span>
					  </div>
			       </td>
			      </tr>
			     </tbody>
			   </table>
			  <div id="save_action_btn_div" class="text-center">
			    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" 
			    	id="save_action_btn" type="button" onclick="validateFormFields('validate_action',null,'save_action')">
			  		<i class="fas fa-save"></i> Save</form:button>
			    <form:button name="action_cancel_btn" style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;" 
			    	onclick="processUserSelection(this)" class="btn btn-sm" type="button">
			    	<i class="fas fa-window-close"></i> Cancel</form:button>
                 </div>
               </div>
              </div>
          </div>
      </div>
   </div>
  </body>
</form:form>
</html>