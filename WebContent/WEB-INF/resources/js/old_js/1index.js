var all_consents_from_db = [];
var infection_risks_db_data = [];

function hideAndShowContainer(whichButton) 
{
	var idOfInputbox = $(whichButton).attr("id");
    var valueOfInputbox = $(whichButton).val();

    switch(idOfInputbox) {
    case 'action_type':
    	
    	var default_save_btn_text = 'Save';
    	if(document.getElementById(idOfInputbox).value.toLowerCase().includes('no_action_to_send')) {
    		document.getElementById("action_notes_div").style.display = "none";
    		if(window.location.pathname.includes('consent_withdrawn')) {
    			default_save_btn_text = 'Confirm Notification';
    		} else if(window.location.pathname.includes('remove_samples')){
    			default_save_btn_text = 'Confirm Disposal Of Samples';
    		} else if(window.location.pathname.includes('verify_consent')){
    			default_save_btn_text = 'Confirm Consent Validation';
    		}
    	} else {
    		document.getElementById("action_notes_div").style.display = "";
   		}
		document.getElementById("save_changes_pat_cons_irs_btn").innerHTML = '<i class="fas fa-check-circle"></i> ' + default_save_btn_text;
    	break;
    	
    case 'digital_cf_attachment_file_label': case 'verbal_consent_document_file_label': case 'withdrawal_document_file_label': case 'additional_document_file_label':

    	if (document.getElementById(idOfInputbox).innerHTML != '') {
    		document.getElementById(idOfInputbox).style.display = "";
    		document.getElementById(idOfInputbox.replace('_label','_button')).style.display = "";
        	if (document.getElementById('whichPageToShow').value.toLowerCase().includes('edit_patient') &&
        			document.getElementById('consent_access').value.toLowerCase().includes('view_restricted'))
        	{
        		document.getElementById(idOfInputbox).style.pointerEvents = 'none';
        	}
    		
    	}
    	else {
    		document.getElementById(idOfInputbox).style.display = "none";
    		document.getElementById(idOfInputbox.replace('_label','_button')).style.display = "none";
    	}
    	break;
    	
    case 'select_continued_risk':
    	
		if(document.getElementById(idOfInputbox).value.toLowerCase().includes('yes')) {
    		document.getElementById("continued_risk").value = "Yes";
    		document.getElementById("episode_finished_date_div").style.display = "none";
		}
		else {
    		document.getElementById("continued_risk").value = "No";
    		document.getElementById("episode_finished_date_div").style.display = "";
		}
		break;
		
    case 'consent_access':

    	if (document.getElementById('whichPageToShow').value.toLowerCase().includes('edit_patient') &&
    			document.getElementById('consent_access').value.toLowerCase().includes('view_restricted'))
    	{	
    		document.getElementById("forename_div").style.display = "none";
    		document.getElementById("surname_div").style.display = "none";
    		document.getElementById("gender_div").style.display = "none";
    		document.getElementById("date_of_birth_div").style.display = "none";
    		document.getElementById("hospital_number_div").style.display = "none";
    		document.getElementById("nhs_number_div").style.display = "none";
    	} 
    	else
    	{
    		document.getElementById("forename_div").style.display = "";
    		document.getElementById("surname_div").style.display = "";
    		document.getElementById("gender_div").style.display = "";
    		document.getElementById("date_of_birth_div").style.display = "";
    		document.getElementById("hospital_number_div").style.display = "";
    		document.getElementById("nhs_number_div").style.display = "";
    	}
    	break;
    	
    case 'select_volunteer':

    	if (document.getElementById('select_volunteer').value.toLowerCase().includes('yes'))
    	{
    		document.getElementById("select_age_div").style.display = "";
    		
    		document.getElementById("date_of_birth_div").style.display = "none";
    		document.getElementById("hospital_number_div").style.display = "none";
    		document.getElementById("nhs_number_div").style.display = "none";

    		document.getElementById("verbal_document_checked_div").style.display = "";
    		document.getElementById("verbal_consent_checked_date_div").style.display = "";
    		document.getElementById("verbal_consent_checked_by_div").style.display = "";

    		document.getElementById("digital_cf_attached_div").style.display = "none";
    		document.getElementById("cf_physical_location_div").style.display = "none";
    		document.getElementById("date_of_consent_stated_div").style.display = "none";
    		document.getElementById("patient_signature_div").style.display = "none";
    		document.getElementById("person_taking_consent_div").style.display = "none";
    		document.getElementById("cf_validity_div").style.display = "none";
    		document.getElementById("cf_checked_date_div").style.display = "none";
    		document.getElementById("cf_checked_by_div").style.display = "none";
    		
    		document.getElementById('form_type').value = "Volunteer";
    	}
    	else
    	{
    		document.getElementById("date_of_birth_div").style.display = "";
    		document.getElementById("hospital_number_div").style.display = "";
    		document.getElementById("nhs_number_div").style.display = "";

    		document.getElementById("select_age_div").style.display = "none";
    	
    		document.getElementById("verbal_document_checked_div").style.display = "none";
    		document.getElementById("verbal_consent_checked_date_div").style.display = "none";
    		document.getElementById("verbal_consent_checked_by_div").style.display = "none";

    		document.getElementById("digital_cf_attached_div").style.display = "";
    		document.getElementById("cf_physical_location_div").style.display = "";
    		document.getElementById("date_of_consent_stated_div").style.display = "";
    		document.getElementById("patient_signature_div").style.display = "";
    		document.getElementById("person_taking_consent_div").style.display = "";
    		document.getElementById("cf_validity_div").style.display = "";
    		document.getElementById("cf_checked_date_div").style.display = "";
    		document.getElementById("cf_checked_by_div").style.display = "";
    		
    		document.getElementById('form_type').value = "Patient";
    	}
    	
    	processDropdownMenus('FORM-VERSION');
    	processDropdownMenus('CONSENT-EXCLUSION');
    	
        break;
        
	case 'consent_type':
    	
    	if (document.getElementById('consent_type').value.toLowerCase().includes('partial'))
    	{
    		document.getElementById("select_consent_exclusions_div").style.display = "";
    	}
    	else
    	{
    		document.getElementById("select_consent_exclusions_div").style.display = "none";
    		document.getElementById("select_consent_exclusions").value = "";
    		document.getElementById("consent_exclusions").value = "";
    	}
    	break;
    
	case 'data_discrepancies_identified':

    	if (document.getElementById('data_discrepancies_identified').value.toLowerCase().includes('yes')){
    		document.getElementById("source_of_verified_data_div").style.display = "";
    		document.getElementById("data_discrepancies_description_div").style.display = "";
    		document.getElementById("data_discrepancies_verified_div").style.display = "";
    		document.getElementById("data_discrepancies_verification_date_div").style.display = "";
    		document.getElementById("data_discrepancies_verified_by_div").style.display = "";
    	} else {
    		document.getElementById("source_of_verified_data_div").style.display = "none";
    		document.getElementById("data_discrepancies_description_div").style.display = "none";
    		document.getElementById("data_discrepancies_verified_div").style.display = "none";
    		document.getElementById("data_discrepancies_verification_date_div").style.display = "none";
    		document.getElementById("data_discrepancies_verified_by_div").style.display = "none";
    	}
		
		break;
    	
	case 'verify_consent_exclusions':

    	if (document.getElementById('verify_consent_exclusions').value.toLowerCase().includes('no')){
    		document.getElementById("statements_excluded_div").style.display = "";
    	} else {
    		document.getElementById("statements_excluded_div").style.display = "none";
    	}
		
		break;
		
    case 'verbal_consent':

    	if (document.getElementById('verbal_consent').value.toLowerCase().includes('yes'))
    	{	
    		document.getElementById("verbal_consent_recorded_div").style.display = "";
    		document.getElementById("verbal_consent_recorded_by_div").style.display = "";
    		document.getElementById("verbal_consent_document_file_div").style.display = "";

    		document.getElementById("verbal_document_checked_div").style.display = "";
    		document.getElementById("verbal_consent_checked_date_div").style.display = "";
    		document.getElementById("verbal_consent_checked_by_div").style.display = "";
    		
    		document.getElementById("form_type_div").style.display = "none";
    		document.getElementById("form_version_div").style.display = "none";
    		document.getElementById("digital_cf_attachment_file_div").style.display = "none";
    		document.getElementById("consent_type_div").style.display = "none";
    		document.getElementById("select_consent_exclusions_div").style.display = "none";

    		document.getElementById("digital_cf_attached_div").style.display = "none";
    		document.getElementById("cf_physical_location_div").style.display = "none";
    		document.getElementById("date_of_consent_stated_div").style.display = "none";
    		document.getElementById("patient_signature_div").style.display = "none";
    		document.getElementById("person_taking_consent_div").style.display = "none";
    		document.getElementById("cf_validity_div").style.display = "none";
    		document.getElementById("cf_checked_date_div").style.display = "none";
    		document.getElementById("cf_checked_by_div").style.display = "none";
    		
    	}
    	else
    	{
    		document.getElementById("verbal_consent_recorded_div").style.display = "none";
    		document.getElementById("verbal_consent_recorded_by_div").style.display = "none";
    		document.getElementById("verbal_consent_document_file_div").style.display = "none";

    		document.getElementById("verbal_document_checked_div").style.display = "none";
    		document.getElementById("verbal_consent_checked_date_div").style.display = "none";
    		document.getElementById("verbal_consent_checked_by_div").style.display = "none";
    		
    		document.getElementById("form_type_div").style.display = "";
    		document.getElementById("form_version_div").style.display = "";
    		document.getElementById("digital_cf_attachment_file_div").style.display = "";
    		document.getElementById("consent_type_div").style.display = "";
    		document.getElementById("select_consent_exclusions_div").style.display = "";

    		document.getElementById("digital_cf_attached_div").style.display = "";
    		document.getElementById("cf_physical_location_div").style.display = "";
    		document.getElementById("date_of_consent_stated_div").style.display = "";
    		document.getElementById("patient_signature_div").style.display = "";
    		document.getElementById("person_taking_consent_div").style.display = "";
    		document.getElementById("cf_validity_div").style.display = "";
    		document.getElementById("cf_checked_date_div").style.display = "";
    		document.getElementById("cf_checked_by_div").style.display = "";
    		
    	}
        break;

    case 'withdrawn':

    	if (!window.location.pathname.includes('consent_withdrawn') 
    			&& !document.getElementById('consent_status').value.toLowerCase().includes('validate') 
    			&& document.getElementById('withdrawn').value.toLowerCase().includes('yes')) {
    		alert('You cannot withdraw consent which has NOT been validated yet. Changing withdrawn consent to NO...');
   			document.getElementById('withdrawn').selectedIndex = 0;
    	} 

    	if (document.getElementById('withdrawn').value.toLowerCase().includes('yes')){
    		document.getElementById("withdrawal_date_div").style.display = "";
    		document.getElementById("withdrawal_document_file_div").style.display = "";
    	} else {
    		document.getElementById("withdrawal_date_div").style.display = "none";
    		document.getElementById("withdrawal_document_file_div").style.display = "none";
    	}
    	
        break;

    case 'stop_sample_donation':
    	
    	if (document.getElementById('stop_sample_donation').value.toLowerCase().includes('yes')){
    		document.getElementById("stop_sample_donation_date_div").style.display = "";
    	} else {
    		document.getElementById("stop_sample_donation_date_div").style.display = "none";
    	}
        break;
        
    case 'infection_risk_exist':
    	
    	if (document.getElementById("infection_risk_exist").value.toLowerCase().includes('yes')
    			|| document.getElementById("infection_risk_exist").value.toLowerCase().includes('unknown'))
    	{
    		document.getElementById("select_infection_type_id_div").style.display = "";
    		document.getElementById("comments_div").style.display = "";
    		document.getElementById("select_episode_of_infection_div").style.display = "";
    		document.getElementById("episode_start_date_div").style.display = "";
    		document.getElementById("select_continued_risk_div").style.display = "";
    		document.getElementById("add_infection_risk_btn_div").style.display = "";
    		document.getElementById("select_sample_collection_div").style.display = "";
    		document.getElementById("select_sample_type_id_div").style.display = "";
    		document.getElementById("sample_collection_date_div").style.display = "";
    	}
    	else
    	{
    		document.getElementById("select_infection_type_id_div").style.display = "none";
       		document.getElementById("other_infection_risk_div").style.display = "none";
    		document.getElementById("comments_div").style.display = "none";
    		document.getElementById("select_episode_of_infection_div").style.display = "none";
    		document.getElementById("episode_start_date_div").style.display = "none";
    		document.getElementById("select_continued_risk_div").style.display = "none";
    		document.getElementById("episode_finished_date_div").style.display = "none";
    		document.getElementById("add_infection_risk_btn_div").style.display = "none";
    		document.getElementById("select_sample_collection_div").style.display = "none";
    		document.getElementById("select_sample_type_id_div").style.display = "none";
    		document.getElementById("sample_collection_date_div").style.display = "none";
    	}
        break;

    case 'select_infection_type_id':
    	
		if (document.getElementById('select_infection_type_id').options[
			document.getElementById('select_infection_type_id').selectedIndex].text.toLowerCase().includes('other'))
		{
    		document.getElementById("other_infection_risk_div").style.display = "";
		}
    	else
    	{
       		document.getElementById("other_infection_risk_div").style.display = "none";
       		document.getElementById("other_infection_risk").value = "";
    	}

		if (document.getElementById('select_infection_type_id').options[
			document.getElementById('select_infection_type_id').selectedIndex].text.toLowerCase().includes('ongoing')) {
    		document.getElementById("select_ongoing_infection_type_id_div").style.display = "";
		}
    	else
    	{
       		document.getElementById("select_ongoing_infection_type_id_div").style.display = "none";
       		document.getElementById("ongoing_infection_type_id").value = "";
    	}
		break;
    }
}
function reloadPatientConsentInfectionRiskData()
{
	if (window.location.pathname.includes('edit_patient') || window.location.pathname.includes('verify_consent') 
			|| window.location.pathname.includes('reapproach_patient') || window.location.pathname.includes('query_patient') 
			|| window.location.pathname.includes('consent_withdrawn') || window.location.pathname.includes('remove_samples') 
			|| window.location.pathname.includes('infection_risk_unknown') || window.location.pathname.includes('infection_risk_found')) 
	{

		processPatientConsentInfectionRisk('PATIENT',document.getElementById('patient_id'),false);
		
		$("#patient_body_div *").prop('disabled',true);
		$("#consent_body_div *").prop('disabled',true);
		$("#infection_risk_body_div *").prop('disabled',true);
		$(".btn-circle-sm").prop('disabled',false);

   		document.getElementById("user_action_form_div").style.display = "none";
		document.getElementById("validate_consent_form_div").style.display = "none";
		document.getElementById("consent_body_div").style.display = "none";
   		document.getElementById("infection_risk_body_div").style.display = "none";
   		document.getElementById("save_clear_btns").style.display = "none";
   		document.getElementById("save_changes_cancel_btn_div").style.display = "none";
   		document.getElementById("edit_btn_div").style.display = "";
   		
	} else if (window.location.pathname.includes('add_another_infection_risk')) {

		if (document.getElementById('patient_id').value <= 0) { // Clear database consent/IR 
		
			infection_risks_db_data = [];
			all_consents_from_db = [];

		} else {
			
			if (infection_risks_db_data.length > 0) {
		  		initialiseForm('INFECTION-RISK-DB-LIST',infection_risks_db_data);
			}
			else {
				if (document.getElementById('patient_id').value) {
					processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-DB',document.getElementById('patient_id'),false);
				}
			}

			if (all_consents_from_db.length > 0) {
		  		initialiseForm('CONSENTS-LIST-DB',all_consents_from_db);
			}
			else {
				if (document.getElementById('patient_id').value) {
					processPatientConsentInfectionRisk('GET-ALL-CONSENTS-FROM-DB',document.getElementById('patient_id'),false);
				}
			}
			
		}
		
		document.getElementById('select_gender').value = document.getElementById('gender').value;
		document.getElementById('select_volunteer').value = document.getElementById('volunteer').value;
	    hideAndShowContainer(document.getElementById('select_volunteer'));

	    processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-SESSION',null,false);
		processPatientConsentInfectionRisk('GET-CONSENT-FROM-SESSION',null,false);
	} 

	if((document.getElementById("whichPageToShow").value.toLowerCase().includes('add_consent') 
			|| document.getElementById("whichPageToShow").value.toLowerCase().includes('edit_patient'))
			&& document.getElementById("whichDepartment").value.toUpperCase().includes('HOTB')) {
   		document.getElementById("select_samples_consented_to_div").style.display = '';
	} else {
   		document.getElementById("select_samples_consented_to_div").style.display = 'none';
	}
}
function formatInputBoxValue(typeOfInput,whichInputBox)
{
	switch (typeOfInput) {
	case "DATE":

		$(whichInputBox).val($(whichInputBox).val().split('/').join('-')); // Replace date's forward slashes (if any) with hyphens
		$(whichInputBox).val($(whichInputBox).val().split('.').join('-'));// Replace date's dots (if any) with hyphens
		$(whichInputBox).val($(whichInputBox).val().replace(/(^|-)0+/g, "$1"));
		var split_date = $(whichInputBox).val().split('-');
		
		if (Number.isNaN(Date.parse(
				$(whichInputBox).val().split('-')[1] + '-' + 
				$(whichInputBox).val().split('-')[0] + '-' + 
				$(whichInputBox).val().split('-')[2]))) { // Date parsing takes mm-dd-yyyy as compared to dd-mm-yyyy entered by user
			alert("Invalid date entered");
			document.getElementById($(whichInputBox).attr("id")).value = '';
			document.getElementById($(whichInputBox).attr("id")).focus({preventScroll:false});
			return false;
		} 
		break;
	}
}
function processPatientConsentInfectionRisk(whatToProcess, whichInputBox, giveConfirmPrompt) 
{
    var nameOfInputbox = [];
    var valueOfInputbox = [];

    if (whichInputBox != null) {
    	
    	switch (whatToProcess) {
    	case 'REMOVE-FILE':
            nameOfInputbox.push($(whichInputBox).attr("id").replace('_button','')); // Get the file input box name instead of the button name
            valueOfInputbox.push(document.getElementById('consent_id').value);
    		break;
    	default:
            nameOfInputbox.push($(whichInputBox).attr("id"));
	    	if ($(whichInputBox).val() != '') {
	            valueOfInputbox.push($(whichInputBox).val());
	        } else {
	            valueOfInputbox.push($(whichInputBox).attr("value"));
	        }
    		break;
    	}
    	
    }
    
	switch(whatToProcess) {
	case 'DELETE-PAT-CON-IR':
		
		$('#myModal input:checked').each(function() {
			nameOfInputbox.push($(this).attr('id'));
			valueOfInputbox.push($(this).attr('value'));
		});
		
        $.ajax({    
            type : "Get",     
            url : "processPatientConsentInfectionRisk.html",     
            data : "whatToProcess=" + whatToProcess + "&nameOfInputbox=" + nameOfInputbox + "&valueOfInputbox=" + valueOfInputbox,
            dataType : "json",
            success : function(data) {

            },    
            error : function(e) {    
           	 	console.log('Error: ' + e);     
            }    
           });
        
		break;

	case 'GET-CONSENT-FROM-SESSION':

        $.ajax({    
            type : "Get",     
            url : "processPatientConsentInfectionRisk.html",     
            data : "whatToProcess=" + whatToProcess,
            dataType : "json",
            success : function(data) {
          		initialiseForm("CONSENT", data);
            },    
            error : function(e) {    
           	 	console.log('Error occured in ' + whatToProcess + " with error description = " + e);     
            }    
           });    
    	break;

    case 'PATIENT': 

	    switch(nameOfInputbox[0]) {
	    case 'nhs_number': case 'patient_id': // Search for NHS number only
	    	break;
	    case 'surname': // Search 2 out of 3 fields
	    	if ($('#date_of_birth').val() != '') {
	    		nameOfInputbox.push('date_of_birth');
	    		valueOfInputbox.push($('#date_of_birth').val());
	    	} else if ($('#hospital_number').val() != '') {
	    		nameOfInputbox.push('hospital_number');
	    		valueOfInputbox.push($('#hospital_number').val());
	    	} else if ($('#patient.select_age').val() != '') {
	    		nameOfInputbox.push('age');
	    		valueOfInputbox.push($('#patient.select_age').val());
	    	}
	    	else {
	    		return;
	    	}
	    	break;
	    case 'patient.select_age':
	    	if ($('#surname').val() != '') {
	    		nameOfInputbox.push('surname');
	    		valueOfInputbox.push($('#surname').val());
	    	} 
	    	else {
	    		return;
	    	}
	    	break;
	    case 'date_of_birth':
	    	if ($('#surname').val() != '') {
	    		nameOfInputbox.push('surname');
	    		valueOfInputbox.push($('#surname').val());
	    	} else if ($('#hospital_number').val() != '') {
	    		nameOfInputbox.push('hospital_number');
	    		valueOfInputbox.push($('#hospital_number').val());
	    	}
	    	else {
	    		return;
	    	}
	    	break;
	    case 'hospital_number':
	    	if ($('#surname').val() != '') {
	    		nameOfInputbox.push('surname');
	    		valueOfInputbox.push($('#surname').val());
	    	} else if ($('#date_of_birth').val() != '') {
	    		nameOfInputbox.push('date_of_birth');
	    		valueOfInputbox.push($('#date_of_birth').val());
	    	}
	    	else {
	    		return;
	    	}
	    	break;
	    }   
    	
    	$.ajax({    
	     type : "Get",     
	     url : "processPatientConsentInfectionRisk.html",     
	     data : "whatToProcess=" + whatToProcess + "&nameOfInputbox=" + nameOfInputbox + "&valueOfInputbox=" + valueOfInputbox,
	     dataType : "json",
	     success : function(data) {
	    	 
	    	 if(data.length == 1)
	    	 {
		    	 data.forEach(function(item,index,arr){
		    		 var processPatient = false;
		    		 if (giveConfirmPrompt == true) {
			    		 	var confirmMsg;
			    		 	if (nameOfInputbox[0] == 'patient.select_age') {
			    		    	confirmMsg = 'Patient with Surname [' + item.surname + '] and Age [' + item.age + ']'
									    		+ ' already exists. Click OK to populate the data or click CANCEL to carry on adding a new patient.';
			    		    }
			    		 	else {
			    		    	confirmMsg = 'Patient with NHS number [' + item.nhs_number + '], Surname [' + item.surname 
					    		+ '], Date Of Birth [' + item.date_of_birth + '] and MRN [' + item.hospital_number + ']'
					    		+ ' already exists. Click OK to populate the data or click CANCEL to carry on adding a new patient.';
			    		 	}
					    	if (confirm(confirmMsg)) {
					    		processPatient = true;
					    	}
		    		 }
		    		 else {
			    		processPatient = true;
		    		 }

			    	if (processPatient == true) {
			    		initialiseForm('PATIENT',item);
			    		all_consents_from_db = [];
			    		infection_risks_db_data = [];
			    		if (window.location.pathname.includes('verify_consent') || window.location.pathname.includes('reapproach_patient') 
			    				|| window.location.pathname.includes('query_patient') || window.location.pathname.includes('remove_samples') 
			    				|| window.location.pathname.includes('infection_risk_unknown') || window.location.pathname.includes('infection_risk_found')) {
				    		processPatientConsentInfectionRisk('GET-SINGLE-CONSENT-FROM-DB',document.getElementById('consent_id'),false);
			    		} else if (window.location.pathname.includes('consent_withdrawn')) {
				    		processPatientConsentInfectionRisk('GET-ANY-CONSENT-FROM-DB',document.getElementById('consent_id'),false);
			    		} else {
				    		processPatientConsentInfectionRisk('GET-ALL-CONSENTS-FROM-DB',document.getElementById('patient_id'),false);
			    		}
			    		processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-DB',document.getElementById('patient_id'),false);
			    	}
			 	    	
			 	    }); 
	    	 }
	     },    
	     error : function(e) {    
       	 	 console.log('Error occured in ' + whatToProcess + " with error description = " + e);     
	     }    
	    });    
    	break;

    case 'GET-ALL-INFECTION-RISKS-FROM-DB': case 'GET-ALL-INFECTION-RISKS-FROM-SESSION': case 'GET-SINGLE-INFECTION-RISK-FROM-SESSION':

    	$.ajax({    
            type : "Get",     
            url : "processPatientConsentInfectionRisk.html",     
            data : "whatToProcess=" + whatToProcess + "&nameOfInputbox=" + nameOfInputbox + "&valueOfInputbox=" + valueOfInputbox,
            dataType : "json",
            success : function(data) {
            	switch (whatToProcess) {
            	case "GET-ALL-INFECTION-RISKS-FROM-DB": // Get all IRs from DB
		    		infection_risks_db_data = data;
              		initialiseForm('INFECTION-RISK-DB-LIST',data);
              		break;
            	case "GET-ALL-INFECTION-RISKS-FROM-SESSION": // Get all IRs from BUFFER
              		initialiseForm('ALL-INFECTION-RISK-FROM-SESSION-LIST',data);
              		break;
            	case "GET-SINGLE-INFECTION-RISK-FROM-SESSION": // Get single IR from BUFFER
              		initialiseForm('INFECTION-RISK',data);
              		break;
            	}
            },    
            error : function(e) {    
          	 	console.log('Error occured in ' + whatToProcess + " with error description = " + e);     
            }    
           });    
    	break;
    
    case "GET-ALL-CONSENTS-FROM-DB": case 'GET-SINGLE-CONSENT-FROM-DB': case "GET-SINGLE-CONSENT-FROM-SESSION": case "GET-SINGLE-CONSENT-VALIDATION-FROM-SESSION": 
    case "REMOVE-FILE": case "GET-ALL-ACTIONS-FROM-SESSION": case "GET-SINGLE-ACTION-FROM-SESSION": case 'GET-ANY-CONSENT-FROM-DB':
    	
		$.ajax({    
            type : "Get",     
            url : "processPatientConsentInfectionRisk.html",     
            data : "whatToProcess=" + whatToProcess + "&nameOfInputbox=" + nameOfInputbox + "&valueOfInputbox=" + valueOfInputbox,
            dataType : "json",
            success : function(data) {
            	switch (whatToProcess) {
            	case "GET-SINGLE-CONSENT-FROM-SESSION": 
              		initialiseForm('CONSENT',data);
              		break;
            	case "GET-SINGLE-CONSENT-VALIDATION-FROM-SESSION": 
              		initialiseForm('CONSENT-VALIDATION',data);
              		break;
            	case "GET-ALL-CONSENTS-FROM-DB": case 'GET-SINGLE-CONSENT-FROM-DB': case 'GET-ANY-CONSENT-FROM-DB': 
		    		all_consents_from_db = data;
              		initialiseForm('CONSENTS-LIST-DB',data);
              		break;
            	case "GET-ALL-ACTIONS-FROM-SESSION":
              		initialiseForm('ACTIONS-LIST-DB',data);
              		break;
            	case "GET-SINGLE-ACTION-FROM-SESSION":
              		initialiseForm('ACTION',data);
            		break;
            	}
            },    
            error : function(e) {    
          	 	console.log('Error occured in ' + whatToProcess + " with error description = " + e);     
            }    
           });    

	    if (whatToProcess == "REMOVE-FILE") {
	   		document.getElementById(nameOfInputbox[0] + '_label').innerHTML = '';
	   		document.getElementById(nameOfInputbox[0]).value = '';
			hideAndShowContainer(document.getElementById(nameOfInputbox[0] + '_label'));
	    }
		
    	break;

	}
}    	 
function initialiseForm(whichForm, dataToUse)
{
	switch (whichForm)
	{
	case "PATIENT":

		if (dataToUse)
		{	
			document.getElementById('patient_id').value = dataToUse.patient_id;
		    document.getElementById('database_id').value = dataToUse.database_id;
		    document.getElementById('secondary_id').value = dataToUse.secondary_id;
		    document.getElementById('surname').value = dataToUse.surname;
		    document.getElementById('forename').value = dataToUse.forename;
		    document.getElementById('select_gender').value = dataToUse.gender;
		    document.getElementById('select_volunteer').value = dataToUse.volunteer;
		    document.getElementById('patient.select_age').value = dataToUse.age;
		    document.getElementById('age').value = dataToUse.age;
		    document.getElementById('date_of_birth').value = dataToUse.date_of_birth;
		    document.getElementById('hospital_number').value = dataToUse.hospital_number;
		    document.getElementById('nhs_number').value = dataToUse.nhs_number;
		    document.getElementById('old_pat_id').value = dataToUse.old_pat_id;

		    var header = document.createElement("h4");
		    var checkbox = document.createElement("input");
	    	var label = document.createElement("label");
	    	
	    	header.textContent = "Patient";
	    	
	        checkbox.type = "checkbox"; 
	        checkbox.value = dataToUse.patient_id; 
	        checkbox.name = "delete_patient_id"; 
	        checkbox.id = "delete_patient_id"; 
	    	checkbox.setAttribute('onchange', 'processUserSelection(this)');
	        
	        label.htmlFor = "delete_patient_id";
	        label.appendChild(document.createTextNode(dataToUse.forename.toUpperCase() + ' ' + dataToUse.surname.toUpperCase()));
	        
	        $('.modal-body').empty();
	        document.getElementById("modal_dialog_body").appendChild(header);
	        document.getElementById("modal_dialog_body").appendChild(checkbox);
	        document.getElementById("modal_dialog_body").appendChild(document.createTextNode(' '));
	        document.getElementById("modal_dialog_body").appendChild(label);
			
		}
		else
		{
			document.getElementById('patient_id').value = 0;
		    document.getElementById('database_id').value = '';
		    document.getElementById('secondary_id').value = '';
		    document.getElementById('surname').value = '';
		    document.getElementById('forename').value = '';
		    document.getElementById('select_gender').selectedIndex = 0;
		    document.getElementById('select_volunteer').selectedIndex = 0;
		    document.getElementById('patient.select_age').selectedIndex = 0;
		    document.getElementById('age').value = '';
		    document.getElementById('date_of_birth').value = '';
		    document.getElementById('hospital_number').value = '';
		    document.getElementById('nhs_number').value = '';
		    document.getElementById('old_pat_id').value = '';
		}
	    hideAndShowContainer(document.getElementById('select_volunteer'));
	    hideAndShowContainer(document.getElementById('consent_access'));
		break;

	case "INFECTION-RISK-DB-LIST": case "ALL-INFECTION-RISK-FROM-SESSION-LIST": 
		
		var ul = document.getElementById("prev_infection_risk");
		var sel = document.getElementById("select_ongoing_infection_type_id");
		
		if (document.contains(document.getElementById("delete_all_ir_ids_div"))) {
            document.getElementById("delete_all_ir_ids_div").remove();
		}   
		
    	var delete_all_ir_ids_div = document.createElement("div");
    	delete_all_ir_ids_div.id = 'delete_all_ir_ids_div';
		
	    var header = document.createElement("h4");
    	header.textContent = "Infection Risk(s)";
    	delete_all_ir_ids_div.appendChild(document.createElement('hr'));
    	delete_all_ir_ids_div.appendChild(header);
		
		if (dataToUse)
		{
			var myCounter = 0;
			var dataToShow = '';
			dataToUse.forEach(function(item,index,arr){
		    	
		    	var li = document.createElement("li");	    	
		    	var input = document.createElement("input");
		    	var label = document.createElement("label");
		    	var option = document.createElement("option");

		    	input.setAttribute('type', 'checkbox');
		    	input.setAttribute('value', item.infection_risk_id);
		    	input.setAttribute('class', 'prev_infection_chk_boxes');
		    	input.setAttribute('name', 'prev_infection_chk_boxes');
		    	input.setAttribute('onchange', 'processUserSelection(this);');
		    	if (whichForm == 'INFECTION-RISK-DB-LIST' 
		    		&& (window.location.pathname.includes('add_consent') || window.location.pathname.includes('add_another_infection_risk'))) {
			    	input.setAttribute('disabled', true);
		    	}
		    	if(item.infection_type != null) {
			    	if (item.infection_type.is_this_ongoing == 1) {
				    	if(item.ongoing_infection_type.description.length >= 12) {
				    		dataToShow = item.ongoing_infection_type.short_description + ', '; 
				    	} else {
				    		dataToShow = item.ongoing_infection_type.description + ', '; 
				    	}
			    	} else {
				    	if(item.infection_type.description.length >= 12) {
				    		dataToShow = item.infection_type.short_description + ', '; 
				    	} else {
				    		dataToShow = item.infection_type.description + ', '; 
				    	}
			    	}
		    	}
		    	if (item.continued_risk.toLowerCase().includes('no')) {
		    		dataToShow = dataToShow + item.episode_start_date + ' & End: ' + item.episode_finished_date;
		    	} else {
		    		dataToShow = dataToShow + item.episode_start_date + ' - continued risk'; 
		    	}

		    	myCounter = myCounter + 1;
		    	label.innerHTML =  myCounter + '. ' + dataToShow;

		    	if (whichForm == "INFECTION-RISK-DB-LIST"){
		    		if(item.infection_type != null) {
				    	if (item.infection_type.is_this_ongoing == 1) {
				    		option.setAttribute('value', item.ongoing_infection_type.infection_type_id);
				    	} else {
				    		option.setAttribute('value', item.infection_type.infection_type_id);
				    	}
		    		}
		    		option.innerHTML = dataToShow;
		    		sel.appendChild(option);
		    	}
		    	
		    	li.appendChild(input);
		    	li.innerHTML += ' ';
		    	li.appendChild(label);
	
		    	ul.appendChild(li);
		    	
		    	var delete_ir_div = document.createElement("div");
		    	var delete_ir_checkbox = document.createElement("input");
		    	var delete_ir_label = document.createElement("label");

		    	delete_ir_checkbox.type = "checkbox"; 
		    	delete_ir_checkbox.value = item.infection_risk_id; 
		    	delete_ir_checkbox.id = "delete_ir_id"; 
		        
		    	delete_ir_label.htmlFor = "delete_ir_id";
		    	delete_ir_label.appendChild(document.createTextNode(myCounter + '. ' + dataToShow));
		        
		    	delete_ir_div.appendChild(delete_ir_checkbox);
		    	delete_ir_div.appendChild(document.createTextNode(' '));
		    	delete_ir_div.appendChild(delete_ir_label);
		    	
		    	delete_all_ir_ids_div.appendChild(delete_ir_div);
		    	
		    });
		}

		if (ul.getElementsByTagName("li").length > 0) {
			document.getElementById('prev_infection_risk_div').style.display = '';
			document.getElementById("modal_dialog_body").appendChild(delete_all_ir_ids_div);
		} else {
			document.getElementById('prev_infection_risk_div').style.display = 'none';
		}

	    break;
		
	case "INFECTION-RISK":

		document.getElementById('infection_risk_id').value = '';
	    document.getElementById('infection_risk_exist').selectedIndex = 0;
	    document.getElementById('select_infection_type_id').selectedIndex = 0;
	    document.getElementById('other_infection_risk').value = '';
	    document.getElementById('comments').value = '';
	    document.getElementById('select_episode_of_infection').selectedIndex = 0;
	    document.getElementById('episode_start_date').value = '';
	    document.getElementById('select_continued_risk').selectedIndex = 0;
	    document.getElementById('episode_finished_date').value = '';
	    document.getElementById('ongoing_infection_type_id').value = '';
	    document.getElementById('episode_of_infection').value = '';
	    document.getElementById('infection_type_id').value = '';
	    document.getElementById('continued_risk').value = 'No';
	    document.getElementById('select_sample_collection').selectedIndex = 0;
	    document.getElementById('select_sample_type_id').selectedIndex = 0;
	    document.getElementById('sample_collection_date').value = '';
		
		if (dataToUse)
		{	
			document.getElementById('infection_risk_id').value = dataToUse.infection_risk_id;
		    document.getElementById('infection_risk_exist').value = dataToUse.infection_risk_exist;
		    document.getElementById('select_infection_type_id').value = dataToUse.infection_type_id;
		    document.getElementById('other_infection_risk').value = dataToUse.other_infection_risk;
		    document.getElementById('comments').value = dataToUse.comments;
		    document.getElementById('select_episode_of_infection').value = dataToUse.episode_of_infection;
		    document.getElementById('episode_start_date').value = dataToUse.episode_start_date;
		    document.getElementById('select_continued_risk').value = dataToUse.continued_risk;
		    document.getElementById('episode_finished_date').value = dataToUse.episode_finished_date;
		    document.getElementById('select_ongoing_infection_type_id').value = dataToUse.ongoing_infection_type_id;
		    document.getElementById('ongoing_infection_type_id').value = dataToUse.ongoing_infection_type_id;
		    document.getElementById('episode_of_infection').value = dataToUse.episode_of_infection;
		    document.getElementById('infection_type_id').value = dataToUse.infection_type_id;
		    document.getElementById('continued_risk').value = dataToUse.continued_risk;
		    document.getElementById('select_sample_collection').value = dataToUse.sample_collection;
		    document.getElementById('select_sample_type_id').value = dataToUse.sample_type_id;
		    document.getElementById('sample_collection_date').value = dataToUse.sample_collection_date;
    	} 
	    
	    hideAndShowContainer(document.getElementById("infection_risk_exist"));
	    hideAndShowContainer(document.getElementById("select_infection_type_id"));
	    hideAndShowContainer(document.getElementById("select_continued_risk"));
		
		break;
	
	case 'CONSENT':
		
		document.getElementById('consent_id').value = '';
		document.getElementById('date_of_consent').value = '';
		document.getElementById('consent_taken_by').value = '';
		document.getElementById('loc_id').selectedIndex = 0;
		document.getElementById('verbal_consent').selectedIndex = 0;
		document.getElementById('form_type').selectedIndex = 0;
		document.getElementById('form_version_id').value = '';
		document.getElementById('select_form_version_id').selectedIndex = 0;
		document.getElementById('verbal_consent_recorded').selectedIndex = 0;
		document.getElementById('verbal_consent_recorded_by').value = '';
		document.getElementById('consent_type').selectedIndex = 0;
		document.getElementById('consent_exclusions').value = '';
		document.getElementById('samples_consented_to').value = '';
		document.getElementById('notes').value = '';
		document.getElementById('withdrawn').selectedIndex = 0;
		document.getElementById('withdrawal_date').value = '';
		document.getElementById('additional_document_file_label').innerHTML = '';
		document.getElementById('digital_cf_attachment_file_label').innerHTML = '';
		document.getElementById('withdrawal_document_file_label').innerHTML = '';
		document.getElementById('verbal_consent_document_file_label').innerHTML = '';
		document.getElementById('consent_status').value = '';
		
		if (dataToUse)
		{	
			document.getElementById('consent_id').value = dataToUse.consent_id;
	   		document.getElementById('cv_consent_id').value = dataToUse.consent_id;
			document.getElementById('date_of_consent').value = dataToUse.date_of_consent;
			document.getElementById('consent_taken_by').value = dataToUse.consent_taken_by;
			document.getElementById('loc_id').value = dataToUse.loc_id;
			document.getElementById('verbal_consent').value = dataToUse.verbal_consent;
			document.getElementById('form_type').value = dataToUse.form_type;
			document.getElementById('form_version_id').value = dataToUse.form_version_id;
			document.getElementById('verbal_consent_recorded').value = dataToUse.verbal_consent_recorded;
			document.getElementById('verbal_consent_recorded_by').value = dataToUse.verbal_consent_recorded_by;
			document.getElementById('consent_type').value = dataToUse.consent_type;
			document.getElementById('consent_exclusions').value = dataToUse.consent_exclusions;
			document.getElementById('samples_consented_to').value = dataToUse.samples_consented_to;
			document.getElementById('notes').value = dataToUse.notes;
			document.getElementById('withdrawn').value = dataToUse.withdrawn;
			document.getElementById('withdrawal_date').value = dataToUse.withdrawal_date;
			document.getElementById('consent_status').value = dataToUse.status;

			if (dataToUse.additional_document != null) {
				document.getElementById('additional_document_file_label').innerHTML = dataToUse.additional_document.file_name;
	            document.getElementById('additional_document_file_label').href = 
	            	URL.createObjectURL(new Blob([new Uint8Array(dataToUse.additional_document.file_data)], {type: dataToUse.additional_document.content_type}));
			}
			if (dataToUse.digital_cf_attachment != null) {
				document.getElementById('digital_cf_attachment_file_label').innerHTML = dataToUse.digital_cf_attachment.file_name;
	            document.getElementById('digital_cf_attachment_file_label').href = 
	            	URL.createObjectURL(new Blob([new Uint8Array(dataToUse.digital_cf_attachment.file_data)], {type: dataToUse.digital_cf_attachment.content_type}));
			}
			if (dataToUse.withdrawal_document != null) {
				document.getElementById('withdrawal_document_file_label').innerHTML = dataToUse.withdrawal_document.file_name;
	            document.getElementById('withdrawal_document_file_label').href = 
	            	URL.createObjectURL(new Blob([new Uint8Array(dataToUse.withdrawal_document.file_data)], {type: dataToUse.withdrawal_document.content_type}));
			}
			if (dataToUse.verbal_consent_document != null) {
				document.getElementById('verbal_consent_document_file_label').innerHTML = dataToUse.verbal_consent_document.file_name;
	            document.getElementById('verbal_consent_document_file_label').href = 
	            	URL.createObjectURL(new Blob([new Uint8Array(dataToUse.verbal_consent_document.file_data)], {type: dataToUse.verbal_consent_document.content_type}));
			}
		}

	    processDropdownMenus('FORM-VERSION'); 
	    processDropdownMenus('CONSENT-EXCLUSION');
	    if(document.getElementById('select_samples_consented_to_div').style.display == '') {
		    processDropdownMenus('SAMPLE-CONSENT-TO');
	    }
	    
	    hideAndShowContainer(document.getElementById("verbal_consent"));
	    hideAndShowContainer(document.getElementById("consent_type"));
	    hideAndShowContainer(document.getElementById("withdrawn"));
	    hideAndShowContainer(document.getElementById("additional_document_file_label"));
	    hideAndShowContainer(document.getElementById("digital_cf_attachment_file_label"));
	    hideAndShowContainer(document.getElementById("withdrawal_document_file_label"));
	    hideAndShowContainer(document.getElementById("verbal_consent_document_file_label"));

	    $('#consent.select_consent_exclusions').selectpicker('refresh');
	    $('#consent.select_samples_consented_to').selectpicker('refresh');
		
	    break;
	
	case 'CONSENT-VALIDATION':
		
   		document.getElementById('cv_consent_id').value = 0;
		document.getElementById('verbal_document_checked').selectedIndex = 0;
		document.getElementById('verbal_consent_checked_date').value = '';
		document.getElementById('digital_cf_attached').selectedIndex = 0;
		document.getElementById('cf_physical_location').value = '';
		document.getElementById('date_of_consent_stated').selectedIndex = 0;
		document.getElementById('patient_signature').selectedIndex = 0;
		document.getElementById('person_taking_consent').selectedIndex = 0;
		document.getElementById('cf_validity').selectedIndex = 0;
		document.getElementById('cf_checked_date').value = '';
		document.getElementById('verify_consent_exclusions').selectedIndex = 0;
		document.getElementById('statements_excluded').value = '';
		document.getElementById('cf_audit_notes').value = '';
		document.getElementById('data_discrepancies_identified').selectedIndex = 0;
		document.getElementById('data_accuracy_date').value = '';
		document.getElementById('source_of_verified_data').selectedIndex = 0;
		document.getElementById('data_discrepancies_description').value = '';
		document.getElementById('data_discrepancies_verified').selectedIndex = 0;
		document.getElementById('data_discrepancies_verification_date').value = '';
		document.getElementById('cf_checked_by').value = document.getElementById('current_user').value;
		document.getElementById('verbal_consent_checked_by').value = document.getElementById('current_user').value;
		document.getElementById('data_discrepancies_verified_by').value = document.getElementById('current_user').value;
		document.getElementById('data_accuracy_checked_by').value = document.getElementById('current_user').value;
		
		if (dataToUse)
		{	
			document.getElementById('cv_consent_id').value = dataToUse.cv_consent_id;
			if(dataToUse.consent_validate_id > 0) {
				document.getElementById('verbal_document_checked').value = dataToUse.verbal_document_checked;
				document.getElementById('verbal_consent_checked_date').value = dataToUse.verbal_consent_checked_date;
				document.getElementById('verbal_consent_checked_by').value = dataToUse.verbal_consent_checked_by;
				document.getElementById('digital_cf_attached').value = dataToUse.digital_cf_attached;
				document.getElementById('cf_physical_location').value = dataToUse.cf_physical_location;
				document.getElementById('date_of_consent_stated').value = dataToUse.date_of_consent_stated;
				document.getElementById('patient_signature').value = dataToUse.patient_signature;
				document.getElementById('person_taking_consent').value = dataToUse.person_taking_consent;
				document.getElementById('cf_validity').value = dataToUse.cf_validity;
				document.getElementById('cf_checked_date').value = dataToUse.cf_checked_date;
				document.getElementById('cf_checked_by').value = dataToUse.cf_checked_by;
				document.getElementById('verify_consent_exclusions').value = dataToUse.verify_consent_exclusions;
				document.getElementById('statements_excluded').value = dataToUse.statements_excluded;
				document.getElementById('cf_audit_notes').value = dataToUse.cf_audit_notes;
				document.getElementById('data_discrepancies_identified').value = dataToUse.data_discrepancies_identified;
				document.getElementById('data_accuracy_date').value = dataToUse.data_accuracy_date;
				document.getElementById('data_accuracy_checked_by').value = dataToUse.data_accuracy_checked_by;
				document.getElementById('source_of_verified_data').value =dataToUse.source_of_verified_data;
				document.getElementById('data_discrepancies_description').value = dataToUse.data_discrepancies_description;
				document.getElementById('data_discrepancies_verified').value = dataToUse.data_discrepancies_verified;
				document.getElementById('data_discrepancies_verification_date').value = dataToUse.data_discrepancies_verification_date;
				document.getElementById('data_discrepancies_verified_by').value = dataToUse.data_discrepancies_verified_by;
			}
		}
		
	    hideAndShowContainer(document.getElementById("verify_consent_exclusions"));
	    hideAndShowContainer(document.getElementById("data_discrepancies_identified"));
		
	    break;

	case 'ACTION':

		if(window.location.pathname.includes('user_actions')) {
	   		document.getElementById('completed_task_action_notes').value = '';
			if (dataToUse){
		   		document.getElementById('completed_task_action_notes').value = dataToUse.notes;
			}
		} else {
	   		document.getElementById('prev_user_action_notes').value = '';
			if (dataToUse){
		   		document.getElementById('prev_user_action_notes').value = dataToUse.notes;
			}
		}

   		break;

	case "CONSENTS-LIST-DB":
		
		var ul = document.getElementById("prev_consent_list");

        var header = document.createElement("h4");
    	header.textContent = "Consent(s)";
    	document.getElementById("modal_dialog_body").appendChild(document.createElement('hr'));
    	document.getElementById("modal_dialog_body").appendChild(header);
		
		if (dataToUse)
		{
			var myCounter = 0;
			if(Array.isArray(dataToUse)) {
				dataToUse.forEach(function(item,index,arr){
					myCounter = myCounter + 1;
					addItemsToList('CONSENT', ul, item, myCounter);
				});
			} else {
				myCounter = myCounter + 1;
				addItemsToList('CONSENT', ul, dataToUse, myCounter);
			}
		}

		if (ul.getElementsByTagName("li").length > 0) {
			document.getElementById('prev_consent_list_div').style.display = '';
		} else {
			document.getElementById('prev_consent_list_div').style.display = 'none';
		}

	    break;
	    
	case "ACTIONS-LIST-DB":
		
		var myCounter = 0;
		
		if(window.location.pathname.includes('user_actions')) {

			$("#completed_task_list").empty();
			
			var completed_task_list = document.getElementById("completed_task_list");
			
			addItemsToList('ACTION', completed_task_list, dataToUse, myCounter);
			
			if (completed_task_list.getElementsByTagName("li").length > 0) {
				document.getElementById('completed_task_list_label').innerHTML = 'Completed Tasks List';
			} else {
				document.getElementById('completed_task_list_label').innerHTML = 'No Completed Tasks List Found';
			}
			
		} else {
			
			$("#prev_user_action_list").empty();
			
			var prev_user_action_list = document.getElementById("prev_user_action_list");
			
			addItemsToList('ACTION',prev_user_action_list,dataToUse,myCounter);
			
			if (prev_user_action_list.getElementsByTagName("li").length > 0) {
				document.getElementById('prev_user_action_list_label').innerHTML = 'Previous User Actions';
			} else {
				document.getElementById('prev_user_action_list_label').innerHTML = 'No Previous User Actions Found';
			}
		}
		
		break;

	}
}
function addItemsToList(whatToProcess, combo_box, dataToProcess, myCounter)
{
	switch (whatToProcess) {
	case 'ACTION':
		
		var action_list_text = '';
		
		if (dataToProcess)
		{
			dataToProcess.forEach(function(item,index,arr){
				
				var li = document.createElement("li");	    	
		    	var input = document.createElement("input");
		    	var label = document.createElement("label");
		    	
		    	input.setAttribute('type', 'checkbox');
		    	input.setAttribute('value', item.action_id);
		    	input.setAttribute('class', 'prev_action_chk_boxes');
		    	input.setAttribute('name', 'prev_action_chk_boxes');
		    	input.setAttribute('onchange', 'processUserSelection(this);');

				myCounter = myCounter + 1;
				action_list_text =  myCounter + '. ' + item.description;
				label.innerHTML = action_list_text.substring(0,100); 
				if(action_list_text.length > 100) {
					label.innerHTML = label.innerHTML + '...'; 
				} 
				
		    	li.appendChild(input);
		    	li.innerHTML += ' ';
		    	li.appendChild(label);
		    	
		    	combo_box.appendChild(li);

			});
		}
		
		break;
		
	case 'CONSENT':
		
		var li = document.createElement("li");	    	
		var input = document.createElement("input");
		var label = document.createElement("label");
		
		input.setAttribute('type', 'checkbox');
		input.setAttribute('value', dataToProcess.consent_id);
		input.setAttribute('class', 'prev_consent_chk_boxes');
		input.setAttribute('name', 'prev_consent_chk_boxes');
		input.setAttribute('onchange', 'processUserSelection(this);');

		label.setAttribute('name', 'prev_consent_labels');
		
		label.innerHTML =  myCounter + '. ' + dataToProcess.date_of_consent + ', ' + dataToProcess.form_type + ', ' + dataToProcess.form_version.short_description + ', ' + dataToProcess.consent_type;
		if (dataToProcess.status.toLowerCase().includes('validate')) {
			label.innerHTML = label.innerHTML + ' (VALIDATED)'
		}
		
		if (window.location.pathname.includes('add_consent') || window.location.pathname.includes('add_another_infection_risk')) {
	    	input.setAttribute('disabled', true);
		}
		
		li.appendChild(input);
		li.innerHTML += ' ';
		li.appendChild(label);
		
		combo_box.appendChild(li);
		
		var go_ahead = true;
		if(dataToProcess.status.toLowerCase().includes('validate') 
				&& !document.getElementById('consent_access').value.toLowerCase().includes('delete')) { // Some user cannot delete validated consents
			go_ahead = false;
		}
		
		if(go_ahead) 
		{
			var delete_consent_div = document.createElement("div");
			var delete_consent_checkbox = document.createElement("input");
			var delete_consent_label = document.createElement("label");
			
			delete_consent_checkbox.type = "checkbox"; 
			delete_consent_checkbox.value = dataToProcess.consent_id; 
			delete_consent_checkbox.id = "delete_consent_id"; 
		    
			delete_consent_label.htmlFor = "delete_consent_id";
			delete_consent_label.appendChild(document.createTextNode(' ' + label.innerHTML));
			
			delete_consent_div.appendChild(delete_consent_checkbox);
			delete_consent_div.appendChild(document.createTextNode(' '));
			delete_consent_div.appendChild(delete_consent_label);
			
			document.getElementById("modal_dialog_body").appendChild(delete_consent_div);
			
		}
		
		break;
	}
}
function processKeyPressEvent(whichEvent, whichInput) 
{
	var key = whichEvent.which || whichEvent.keyCode || whichEvent.charCode;
	if(key === 8) {return false;}
	
	var thisVal = $(whichInput).val();
	var numChars = thisVal.length;

	switch ($(whichInput).attr("id")) {
	case 'nhs_number':
		if(thisVal.length === 3 || thisVal.length === 7){
			thisVal += '-';
			$(whichInput).val(thisVal);
		}    		
		break;
	}
}    		
function processUserSelection(whichInput)
{	
    var selectedCheckBoxValues = [];
    
    if (window.location.pathname.includes('home')) {

    	var brands = $("#" + whichInput.id + "_selected_locations option:selected");
	    if (brands.length <= 0) {
    		alert('Please select location(s) before clicking the submit button');
    		return false;
	    }
	    
	    $(brands).each(function(index, brand){
	    	selectedCheckBoxValues.push([$(this).val()]);
	    });
	    document.getElementById('selected_dept_id').value = whichInput.value;
	    document.getElementById('selected_locations').value = selectedCheckBoxValues;
    	
		$('.my_waiting_modal').modal('show');
    	document.choose_locations_form.submit();
	    
    } else {

    	switch ($(whichInput).attr("id")) {
    	case 'view_patient_link':
    		$('.my_waiting_modal').modal('show');
    		break;
    	}
    	
    	switch ($(whichInput).attr("name")) {
    	case 'completed_task_radio':

      		initialiseForm('ACTION',null);
    		if ($(whichInput).attr('previousValue') == 'checked') {
	   	        $(whichInput).removeAttr('checked');
	    	    $(whichInput).attr('previousValue', false);
    	    } else {
	    	    $("input[name="+$(whichInput).attr('name')+"]:radio").attr('previousValue', false);
	    	    $(whichInput).attr('previousValue', 'checked');
    			processPatientConsentInfectionRisk('GET-SINGLE-ACTION-FROM-SESSION',whichInput,false);
    	    }
    		break;

    	case 'user_action_radio': 
    	    
    		if ($(whichInput).attr('previousValue') == 'checked') {
	   	        $(whichInput).removeAttr('checked');
	    	    $(whichInput).attr('previousValue', false);
    	    } else {
	    	    $("input[name="+$(whichInput).attr('name')+"]:radio").attr('previousValue', false);
	    	    $(whichInput).attr('previousValue', 'checked');
    	    }
    	    break;
    	    
    	case 'view_action_btn': 

    		if ($("input[name=user_action_radio]:checked").val()) {
    			document.getElementById('action_id').value = $("input[name=user_action_radio]:checked").val();
       			document.user_actions_form.action = $("input[name=user_action_radio]:checked").attr("id")
       				.substring(0,$("input[name=user_action_radio]:checked").attr("id").indexOf('_radio')); // Remove '_radio_1' from 'verify_consent_radio_1' send 'verify_consent' to controller 
        		$('.my_waiting_modal').modal('show');
    			document.user_actions_form.submit();
    		} else {
    			alert("You must select a task above to view it");
    		}

    		break;

    	case 'delete_patient_id':

    		$('#myModal input:checkbox').prop('checked', $(whichInput).prop("checked"));
    		if($(whichInput).prop("checked")) {
        		$('#myModal input:checkbox').prop('disabled', true);
    		} else {
        		$('#myModal input:checkbox').prop('disabled', false);
    		}
    		$(whichInput).prop('disabled', false);
    		break;
    		
    	case 'cancel_delete':
    		
    		$('#myModal input:checkbox').prop('checked', false);
    		$('#myModal').modal('hide');
    		break;
    		
    	case 'confirm_delete':
    		
    		if($('#myModal input:checked').length <= 0) {
    			alert('Nothing selected, please select what you want to delete');
    		} else {
    			if(document.getElementById('confirm_delete_notes').value) {
            		if (confirm('Do you want to continue deleting these items from the database?')) {

            			$('#myModal input:checked').each(function() {
            		    	selectedCheckBoxValues.push([$(this).attr('id') + ':' + $(this).attr('value')]);
            			});
            			document.getElementById('deletion_list').value = selectedCheckBoxValues;
            			document.getElementById('deletion_notes').value = document.getElementById('confirm_delete_notes').value;
            	    	document.patient_consent_ir_form.action = 'delete_pat_con_ir';
                		$('.my_waiting_modal').modal('show');
            	    	document.patient_consent_ir_form.submit();
            			break;

            		} 
    			} else {
        			alert('Please type in a reason for deletion in the NOTES section');
    			}
    		}
    		
    		break;
    		
    	case 'delete_patient_consents_ir_btn':
    		
    		$('.modal-title').html("Confirm Delete?");
    		
    		var notes_header = document.createElement("h4");
    	    notes_header.textContent = "Notes";
    	    document.getElementById("modal_dialog_body").appendChild(document.createElement('hr'));
    	    document.getElementById("modal_dialog_body").appendChild(notes_header);
        	
        	var textarea = document.createElement("textarea");
        	textarea.setAttribute('id', 'confirm_delete_notes');
        	textarea.cols = 50;
        	document.getElementById("modal_dialog_body").appendChild(textarea);
    		
	    	var confirm_button = document.createElement("input");
	    	confirm_button.setAttribute('type', 'button');
	    	confirm_button.setAttribute('value', 'Delete');
	    	confirm_button.setAttribute('name', 'confirm_delete');
	    	confirm_button.setAttribute('onclick', 'processUserSelection(this);');

	    	var cancel_button = document.createElement("input");
	    	cancel_button.setAttribute('type', 'button');
	    	cancel_button.setAttribute('value', 'Cancel');
	    	cancel_button.setAttribute('name', 'cancel_delete');
	    	cancel_button.setAttribute('onclick', 'processUserSelection(this);');
	    	
	    	$('.modal-footer').empty();
    		document.getElementById('modal_dialog_footer').appendChild(confirm_button);
    		document.getElementById('modal_dialog_footer').appendChild(cancel_button);
    		
    		$('#myModal').modal('show');
    		
    		break;
    		
    	case 'edit_consent_btn':
    		
    		var go_ahead = true;
    		if (document.getElementById('consent_id').value <= 0) {
    			alert('Please select the consent first before clicking on the EDIT button');
    			go_ahead = false;
    		}
    		
    		$('.prev_consent_chk_boxes').each(function() {
    			if ($(this).prop("checked") && $(this).next().text().toLowerCase().includes('validated') && 
    					!document.getElementById('consent_access').value.toLowerCase().includes('edit')) {
        			alert('You are not authorized to modify the selected consent');
        			go_ahead = false;
    			}
			});
    		
    		if (go_ahead) {
        		$("#patient_body_div *").prop('disabled',false);
        		$("#consent_body_div *").prop('disabled',false);
        		$("#infection_risk_body_div *").prop('disabled',false);
        		$("#add_infection_risk_btn").click('processUserSelection(this);');
           		document.getElementById("save_clear_btns").style.display = 'none';
           		document.getElementById("edit_btn_div").style.display = 'none';
           		document.getElementById("save_changes_pat_cons_irs_btn").style.display = ''; 
           		document.getElementById("add_infection_risk_btn").style.display = 'none'; 
           		document.getElementById("infection_risk_body_div").style.display = ''; 
           		document.getElementById("save_changes_cancel_btn_div").style.display = '';
           		document.getElementById("prev_infection_risk_div").style.display = '';
           		$('.selectpicker').selectpicker('refresh');
    		}
    		break;
    	
    	case 'prev_action_chk_boxes':

    		$('.prev_action_chk_boxes').on('change', function() {
    		    $('.prev_action_chk_boxes').not(this).prop('checked', false);
    		});	
    		
    	    if ($(whichInput).prop("checked") == true) {
    	    	processPatientConsentInfectionRisk('GET-SINGLE-ACTION-FROM-SESSION',whichInput,false);
    	    } else {
        	    document.getElementById('prev_user_action_notes').value = '';
    	    }
    	    	
    		break;
    	
    	case 'prev_consent_chk_boxes':
    		
    		if (document.getElementById('consent_id').value > 0) {
            	if (validateFormFields('validate_consent',null,false) == false) {
            		return false;
            	}
    		}
    		$('.prev_consent_chk_boxes').on('change', function() {
    		    $('.prev_consent_chk_boxes').not(this).prop('checked', false);
    		});	
    	    if ($(whichInput).prop("checked") == true) {
    			processPatientConsentInfectionRisk('GET-SINGLE-CONSENT-FROM-SESSION',whichInput,false);
        	    document.getElementById('consent_body_div').style.display = '';
        		if (window.location.pathname.includes('verify_consent')) {
    				if(document.getElementById('consent_validate_access').value.toLowerCase().includes('edit')
    						|| document.getElementById('consent_validate_access').value.toLowerCase().includes('view')) {
    					if (!$(whichInput).parent().text().toLowerCase().includes('validated')) {
    		    			processPatientConsentInfectionRisk('GET-SINGLE-CONSENT-VALIDATION-FROM-SESSION',whichInput,false);
    		    			processPatientConsentInfectionRisk('GET-ALL-ACTIONS-FROM-SESSION',whichInput,false);
                       		document.getElementById("validate_consent_form_div").style.display = "";
                       		document.getElementById("user_action_form_div").style.display = "";
        					document.getElementById("save_changes_cancel_btn_div").style.display = "";
    					} else {
                       		document.getElementById("validate_consent_form_div").style.display = "none";
                       		document.getElementById("user_action_form_div").style.display = "none";
                       		if(document.getElementById('edit_btn_div').style.display == '') {
                           		document.getElementById("save_changes_cancel_btn_div").style.display = "none";
                       		} else {
                           		document.getElementById("save_changes_cancel_btn_div").style.display = "";
                       		}
    					}
    				} 
            	    if (document.getElementById('consent_validate_access').value.toLowerCase().includes('edit')) {
            			$("#validate_consent_form_div *").prop('disabled',false);
            	    } else {
            			$("#validate_consent_form_div *").prop('disabled',true);
            	    }
        		} else if (window.location.pathname.includes('reapproach_patient') || window.location.pathname.includes('query_patient')
        				|| window.location.pathname.includes('consent_withdrawn') || window.location.pathname.includes('remove_samples')
        				|| window.location.pathname.includes('infection_risk_unknown') || window.location.pathname.includes('infection_risk_found')) 
        			{
					if (!$(whichInput).parent().text().toLowerCase().includes('validated')) {
		    			processPatientConsentInfectionRisk('GET-ALL-ACTIONS-FROM-SESSION',whichInput,false);
                   		document.getElementById("user_action_form_div").style.display = "";
    					document.getElementById("save_changes_cancel_btn_div").style.display = "";
					} else {
                   		document.getElementById("user_action_form_div").style.display = "none";
                   		if(document.getElementById('edit_btn_div').style.display == '') {
                       		document.getElementById("save_changes_cancel_btn_div").style.display = "none";
                   		} else {
                       		document.getElementById("save_changes_cancel_btn_div").style.display = "";
                   		}
					}
        		}
				if(document.getElementById('edit_btn_div').style.display == 'none') {
					if ($(whichInput).next().text().toLowerCase().includes('validated') && !document.getElementById('consent_access').value.toLowerCase().includes('edit')) {
	        			alert('You are not authorized to modify the selected consent');
						$("#consent_body_div *").prop('disabled',true);
					} else if (!$(whichInput).next().text().toLowerCase().includes('validated') 
							&& (document.getElementById('consent_access').value.toLowerCase().includes('edit')
								|| document.getElementById('consent_draft_access').value.toLowerCase().includes('edit'))) {
						$("#consent_body_div *").prop('disabled',false);
					}
				}
    	    }
    	    else {
        	    document.getElementById('consent_body_div').style.display = 'none';
           		document.getElementById("validate_consent_form_div").style.display = "none";
           		document.getElementById("user_action_form_div").style.display = "none";
           		if(document.getElementById("edit_btn_div").style.display == "") { // If 'edit' button is visible and no consent is selected then hide 'save changes' button
           			document.getElementById("save_changes_cancel_btn_div").style.display = "none";
           		}
    		    initialiseForm('CONSENT', null);
    		    initialiseForm('CONSENT-VALIDATION', null);
    	    }
    	    break;

    	case 'prev_infection_chk_boxes':
    		
    		if (document.getElementById('infection_risk_id').value > 0) {
            	if (validateFormFields('',document.getElementById('add_infection_risk_btn'),false) == false) {
            		return false;
            	}
    		}
    		
    		$('.prev_infection_chk_boxes').on('change', function() {
    		    $('.prev_infection_chk_boxes').not(this).prop('checked', false);
    		});	
    	    if ($(whichInput).prop("checked") == true) {
    			processPatientConsentInfectionRisk('GET-SINGLE-INFECTION-RISK-FROM-SESSION',whichInput,false);
        	    document.getElementById('infection_risk_body_div').style.display = '';
    	    }
    	    else {
    		    initialiseForm('INFECTION-RISK', null);
    	    }
    	    break;
    	
    	case 'consent.select_consent_exclusions':
    		
			var select_consent_exclusions = document.getElementById('select_consent_exclusions');
    		
	    	for(i=0; i < select_consent_exclusions.options.length;i++){
	    		if (select_consent_exclusions.options[i].selected == true) {
	    			selectedCheckBoxValues.push([select_consent_exclusions.options[i].value]);
	    		}
	    	}		
    	    document.getElementById('consent_exclusions').value = selectedCheckBoxValues;
    		break;

    	case 'consent.select_samples_consented_to':
    		
			var select_samples_consented_to = document.getElementById('select_samples_consented_to');
    		
	    	for(i=0; i < select_samples_consented_to.options.length;i++){
	    		if (select_samples_consented_to.options[i].selected == true) {
	    			selectedCheckBoxValues.push([select_samples_consented_to.options[i].value]);
	    		}
	    	}		
    	    document.getElementById('samples_consented_to').value = selectedCheckBoxValues;
    		break;
    		
    	}
    }
}
function validateFormFields(whatToProcess,whichButtonId,submitFormNow)
{	
	switch (whatToProcess.toLowerCase()) {
    case 'validate_all':

    	if(document.getElementById('validate_consent_form_div').style.display == '') 
    	{
        	if (document.getElementById('verbal_consent').value.toLowerCase().includes('yes')) {
    			if (!checkEmpty(document.getElementById('verbal_document_checked'),'Verbal Document Checked') 
    					|| !checkEmpty(document.getElementById('verbal_consent_checked_date'),'Verbal Consent Checked Date')
    					|| !checkDateRange('NO-FUTURE-DATE',document.getElementById('verbal_consent_checked_date'),'Future Date Not Allowed')
    					|| !checkEmpty(document.getElementById('verbal_consent_checked_by'),'Verbal Consent Checked By')) {
    	    		return false;
    			}
    			document.getElementById('digital_cf_attached').value = '';
    			document.getElementById('cf_physical_location').value = '';
    			document.getElementById('date_of_consent_stated').value = '';
    			document.getElementById('patient_signature').value = '';
    			document.getElementById('person_taking_consent').value = '';
    			document.getElementById('cf_validity').value = '';
    			document.getElementById('cf_checked_date').value = '';
    			document.getElementById('cf_checked_by').value = '';
        	} else {
    			if (!checkEmpty(document.getElementById('digital_cf_attached'),'Digital CF Attached') 
    					|| !checkEmpty(document.getElementById('cf_physical_location'),'CF Physical Location') 
    					|| !checkEmpty(document.getElementById('date_of_consent_stated'),'Consent Date Stated') 
    					|| !checkEmpty(document.getElementById('patient_signature'),'Patient Signature') 
    					|| !checkEmpty(document.getElementById('person_taking_consent'),'Person Taking Consent') 
    					|| !checkEmpty(document.getElementById('cf_validity'),'CF Validity') 
    					|| !checkEmpty(document.getElementById('cf_checked_date'),'CF Checked Date')
    					|| !checkDateRange('NO-FUTURE-DATE',document.getElementById('cf_checked_date'),'Future Date Not Allowed')
    					|| !checkEmpty(document.getElementById('cf_checked_by'),'CF Checked By')) {
    	    		return false;
    			}
    			document.getElementById('verbal_document_checked').value = '';
    			document.getElementById('verbal_consent_checked_date').value = '';
    			document.getElementById('verbal_consent_checked_by').value = '';
        	} 
        	if (!checkEmpty(document.getElementById('verify_consent_exclusions'),'Verify Consent Exclusions')) {
        		return false;
        	} else {
            	if (document.getElementById('verify_consent_exclusions').value.toLowerCase().includes('no')) {
        			if (!checkEmpty(document.getElementById('statements_excluded'),'Consent Excluded Statements')) {
        	    		return false;
        			}
            	} else {
        			document.getElementById('statements_excluded').value = '';
            	}
        	}
    		if (!checkEmpty(document.getElementById('data_discrepancies_identified'),'Data Discrepancies Identified') 
    				|| !checkEmpty(document.getElementById('data_accuracy_date'),'Data Accuracy Date')
    				|| !checkDateRange('NO-FUTURE-DATE',document.getElementById('data_accuracy_date'),'Future Date Not Allowed')
    				|| !checkEmpty(document.getElementById('data_accuracy_checked_by'),'Data Accuracy Checked By')) {
        		return false;
    		}
        	if (document.getElementById('data_discrepancies_identified').value.toLowerCase().includes('yes')) {
    			if (!checkEmpty(document.getElementById('source_of_verified_data'),'Source Verified Data')
    					|| !checkEmpty(document.getElementById('data_discrepancies_description'),'Data Discrepancies Description')) {
    	    		return false;
    			}
        	} else {
    			document.getElementById('source_of_verified_data').value = '';
    			document.getElementById('data_discrepancies_description').value = '';
    			document.getElementById('data_discrepancies_verified').value = '';
    			document.getElementById('data_discrepancies_verification_date').value = '';
    			document.getElementById('data_discrepancies_verified_by').value = '';
        	}
    	}
    	break;
    	
    case 'validate_patient':

    	if (!checkEmpty(document.getElementById('surname'),'Surname') || !checkEmpty(document.getElementById('forename'),'Forname') 
    			|| !checkEmpty(document.getElementById('select_gender'),'Gender')) {
    		return false;
   		} 
    	else {
    		document.getElementById('gender').value = document.getElementById('select_gender').value;
    		if (!checkEmpty(document.getElementById('select_volunteer'),'Volunteer')) {
        		return false;
    		} else {
        		document.getElementById('volunteer').value = document.getElementById('select_volunteer').value;
            	if (document.getElementById('select_volunteer').value.toLowerCase().includes('no')) {
        			if (!checkEmpty(document.getElementById('date_of_birth'),'Date Of Birth') || !checkDateRange('NO-FUTURE-DATE',document.getElementById('date_of_birth'),'Future Date Not Allowed')
        					|| !checkEmpty(document.getElementById('hospital_number'),'Hospital No') || !checkTextLength(document.getElementById('hospital_number'),4,30,'Hospital No') 
        					|| !checkEmpty(document.getElementById('nhs_number'),'NHS No') || !checkTextLength(document.getElementById('nhs_number'),4,30,'NHS No')) {
        	    		return false;
        				}
        			else {
        					document.getElementById('age').value = '';
        				}
        			}
            	else if (document.getElementById('select_volunteer').value.toLowerCase().includes('yes')) {
        			document.getElementById('date_of_birth').value = '';
        			document.getElementById('hospital_number').value = '';
        			document.getElementById('nhs_number').value = '';
        			document.getElementById('age').value = document.getElementById('patient.select_age').value;
            	}
    		} 
    	}
    	
    	break;
	
    case 'validate_consent':
    	
    	if(document.getElementById('consent_body_div').style.display == '') 
    	{
        	if (!checkEmpty(document.getElementById('date_of_consent'),'Date Of Consent') 
        			|| !checkDateRange('NO-FUTURE-DATE',document.getElementById('date_of_consent'),'Future Date Not Allowed') 
        			|| !checkEmpty(document.getElementById('consent_taken_by'),'Consent Taken By')) {
        		return false;
        	}
        	if (document.getElementById('verbal_consent').value.toLowerCase().includes('yes')) {
    			if (!checkEmpty(document.getElementById('verbal_consent_recorded'),'Verbal Consent Recorded')
    					|| !checkEmpty(document.getElementById('verbal_consent_recorded_by'),'Verbal Consent Recorded By') 
    					|| !checkEmpty(document.getElementById('verbal_consent_document_file_label'),'Verbal Consent Document')) {
    	    		return false;
    			}
        	} else {
            	if (!checkEmpty(document.getElementById('form_type'),'Form Type') || !checkEmpty(document.getElementById('digital_cf_attachment_file_label'),'Digital CF')) {
        	    	return false;
       			}
            	if (document.getElementById('consent_type').value.toLowerCase().includes('partial')) {
        			if (!checkEmpty(document.getElementById("select_consent_exclusions"),'Consent Exclusions')) {
        	    		return false;
        			}
            	} else {
            		document.getElementById('consent_exclusions').value = '';
            		document.getElementById('exclusions_comment').value = '';
            	}
        		document.getElementById('verbal_consent_recorded').value = '';
        		document.getElementById('verbal_consent_recorded_by').value = '';
        		document.getElementById('verbal_consent_document_file').value = '';
        		if(document.getElementById('verbal_consent_document_file_label').innerHTML != '') {
        			processPatientConsentInfectionRisk('REMOVE-FILE',document.getElementById('verbal_consent_document_file_button'),false);
        		} 
        	}
        	if (document.getElementById('withdrawn').value.toLowerCase().includes('yes')) {
    			if (!checkEmpty(document.getElementById('withdrawal_date'),'Withdrawal Date') || !checkDateRange('NO-FUTURE-DATE',document.getElementById('withdrawal_date'),'Future Date Not Allowed')
    					|| !checkEmpty(document.getElementById('withdrawal_document_file_label'),'Withdrawal Document')) {
    	    		return false;
    			}
        	} else {
        		document.getElementById('withdrawal_date').value = '';
        		document.getElementById('withdrawal_document_file').value = '';
        		if(document.getElementById('withdrawal_document_file_label').innerHTML != '') {
            		processPatientConsentInfectionRisk('REMOVE-FILE',document.getElementById('withdrawal_document_file'),false);
        		}
        	}
        	if (document.getElementById('stop_sample_donation').value.toLowerCase().includes('yes')) {
    			if (!checkEmpty(document.getElementById('stop_sample_donation_date'),'Stop Sample Donation Date') 
    					|| !checkDateRange('NO-FUTURE-DATE',document.getElementById('stop_sample_donation_date'),'Future Date Not Allowed')) {
    	    		return false;
    			}
        	} else {
        		document.getElementById('stop_sample_donation_date').value = '';
        	}
        	if(document.getElementById("whichDepartment").value.toUpperCase().includes('HOTB')) {
    			if (!checkEmpty(document.getElementById("select_samples_consented_to"),'Consented Samples')) {
    	    		return false;
    			}
        	}
    	}
    	break;
    	
    default:
    	
        switch(whichButtonId.id) {
        case 'search_patient_btn':
        	
        	if (!checkEmpty(document.getElementById('search_patient_keyword'),'Search Keyword')){
        		return false;
        	}
        	if (document.getElementById('select_search_criteria').value.toLowerCase().includes('date')) {
        		formatInputBoxValue('DATE',document.getElementById('search_patient_keyword'));
    	  	}
    		$('.my_waiting_modal').modal('show');
        	document.search_patient_form.submit();
        	
        	break;
        	
        case 'save_user_btn':
        	
        	if (!checkEmpty(document.getElementById('title'),'Title') || !checkEmpty(document.getElementById('firstname'),'Firstname') 
        			|| !checkEmpty(document.getElementById('surname'),'Surname')) {
    	    		return false;
    	    } else {
    		  	    document.getElementById('selected_role_id').value = document.getElementById('current_role_id').value;
    	    		$('.my_waiting_modal').modal('show');
    		  	    document.user_profile_form.submit();
    	  	}
        	
        	break;
        
        case 'add_infection_risk_btn':

        	if (validateFormFields('validate_patient',null,false) == false) {
        		return false;
        	}

        	/*----------Infection Risk----------*/
        	
        	if(document.getElementById('infection_risk_body_div').style.display == '') 
        	{
            	if (!checkEmpty(document.getElementById('infection_risk_exist'),'Infection Risk Exist')) {
    	    		return false;
            	} else {
                	if (document.getElementById('infection_risk_exist').value.toLowerCase().includes('yes')
                			|| document.getElementById('infection_risk_exist').value.toLowerCase().includes('unknown')) {
                		
                		if (!checkEmpty(document.getElementById('select_infection_type_id'),'Infection Type')) {
            	    		return false;
                		} else {
                    		if (document.getElementById('select_infection_type_id').options[
                    			document.getElementById('select_infection_type_id').selectedIndex].text.toLowerCase().includes('other')) {
                				if (!checkEmpty(document.getElementById('other_infection_risk'),'Other Infection Risk')) {
                		    		return false;
                				}
                			}
                    		else if (document.getElementById('select_infection_type_id').options[
                					 document.getElementById('select_infection_type_id').selectedIndex].text.toLowerCase().includes('ongoing')) {
                				if (!checkEmpty(document.getElementById('select_ongoing_infection_type_id'),'OnGoing Prev IR')) {
                		    		return false;
                				}
                			}
                		}
                		
            			if (!checkEmpty(document.getElementById('episode_start_date'),'Start Date') 
            					|| !checkDateRange('NO-FUTURE-DATE',document.getElementById('episode_start_date'),'Future Date Not Allowed')) {
            	    		return false;
            			}
            			if (document.getElementById('select_continued_risk').value.toLowerCase().includes('no')) {
            				if (!checkEmpty(document.getElementById('episode_finished_date'),'Finished Date')
            						|| !checkDateRange('NO-FUTURE-DATE',document.getElementById('episode_finished_date'),'Future Date Not Allowed')) {
            		    		return false;
            					}
            				document.getElementById('continued_risk').value = 'No';
            			}
            			else {
            				document.getElementById('continued_risk').value = 'Yes';
            	    	}
            			if (!checkEmpty(document.getElementById('select_sample_collection'),'Sample Collection')) {
            	    		return false;
            			}
            			
                		document.getElementById('infection_type_id').value = document.getElementById('select_infection_type_id').value;
                		document.getElementById('ongoing_infection_type_id').value = document.getElementById('select_ongoing_infection_type_id').value;
                		document.getElementById('episode_of_infection').value = document.getElementById('select_episode_of_infection').value;
                		document.getElementById('sample_collection').value = document.getElementById('select_sample_collection').value;
                		document.getElementById('sample_type_id').value = document.getElementById('select_sample_type_id').value;
               		}
                	else {
                		document.getElementById('infection_type_id').value = '';
                		document.getElementById('ongoing_infection_type_id').value = '';
                		document.getElementById('other_infection_risk').value = '';
                		document.getElementById('comments').value = '';
                		document.getElementById('episode_of_infection').value = '';
            			document.getElementById('continued_risk').value = '';
            			document.getElementById('episode_start_date').value = '';
            			document.getElementById('episode_finished_date').value = '';
            			document.getElementById('sample_collection').value = '';
            			document.getElementById('sample_type_id').value = '';
            			document.getElementById('sample_collection_date').value = '';
                	}
            	}
            	
        		document.getElementById('form_version_id').value = document.getElementById('select_form_version_id').value;
        		if (submitFormNow == true) {
                	document.patient_consent_ir_form.action = 'add_another_infection_risk';
    	    		$('.my_waiting_modal').modal('show');
                	document.patient_consent_ir_form.submit();
            	}
        		return true;
        	}
        	break;
        	
        case 'save_consent_btn': 
        	
        	if (validateFormFields('validate_consent',null,false) == false) {
       			return false;
        	}
        	if (validateFormFields('',document.getElementById('add_infection_risk_btn'),false) == false) {
        		return false;
        	}
        	
           	document.patient_consent_ir_form.action = 'save_patient_consent_ir';
    		$('.my_waiting_modal').modal('show');
           	document.patient_consent_ir_form.submit();
        	
        	break;
    		
        case 'save_changes_pat_cons_irs_btn':

    		if (document.getElementById('consent_id').value > 0) {
            	if (validateFormFields('validate_consent',null,false) == false) {
            		return false;
            	}
    		}
        	if (validateFormFields('',document.getElementById('add_infection_risk_btn'),false) == false) {
        		return false;
        	}
        	if (validateFormFields('validate_all',null,false) == false) {
        		return false;
        	}
        	if (document.getElementById('user_action_form_div').style.display == '') {
        		if (document.getElementById('action_type').value.toLowerCase().includes('no_action_to_send')) { 
            		if(window.location.pathname.includes('consent_withdrawn')) {
                		document.getElementById('action_type').value = 'confirm_notification';
            		} else if(window.location.pathname.includes('remove_samples')){
                		document.getElementById('action_type').value = 'confirm_samples_disposal';
            		} else if(window.location.pathname.includes('verify_consent')){
            			if (confirm('Are you sure you want to validate this consent?') == false) {
                			return false;
                		}
                		document.getElementById('action_type').value = 'validate';
            		}
        		}
        	}
    		
	  	    processUserSelection(document.getElementById('edit_consent_btn')); // Enable everything first or else patient model attribute will send null values
   	  	    document.patient_consent_ir_form.action = 'save_changes_patient_consents_irs';
    		$('.my_waiting_modal').modal('show');
          	document.patient_consent_ir_form.submit();
        	
        	break;

        }
	}
}
function checkDateRange(whatToProcess,inputBox,textToShow)
{	
	switch (whatToProcess.toUpperCase()) {
	case 'NO-FUTURE-DATE':

		var dateString = $(inputBox).val();
		var dateParts = dateString.split("-");
		var myDate = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 
		var name = $(inputBox).attr("id");
		
		document.getElementById(name+"-validation").innerHTML = '';
		document.getElementById(name+"-validation").style.display = 'none';
		$(inputBox).css("border","");

		if(myDate > new Date()){
			$(inputBox).css("border","#E11E26 2px solid");
			document.getElementById(name+"-validation").innerHTML = textToShow;
			document.getElementById(name+"-validation").style.display = '';
			document.getElementById(name).focus({preventScroll:false});
			return false;
		}
		return true;	
		
		break;
	}
}
		
function checkEmpty(inputBox,textToShow) {

	var whatToCheck = '';
	var name = $(inputBox).attr("id");
	if (document.getElementById(name).type.toLowerCase().includes('select-multiple')) {
    	for(i=0; i < inputBox.options.length;i++){
    		if (inputBox.options[i].selected == true) {
    			whatToCheck = inputBox.options[i].value; // Populate any of the selected options, just so we know that it's not left empty.
    		}
    	}		
	} else {
		whatToCheck = document.getElementById(name).value;
	}
	if (name.toLowerCase().includes('label')) {
		whatToCheck = document.getElementById(name).innerHTML;
	} 
	
	document.getElementById(name+"-validation").innerHTML = '';
	document.getElementById(name+"-validation").style.display = 'none';
	$(inputBox).css("border","");

	if(whatToCheck == "") {
		$(inputBox).css("border","#E11E26 2px solid");
		document.getElementById(name+"-validation").innerHTML = textToShow + " required";
		document.getElementById(name+"-validation").style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}
function checkTextLength(inputBox,minimumLength,maximumLength,textToShow) {

	var name = $(inputBox).attr("id");

	var whatToCheck = document.getElementById(name).value;

	document.getElementById(name+"-validation").innerHTML = '';
	document.getElementById(name+"-validation").style.display = 'none';
	$(inputBox).css("border","");

	if(whatToCheck.length < minimumLength || whatToCheck.length > maximumLength) {
		$(inputBox).css("border","#E11E26 2px solid");
		document.getElementById(name+"-validation").innerHTML = "'" + textToShow + "' text length must be between " + minimumLength + " to " + maximumLength + " characters";
		document.getElementById(name+"-validation").style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}
function doFileValidation(inputBox) {

	var name = $(inputBox).attr("id");
	var fileTypes = ['/jpg','/jpeg','/png','/pdf'];
	var fileTypeFound = false;
	
	document.getElementById(name+"_label-validation").innerHTML = '';
	document.getElementById(name+"_label-validation").style.display = 'none';
	$(inputBox).css("border","");

    for(var i=0; i < fileTypes.length; i++){
        if (document.getElementById(name).files[0].type.toLowerCase().indexOf(fileTypes[i].toLowerCase()) != -1){
        	fileTypeFound = true;
        }
    }	
	if(!fileTypeFound) {
		$(inputBox).css("border","#E11E26 2px solid");
		document.getElementById(name+"_label-validation").innerHTML = 'Invalid file! Only [JPG, JPEG, PNG & PDF] supported';
		document.getElementById(name+"_label-validation").style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}
function processUserPrimaryRoleSelection(this_object)
{
  var selectedD;
  $("#current_role_id").on("changed.bs.select", function(e, clickedIndex, newValue) {
	  selectedD = $(this).find('option').eq(clickedIndex).text();
	  $("#current_role_id > option").each(function() {
		  if (selectedD == this.text){this.selected = true;}
		  else {this.selected = false;}
		});
	  $('#current_role_id').selectpicker('refresh');
  });	
}
function showDepartmentDetails(whichImage,whatToDo)
{
  switch (whatToDo) {
  case 'SHOW':
	  document.getElementById('dept_details_title').innerHTML = $(whichImage).data('popover-title');
	  document.getElementById('dept_details_body').innerHTML = $(whichImage).data('popover-content');
	  document.getElementById('dept_details_alert').style.display = "";
	  break;
  case 'HIDE':
	  document.getElementById('dept_details_alert').style.display = "none";
	  break;
  }
}
function uploadFormDataToSessionObjects(whichInput)
{
	var idOfInputbox = $(whichInput).attr("id");
	var formData = new FormData();
	
	switch($(whichInput).attr("type")) {
	case 'file':

		document.getElementById(idOfInputbox + '_label').innerHTML = '';
		hideAndShowContainer(document.getElementById(idOfInputbox + '_label'));
		
		if(doFileValidation(document.getElementById(idOfInputbox))) {
			
			formData.append(idOfInputbox,whichInput.files[0]);
			formData.append(document.getElementById('consent_id').id,document.getElementById('consent_id').value);  
			
			var whatUrlToSend = 'uploadFileToSessionPatConIr'; // adding file to a single consent stored in session - one at a time
			if (window.location.pathname.includes('edit_patient') || window.location.pathname.includes('verify_consent')
					|| window.location.pathname.includes('reapproach_patient') || window.location.pathname.includes('query_patient')
					|| window.location.pathname.includes('remove_samples') || window.location.pathname.includes('consent_withdrawn')
					|| window.location.pathname.includes('infection_risk_unknown') || window.location.pathname.includes('infection_risk_found')) {
				whatUrlToSend = 'uploadFileToConsentsArray'; // updating or adding file to one of the multiple consents using consent id
			}
			
			$.ajax({    
				headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
		        url : whatUrlToSend,     
		        data : formData,
		        cache: false,
		        contentType: false,
		        processData: false,
		        type: "POST",     
		        success : function(response) {
		            document.getElementById(idOfInputbox + '_label').href = URL.createObjectURL(new Blob([whichInput.files[0]], {type: whichInput.files[0].type}));
		        	document.getElementById(idOfInputbox + '_label').innerHTML = whichInput.files[0].name;
		        	hideAndShowContainer(document.getElementById(idOfInputbox + '_label'));
		        },    
		        error : function(e) {    
		       	 	console.log('Error occured in uploadFormDataToSessionObjects with error description = ' + e);     
		        }    
		    });
		}

		break;

	default:
		
		if (window.location.pathname.includes('edit_patient') || window.location.pathname.includes('verify_consent')
				|| window.location.pathname.includes('reapproach_patient') || window.location.pathname.includes('query_patient')
				|| window.location.pathname.includes('consent_withdrawn') || window.location.pathname.includes('remove_samples')
				|| window.location.pathname.includes('infection_risk_unknown') || window.location.pathname.includes('infection_risk_found')) {
			
		    var selectedCheckBoxValues = [];
			var db_table_name = $(whichInput).attr("name").slice(0, $(whichInput).attr("name").indexOf("."));

			switch (db_table_name.toLowerCase()) {
			case 'consent_validate':
			    if(!document.getElementById('cv_consent_id').value > 0) {
					return false;
			    }
				formData.append(document.getElementById('cv_consent_id').id,document.getElementById('cv_consent_id').value);  
				$("input[name^='consent_validate']").each(function(){
					formData.append($(this).attr('name'),document.getElementById($(this).attr('id')).value);  
				});				
				$("select[name^='consent_validate']").each(function(){
					formData.append($(this).attr('name'),document.getElementById($(this).attr('id')).value);  
				});				
				$("textarea[name^='consent_validate']").each(function(){
					formData.append($(this).attr('name'),document.getElementById($(this).attr('id')).value);  
				});				
				break;
			default:
			    if(!document.getElementById(db_table_name + '_id').value > 0) {
					return false;
			    }
				formData.append(document.getElementById(db_table_name + '_id').id,document.getElementById(db_table_name + '_id').value);
				switch (whichInput.type) {
				case 'select-multiple':
					var select_picker = document.getElementById($(whichInput).attr("id"));
			    	for(i=0; i < select_picker.options.length;i++){
			    		if (select_picker.options[i].selected == true) {
			    			selectedCheckBoxValues.push([select_picker.options[i].value]);
			    		}
			    	}		
					formData.append($(whichInput).attr("name").replace('select_',''),selectedCheckBoxValues); // All non-binded elements are prefix with 'select_'. So remove the prefix before uploading data.
					break;
				default:
					formData.append($(whichInput).attr("name").replace('select_',''),whichInput.value);  // All non-binded elements are prefix with 'select_'. So remove the prefix before uploading data.
					break;
				}
				break;
			}
		
			$.ajax({    
				headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
		        url : "uploadFormData",     
		        data : formData,
		        cache: false,
		        contentType: false,
		        processData: false,
		        type: "POST",     
		        success : function(response) {
		        },    
		        error : function(e) {    
		       	 	console.log('Error occured in uploadFormDataToSessionObjects with error description = ' + e);     
		        }    
		    });
		}
		break;
	}
}
function loadAndUnloadWaitingModal(whatToDo)
{
	switch (whatToDo) {
	case 'WAIT':
		$("#index_body *").prop('disabled',true);
		document.getElementById('loading_container').style.display = "";
		break;
	default:
		$("#index_body *").prop('disabled',false);
		document.getElementById('loading_container').style.display = "none";
		break;
	}
}
jQuery(function ($) {

  $('[data-toggle="popover"]').popover();
	
  $('.date').datetimepicker({
        format: 'd-m-yyyy',
        weekStart: 1,
        todayBtn:  true,
        autoclose: true,
        todayHighlight: true,
        startView: 2,
        minView: 2,
        forceParse: 0
   });	
	
  $(".sidebar-dropdown > a").click(function() {
  $(".sidebar-submenu").slideUp(200);
  if (
    $(this)
      .parent()
      .hasClass("active")
  ) {
    $(".sidebar-dropdown").removeClass("active");
    $(this)
      .parent()
      .removeClass("active");
  } else {
    $(".sidebar-dropdown").removeClass("active");
    $(this)
      .next(".sidebar-submenu")
      .slideDown(200);
    $(this)
      .parent()
      .addClass("active");
  }

  });

  $("#close-sidebar").click(function() {
	  $(".page-wrapper").removeClass("toggled");
	});
	$("#show-sidebar").click(function() {
	  $(".page-wrapper").addClass("toggled");
	});

	$('#patient_search_result').DataTable({
	 "info": false,
	 "searching": true,
	 "paging": false,
	 "language": {
	     search: "_INPUT_",
	     searchPlaceholder: "Filter..."
	  },
	});

});
