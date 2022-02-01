<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<sec:csrfMetaTags/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<form:form name="consent_audit_form" method="POST" action="save_consent_audit" modelAttribute="session_consent_audit" autocomplete="off">
<div class="content py-5" style="background-color:#EAE8FF;color:#2E008B;">
	<div class="container">
		<div class="row">
           <span class="anchor"></span>
             <div class="card card-outline-secondary">
                 <div class="card-header" style="color: #FEFEFE">
                     <h3 class="mb-0">Consent Audit</h3>
                 </div>
              <div id="consent_audit_body_div" class="card-body">
			 <table class="table table-bordered"> 
			  <tbody>
			    <tr>
			      <td style="width:600px;">
				  <div id="aud_verbal_document_checked_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
				    <label for="aud_verbal_document_checked" class="col-sm-4 col-form-label text-left">Verbal Document Check <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_verbal_document_checked" name="aud_verbal_document_checked" path="aud_verbal_document_checked"  
				      		class="browser-default custom-select custom-select-sm" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
		              <label id="aud_verbal_document_checked-validation" style="color:red;display:none;"></label> 
				    </div>
				  </div>
				  <div id="aud_digital_cf_attached_alert" class="alert alert-danger ml-2" role="alert" style="display:none;">
				  	 Digital CF Attachment Alert Text
				  </div>	          
				  <div id="aud_digital_cf_attached_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
				    <label for="aud_digital_cf_attached" class="col-sm-4 col-form-label text-left">Digital CF Attachment <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_digital_cf_attached" name="aud_digital_cf_attached" path="aud_digital_cf_attached"  
				      		class="browser-default custom-select custom-select-sm" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)"
				      		onchange="hideAndShowContainer(this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
		              <label id="aud_digital_cf_attached-validation" style="color:red; display:none;"></label> 
				    </div>
				  </div>
				  <div id="aud_physical_consent_form_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
				    <label for="aud_physical_consent_form" class="col-sm-4 col-form-label text-left">Physical Consent Form Present? <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_physical_consent_form" name="aud_physical_consent_form" path="aud_physical_consent_form"  
				      		class="browser-default custom-select custom-select-sm" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				        <option value="Unknown">Unknown</option>
				      </form:select>
		              <label id="aud_physical_consent_form-validation" style="color:red; display:none;"></label> 
				    </div>
				  </div>
				  <div id="aud_cf_physical_location_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
				    <label for="aud_cf_physical_location" class="col-sm-4 col-form-label text-left">CF Physical Location <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:textarea id="aud_cf_physical_location" name="aud_cf_physical_location" path="aud_cf_physical_location"
		               		maxlength="200" rows="5" class="form-control form-control-sm floatlabel avoid_pid_data" 
		               		onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)" ></form:textarea>
		               <label id="aud_cf_physical_location-validation" style="color:red; display:none;"></label>
				    </div>
				  </div>
				  <div id="aud_date_of_consent_stated_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
				    <label for="aud_date_of_consent_stated" class="col-sm-4 col-form-label text-left">Date Of Consent Stated <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_date_of_consent_stated" name="aud_date_of_consent_stated" path="aud_date_of_consent_stated"  
				      		class="browser-default custom-select custom-select-sm" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)" >
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				        <option value="Unknown">Unknown</option>
				        <option value="Not applicable">Not applicable</option>
				      </form:select>
		              <label id="aud_date_of_consent_stated-validation" style="color:red; display:none;"></label> 
				    </div>
				  </div>
				  <div id="aud_patient_signature_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
				    <label for="aud_patient_signature" class="col-sm-4 col-form-label text-left">Patient Signature <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_patient_signature" name="aud_patient_signature" path="aud_patient_signature"  
				      		class="browser-default custom-select custom-select-sm" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)" >
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				        <option value="Unknown">Unknown</option>
				        <option value="Not applicable">Not applicable</option>
				      </form:select>
		              <label id="aud_patient_signature-validation" style="color:red; display:none;"></label> 
				    </div>
				  </div>
				  <div id="aud_person_taking_consent_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
				    <label for="aud_person_taking_consent" class="col-sm-4 col-form-label text-left">Person Taking Signature <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_person_taking_consent" name="aud_person_taking_consent" path="aud_person_taking_consent"  
				      		class="browser-default custom-select custom-select-sm" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)" >
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				        <option value="Unknown">Unknown</option>
				        <option value="Not applicable">Not applicable</option>
				      </form:select>
		              <label id="aud_person_taking_consent-validation" style="color:red; display:none;"></label> 
				    </div>
				  </div>
				  <div id="aud_cf_validity_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
				    <label for="aud_cf_validity" class="col-sm-4 col-form-label text-left">CF Validity <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_cf_validity" name="aud_cf_validity" path="aud_cf_validity" class="browser-default custom-select custom-select-sm"
				      	 onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				        <option value="Unknown">Unknown</option>
				        <option value="Not applicable">Not applicable</option>
				      </form:select>
		              <label id="aud_cf_validity-validation" style="color:red; display:none;"></label> 
				    </div>
				  </div>
				</td>
			    <td style="width:600px;">
				  <div id="verify_consent_exclusions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="aud_verify_consent_exclusions" class="col-sm-4 col-form-label text-left">Verify Exclusions <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_verify_consent_exclusions" name="aud_verify_consent_exclusions" path="aud_verify_consent_exclusions"  
				      		class="browser-default custom-select custom-select-sm" onchange="hideAndShowContainer(this);"
				      		 onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				        <option value="Unknown">Unknown</option>
				        <option value="Not applicable">Not applicable</option>
				      </form:select>
		               <label id="aud_verify_consent_exclusions-validation" style="color:red; display:none;"></label>
				    </div>
				  </div>
				  <div id="aud_statements_excluded_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
				    <label for="aud_statements_excluded" class="col-sm-4 col-form-label text-left">Statements Excluded <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:textarea id="aud_statements_excluded" name="aud_statements_excluded" path="aud_statements_excluded" maxlength="200" rows="5" 
		               		class="form-control form-control-sm floatlabel avoid_pid_data" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)" ></form:textarea>
		               <label id="aud_statements_excluded-validation" style="color:red; display:none;"></label>
				    </div>
				  </div>
				  <div id="reapproach_patient_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="reapproach_patient" class="col-sm-4 col-form-label text-left">Re-approach Patient </label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="reapproach_patient" name="reapproach_patient" path="reapproach_patient"  
				      		class="browser-default custom-select custom-select-sm" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)" >
				        <option value=""></option>
				        <option value="No">No</option>
				        <option value="Yes">Yes</option>
				        <option value="Unknown">Unknown</option>
				      </form:select>
				    </div>
				  </div>
				  <div id="reapproach_reason_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="reapproach_reason" class="col-sm-4 col-form-label text-left">Reason For Re-Approach</label>
				    <div class="col-sm-6 col-md-6">
		               <form:textarea id="reapproach_reason" name="reapproach_reason" path="reapproach_reason" 
		               		maxlength="200" rows="5" class="form-control form-control-sm floatlabel avoid_pid_data"
		               		 onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)"></form:textarea>
				    </div>
				  </div>
				  <div id="cf_audit_notes_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="aud_cf_audit_notes" class="col-sm-4 col-form-label text-left">CF Audit Notes</label>
				    <div class="col-sm-6 col-md-6">
		               <form:textarea id="aud_cf_audit_notes" name="aud_cf_audit_notes" path="aud_cf_audit_notes" 
		               		maxlength="200" rows="5" class="form-control form-control-sm floatlabel avoid_pid_data"
		               		 onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)"></form:textarea>
				    </div>
				  </div>
				  <div id="aud_data_discrepancies_identified_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="aud_data_discrepancies_identified" class="col-sm-4 col-form-label text-left">Discrepancies Identified <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_data_discrepancies_identified" name="aud_data_discrepancies_identified" path="aud_data_discrepancies_identified"  
				      		class="browser-default custom-select custom-select-sm" onchange="hideAndShowContainer(this);"
				      		 onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)">
				        <option value=""></option>
				        <option value="No">No</option>
				        <option value="Yes">Yes</option>
				      </form:select>
		              <label id="aud_data_discrepancies_identified-validation" style="color:red; display:none;"></label> 
				    </div>
				  </div>
				  <div id="aud_source_of_verified_data_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
				    <label for="aud_source_of_verified_data" class="col-sm-4 col-form-label text-left">Source Of Verified Data </label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="aud_source_of_verified_data" name="aud_source_of_verified_data" path="aud_source_of_verified_data"  
				      		class="browser-default custom-select custom-select-sm" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)">
				        <option value=""></option>
				        <option value="Physical notes">Physical notes</option>
				        <option value="Email">Email</option>
				        <option value="Millenium">Millenium</option>
				        <option value="Other">Other</option>
				        <option value="Winpath">Winpath</option>
				        <option value="EPR">EPR</option>
				      </form:select>
				    </div>
				  </div>
				  <div id="aud_discrepancies_description_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
				    <label for="discrepancies_description" class="col-sm-4 col-form-label text-left">Discrepancies Description </label>
				    <div class="col-sm-6 col-md-6">
		               <form:textarea id="discrepancies_description" name="discrepancies_description" rows="5" 
		               		path="discrepancies_description" maxlength="200" class="form-control form-control-sm floatlabel avoid_pid_data"
		               		 onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)"></form:textarea>
				    </div>
				  </div>
				</td>
			    <td style="width:600px;">
				  <div id="samples_obtained_electronically_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="samples_obtained_electronically" class="col-sm-4 col-form-label text-left">Samples Obtained Electronically <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="samples_obtained_electronically" name="samples_obtained_electronically" path="samples_obtained_electronically"  
				      		class="browser-default custom-select custom-select-sm" onchange="hideAndShowContainer(this);"
				      		 onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)">
				        <option value=""></option>
				        <option value="No">No</option>
				        <option value="Yes">Yes</option>
				        <option value="Unknown">Unknown</option>
				      </form:select>
		              <label id="samples_obtained_electronically-validation" style="color:red; display:none;"></label> 
				    </div>
				  </div>
				  <div id="prev_audit_samples_chkbox_div" style="display:none;" class="shadow-lg rounded form-group row row-bottom-margin">
		 		    <label for="prev_audit_samples_chkbox" class="col-sm-6 col-form-label text-left">Previously recorded Sample(s)</label> 
				    <div class="col-sm-10 col-md-10">
				    	<ul id="prev_audit_samples_chkbox" style="list-style-type:none;font-size:12px;">
				    	</ul>
		         	</div>
		          </div> 
				  <div id="add_save_discard_sample_div" class="text-center" style="display:none;margin-bottom:5px;">
					  <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button" 
					  		name="add_audit_sample_btn" id="add_audit_sample_btn" onclick="processUserSelection(this);">
					  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
					  		<i class="fas fa-check-circle"></i> Add Sample</form:button>
			        <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="save_audit_sample_btn" id="save_audit_sample_btn" onclick="validateFormFields('validate_audit_sample',this,null,false)">
			  			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
			  			<i class="fas fa-check-circle"></i> Save Sample</form:button>
				  <form:button style="margin-top:5px; background-color: #2E008B;color:#FEFEFE;" class="btn btn-sm" type="button" 
				  		name="discard_audit_sample_btn" id="discard_audit_sample_btn" onclick="processUserSelection(this)">
				  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
				  		<i class="fas fa-trash-alt"></i> Discard Sample</form:button>
				  </div>
				  <div id="audit_sample_div" class="border border-info rounded" style="display:none;"> 
					  <div id="aud_sample_id_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="aud_sample_id" class="col-sm-4 col-form-label text-left">Sample ID <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
				             <form:input type="text" id="aud_sample_id" name="aud_sample_id" maxlength="30" path=""
				             	    class="form-control form-control-sm floatlabel"></form:input>
			                  <label id="aud_sample_id-validation" style="color:red;display:none;"></label> 
					    </div>
					  </div>
					  <div id="aud_select_sample_type_id_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="aud_select_sample_type_id" class="col-sm-4 col-form-label text-left">Sample Type
					    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
					      <form:select id="aud_select_sample_type_id" name="aud_select_sample_type_id" path="" 
					      		class="browser-default custom-select custom-select-sm">
					          <option value=""></option>
							  <c:forEach items="${auditSampleTypes}" var = "st">
						          <option value="${st.audit_sample_type_id}">${st.description}</option>
							  </c:forEach>
					      </form:select>
			              <label id="aud_select_sample_type_id-validation" style="color:red; display:none;"></label> 
					    </div>
					  </div>
					  <div id="sample_date_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="sample_date" class="col-sm-4 col-form-label text-left">Sample Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
			               <form:input type="date" id="sample_date" name="sample_date" path="" maxlength="10" placeholder="dd-MM-YYYY"
			               		class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
			               <label id="sample_date-validation" style="color:red; display:none;"></label> 
				        </div>
					  </div>
					  <div id="after_consent_date_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="after_consent_date" class="col-sm-4 col-form-label text-left">Sample Collected After Consent Date 
					    		<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
					      <form:select id="after_consent_date" name="after_consent_date" 
					      		path="" class="browser-default custom-select custom-select-sm" >
					        <option value=""></option>
					        <option value="No">No</option>
					        <option value="Yes">Yes</option>
					        <option value="Unknown">Unknown</option>
					      </form:select>
			              <label id="after_consent_date-validation" style="color:red; display:none;"></label> 
					    </div>
					  </div>
					  <div id="sample_in_assigned_location_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="sample_in_assigned_location" class="col-sm-4 col-form-label text-left">Sample In Assigned Location 
					    		<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
					      <form:select id="sample_in_assigned_location" name="sample_in_assigned_location" 
					      		path="" class="browser-default custom-select custom-select-sm" >
					        <option value=""></option>
					        <option value="No">No</option>
					        <option value="Yes">Yes</option>
					        <option value="Unknown">Unknown</option>
					        <option value="Not Applicable">Not Applicable</option>
					      </form:select>
			              <label id="sample_in_assigned_location-validation" style="color:red; display:none;"></label> 
					    </div>
					  </div>
					  <div id="sample_details_legible_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="sample_details_legible" class="col-sm-4 col-form-label text-left">Sample Details Legible 
					    		<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
					      <form:select id="sample_details_legible" name="sample_details_legible" 
					      		path="" class="browser-default custom-select custom-select-sm" >
					        <option value=""></option>
					        <option value="No">No</option>
					        <option value="Yes">Yes</option>
					        <option value="Unknown">Unknown</option>
					        <option value="Not Applicable">Not Applicable</option>
					      </form:select>
			              <label id="sample_details_legible-validation" style="color:red; display:none;"></label> 
					    </div>
					  </div>
					  <div id="appropriate_consent_present_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="appropriate_consent_present" class="col-sm-4 col-form-label text-left">Appropriate Consent 
					    		<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
					      <form:select id="appropriate_consent_present" name="appropriate_consent_present" 
					      		path="" class="browser-default custom-select custom-select-sm" >
					        <option value=""></option>
					        <option value="No">No</option>
					        <option value="Yes">Yes</option>
					        <option value="Unknown">Unknown</option>
					      </form:select>
			              <label id="appropriate_consent_present-validation" style="color:red;display:none;"></label> 
					    </div>
					  </div>
					  <div id="non_conformances_details_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="non_conformances_details" class="col-sm-4 col-form-label text-left">Non-Conformances Details</label>
					    <div class="col-sm-6 col-md-6">
			               <form:textarea id="non_conformances_details" name="non_conformances_details" path="" 
			               		maxlength="200" rows="5" class="form-control form-control-sm floatlabel avoid_pid_data"></form:textarea>
					    </div>
					  </div>
					  <form:input type="hidden" id="aud_sample_pid" name="aud_sample_pid" path=""/>
					  <form:input type="hidden" id="aud_sample_type_id" name="aud_sample_type_id" path=""/>
					  <form:input type="hidden" id="ca_id" name="ca_id" path=""/>
				  </div>
				  <div id="sample_missing_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="sample_missing" class="col-sm-4 col-form-label text-left">Any Sample Missing? 
				    		<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="sample_missing" name="sample_missing" path="sample_missing" class="browser-default custom-select custom-select-sm" 
				       	onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)">
				        <option value=""></option>
				        <option value="No">No</option>
				        <option value="Yes">Yes</option>
				        <option value="Unknown">Unknown</option>
				        <option value="Not Applicable">Not Applicable</option>
				      </form:select>
		              <label id="sample_missing-validation" style="color:red; display:none;"></label> 
				    </div>
				  </div>
				  <div id="primary_auditor_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="primary_auditor" class="col-sm-4 col-form-label text-left">Primary Auditor <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
			             <form:input type="text" id="primary_auditor" name="primary_auditor" maxlength="30" path="primary_auditor" style="text-transform:capitalize;"
			             	  class="form-control form-control-sm floatlabel" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)"></form:input>
		                  <label id="primary_auditor-validation" style="color:red;display:none;"></label> 
				    </div>
				  </div>
				  <div id="secondary_auditor_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="secondary_auditor" class="col-sm-4 col-form-label text-left">Secondary Auditor <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
			             <form:input type="text" id="secondary_auditor" name="secondary_auditor" maxlength="30" path="secondary_auditor" style="text-transform:capitalize;"
			             	  class="form-control form-control-sm floatlabel" onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)"></form:input>
		                  <label id="secondary_auditor-validation" style="color:red;display:none;"></label> 
				    </div>
				  </div>
				  <div id="audit_date_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="audit_date" class="col-sm-4 col-form-label text-left">Audit Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:input type="date" id="audit_date" name="audit_date" path="audit_date"
		               		placeholder="dd-MM-YYYY" class="form-control form-control-sm floatlabel validate_this_date"
		               		 onblur="uploadFormDataToSessionObjects('CONSENT-AUDIT',this)"></form:input> 
		               <label id="audit_date-validation" style="color:red; display:none;"></label> 
			        </div>
				  </div>
			    </td>
			   </tr>
			 </tbody>
			</table>
		  <div id="audit_save_finalise_btn_div" class="text-center">
		    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  		id="finalise_consent_audit_btn" name="finalise_consent_audit_btn" 
		  		onclick="validateFormFields('validate_consent_audit_verify',this,'finalise_consent_audit',false)">
		  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
		  		<i class="fas fa-file-invoice"></i> Finalise Audit</form:button>
		    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  		name="save_consent_audit_btn" id="save_consent_audit_btn" onclick="processUserSelection(this)">
		  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
		  		<i class="fas fa-save"></i> Save Audit</form:button>
		    <a id="cancel_consent_audit_href" href="welcome"><form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;"
		    	class="btn btn-sm" type="button"><i class="fas fa-window-close"></i> Cancel</form:button></a>
           </div>
		  <form:input type="hidden" id="consent_audit_id" name="consent_audit_id" path="consent_audit_id"/>
		  <form:input type="hidden" id="ca_consent_id" name="ca_consent_id" path="ca_consent_id"/>
		  <form:input type="hidden" id="ca_which_department" name="ca_which_department" path="ca_which_department"/>
          </div>
         </div>
       </div>
     </div>
   </div>
  </form:form>
 </body>
</html>