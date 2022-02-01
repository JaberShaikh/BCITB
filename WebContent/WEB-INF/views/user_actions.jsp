<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
	<sec:csrfMetaTags />
</head>
<body>
   <div class="container">
       <div class="row">
           <div class="col-md-6">
               <h3><small>Actions Page</small></h3>
               <!-- Tabs with icons on Card -->
               <div class="card card-nav-tabs">
                   <div class="card-header card-header-primary">
                       <div class="nav-tabs-navigation">
                           <div class="nav-tabs-wrapper">
                               <ul class="nav nav-tabs" data-tabs="tabs">
                                   <li class="nav-item">
                                      <a class="nav-link active" href="#consent_tasks_tab" data-toggle="tab">
                                       	Actions To Perform
							             <span id="active_action_span" class="badge badge-pill badge-danger" style="display:none;"></span>
                                       </a>
                                   </li>
                                   <li class="nav-item">
                                       <a class="nav-link" href="#completed_tasks_tab" data-toggle="tab">
                                       	Completed Actions
                                       </a>
                                   </li>
                                   <li id="locked_action_li" class="nav-item">
                                       <a class="nav-link" href="#locked_tasks_tab" data-toggle="tab">
                                       	Locked Actions
							            <span id="locked_action_span" class="badge badge-pill badge-danger" style="dislay:none;"></span>
                                       </a>
                                   </li>
                             		<c:if test="${fn:containsIgnoreCase(imported_data, 'edit')}">
	                                   <li class="nav-item">
	                                       <a class="nav-link" href="#imported_consents_tab" data-toggle="tab">Imported Consents
							               <span id="imported_consents_span" class="badge badge-pill badge-danger" style="dislay:none;"></span>
							              </a>
	                                   </li>
                              		</c:if>
                               		<c:if test="${fn:containsIgnoreCase(audit_consent, 'edit')}">
	                                   <li class="nav-item">
	                                       <a class="nav-link" href="#audit_edit_consent_tab" data-toggle="tab">Audit Consents
							               <span id="audit_consents_span" class="badge badge-pill badge-danger" style="display:none;">${fn:length(existing_audit_consents)}</span>
							              </a>
	                                   </li>
                               		</c:if> 
                               </ul>
                           </div>
                       </div>
                   </div>
				<form:form name="user_actions_form" method="GET" action="validate_consent" autocomplete="off">
                   <div class="card-body">
                       <div class="tab-content">
                           <div class="tab-pane active" id="consent_tasks_tab">
							<div class="container">
								<div id="active_actions_div" class="form-check" 
									style="display:none;border:solid 2px black;max-height:350px;margin-bottom:10px;overflow-y:scroll;">
								<br>
								</div>
					  	  		<button style="background-color:#2E008B;color:#FEFEFE;display:none;" name="view_action_btn" id="view_action_btn" 
					  	  			type="button" onclick="processUserSelection(this)" class="btn btn-sm">
						  			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
					  	  			<i class="fas fa-eye"></i> View Action</button>
								<h6 id="no_active_actions_found_h6" style="display:none;">No Actions To Perform</h6>
							</div>
                          </div>
                           <div class="tab-pane" id="completed_tasks_tab">
							<div class="container">
								<div id="completed_actions_div" class="form-check" 
									style="display:none;border:solid 2px black;max-height:350px;margin-bottom:10px;overflow-y:scroll;">
								<br><br>
								</div>
								  <div id="completed_actions_notes_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
								    <label for="completed_task_action_notes" class="col-sm-5 col-form-label text-left">Action Notes</label>
								    <div class="col-sm-6 col-md-6">
						               <textarea id="completed_task_action_notes" name="completed_task_action_notes" 
						               		maxlength="200" rows="5" class="form-control form-control-sm floatlabel" readonly="readonly"></textarea>
								    </div>
								  </div>	 									
								<h6 id="no_completed_actions_found_h6" style="display:none;">No Completed Actions Found</h6>
							</div>
                          </div>
                           <div class="tab-pane" id="locked_tasks_tab">
							<div class="container">
								<div id="locked_actions_div" class="form-check" 
									style="display:none;border:solid 2px black;max-height:350px;margin-bottom:10px;overflow-y:scroll;">
									<br>
								</div>
					  	  		<button style="background-color:#2E008B;color:#FEFEFE;display:none;" name="unlock_action_btn" id="unlock_action_btn" 
					  	  			type="button" onclick="processUserSelection(this)" class="btn btn-sm">
						  			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
					  	  			<i class="fas fa-unlock"></i> Unlock Action</button>
							    <h6 id="no_locked_actions_found_h6" style="display:none;">No Locked Actions Found</h6>
							</div>
                          </div>
                       		<c:if test="${fn:containsIgnoreCase(imported_data, 'edit')}">
	                           <div class="tab-pane" id="imported_consents_tab">
								<div class="container">
									<div id="imported_consents_div" class="form-check" 
										style="display:none;border:solid 2px black;max-height:350px;margin-bottom:10px;overflow-y:scroll;overflow-x:auto;">
										<br>
									</div>
						  	  		<button style="background-color:#2E008B;color:#FEFEFE;display:none;" name="validate_imported_consent_btn" id="validate_imported_consent_btn" 
						  	  			type="button" onclick="processUserSelection(this)" class="btn btn-sm">
							  			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
						  	  			<i class="fas fa-eye"></i> View Imported Consents</button>
								    <h6 id="no_validate_imported_consent_h6" style="display:none;">No Imported Consents To Validate</h6>
								</div>
	                          </div>
                       		</c:if>
	                   		<c:if test="${fn:containsIgnoreCase(audit_consent, 'edit')}">
	                           <div class="tab-pane" id="audit_edit_consent_tab">
								<div class="container">
									<div id="audit_consents_div" class="form-check" 
										style="display:none;border:solid 2px black;max-height:350px;margin-bottom:10px;overflow-y:scroll;">
									<br>
									</div>
						  	  		<button style="background-color:#2E008B;color:#FEFEFE;display:none;" name="audit_consent_btn" id="audit_consent_btn" 
						  	  			type="button" onclick="processUserSelection(this)" class="btn btn-sm">
							  			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
						  	  			<i class="fas fa-eye"></i> View Consent</button>
									<h6 id="no_audit_consents_found_h6" style="display:none;">No Audit Consents To View</h6>
								</div>
	                          </div>
                       		</c:if> 
                         </div>
                       </div>
					  <input type="hidden" id="selected_data_id" name="selected_data_id"/>
					  <input type="hidden" id="selected_department" name="selected_department"/>
                     </form:form>
                   </div>
                 </div>
               <!-- End Tabs with icons on Card -->
           </div>
       </div>
</body>
</html>