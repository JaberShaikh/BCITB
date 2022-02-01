<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
</head>
<form:form action="save_patient_consent_ir" method="POST" modelAttribute="patient_consent_ir" enctype="multipart/form-data">
<body onload="reloadPatientConsentInfectionRiskData();">
<div class="row">
 <table class="table table-bordered"> 
  <tbody>
    <tr>
      <td style="background-color: #513CA1; color: #FEFEFE; width: 400px;">
       	<h4 class="text-center">Patient</h4>
         <fieldset>
 		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="pat_id" class="col-sm-4 col-form-label text-left">Pat ID</label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="pat_id" name="pat_id" path="patient.pat_id" class="form-control form-control-sm floatlabel" 
	              		title="Pat ID is always disabled" readonly="true"></form:input>
		    </div>
		  </div>
			  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="secondary_id" class="col-sm-4 col-form-label text-left">Secondary ID</label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="secondary_id" name="secondary_id" path="patient.secondary_id" class="form-control form-control-sm floatlabel" 
	              		title="Secondary ID is always disabled" readonly="true"></form:input>
		    </div>
		  </div>  
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="surname" class="col-sm-4 col-form-label text-left">Surname <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="surname" name="surname" placeholder="Enter Surname" maxlength="30" path="patient.surname"
	              	  onchange="processPatientConsentInfectionRisk('PATIENT',this,true);" class="form-control form-control-sm floatlabel"></form:input>
	              <span style="color:red" class="surname-validation validation-error"></span> 
		    </div>
		  </div>
 		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="forename" class="col-sm-4 col-form-label text-left">Forename <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="forename" name="forename" path="patient.forename" placeholder="Enter Forename" maxlength="30" 
	              		class="form-control form-control-sm floatlabel"></form:input>
	              <span style="color:red" class="forename-validation validation-error"></span> 
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="volunteer" class="col-sm-4 col-form-label text-left">Volunteer</label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="volunteer" name="volunteer" onchange="hideAndShowContainer(this)" path="patient.volunteer"  
		      		class="browser-default custom-select custom-select-sm">
		        <option value="No" selected="selected">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
		    </div>
		  </div>
		  <div id="select_age_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="select_age" class="col-sm-4 col-form-label text-left">Age</label>
		    <div class="col-sm-4 col-md-4">
		      <select id="select_age" name="select_age" class="browser-default custom-select custom-select-sm" onchange="processPatientConsentInfectionRisk('PATIENT',this,true);" >
		        <option value=""></option>
		      	<c:forEach var = "age_num" begin = "18" end = "110">
			        <option value="${age_num}">${age_num}</option>
		      	</c:forEach>
		      </select>
		    </div>
		  </div>
		  <div id="date_of_birth_div" class="form-group row row-bottom-margin justify-content-center">
		    <label for="date_of_birth" class="col-sm-4 col-form-label text-left">Date Of Birth <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="date_of_birth" name="date_of_birth" path="patient.date_of_birth" placeholder="Enter Date Of Birth" 
	              		onchange="processPatientConsentInfectionRisk('PATIENT',this,true);" class="date form-control form-control-sm floatlabel"></form:input>
		       <span style="color:red" class="date_of_birth-validation validation-error"></span>
		    </div>
		  </div>
		  <div id="hospital_number_div" class="form-group row row-bottom-margin justify-content-center">
		    <label for="hospital_number" class="col-sm-4 col-form-label text-left">Hospital No <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="hospital_number" name="hospital_number" path="patient.hospital_number" placeholder="Enter Hospital Number" maxlength="30" 
	              		onchange="processPatientConsentInfectionRisk('PATIENT',this,true);" class="form-control form-control-sm floatlabel"></form:input>
	              <span style="color:red" class="hospital_number-validation validation-error"></span> 
		    </div>
		  </div>
		  <div id="nhs_number_div" class="form-group row row-bottom-margin justify-content-center">
		    <label for="nhs_number" class="col-sm-4 col-form-label text-left">NHS Number <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="nhs_number" name="nhs_number" path="patient.nhs_number" placeholder="Enter NHS Number" maxlength="30" 
	              		onchange="processPatientConsentInfectionRisk('PATIENT',this,true);" class="form-control form-control-sm floatlabel"></form:input>
	              <span style="color:red" class="nhs_number-validation validation-error"></span> 
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="tissue_bank_number" class="col-sm-4 col-form-label text-left">Tissue Bank No</label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="tissue_bank_number" name="tissue_bank_number" path="patient.tissue_bank_number" placeholder="Enter Tissue Bank Number" 
	              		maxlength="30" class="form-control form-control-sm floatlabel"></form:input> 
		    </div>
	  	  </div>
	  	  <form:input type="hidden" name="age" id="age" path="patient.age"></form:input> 
	  	  <form:input type="hidden" name="patient_id" id="patient_id" path="patient.patient_id"></form:input> 
     	</fieldset>
  </td>
     <td style="background-color: #EAE8FF; color: #2EOO8B; width: 600px;">
       	 <h4 class="text-center">Consent</h4>
          <fieldset>
		  <div id="prev_consent_list_div" style="display: none; border: 2px solid;" class="form-group row row-bottom-margin justify-content-center">
 		    <label for="prev_consent_list" class="col-sm-6 col-form-label text-left">Previous Consent form(s)</label> 
		    <div class="col-sm-10 col-md-10">
		    	<ul id="prev_consent_list" style="list-style-type: none; font-size: 15px;">
		    	</ul>
         	</div>
          </div> 
          <br>
		  <div id="date_of_consent_div" class="form-group row row-bottom-margin justify-content-center">
		    <label for="date_of_consent" class="col-sm-4 col-form-label text-left">Date Of Consent <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="date_of_consent" name="date_of_consent" path="consent.date_of_consent" placeholder="Enter Date Of Consent" 
               		class="date form-control form-control-sm floatlabel"></form:input> 
		       <span style="color:red" class="date_of_consent-validation validation-error"></span>
		    </div>
		  </div>
		  <div id="consent_taken_by_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="consent_taken_by" class="col-sm-4 col-form-label text-left">Consent Taken By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="consent_taken_by" name="consent_taken_by" path="consent.consent_taken_by" 
               		placeholder="Enter Consent Taken By" maxlength="50" class="form-control form-control-sm floatlabel"></form:input> 
               <span style="color:red" class="consent_taken_by-validation validation-error"></span> 
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="loc_id" class="col-sm-4 col-form-label text-left">Location <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="loc_id" name="loc_id" path="consent.loc_id" class="browser-default custom-select custom-select-sm">
				  <c:forEach items="${user_locs}" var = "loc">
			          <option value="${loc.loc_id}">${loc.loc_name}</option>
				  </c:forEach>
		      </form:select>
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="verbal_consent" class="col-sm-4 col-form-label text-left">Verbal Consent <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="verbal_consent" name="verbal_consent" path="consent.verbal_consent" onchange="hideAndShowContainer(this)" 
		      		class="browser-default custom-select custom-select-sm">
		        <option value="No">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
		    </div>
		  </div>
		  <div id="verbal_consent_recorded_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="verbal_consent_recorded" class="col-sm-4 col-form-label text-left">Verbal Consent Recorded <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="verbal_consent_recorded" name="verbal_consent_recorded" path="consent.verbal_consent_recorded" 
		      		class="browser-default custom-select custom-select-sm">
		        <option value="Physical notes">Physical notes</option>
		        <option value="Email">Email</option>
		        <option value="Millenium">Millenium</option>
		        <option value="Other">Other</option>
		      </form:select>
		    </div>
		  </div>
		  <div id="verbal_consent_recorded_by_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="verbal_consent_recorded_by" class="col-sm-4 col-form-label text-left">Verbal Consent Recorded By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="verbal_consent_recorded_by" name="verbal_consent_recorded_by" path="consent.verbal_consent_recorded_by" 
               		placeholder="Enter Verbal Consent Recorded By" maxlength="30" class="form-control form-control-sm floatlabel"></form:input> 
               <span style="color:red" class="verbal_consent_recorded_by-validation validation-error"></span> 
		    </div>
		  </div>
		  <div id="verbal_consent_document_file_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="verbal_consent_document_file" class="col-sm-4 col-form-label text-left">Verbal Consent Document <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="file" id="verbal_consent_document_file" name="verbal_consent_document_file" path=""
               		placeholder="Upload Verbal Consent Document" class="form-control form-control-sm floatlabel"></form:input> 
               <span style="color:red" class="verbal_consent_document-validation validation-error"></span> 
		    </div>
		  </div>
		  <div id="form_type_div" class="form-group row row-bottom-margin justify-content-center">
		    <label for="form_type" class="col-sm-4 col-form-label text-left">Form Type <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="form_type" name="form_type" path="consent.form_type" class="browser-default custom-select custom-select-sm">
		        <option value="Patient">Patient</option>
		        <option value="Volunteer">Volunteer</option>
		      </form:select>
		    </div>
		  </div>
		  <div id="form_version_div" class="form-group row row-bottom-margin justify-content-center">
		    <label for="form_version_id" class="col-sm-4 col-form-label text-left">Form Version </label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="form_version_id" name="form_version_id" path="consent.form_version_id"
		      		onchange="processPatientConsentInfectionRisk('FORM-VERSION', this, false)" 
		      		class="browser-default custom-select custom-select-sm">
				  <c:forEach items="${form_versions}" var = "form_version">
			          <option value="${form_version.form_version_id}">${form_version.description}</option>
				  </c:forEach>
		      </form:select>
		    </div>
		  </div>
		  <div id="digital_cf_attachment_file_div" class="form-group row row-bottom-margin justify-content-center">
		    <label for="digital_cf_attachment_file" class="col-sm-4 col-form-label text-left">Digital CF Attachment</label>
		    <div class="col-sm-4 col-md-4">
               <input type="file" id="digital_cf_attachment_file" name="digital_cf_attachment_file"
               		placeholder="Upload Digital CF Attachment" class="form-control form-control-sm floatlabel"></input> 
		    </div>
		  </div>
		  <c:if  test = "${show_sample_consent_to == true}">
			  <div class="form-group row row-bottom-margin justify-content-center">
			    <label for="samples_consented_to" class="col-sm-4 col-form-label text-left">Samples Consented To</label>
			    <div class="col-sm-4 col-md-4">
	               <form:input type="text" id="samples_consented_to" name="samples_consented_to" path="consent.samples_consented_to"  
	               		placeholder="Enter Samples Consented To" value="All Tissue" maxlength="30" class="form-control form-control-sm floatlabel"></form:input> 
	               <span style="color:red" class="samples_consented_to-validation validation-error"></span> 
			    </div>
			  </div>
		  </c:if>
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="consent_type" class="col-sm-4 col-form-label text-left">Type Of Consent <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="consent_type" name="consent_type" path="consent.consent_type" onchange="hideAndShowContainer(this)" 
		      		class="browser-default custom-select custom-select-sm">
		        <option value="Full">Full</option>
		        <option value="Partial">Partial</option>
		      </form:select>
		    </div>
		  </div>
		  <div id="selected_exclusions_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="selected_exclusions" class="col-sm-4 col-form-label text-left">Consent Exclusions <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="selected_exclusions" name="selected_exclusions" path="" multiple="true" class="selectpicker" data-width="100px" data-actions-box="true">
		      </form:select>
		      <span style="color:red" class="selected_exclusions-validation validation-error"></span>
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="exclusions_comment" class="col-sm-4 col-form-label text-left">Exclusion Comments</label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="exclusions_comment" name="exclusions_comment" path="consent.exclusions_comment" 
               		placeholder="Enter Consent Exclusion Comments" maxlength="100" class="form-control form-control-sm floatlabel"></form:textarea> 
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="notes" class="col-sm-4 col-form-label text-left">Notes</label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="notes" name="notes" path="consent.notes" placeholder="Enter Notes" maxlength="100"
               		class="form-control form-control-sm floatlabel"></form:textarea> 
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="withdrawn" class="col-sm-4 col-form-label text-left">Consent Withdrawal <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="withdrawn" name="withdrawn" onchange="hideAndShowContainer(this)" path="consent.withdrawn" 
		      		class="browser-default custom-select custom-select-sm">
		        <option value="No">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
		    </div>
		  </div>
		  <div id="withdrawal_date_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="withdrawal_date" class="col-sm-4 col-form-label text-left">Withdrawn Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="withdrawal_date" name="withdrawal_date" path="consent.withdrawal_date" placeholder="Enter Consent Withdrawal Date" 
               		class="date form-control form-control-sm floatlabel"></form:input> 
		       <span style="color:red" class="withdrawn_date-validation validation-error"></span>
		    </div>
		  </div>
		  <div id="withdrawal_document_file_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="withdrawal_document_file" class="col-sm-4 col-form-label text-left">Withdrawn Document <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="file" id="withdrawal_document_file" name="withdrawal_document_file" placeholder="Upload Consent Withdrawal Document" path=""
               		class="form-control form-control-sm floatlabel"></form:input>
               <span style="color:red" class="withdrawal_document-validation validation-error"></span> 
		    </div>
		  </div>
         </fieldset>
       </td>
     <td style="background-color: #F477CD; width: 500px;">
       	 <h4 class="text-center">Infection Risk</h4>
          <fieldset>
		  <div id="prev_infection_risk_div" style="display: none; border: 2px solid;" class="form-group row row-bottom-margin justify-content-center">
 		    <label for="prev_infection_risk" class="col-sm-6 col-form-label text-left">Previously recorded Infection Risk(s)</label> 
		    <div class="col-sm-10 col-md-10">
		    	<ul id="prev_infection_risk" style="list-style-type: none; font-size: 15px;">
		    	</ul>
         	</div>
          </div> 
          <br>
		  <div class="form-group row row-bottom-margin justify-content-center">
		    <label for="infection_risk_exist" class="col-sm-6 col-form-label text-left">Infection Risk <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="infection_risk_exist" name="infection_risk_exist" path="infection_risk.infection_risk_exist" onchange="hideAndShowContainer(this)" 
		      		class="browser-default custom-select custom-select-sm">
		        <option value="No (Tests = Negative)" selected="selected">No (Tests = Negative)</option>
		        <option value="Yes (Tests = Positive)">Yes (Tests = Positive)</option>
		        <option value="No infection risks recorded">No infection risks recorded</option>
		        <option value="Unknown - possible risk">Unknown - possible risk</option>
		      </form:select>
		    </div>
		  </div>
		  <div id="select_type_of_infection_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="select_type_of_infection" class="col-sm-6 col-form-label text-left">Type Of Infection <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="select_type_of_infection" name="select_type_of_infection" path="" onchange="hideAndShowContainer(this)"
		      		class="browser-default custom-select custom-select-sm">
		        <option value="Hepatitis B" selected="selected">Hepatitis B</option>
		        <option value="Hepatitis A">Hepatitis A</option>
		        <option value="HIV">HIV</option>
		        <option value="TB">TB</option>
		        <option value="Human T-lymphotrophic virus">Human T-lymphotrophic virus</option>
		        <option value="Other">Other</option>
 		        <option value="Ongoing Previous Infection Risk">Ongoing Previous Infection Risk</option>
		      </form:select>
		    </div>
		  </div>
		  <div id="select_ongoing_prev_ir_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="select_ongoing_prev_ir" class="col-sm-6 col-form-label text-left">Previous Infection Risk <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="select_ongoing_prev_ir" name="select_ongoing_prev_ir" path="" onchange="hideAndShowContainer(this)" 
		      		class="browser-default custom-select custom-select-sm">
		      </form:select>
               <span style="color:red" class="select_ongoing_prev_ir-validation validation-error"></span> 
		    </div>
		  </div>
		  <div id="other_infection_risk_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="other_infection_risk" class="col-sm-6 col-form-label text-left">Other Infection Risk <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="other_infection_risk" name="other_infection_risk" path="infection_risk.other_infection_risk"
               		placeholder="Enter Other Infection Risk" maxlength="20" class="form-control form-control-sm floatlabel"></form:input> 
               <span style="color:red" class="other_infection_risk-validation validation-error"></span> 
		    </div>
		  </div>
		  <div id="comments_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="comments" class="col-sm-6 col-form-label text-left">Comments</label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="comments" name="comments" placeholder="Enter Comments Or Correspondence" path="infection_risk.comments"
               		maxlength="200" class="form-control form-control-sm floatlabel"></form:textarea> 
		    </div>
		  </div>
		  <div id="select_episode_of_infection_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="select_episode_of_infection" class="col-sm-6 col-form-label text-left">Episode Of Infection <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="select_episode_of_infection" name="select_episode_of_infection" path="" 
		      		class="browser-default custom-select custom-select-sm">
		      	<c:forEach var = "risk_num" begin = "1" end = "4">
			        <option value="${risk_num}">${risk_num}</option>
		      	</c:forEach>
		      </form:select>
		    </div>
		  </div>
		  <div id="episode_start_date_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="episode_start_date" class="col-sm-6 col-form-label text-left">Episode Start Date<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="episode_start_date" name="episode_start_date" placeholder="Enter Episode Start Date" path="infection_risk.episode_start_date"
               		class="date form-control form-control-sm floatlabel"></form:input> 
		       <span style="color:red" class="episode_start_date-validation validation-error"></span>
		    </div>
		  </div>
		  <div id="select_on_going_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="select_on_going" class="col-sm-6 col-form-label text-left">On Going</label>
		    <div class="col-sm-1 col-md-4">
              <form:checkbox id="select_on_going" name="select_on_going" value="No" onchange="hideAndShowContainer(this);" path="" 
              		class="form-control form-control-sm floatlabel"></form:checkbox> 
		    </div>
		  </div>
		  <div id="episode_finished_date_div" class="form-group row row-bottom-margin justify-content-center" style="display: none;">
		    <label for="episode_finished_date" class="col-sm-6 col-form-label text-left">Episode Finished Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="episode_finished_date" name="episode_finished_date" placeholder="Enter Episode Finished Date" path="infection_risk.episode_finished_date"
               		class="date form-control form-control-sm floatlabel"></form:input> 
		       <span style="color:red" class="episode_finished_date-validation validation-error"></span>
		    </div>
		  </div>
		  <form:hidden id="ongoing_prev_ir" name="ongoing_prev_ir" path="infection_risk.ongoing_prev_ir"/>
		  <form:hidden id="episode_of_infection" name="episode_of_infection" path="infection_risk.episode_of_infection"/>
		  <form:hidden id="type_of_infection" name="type_of_infection" path="infection_risk.type_of_infection"/>
		  <form:hidden id="on_going" name="on_going" path="infection_risk.on_going"/>
		  <form:hidden id="infection_risk_id" name="infection_risk_id" path="infection_risk.infection_risk_id"/>		  
		  </fieldset>       	 
 		  <c:if test = "${fn:containsIgnoreCase(consent_access, 'add')}">
			<div class="text-center">
       			<div class="custom-control custom-checkbox">
				  <input type="checkbox" class="custom-control-input" id="add_infection_risk_chk" name="add_infection_risk_chk" value="add_infection_risk_chk"></input>
				  <label class="custom-control-label" for="add_infection_risk_chk">Add Infection Risk</label>
				</div>        
		  	</div> 
		  </c:if> 
       </td>
      </tr>
     </tbody>
   </table>
 </div> 
 <div class="text-center">
	 <div class="btn-group">
		<c:if test = "${fn:containsIgnoreCase(consent_access, 'add')}">
		  <button style="margin-top:5px; background-color: #2E008B; color: #FEFEFE;" class="btn btn-sm" type="submit" onclick="validateFormFields('CONSENT',this);"
	  		name="save_consent_btn" id="save_consent_btn"><i class="fas fa-check-circle"></i> Save All</button>
	  	</c:if>
	  	&nbsp;&nbsp;
	    <a href="add_consent"><button style="margin-top:5px; background-color: #E60E8B; color: #FEFEFE;" class="btn btn-sm" type="button"><i class="fas fa-broom"></i> Clear All</button></a>
	 </div>
</div>
<form:hidden id="addAnotherInfectionRisk" name="addAnotherInfectionRisk" path=""/>		  
</body>
</form:form>
</html>