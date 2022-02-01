<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<sec:csrfMetaTags />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<form:form name="patient_form" method="POST" action="save_patient_add_consent" modelAttribute="patient" autocomplete="off">
<body onload="reloadData()">
<div class="row" style="display:flex;justify-content:center;align-items:center;">
 <table class="table table-bordered" style="background-color:#EAE8FF;color:#2EOO8B;width:500px;"> 
  <tbody>
    <tr>
      <td>
       	<h4 class="text-center">Patient</h4>
       	&nbsp;&nbsp;&nbsp;
         <fieldset>
          <div id="patient_body_div">
	 		  <div id="database_id_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label for="database_id" class="col-sm-4 col-form-label text-left">Database ID</label>
			    <div class="col-sm-6 col-md-6">
		              <form:input type="text" id="database_id" name="database_id" path="database_id" class="form-control form-control-sm floatlabel" 
		              		title="Database ID is always disabled" readonly="true"></form:input>
			    </div>
			    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Unique pseudo-anonymised ID">
					<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
						<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
			    </span>
			  </div>
		  	<div id="secondary_id_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="secondary_id" class="col-sm-4 col-form-label text-left">Secondary ID</label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="text" id="secondary_id" name="secondary_id" path="secondary_id" style="text-transform: uppercase;"
	              		class="form-control form-control-sm floatlabel" title="Secondary ID is always disabled" readonly="true"></form:input>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Secondary unique pseudo-anonymised ID">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>  
		  <div id="surname_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="surname" class="col-sm-4 col-form-label text-left">Surname <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	             <form:input type="text" id="surname" name="surname" maxlength="30" path="surname" style="text-transform: uppercase;"
	             	  onchange="processPatientConsentInfectionRisk('PATIENT',this,true)" class="form-control form-control-sm floatlabel"></form:input>
                  <label id="surname-validation" style="color:red;display:none;"></label> 
		    </div>
		  </div>
 		  <div id="forename_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="forename" class="col-sm-4 col-form-label text-left">Forename <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="text" id="forename" name="forename" path="forename" style="text-transform:capitalize;"
	              		maxlength="30" class="form-control form-control-sm floatlabel"></form:input>
                  <label id="forename-validation" style="color:red; display: none;"></label> 
		    </div>
		  </div>
		  <div id="gender_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="select_gender" class="col-sm-4 col-form-label text-left">Gender <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="select_gender" name="select_gender" path="" class="browser-default custom-select custom-select-sm">
		        <option value=""></option>
		        <option value="Female">Female</option>
		        <option value="Male">Male</option>
		        <option value="Unspecified">Unspecified</option>
		      </form:select>
              <label id="select_gender-validation" style="color:red; display: none;"></label> 
		    </div>
		  </div>
		  <div id="select_volunteer_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="select_volunteer" class="col-sm-4 col-form-label text-left">Volunteer <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="select_volunteer" name="select_volunteer" onchange="hideAndShowContainer(this)" path=""  
		      		class="browser-default custom-select custom-select-sm">
		        <option value=""></option>
		        <option value="No">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
              <label id="select_volunteer-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Whether the donor is a healthy volunteer">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="select_age_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="select_age" class="col-sm-4 col-form-label text-left">Age</label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="select_age" path="" name="select_age" class="browser-default custom-select custom-select-sm" 
		      	onchange="processPatientConsentInfectionRisk('PATIENT',this,true)" >
		        <option value=""></option>
		      	<c:forEach var = "age_num" begin = "18" end = "110">
			        <option value="${age_num}">${age_num}</option>
		      	</c:forEach>
		      </form:select>
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Patient's age at the time of consent">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="date_of_birth_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="date_of_birth" class="col-sm-4 col-form-label text-left">Date Of Birth <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="text" id="date_of_birth" name="date_of_birth" path="date_of_birth" maxlength="10" placeholder="dd-MM-YYYY"
	              		onchange="formatInputBoxValue('DATE',this);processPatientConsentInfectionRisk('PATIENT',this,true)" 
	              		class="date form-control form-control-sm floatlabel"></form:input>
                  <label id="date_of_birth-validation" style="color:red; display: none;"></label> 
		    </div>
		  </div>
		  <div id="hospital_number_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="hospital_number" class="col-sm-4 col-form-label text-left">Hospital No <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="text" id="hospital_number" name="hospital_number" path="hospital_number" maxlength="30" 
	              		onchange="processPatientConsentInfectionRisk('PATIENT',this,true)" class="form-control form-control-sm floatlabel"></form:input>
                  <label id="hospital_number-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="MRN number">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="nhs_number_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="nhs_number" class="col-sm-4 col-form-label text-left">NHS Number <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="text" id="nhs_number" name="nhs_number" path="nhs_number" maxlength="12" style="text-transform:uppercase;"
	              		onchange="processPatientConsentInfectionRisk('PATIENT',this,true)" onkeyup="processKeyPressEvent(event,this)" class="form-control form-control-sm floatlabel" ></form:input>
                  <label id="nhs_number-validation" style="color:red; display: none;"></label> 
		    </div>
		  </div>
		  <div id="old_pat_id_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="old_pat_id" class="col-sm-4 col-form-label text-left">Old Pat ID</label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="text" id="old_pat_id" name="old_pat_id" path="old_pat_id"  
	              		maxlength="30" class="form-control form-control-sm floatlabel"></form:input> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="A previously assigned anonymised ID">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
	  	  </div>
	  	  </div>
	  	  <form:input type="hidden" name="age" id="age" path="age"></form:input> 
	  	  <form:input type="hidden" name="gender" id="gender" path="gender"></form:input> 
	  	  <form:input type="hidden" name="volunteer" id="volunteer" path="volunteer"></form:input> 
	  	  <form:input type="hidden" name="patient_id" id="patient_id" path="patient_id"></form:input> 
		  <div id="save_patient_add_consent_btn_div" class="text-center">
		 	<c:choose>
	 		  <c:when test = "${fn:containsIgnoreCase(consent_access, 'add')}">
				    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="save_patient_add_consent_btn" id="save_patient_add_consent_btn" onclick="validateFormFields('validate_patient',this,'save_patient_add_consent')">
				  		<i class="fas fa-check-circle"></i> Add Consent</form:button>
				  	&nbsp;&nbsp;
				    <a href="add_patient"><form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;" id="save_patient_clear_href"
				    	class="btn btn-sm" type="button"><i class="fas fa-broom"></i> Clear All</form:button></a>
	 		  </c:when>
	 		  <c:otherwise>
	 		  		<br>
		 			<h5>You are NOT authorised to add a patient</h5>
		      </c:otherwise>
	 		</c:choose>
	  	 </div>
		  <div id="edit_patient_btn_div" class="text-center" style="display: none;">
		 	<c:choose>
		 		<c:when test="${fn:containsIgnoreCase(consent_access, 'edit')}">
				  <form:button style="margin-top:5px; background-color: #2E008B; color: #FEFEFE;" class="btn btn-sm" type="button" 
			  		name="edit_patient_btn" id="edit_patient_btn" onclick="processUserSelection(this)">
			  		<i class="fas fa-check-circle"></i> Edit Patient</form:button>
		 		</c:when>
		 		<c:otherwise>
		 			<h5>You are NOT authorised to modify a patient</h5>
		 		</c:otherwise>
		 	</c:choose>
		 </div>
	  	 <br>
		  <div id="prev_consent_list_div" style="display: none;" class="shadow-lg rounded form-group row row-bottom-margin">
 		    <label for="prev_consent_list" class="col-sm-6 col-form-label text-left">Previous Consent form(s)</label> 
		    <div class="col-sm-10 col-md-10">
		    	<ul id="prev_consent_list" style="list-style-type: none; font-size: 12px;">
		    	</ul>
         	</div>
          </div> 
          <br>
		  <div id="prev_infection_risk_div" style="display: none;" class="shadow-lg rounded form-group row row-bottom-margin">
 		    <label for="prev_infection_risk" class="col-sm-6 col-form-label text-left">Previously recorded Infection Risk(s)</label> 
		    <div class="col-sm-10 col-md-10">
		    	<ul id="prev_infection_risk" style="list-style-type: none; font-size: 12px;">
		    	</ul>
         	</div>
          </div> 
     	</fieldset>
       </td>
      </tr>
     </tbody>
   </table>
   </div>
  </body>
</form:form>
</html>