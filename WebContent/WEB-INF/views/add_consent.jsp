<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<sec:csrfMetaTags/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body onload="reloadData();processDropdownMenus('FORM-VERSION');processDropdownMenus('CONSENT-EXCLUSION');processDropdownMenus('SAMPLE-CONSENT-TO');">
<form:form name="consent_form" method="POST" action="save_consent" modelAttribute="consent" autocomplete="off">
<script type="text/javascript">

	function processDropdownMenus(whichDropdownToProcess) {

		switch (whichDropdownToProcess) {
		case 'FORM-VERSION': case 'CONSENT-EXCLUSION':
			
			switch (document.getElementById('user_department').value.toUpperCase()) {
			case 'BGTB':
				var formVersionConsentTermJson = eval('('+'${formVersionConsentTermJson}'+')');
				break
			case 'HOTB':
				var formTypeVersionConsentTermJson = eval('('+'${formTypeVersionConsentTermJson}'+')');
				var formVersionJson = eval('('+'${formVersionJson}'+')');
				break;
			}
			break;
		}
		
	    var myCounter = 0;
	    var itemFound = false;
	    var selectedCheckBoxValues = [];
		
		switch (whichDropdownToProcess) {
		case 'FORM-VERSION':
			
		    $("#select_form_version_id").empty();
		    var form_version_dropdown = document.getElementById("select_form_version_id");

		    var opt = document.createElement("option"); 
    	    opt.value = '';
	        opt.text = '';
	        form_version_dropdown.options.add(opt);			    

			switch (document.getElementById('user_department').value.toUpperCase()) {
			case 'BGTB':
	        
			    formVersionConsentTermJson.forEach(function(item,index,arr){
				    if(item.form_version.form_type.length > 0 && document.getElementById("form_type").value.length > 0 
				    		&& item.form_version.form_type.toLowerCase().includes(document.getElementById("form_type").value.toLowerCase()))
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
					        opt.text = item.form_version.description;
					        form_version_dropdown.options.add(opt);			    
				    	}
			    	}
				});
				
			    break;
				
			case 'HOTB':
				
				formVersionJson.forEach(function(item,index,arr){
				    if(item.form_type.length > 0 && document.getElementById("form_type").value.length > 0 
				    		&& item.form_type.toLowerCase().includes(document.getElementById("form_type").value.toLowerCase()))
			    	{
				    	itemFound = false;
				    	for(i=0; i < form_version_dropdown.options.length;i++){
				    		if (form_version_dropdown.options[i].value == item.form_version_id) {
					    		itemFound = true;
				    		}
				    	}		
				    	if (itemFound == false) {
						    var opt = document.createElement("option"); 
				    	    opt.value = item.form_version_id;
					        opt.text = item.description;
					        form_version_dropdown.options.add(opt);			    
				    	}
			    	}
				});
				
			    break;

			}
			
		    if(document.getElementById("form_version_id").value > 0) {
		    	document.getElementById('select_form_version_id').value = document.getElementById("form_version_id").value;
		    } else {
		    	document.getElementById('select_form_version_id').selectedIndex = 0;
		    } 
			break;
			
		case 'CONSENT-EXCLUSION':

			var select_consent_exclusions = document.getElementById("select_consent_exclusions");
			var all_con_exclusions_txt = '';
			
			$('.consent_exclusions_selectpicker').empty();

			switch (document.getElementById('user_department').value.toUpperCase()) {
			case 'BGTB':
				
				formVersionConsentTermJson.forEach(function(item,index,arr) {
					
				    if(document.getElementById("select_form_version_id").value == item.form_version.form_version_id) {
				    	itemFound = false;
				    	for(i=0; i < select_consent_exclusions.options.length;i++){
				    		if (select_consent_exclusions.options[i].value == item.consent_term.consent_term_id) {
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
				
				break;

			case 'HOTB':

				formTypeVersionConsentTermJson.forEach(function(item,index,arr) {

				    if(document.getElementById("form_type").value.length > 0 
				    		&& item.consent_form_type.description.toLowerCase().includes(document.getElementById("form_type").value.toLowerCase())
				    		&& item.form_version_id == document.getElementById('select_form_version_id').value) {

				    	itemFound = false;
				    	for(i=0; i < select_consent_exclusions.options.length;i++){
				    		if (select_consent_exclusions.options[i].value == item.consent_term.consent_term_id) {
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
				
				break;

			}

		    if($('#consent_exclusions').val() != "") {
			    var sel_ex_split = $('#consent_exclusions').val().split(",");
			    for (i=0;i<sel_ex_split.length;i++){
			    	selectedCheckBoxValues.push(sel_ex_split[i]);
			    }
			    $('.consent_exclusions_selectpicker').selectpicker('val', selectedCheckBoxValues); 
		    }
		    $('.consent_exclusions_selectpicker').selectpicker('refresh');
		    $('#select_consent_exclusions option:selected').each(function() {
		        all_con_exclusions_txt = all_con_exclusions_txt + $(this).text() + ' ';
		    });
		    $('#consent_exclusions_span').attr('data-content', all_con_exclusions_txt);
		    
			break;

		case 'SAMPLE-CONSENT-TO':

			if(document.getElementById("select_samples_consented_to_div").style.display == '') {
				
				var sampleConsentedToJson = eval('('+'${sampleConsentedToJson}'+')');
				var all_sample_consent_txt = '';
				
				var select_samples_consented_to = document.getElementById("select_samples_consented_to");

				$('.samples_consented_to_selectpicker').empty();
				
				sampleConsentedToJson.forEach(function(item,index,arr) {
				    var opt = document.createElement("option"); 
		    	    opt.value = item.consent_sample_type_id;
		    	    myCounter = myCounter + 1;
			        opt.text = myCounter + ' - ' + item.description;
			        select_samples_consented_to.options.add(opt);
			    });

				if(myCounter > 0) {
				    if($('#samples_consented_to').val() != "") {
					    var sel_sct_split = $('#samples_consented_to').val().split(",");
					    for (i=0;i<sel_sct_split.length;i++){
					    	selectedCheckBoxValues.push(sel_sct_split[i]);
					    }
					    $('.samples_consented_to_selectpicker').selectpicker('val', selectedCheckBoxValues); 
				    }
				    $('.samples_consented_to_selectpicker').selectpicker('refresh');
				    $('#select_samples_consented_to option:selected').each(function() {
				    	all_sample_consent_txt = all_sample_consent_txt + $(this).text() + ' ';
				    });
				    $('#samples_consented_to_span').attr('data-content', all_sample_consent_txt);
				}
			}
		    
			break;
			
		}
	}
</script>
<h4>${headerTitleToShow}</h4>
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-8 offset-md-2">
       <span class="anchor" id="formConsent"></span>
         <div class="card card-outline-secondary">
           <div class="card-header" style="color: #FEFEFE">
             <h3 class="mb-0">Consent</h3>
           </div>
          <div id="consent_body_div" class="card-body">
			<div id="consent_withdrawn_alert_header_bold" class="alert alert-danger" role="alert" style="display:none;">
		  	</div>
		  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="sam_coll_before_sep_2006" class="col-sm-4 col-form-label text-left">Sample Collected Before Sep'06 <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="sam_coll_before_sep_2006" name="sam_coll_before_sep_2006" path="sam_coll_before_sep_2006" 
		      		onchange="uploadFormDataToSessionObjects('CONSENT',this)" class="browser-default custom-select custom-select-sm validate_this_selection">
		          <option value=""></option>
		          <option value="No">No</option>
		          <option value="Yes">Yes</option>
		      </form:select>
              <label id="sam_coll_before_sep_2006-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Was the sample collected before September 2006?">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="date_of_consent_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="date_of_consent" class="col-sm-4 col-form-label text-left">Date Of Consent <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="date" id="date_of_consent" name="date_of_consent" path="date_of_consent" placeholder="dd-MM-YYYY" maxlength="10"
	              		onblur="uploadFormDataToSessionObjects('CONSENT',this);" class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
	              <label id="date_of_consent-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="The date consent was obtained, as recorded on consent form">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="consent_taken_by_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="consent_taken_by" class="col-sm-4 col-form-label text-left">Consent Taken By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="text" id="consent_taken_by" name="consent_taken_by" path="consent_taken_by" style="text-transform: capitalize;"
	              		maxlength="50" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects('CONSENT',this)"></form:input> 
	              <label id="consent_taken_by-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="The name of the person that took consent">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="loc_id_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="loc_id" class="col-sm-4 col-form-label text-left">Location <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="loc_id" name="loc_id" path="loc_id" onchange="uploadFormDataToSessionObjects('CONSENT',this)" 
		      		class="browser-default custom-select custom-select-sm">
		          <option value=""></option>
				  <c:forEach items="${user_selected_locations}" var = "loc">
			          <option value="${loc.loc_id}">${loc.loc_name}</option>
				  </c:forEach>
		      </form:select>
              <label id="loc_id-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Hospital site where consent was obtained">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="verbal_consent_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="verbal_consent" class="col-sm-4 col-form-label text-left">Verbal Consent <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="verbal_consent" name="verbal_consent" path="verbal_consent" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects('CONSENT',this);" 
		      		class="browser-default custom-select custom-select-sm">
		        <option value=""></option>
		        <option value="No">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
	             <label id="verbal_consent-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Was the consent obtained only verbally?">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="verbal_consent_recorded_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="verbal_consent_recorded" class="col-sm-4 col-form-label text-left">Verbal Consent Recorded <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="verbal_consent_recorded" name="verbal_consent_recorded" path="verbal_consent_recorded" 
		      		class="browser-default custom-select custom-select-sm" onchange="uploadFormDataToSessionObjects('CONSENT',this)">
		        <option value=""></option>
		        <option value="Physical notes">Physical notes</option>
		        <option value="Email">Email</option>
		        <option value="Millenium">Millenium</option>
		        <option value="Other">Other</option>
		      </form:select>
	             <label id="verbal_consent_recorded-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Where was the verbal consent recorded?">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="verbal_consent_recorded_by_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="verbal_consent_recorded_by" class="col-sm-4 col-form-label text-left">Verbal Consent Recorded By <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="text" id="verbal_consent_recorded_by" name="verbal_consent_recorded_by" path="verbal_consent_recorded_by" style="text-transform: capitalize;"
	              		maxlength="30" class="form-control form-control-sm floatlabel" onchange="uploadFormDataToSessionObjects('CONSENT',this)"></form:input> 
	              <label id="verbal_consent_recorded_by-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="The name of the person who took verbal consent">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="verbal_consent_document_file_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="verbal_consent_document_file" class="col-sm-4 col-form-label text-left">Verbal Consent Document <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
					<div class="custom-file">
				  <form:input type="file" class="custom-file-input" id="verbal_consent_document_file" name="verbal_consent_document_file" 
				  		path="" style="color: transparent;" accept="image/*,application/pdf" onchange="uploadFormDataToSessionObjects('CONSENT',this)"></form:input>
				  <label class="custom-file-label" for="verbal_consent_document_file" data-browse="Choose File"></label>
					</div>		 
	              	<a id="verbal_consent_document_file_label" style="color:#513CA1; display: none;" class="consents_document_label_link"
	              		onclick="uploadFormDataToSessionObjects('CONSENT_FILES',this)"></a>
				<button id="verbal_consent_document_file_button" type="button" class="close" aria-label="Close" 
						style="color:#513CA1; display: none;" onclick="processPatientConsentInfectionRisk('REMOVE-FILE',this,false);">
				  <span aria-hidden="true">&times;</span>
				</button>               
	              <label id="verbal_consent_document_file_label-validation" style="color:red; display: none;"></label> 
		    </div>			    
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Upload a copy of any documentation confirming verbal consent">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
			<div id="form_type_alert" class="alert alert-danger" role="alert" style="display:none;">
			   This does not match Volunteer status selected on previous screen
			</div>	          
		  <div id="form_type_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="form_type" class="col-sm-4 col-form-label text-left">Form Type <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="form_type" name="form_type" path="form_type" class="browser-default custom-select custom-select-sm"
		      		onchange="processPatientConsentInfectionRisk('ALERT-PATIENT-VOLUNTEER',this,false);processDropdownMenus('FORM-VERSION');
		      			processDropdownMenus('CONSENT-EXCLUSION');uploadFormDataToSessionObjects('CONSENT',this);" >
		          <option value=""></option>
				  <c:forEach items="${consentFormTypes}" var = "cft">
			          <option value="${cft.description}">${cft.description}</option>
				  </c:forEach>
		      </form:select>
	          <label id="form_type-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Is this a patient or volunteer consent form?">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="form_version_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="select_form_version_id" class="col-sm-4 col-form-label text-left">Form Version <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="select_form_version_id" name="select_form_version_id" path="" 
		      		onchange="processDropdownMenus('CONSENT-EXCLUSION');uploadFormDataToSessionObjects('CONSENT',this);" 
		      		class="browser-default custom-select custom-select-sm">
		      </form:select>
	          <label id="select_form_version_id-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="The version of the consent form used">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="digital_cf_attachment_file_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="digital_cf_attachment_file" class="col-sm-4 col-form-label text-left">Digital CF Attachment <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
				<div class="custom-file">
				  <form:input type="file" class="custom-file-input" id="digital_cf_attachment_file" name="digital_cf_attachment_file" 
				  		path="" style="color: transparent;" accept="image/*,application/pdf" onchange="uploadFormDataToSessionObjects('CONSENT',this)"></form:input>
				  <label class="custom-file-label" for="digital_cf_attachment_file" data-browse="Choose File"></label>
				</div>		 
	            <a id="digital_cf_attachment_file_label" style="color:#513CA1;display:none;" class="consents_document_label_link"
	            	onclick="uploadFormDataToSessionObjects('CONSENT_FILES',this)"></a>
				<button id="digital_cf_attachment_file_button" type="button" class="close" aria-label="Close" 
						style="color:#513CA1; display: none;" onclick="processPatientConsentInfectionRisk('REMOVE-FILE',this,false);">
				  <span aria-hidden="true">&times;</span>
				</button>               
	            <label id="digital_cf_attachment_file_label-validation" style="color:red;display:none;"></label> 
		    </div>			    
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Attach a copy of the physical consent form">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="consent_type_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="consent_type" class="col-sm-4 col-form-label text-left">Type Of Consent <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="consent_type" name="consent_type" path="consent_type" 
		      		onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects('CONSENT',this);" class="browser-default custom-select custom-select-sm">
		        <option value=""></option>
		        <option value="Partial">Partial</option>
		        <option value="Full">Full</option>
		      </form:select>
	          <label id="consent_type-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" 
		    	data-content="If the patient has consented to all clauses, then the 'Type of consent' will be 'Full'. If there are any clauses not consented to, then they 'Type of consent' will be 'Partial'.">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="select_consent_exclusions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="select_consent_exclusions" class="col-sm-4 col-form-label text-left">Select the clauses that were not agreed to <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="select_consent_exclusions" name="select_consent_exclusions" path="" title="Nothing selected"
		      		multiple="true" class="selectpicker consent_exclusions_selectpicker form-control" 
		      		data-actions-box="true" onchange="processUserSelection(this);uploadFormDataToSessionObjects('CONSENT',this);">
		      </form:select>
	          <label id="select_consent_exclusions-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span id="consent_exclusions_span" class="d-inline-block" data-toggle="popover" data-trigger="focus" 
		    	data-content="Select all clauses that the individual did not consent to.">
				<button id="consent_exclusion_btn" type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="exclusions_comment_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="exclusions_comment" class="col-sm-4 col-form-label text-left">Exclusion Comments</label>
		    <div class="col-sm-6 col-md-6">
	              <form:textarea id="exclusions_comment" name="exclusions_comment" path="exclusions_comment" onchange="uploadFormDataToSessionObjects('CONSENT',this)"
	              		maxlength="500" rows="5" class="form-control form-control-sm floatlabel avoid_pid_data"></form:textarea> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" 
		    	data-content="Notes on consent exclusions given that is not fully covered by the clauses e.g. Do not reapproach in the future">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="select_samples_consented_to_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="select_samples_consented_to" class="col-sm-4 col-form-label text-left">Select the samples that were consented to
		    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="select_samples_consented_to" name="select_samples_consented_to" path="" multiple="true" title="Nothing selected" data-selected-text-format="count"
		      		class="selectpicker samples_consented_to_selectpicker form-control" data-actions-box="true" onchange="processUserSelection(this);uploadFormDataToSessionObjects('CONSENT',this);">
		      </form:select>
	          <label id="select_samples_consented_to-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span id="samples_consented_to_span" class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Select all samples that the individual consented to.">
				<button id="samples_consented_to_btn" type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="additional_document_file_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="additional_document_file" class="col-sm-4 col-form-label text-left">Additional Documents </label>
		    <div class="col-sm-6 col-md-6">
					<div class="custom-file">
				  <form:input type="file" class="custom-file-input" id="additional_document_file" name="additional_document_file" 
				  		path="" style="color: transparent;" accept="image/*,application/pdf" onchange="uploadFormDataToSessionObjects('CONSENT',this)"></form:input>
				  <label class="custom-file-label" for="additional_document_file" data-browse="Choose File"></label>
					</div>		 
	              	<a id="additional_document_file_label" style="color:#513CA1; display: none;" class="consents_document_label_link"
	              		onclick="uploadFormDataToSessionObjects('CONSENT_FILES',this)"></a>
				<button id="additional_document_file_button" type="button" class="close" aria-label="Close" 
						style="color:#513CA1; display: none;" onclick="processPatientConsentInfectionRisk('REMOVE-FILE',this,false);">
				  <span aria-hidden="true">&times;</span>
				</button>               
	              <label id="additional_document_file_label-validation" style="color:red; display: none;"></label> 
		    </div>
		  </div>			    
		  <div id="consent_notes_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="consent_notes" class="col-sm-4 col-form-label text-left">Notes</label>
		    <div class="col-sm-6 col-md-6">
	              <form:textarea id="consent_notes" name="consent_notes" path="consent_notes" maxlength="500" 
	              		rows="5" onchange="uploadFormDataToSessionObjects('CONSENT',this)"
	              		class="form-control form-control-sm floatlabel avoid_pid_data"></form:textarea> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Any relevant information including file notes">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="stop_sample_donation_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
		    <label for="stop_sample_donation" class="col-sm-4 col-form-label text-left">Patient Stops Sample Donation <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="stop_sample_donation" name="stop_sample_donation" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects('CONSENT',this)" 
		      		path="stop_sample_donation" class="browser-default custom-select custom-select-sm">
		        <option value=""></option>
		        <option value="No">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
	          <label id="stop_sample_donation-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Patient no longer wishes to donate samples">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="stop_sample_donation_date_div" class="form-group row row-bottom-margin ml-2" style="display:none;margin-bottom:5px;">
		    <label for="stop_sample_donation_date" class="col-sm-4 col-form-label text-left">Sample Donation End Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="date" id="stop_sample_donation_date" name="stop_sample_donation_date" path="stop_sample_donation_date" 
	              		 placeholder="dd-MM-YYYY" onblur="uploadFormDataToSessionObjects('CONSENT',this);" 
	              		 class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
	             <label id="stop_sample_donation_date-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Date from which no samples should be collected">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="withdrawn_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			<div id="consent_withdrawn_alert" class="alert alert-danger ml-2" role="alert" style="display:none;">
			   Consent Withdrawn Alert Text
			</div>	          
		    <label for="withdrawn" class="col-sm-4 col-form-label text-left">Consent Withdrawal <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
		      <form:select id="withdrawn" name="withdrawn" onchange="hideAndShowContainer(this);uploadFormDataToSessionObjects('CONSENT',this);" 
		      		path="withdrawn" class="browser-default custom-select custom-select-sm">
		        <option value=""></option>
		        <option value="No">No</option>
		        <option value="Yes">Yes</option>
		      </form:select>
	          <label id="withdrawn-validation" style="color:red;display:none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Has consent been withdrawn?">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="withdrawal_date_div" class="form-group row row-bottom-margin ml-2" style="display: none;margin-bottom:5px;">
		    <label for="withdrawal_date" class="col-sm-4 col-form-label text-left">Withdrawn Date <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
	              <form:input type="date" id="withdrawal_date" name="withdrawal_date" path="withdrawal_date" 
	              		 placeholder="dd-MM-YYYY" onblur="uploadFormDataToSessionObjects('CONSENT',this);" 
	              		class="form-control form-control-sm floatlabel validate_this_date"></form:input> 
	             <label id="withdrawal_date-validation" style="color:red; display: none;"></label> 
		    </div>
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Date consent was withdrawn">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <div id="withdrawal_document_file_div" class="form-group row row-bottom-margin ml-2" style="display:none; margin-bottom:5px;">
		    <label for="withdrawal_document_file" class="col-sm-4 col-form-label text-left">Withdrawn Document <i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
		    <div class="col-sm-6 col-md-6">
					<div class="custom-file">
				  <form:input type="file" class="custom-file-input" id="withdrawal_document_file" name="withdrawal_document_file" 
				  		path="" style="color: transparent;" accept="image/*,application/pdf" onchange="uploadFormDataToSessionObjects('CONSENT',this)"></form:input>
				  <label class="custom-file-label" for="withdrawal_document_file" data-browse="Choose File"></label>
					</div>		 
	              	<a id="withdrawal_document_file_label" style="color:#513CA1; display: none;" class="consents_document_label_link"
	              		onclick="uploadFormDataToSessionObjects('CONSENT_FILES',this)"></a>
				<button id="withdrawal_document_file_button" type="button" class="close" aria-label="Close" 
						style="color:#513CA1; display: none;" onclick="processPatientConsentInfectionRisk('REMOVE-FILE',this,false);">
				  <span aria-hidden="true">&times;</span>
				</button>               
	            <label id="withdrawal_document_file_label-validation" style="color:red; display: none;"></label> 
		    </div>			    
		    <span class="d-inline-block" data-toggle="popover" data-trigger="focus" data-content="Upload letter/email requesting consent withdrawal">
				<button type="button" tabindex="-1" class="btn-circle-sm m-1" style="background-color:transparent;border:none;outline:none;">
					<i class="fas fa-info-circle" style="color:#513CA1;"></i></button>		    
		    </span>
		  </div>
		  <form:input type="hidden" name="cn_patient_id" id="cn_patient_id" path="cn_patient_id"/>
		  <form:input type="hidden" name="consent_status" id="consent_status" path="status"/>
		  <form:input type="hidden" name="consent_id" id="consent_id" path="consent_id"/>
		  <form:input type="hidden" name="form_version_id" id="form_version_id" path="form_version_id"/>
		  <form:input type="hidden" name="consent_exclusions" id="consent_exclusions" path="consent_exclusions"/>
		  <form:input type="hidden" name="samples_consented_to" id="samples_consented_to" path="samples_consented_to"/>
		  <form:input type="hidden" name="consent_which_department" id="consent_which_department" path="consent_which_department"/>
		  <form:input type="hidden" name="is_imported" id="is_imported" path="is_imported"/>
		  <form:input type="hidden" name="is_validated" id="is_validated" path="is_validated"/>
		  <form:input type="hidden" name="is_audited" id="is_audited" path="is_audited"/>
		  <form:input type="hidden" name="is_finalised" id="is_finalised" path="is_finalised"/>
		  <form:input type="hidden" name="is_withdrawn" id="is_withdrawn" path="is_withdrawn"/>
		  <form:input type="hidden" name="withdrawal_document_id" id="withdrawal_document_id" path="withdrawal_document_id"/>
		  <form:input type="hidden" name="consent_deletion_date" id="consent_deletion_date" path="consent_deletion_date"/>
		  <form:input type="hidden" name="marked_for_auditing" id="marked_for_auditing" path="marked_for_auditing"/>
			<div id="save_consent_btn_div" class="text-center">
				<br>
				<c:choose>
				  <c:when test = "${fn:containsIgnoreCase(consent_access, 'add') || fn:containsIgnoreCase(consent_access, 'edit')  || fn:length(various_actions.active_actions) gt 0}">
				    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;display:none;" class="btn btn-sm" type="button"
				  		name="edit_patient_btn" id="edit_patient_btn" onclick="processUserSelection(this)">
				  		<i class="fas fa-user"></i> Back: Patient</form:button>
				    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;display:none;" class="btn btn-sm" type="button"
				  		name="edit_consent_btn" id="edit_consent_btn" onclick="processUserSelection(this)">
				  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
				  		<i class="fas fa-edit"></i> Edit Consent</form:button>
				    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;display:none;" class="btn btn-sm" type="button"
				  		name="save_consent_btn" id="save_consent_btn" onclick="validateFormFields('validate_consent',this,'update_consent',false)">
				  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
				  		<i class="fas fa-check-circle"></i> Save Consent</form:button>
				    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;display:none;"
				    	class="btn btn-sm" type="button" name="confirm_notification_consent_btn" id="confirm_notification_consent_btn"><i class="fas fa-broom"></i> Confirm Notification</form:button>
				    <form:button style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;display:none;" onclick="processUserSelection(this)"
				    	class="btn btn-sm" type="button" name="clear_consent_data_btn" id="clear_consent_data_btn"><i class="fas fa-broom"></i> Clear Consent Data</form:button>
				    <form:button style="margin-top:5px; background-color:#2E008B;color:#FEFEFE;display:none;" class="btn btn-sm" type="button" 
				 		name="add_infection_risk_btn" id="add_infection_risk_btn" 
				 			onclick="validateFormFields('validate_consent',this,'add_infection_risk',true)">
				 		<i class="fas fa-check-circle"></i> Next: Record Infection Risk</form:button>
				    <a id="consent_cancel_href" href="choose_locations" style="display:none;"><form:button id="cancel_consent_btn" 
				    	style="margin-top:5px;background-color:#2E008B;color:#FEFEFE;"
				    	class="btn btn-sm" type="button"><i class="fas fa-window-close"></i> Cancel</form:button></a>
				  </c:when>
				  <c:otherwise>
					 <h5>You are NOT authorised to add or edit a Consent</h5>
				  </c:otherwise>
				</c:choose>
			 </div>
			</div>
		  </div>
	   </div>
	</div>
  </div>
 </div> 
<form:input type="hidden" name="user_department" id="user_department" path="" value="${user_selected_department.dept_acronym}"/>
</form:form>
<div id="consent_validate_div" style="display:none">
	<%@ include file="add_consent_validate.jsp" %>
</div>
<div id="consent_audit_div" style="display:none">
	<%@ include file="add_consent_audit.jsp" %>
</div>
</body>
</html>