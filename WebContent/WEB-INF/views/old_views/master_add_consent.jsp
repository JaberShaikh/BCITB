<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<sec:csrfMetaTags />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<form:form name="patient_consent_ir_form" method="POST" action="save_patient_consent_ir" modelAttribute="patient_consent_ir" autocomplete="off">
<body onload="reloadPatientConsentInfectionRiskData();processDropdownMenus('FORM-VERSION');processDropdownMenus('CONSENT-EXCLUSION');processDropdownMenus('USER-ROLES');processColouring();">
<script type="text/javascript">

	function processDropdownMenus(whichDropdownToProcess) {

		var formVersionConsentTerm = eval('('+'${formVersionConsentTerm}'+')');
		
	    var myCounter = 0;
	    var itemFound = false;
	    var selectedCheckBoxValues = [];
		
		switch (whichDropdownToProcess) {
		case 'FORM-VERSION':
			
		    $("#consent.select_form_version_id").empty();
		    var form_version_dropdown = document.getElementById("select_form_version_id");

		    formVersionConsentTerm.forEach(function(item,index,arr){
			    if(document.getElementById("form_type").value.toLowerCase() == item.form_version.form_type.toLowerCase())
		    	{
			    	itemFound = false;
			    	for(i=0; i < form_version_dropdown.options.length;i++){
			    		if (form_version_dropdown.options[i].value == item.form_version.form_version_id) {
				    		itemFound = true;
			    		}
			    	}		
			    	if (itemFound == false) {
					    var opt = document.createElement("option"); 
			    	    opt.value = item.form_version.form_version_id;
			    	    myCounter = myCounter + 1;
				        opt.text = myCounter + ' - ' + item.form_version.description;
				        form_version_dropdown.options.add(opt);			    
			    	}
		    	}
			});
		    if(document.getElementById("form_version_id").value > 0) {
		    	document.getElementById('select_form_version_id').value = document.getElementById("form_version_id").value;
		    } else {
		    	document.getElementById('select_form_version_id').selectedIndex = document.getElementById("select_form_version_id").length - 1;;
		    } 
		    
			break;
			
		case 'CONSENT-EXCLUSION':

			var select_consent_exclusions = document.getElementById("select_consent_exclusions");

			$('.consent_exclusions_selectpicker').empty();
			
			formVersionConsentTerm.forEach(function(item,index,arr) {
			    if(document.getElementById("select_form_version_id").value == item.form_version.form_version_id) {

			    	itemFound = false;
			    	for(i=0; i < select_consent_exclusions.options.length;i++){
			    		if (select_consent_exclusions.options[i].value == item.form_version.form_version_id) {
				    		itemFound = true;
			    		}
			    	}		
			    	if (itemFound == false) {
					    var opt = document.createElement("option"); 
			    	    opt.value = item.consent_term.consent_term_id;
			    	    myCounter = myCounter + 1;
				        opt.text = myCounter + ' - ' + item.consent_term.description;
				        select_consent_exclusions.options.add(opt);
			    	}
			    	
			    }
		    });

		    if($('#consent_exclusions').val() != "") {
			    var sel_ex_split = $('#consent_exclusions').val().split(",");
			    for (i=0;i<sel_ex_split.length;i++){
			    	selectedCheckBoxValues.push(sel_ex_split[i]);
			    }
			    $('.consent_exclusions_selectpicker').selectpicker('val', selectedCheckBoxValues); 
		    }
		    $('.consent_exclusions_selectpicker').selectpicker('refresh');
		    
			break;
			
		case 'USER-ROLES':

/*  		    if($('#user_roles').val() != "") {
			    var sel_ur_split = $('#user_roles').val().split(",");
			    for (i=0;i<sel_ur_split.length;i++){
			    	selectedCheckBoxValues.push(sel_ur_split[i]);
			    }
			    $('.user_roles_selectpicker').selectpicker('val', selectedCheckBoxValues); 
			    $('.user_roles_selectpicker').selectpicker('refresh');
		    }  */
 		    
			break;
		}
	}

</script>

<div class="row">
 <table class="table table-bordered"> 
  <tbody>
    <tr>
      <td style="background-color: #513CA1; color: #FEFEFE; width: 400px;">
       	<h4 class="text-center">Patient</h4>
         <fieldset>
          <div id="patient_body_div">
	 		  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label for="pat_id" class="col-sm-4 col-form-label text-left">Pat ID</label>
			    <div class="col-sm-4 col-md-4">
		              <form:input type="text" id="pat_id" name="patient.pat_id" path="patient.pat_id" class="form-control form-control-sm floatlabel" 
		              		title="Pat ID is always disabled" readonly="true"></form:input>
			    </div>
			    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Unique pseudo-anonymised ID">
					<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
						<i class="fas fa-info-circle" style="color:#FEFEFE;"></i></button>		    
			    </span>
			  </div>
		  	<div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="secondary_id" class="col-sm-4 col-form-label text-left">Secondary ID</label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="secondary_id" name="patient.secondary_id" path="patient.secondary_id" class="form-control form-control-sm floatlabel" 
	              		title="Secondary ID is always disabled" readonly="true"></form:input>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Secondary unique pseudo-anonymised ID">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#FEFEFE;"></i></button>		    
		    </span>
		  </div>  
		  <div id="surname_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="surname" class="col-sm-4 col-form-label text-left">Surname <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	             <form:input type="text" id="surname" name="patient.surname" placeholder="Enter Surname" maxlength="30" path="patient.surname" style="text-transform: uppercase;"
	             	  onchange="processPatientConsentInfectionRisk('PATIENT',this,true);" class="form-control form-control-sm floatlabel"></form:input>
                  <label id="surname-validation" style="color:#FEFEFE; display: none;"></label> 
		    </div>
		  </div>
 		  <div id="forename_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="forename" class="col-sm-4 col-form-label text-left">Forename <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="forename" name="patient.forename" path="patient.forename" style="text-transform: capitalize;"
	              		placeholder="Enter Forename" maxlength="30" class="form-control form-control-sm floatlabel"></form:input>
                  <label id="forename-validation" style="color:#FEFEFE; display: none;"></label> 
		    </div>
		  </div>
		  <div id="gender_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="patient.gender" class="col-sm-4 col-form-label text-left">Gender <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <select id="gender" name="patient.gender" class="browser-default custom-select custom-select-sm">
		        <option value="Unspecified">Unspecified</option>
		        <option value="Female">Female</option>
		        <option value="Male">Male</option>
		      </select>
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="volunteer" class="col-sm-4 col-form-label text-left">Volunteer</label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="volunteer" name="patient.volunteer" onchange="hideAndShowContainer(this)" path="patient.volunteer"  
		      		class="browser-default custom-select custom-select-sm">
		        <option value="No" selected="selected">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Whether the donor is a healthy volunteer">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#FEFEFE;"></i></button>		    
		    </span>
		  </div>
		  <div id="select_age_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="patient.select_age" class="col-sm-4 col-form-label text-left">Age</label>
		    <div class="col-sm-4 col-md-4">
		      <select id="patient.select_age" name="patient.select_age" class="browser-default custom-select custom-select-sm" 
		      	onchange="processPatientConsentInfectionRisk('PATIENT',this,true)" >
		        <option value=""></option>
		      	<c:forEach var = "age_num" begin = "18" end = "110">
			        <option value="${age_num}">${age_num}</option>
		      	</c:forEach>
		      </select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Patient's age at the time of consent">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#FEFEFE;"></i></button>		    
		    </span>
		  </div>
		  <div id="date_of_birth_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="date_of_birth" class="col-sm-4 col-form-label text-left">Date Of Birth <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="date_of_birth" name="patient.date_of_birth" path="patient.date_of_birth" placeholder="Enter Date Of Birth"
	              		onchange="formatInputBoxValue('DATE',this);processPatientConsentInfectionRisk('PATIENT',this,true);" 
	              		class="date form-control form-control-sm floatlabel"></form:input>
                  <label id="date_of_birth-validation" style="color:#FEFEFE; display: none;"></label> 
		    </div>
		  </div>
		  <div id="hospital_number_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="hospital_number" class="col-sm-4 col-form-label text-left">Hospital No <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="hospital_number" name="patient.hospital_number" path="patient.hospital_number" placeholder="Enter Hospital Number" maxlength="30" 
	              		onchange="processPatientConsentInfectionRisk('PATIENT',this,true);" class="form-control form-control-sm floatlabel"></form:input>
                  <label id="hospital_number-validation" style="color:#FEFEFE; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="MRN number">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#FEFEFE;"></i></button>		    
		    </span>
		  </div>
		  <div id="nhs_number_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="nhs_number" class="col-sm-4 col-form-label text-left">NHS Number <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="nhs_number" name="patient.nhs_number" path="patient.nhs_number" placeholder="Enter NHS Number" maxlength="30"
	              		onchange="processPatientConsentInfectionRisk('PATIENT',this,true);" class="form-control form-control-sm floatlabel"></form:input>
                  <label id="nhs_number-validation" style="color:#FEFEFE; display: none;"></label> 
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="tissue_bank_number" class="col-sm-4 col-form-label text-left">Tissue Bank No</label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="tissue_bank_number" name="patient.tissue_bank_number" path="patient.tissue_bank_number" placeholder="Enter Tissue Bank Number" 
	              		maxlength="30" class="form-control form-control-sm floatlabel"></form:input> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="A previously assigned anonymised ID">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#FEFEFE;"></i></button>		    
		    </span>
	  	  </div>
	  	  </div>
	  	  <form:input type="hidden" name="patient.age" id="age" path="patient.age"></form:input> 
	  	  <form:input type="hidden" name="patient.patient_id" id="patient_id" path="patient.patient_id"></form:input> 
     	</fieldset>
     </td>
     <td style="background-color: #EAE8FF; color: #2EOO8B; width: 600px;">
       	 <h4 class="text-center">Consent</h4>
          <fieldset>
		  <div id="prev_consent_list_div" style="display: none;" class="shadow-lg rounded form-group row row-bottom-margin">
 		    <label for="prev_consent_list" class="col-sm-6 col-form-label text-left">Previous Consent form(s)</label> 
		    <div class="col-sm-10 col-md-10">
		    	<ul id="prev_consent_list" style="list-style-type: none; font-size: 12px;">
		    	</ul>
         	</div>
          </div> 
          <br>
          <div id="consent_body_div">
		  <div id="date_of_consent_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="date_of_consent" class="col-sm-4 col-form-label text-left">Date Of Consent <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="date_of_consent" name="consent.date_of_consent" path="consent.date_of_consent" placeholder="Enter Date Of Consent" 
               		onchange="formatInputBoxValue('DATE',this);uploadFormDataToSessionObjects(this);" class="date form-control form-control-sm floatlabel"></form:input> 
               <label id="date_of_consent-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="The date consent was obtained, as recorded on consent form">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="consent_taken_by_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="consent_taken_by" class="col-sm-4 col-form-label text-left">Consent Taken By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="consent_taken_by" name="consent.consent_taken_by" path="consent.consent_taken_by" style="text-transform: capitalize;"
               		placeholder="Enter Consent Taken By" maxlength="50" onchange="uploadFormDataToSessionObjects(this);" class="form-control form-control-sm floatlabel"></form:input> 
               <label id="consent_taken_by-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="The name of the person that took consent">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="loc_id" class="col-sm-4 col-form-label text-left">Location <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="loc_id" name="consent.loc_id" path="consent.loc_id" onchange="uploadFormDataToSessionObjects(this);" class="browser-default custom-select custom-select-sm">
				  <c:forEach items="${user_locs}" var = "loc">
			          <option value="${loc.loc_id}">${loc.loc_name}</option>
				  </c:forEach>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Hospital site where consent was obtained">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="verbal_consent" class="col-sm-4 col-form-label text-left">Verbal Consent <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="verbal_consent" name="consent.verbal_consent" path="consent.verbal_consent" 
		      		onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects(this);" class="browser-default custom-select custom-select-sm">
		        <option value="No">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Was the consent obtained only verbally?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="verbal_consent_recorded_div" class="form-group row row-bottom-margin ml-2" style="display: none;margin-bottom:5px;">
		    <label for="verbal_consent_recorded" class="col-sm-4 col-form-label text-left">Verbal Consent Recorded <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="verbal_consent_recorded" name="consent.verbal_consent_recorded" path="consent.verbal_consent_recorded" 
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this);">
		        <option value="Physical notes">Physical notes</option>
		        <option value="Email">Email</option>
		        <option value="Millenium">Millenium</option>
		        <option value="Other">Other</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Where was the verbal consent recorded?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="verbal_consent_recorded_by_div" class="form-group row row-bottom-margin ml-2" style="display: none;margin-bottom:5px;">
		    <label for="verbal_consent_recorded_by" class="col-sm-4 col-form-label text-left">Verbal Consent Recorded By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="verbal_consent_recorded_by" name="consent.verbal_consent_recorded_by" path="consent.verbal_consent_recorded_by" style="text-transform: capitalize;"
               		placeholder="Enter Verbal Consent Recorded By" maxlength="30" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects(this)"></form:input> 
               <label id="verbal_consent_recorded_by-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="The name of the person who took verbal consent">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="verbal_consent_document_file_div" class="form-group row row-bottom-margin ml-2" style="display: none;margin-bottom:5px;">
		    <label for="verbal_consent_document_file" class="col-sm-4 col-form-label text-left">Verbal Consent Document <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="file" id="verbal_consent_document_file" name="consent.verbal_consent_document_file" path="consent.verbal_consent_document.filename" 
               		onchange="uploadFormDataToSessionObjects(this)" placeholder="Upload Verbal Consent Document" class="form-control form-control-sm floatlabel" 
               		style="color: transparent;" accept="image/*,application/pdf"></form:input> 
               	<a id="verbal_consent_document_file_label" style="color:#513CA1; display: none;" target="_blank"></a>
				<button id="verbal_consent_document_file_button" type="button" class="close" aria-label="Close" 
						style="color:#513CA1; display: none;" onclick="processPatientConsentInfectionRisk('REMOVE-FILE',this,false);">
				  <span aria-hidden="true">&times;</span>
				</button>               
                <label id="verbal_consent_document_file_label-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Upload a copy of any documentation confirming verbal consent">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="form_type_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="form_type" class="col-sm-4 col-form-label text-left">Form Type <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="form_type" name="consent.form_type" path="consent.form_type" class="browser-default custom-select custom-select-sm"
		      		onchange="processDropdownMenus('FORM-VERSION');processDropdownMenus('CONSENT-EXCLUSION');uploadFormDataToSessionObjects(this);" >
				  <c:forEach items="${consentFormTypes}" var = "cft">
			          <option value="${cft.description}">${cft.description}</option>
				  </c:forEach>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is this a patient or volunteer consent form?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="form_version_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="select_form_version_id" class="col-sm-4 col-form-label text-left">Form Version </label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="select_form_version_id" name="consent.select_form_version_id" path=""
		      		onchange="processDropdownMenus('CONSENT-EXCLUSION');uploadFormDataToSessionObjects(this);" class="browser-default custom-select custom-select-sm">
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="The version of the consent form used">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="digital_cf_attachment_file_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="digital_cf_attachment_file" class="col-sm-4 col-form-label text-left">Digital CF Attachment <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="file" id="digital_cf_attachment_file" name="consent.digital_cf_attachment_file" path="consent.digital_cf_attachment.filename" style="color: transparent;"
               		onchange="uploadFormDataToSessionObjects(this)" placeholder="Upload Digital CF Attachment" 
               		class="form-control form-control-sm floatlabel" accept="image/*,application/pdf"></form:input>
               	<a id="digital_cf_attachment_file_label" style="color:#513CA1; display: none;" target="_blank"></a>
				<button id="digital_cf_attachment_file_button" type="button" class="close" aria-label="Close" 
						style="color:#513CA1; display: none;" onclick="processPatientConsentInfectionRisk('REMOVE-FILE',this,false);">
				  <span aria-hidden="true">&times;</span>
				</button>               
               <label id="digital_cf_attachment_file_label-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Attach a copy of the physical consent form">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <c:if test = "${show_sample_consent_to == true}">
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label for="samples_consented_to" class="col-sm-4 col-form-label text-left">Samples Consented To</label>
			    <div class="col-sm-4 col-md-4">
	               <form:input type="text" id="samples_consented_to" name="consent.samples_consented_to" path="consent.samples_consented_to" style="text-transform: capitalize;" 
	               		placeholder="Enter Samples Consented To" maxlength="30" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects(this)"></form:input> 
               	   <label id="samples_consented_to-validation" style="color:red; display: none;"></label> 
			    </div>
			  </div>
		  </c:if>
		  <div id="consent_type_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="consent_type" class="col-sm-4 col-form-label text-left">Type Of Consent <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="consent_type" name="consent.consent_type" path="consent.consent_type" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects(this);" 
		      		class="browser-default custom-select custom-select-sm">
		        <option value="Partial">Partial</option>
		        <option value="Full">Full</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="If the patient has consented to all clauses, then the 'Type of consent' will be 'Full'. If there are any clauses not consented to, then they 'Type of consent' will be 'Partial'.">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="select_consent_exclusions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="select_consent_exclusions" class="col-sm-4 col-form-label text-left">Consent Exclusions <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="select_consent_exclusions" name="consent.select_consent_exclusions" path="" multiple="true" title="None" data-selected-text-format="count"
		      		class="selectpicker consent_exclusions_selectpicker form-control" data-actions-box="true" onchange="processUserSelection(this);uploadFormDataToSessionObjects(this);">
		      </form:select>
              <label id="select_consent_exclusions-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" 
		    	data-content="Select all clauses that the individual did not consent to.">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="exclusions_comment_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="exclusions_comment" class="col-sm-4 col-form-label text-left">Exclusion Comments</label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="exclusions_comment" name="consent.exclusions_comment" path="consent.exclusions_comment" onchange="uploadFormDataToSessionObjects(this);"
               		placeholder="Enter Consent Exclusion Comments" maxlength="100" class="form-control form-control-sm floatlabel"></form:textarea> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" 
		    	data-content="Notes on consent exclusions given that is not fully covered by the clauses e.g. Do not reapproach in the future">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="notes" class="col-sm-4 col-form-label text-left">Notes</label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="notes" name="consent.notes" path="consent.notes" placeholder="Enter Notes" maxlength="100"
               		class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects(this);"></form:textarea> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Any relevant information including file notes">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="additional_document_file_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="additional_document_file" class="col-sm-4 col-form-label text-left">Additional Documents </label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="file" id="additional_document_file" name="consent.additional_document_file" onchange="uploadFormDataToSessionObjects(this)" style="color:transparent;"
               		placeholder="Upload Additional Document" path="consent.additional_document.filename" 
               		class="form-control form-control-sm floatlabel" accept="image/*,application/pdf"></form:input>
               	<a id="additional_document_file_label" style="color:#513CA1; display: none;" target="_blank"></a>
				<button id="additional_document_file_button" type="button" class="close" aria-label="Close" 
						style="color:#513CA1; display: none;" onclick="processPatientConsentInfectionRisk('REMOVE-FILE',this,false);">
				  <span aria-hidden="true">&times;</span>
				</button>               
               <label id="additional_document_file_label-validation" style="color:red; display: none;"></label> 
		    </div>
		  </div>
		  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="withdrawn" class="col-sm-4 col-form-label text-left">Consent Withdrawal <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="withdrawn" name="consent.withdrawn" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects(this);" 
		      		path="consent.withdrawn" class="browser-default custom-select custom-select-sm">
		        <option value="No">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Has consent been withdrawn?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="withdrawal_date_div" class="form-group row row-bottom-margin ml-2" style="display: none;margin-bottom:5px;">
		    <label for="withdrawal_date" class="col-sm-4 col-form-label text-left">Withdrawn Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="withdrawal_date" name="consent.withdrawal_date" path="consent.withdrawal_date" placeholder="Enter Consent Withdrawal Date" 
               		onchange="formatInputBoxValue('DATE',this);uploadFormDataToSessionObjects(this);" class="date form-control form-control-sm floatlabel"></form:input> 
              <label id="withdrawn_date-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Date consent was withdrawn">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="withdrawal_document_file_div" class="form-group row row-bottom-margin ml-2" style="display:none; margin-bottom:5px;">
		    <label for="withdrawal_document_file" class="col-sm-4 col-form-label text-left">Withdrawn Document <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="file" id="withdrawal_document_file" name="consent.withdrawal_document_file" onchange="uploadFormDataToSessionObjects(this)"
               		placeholder="Upload Consent Withdrawal Document" path="consent.withdrawal_document.filename" style="color: transparent;"
               		class="form-control form-control-sm floatlabel" accept="image/*,application/pdf"></form:input>
               	<a id="withdrawal_document_file_label" style="color:#513CA1; display: none;" target="_blank"></a>
				<button id="withdrawal_document_file_button" type="button" class="close" aria-label="Close" 
						style="color:#513CA1; display: none;" onclick="processPatientConsentInfectionRisk('REMOVE-FILE',this,false);">
				  <span aria-hidden="true">&times;</span>
				</button>               
                <label id="withdrawal_document_file_label-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Upload letter/email requesting consent withdrawal">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <form:input type="hidden" name="consent.consent_id" id="consent_id" path="consent.consent_id"/>
		  <form:input type="hidden" name="consent.form_version_id" id="form_version_id" path="consent.form_version_id"/>
		  <form:input type="hidden" name="consent.consent_exclusions" id="consent_exclusions" path="consent.consent_exclusions"/>
		  </div>
         </fieldset>
       </td>
       <td style="background-color: #F477CD; width: 500px;">
       	 <h4 class="text-center">Infection Risk</h4>
          <fieldset>
		  <div id="prev_infection_risk_div" style="display: none;" class="shadow-lg rounded form-group row row-bottom-margin">
 		    <label for="prev_infection_risk" class="col-sm-6 col-form-label text-left">Previously recorded Infection Risk(s)</label> 
		    <div class="col-sm-10 col-md-10">
		    	<ul id="prev_infection_risk" style="list-style-type: none; font-size: 12px;">
		    	</ul>
         	</div>
          </div> 
          <br>
          <div id="infection_risk_body_div">
		  <div id="infection_risk_exist_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="infection_risk_exist" class="col-sm-6 col-form-label text-left">Infection Risk <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="infection_risk_exist" name="infection_risk.infection_risk_exist" path="infection_risk.infection_risk_exist" 
		      		onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects(this);" class="browser-default custom-select custom-select-sm">
		        <option value="No (Tests = Negative)" selected="selected">No (Tests = Negative)</option>
		        <option value="Yes (Tests = Positive)">Yes (Tests = Positive)</option>
		        <option value="No infection risks recorded">No infection risks recorded</option>
		        <option value="Unknown - possible risk">Unknown - possible risk</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Infection Risk" 
		    	data-content="Does the patient have any known or possible infection risk, according to their medical records?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="select_infection_type_id_div" class="form-group row row-bottom-margin ml-2" style="display:none; margin-bottom:5px;">
		    <label for="select_infection_type_id" class="col-sm-6 col-form-label text-left">Type Of Infection <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="select_infection_type_id" name="infection_risk.select_infection_type_id" path="" 
		      		onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects(this);" class="browser-default custom-select custom-select-sm">
				  <c:forEach items="${infection_types}" var = "it">
			          <option value="${it.infection_type_id}">${it.description}</option>
				  </c:forEach>
		      </form:select>
		    </div>
		  </div>
		  <div id="select_ongoing_infection_type_id_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
		    <label for="select_ongoing_infection_type_id" class="col-sm-6 col-form-label text-left">Previous Infection Risk <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="select_ongoing_infection_type_id" name="infection_risk.select_ongoing_infection_type_id" path="" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects(this);" 
		      		class="browser-default custom-select custom-select-sm">
		      </form:select>
              <label id="select_ongoing_infection_type_id-validation" style="color:black; display: none;"></label> 
		    </div>
		  </div>
		  <div id="other_infection_risk_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
		    <label for="other_infection_risk" class="col-sm-6 col-form-label text-left">Other Infection Risk <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="other_infection_risk" name="infection_risk.other_infection_risk" path="infection_risk.other_infection_risk"
               		placeholder="Enter Other Infection Risk" maxlength="20" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects(this)"></form:input> 
               <label id="other_infection_risk-validation" style="color:black; display: none;"></label> 
		    </div>
		  </div>
		  <div id="comments_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
		    <label for="comments" class="col-sm-6 col-form-label text-left">Comments</label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="comments" name="infection_risk.comments" placeholder="Enter Comments Or Correspondence" path="infection_risk.comments"
               		maxlength="200" class="form-control form-control-sm floatlabel"></form:textarea> 
		    </div>
		  </div>
		  <div id="select_episode_of_infection_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
		    <label for="select_episode_of_infection" class="col-sm-6 col-form-label text-left">Episode Of Infection <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="select_episode_of_infection" name="infection_risk.select_episode_of_infection" path="" onchange="uploadFormDataToSessionObjects(this)"
		      		class="browser-default custom-select custom-select-sm">
		      	<c:forEach var = "risk_num" begin = "1" end = "4">
			        <option value="${risk_num}">${risk_num}</option>
		      	</c:forEach>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Episode Of Infection" 
		    	data-content="State whether this is the patient's first episode of infection, or their second/third etc. if the infection was treated and reoccurred">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="episode_start_date_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
		    <label for="episode_start_date" class="col-sm-6 col-form-label text-left">Episode Start Date<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="episode_start_date" name="infection_risk.episode_start_date" placeholder="Enter Episode Start Date" path="infection_risk.episode_start_date"
               		onchange="formatInputBoxValue('DATE',this);uploadFormDataToSessionObjects(this);" class="date form-control form-control-sm floatlabel"></form:input> 
               <label id="episode_start_date-validation" style="color:black; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Episode Start Date" data-content="Indicate the date the patient was diagnosed with the infection">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="select_continued_risk_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
		    <label for="select_continued_risk" class="col-sm-6 col-form-label text-left">Continued Risk</label>
		    <div class="col-sm-1 col-md-4">
		      <form:select id="select_continued_risk" name="infection_risk.select_continued_risk" path="" 
		      		onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects(this);" class="browser-default custom-select custom-select-sm">
			        <option value="Yes">Yes</option>
			        <option value="No">No</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Continued Risk" data-content="If the infection is still on-going then confirm here">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="episode_finished_date_div" class="form-group row row-bottom-margin ml-2" style="display: none; margin-bottom:5px;">
		    <label for="episode_finished_date" class="col-sm-6 col-form-label text-left">Episode Finished Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="episode_finished_date" name="infection_risk.episode_finished_date" placeholder="Enter Episode Finished Date" path="infection_risk.episode_finished_date"
               		onchange="formatInputBoxValue('DATE',this);uploadFormDataToSessionObjects(this);" class="date form-control form-control-sm floatlabel"></form:input> 
               <label id="episode_finished_date-validation" style="color:black; display: none;"></label> 
	        </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" title="Episode Finished Date" 
		    	data-content="Indicate the date the patient was deemed to be clear of infection, if applicable.">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <form:input type="hidden" id="ongoing_infection_type_id" name="infection_risk.ongoing_infection_type_id" path="infection_risk.ongoing_infection_type_id"/>
		  <form:input type="hidden" id="episode_of_infection" name="infection_risk.episode_of_infection" path="infection_risk.episode_of_infection"/>
		  <form:input type="hidden" id="infection_type_id" name="infection_risk.infection_type_id" path="infection_risk.infection_type_id"/>
		  <form:input type="hidden" id="continued_risk" name="infection_risk.continued_risk" path="infection_risk.continued_risk"/>
		  <form:input type="hidden" id="infection_risk_id" name="infection_risk.infection_risk_id" path="infection_risk.infection_risk_id"/>	
 		  <c:if test = "${fn:containsIgnoreCase(consent_access, 'add')}">
			<div id="add_infection_risk_btn_div" class="text-center" style="display: none;">
			  <form:button style="margin-top:5px; background-color: #2E008B; color: #FEFEFE;" class="btn btn-sm" type="button" 
		  		name="add_infection_risk_btn" id="add_infection_risk_btn" value="add_infection_risk_btn" onclick="validateFormFields('',this,true);">
		  			<i class="fas fa-check-circle"></i> Add Another Infection Risk</form:button>
		  	</div> 
		  </c:if> 
		  </div>
		  </fieldset>       	 
       </td>
      </tr>
     </tbody>
   </table>
 </div> 
  <div class="text-center">
	 <div id="edit_btn_div" class="btn-group" style="display: none;">
	 	<c:choose>
	 		<c:when test="${fn:containsIgnoreCase(consent_access, 'edit') || fn:containsIgnoreCase(consent_draft_access, 'edit')}">
			  <form:button style="margin-top:5px; background-color: #2E008B; color: #FEFEFE;" class="btn btn-sm" type="button" 
		  		name="edit_consent_btn" id="edit_consent_btn" value="edit_consent_btn" onclick="processUserSelection(this)">
		  		<i class="fas fa-check-circle"></i> Edit Consent</form:button>
	 		</c:when>
	 		<c:otherwise>
	 			<h5>You are NOT authorised to modify a patient, consent or infection risk</h5>
	 		</c:otherwise>
	 	</c:choose>
	 </div>
	 <br>
</div>
 <div id="validate_consent_form_div" class="row" style="display:none;">
 <div class="text-center">
 	<h4>Validate Consent Form</h4>
 </div>
 <table class="table"> 
  <tbody style="background-color:#F6F5B6;color:#513CA1;width:400px;">
    <tr>
      <td>
		  <div id="verbal_document_checked_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="verbal_document_checked" class="col-sm-5 col-form-label text-left">Verbal Document Check <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="verbal_document_checked" name="consent_validate.verbal_document_checked" path="consent_validate.verbal_document_checked"  
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this)">
		        <option value="Yes" selected="selected">Yes</option>
		        <option value="No">No</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Has the documentation of verbal consent been checked?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="verbal_consent_checked_date_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="verbal_consent_checked_date" class="col-sm-5 col-form-label text-left">Verbal Check Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="verbal_consent_checked_date" name="consent_validate.verbal_consent_checked_date" placeholder="Enter Verbal Consent Checked Date" 
               		path="consent_validate.verbal_consent_checked_date" onchange="formatInputBoxValue('DATE',this);uploadFormDataToSessionObjects(this);" 
               		class="date form-control form-control-sm floatlabel"></form:input> 
               <label id="verbal_consent_checked_date-validation" style="color:red;display:none;"></label> 
	        </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="The date the person checked the documentation of verbal consent">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="verbal_consent_checked_by_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="verbal_consent_checked_by" class="col-sm-5 col-form-label text-left">Verbal Checked By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="verbal_consent_checked_by" name="consent_validate.verbal_consent_checked_by" path="consent_validate.verbal_consent_checked_by" 
               		style="text-transform: capitalize;" placeholder="Verbal Consent Checked By" maxlength="50" onchange="uploadFormDataToSessionObjects(this)" 
               		class="form-control form-control-sm floatlabel"></form:input> 
              <label id="verbal_consent_checked_by-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="The initials of person checking the documentation of verbal consent">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="digital_cf_attached_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="digital_cf_attached" class="col-sm-5 col-form-label text-left">Digital CF Attachment <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="digital_cf_attached" name="consent_validate.digital_cf_attached" path="consent_validate.digital_cf_attached"  
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this)">
		        <option value="Yes" selected="selected">Yes</option>
		        <option value="No">No</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Whether or not the Digital CF is attached">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="cf_physical_location_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="cf_physical_location" class="col-sm-5 col-form-label text-left">CF Physical Location <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="cf_physical_location" name="consent_validate.cf_physical_location" placeholder="Enter CF Physical Location" path="consent_validate.cf_physical_location"
               		maxlength="200" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects(this)"></form:textarea>
               <label id="cf_physical_location-validation" style="color:red; display: none;"></label>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="The folder where the hard copy of CF is stored">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="date_of_consent_stated_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="date_of_consent_stated" class="col-sm-5 col-form-label text-left">Date Of Consent Stated <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="date_of_consent_stated" name="consent_validate.date_of_consent_stated" path="consent_validate.date_of_consent_stated"  
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this)">
		        <option value="Yes" selected="selected">Yes</option>
		        <option value="No">No</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is there at least one date of consent provided?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="patient_signature_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="patient_signature" class="col-sm-5 col-form-label text-left">Patient Signature <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="patient_signature" name="consent_validate.patient_signature" path="consent_validate.patient_signature"  
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this)">
		        <option value="Yes" selected="selected">Yes</option>
		        <option value="No">No</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is there a patient signature?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="person_taking_consent_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="person_taking_consent" class="col-sm-5 col-form-label text-left">Person Taking Signature <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="person_taking_consent" name="consent_validate.person_taking_consent" path="consent_validate.person_taking_consent"  
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this)">
		        <option value="Yes" selected="selected">Yes</option>
		        <option value="No">No</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is there a signature of the person taking consent?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="cf_validity_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="cf_validity" class="col-sm-5 col-form-label text-left">CF Validity <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="cf_validity" name="consent_validate.cf_validity" path="consent_validate.cf_validity"  
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this)">
		        <option value="Yes" selected="selected">Yes</option>
		        <option value="No">No</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is the consent form valid?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="cf_checked_date_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="cf_checked_date" class="col-sm-5 col-form-label text-left">CF Checked Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
	              <form:input type="text" id="cf_checked_date" name="consent_validate.cf_checked_date" path="consent_validate.cf_checked_date" placeholder="Enter CF Checked Date"
	              		onchange="formatInputBoxValue('DATE',this);uploadFormDataToSessionObjects(this);" 
	              		class="date form-control form-control-sm floatlabel"></form:input>
                  <label id="cf_checked_date-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="The date the person checked the existance and location of the CF">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="cf_checked_by_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="cf_checked_by" class="col-sm-5 col-form-label text-left">CF Checked By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="cf_checked_by" name="consent_validate.cf_checked_by" path="consent_validate.cf_checked_by" style="text-transform: capitalize;" onchange="uploadFormDataToSessionObjects(this)"
               		placeholder="Enter CF Checked By" maxlength="50" class="form-control form-control-sm floatlabel"></form:input> 
              	   <label id="cf_checked_by-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="The initials of person checking the existance and location of the CF">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
       </td>
       <td>
		  <div id="verify_consent_exclusions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="verify_consent_exclusions" class="col-sm-4 col-form-label text-left">Verify Exclusions</label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="verify_consent_exclusions" name="consent_validate.verify_consent_exclusions" path="consent_validate.verify_consent_exclusions"  
		      		class="browser-default custom-select custom-select-sm" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects(this)">
		        <option value="Yes" selected="selected">Yes</option>
		        <option value="No">No</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is the consent form valid?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="statements_excluded_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="statements_excluded" class="col-sm-4 col-form-label text-left">Statements Excluded <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="statements_excluded" name="consent_validate.statements_excluded" placeholder="Enter Consent Statements Excluded" path="consent_validate.statements_excluded"
               		maxlength="200" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects(this)"></form:textarea>
               <label id="statements_excluded-validation" style="color:red; display: none;"></label>
		    </div>
		  </div>
		  <div id="reapproach_patient_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="reapproach_patient" class="col-sm-4 col-form-label text-left">Reapproach Patient</label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="reapproach_patient" name="consent_validate.reapproach_patient" path="consent_validate.reapproach_patient"  
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this)">
		        <option value="Yes" selected="selected">Yes</option>
		        <option value="No">No</option>
		        <option value="Unknown">Unknown</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Does the patient need to be reapproached to confirm consent terms?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="reason_for_reapproach_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="reason_for_reapproach" class="col-sm-4 col-form-label text-left">Reapproach Reason</label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="reason_for_reapproach" name="consent_validate.reason_for_reapproach" placeholder="Enter Reason For Reapproach" path="consent_validate.reason_for_reapproach"
               		maxlength="200" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects(this)"></form:textarea>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="What are the reasons for reapproaching a patient?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="cf_audit_notes_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="cf_audit_notes" class="col-sm-4 col-form-label text-left">CF Audit Notes</label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="cf_audit_notes" name="consent_validate.cf_audit_notes" placeholder="Enter CF Audit Notes" path="consent_validate.cf_audit_notes"
               		maxlength="200" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects(this)"></form:textarea>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Consent form audit notes">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
       </td>
       <td>
		  <div id="data_discrepancies_identified_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="data_discrepancies_identified" class="col-sm-5 col-form-label text-left">Discrepancies Identified <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="data_discrepancies_identified" name="consent_validate.data_discrepancies_identified" path="consent_validate.data_discrepancies_identified"  
		      		class="browser-default custom-select custom-select-sm" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects(this);">
		        <option value="No" selected="selected">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Have data discrepanicies e.g. Hospital number, been identified?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="data_accuracy_date_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="data_accuracy_date" class="col-sm-5 col-form-label text-left">Accuracy Check Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="data_accuracy_date" name="consent_validate.data_accuracy_date" placeholder="Enter Data Accuracy Check Date" path="consent_validate.data_accuracy_date"
               		onchange="formatInputBoxValue('DATE',this);uploadFormDataToSessionObjects(this);" class="date form-control form-control-sm floatlabel"></form:input> 
               <label id="data_accuracy_date-validation" style="color:red; display: none;"></label> 
	        </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="The date the person checked the accuracy of the data">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="data_accuracy_checked_by_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="data_accuracy_checked_by" class="col-sm-5 col-form-label text-left">Accuracy Checked By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="data_accuracy_checked_by" name="consent_validate.data_accuracy_checked_by" path="consent_validate.data_accuracy_checked_by" 
               		style="text-transform: capitalize;" placeholder="Enter Data Accuracy Checked By" maxlength="50" class="form-control form-control-sm floatlabel" 
               		 onchange="uploadFormDataToSessionObjects(this)"></form:input> 
              	   <label id="data_accuracy_checked_by-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="The initials of the person who checked the accuracy of the data">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="source_of_verified_data_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="source_of_verified_data" class="col-sm-5 col-form-label text-left">Source Of Verified Data <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="source_of_verified_data" name="consent_validate.source_of_verified_data" path="consent_validate.source_of_verified_data"  
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this)">
		        <option value="Physical notes" selected="selected">Physical notes</option>
		        <option value="Email">Email</option>
		        <option value="Millenium">Millenium</option>
		        <option value="Other">Other</option>
		        <option value="Winpath">Winpath</option>
		        <option value="EPR">EPR</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="What is the source of the verified data? E.g. EPR">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="data_discrepancies_description_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="data_discrepancies_description" class="col-sm-5 col-form-label text-left">Discrepancies Description 
		    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="data_discrepancies_description" name="consent_validate.data_discrepancies_description" placeholder="Enter Data Discrepancies Description" 
               		path="consent_validate.data_discrepancies_description" maxlength="200" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects(this)"></form:textarea>
               <label id="data_discrepancies_description-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Description of data discrepancies e.g. Change hospital no to match EPR.">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="data_discrepancies_verified_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="data_discrepancies_verified" class="col-sm-5 col-form-label text-left">Discrepancies Verified
		    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="data_discrepancies_verified" name="consent_validate.data_discrepancies_verified" path="consent_validate.data_discrepancies_verified"  
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects(this)">
		        <option value="Yes" selected="selected">Yes</option>
		        <option value="No">No</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is the consent form valid?">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="data_discrepancies_verification_date_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="data_discrepancies_verification_date" class="col-sm-5 col-form-label text-left">Discrepancies Date 
		    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="data_discrepancies_verification_date" name="consent_validate.data_discrepancies_verification_date" 
               		placeholder="Enter Data Discrepancies Verification Date" path="consent_validate.data_discrepancies_verification_date"
               		onchange="formatInputBoxValue('DATE',this);uploadFormDataToSessionObjects(this);" class="date form-control form-control-sm floatlabel"></form:input> 
               <label id="data_discrepancies_verification_date-validation" style="color:red; display: none;"></label> 
	        </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="The date the person checked the discrepancies of the data">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="data_discrepancies_verified_by_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="data_discrepancies_verified_by" class="col-sm-5 col-form-label text-left">Discrepancies Verified By 
		    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
               <form:input type="text" id="data_discrepancies_verified_by" name="consent_validate.data_discrepancies_verified_by" path="consent_validate.data_discrepancies_verified_by" 
               		style="text-transform: capitalize;" placeholder="Enter Data Discrepancies Verified By" maxlength="50" class="form-control form-control-sm floatlabel" 
               		 onchange="uploadFormDataToSessionObjects(this)"></form:input> 
              	   <label id="data_discrepancies_verified_by-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus"  
		    	data-content="The initials of the person who checked the discrepancies of the data">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <form:input type="hidden" id="cv_consent_id" name="consent_validate.cv_consent_id" path="consent_validate.cv_consent_id"/>
		  <form:input type="hidden" id="current_user" path="" value="${user.firstname} ${user.surname}"/>
       </td>
      </tr>
     </tbody>
   </table>
 </div> 
  <table id="user_action_form" class="table" style="display:none;"> 
  <tbody style="background-color:#EEA460;color:#513CA1;width:400px;">
    <tr>
      <td>
	  	  <div id="action_type_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="action_type" class="col-sm-5 col-form-label text-left">Select Action
		    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="action_type" name="action.type" path="action.type"  
		      		class="browser-default custom-select custom-select-sm">
		        <option value=""></option>
		        <option value="consent_validation_pending">Pending Validation</option>
		        <option value="reapproach_patient">Reapproach Patient</option>
		        <option value="infection_risk">Infection Risk</option>
		        <option value="validate">Validate</option>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Select action type to send">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
      </td>
      <td>
		  <div id="select_user_roles_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="select_user_roles" class="col-sm-5 col-form-label text-left">Send Actions To... <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-4 col-md-4">
		      <form:select id="select_user_roles" name="action.select_user_roles" path="" multiple="true" title="None" data-selected-text-format="count"
		      		class="selectpicker user_roles_selectpicker form-control" data-actions-box="true" onchange="processUserSelection(this)">
				  <c:forEach items="${userRoles}" var = "role">
			         <option value="${role.role_id}">${role.role_description}</option>
				  </c:forEach>
		      </form:select>
	          <label id="select_user_roles-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Select all users who must receive this validation as a task.">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
      </td>
      <td>
		  <div id="user_action_notes_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="user_action_notes" class="col-sm-5 col-form-label text-left">Notes </label>
		    <div class="col-sm-4 col-md-4">
               <form:textarea id="user_action_notes" name="action.notes" placeholder="Enter Notes" 
               		path="action.notes" maxlength="200" class="form-control form-control-sm floatlabel"></form:textarea>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Leave note for the next user who will be working on this consent.">
				<button type="button" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <form:input type="hidden" id="user_roles" name="action.user_roles" path="action.user_roles"/>
      </td>
    </tr>
  </tbody>
 </table>  
 <div class="text-center">
	 <div id="save_changes_cancel_btn_div" class="btn-group" style="display: none;">
	 	<c:choose>
 		  <c:when test = "${fn:containsIgnoreCase(consent_access, 'edit') || fn:containsIgnoreCase(consent_draft_access, 'edit')}">
			  <form:button style="margin-top:5px; background-color:#2E008B; color: #FEFEFE;" class="btn btn-sm" type="button" 
		  		name="save_changes_pat_cons_irs_btn" id="save_changes_pat_cons_irs_btn" onclick="validateFormFields('',this,false);">
		  		<i class="fas fa-check-circle"></i> Save</form:button>
<%--			  	<c:if test="${fn:containsIgnoreCase(consent_access, 'delete')}">
			  	  	&nbsp;&nbsp;
				    <form:button name="delete_patient_consents_ir_btn" id="delete_patient_consents_ir_btn" data-toggle="modal" data-target="#myModal"
				    	style="margin-top:5px;background-color:#E60E8B;color:#FEFEFE;" class="btn btn-sm" type="button" onclick="processUserSelection(this)">
				    	<i class="fas fa-trash-alt"></i> Delete Options</form:button>
			  	</c:if>
 			  	&nbsp;&nbsp;
			    <a id="cancel_changes_pat_cons_irs_href" href="#" onclick="processUserSelection(this)"><form:button style="margin-top:5px; background-color:#EAE8FF; color:#513CA1;" 
			    	class="btn btn-sm" type="button"><i class="fas fa-broom"></i> Cancel</form:button></a> --%>
	 		</c:when>
	 		<c:otherwise>
	 			<h5>You are NOT authorised to modify a patient, consent or infection risk</h5>
	 		</c:otherwise>
	 	</c:choose>
	 </div>
	 <div id="save_clear_btns" class="btn-group">
	 	<c:choose>
 		  <c:when test = "${fn:containsIgnoreCase(consent_access, 'add')}">
			  <form:button style="margin-top:5px; background-color: #2E008B; color: #FEFEFE;" class="btn btn-sm" type="button" 
		  		name="save_consent_btn" id="save_consent_btn" value="save_consent_btn" onclick="validateFormFields('',this,false);">
		  		<i class="fas fa-check-circle"></i> Save All</form:button>
			  	&nbsp;&nbsp;
			    <a href="add_consent"><form:button style="margin-top:5px; background-color: #E60E8B; color: #FEFEFE;" 
			    	class="btn btn-sm" type="button"><i class="fas fa-broom"></i> Clear All</form:button></a>
	 		</c:when>
	 		<c:otherwise>
	 			<h5>You are NOT authorised to add a patient, consent or infection risk</h5>
	 		</c:otherwise>
	 	</c:choose>
	 </div>
</div>
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">Modal Header</h4>
        </div>
        <div class="modal-body" id="modal_dialog_body">
        </div>
        <div class="modal-footer" id="modal_dialog_footer">
        </div>
      </div>
    </div>
  </div>
  <form:hidden name="consent_validate_access" id="consent_validate_access" path="" value="${consent_validate_access}"/>
  <form:hidden name="consent_access" id="consent_access" path="" value="${consent_access}"/>
  <form:hidden name="consent_draft_access" id="consent_draft_access" path="" value="${consent_draft_access}"/>
  <form:hidden name="whichPageToShow" id="whichPageToShow" path="" value="${whichPageToShow}"/>
  <form:hidden name="deletion_list" id="deletion_list" path=""/>
  <form:hidden name="deletion_notes" id="deletion_notes" path=""/>
</body>
</form:form>
</html>