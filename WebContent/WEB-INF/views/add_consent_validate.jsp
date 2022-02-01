<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<sec:csrfMetaTags />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<form:form name="consent_validate_form" method="POST" action="save_consent_validate" modelAttribute="consent_validate" autocomplete="off">
<body>
<div class="content py-5" style="background-color:#EAE8FF;color:#2E008B;">
	<div class="container">
		<div class="row">
           <span class="anchor"></span>
             <div class="card card-outline-secondary">
                 <div class="card-header" style="color: #FEFEFE">
                     <h3 class="mb-0">Check consent validity and data accuracy</h3>
              </div>
              <div id="consent_validate_body_div" class="card-body">
			 <table class="table table-bordered"> 
			  <tbody>
			    <tr>
			      <td style="width:600px;">
				  <div id="verbal_document_checked_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="verbal_document_checked" class="col-sm-5 col-form-label text-left">Verbal Document Check <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="verbal_document_checked" name="verbal_document_checked" path="verbal_document_checked"  
				      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
		              <label id="verbal_document_checked-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Has the documentation of verbal consent been checked?">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="verbal_consent_checked_date_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="verbal_consent_checked_date" class="col-sm-5 col-form-label text-left">Verbal Check Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:input type="date" id="verbal_consent_checked_date" name="verbal_consent_checked_date"  
		               		path="verbal_consent_checked_date" onblur="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this);" 
		               		placeholder="dd-MM-YYYY" class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
		               <label id="verbal_consent_checked_date-validation" style="color:red;display:none;"></label> 
			        </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
				    	data-content="The date the person checked the documentation of verbal consent">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="verbal_consent_checked_by_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="verbal_consent_checked_by" class="col-sm-5 col-form-label text-left">Verbal Checked By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:input type="text" id="verbal_consent_checked_by" name="verbal_consent_checked_by" path="verbal_consent_checked_by" 
		               		style="text-transform: capitalize;" maxlength="50" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)"
		               		class="form-control form-control-sm floatlabel"></form:input> 
		              <label id="verbal_consent_checked_by-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
				    	data-content="The initials of person checking the documentation of verbal consent">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="digital_cf_attached_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="digital_cf_attached" class="col-sm-5 col-form-label text-left">Digital CF Attachment <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="digital_cf_attached" name="digital_cf_attached" path="digital_cf_attached"  
				      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
		              <label id="digital_cf_attached-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Whether or not the Digital CF is attached">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="cf_physical_location_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="cf_physical_location" class="col-sm-5 col-form-label text-left">CF Physical Location <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:textarea id="cf_physical_location" name="cf_physical_location" path="cf_physical_location"
		               		maxlength="200" rows="5" class="form-control form-control-sm floatlabel avoid_pid_data"></form:textarea>
		               <label id="cf_physical_location-validation" style="color:red; display: none;"></label>
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
				    	data-content="The folder where the hard copy of CF is stored">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="date_of_consent_stated_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="date_of_consent_stated" class="col-sm-5 col-form-label text-left">Date Of Consent Stated <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="date_of_consent_stated" name="date_of_consent_stated" path="date_of_consent_stated"  
				      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
		              <label id="date_of_consent_stated-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is there at least one date of consent provided?">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="patient_signature_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="patient_signature" class="col-sm-5 col-form-label text-left">Patient Signature <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="patient_signature" name="patient_signature" path="patient_signature"  
				      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
		              <label id="patient_signature-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is there a patient signature?">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="person_taking_consent_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="person_taking_consent" class="col-sm-5 col-form-label text-left">Person Taking Signature <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="person_taking_consent" name="person_taking_consent" path="person_taking_consent"  
				      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
		              <label id="person_taking_consent-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is there a signature of the person taking consent?">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="cf_validity_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="cf_validity" class="col-sm-5 col-form-label text-left">CF Validity <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="cf_validity" name="cf_validity" path="cf_validity" class="browser-default custom-select custom-select-sm" 
				      		onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
		              <label id="cf_validity-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is the consent form valid?">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="cf_checked_date_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="cf_checked_date" class="col-sm-5 col-form-label text-left">CF Checked Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
			              <form:input type="date" id="cf_checked_date" name="cf_checked_date" path="cf_checked_date" 
			              		onblur="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this);"  
			              		placeholder="dd-MM-YYYY" class="form-control form-control-sm floatlabel validate_this_date"></form:input>
		                  <label id="cf_checked_date-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
				    	data-content="The date the person checked the existance and location of the CF">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="cf_checked_by_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="cf_checked_by" class="col-sm-5 col-form-label text-left">CF Checked By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:input type="text" id="cf_checked_by" name="cf_checked_by" path="cf_checked_by" style="text-transform: capitalize;"
		               		maxlength="50" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)"></form:input> 
		              	   <label id="cf_checked_by-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
				    	data-content="The initials of person checking the existance and location of the CF">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				</td>
			    <td style="width:600px;">
				  <div id="verify_consent_exclusions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="verify_consent_exclusions" class="col-sm-4 col-form-label text-left">Verify Exclusions <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="verify_consent_exclusions" name="verify_consent_exclusions" path="verify_consent_exclusions"  
				      		class="browser-default custom-select custom-select-sm" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects('CONSENT-VALIDATE',this);">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
		               <label id="verify_consent_exclusions-validation" style="color:red; display: none;"></label>
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is the consent form valid?">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="statements_excluded_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
				    <label for="statements_excluded" class="col-sm-4 col-form-label text-left">Statements Excluded <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:textarea id="statements_excluded" name="statements_excluded" path="statements_excluded" maxlength="200" rows="5" 
		               		class="form-control form-control-sm floatlabel avoid_pid_data" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)"></form:textarea>
		               <label id="statements_excluded-validation" style="color:red; display: none;"></label>
				    </div>
				  </div>
				  <div id="cf_audit_notes_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="cf_audit_notes" class="col-sm-4 col-form-label text-left">CF Audit Notes</label>
				    <div class="col-sm-6 col-md-6">
		               <form:textarea id="cf_audit_notes" name="cf_audit_notes" path="cf_audit_notes" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)"
		               		maxlength="200" rows="5" class="form-control form-control-sm floatlabel avoid_pid_data"></form:textarea>
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Consent form audit notes">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="data_discrepancies_identified_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="data_discrepancies_identified" class="col-sm-5 col-form-label text-left">Discrepancies Identified <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="data_discrepancies_identified" name="data_discrepancies_identified" path="data_discrepancies_identified"  
				      		class="browser-default custom-select custom-select-sm" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects('CONSENT-VALIDATE',this);">
				        <option value=""></option>
				        <option value="No">No</option>
				        <option value="Yes">Yes</option>
				      </form:select>
		              <label id="data_discrepancies_identified-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Have data discrepanicies e.g. Hospital number, been identified?">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="data_accuracy_date_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="data_accuracy_date" class="col-sm-5 col-form-label text-left">Accuracy Check Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:input type="date" id="data_accuracy_date" name="data_accuracy_date" path="data_accuracy_date"
		               		onblur="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this);"  
		               		placeholder="dd-MM-YYYY" class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
		               <label id="data_accuracy_date-validation" style="color:red; display: none;"></label> 
			        </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
				    	data-content="The date the person checked the accuracy of the data">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="data_accuracy_checked_by_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
				    <label for="data_accuracy_checked_by" class="col-sm-5 col-form-label text-left">Accuracy Checked By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:input type="text" id="data_accuracy_checked_by" name="data_accuracy_checked_by" path="data_accuracy_checked_by" 
		               		style="text-transform: capitalize;" maxlength="50" class="form-control form-control-sm floatlabel" 
		               		onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)"></form:input> 
		              	   <label id="data_accuracy_checked_by-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
				    	data-content="The initials of the person who checked the accuracy of the data">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="source_of_verified_data_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
				    <label for="source_of_verified_data" class="col-sm-5 col-form-label text-left">Source Of Verified Data <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="source_of_verified_data" name="source_of_verified_data" path="source_of_verified_data"  
				      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)">
				        <option value=""></option>
				        <option value="Physical notes">Physical notes</option>
				        <option value="Email">Email</option>
				        <option value="Millenium">Millenium</option>
				        <option value="Other">Other</option>
				        <option value="Winpath">Winpath</option>
				        <option value="EPR">EPR</option>
				      </form:select>
		              <label id="source_of_verified_data-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="What is the source of the verified data? E.g. EPR">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="data_discrepancies_description_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
				    <label for="data_discrepancies_description" class="col-sm-5 col-form-label text-left">Discrepancies Description 
				    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
				    <div class="col-sm-6 col-md-6">
		               <form:textarea id="data_discrepancies_description" name="data_discrepancies_description" rows="5" 
		               		path="data_discrepancies_description" maxlength="200" class="form-control form-control-sm floatlabel avoid_pid_data" 
		               		onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)"></form:textarea>
		               <label id="data_discrepancies_description-validation" style="color:red; display: none;"></label> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Description of data discrepancies e.g. Change hospital no to match EPR.">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="data_discrepancies_verified_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
				    <label for="data_discrepancies_verified" class="col-sm-5 col-form-label text-left">Discrepancies Verified</label>
				    <div class="col-sm-6 col-md-6">
				      <form:select id="data_discrepancies_verified" name="data_discrepancies_verified" path="data_discrepancies_verified"  
				      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)">
				        <option value=""></option>
				        <option value="Yes">Yes</option>
				        <option value="No">No</option>
				      </form:select>
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is the consent form valid?">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="data_discrepancies_verification_date_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
				    <label for="data_discrepancies_verification_date" class="col-sm-5 col-form-label text-left">Discrepancies Date</label>
				    <div class="col-sm-6 col-md-6">
		               <form:input type="date" id="data_discrepancies_verification_date" name="data_discrepancies_verification_date" 
		               		path="data_discrepancies_verification_date" onblur="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this);" 
		               		 placeholder="dd-MM-YYYY" class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
		              <label id="data_discrepancies_verification_date-validation" style="color:red; display: none;"></label> 
			        </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
				    	data-content="The date the person checked the discrepancies of the data">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				  <div id="data_discrepancies_verified_by_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
				    <label for="data_discrepancies_verified_by" class="col-sm-5 col-form-label text-left">Discrepancies Verified By</label>
				    <div class="col-sm-6 col-md-6">
		               <form:input type="text" id="data_discrepancies_verified_by" name="data_discrepancies_verified_by" path="data_discrepancies_verified_by" 
		               		style="text-transform: capitalize;" maxlength="50" class="form-control form-control-sm floatlabel"
		               		onchange="uploadFormDataToSessionObjects('CONSENT-VALIDATE',this)"></form:input> 
				    </div>
				    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
				    	data-content="The initials of the person who checked the discrepancies of the data">
						<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
							<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
				    </span>
				  </div>
				</td>
			   </tr>
			 </tbody>
			</table>
		  <div id="save_consent_validate_div" class="text-center" style="display:none;">
		    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  		id="save_consent_validate_btn" name="save_consent_validate_btn" 
		  		onclick="validateFormFields('validate_exported_consent_verify',this,'finalise_exported_consent',true)">
		  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
		  		<i class="fas fa-edit"></i> Save Consent Validation</form:button>
           </div>
		  <form:input type="hidden" id="consent_validate_id" name="consent_validate_id" path="consent_validate_id"/>
		  <form:input type="hidden" id="cv_consent_id" name="cv_consent_id" path="cv_consent_id"/>
		  <form:input type="hidden" id="cv_which_department" name="cv_which_department" path="cv_which_department"/>
          </div>
         </div>
       </div>
     </div>
   </div>
  </body>
</form:form>
</html>