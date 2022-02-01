<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<sec:csrfMetaTags />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<form:form name="infection_risk_form" method="POST" action="save_infection_risk" modelAttribute="infection_risk" autocomplete="off">
<body onload="reloadData()">
<h4>${headerTitleToShow}</h4>
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-8 offset-md-2">
       <span class="anchor" id="formConsent"></span>
         <div class="card card-outline-secondary">
           <div class="card-header" style="color: #FEFEFE">
             <h3 class="mb-0">Infection Risk</h3>
           </div>
          <div class="card-body">
			  <div id="prev_infection_risk_chkbox_div" style="display: none;" class="shadow-lg rounded form-group row row-bottom-margin">
	 		    <label for="prev_infection_risk_chkbox" class="col-sm-6 col-form-label text-left">Previously recorded Infection Risk(s)</label> 
			    <div class="col-sm-10 col-md-10">
			    	<ul id="prev_infection_risk_chkbox" style="list-style-type: none; font-size: 12px;">
			    	</ul>
	         	</div>
	          </div> 
	          <br>
	          <div id="infection_risk_body_div">
				<div id="infection_risk_exist_alert" class="alert alert-danger ml-2" role="alert" style="display:none;">
				  	Infection Risk Exist Alert Text
				</div>	          
			  <div id="infection_risk_exist_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label for="infection_risk_exist" class="col-sm-6 col-form-label text-left">Infection Risk <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-5 col-md-5">
			      <form:select id="infection_risk_exist" name="infection_risk_exist" path="infection_risk_exist" 
			      		onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects('INFECTION-RISK',this);" class="browser-default custom-select custom-select-sm">
			        <option value=""></option>
			        <option value="No (Tests = Negative)">No (Tests = Negative)</option>
			        <option value="Yes (Tests = Positive)">Yes (Tests = Positive)</option>
			        <option value="No infection risks recorded">No infection risks recorded</option>
			        <option value="Unknown - possible risk">Unknown - possible risk</option>
			        <option value="Not checked (imported)">Not checked (imported)</option>
			      </form:select>
	              <label id="infection_risk_exist-validation" style="color:red; display: none;"></label> 
			    </div>
			    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Infection Risk" 
			    	data-content="Does the patient have any known or possible infection risk, according to their medical records?">
					<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
						<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
			    </span>
			  </div>
			  <div id="select_infection_type_id_div" class="form-group row row-bottom-margin ml-2" style="display:none; margin-bottom:5px;">
			    <label for="select_infection_type_id" class="col-sm-6 col-form-label text-left">Type Of Infection <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-5 col-md-5">
			      <form:select id="select_infection_type_id" name="select_infection_type_id" path="" 
			      		onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects('INFECTION-RISK',this);" class="browser-default custom-select custom-select-sm">
			          <option value=""></option>
					  <c:forEach items="${infection_types}" var = "it">
				          <option value="${it.infection_type_id}">${it.description}</option>
					  </c:forEach>
			      </form:select>
	              <label id="select_infection_type_id-validation" style="color:red; display: none;"></label> 
			    </div>
			  </div>
			  <div id="other_infection_risk_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
			    <label for="other_infection_risk" class="col-sm-6 col-form-label text-left">Other Infection Risk <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-5 col-md-5">
	               <form:input type="text" id="other_infection_risk" name="other_infection_risk" path="other_infection_risk"
	               		maxlength="20" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects('INFECTION-RISK',this)"></form:input> 
	               <label id="other_infection_risk-validation" style="color:red; display: none;"></label> 
			    </div>
			  </div>
			  <div id="infection_risk_notes_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
			    <label for="infection_risk_notes" class="col-sm-6 col-form-label text-left">Notes</label>
			    <div class="col-sm-5 col-md-5">
	               <form:textarea id="infection_risk_notes" name="infection_risk_notes" path="infection_risk_notes" rows="5" onchange="uploadFormDataToSessionObjects('INFECTION-RISK',this)"
	               		maxlength="500" class="form-control form-control-sm floatlabel avoid_pid_data"></form:textarea> 
			    </div>
			  </div>
			  <div id="select_episode_of_infection_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
			    <label for="select_episode_of_infection" class="col-sm-6 col-form-label text-left">Episode Of Infection <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-5 col-md-5">
			      <form:select id="select_episode_of_infection" name="select_episode_of_infection" path="" onchange="uploadFormDataToSessionObjects('INFECTION-RISK',this)"
			      		class="browser-default custom-select custom-select-sm">
			        <option value=""></option>
			        <option value="Unknown">Unknown</option>
			      	<c:forEach var = "risk_num" begin = "1" end = "4">
				        <option value="${risk_num}">${risk_num}</option>
			      	</c:forEach>
			      </form:select>
	               <label id="select_episode_of_infection-validation" style="color:red; display: none;"></label> 
			    </div>
			    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Episode Of Infection" 
			    	data-content="State whether this is the patient's first episode of infection, or their second/third etc. if the infection was treated and reoccurred">
					<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
						<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
			    </span>
			  </div>
			  <div id="episode_start_date_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
			    <label for="episode_start_date" class="col-sm-6 col-form-label text-left">Episode Start Date<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-5 col-md-5">
	               <form:input type="date" id="episode_start_date" name="episode_start_date" path="episode_start_date"
	               		onblur="uploadFormDataToSessionObjects('INFECTION-RISK',this);" placeholder="dd-MM-YYYY" 
	               		class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
	               <label id="episode_start_date-validation" style="color:red; display: none;"></label> 
			    </div>
			    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Episode Start Date" data-content="Indicate the date the patient was diagnosed with the infection">
					<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
						<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
			    </span>
			  </div>
			  <div id="select_continued_risk_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
			    <label for="select_continued_risk" class="col-sm-6 col-form-label text-left">Continued Risk <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-5 col-md-5">
			      <form:select id="select_continued_risk" name="select_continued_risk" path="" 
			      		onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects('INFECTION-RISK',this);" class="browser-default custom-select custom-select-sm">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				        <option value="Unknown">Unknown</option>
			      </form:select>
	               <label id="select_continued_risk-validation" style="color:red; display: none;"></label> 
			    </div>
			    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Continued Risk" data-content="If the infection is still on-going then confirm here">
					<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
						<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
			    </span>
			  </div>
			  <div id="episode_finished_date_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
			    <label for="episode_finished_date" class="col-sm-6 col-form-label text-left">Episode Finished Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-5 col-md-5">
	               <form:input type="date" id="episode_finished_date" name="episode_finished_date" path="episode_finished_date"
	               		onblur="uploadFormDataToSessionObjects('INFECTION-RISK',this);"  placeholder="dd-MM-YYYY"
	               		class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
	               <label id="episode_finished_date-validation" style="color:red; display: none;"></label> 
		        </div>
			    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Episode Finished Date" 
			    	data-content="Indicate the date the patient was deemed to be clear of infection, if applicable.">
					<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
						<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
			    </span>
			  </div>
			  <div id="select_sample_collection_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
			    <label for=select_sample_collection class="col-sm-6 col-form-label text-left">Sample Collected <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-5 col-md-5">
			      <form:select id="select_sample_collection" name="select_sample_collection" path="" 
			      		onchange="uploadFormDataToSessionObjects('INFECTION-RISK',this);" class="browser-default custom-select custom-select-sm">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				        <option value="Unknown">Unknown</option>
			      </form:select>
	              <label id="select_sample_collection-validation" style="color:red; display: none;"></label> 
			    </div>
			    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Sample Collected" data-content="Have samples been collected">
					<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
						<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
			    </span>
			  </div>
			  <div id="select_sample_type_id_div" class="form-group row row-bottom-margin ml-2" style="display:none; margin-bottom:5px;">
			    <label for="select_sample_type_id" class="col-sm-6 col-form-label text-left">Sample Type</label>
			    <div class="col-sm-5 col-md-5">
			      <form:select id="select_sample_type_id" name="select_sample_type_id" path="" 
			      		onchange="uploadFormDataToSessionObjects('INFECTION-RISK',this);" class="browser-default custom-select custom-select-sm">
			          <option value="0"></option>
					  <c:forEach items="${sampleTypes}" var = "st">
				          <option value="${st.sample_type_id}">${st.description}</option>
					  </c:forEach>
			      </form:select>
			    </div>
			  </div>
			  <div id="sample_collection_date_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
			    <label for="sample_collection_date" class="col-sm-6 col-form-label text-left">Sample Collection Date</label>
			    <div class="col-sm-5 col-md-5">
	               <form:input type="date" id="sample_collection_date" name="sample_collection_date" path="sample_collection_date"
	               		onblur="uploadFormDataToSessionObjects('INFECTION-RISK',this);"  placeholder="dd-MM-YYYY"
	               		class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
	               <label id="sample_collection_date-validation" style="color:red; display: none;"></label> 
		        </div>
			  </div>
			  <form:input type="hidden" id="episode_of_infection" name="episode_of_infection" path="episode_of_infection"/>
			  <form:input type="hidden" id="infection_type_id" name="infection_type_id" path="infection_type_id"/>
			  <form:input type="hidden" id="continued_risk" name="continued_risk" path="continued_risk"/>
			  <form:input type="hidden" id="infection_risk_id" name="infection_risk_id" path="infection_risk_id"/>	
			  <form:input type="hidden" id="sample_collection" name="sample_collection" path="sample_collection"/>	
			  <form:input type="hidden" id="sample_type_id" name="sample_type_id" path="sample_type_id"/>	
			  <form:input type="hidden" id="ir_which_department" name="ir_which_department" path="ir_which_department"/>	
			  <form:input type="hidden" id="ir_deletion_date" name="ir_deletion_date" path="ir_deletion_date"/>	
			  <form:input type="hidden" id="ir_patient_id" name="ir_patient_id" path="ir_patient_id"/>	
		    </div>
		    <div class="text-center">
				<h5 id="infection_risk_buttons_message" style="display:none;"></h5>
		    </div>
			<div id="save_infection_risk_btn_div" class="text-center">
				  <c:choose>
				    <c:when test = "${fn:containsIgnoreCase(consent_access, 'add') || fn:containsIgnoreCase(consent_access, 'edit') || fn:length(various_actions.active_actions) gt 0}">
					  <form:button style="margin-top:5px; background-color:#2E008B;color:#FEFEFE;display:none;" class="btn btn-sm" type="button" 
					  		name="add_another_infection_risk_btn" id="add_another_infection_risk_btn" onclick="processUserSelection(this);">
					  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
					  		<i class="fas fa-plus-circle"></i> Add Infection Risk</form:button>
					  <form:button style="margin-top:5px; background-color: #2E008B;color:#FEFEFE;display:none;" class="btn btn-sm" type="button" 
					  		name="discard_infection_risk_btn" id="discard_infection_risk_btn" onclick="processUserSelection(this)">
					  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
					  		<i class="fas fa-trash-alt"></i> Discard Infection Risk</form:button>
					    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;display:none;" class="btn btn-sm" type="button"
					  		name="edit_consent_btn" id="edit_consent_btn" onclick="processUserSelection(this)">
					  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
					  		<i class="fas fa-file"></i> Back: Consent</form:button>
					    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;display:none" class="btn btn-sm" type="button"
					  		name="edit_patient_btn" id="edit_patient_btn" onclick="validateFormFields('validate_infection_risk',this,'view_patient',true)">
					  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
					  		<i class="fas fa-user"></i> Back: Patient</form:button>
					    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;display:none" class="btn btn-sm" type="button"
					  		name="edit_infection_risk_btn" id="edit_infection_risk_btn" onclick="processUserSelection(this)">
					  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
					  		<i class="fas fa-edit"></i> Edit</form:button>
				        <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;display:none;" class="btn btn-sm" type="button"
				  			name="save_infection_risk_btn" id="save_infection_risk_btn" onclick="validateFormFields('validate_infection_risk',this,'update_infection_risk',true)">
				  			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
				  			<i class="fas fa-save"></i> Save Infection Risk</form:button>
					    <form:button style="margin-top:5px; background-color:#2E008B;color:#FEFEFE;display:none;" class="btn btn-sm" type="button" 
					  		name="save_all_btn" id="save_all_btn" onclick="validateFormFields('validate_infection_risk',this,'save_patient_consent_ir',true)">
					  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
					  		<i class="fas fa-check-double"></i> Finish: Save All</form:button>
					    <a id="infection_risk_cancel_href" href="choose_locations" style="display:none;"><form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;"
					    	class="btn btn-sm" type="button"><i class="fas fa-window-close"></i> Cancel</form:button></a>
					</c:when>
					<c:otherwise>
						<h5>You are NOT authorised to add or edit an infection risk</h5>
					</c:otherwise>
				  </c:choose>
		  	</div>
		</div>
	  </div>
	</div>
  </div>
 </div>
</div>
</body>
</form:form>
</html>