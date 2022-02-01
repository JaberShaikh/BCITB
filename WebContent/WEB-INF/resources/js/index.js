function changePlusMinusIcon(whichHref) 
{	
	if($(whichHref).find('i').hasClass('fa-plus')) {
		$(whichHref).find('i').removeClass('fa-plus').addClass('fa-minus');
	} else if($(whichHref).find('i').hasClass('fa-minus')) {
		$(whichHref).find('i').removeClass('fa-minus').addClass('fa-plus');
	}
}
function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START-VIEW-AUDIT-CONSENT': 
		$('.spinner-border').show();
		$('#audit_consent_btn').prop('disabled',true);
		break;
	case 'START-VIEW-IMPORT-CONSENT': 
		$('.spinner-border').show();
		$('#validate_imported_consent_btn').prop('disabled',true);
		break;
	case 'START-SEARCH-PATIENT': 
		$('.spinner-border').show();
		$('#basic_search_patient_btn').prop('disabled',true);
		break;
	case 'START-CONSENT-AUDIT':
		$('.spinner-border').show();
		$('#add_save_discard_sample_div *').prop('disabled',true);
		$('#audit_save_finalise_btn_div *').prop('disabled',true);
		break;
	case 'START-CONSENT-VALIDATE':
		$('.spinner-border').show();
		$('#save_consent_validate_btn').prop('disabled',true);
		break;
	case 'START-LOCKED-ACTION':
		$('.spinner-border').show();
		$('#unlock_action_btn').prop('disabled',true);
		break;
	case 'START-USER-ACTION': 
		$('.spinner-border').show();
		$('#view_action_btn').prop('disabled',true);
		break;
	case 'START-INFECTION-RISK': 
		$('.spinner-border').show();
		$('#save_infection_risk_btn_div *').prop('disabled',true);
		break;
	case 'START-PATIENT':
		$('.spinner-border').show();
		$('#save_patient_btn_div *').prop('disabled',true);
		break;
	case 'START-CONSENT':
		$('.spinner-border').show();
		$('#save_consent_btn_div *').prop('disabled',true);
		break;
	case 'END-VIEW-AUDIT-CONSENT': 
		$('.spinner-border').hide();
		$('#audit_consent_btn').prop('disabled',false);
		break;
	case 'END-VIEW-IMPORT-CONSENT': 
		$('.spinner-border').hide();
		$('#validate_imported_consent_btn').prop('disabled',false);
		break;
	case 'END-SEARCH-PATIENT': 
		$('.spinner-border').hide();
		$('#basic_search_patient_btn').prop('disabled',false);
		break;
	case 'END-CONSENT-AUDIT':
		$('.spinner-border').hide();
		$('#add_save_discard_sample_div *').prop('disabled',false);
		$('#audit_save_finalise_btn_div *').prop('disabled',false);
		break;
	case 'END-CONSENT-VALIDATE':
		$('.spinner-border').hide();
		$('#save_consent_validate_btn').prop('disabled',false);
		break;
	case 'END-LOCKED-ACTION':
		$('.spinner-border').hide();
		$('#unlock_action_btn').prop('disabled',false);
		break;
	case 'END-USER-ACTION': 
		$('.spinner-border').hide();
		$('#view_action_btn').prop('disabled',false);
		break;
	case 'END-INFECTION-RISK':
		$('.spinner-border').hide();
		$('#save_infection_risk_btn_div *').prop('disabled',false);
		break;
	case 'END-PATIENT':
		$('.spinner-border').hide();
		$('#save_patient_btn_div *').prop('disabled',false);
		break;
	case 'END-CONSENT':
		$('.spinner-border').hide();
		$('#save_consent_btn_div *').prop('disabled',false);
		break;
	}
}
function reloadData()
{
	if(window.location.pathname.includes('save_patient_consent_ir') || window.location.pathname.includes('select_department_locations')
			|| window.location.pathname.includes('update_infection_risk') || window.location.pathname.includes('update_consent')
			|| window.location.pathname.includes('save_user') || window.location.pathname.includes('user_actions')
			|| window.location.pathname.includes('save_action')) {
		processPatientConsentInfectionRisk('GET-ACTIVE-ACTIONS-FROM-SESSION',null,false);
		if(window.location.pathname.includes('user_actions') || window.location.pathname.includes('save_action')) {
			processPatientConsentInfectionRisk('GET-COMPLETED-ACTIONS-FROM-SESSION',null,false);
			processPatientConsentInfectionRisk('GET-LOCKED-ACTIONS-FROM-SESSION',null,false);
			if(document.getElementById('audit_consents_div')) {
				processPatientConsentInfectionRisk('GET-AUDIT-CONSENTS-FROM-SESSION',null,false);
			}
			if(document.getElementById('imported_consents_div')) {
				processPatientConsentInfectionRisk('GET-IMPORT-CONSENTS-FROM-SESSION',null,false);
			}
		}
	} else if(window.location.pathname.includes('verify_consent') || window.location.pathname.includes('reapproach_patient')
			|| window.location.pathname.includes('query_patient') || window.location.pathname.includes('consent_withdrawn')
			|| window.location.pathname.includes('remove_samples') || window.location.pathname.includes('infection_risk_found_notify_om') 
			|| window.location.pathname.includes('infection_risk_found_notify_tech') || window.location.pathname.includes('infection_risk_unknown')
			|| window.location.pathname.includes('infection_risk_unknown_notify_tech') || window.location.pathname.includes('verify_imported_consent') 
			|| window.location.pathname.includes('audit_consent')) {
	} else {
		processPatientConsentInfectionRisk('UNLOCK-USER-ACTION',null,false);
	}
	
	if(window.location.pathname.includes('/audit_consent')) {

		processPatientConsentInfectionRisk('PATIENT',document.getElementById('patient_id'),false);
   		document.getElementById('patient_sub_menu').className = 'panel-collapse collapse show'; // By default show patient
   		$('#patient_sub_menu_icon').removeClass('fa-plus').addClass('fa-minus');
   		updateEventsOnContainer('UPLOAD-PATIENT-DATA-ONLY');
   		
		switch (document.getElementById('user_department').value.toUpperCase()) {
		case 'HOTB':
	   		document.getElementById('select_samples_consented_to_div').style.display = '';
	   		break;
		default:
	   		document.getElementById('select_samples_consented_to_div').style.display = 'none';
			break;
		}
   			
   		processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-DB-SESSION',document.getElementById('patient_id'),false);
   		document.getElementById('infection_risk_sub_menu').className = 'panel-collapse collapse show'; // By default show IR
   		$('#ir_sub_menu_icon').removeClass('fa-plus').addClass('fa-minus');
   		document.getElementById('infection_risk_body_div').style.display = 'none';
   		
   		processPatientConsentInfectionRisk('GET-SINGLE-COMPLETE-CONSENT-FROM-SESSION',null,false);

   		document.getElementById('consent_audit_div').style.display = '';
   		hideAndShowContainer(document.getElementById('verify_consent_exclusions'));
   		hideAndShowContainer(document.getElementById('data_discrepancies_identified'));
   		hideAndShowContainer(document.getElementById('samples_obtained_electronically'));
   		
	} else if(window.location.pathname.includes('/previous_advanced_search_result_page') 
			|| window.location.pathname.includes('/previous_basic_search_result_page')) {

		processWaitingButtonSpinner('START-SEARCH-PATIENT');
		if(window.location.pathname.includes('/previous_advanced_search_result_page')) {
			processPatientConsentInfectionRisk('PREVIOUS-ADVANCED-SEARCH',document.getElementById('advanced_search_sql_script'),false);
		} else if(window.location.pathname.includes('/previous_basic_search_result_page')) {
			processPatientConsentInfectionRisk('PREVIOUS-BASIC-SEARCH',null,false);
			if(document.getElementById('basic_search_options').value 
					&& document.getElementById('basic_search_options').value.split('|').length >= 2) {
		   		document.getElementById('search_patient_keyword').value = document.getElementById('basic_search_options').value.split('|')[0];
		   		document.getElementById('select_search_criteria').value = document.getElementById('basic_search_options').value.split('|')[1];
		   		hideAndShowContainer(document.getElementById('select_search_criteria'));
			}
		}
   		
	} else if(window.location.pathname.includes('verify_imported_consent')) {

		processPatientConsentInfectionRisk('PATIENT',document.getElementById('patient_id'),false);
   		document.getElementById('patient_sub_menu').className = 'panel-collapse collapse show'; // By default show patient
   		$('#patient_sub_menu_icon').removeClass('fa-plus').addClass('fa-minus');
   		document.getElementById('save_patient_btn').style.display = '';
   		document.getElementById('save_patient_btn').onclick = function() {validateFormFields('validate_save_session_patient',null,null,false)};
   		updateEventsOnContainer('UPLOAD-PATIENT-DATA-ONLY');
   		
		switch (document.getElementById('user_department').value.toUpperCase()) {
		case 'HOTB':
	   		document.getElementById('select_samples_consented_to_div').style.display = '';
	   		break;
		default:
	   		document.getElementById('select_samples_consented_to_div').style.display = 'none';
			break;
		}
   			
   		processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-DB-SESSION',document.getElementById('patient_id'),false);
   		document.getElementById('infection_risk_sub_menu').className = 'panel-collapse collapse show'; // By default show IR
   		$('#ir_sub_menu_icon').removeClass('fa-plus').addClass('fa-minus');
   		document.getElementById('save_infection_risk_btn').style.display = '';
   		document.getElementById('save_infection_risk_btn').onclick = function() {validateFormFields('validate_save_session_infection_risk',null,null,false)};
   		document.getElementById('infection_risk_body_div').style.display = 'none';
   		
   		processPatientConsentInfectionRisk('GET-SINGLE-COMPLETE-CONSENT-FROM-SESSION',null,false);

		document.getElementById('save_consent_btn').style.display = '';
   		document.getElementById('save_consent_btn').onclick = function() {validateFormFields('validate_save_session_consent',null,null,false)};
   		
   		document.getElementById('user_actions_div').style.display = '';
		addItemsToList('ACTION-TYPE',document.getElementById('action_type'),null,null);
   		hideAndShowContainer(document.getElementById('action_type'));
		
	}  else if (window.location.pathname.includes('remove_samples')) {

		processPatientConsentInfectionRisk('PATIENT',document.getElementById('patient_id'),false);
   		updateEventsOnContainer('UPLOAD-PATIENT-DATA-ONLY');
		$('#patient_body_div *').prop('disabled',true);

		processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-DB-SESSION',document.getElementById('patient_id'),false);
   		$('#infection_risk_body_div *').prop('disabled',true);
   		document.getElementById('save_infection_risk_btn_div').style.display = 'none';
   		document.getElementById('infection_risk_buttons_message').style.display = '';
   		document.getElementById('infection_risk_buttons_message').innerHTML = 'You are NOT authorised to add or edit an infection risk';

		switch (document.getElementById('user_department').value.toUpperCase()) {
		case 'HOTB':
	   		document.getElementById('select_samples_consented_to_div').style.display = '';
	   		break;
		default:
	   		document.getElementById('select_samples_consented_to_div').style.display = 'none';
			break;
		}
   		
   		processPatientConsentInfectionRisk('GET-SINGLE-COMPLETE-CONSENT-FROM-SESSION',null,false);
   		$('#consent_body_div *').prop('disabled',true);
   		$('#consent_notes').prop('disabled',false);
   		$('#consent_exclusion_btn').attr('disabled',false);
	    if(document.getElementById('select_samples_consented_to_div').style.display == '') {
	   		$('#samples_consented_to_btn').attr('disabled',false);
	    }
   		
		document.getElementById('save_consent_btn').style.display = '';
   		document.getElementById('save_consent_btn').disabled = false;
   		document.getElementById('save_consent_btn').onclick = function() {
   			validateFormFields('validate_save_session_consent',null,null,true)};
   		
		document.getElementById('confirm_notification_consent_btn').style.display = '';
   		document.getElementById('confirm_notification_consent_btn').disabled = false;
   	   	document.getElementById('confirm_notification_consent_btn').onclick = function() {processUserSelection(document.getElementById('confirm_notification_consent_btn'))};
		document.getElementById('confirm_notification_consent_btn').innerHTML = '<i class="fas fa-check-circle"></i> Confirm Samples Disposal';
		document.getElementById('confirm_notification_consent_btn').value = 'confirm_samples_disposal';
		processPatientConsentInfectionRisk('SAVE-USER-SELECTED-ACTION-TYPE',document.getElementById('confirm_notification_consent_btn'),false);
   		
	} else if(window.location.pathname.includes('verify_consent') || window.location.pathname.includes('reapproach_patient')
			|| window.location.pathname.includes('query_patient') || window.location.pathname.includes('consent_withdrawn')) {

		processPatientConsentInfectionRisk('PATIENT',document.getElementById('patient_id'),false);
		$('#patient_body_div *').prop('disabled',true);
   		updateEventsOnContainer('UPLOAD-PATIENT-DATA-ONLY');
   		document.getElementById('edit_patient_btn').style.display = '';
   		document.getElementById('edit_patient_btn').disabled = false;
   		document.getElementById('save_patient_btn').onclick = function() {
   			validateFormFields('validate_save_session_patient',null,null,true);processUserSelection(document.getElementById('save_patient_btn'));};

		switch (document.getElementById('user_department').value.toUpperCase()) {
		case 'HOTB':
	   		document.getElementById('select_samples_consented_to_div').style.display = '';
	   		break;
		default:
	   		document.getElementById('select_samples_consented_to_div').style.display = 'none';
			break;
		}
   			
   		processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-DB-SESSION',document.getElementById('patient_id'),false);
   		$('#infection_risk_body_div *').prop('disabled',true);
   		document.getElementById('edit_infection_risk_btn').style.display = '';
   		document.getElementById('edit_infection_risk_btn').disabled = false;
   		document.getElementById('save_infection_risk_btn').onclick = function() {
   			validateFormFields('validate_save_session_infection_risk',null,null,true);processUserSelection(document.getElementById('save_infection_risk_btn'));};
   		document.getElementById('infection_risk_body_div').style.display = 'none';
   		
   		processPatientConsentInfectionRisk('GET-SINGLE-COMPLETE-CONSENT-FROM-SESSION',null,false);
   		$('#consent_body_div *').prop('disabled',true);
   		$('#consent_exclusion_btn').attr('disabled',false);
	    if(document.getElementById('select_samples_consented_to_div').style.display == '') {
	   		$('#samples_consented_to_btn').attr('disabled',false);
	    }
   		if(window.location.pathname.includes('consent_withdrawn')) {
   	   		
   			document.getElementById('edit_consent_btn').style.display = '';
   	   		document.getElementById('edit_consent_btn').disabled = false;
   			document.getElementById('save_consent_btn').style.display = 'none';
   	   		document.getElementById('save_consent_btn').onclick = function() {
   	   			validateFormFields('validate_save_session_consent',null,null,true);processUserSelection(document.getElementById('save_consent_btn'));};
   	   		
   	 		document.getElementById('confirm_notification_consent_btn').style.display = '';
   	   		document.getElementById('confirm_notification_consent_btn').disabled = false;
   	   	   	document.getElementById('confirm_notification_consent_btn').onclick = function() {processUserSelection(document.getElementById('confirm_notification_consent_btn'))};
   			document.getElementById('confirm_notification_consent_btn').innerHTML = '<i class="fas fa-check-circle"></i> Confirm Notification';
   			document.getElementById('confirm_notification_consent_btn').value = 'confirm_notification';
   			processPatientConsentInfectionRisk('SAVE-USER-SELECTED-ACTION-TYPE',document.getElementById('confirm_notification_consent_btn'),false);
   	   			
  			document.getElementById('user_actions_div').style.display = 'none';

   		} else {
   			
			document.getElementById('edit_consent_btn').style.display = '';
   	   		document.getElementById('edit_consent_btn').disabled = false;
   	   		document.getElementById('save_consent_btn').onclick = function() {
   	   			validateFormFields('validate_save_session_consent',null,null,false);processUserSelection(document.getElementById('save_consent_btn'));};
   	   		
   	   		document.getElementById('user_actions_div').style.display = '';
			addItemsToList('ACTION-TYPE',document.getElementById('action_type'),null,null);
   	   		hideAndShowContainer(document.getElementById('action_type'));
   		
   		}
   		
	} else if(window.location.pathname.includes('edit_consent') || window.location.pathname.includes('view_consent') || window.location.pathname.includes('view_searched_consent')) {
		
		processPatientConsentInfectionRisk('GET-SINGLE-COMPLETE-CONSENT-FROM-SESSION',null,false);
		
		switch (document.getElementById('user_department').value.toUpperCase()) {
		case 'HOTB':
	   		document.getElementById('select_samples_consented_to_div').style.display = '';
	   		break;
		default:
	   		document.getElementById('select_samples_consented_to_div').style.display = 'none';
			break;
		}
		
		if(window.location.pathname.includes('view_consent') && document.getElementById('edit_consent_btn') || window.location.pathname.includes('view_searched_consent')) {

			if (document.getElementById('consent_access').value.toLowerCase().includes('edit')) {
				document.getElementById('edit_consent_btn').style.display = '';
		   		document.getElementById('edit_consent_btn').disabled = false;
			} else {
				document.getElementById('edit_consent_btn').style.display = 'none';
			}
			
			if(window.location.pathname.includes('view_searched_consent')) {
		   		document.getElementById('edit_patient_btn').onclick = function() {
		   			validateFormFields('validate_consent',null,'view_searched_patient?patient_id=' + document.getElementById('cn_patient_id').value,false)};
			} else {
		   		document.getElementById('edit_patient_btn').onclick = function() {
		   			validateFormFields('validate_consent',null,'view_patient?patient_id=' + document.getElementById('cn_patient_id').value,false)};
			}
	   		
			if(window.location.pathname.includes('view_searched_consent')) {
				if(document.getElementById('advanced_search_sql_script').value) {
					addItemsToList('POPULATE-BACK-TO-ADVANCED-SEARCH-PAGE',null,null,null);
				} else if(document.getElementById('basic_search_options').value) {
					addItemsToList('POPULATE-BACK-TO-BASIC-SEARCH-PAGE',null,null,null);
				}
			}
	   			
		} else if(window.location.pathname.includes('edit_consent') && document.getElementById('clear_consent_data_btn')) {
			
	   		document.getElementById('clear_consent_data_btn').style.display = '';
	   		document.getElementById('add_infection_risk_btn').style.display = '';
	   		
		} 
   		
		if(document.getElementById('edit_patient_btn')) {
			document.getElementById('edit_patient_btn').disabled = false;
	   		document.getElementById('edit_patient_btn').style.display = '';
	   		document.getElementById('consent_cancel_href').style.display = '';
	   		document.getElementById('cancel_consent_btn').disabled = false;
		}
   		
	} else if(window.location.pathname.includes('add_patient') || window.location.pathname.includes('edit_patient') 
			|| window.location.pathname.includes('view_patient') || window.location.pathname.includes('save_patient_consent_ir')
			|| window.location.pathname.includes('update_patient') || window.location.pathname.includes('update_consent')
			|| window.location.pathname.includes('update_infection_risk_confirm_tech_notification')  
			|| window.location.pathname.includes('update_infection_risk_confirm_om_notification')
			|| window.location.pathname.includes('update_infection_risk') || window.location.pathname.includes('view_searched_patient')
			|| window.location.pathname.includes('view_timeline') || window.location.pathname.includes('finalise_imported_consent')
			|| window.location.pathname.includes('finalise_audit_consent') || window.location.pathname.includes('/search')) {
		
		if(window.location.pathname.includes('view_patient') || window.location.pathname.includes('save_patient_consent_ir')
				|| window.location.pathname.includes('update_patient') || window.location.pathname.includes('update_consent')
				|| window.location.pathname.includes('update_infection_risk_confirm_tech_notification') 
				|| window.location.pathname.includes('update_infection_risk_confirm_om_notification')
				|| window.location.pathname.includes('update_infection_risk') || window.location.pathname.includes('view_searched_patient')
				|| window.location.pathname.includes('view_timeline') || window.location.pathname.includes('finalise_imported_consent')
				|| window.location.pathname.includes('finalise_audit_consent') || window.location.pathname.includes('/search')) {
			
			if(document.getElementById('patient_body_div')){
				if(window.location.pathname.includes('view_searched_patient') || window.location.pathname.includes('view_timeline')) {
					if(document.getElementById('advanced_search_sql_script').value) {
						addItemsToList('POPULATE-BACK-TO-ADVANCED-SEARCH-PAGE',null,null,null);
					} else if(document.getElementById('basic_search_options').value) {
						addItemsToList('POPULATE-BACK-TO-BASIC-SEARCH-PAGE',null,null,null);
					}
				}
				$('#patient_body_div *').prop('disabled',true);
		   		updateEventsOnContainer('UPLOAD-PATIENT-DATA-ONLY');
				if(document.getElementById('edit_patient_btn')) {
			   		document.getElementById('edit_patient_btn').style.display = '';
				}
		   		processPatientConsentInfectionRisk('PATIENT',document.getElementById('patient_id'),false);
				processPatientConsentInfectionRisk('GET-ALL-CONSENTS-FROM-DB',document.getElementById('patient_id'),false);
				processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-DB-SESSION',document.getElementById('patient_id'),false);
				
				if(window.location.pathname.includes('view_timeline')) {
					processWaitingButtonSpinner('START-PATIENT');
			   		processPatientConsentInfectionRisk('GET-ALL-TIMELINE-FROM-DB',document.getElementById('patient_id'),false);
				}
			}
			
		} else if(window.location.pathname.includes('edit_patient')) {
			
			initialiseForm('SESSION-PATIENT',null);
			
			$('#patient_body_div *').prop('disabled',false);
			
	   		document.getElementById('save_patient_clear_href').style.display = '';
	   		document.getElementById('add_consent_btn').style.display = '';
	   		
		} else if(window.location.pathname.includes('add_patient')) {
			
	   		document.getElementById('save_patient_clear_href').style.display = '';
	   		document.getElementById('add_consent_btn').style.display = '';
	   		
		}
   		
		if(document.getElementById('patient_cancel_href')) {
			document.getElementById('patient_cancel_href').style.display = '';
		}
   		
	} else if(window.location.pathname.includes('add_infection_risk') || window.location.pathname.includes('view_infection_risk')
			|| window.location.pathname.includes('view_searched_infection_risk')) {
		
		processWaitingButtonSpinner('START-INFECTION-RISK');
		processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-SESSION',null,false);
		
		if(window.location.pathname.includes('view_infection_risk') || window.location.pathname.includes('view_searched_infection_risk')) {

	   		document.getElementById('edit_patient_btn').style.display = '';
			if(window.location.pathname.includes('view_searched_infection_risk')) {
	   	   		document.getElementById('edit_patient_btn').onclick = function() {
	   	   			validateFormFields('do_not_validate_just_submit',null,'view_searched_patient',false)};
			}
			
			if (document.getElementById('consent_access').value.toLowerCase().includes('edit')) {
		   		document.getElementById('edit_infection_risk_btn').style.display = '';
				document.getElementById('save_infection_risk_btn').onclick = function() {
					validateFormFields('validate_infection_risk',null,'update_infection_risk',true)}; 
		   		document.getElementById('add_another_infection_risk_btn').style.display = '';
			} else {
				document.getElementById('edit_infection_risk_btn').style.display = 'none';
		   		document.getElementById('add_another_infection_risk_btn').style.display = 'none';
			}
			
			if(window.location.pathname.includes('view_searched_infection_risk')) {
				if(document.getElementById('advanced_search_sql_script').value) {
					addItemsToList('POPULATE-BACK-TO-ADVANCED-SEARCH-PAGE',null,null,null);
				} else if(document.getElementById('basic_search_options').value) {
					addItemsToList('POPULATE-BACK-TO-BASIC-SEARCH-PAGE',null,null,null);
				}
			}
			
		} else if(window.location.pathname.includes('add_infection_risk')) {
			
	   		document.getElementById('save_all_btn').style.display = '';
	   		document.getElementById('edit_consent_btn').style.display = '';
	   		document.getElementById('save_infection_risk_btn').onclick = function() {validateFormFields('validate_infection_risk',null,'add_infection_risk',true)}; 
	   		document.getElementById('add_another_infection_risk_btn').style.display = '';
	   		
		}
		
   		document.getElementById('infection_risk_body_div').style.display = 'none';
   		document.getElementById('infection_risk_cancel_href').style.display = '';
		
   		$('#infection_risk_body_div *').prop('disabled',true);
   		
		initialiseForm('INFECTION-RISK',null);

	} else if(window.location.pathname.includes('infection_risk_found_notify_om') || window.location.pathname.includes('infection_risk_found_notify_tech') 
			|| window.location.pathname.includes('infection_risk_unknown') || window.location.pathname.includes('infection_risk_unknown_notify_tech')) {
		
		initialiseForm('SESSION-PATIENT',null);
		$('#patient_body_div *').prop('disabled',true);
		processPatientConsentInfectionRisk('GET-FIRST-INFECTION-RISK-FROM-SESSION',null,false);
   		
		document.getElementById('prev_infection_risk_chkbox_div').style.display = 'none';
   		document.getElementById('add_another_infection_risk_btn').style.display = 'none';
   		document.getElementById('discard_infection_risk_btn').style.display = 'none';
   		document.getElementById('edit_consent_btn').style.display = 'none';
   		document.getElementById('edit_patient_btn').style.display = 'none';
   		document.getElementById('patient_buttons_message').style.display = 'none';
   		document.getElementById('save_all_btn').style.display = '';
   		document.getElementById('save_all_btn').innerHTML = 'Confirm Notification';
   		document.getElementById('edit_infection_risk_btn').style.display = '';
   	   	if(window.location.pathname.includes('infection_risk_found_notify_om')) {
   	   		document.getElementById('save_all_btn').onclick = function() {
   	   			validateFormFields('do_not_validate_just_submit',null,'update_infection_risk_confirm_om_notification',true)};
   	   	} else if(window.location.pathname.includes('infection_risk_found_notify_tech')) {
	   	   	document.getElementById('save_all_btn').onclick = function() {
	   	   		validateFormFields('validate_infection_risk',null,'update_infection_risk_confirm_tech_notification',true)};
   	   	} else if(window.location.pathname.includes('infection_risk_unknown_notify_tech')) {
	   	   	document.getElementById('save_all_btn').onclick = function() {
	   	   		validateFormFields('validate_infection_risk',null,'update_infection_risk_unknown_confirm_tech_notification',true)};
   		} else {
   	   		document.getElementById('save_all_btn').onclick = function() {
   	   			validateFormFields('validate_infection_risk',null,'update_infection_risk_unknown',true)};
   		}
   		document.getElementById('infection_risk_body_div').style.display = '';
		$('#infection_risk_body_div *').prop('disabled',true);
	}
}
function updateEventsOnContainer(whatToProcess)
{
	switch (whatToProcess) {
	case 'UPLOAD-PATIENT-DATA-ONLY':
   		document.getElementById('patient_surname').onblur = function() {uploadFormDataToSessionObjects('PATIENT',document.getElementById('patient_surname'))};
   		document.getElementById('select_age').onblur = function() {uploadFormDataToSessionObjects('PATIENT',document.getElementById('select_age'))};
   		document.getElementById('date_of_birth').onblur = function() {uploadFormDataToSessionObjects('PATIENT',document.getElementById('date_of_birth'))};
   		document.getElementById('hospital_number').onblur = function() {uploadFormDataToSessionObjects('PATIENT',document.getElementById('hospital_number'))};
   		document.getElementById('nhs_number').onblur = function() {uploadFormDataToSessionObjects('PATIENT',document.getElementById('nhs_number'))};
		break;
	}
}
function hideAndShowContainer(whichButton) 
{
	var idOfInputbox = $(whichButton).attr('id');
    var valueOfInputbox = $(whichButton).val();

    switch(idOfInputbox) {
    case 'aud_digital_cf_attached':
    	
    	if(valueOfInputbox.toLowerCase() == 'yes') {
        	if (document.getElementById('digital_cf_attachment_file_label').innerHTML == '') {
        		document.getElementById('aud_digital_cf_attached_alert').innerHTML = 'Warning - No consent form has been attached';
        		document.getElementById('aud_digital_cf_attached_alert').style.display = '';
        	} else {
        		document.getElementById('aud_digital_cf_attached_alert').style.display = 'none';
        	}
		} else {
    		document.getElementById('aud_digital_cf_attached_alert').style.display = 'none';
		}
		break;
		
    case 'select_search_criteria':
    	
    	if(valueOfInputbox == 'date_of_birth') {
    		document.getElementById('search_patient_keyword-validation').innerHTML = 'Search date format is dd-mm-yyyy or dd/mm/yyyy';
    		document.getElementById('search_patient_keyword-validation').style.display = '';
    	} else {
    		document.getElementById('search_patient_keyword-validation').style.display = 'none';
    	}

    	break;
    	
//    case 'sam_coll_before_sep_2006':
//    	
//    	if (document.getElementById('sam_coll_before_sep_2006').value.toLowerCase().includes('yes')){
//    		document.getElementById('date_of_consent_div').style.display = 'none';
//    		document.getElementById('consent_taken_by_div').style.display = 'none';
//    		document.getElementById('loc_id_div').style.display = 'none';
//    		document.getElementById('verbal_consent_div').style.display = 'none';
//    		document.getElementById('verbal_consent_recorded_div').style.display = 'none';
//    		document.getElementById('verbal_consent_recorded_by_div').style.display = 'none';
//    		document.getElementById('verbal_consent_document_file_div').style.display = 'none';
//    		document.getElementById('form_type_div').style.display = 'none';
//    		document.getElementById('form_version_div').style.display = 'none';
//    		document.getElementById('digital_cf_attachment_file_div').style.display = 'none';
//    		document.getElementById('consent_type_div').style.display = 'none';
//    		document.getElementById('select_consent_exclusions_div').style.display = 'none';
//    		document.getElementById('exclusions_comment_div').style.display = 'none';
//    		document.getElementById('select_samples_consented_to_div').style.display = 'none';
//    		document.getElementById('additional_document_file_div').style.display = 'none';
//    		document.getElementById('consent_notes_div').style.display = 'none';
//    		document.getElementById('stop_sample_donation_div').style.display = 'none';
//    		document.getElementById('stop_sample_donation_date_div').style.display = 'none';
//    		document.getElementById('withdrawn_div').style.display = 'none';
//    		document.getElementById('withdrawal_date_div').style.display = 'none';
//    		document.getElementById('withdrawal_document_file_div').style.display = 'none';
//    		document.getElementById('consent_validate_div').style.display = 'none';
//    	} else {
//    		document.getElementById('date_of_consent_div').style.display = '';
//    		document.getElementById('consent_taken_by_div').style.display = '';
//    		document.getElementById('loc_id_div').style.display = '';
//    		document.getElementById('verbal_consent_div').style.display = '';
//    		document.getElementById('form_type_div').style.display = '';
//    		document.getElementById('form_version_div').style.display = '';
//    		document.getElementById('digital_cf_attachment_file_div').style.display = '';
//    		document.getElementById('consent_type_div').style.display = '';
//    		document.getElementById('select_consent_exclusions_div').style.display = '';
//    		document.getElementById('exclusions_comment_div').style.display = '';
//    		switch (document.getElementById('user_department').value.toUpperCase()) {
//    		case 'HOTB':
//    	   		document.getElementById('select_samples_consented_to_div').style.display = '';
//    	   		break;
//    		default:
//    	   		document.getElementById('select_samples_consented_to_div').style.display = 'none';
//    			break;
//    		}
//    		document.getElementById('additional_document_file_div').style.display = '';
//    		document.getElementById('consent_notes_div').style.display = '';
//    		document.getElementById('stop_sample_donation_div').style.display = '';
//    		document.getElementById('withdrawn_div').style.display = '';
//
//    	    hideAndShowContainer(document.getElementById('verbal_consent'));
//    	    hideAndShowContainer(document.getElementById('consent_type'));
//    	    hideAndShowContainer(document.getElementById('withdrawn'));
//    	    hideAndShowContainer(document.getElementById('additional_document_file_label'));
//    	    hideAndShowContainer(document.getElementById('digital_cf_attachment_file_label'));
//    	    hideAndShowContainer(document.getElementById('withdrawal_document_file_label'));
//    	    hideAndShowContainer(document.getElementById('verbal_consent_document_file_label'));
//    	    hideAndShowContainer(document.getElementById('stop_sample_donation'));
//
//    	}
//
//    	break;
    
    case 'action_type':
    	
    	var default_save_btn_text = 'Send Action';
    	var btn_value = '';
    	if(document.getElementById(idOfInputbox).selectedIndex <= 0 || 
    			document.getElementById(idOfInputbox).value.toLowerCase().includes('no_action_to_send')) {
    		document.getElementById('action_notes_div').style.display = 'none';
    		if(window.location.pathname.includes('consent_withdrawn')) {
    			btn_value = 'confirm_notification';
    			default_save_btn_text = 'Confirm Notification';
    		} else if(window.location.pathname.includes('remove_samples')){
    			btn_value = 'confirm_samples_disposal';
    			default_save_btn_text = 'Confirm Disposal Of Samples';
    		} else if(window.location.pathname.includes('verify_consent') || window.location.pathname.includes('verify_imported_consent')){
    			btn_value = 'validate';
    			if(window.location.pathname.includes('verify_imported_consent')) {
        			default_save_btn_text = 'Finalise Imported Consent';
    			} else {
        			default_save_btn_text = 'Confirm Consent Validation';
    			}
    		}
    	} else {
    		document.getElementById('action_notes_div').style.display = '';
    		btn_value = document.getElementById(idOfInputbox).value;
    	}
    	switch (btn_value) {
    	case 'validate':
    		if(window.location.pathname.includes('verify_imported_consent')) {
       	   		document.getElementById('save_action_btn').onclick = function() {validateFormFields('validate_imported_consent_verify',null,'finalise_imported_consent',true);};
    		} else {
       	   		document.getElementById('save_action_btn').onclick = function() {validateFormFields('validate_consent_verify',null,null,true)};
    		}
    		break;
    	default:
    		if(btn_value != '') {
       	   		document.getElementById('save_action_btn').onclick = function() {validateFormFields('validate_action',null,'save_action',true)};
    		} else {
       	   		document.getElementById('save_action_btn').onclick = function() {alert('Invalid Action Selected')};
    		}
    		break;
    	}
		document.getElementById('save_action_btn').innerHTML = '<i class="fas fa-check-circle"></i> ' + default_save_btn_text;
		document.getElementById('save_action_btn').value = btn_value;
		if(document.getElementById('save_action_btn').value.length > 0) {
			processPatientConsentInfectionRisk('SAVE-USER-SELECTED-ACTION-TYPE',document.getElementById('save_action_btn'),false);
		}
		break;

    case 'samples_obtained_electronically':

    	if (document.getElementById('samples_obtained_electronically').value.toLowerCase().includes('yes')){
    		document.getElementById('prev_audit_samples_chkbox_div').style.display = '';
    		document.getElementById('add_save_discard_sample_div').style.display = '';
    		document.getElementById('add_audit_sample_btn').style.display = '';
    		document.getElementById('discard_audit_sample_btn').style.display = '';
    	} else { 
    		document.getElementById('prev_audit_samples_chkbox_div').style.display = 'none';
    		document.getElementById('add_save_discard_sample_div').style.display = 'none';
    		document.getElementById('add_audit_sample_btn').style.display = 'none';
    		document.getElementById('discard_audit_sample_btn').style.display = 'none';
    	}
		document.getElementById('audit_sample_div').style.display = 'none';
		document.getElementById('save_audit_sample_btn').style.display = 'none';
		document.getElementById('audit_sample_div').style.display = 'none';
    	
    	break;

    case 'aud_data_discrepancies_identified':

    	if (document.getElementById('aud_data_discrepancies_identified').value.toLowerCase().includes('yes')){
    		document.getElementById('aud_source_of_verified_data_div').style.display = '';
    		document.getElementById('aud_discrepancies_description_div').style.display = '';
    	} else {
    		document.getElementById('aud_source_of_verified_data_div').style.display = 'none';
    		document.getElementById('aud_discrepancies_description_div').style.display = 'none';
    	}
    	
    	break;
    	
    case 'data_discrepancies_identified':

       	if(document.getElementById('consent_validate_div')) {
    	
	    	if (document.getElementById('data_discrepancies_identified').value.toLowerCase().includes('yes')){
	    		document.getElementById('source_of_verified_data_div').style.display = '';
	    		document.getElementById('data_discrepancies_description_div').style.display = '';
	    		document.getElementById('data_discrepancies_verified_div').style.display = '';
	    		document.getElementById('data_discrepancies_verification_date_div').style.display = '';
	    		document.getElementById('data_discrepancies_verified_by_div').style.display = '';
	    	} else {
	    		document.getElementById('source_of_verified_data_div').style.display = 'none';
	    		document.getElementById('data_discrepancies_description_div').style.display = 'none';
	    		document.getElementById('data_discrepancies_verified_div').style.display = 'none';
	    		document.getElementById('data_discrepancies_verification_date_div').style.display = 'none';
	    		document.getElementById('data_discrepancies_verified_by_div').style.display = 'none';
	    	}

    	}
	    	
    	break;

	case 'aud_verify_consent_exclusions':
		
		if (document.getElementById('aud_verify_consent_exclusions').value.toLowerCase().includes('yes')){
    		document.getElementById('aud_statements_excluded_div').style.display = 'none';
    	} else {
    		document.getElementById('aud_statements_excluded_div').style.display = '';
    	}
    	
	case 'verify_consent_exclusions':

    	if (document.getElementById('verify_consent_exclusions').value.toLowerCase().includes('yes')){
    		document.getElementById('statements_excluded_div').style.display = 'none';
    	} else {
    		document.getElementById('statements_excluded_div').style.display = '';
    	}
		
		break;
    
    case 'infection_risk_exist':

    	if (document.getElementById('infection_risk_exist').value.toLowerCase().includes('yes')
    			|| document.getElementById('infection_risk_exist').value.toLowerCase().includes('unknown'))
    	{
    		if(window.location.pathname.includes('infection_risk_found_notify_om') || window.location.pathname.includes('infection_risk_found_notify_tech')) {
        		document.getElementById('infection_risk_exist_alert').style.display = '';
        		if(window.location.pathname.includes('infection_risk_found_notify_tech')) {
            		document.getElementById('infection_risk_exist_alert').innerHTML = 
            			'<b>Request approval from TB Operation\'s Manager for destruction of sample and confirm destruction.' + 
            			'Samples to be disposed as per relevant disposable procedures. Record all relevant information in \'Notes\', before confirming notification</b>';
        		} else if(window.location.pathname.includes('infection_risk_found_notify_om')) {
            		document.getElementById('infection_risk_exist_alert').innerHTML = 
            			'<b>Please confirm that you have received notification of Infection risk.' + 
            			' Technicians are to request approval and confirm sample destruction </b>';
        		}
    		} else if(window.location.pathname.includes('infection_risk_unknown_notify_tech') || window.location.pathname.includes('infection_risk_unknown')) {
        		document.getElementById('infection_risk_exist_alert').style.display = '';
        		if(window.location.pathname.includes('infection_risk_unknown_notify_tech')) {
            		document.getElementById('infection_risk_exist_alert').innerHTML = 
            			'<b>Unknown infection risk detected. Please quarantine samples until further notice and record relevant information in \'Notes\'</b>';
        		} else if(window.location.pathname.includes('infection_risk_unknown')) {
            		document.getElementById('infection_risk_exist_alert').innerHTML = 
            			'<b>Presence of uncertain infection risk recorded by Tissue Bank staff. Please edit the fields to confirm/decline risk of infection and record relevant information \'Notes\'</b>';
        		}
    		} else if(window.location.pathname.includes('/audit_consent')) {
        		document.getElementById('infection_risk_exist_alert').style.display = 'none'; // Don't need to show alert when auditing consent
    		} else {
        		document.getElementById('infection_risk_exist_alert').style.display = '';
            	if (document.getElementById('infection_risk_exist').value.toLowerCase().includes('yes')) {
            		document.getElementById('infection_risk_exist_alert').innerHTML = 
            			'<b>Notified Tissue Bank Operations Manager and Technician</b>';
            	} else if (document.getElementById('infection_risk_exist').value.toLowerCase().includes('unknown')) {
            		document.getElementById('infection_risk_exist_alert').innerHTML = 
            			'<b>Clinician to confirm and Technician notified</b>';
            	}
    		}
    		document.getElementById('select_infection_type_id_div').style.display = '';
    		document.getElementById('select_episode_of_infection_div').style.display = '';
    		document.getElementById('episode_start_date_div').style.display = '';
    		document.getElementById('select_continued_risk_div').style.display = '';
    		document.getElementById('select_sample_collection_div').style.display = '';
    		document.getElementById('select_sample_type_id_div').style.display = '';
    		document.getElementById('sample_collection_date_div').style.display = '';
    	}
    	else
    	{
    		document.getElementById('infection_risk_exist_alert').style.display = 'none';
    		document.getElementById('select_infection_type_id_div').style.display = 'none';
       		document.getElementById('other_infection_risk_div').style.display = 'none';
    		document.getElementById('select_episode_of_infection_div').style.display = 'none';
    		document.getElementById('episode_start_date_div').style.display = 'none';
    		document.getElementById('select_continued_risk_div').style.display = 'none';
    		document.getElementById('episode_finished_date_div').style.display = 'none';
    		document.getElementById('select_sample_collection_div').style.display = 'none';
    		document.getElementById('select_sample_type_id_div').style.display = 'none';
    		document.getElementById('sample_collection_date_div').style.display = 'none';
    	}
		document.getElementById('infection_risk_notes_div').style.display = '';
    	
        break;

    case 'select_infection_type_id':
    	
		if (document.getElementById('select_infection_type_id').selectedIndex > 0 && document.getElementById('select_infection_type_id').options[
			document.getElementById('select_infection_type_id').selectedIndex].text.toLowerCase().includes('other')) {
			
    		document.getElementById('other_infection_risk_div').style.display = '';
    		
		} else {
			
       		document.getElementById('other_infection_risk_div').style.display = 'none';
       		document.getElementById('other_infection_risk').value = '';
       		
    	}

		break;
        
    case 'select_continued_risk':

    	if (document.getElementById('infection_risk_exist').value.toLowerCase().includes('yes')
    			|| document.getElementById('infection_risk_exist').value.toLowerCase().includes('unknown')) {
    		if(document.getElementById(idOfInputbox).value.toLowerCase().includes('yes') || document.getElementById(idOfInputbox).value.toLowerCase().includes('unknown')) {
        		document.getElementById('continued_risk').value = document.getElementById(idOfInputbox).value;
        		document.getElementById('episode_finished_date_div').style.display = 'none';
    		} else if(document.getElementById(idOfInputbox).value.toLowerCase().includes('no')) {
        		document.getElementById('continued_risk').value = 'No';
        		document.getElementById('episode_finished_date_div').style.display = '';
    		}
    	}
    	
		break;
		
    case 'stop_sample_donation':
    	
    	if (document.getElementById('stop_sample_donation').value.toLowerCase().includes('yes')){
    		document.getElementById('stop_sample_donation_date_div').style.display = '';
    	} else {
    		document.getElementById('stop_sample_donation_date_div').style.display = 'none';
    	}
        break;
    
    case 'withdrawn':
    		
    	if(document.getElementById('withdrawn').value.toLowerCase().includes('yes') && !document.getElementById('withdrawn').disabled)
    	{
        	if(!document.getElementById('is_validated').value.toLowerCase().includes('yes') && !document.getElementById('is_finalised').value.toLowerCase().includes('yes')) {
        		alert('You cannot withdraw consent which has NOT been validated yet. Changing withdrawn consent to NO...');
       			document.getElementById('withdrawn').selectedIndex = 1;
        	} else if(document.getElementById('marked_for_auditing').value.toLowerCase().includes('yes')) {
        		alert('You cannot withdraw consent which has been marked for auditing. Changing withdrawn consent to NO...');
       			document.getElementById('withdrawn').selectedIndex = 1;
        	}
    	}
    		
	    if(window.location.pathname.includes('edit_consent') || window.location.pathname.includes('view_consent') || window.location.pathname.includes('view_searched_consent')
	    		|| window.location.pathname.includes('consent_withdrawn') || window.location.pathname.includes('remove_samples')) {
			
        	if (document.getElementById('withdrawn').value.toLowerCase().includes('yes')) {
        		document.getElementById('consent_withdrawn_alert').style.display = '';
        		document.getElementById('consent_withdrawn_alert').innerHTML = 
        			'<b>Consent withdrawn - Action will be sent to notify Technicians and Operations Manager</b>';
        		if(window.location.pathname.includes('consent_withdrawn') || window.location.pathname.includes('remove_samples')) {
            		document.getElementById('consent_withdrawn_alert_header_bold').style.display = '';
            		if(window.location.pathname.includes('remove_samples')) {
                		document.getElementById('consent_withdrawn_alert_header_bold').innerHTML = 
                			'<b>Consent withdrawn: \'Request approval from TB Operation\'s Manager for destruction of sample and confirm destruction. ' + 
                			'Samples to be disposed as per relevant disposable procedures. Record all relevant information in \'Notes\', before confirming notification</b>';
            		} else if (window.location.pathname.includes('consent_withdrawn')) {
                		document.getElementById('consent_withdrawn_alert_header_bold').innerHTML = 
                			'<b>Please confirm that you have received notification of Consent Withdrawn. Technicians are to request approval and confirm sample destruction</b>';
            		}
        		}
        	} else {
        		document.getElementById('consent_withdrawn_alert').style.display = 'none';
        	}
        	
		} else {
    		document.getElementById('consent_withdrawn_alert').style.display = 'none';
		}
    	
    	if (document.getElementById('withdrawn').value.toLowerCase().includes('yes')){
    		document.getElementById('withdrawal_date_div').style.display = '';
    		document.getElementById('withdrawal_document_file_div').style.display = '';
    	} else {
    		document.getElementById('withdrawal_date_div').style.display = 'none';
    		document.getElementById('withdrawal_document_file_div').style.display = 'none';
    	}
    	
        break;
    
	case 'consent_type':

    	if (document.getElementById('consent_type').value.toLowerCase().includes('partial')) {
    		document.getElementById('select_consent_exclusions_div').style.display = '';
    	} 
    	else {
    		document.getElementById('select_consent_exclusions_div').style.display = 'none';
    		document.getElementById('select_consent_exclusions').value = '';
    		document.getElementById('consent_exclusions').value = '';
    	}
    	break;
    
    case 'digital_cf_attachment_file_label': case 'verbal_consent_document_file_label': case 'withdrawal_document_file_label': case 'additional_document_file_label':

    	if (document.getElementById(idOfInputbox).innerHTML != '') 
    	{
    		document.getElementById(idOfInputbox).style.display = '';
    		document.getElementById(idOfInputbox.replace('_label','_button')).style.display = '';
    	}
    	else {
    		document.getElementById(idOfInputbox).style.display = 'none';
    		document.getElementById(idOfInputbox.replace('_label','_button')).style.display = 'none';
    	}
    	break;
    
    case 'verbal_consent':

    	if (document.getElementById('verbal_consent').value.toLowerCase().includes('yes'))
    	{	
    		document.getElementById('verbal_consent_recorded_div').style.display = '';
    		document.getElementById('verbal_consent_recorded_by_div').style.display = '';
    		document.getElementById('verbal_consent_document_file_div').style.display = '';

    		document.getElementById('form_type_div').style.display = 'none';
    		document.getElementById('form_version_div').style.display = 'none';
    		document.getElementById('digital_cf_attachment_file_div').style.display = 'none';
    		document.getElementById('consent_type_div').style.display = 'none';
    		
        	if(document.getElementById('consent_validate_div')) {
        		
    			document.getElementById('verbal_document_checked_div').style.display = '';
        		document.getElementById('verbal_consent_checked_date_div').style.display = '';
        		document.getElementById('verbal_consent_checked_by_div').style.display = '';
        		document.getElementById('digital_cf_attached_div').style.display = 'none';
        		document.getElementById('cf_physical_location_div').style.display = 'none';
        		document.getElementById('date_of_consent_stated_div').style.display = 'none';
        		document.getElementById('patient_signature_div').style.display = 'none';
        		document.getElementById('person_taking_consent_div').style.display = 'none';
        		document.getElementById('cf_validity_div').style.display = 'none';
        		document.getElementById('cf_checked_date_div').style.display = 'none';
        		document.getElementById('cf_checked_by_div').style.display = 'none';
    		
    		} 
        	
        	if (document.getElementById('consent_audit_div')) {

    			document.getElementById('aud_verbal_document_checked_div').style.display = '';
        		document.getElementById('aud_digital_cf_attached_div').style.display = 'none';
        		document.getElementById('aud_physical_consent_form_div').style.display = 'none';
        		document.getElementById('aud_cf_physical_location_div').style.display = 'none';
        		document.getElementById('aud_date_of_consent_stated_div').style.display = 'none';
        		document.getElementById('aud_patient_signature_div').style.display = 'none';
        		document.getElementById('aud_person_taking_consent_div').style.display = 'none';
        		document.getElementById('aud_cf_validity_div').style.display = 'none';
    			
    		}

    	}
    	else
    	{
    		document.getElementById('verbal_consent_recorded_div').style.display = 'none';
    		document.getElementById('verbal_consent_recorded_by_div').style.display = 'none';
    		document.getElementById('verbal_consent_document_file_div').style.display = 'none';

    		document.getElementById('form_type_div').style.display = '';
    		document.getElementById('form_version_div').style.display = '';
    		document.getElementById('digital_cf_attachment_file_div').style.display = '';
    		document.getElementById('consent_type_div').style.display = '';

    		if(document.getElementById('consent_validate_div')) {

	    		document.getElementById('verbal_document_checked_div').style.display = 'none';
	    		document.getElementById('verbal_consent_checked_date_div').style.display = 'none';
	    		document.getElementById('verbal_consent_checked_by_div').style.display = 'none';
	    		document.getElementById('digital_cf_attached_div').style.display = '';
	    		document.getElementById('cf_physical_location_div').style.display = '';
	    		document.getElementById('date_of_consent_stated_div').style.display = '';
	    		document.getElementById('patient_signature_div').style.display = '';
	    		document.getElementById('person_taking_consent_div').style.display = '';
	    		document.getElementById('cf_validity_div').style.display = '';
	    		document.getElementById('cf_checked_date_div').style.display = '';
	    		document.getElementById('cf_checked_by_div').style.display = '';

        	}
    		
        	if (document.getElementById('consent_audit_div')) {

    			document.getElementById('aud_verbal_document_checked_div').style.display = 'none';
        		document.getElementById('aud_digital_cf_attached_div').style.display = '';
        		document.getElementById('aud_physical_consent_form_div').style.display = '';
        		document.getElementById('aud_cf_physical_location_div').style.display = '';
        		document.getElementById('aud_date_of_consent_stated_div').style.display = '';
        		document.getElementById('aud_patient_signature_div').style.display = '';
        		document.getElementById('aud_person_taking_consent_div').style.display = '';
        		document.getElementById('aud_cf_validity_div').style.display = '';
	    		
    		}     		
    	}

    	break;
    
    case 'select_volunteer':

    	if (document.getElementById('select_volunteer').value.toLowerCase().includes('yes'))
    	{
    		document.getElementById('select_age_div').style.display = '';
    		document.getElementById('date_of_birth_div').style.display = 'none';
    		document.getElementById('hospital_number_div').style.display = 'none';
    		document.getElementById('nhs_number_div').style.display = 'none';
    		
        	if(document.getElementById('consent_body_div')) {
        		document.getElementById('form_type').value = 'Volunteer';
    		}
    	
    	} else {
    		
    		document.getElementById('date_of_birth_div').style.display = '';
    		document.getElementById('hospital_number_div').style.display = '';
    		document.getElementById('nhs_number_div').style.display = '';
    		document.getElementById('select_age_div').style.display = 'none';

        	if(document.getElementById('consent_body_div')) {
        		document.getElementById('form_type').value = 'Patient';
    		}

    	}
       	if(document.getElementById('consent_body_div')) {
        	processDropdownMenus('FORM-VERSION');
        	processDropdownMenus('CONSENT-EXCLUSION');
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
    	case 'ADVANCED-SEARCH': case 'PREVIOUS-ADVANCED-SEARCH': 
    		var selected_cols = '', str_to_use = '';
    		var start_index = 0;
        	switch (whatToProcess) {
        	case 'ADVANCED-SEARCH': 
        		start_index = 0; str_to_use = whichInputBox.replace(/\s/g, '$'); // replace white space with dollar sign
        		for(var iSplit=1; iSplit <= Math.trunc(str_to_use.length / 40) + 1; iSplit++) {
        			if(iSplit == Math.trunc(str_to_use.length / 40) + 1) {
                		nameOfInputbox.push(str_to_use.substring(start_index,str_to_use.length));
        			} else {
                		nameOfInputbox.push(str_to_use.substring(start_index,start_index+40));
        			}
        			start_index = start_index + 40;
        		}
        	    $('#select_search_result_columns option:selected').each(function() {
        	    	if(selected_cols) {
            	    	selected_cols = selected_cols + ' | ' + $(this).val();
        	    	} else {
            	    	selected_cols = $(this).val();
        	    	}
        	    });
        	    start_index = 0; str_to_use = selected_cols.replace(/\s/g, '$');
        		for(var iSplit=1; iSplit <= Math.trunc(str_to_use.length / 40) + 1; iSplit++) {
        			if(iSplit == Math.trunc(str_to_use.length / 40) + 1) {
        				valueOfInputbox.push(str_to_use.substring(start_index,str_to_use.length));
        			} else {
        				valueOfInputbox.push(str_to_use.substring(start_index,start_index+40));
        			}
        			start_index = start_index + 40;
        		}
        		break;
        	case 'PREVIOUS-ADVANCED-SEARCH':
        		start_index = 0; str_to_use = document.getElementById('advanced_search_sql_script').value.replaceAll(',', '|').replace(/\s/g, '$'); // replace white space with dollar sign
        		for(var iSplit=1; iSplit <= Math.trunc(str_to_use.length / 40) + 1; iSplit++) {
        			if(iSplit == Math.trunc(str_to_use.length / 40) + 1) {
                		nameOfInputbox.push(str_to_use.substring(start_index,str_to_use.length));
        			} else {
                		nameOfInputbox.push(str_to_use.substring(start_index,start_index+40));
        			}
        			start_index = start_index + 40;
        		}
        		start_index = 0; str_to_use = document.getElementById('advanced_search_selected_columns').value.replace(/\s/g, '$');
        		for(var iSplit=1; iSplit <= Math.trunc(str_to_use.length / 40) + 1; iSplit++) {
        			if(iSplit == Math.trunc(str_to_use.length / 40) + 1) {
        				valueOfInputbox.push(str_to_use.substring(start_index,str_to_use.length));
        			} else {
        				valueOfInputbox.push(str_to_use.substring(start_index,start_index+40));
        			}
        			start_index = start_index + 40;
        		}
        		break;
        	}
    		break;
    	case 'REMOVE-FILE':
            nameOfInputbox.push($(whichInputBox).attr('id').replace('_button','')); // Get the file input box name instead of the button name
            valueOfInputbox.push(document.getElementById('consent_id').value);
    		break;
    	default:
    		switch(whatToProcess) {
    		case 'CHECK-PATIENT-LOCKED-AND-SUBMIT':
                nameOfInputbox.push($(whichInputBox).attr('id'));
	            valueOfInputbox.push($(whichInputBox).attr('id'));
    			break;
    		case 'PATIENT':
    			if(document.getElementById('consent_access').value == 'view_restricted') {
    				return false;
    			}
                nameOfInputbox.push($(whichInputBox).attr('id').replace('select_',''));
    			break;
    		case 'GET-VARIOUS-SEARCH-OPTIONS':
    	        nameOfInputbox.push($(whichInputBox).val());
    			break;
    		default:
                nameOfInputbox.push($(whichInputBox).attr('id'));
    			break;
    		}

	    	if ($(whichInputBox).val() != '') {
	            valueOfInputbox.push($(whichInputBox).val());
	        } else {
	            valueOfInputbox.push($(whichInputBox).attr('value'));
	        }
	    	
    		break;
    		
    	}
    }
    
    var this_department = $('#whichDepartment').val();
    
	switch(whatToProcess) {
	case 'BASIC-SEARCH': case 'PREVIOUS-BASIC-SEARCH':
		switch(whatToProcess) {
		case 'BASIC-SEARCH':
	        nameOfInputbox.push(document.getElementById('search_patient_keyword').id);
	        nameOfInputbox.push(document.getElementById('select_search_criteria').id.replace('select_',''));
	        valueOfInputbox.push(document.getElementById('search_patient_keyword').value);
	        valueOfInputbox.push(document.getElementById('select_search_criteria').value);
			break;
		case 'PREVIOUS-BASIC-SEARCH':
	        nameOfInputbox.push('search_patient_keyword');
	        nameOfInputbox.push('search_criteria');
	        valueOfInputbox.push(document.getElementById('basic_search_options').value.split('|')[0]);
	        valueOfInputbox.push(document.getElementById('basic_search_options').value.split('|')[1]);
			break;
		}
		break;
	case 'CHECK-ACTION-LOCKED-AND-SUBMIT': case 'CHECK-PATIENT-STATUS-IMPORTED-CONSENT-AND-SUBMIT': 
	case 'CHECK-PATIENT-STATUS-AUDIT-CONSENT-AND-SUBMIT': case 'GET-SINGLE-COMPLETED-ACTION-FROM-SESSION':
		this_department = $(whichInputBox).attr('id').split('_')[0];
		break;
	case 'UNLOCK-PATIENT-AND-REFRESH':
		this_department = $('input[name=locked_patient_radio]:checked').attr('id').split('_')[0];
		break;
	}

	switch(whatToProcess) {
	case 'REMOVE-FILE': case 'GET-SINGLE-COMPLETED-ACTION-FROM-SESSION': case 'GET-ALL-CONSENTS-FROM-DB': case 'EXPORT-SEARCH-RESULT-LOG-TIMELINE':
	case 'GET-ALL-INFECTION-RISKS-FROM-DB-SESSION': case 'GET-ALL-INFECTION-RISKS-FROM-SESSION': case 'GET-SINGLE-INFECTION-RISK-FROM-SESSION': case 'GET-ALL-AUDIT-SAMPLES-FROM-SESSION':
	case 'DISCARD-INFECTION-RISK-FROM-SESSION-IRS': case 'ADD-NEW-INFECTION-RISK-TO-SESSION-IRS': case 'SAVE-SESSION-INFECTION-RISK': case 'GET-IMPORT-CONSENTS-FROM-SESSION':
	case 'GET-SINGLE-COMPLETE-CONSENT-VALIDATE-FROM-SESSION': case 'GET-ACTIVE-ACTIONS-FROM-SESSION': case 'GET-COMPLETED-ACTIONS-FROM-SESSION': case 'GET-LOCKED-ACTIONS-FROM-SESSION': 
	case 'GET-SINGLE-COMPLETE-CONSENT-FROM-SESSION': case 'GET-CONSENT-ACTIONS-FROM-DB': case 'CHECK-ACTION-LOCKED-AND-SUBMIT': case 'SAVE-USER-SELECTED-ACTION-TYPE': 
	case 'UNLOCK-USER-ACTION': case 'GET-SINGLE-COMPLETED-ACTION': case 'UNLOCK-ANY-LOCKED-ACTION': case 'GET-SINGLE-LOCKED-ACTION-FROM-SESSION': case 'UNLOCK-PATIENT-AND-REFRESH':
	case 'GET-FIRST-INFECTION-RISK-FROM-SESSION': case 'ALLOWING-USER-TO-EDIT-PATIENT': case 'ALLOWING-USER-TO-EDIT-INFECTION-RISK': case 'GET-LOCKED-PATIENTS-FROM-SESSION':
	case 'UNLOCK-ACTION-AND-REFRESH': case 'ALERT-PATIENT-VOLUNTEER': case 'GET-ALL-TIMELINE-FROM-DB': case 'GET-SINGLE-TIMELINE': case 'GET-AUDIT-CONSENTS-FROM-SESSION':
	case 'ADD-NEW-AUDIT-SAMPLE-TO-SESSION': case 'CHECK-PATIENT-LOCKED-AND-SUBMIT': case 'CHECK-PATIENT-STATUS-IMPORTED-CONSENT-AND-SUBMIT': 
	case 'CHECK-PATIENT-STATUS-AUDIT-CONSENT-AND-SUBMIT': case 'GET-SINGLE-AUDIT-SAMPLE-FROM-SESSION': case 'GET-SINGLE-COMPLETE-CONSENT-AUDIT-FROM-SESSION': case 'PREVIOUS-BASIC-SEARCH':
	case 'SAVE-CONSENT-AUDIT': case 'DISCARD-SAMPLE-AUDIT-FROM-SESSION': case 'BASIC-SEARCH': case 'GET-VARIOUS-SEARCH-OPTIONS': case 'ADVANCED-SEARCH': case 'PREVIOUS-ADVANCED-SEARCH':
		
		$.ajax({    
            type : 'Get',     
            url : 'processPatientConsentInfectionRisk.html',     
            data : 'whatToProcess=' + whatToProcess + '&nameOfInputbox=' + nameOfInputbox + '&valueOfInputbox=' + valueOfInputbox 
            	+ '&whichDepartment=' + this_department + '&whichURL=' + window.location.pathname,
            dataType : 'json',
            success : function(data) {
            	switch (whatToProcess) {
            	case 'ADVANCED-SEARCH': case 'PREVIOUS-ADVANCED-SEARCH':
            		if(Array.isArray(data)){
                    	switch (whatToProcess) {
                    	case 'ADVANCED-SEARCH':
                    		initialiseForm('POPULATE-ADVANCED-SEARCH',data);
                    		break;
                    	case 'PREVIOUS-ADVANCED-SEARCH':
                    		initialiseForm('POPULATE-PREVIOUS-ADVANCED-SEARCH',data);
                    		break;
                    	}
//                		$('#select_advanced_search_criteria').attr('disabled',true);            		
//                		$('#select_search_result_columns').attr('disabled',true);
//                		$('#advanced_search_btn').attr('disabled',true);
//                		$('#advanced_search_add_option_btn').attr('disabled',true);
            		} else {
                   		document.getElementById('search_result_div').style.display = 'none';
            			document.getElementById('no_search_result_found_h6').style.display = '';
            			document.getElementById('no_search_result_found_h6').innerHTML = data.error_occured_txt;
            		}
            		processWaitingButtonSpinner('END-SEARCH-PATIENT');
            		break;
            	case 'GET-VARIOUS-SEARCH-OPTIONS':
            		addItemsToList('POPULATE-ADVANCED-SEARCH-OPTIONS-' + $(whichInputBox).val().toUpperCase(), 
            				document.getElementById('advanced_search_options_div'),data,null);
            		break;
            	case 'BASIC-SEARCH': case 'PREVIOUS-BASIC-SEARCH':
            		initialiseForm('POPULATE-BASIC-SEARCH',data);
            		processWaitingButtonSpinner('END-SEARCH-PATIENT');
            		break;
            	case 'SAVE-CONSENT-AUDIT':
    				processWaitingButtonSpinner('END-CONSENT-AUDIT');
        			alert('Consent Audit Form Saved');
            		break;
            	case 'GET-ALL-AUDIT-SAMPLES-FROM-SESSION': case 'DISCARD-SAMPLE-AUDIT-FROM-SESSION':
              		initialiseForm('SAMPLE-AUDIT-CHECK-BOXES', data);
            		processWaitingButtonSpinner('END-CONSENT-AUDIT');
        	   		document.getElementById('save_audit_sample_btn').style.display = 'none';
        	   		document.getElementById('add_audit_sample_btn').style.display = '';
            		document.getElementById('discard_audit_sample_btn').style.display = '';
        	   		document.getElementById('audit_sample_div').style.display = 'none';
            		break;
            	case 'GET-SINGLE-TIMELINE':
            		initialiseForm('TIMELINE', data);
            		break;
            	case 'GET-ALL-TIMELINE-FROM-DB':
              		initialiseForm('TIMELINE-LIST-DB',data);
    				processWaitingButtonSpinner('END-PATIENT');
            		break;
            	case 'ALERT-PATIENT-VOLUNTEER':
            		if(data == null) {
    	           		document.getElementById('form_type_alert').style.display = '';
            		} else {
    	           		document.getElementById('form_type_alert').style.display = 'none';
            		}
            		break;
            	case 'ALLOWING-USER-TO-EDIT-PATIENT': case 'ALLOWING-USER-TO-EDIT-INFECTION-RISK':
            		if(data == null) {
            			alert('You are not allowed to edit patient details nor infection risks.');
                    	switch (whatToProcess) {
                    	case 'ALLOWING-USER-TO-EDIT-PATIENT': 
                    		processWaitingButtonSpinner('END-PATIENT');
                    		break;
                    	case 'ALLOWING-USER-TO-EDIT-INFECTION-RISK':
                    		processWaitingButtonSpinner('END-INFECTION-RISK');
                    		break;
                    	}
                    	return false;
            		} 
                	switch (whatToProcess) {
                	case 'ALLOWING-USER-TO-EDIT-PATIENT': 
                		if(document.getElementById('locked_by').value > 0 && document.getElementById('user_id').value > 0 
                				&& document.getElementById('locked_by').value != document.getElementById('user_id').value) {
                			alert('Patient is locked by another user \n\n' + document.getElementById('locked_description').value);
                	    	document.patient_form.action = 'welcome';
                	       	document.patient_form.submit();
                	       	return false;
               			}
                		$('#patient_body_div *').prop('disabled',false);
                   		document.getElementById('save_patient_btn').style.display = '';
                   		document.getElementById('edit_patient_btn').style.display = 'none';
                		processWaitingButtonSpinner('END-PATIENT');
                		break;
                	case 'ALLOWING-USER-TO-EDIT-INFECTION-RISK':
    	        		$('#infection_risk_body_div *').prop('disabled',false);
    	           		document.getElementById('save_infection_risk_btn').style.display = '';
    	           		if(window.location.pathname.includes('add_infection_risk') || window.location.pathname.includes('view_infection_risk')
    	           			|| window.location.pathname.includes('view_searched_infection_risk')) {
    	               			document.getElementById('add_another_infection_risk_btn').style.display = '';
    	           		}       		
    	           		document.getElementById('edit_infection_risk_btn').style.display = 'none';
                		processWaitingButtonSpinner('END-INFECTION-RISK');
                		break;
                	}
            		break;
            	case 'CHECK-PATIENT-LOCKED-AND-SUBMIT': case 'CHECK-PATIENT-STATUS-IMPORTED-CONSENT-AND-SUBMIT': case 'CHECK-PATIENT-STATUS-AUDIT-CONSENT-AND-SUBMIT':
            		if(data.locked_by > 0 && document.getElementById('user_id').value > 0 && data.locked_by != document.getElementById('user_id').value) {
            			alert('Patient is locked by another user \n\n' + data.locked_description);
                    	switch (whatToProcess) {
                    	case 'CHECK-PATIENT-LOCKED-AND-SUBMIT': 
                			processWaitingButtonSpinner('END-SEARCH-PATIENT');
                    		break;
                    	case 'CHECK-PATIENT-STATUS-IMPORTED-CONSENT-AND-SUBMIT':
                			processWaitingButtonSpinner('END-VIEW-IMPORT-CONSENT');
                    		break;
                    	case 'CHECK-PATIENT-STATUS-AUDIT-CONSENT-AND-SUBMIT':
                			processWaitingButtonSpinner('END-VIEW-AUDIT-CONSENT');
                    		break;
                    	}
            			return false;
            		} else {
                    	switch (whatToProcess) {
                    	case 'CHECK-PATIENT-LOCKED-AND-SUBMIT': 
                			document.getElementById('patient_id').value = valueOfInputbox[0];
                			document.getElementById('selected_department').value = this_department;
                			if($(whichInputBox).find(':checkbox').prop('checked') == true) {
                       			document.search_form.action = 'view_timeline';
                			} else if(document.getElementById('view_patient_audit_chk_' + valueOfInputbox[0]) && 
                					document.getElementById('view_patient_audit_chk_' + valueOfInputbox[0]).checked == true) {
                       			document.search_form.action = 'view_audit';
                			} else {
                       			document.search_form.action = 'view_searched_patient';
                			}
                   			$('.my_waiting_modal').modal('show');
                			document.search_form.submit();
                    		break;
                    	case 'CHECK-PATIENT-STATUS-IMPORTED-CONSENT-AND-SUBMIT': case 'CHECK-PATIENT-STATUS-AUDIT-CONSENT-AND-SUBMIT':
                			document.getElementById('selected_data_id').value = $(whichInputBox).val();
                			document.getElementById('selected_department').value = $(whichInputBox).attr('id').split('_')[0];
                        	switch (whatToProcess) {
                        	case 'CHECK-PATIENT-STATUS-IMPORTED-CONSENT-AND-SUBMIT':
                    			document.user_actions_form.action = 'verify_imported_consent';
                        		break;
                        	case 'CHECK-PATIENT-STATUS-AUDIT-CONSENT-AND-SUBMIT':
                    			document.user_actions_form.action = 'audit_consent';
                        		break;
                        	}
                			$('.my_waiting_modal').modal('show');
                	       	document.user_actions_form.submit();
                    		break;
                    	}
            		} 
            		break;
            	case 'CHECK-ACTION-LOCKED-AND-SUBMIT':
            		if(data == null) {
            			alert('This entity does NOT exist anymore');
            			processWaitingButtonSpinner('END-USER-ACTION');
            		} else {
            			if(data.status.toLowerCase().includes('locked') || data.locked_description.length > 0) {
                			alert('This entity is locked by another user \n\n' + data.locked_description);
                			processWaitingButtonSpinner('END-USER-ACTION');
            			} else {
                			document.getElementById('selected_data_id').value = $('input[name=user_action_radio]:checked').val();
                			document.getElementById('selected_department').value = $('input[name=user_action_radio]:checked').attr('id').split('_')[0];
                   			document.user_actions_form.action = $('input[name=user_action_radio]:checked').attr('id')
                   				.substring(5,$('input[name=user_action_radio]:checked').attr('id').indexOf('_radio')); // Remove '_radio_1' from 'verify_consent_radio_1' send 'verify_consent' to controller 
                   			$('.my_waiting_modal').modal('show');
                			document.user_actions_form.submit();
            			}
            		}
            		break;
            	case 'GET-CONSENT-ACTIONS-FROM-DB':
              		initialiseForm('ACTIONS-LIST-DB',data);
              		break;
            	case 'GET-IMPORT-CONSENTS-FROM-SESSION':
           			initialiseForm('POPULATE-IMPORT-CONSENTS', data);
            		break;
            	case 'GET-AUDIT-CONSENTS-FROM-SESSION':
           			initialiseForm('POPULATE-AUDIT-CONSENTS', data);
            		break;
            	case 'GET-ACTIVE-ACTIONS-FROM-SESSION':
            		initialiseForm('ACTION-COUNT', data);
            		if(window.location.pathname.includes('user_actions') || window.location.pathname.includes('save_action')) {
                		initialiseForm('POPULATE-ACTIVE-ACTIONS', data);
            		}
            		break;
            	case 'GET-COMPLETED-ACTIONS-FROM-SESSION':
               		initialiseForm('POPULATE-COMPLETED-ACTIONS', data);
            		break;
            	case 'GET-LOCKED-PATIENTS-FROM-SESSION':
               		initialiseForm('POPULATE-LOCKED-PATIENTS', data);
            		break;
            	case 'GET-LOCKED-ACTIONS-FROM-SESSION':
               		initialiseForm('POPULATE-LOCKED-ACTIONS', data);
    				processPatientConsentInfectionRisk('GET-LOCKED-PATIENTS-FROM-SESSION',null,false);
            		break;
            	case 'SAVE-SESSION-INFECTION-RISK':
            		processWaitingButtonSpinner('END-INFECTION-RISK');
            		break;
            	case 'GET-SINGLE-COMPLETE-CONSENT-AUDIT-FROM-SESSION':
               		initialiseForm('CONSENT-AUDIT', data);
               		initialiseForm('SAMPLE-AUDIT-CHECK-BOXES', data.sample_types);
           			if(document.getElementById('consent_audit_id').value > 0) {
               	   		document.getElementById('consent_audit_div').style.display = '';
            		    hideAndShowContainer(document.getElementById('verbal_consent'));
            	   		hideAndShowContainer(document.getElementById('samples_obtained_electronically'));
           			}
               		if(window.location.pathname.includes('edit_consent') || window.location.pathname.includes('view_consent')
               				|| window.location.pathname.includes('view_searched_consent')) {
                		$('#consent_audit_body_div *').prop('disabled',true);
                		$('#prev_audit_samples_chkbox_div *').prop('disabled',false);
               	   		document.getElementById('add_save_discard_sample_div').style.display = 'none';
               	   		document.getElementById('audit_save_finalise_btn_div').style.display = 'none';
               		}
               		break;
            	case 'GET-SINGLE-COMPLETE-CONSENT-VALIDATE-FROM-SESSION':
               		initialiseForm('CONSENT-VALIDATION', data);
               		if(window.location.pathname.includes('edit_consent') || window.location.pathname.includes('view_consent')
               				|| window.location.pathname.includes('view_searched_consent')) {
               			if(document.getElementById('consent_validate_id').value > 0) {
                   	   		document.getElementById('consent_validate_div').style.display = '';
                			if (document.getElementById('consent_access').value.toLowerCase().includes('edit')) {
                				$('#consent_validate_div *').prop('disabled',false);
        	           	   		document.getElementById('save_consent_validate_div').style.display = '';
    	               	   		document.getElementById('save_consent_validate_btn').onclick = function() {
    	               	   			validateFormFields('save_validate_consent_data',null,null,true)};
                			} else {
                				$('#consent_validate_div *').prop('disabled',true);
        	           	   		document.getElementById('save_consent_validate_div').style.display = 'none';
                			}
                		    hideAndShowContainer(document.getElementById('verbal_consent'));
               			}
               		}
            		break;
            	case 'GET-SINGLE-COMPLETE-CONSENT-FROM-SESSION':
            		initialiseForm('CONSENT', data);
            		if (document.getElementById('consent_id').value > 0  && document.getElementById('is_imported').value.toLowerCase().includes('yes')) {
        	    		validateFormFields('validate_consent',null,null,false);
            		}
            		if(document.getElementById('user_actions_div') && document.getElementById('user_actions_div').style.display == '') {
                   		processPatientConsentInfectionRisk('GET-CONSENT-ACTIONS-FROM-DB',document.getElementById('consent_id'),false);
            		}
               		if (window.location.pathname.includes('edit_consent') || window.location.pathname.includes('view_consent')
               				|| window.location.pathname.includes('view_searched_consent') || window.location.pathname.includes('verify_consent') 
               				|| window.location.pathname.includes('verify_imported_consent') || window.location.pathname.includes('/audit_consent')) {
               			if(document.getElementById('consent_id').value > 0) {
                   			processPatientConsentInfectionRisk('GET-SINGLE-COMPLETE-CONSENT-VALIDATE-FROM-SESSION',document.getElementById('consent_id'),false);
               				if(window.location.pathname.includes('/audit_consent') || window.location.pathname.includes('view_consent')
               						|| window.location.pathname.includes('view_searched_consent')) {
                       			processPatientConsentInfectionRisk('GET-SINGLE-COMPLETE-CONSENT-AUDIT-FROM-SESSION',document.getElementById('consent_id'),false);
               				}                    			
               			}
               			if(window.location.pathname.includes('view_consent') || window.location.pathname.includes('view_searched_consent')) {
               				$('#consent_body_div *').prop('disabled',true);
               		   		$('#consent_exclusion_btn').attr('disabled',false);
               			    if(document.getElementById('select_samples_consented_to_div').style.display == '') {
               			   		$('#samples_consented_to_btn').attr('disabled',false);
               			    }
               		   		$('#save_consent_btn_div *').attr('disabled',false);
               			} else if(window.location.pathname.includes('edit_consent')) {
               				$('#consent_body_div *').prop('disabled',false);
               		   		$('#save_consent_btn_div *').attr('disabled',false);
               			} 
               			if(document.getElementById('consent_sub_menu')) {
                   	   		document.getElementById('consent_sub_menu').className = 'panel-collapse collapse show'; // By default show consent & validate form
                   	   		$('#consent_sub_menu_icon').removeClass('fa-plus').addClass('fa-minus');
               			}
               			if(window.location.pathname.includes('verify_consent')) 
               			{	
                   	   		document.getElementById('consent_validate_div').style.display = '';
    	           	   		document.getElementById('save_consent_validate_div').style.display = '';
   	               	   		document.getElementById('save_consent_validate_btn').onclick = function() {validateFormFields('save_validate_consent_data',null,null,true)};
               			} 
               		} else if(window.location.pathname.includes('consent_withdrawn') || window.location.pathname.includes('remove_samples')) {
               	   		document.getElementById('consent_sub_menu').className = 'panel-collapse collapse show'; // By default show consent
               	   		$('#consent_sub_menu_icon').removeClass('fa-plus').addClass('fa-minus');
           			}
               		processWaitingButtonSpinner('END-PATIENT');
            		break;
            	case 'GET-ALL-CONSENTS-FROM-DB':
            		initialiseForm('CONSENTS-LIST', data);
            		break;
            	case 'GET-SINGLE-COMPLETED-ACTION-FROM-SESSION': case 'GET-SINGLE-COMPLETED-ACTION':
              		initialiseForm('ACTION',data);
            		break;
            	case 'DISCARD-INFECTION-RISK-FROM-SESSION-IRS':
               		initialiseForm('INFECTION-RISK', null);
            		break;
            	case 'ADD-NEW-AUDIT-SAMPLE-TO-SESSION':
               		document.getElementById('aud_sample_pid').value = data.aud_sample_pid;
            		break;
               	case 'ADD-NEW-INFECTION-RISK-TO-SESSION-IRS':
               		document.getElementById('infection_risk_id').value = data.infection_risk_id;
               		break;
               	case 'GET-FIRST-INFECTION-RISK-FROM-SESSION':
            		initialiseForm('INFECTION-RISK', data);
               		break;
               	case 'GET-SINGLE-AUDIT-SAMPLE-FROM-SESSION':
            		initialiseForm('AUDIT-SAMPLE', data);
        	   		document.getElementById('save_audit_sample_btn').style.display = 'none';
        	   		document.getElementById('add_audit_sample_btn').style.display = 'none';
            		document.getElementById('discard_audit_sample_btn').style.display = '';
        	   		document.getElementById('audit_sample_div').style.display = '';
        	   		break;
            	case 'GET-SINGLE-INFECTION-RISK-FROM-SESSION':
            		initialiseForm('INFECTION-RISK', data);
               		if(window.location.pathname.includes('view_infection_risk') || window.location.pathname.includes('view_searched_infection_risk')) {
            	   		document.getElementById('save_all_btn').style.display = 'none';
            	   		document.getElementById('edit_patient_btn').style.display = '';
            	   		document.getElementById('edit_consent_btn').style.display = 'none';
               		} else if(window.location.pathname.includes('add_infection_risk')) {
            	   		document.getElementById('save_all_btn').style.display = '';
            	   		document.getElementById('edit_patient_btn').style.display = 'none';
            	   		document.getElementById('edit_consent_btn').style.display = '';
               		}
               		if(window.location.pathname.includes('verify_imported_consent')) {
            	   		document.getElementById('save_infection_risk_btn').style.display = '';
               		} else {
            			if (document.getElementById('consent_access').value.toLowerCase().includes('edit')) {
                	   		document.getElementById('save_infection_risk_btn').style.display = 'none';
                	   		document.getElementById('edit_infection_risk_btn').style.display = '';
                	   		document.getElementById('add_another_infection_risk_btn').style.display = 'none';
                	   		document.getElementById('discard_infection_risk_btn').style.display = 'none';
            			} 
            			$('#infection_risk_body_div *').prop('disabled',true);
               		}
        	   		document.getElementById('infection_risk_body_div').style.display = '';
            		break;
            	case 'GET-ALL-INFECTION-RISKS-FROM-DB-SESSION': case 'GET-ALL-INFECTION-RISKS-FROM-SESSION':
                	if(document.getElementById('whichPageToShow').value.toLowerCase().includes('infection_risk') 
                			|| document.getElementById('whichPageToShow').value.toLowerCase().includes('pat_con_ir_val')) {
                  		initialiseForm('INFECTION-RISK-CHECK-BOXES', data);
                  		if(window.location.pathname.includes('verify_imported_consent')) {
                  			$('.prev_infection_chk_boxes').prop('checked', true);
                  			processUserSelection($('.prev_infection_chk_boxes'));
                  		}
                		processWaitingButtonSpinner('END-INFECTION-RISK');
                	} else if(document.getElementById('whichPageToShow').value.toLowerCase().includes('patient')) {
                   		initialiseForm('INFECTION-RISK-LIST', data);
                	} else if(document.getElementById('whichPageToShow').value.toLowerCase().includes('pat_ir')) {
                   		initialiseForm('INFECTION-RISK', data[0]);
                	}
            		processWaitingButtonSpinner('END-PATIENT');
            		break;
            	}
            },    
            error : function(e) {    
          	 	console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
            }    
        });    

		switch(whatToProcess) {
		case 'REMOVE-FILE':
	   		document.getElementById(nameOfInputbox[0] + '_label').innerHTML = '';
	   		document.getElementById(nameOfInputbox[0]).value = '';
			hideAndShowContainer(document.getElementById(nameOfInputbox[0] + '_label'));
			break;
		}
		
		break;
    
	case 'PATIENT': 

	    if(window.location.pathname.includes('add_patient') && $('#patient_id').val() > 0) { // Don't unnecessary search for patient
	    	return false;
	    }

	    switch(nameOfInputbox[0]) {
	    case 'nhs_number': case 'patient_id': // Search for NHS number or patient_id only
		    switch(nameOfInputbox[0]) {
		    case 'nhs_number': 
		    	if ($('#nhs_number').val() == '') {
		    		return false;
		    	}
		    	break;
		    }
	    	break;
	    case 'patient_surname': // Search 2 out of 3 fields
	    	if ($('#patient_surname').val() != '' && $('#date_of_birth').val() != '') {
	    		nameOfInputbox.push('date_of_birth');
	    		valueOfInputbox.push($('#date_of_birth').val());
	    	} else if ($('#patient_surname').val() != '' && $('#hospital_number').val() != '') {
	    		nameOfInputbox.push('hospital_number');
	    		valueOfInputbox.push($('#hospital_number').val());
	    	} else if ($('#patient_surname').val() != '' && $('#select_age').val() != '') {
	    		nameOfInputbox.push('age');
	    		valueOfInputbox.push($('#select_age').val());
	    	} else {
	    		return false;
	    	}
	    	break;
	    case 'select_age':
	    	if ($('#select_age').val() != '' && $('#patient_surname').val() != '') {
	    		nameOfInputbox.push('patient_surname');
	    		valueOfInputbox.push($('#patient_surname').val());
	    	} 
	    	else {
	    		return false;
	    	}
	    	break;
	    case 'date_of_birth':
	    	if ($('#date_of_birth').val() != '' && $('#patient_surname').val() != '') {
	    		nameOfInputbox.push('patient_surname');
	    		valueOfInputbox.push($('#patient_surname').val());
	    	} else if ($('#date_of_birth').val() != '' && $('#hospital_number').val() != '') {
	    		nameOfInputbox.push('hospital_number');
	    		valueOfInputbox.push($('#hospital_number').val());
	    	}
	    	else {
	    		return false;
	    	}
	    	break;
	    case 'hospital_number':
	    	if ($('#hospital_number').val() != '' && $('#patient_surname').val() != '') {
	    		nameOfInputbox.push('patient_surname');
	    		valueOfInputbox.push($('#patient_surname').val());
	    	} else if ($('#hospital_number').val() != '' && $('#date_of_birth').val() != '') {
	    		nameOfInputbox.push('date_of_birth');
	    		valueOfInputbox.push($('#date_of_birth').val());
	    	}
	    	else {
	    		return false;
	    	}
	    	break;
	    }   
	    
    	$.ajax({    
	     type : 'Get',     
	     url : 'processPatientConsentInfectionRisk.html',     
         data : 'whatToProcess=' + whatToProcess + '&nameOfInputbox=' + nameOfInputbox + '&valueOfInputbox=' + valueOfInputbox 
     			+ '&whichDepartment=' + this_department,
	     dataType : 'json',
	     success : function(patient_data) {
    		 if (giveConfirmPrompt == true) {
    		 	if(patient_data != null) {
	    		 	var confirmMsg = '';
    			 	if(patient_data.locked_description.length > 0) {
    			 		processModalPopUp('CUSTOM-ALERT','LOCKED PATIENT','Patient with Surname [' + patient_data.patient_surname + ']'
	    		    			+ ' already exists, but is locked by another user [' + patient_data.locked_description + ']','DISMISS',patient_data);
    			 	} else if(nameOfInputbox[0] == 'select_age') {
    			 		processModalPopUp('CUSTOM-ALERT','EXISTING PATIENT','Patient with Surname [' + patient_data.patient_surname + '] and Age [' + patient_data.age + ']'
					    		+ ' already exists. Click OK to populate the data or click CANCEL to carry on adding a new patient.','YES-NO',patient_data);
    			 	} else if(nameOfInputbox[0] == 'nhs_number' || nameOfInputbox.length >= 2) { 
    			 		processModalPopUp('CUSTOM-ALERT','EXISTING PATIENT','Patient with NHS number [' + patient_data.nhs_number + '], Surname [' + patient_data.patient_surname 
    			 				+ '], Date Of Birth [' + patient_data.date_of_birth + '] and MRN [' + patient_data.hospital_number + ']'
    			 				+ ' already exists. Click YES to populate the data or click NO to carry on adding a new patient.','YES-NO',patient_data);
	    		 	}
    		 	}
    		 }
    		 else {
    			 initialiseForm('POPULATE-EXISTING-PATIENT',patient_data);
    		 }

	     },    
	     error : function(e) {    
       	 	 console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
	     }    
	    });
    	
    	break;
    
	}
}    	

function addItemsToList(whatToProcess, combo_box, dataToProcess, myCounter)
{
	switch (whatToProcess) {
	case 'POPULATE-BACK-TO-ADVANCED-SEARCH-PAGE': case 'POPULATE-BACK-TO-BASIC-SEARCH-PAGE':

		var anchor = document.createElement('a');
		anchor.style.fontSize = '20px';
		switch (whatToProcess) {
		case 'POPULATE-BACK-TO-ADVANCED-SEARCH-PAGE':
			anchor.setAttribute('href', 'previous_advanced_search_result_page');
			break;
		case 'POPULATE-BACK-TO-BASIC-SEARCH-PAGE':
			anchor.setAttribute('href', 'previous_basic_search_result_page');
			break;
		}
		anchor.innerText =  '<- Back to search result';
		
		$('#previous_search_result_page_div').empty();
		$('#previous_search_result_page_div').append(anchor);
		
		break;
	
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATABASE_ID': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-SECONDARY_ID':
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-VOLUNTEER': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATE_OF_BIRTH':
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-AGE': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-INFECTION_RISK_EXIST':
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATE_OF_CONSENT': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-CONSENT_TAKEN_BY':
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DIGITAL_CF_ATTACHMENT': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-WITHDRAWN':
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-VALIDATED_CONSENTS': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-IMPORTED_CONSENTS':
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-FINALISED_CONSENTS': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-SAM_COLL_BEFORE_SEP_2006':
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-LOCATION': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-FORM_TYPE': 
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-FORM_VERSION': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-SAMPLES_CONSENTED_TO':
	case 'POPULATE-ADVANCED-SEARCH-OPTIONS-CONSENT_EXCLUSIONS':
		
		var table,tbody,thead,row,cell,select,select2,option,header_text,loop_end;
		
		if(!document.getElementById('advanced_search_options_table')) {
	    	table = document.createElement('table');
	    	table.id = 'advanced_search_options_table';
	    	table.className = 'table table-striped table-bordered';
			tbody = document.createElement('tbody');
			table.appendChild(tbody);
			combo_box.appendChild(table);
			document.body.style = 'overflow-x: hidden;';
		} else {
			tbody = document.getElementById('advanced_search_options_table');
		}
		
		if(tbody.rows.length <= 0) {
			thead = table.createTHead();
			thead.style = 'height: 50px; background-color: #2E008B; color: white; font-size: 15px;';
			row = thead.insertRow(tbody.rows.length);
			for (var j = 0; j <= 3; j++) {
				cell = row.insertCell(j);
			    switch (j) {
				case 0:
					cell.innerHTML = 'Open Parenthesis';
					break;
				case 1:
					cell.innerHTML = 'Operands';
					break;
				case 2:
					cell.innerHTML = 'Selected Search Options';
					break;
				case 3:
					cell.innerHTML = 'Close Parenthesis';
					break;
				}
				cell.style = 'border: 1px solid';
			}
		}
		
		row = tbody.insertRow(tbody.rows.length);

		cell = row.insertCell(0);
		select = document.createElement('select');
		select.id = 'open_bracket_' + tbody.rows.length;
		select.className = 'browser-default custom-select custom-select-sm';
		for(var i=0;i<2;i++) {
			option = document.createElement('option');
			switch (i) {
			case 0:
				option.value = '';
			    option.text = '';
				break;
			case 1:
				option.value = '(';
			    option.text = '(';
				break;
			}
			select.style = 'width:60px;';
			select.appendChild(option);
		}
		cell.appendChild(select);
		cell.style = 'width:1px;white-space:nowrap;';

		cell = row.insertCell(row.cells.length);
		if(tbody.rows.length > 1) {
			select2 = document.createElement('select');
			select2.className = 'browser-default custom-select custom-select-sm';
			for(var i=0;i<2;i++) {
				option = document.createElement('option');
				switch (i) {
				case 0:
					option.value = 'and';
				    option.text = 'And';
					break;
				case 1:
					option.value = 'or';
				    option.text = 'Or';
					break;
				}
				select2.appendChild(option);
			}
			select2.style = 'width:60px;';
			cell.appendChild(select2);
		}
		cell.style = 'width:1px;white-space:nowrap;';
		
		switch (whatToProcess) {
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-VOLUNTEER': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-WITHDRAWN': 
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DIGITAL_CF_ATTACHMENT': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-VALIDATED_CONSENTS': 
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-IMPORTED_CONSENTS': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-FINALISED_CONSENTS':
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-SAM_COLL_BEFORE_SEP_2006':
			select = document.createElement('select');
			select.className = 'browser-default custom-select custom-select-sm';
		    header_text = document.createElement('label');
			loop_end = 2;
			switch (whatToProcess) {
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-VOLUNTEER':
				select.id = 'search_volunteer_' + tbody.rows.length;
				header_text.innerHTML = 'Volunteer: ';
				break;
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-WITHDRAWN':
				select.id = 'search_withdrawn_' + tbody.rows.length;
				header_text.innerHTML = 'Withdrawn: ';
				break;
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-SAM_COLL_BEFORE_SEP_2006':
				select.id = 'search_sam_coll_before_sep_2006_' + tbody.rows.length;
				header_text.innerHTML = 'Sample Sep 2006: ';
				break;
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DIGITAL_CF_ATTACHMENT':
				select.id = 'search_digital_cf_attachment_' + tbody.rows.length;
				header_text.innerHTML = 'Digital CF Attachment: ';
				loop_end = 1;
				break;
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-VALIDATED_CONSENTS':
				select.id = 'search_validated_consents_' + tbody.rows.length;
				header_text.innerHTML = 'Validated Consents: ';
				loop_end = 1;
				break;
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-IMPORTED_CONSENTS':
				select.id = 'search_imported_consents_' + tbody.rows.length;
				header_text.innerHTML = 'Imported Consents: ';
				loop_end = 1;
				break;
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-FINALISED_CONSENTS':
				select.id = 'search_finalised_consents_' + tbody.rows.length;
				header_text.innerHTML = 'Finalised Consents: ';
				loop_end = 1;
				break;
			}
			for(var i=0;i<=loop_end;i++) {
				option = document.createElement('option');
				switch (i) {
				case 0:
					option.value = '';
				    option.text = '';
					break;
				case 1:
					option.value = 'yes';
				    option.text = 'Yes';
					break;
				case 2:
					option.value = 'no';
				    option.text = 'No';
					break;
				}
			    select.appendChild(option);
			}
			header_text.htmlFor = select.id;
			cell = row.insertCell(row.cells.length);
			cell.appendChild(header_text).appendChild(select);
			cell.style = 'width:1px;white-space:nowrap;';
			if(tbody.rows.length > 1) {
				select2.id = 'and_or_' + tbody.rows.length;
			}
			break;
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATE_OF_BIRTH': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATE_OF_CONSENT':
			cell = row.insertCell(row.cells.length);
			cell.style = 'width:1px;white-space:nowrap;';
			for(var i=0;i<=1;i++) {
			    header_text = document.createElement('label');
			    option = document.createElement('input');
			    switch (i) {
				case 0:
					switch (whatToProcess) {
					case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATE_OF_BIRTH':
					    option.id = 'search_date_of_birth_start_' + tbody.rows.length;
						break;
					case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATE_OF_CONSENT':
					    option.id = 'search_date_of_consent_start_' + tbody.rows.length;
						break;
					}
					header_text.innerHTML = 'Start Range: ';
					break;
				case 1:
					switch (whatToProcess) {
					case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATE_OF_BIRTH':
					    option.id = 'search_date_of_birth_end_' + tbody.rows.length;
						break;
					case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATE_OF_CONSENT':
					    option.id = 'search_date_of_consent_end_' + tbody.rows.length;
						break;
					}
					header_text.innerHTML = 'End Range: ';
					break;
				}
			    option.type = 'date';
			    option.className = 'form-control form-control-sm floatlabel validate_this_date';
				header_text.htmlFor = option.id;
				cell.appendChild(header_text).appendChild(option);
			}
			if(tbody.rows.length > 1) {
				select2.id = 'and_or_' + tbody.rows.length;
			}
			break;
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-AGE':
			cell = row.insertCell(row.cells.length);
			cell.style = 'width:5px;white-space:nowrap;';
			for(var i=1; i<=2; i++) {
				select = document.createElement('select');
				select.className = 'browser-default custom-select custom-select-sm';
				select.style = 'width:60px;margin-right:20px;';
			    header_text = document.createElement('label');
			    switch (i) {
				case 1:
					select.id = 'search_age_start_' + tbody.rows.length;
					header_text.innerHTML = 'Age Start Range: ';
					break;
				case 2:
					select.id = 'search_age_end_' + tbody.rows.length;
					header_text.innerHTML = 'Age End Range: ';
					break;
				}
				for(var j=-1;j<=110;j++) {
					option = document.createElement('option');
					if(j<0) {
						option.value = '';
					    option.text = '';
					} else {
						option.value = j;
					    option.text = j;
					}
				    select.appendChild(option);
				}
				header_text.htmlFor = select.id;
				cell.appendChild(header_text).appendChild(select);
			}
			if(tbody.rows.length > 1) {
				select2.id = 'and_or_' + tbody.rows.length;
			}
			break;
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-INFECTION_RISK_EXIST':
			select = document.createElement('select');
		    header_text = document.createElement('label');
			select.id = 'search_infection_risk_exist_' + tbody.rows.length;
			header_text.innerHTML = 'Infection Risk Exist: ';
			for(var i=1;i<=5;i++) {
				option = document.createElement('option');
				switch (i) {
				case 1:
					option.value = 'No (Tests = Negative)';
					break;
				case 2:
					option.value = 'Yes (Tests = Positive)';
					break;
				case 3:
					option.value = 'No infection risks recorded';
					break;
				case 4:
					option.value = 'Unknown - possible risk';
					break;
				case 5:
					option.value = 'Not checked (imported)';
					break;
				}
				option.text = option.value;
			    select.options.add(option);
			}
			header_text.htmlFor = select.id;
			cell = row.insertCell(row.cells.length);
			select.multiple = true;
			$(select).addClass("selectpicker");
			cell.style = 'width:5px;white-space:nowrap;';
			cell.appendChild(header_text).appendChild(select);
			$(select).attr('data-actions-box',true);
			$(select).attr('data-container','body');
			$(select).attr('data-selected-text-format','count');
			$(select).selectpicker('val', null);
		    $(select).selectpicker('refresh');
			if(tbody.rows.length > 1) {
				select2.id = 'and_or_' + tbody.rows.length;
			}
			break;
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATABASE_ID': case 'POPULATE-ADVANCED-SEARCH-OPTIONS-SECONDARY_ID':
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-CONSENT_TAKEN_BY':
			option = document.createElement('input');
		    option.type = 'text';
		    header_text = document.createElement('label');
			switch (whatToProcess) {
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-CONSENT_TAKEN_BY':
			    option.id = 'search_consent_taken_by_' + tbody.rows.length;
				header_text.innerHTML = 'Consent Taken By: ';
				break;
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-DATABASE_ID': 
			    option.id = 'search_database_id_' + tbody.rows.length;
				header_text.innerHTML = 'Database ID: ';
				break;
			case 'POPULATE-ADVANCED-SEARCH-OPTIONS-SECONDARY_ID':
			    option.id = 'search_secondary_id_' + tbody.rows.length;
				header_text.innerHTML = 'Secondary ID: ';
				break;
			}
			header_text.htmlFor = option.id;
			cell = row.insertCell(row.cells.length);
			cell.appendChild(header_text).appendChild(option);
			cell.style = 'width:5px;white-space:nowrap;';
			if(tbody.rows.length > 1) {
				select2.id = 'and_or_' + tbody.rows.length;
			}
			break;
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-LOCATION':
			select = document.createElement('select');
		    header_text = document.createElement('label');
			select.id = 'search_location_' + tbody.rows.length;
			header_text.innerHTML = 'Location: ';
			dataToProcess.forEach(function(loc,index,arr){
				option = document.createElement('option');
				option.value = loc.loc_id;
				option.text = loc.loc_name;
			    select.options.add(option);
			});
			header_text.htmlFor = select.id;
			cell = row.insertCell(row.cells.length);
			select.multiple = true;
			$(select).addClass("selectpicker");
			cell.style = 'width:5px;white-space:nowrap;';
			cell.appendChild(header_text).appendChild(select);
			$(select).attr('data-actions-box',true);
			$(select).attr('data-container','body');
			$(select).attr('data-selected-text-format','count');
			$(select).selectpicker('val', null);
		    $(select).selectpicker('refresh');
			if(tbody.rows.length > 1) {
				select2.id = 'and_or_' + tbody.rows.length;
			}
			break;
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-FORM_TYPE':
			select = document.createElement('select');
		    header_text = document.createElement('label');
			select.id = 'search_form_type_' + tbody.rows.length;
			header_text.innerHTML = 'Form Type: ';
			dataToProcess.forEach(function(form,index,arr){
				option = document.createElement('option');
				option.value = form.consent_form_type_id;
				option.text = form.description;
			    select.options.add(option);
			});
			header_text.htmlFor = select.id;
			cell = row.insertCell(row.cells.length);
			select.multiple = true;
			$(select).addClass("selectpicker");
			cell.style = 'width:5px;white-space:nowrap;';
			cell.appendChild(header_text).appendChild(select);
			$(select).attr('data-actions-box',true);
			$(select).attr('data-container','body');
			$(select).attr('data-selected-text-format','count');
			$(select).selectpicker('val', null);
		    $(select).selectpicker('refresh');
			if(tbody.rows.length > 1) {
				select2.id = 'and_or_' + tbody.rows.length;
			}
			break;
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-FORM_VERSION':
			select = document.createElement('select');
		    header_text = document.createElement('label');
			select.id = 'search_form_versions_' + tbody.rows.length;
			header_text.innerHTML = 'Form Version: ';
			dataToProcess.forEach(function(form,index,arr){
				option = document.createElement('option');
				option.value = form.form_version_id;
				option.text = form.description;
			    select.options.add(option);
			});
			header_text.htmlFor = select.id;
			cell = row.insertCell(row.cells.length);
			select.multiple = true;
			$(select).addClass("selectpicker");
			cell.style = 'width:5px;white-space:nowrap;';
			cell.appendChild(header_text).appendChild(select);
			$(select).attr('data-actions-box',true);
			$(select).attr('data-container','body');
			$(select).attr('data-selected-text-format','count');
			$(select).selectpicker('val', null);
		    $(select).selectpicker('refresh');
			if(tbody.rows.length > 1) {
				select2.id = 'and_or_' + tbody.rows.length;
			}
			break;
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-SAMPLES_CONSENTED_TO':
			select = document.createElement('select');
		    header_text = document.createElement('label');
			select.id = 'search_samples_consented_to_' + tbody.rows.length;
			header_text.innerHTML = 'Samples Consented To: ';
			dataToProcess.forEach(function(sample,index,arr){
				option = document.createElement('option');
				option.value = sample.consent_sample_type_id;
				option.text = sample.description;
			    select.options.add(option);
			});
			header_text.htmlFor = select.id;
			cell = row.insertCell(row.cells.length);
			select.multiple = true;
			$(select).addClass("selectpicker");
			cell.style = 'width:5px;white-space:nowrap;';
			cell.appendChild(header_text).appendChild(select);
			$(select).attr('data-actions-box',true);
			$(select).attr('data-container','body');
			$(select).attr('data-selected-text-format','count');
			$(select).selectpicker('val', null);
		    $(select).selectpicker('refresh');
			if(tbody.rows.length > 1) {
				select2.id = 'and_or' + tbody.rows.length;
			}
			break;
		case 'POPULATE-ADVANCED-SEARCH-OPTIONS-CONSENT_EXCLUSIONS':
			select = document.createElement('select');
		    header_text = document.createElement('label');
			select.id = 'search_consent_exclusions_' + tbody.rows.length;
			header_text.innerHTML = 'Consent Exclusion: ';
			dataToProcess.forEach(function(ce,index,arr){
				option = document.createElement('option');
				option.value = ce.consent_term_id;
				option.text = ce.description;
			    select.options.add(option);
			});
			header_text.htmlFor = select.id;
			cell = row.insertCell(row.cells.length);
			select.multiple = true;
			$(select).addClass("selectpicker");
			cell.style = 'width:5px;white-space:nowrap;';
			cell.appendChild(header_text).appendChild(select);
			$(select).attr('data-actions-box',true);
			$(select).attr('data-container','body');
			$(select).attr('data-selected-text-format','count');
			$(select).selectpicker('val', null);
		    $(select).selectpicker('refresh');
			if(tbody.rows.length > 1) {
				select2.id = 'and_or_' + tbody.rows.length;
			}
			break;
		}

		cell = row.insertCell(row.cells.length);
		select = document.createElement('select');
		select.id = 'close_bracket_' + tbody.rows.length;
		select.className = 'browser-default custom-select custom-select-sm';
		for(var i=0;i<2;i++) {
			option = document.createElement('option');
			switch (i) {
			case 0:
				option.value = '';
			    option.text = '';
				break;
			case 1:
				option.value = ')';
			    option.text = ')';
				break;
			}
			select.style = 'width:60px;';
			select.appendChild(option);
		}
		cell.appendChild(select);
		cell.style = 'width:1px;white-space:nowrap;';

		cell = row.insertCell(row.cells.length);
		option = document.createElement('button');
		option.name = 'search_criteria_delete_row';
		option.id = 'search_criteria_delete_row_' + tbody.rows.length;
	    option.innerHTML = 'Delete';
	    option.style = 'background-color:#d62828;color:#FEFEFE;';
	    option.className = 'btn btn-md';
	    option.onclick = function(){processUserSelection(this);};
		cell.appendChild(option);
		cell.style = 'width:1px;white-space:nowrap;';
		
		break;

	case 'POPULATE-ADVANCED-SEARCH': case 'POPULATE-PREVIOUS-ADVANCED-SEARCH':
		
		var table,text,count=0;
		var cols=[],val=[],pat_ids=[],exp_cols=[];
		
    	table = document.createElement('table');
    	table.id = 'search_result_table';
    	table.className = 'table table-striped table-bordered';
    	table.style = 'font-family:Consolas;text-transform:uppercase;';
    	if(document.getElementById('export_search_result').value.toLowerCase().includes('full')
        		|| document.getElementById('export_search_result').value.toLowerCase().includes('tech_limited')) {
    		text = document.createElement('h6');
    		text.style = 'text-align:center;color:red;';
    		text.innerHTML = "Before using the 'EXPORT TO EXCEL' button, please remember to tick " +
    				"'ASK WHERE TO SAVE EACH FILE BEFORE DOWNLOADING' in your browser settings. " +
    				"Failing to do so will export the search result in an UNSECURE local default download directory"
    		combo_box.appendChild(text);
    	}
		combo_box.appendChild(table);

		if(!document.getElementById('consent_access').value.toLowerCase().includes('view_restricted')) {
	    	text = {title: 'Primary ID'};
		    cols.push(text);
		}
		
		switch (whatToProcess) {
		case 'POPULATE-ADVANCED-SEARCH': 
		    $('#select_search_result_columns option:selected').each(function() {
		    	text = {title: $(this).val().replaceAll('_',' ').toUpperCase()};
		    	cols.push(text);
		    	if(document.getElementById('export_search_result').value.toLowerCase().includes('tech_limited')) {
			    	if($(this).val().toLowerCase().includes('database_id') || $(this).val().toLowerCase().includes('secondary_id')
			    			|| $(this).val().toLowerCase().includes('date_of_consent') || $(this).val().toLowerCase().includes('consent_type')
			    			|| $(this).val().toLowerCase().includes('consent_exclusions') || $(this).val().toLowerCase().includes('number_of_infection_risks')) {
				    	exp_cols.push(cols.indexOf(text));
			    	}
		    	} else {
			    	exp_cols.push(exp_cols.length + 1);
		    	}
		    });
		    break;
		case 'POPULATE-PREVIOUS-ADVANCED-SEARCH':
		    var adv_search_cols_split = $('#advanced_search_selected_columns').val().split("|");
		    for (i=0;i<adv_search_cols_split.length;i++){
		    	text = {title: adv_search_cols_split[i].replaceAll('_',' ').toUpperCase()};
		    	cols.push(text);
		    	if(document.getElementById('export_search_result').value.toLowerCase().includes('tech_limited')) {
			    	if($(this).val().toLowerCase().includes('database_id') || $(this).val().toLowerCase().includes('secondary_id')
			    			|| $(this).val().toLowerCase().includes('date_of_consent') || $(this).val().toLowerCase().includes('consent_type')
			    			|| $(this).val().toLowerCase().includes('consent_exclusions') || $(this).val().toLowerCase().includes('number_of_infection_risks')) {
				    	exp_cols.push(cols.indexOf(text));
			    	}
		    	} else {
			    	exp_cols.push(exp_cols.length + 1);
		    	}
		    }
		    break;
		}

		if(!document.getElementById('consent_access').value.toLowerCase().includes('view_restricted')) {
		    text = {title: 'Access'};
		    cols.push(text);
	        if(document.getElementById('export_search_result').value.toLowerCase().includes('full')
	        		|| document.getElementById('export_search_result').value.toLowerCase().includes('tech_limited')) {
				table = $('#search_result_table').DataTable( {
					pageLength:5,
					'lengthMenu': [[3, 5, 10, -1], [3, 5, 10, 'Show All']],
			        columns: cols,
			        "columnDefs": [ {
			            "targets": -1,
			            "data": null,
			            "defaultContent": '<button style="background-color:#d62828;color:#FEFEFE;"' + 
		            	' type="button" class="btn btn-md" onclick="processUserSelection(this);">Patient Locked</button>'
			        }],
		            dom: 'lBfrtip',
	                buttons: [
	                    {
	                        extend: 'excelHtml5',
	                        title: 'Tissue Bank Search Result',
	                        text:'<i class="fas fa-file-excel"></i> Export to excel',
	    	                exportOptions: {
		                        columns: exp_cols
		                    },
	                    	action: function(e, dt, node, config) {
	            				processPatientConsentInfectionRisk('EXPORT-SEARCH-RESULT-LOG-TIMELINE',null,false);
	                    		$.fn.dataTable.ext.buttons.excelHtml5.action.call(dt.button(this), e, dt, node, config);
	                    	}
	                    }
	                ]
				});
	        } else {
				table = $('#search_result_table').DataTable( {
					pageLength:5,
					'lengthMenu': [[3, 5, 10, -1], [3, 5, 10, 'Show All']],
			        columns: cols,
			        "columnDefs": [ {
			            "targets": -1,
			            "data": null,
			            "defaultContent": '<button style="background-color:#d62828;color:#FEFEFE;"' + 
		            	' type="button" class="btn btn-md" onclick="processUserSelection(this);">Patient Locked</button>'
			        }],
				});
	        }
		} else {
			table = $('#search_result_table').DataTable( {
				pageLength:5,
				lengthMenu: [[3, 5, 10, -1], [3, 5, 10, 'Show All']],
		        columns: cols
			});
			
		}
		
		dataToProcess.forEach(function(text_to_use,index,arr){
			if(Array.isArray(text_to_use)) {
				text_to_use.forEach(function(split_text_to_use,split_index,split_arr) {
					if(split_text_to_use != null) {
						if(split_text_to_use.toString().split('-').length == 3 && split_text_to_use.toString().split('-')[0].length == 4 
								&& split_text_to_use.toString().split('-')[1].length == 2 && split_text_to_use.toString().split('-')[0].length == 2) {
							val.push(split_text_to_use.toString().split('-')[2] + '-' + split_text_to_use.toString().split('-')[1] 
								+ '-' + split_text_to_use.toString().split('-')[0]);
						} else {
							val.push(split_text_to_use);
						}
					} else {
						val.push('NULL');
					}
					if(split_index == 0) {
						pat_ids.push(split_text_to_use);
					}
				});
				val.push('');
				table.row.add(val);
				val = [];
			} else {
				if(split_text_to_use != null) {
					if(text_to_use.split('-').toString().length == 3 && text_to_use.toString().split('-')[0].length == 4 
							&& text_to_use.split('-')[1].toString().length == 2 && text_to_use.toString().split('-')[0].length == 2) {
						val.push(text_to_use.toString().split('-')[2] + '-' + text_to_use.toString().split('-')[1] + '-' + text_to_use.toString().split('-')[0]);
					} else {
						val.push(text_to_use);
					}
				} else {
					val.push('NULL');
				}
				count = count + 1;
				if(count == cols.length-1) {
					val.push('');
					table.row.add(val);
					count = 0;
					val = [];
				}
			}
		});
		
		table.rows().draw();
		
		if(!document.getElementById('consent_access').value.toLowerCase().includes('view_restricted')) {
	        table.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
				$(this.node()).find('button').attr('name','view_searched_patient');
				$(this.node()).find('button').html('View Patient');
				$(this.node()).find('button').css({'background-color': '#2E008B', 'color': '#FEFEFE'});
				$(this.node()).find('button').attr('id',pat_ids[rowIdx]);
				if(document.getElementById('consent_access').value.toLowerCase().includes('timeline')) {
					$(this.node()).find('button').prepend('<label><input style="background-color:#2E008B;color:#FEFEFE;"' + 
			            	' type="checkbox" id="view_patient_timeline_"' + pat_ids[rowIdx] + '></input> Show Timeline?</label><br>');
				}				
	        });			
		}
		
		break;
		
	case 'POPULATE-BASIC-SEARCH':

		var table,text,option,div,check,label;
    	table = document.createElement('table');
    	table.id = 'search_result_table';
    	table.className = 'table table-striped table-bordered';
    	table.style = 'font-family:Consolas;text-transform:uppercase;';
		combo_box.appendChild(table);
    	
		if(dataToProcess.search_result_view_type.toLowerCase() == 'view_restricted') {
			
			table = $('#search_result_table').DataTable( {
				pageLength:3,
				lengthMenu: [[3, 5, 10, -1], [3, 5, 10, 'Show All']],
		        columns: [
		            { title: 'Database ID' },
		            { title: 'Secondary ID' },
		            { title: 'Volunteer' },
		            { title: 'Age' },
		            { title: 'Old Pat ID' },
		            { title: 'Consent(s)' },
		            { title: 'Infection Risk(s)' }
		        ]
		    });
			
			dataToProcess.patients.forEach(function(pat,index,arr){
				table.row.add( [
		            pat.database_id.toUpperCase(),
		            pat.secondary_id,
		            pat.volunteer,
		            pat.age,
		            pat.old_pat_id,
		            pat.number_consents,
		            pat.number_infection_risks
		        ] ).draw();
			});
			
		} else {
			
			table = $('#search_result_table').DataTable( {
				pageLength:3,
				lengthMenu: [[3, 5, 10, -1], [3, 5, 10, 'Show All']],
		        columns: [
		            { title: 'Database ID' },
		            { title: 'Secondary ID' },
		            { title: 'Volunteer' },
		            { title: 'Firstname' },
		            { title: 'Surname' },
		            { title: 'DoB' },
		            { title: 'NHS' },
		            { title: 'Hospital Number' },
		            { title: 'Old Pat ID' },
		            { title: 'Consent(s)' },
		            { title: 'Withdrawn' },
		            { title: 'Infection Risk(s)' },
		            { title: 'Validated Consent(s)' },
		            { title: 'Imported' },
		            { title: 'Finalise Consent(s)' },
		            { title: 'Access' }
		        ],
		        "columnDefs": [ {
		            "targets": -1,
		            "data": null,
		            "defaultContent": '<button style="background-color:#d62828;color:#FEFEFE;"' + 
	            	' type="button" class="btn btn-sm" onclick="processUserSelection(this);">Patient Locked</button>'
		        }]		        
		    });
			
			dataToProcess.patients.forEach(function(pat,index,arr){
				
				if(pat.status.toLowerCase() == 'imported') {
				    text = 'Yes';
				} else {
				    text = 'No';
				}
            	option = '';
	            if(pat.date_of_birth) {
	            	option = pat.date_of_birth.split('-')[2] + '-' + pat.date_of_birth.split('-')[1] + '-' + pat.date_of_birth.split('-')[0]
	            }
				table.row.add([
		            pat.database_id.toUpperCase(),
		            pat.secondary_id,
		            pat.volunteer,
		            pat.patient_firstname,
		            pat.patient_surname,
		            option,
		            pat.nhs_number,
		            pat.hospital_number,
		            pat.old_pat_id,
		            pat.number_consents,
		            pat.withdrawn_count,
		            pat.number_infection_risks,
		            pat.number_validated_consents,
		            text,
		            pat.number_finalise_import_consents,
		            ''
		        ]).draw();
			});
			
	        table.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
				if(dataToProcess.patients[rowIdx].locked_description != '') {
					$(this.node()).find('button').attr('name','patient_locked_btn');
					$(this.node()).find('button').attr('id',dataToProcess.patients[rowIdx].locked_description);
					$(this.node()).find('button').html('Patient Locked');
					$(this.node()).find('button').css({"background-color": "#d62828", "color": "#FEFEFE"});
					$(this.node()).find('div').hide();
				} else if(dataToProcess.access_data.toLowerCase().includes('patient')) {
					$(this.node()).find('button').attr('name','view_searched_patient');
					$(this.node()).find('button').html('View Patient');
					$(this.node()).find('button').attr('id',dataToProcess.patients[rowIdx].patient_id);
					$(this.node()).find('button').css({"background-color": "#2E008B", "color": "#FEFEFE"});
					if(dataToProcess.access_data.toLowerCase().includes('timeline')) {
						$(this.node()).find('button').prepend('<label><input style="background-color:#2E008B;color:#FEFEFE;" class="form-check-input"' +
				            	' type="checkbox" id="view_patient_timeline_"' + dataToProcess.patients[rowIdx].patient_id + '></input>Show Timeline?</label>');
					}				
				}
	        });			
		}
		
		break;
		
	case 'ACTION-TYPE':
		
    	var blank_option = document.createElement('option');

    	blank_option.setAttribute('value', '');
    	blank_option.innerHTML = '';
		combo_box.appendChild(blank_option);
    	
		if(window.location.pathname.includes('verify_consent') || window.location.pathname.includes('verify_imported_consent')) {
    		
			var no_action_option = document.createElement('option');
			
			no_action_option.setAttribute('value', 'no_action_to_send');
			no_action_option.innerHTML = 'No Action to Send';
    		combo_box.appendChild(no_action_option);
    		
			var reapproach_patient_option = document.createElement('option');
			reapproach_patient_option.setAttribute('value', 'reapproach_patient');
			reapproach_patient_option.innerHTML = 'Query with TBAO';
    		combo_box.appendChild(reapproach_patient_option);
    		
			var query_patient_option = document.createElement('option');
			query_patient_option.setAttribute('value', 'query_patient');
			query_patient_option.innerHTML = 'Query to Operations Manager';
    		combo_box.appendChild(query_patient_option);
    		
		} else if(window.location.pathname.includes('reapproach_patient') || window.location.pathname.includes('query_patient')) {
			
			var verify_consent_option = document.createElement('option');
			verify_consent_option.setAttribute('value', 'verify_consent');
			verify_consent_option.innerHTML = 'Data Officer To Validate Consent';
    		combo_box.appendChild(verify_consent_option);
	    		
		} else {
			
			var option = document.createElement('option');
			
    		option.setAttribute('value', 'no_action_to_send');
    		option.innerHTML = 'No Action to Send';
    		combo_box.appendChild(option);
			
		}
			
		break;
	
	case 'IMPORT-CONSENTS':

		if (dataToProcess)
		{
			dataToProcess.forEach(function(item,index,arr){
			  var chkbox_btn = document.createElement('input');
			  chkbox_btn.type = 'checkbox';
			  chkbox_btn.id = item.consent_which_department + '_radio_' + item.consent_id;
			  chkbox_btn.value = item.consent_id;
			  chkbox_btn.name = 'import_consent_radio';
			  chkbox_btn.style = 'margin-right:10px;';
			  chkbox_btn.onclick = function(){processUserSelection(this);};

			  var label = document.createElement('label');
			  label.htmlFor = chkbox_btn.id;
			  label.innerHTML = item.description;
			  
	     	  combo_box.appendChild(chkbox_btn);
	     	  combo_box.appendChild(label);
	     	  combo_box.appendChild(document.createElement('br'));
			  
			});
		}
		
		break;
		
	case 'AUDIT-CONSENTS':
		
		if (dataToProcess)
		{
			dataToProcess.forEach(function(item,index,arr){
			  var chkbox_btn = document.createElement('input');
			  chkbox_btn.type = 'checkbox';
			  chkbox_btn.id = item.consent_which_department + '_radio_' + item.consent_id;
			  chkbox_btn.value = item.consent_id;
			  chkbox_btn.name = 'audit_consent_radio';
			  chkbox_btn.style = 'margin-right:10px;';
			  chkbox_btn.onclick = function(){processUserSelection(this);};

			  var label = document.createElement('label');
			  label.htmlFor = chkbox_btn.id;
			  if (item.description.toUpperCase().includes(' (PARTIALLY AUDITED)')) {
				  label.innerHTML = item.description.replace(' (PARTIALLY AUDITED)','') + " <span style='color: red;'>(PARTIALLY AUDITED)</span>";
			  } else  {
				  label.innerHTML = item.description;
			  }
			  
	     	  combo_box.appendChild(chkbox_btn);
	     	  combo_box.appendChild(label);
	     	  combo_box.appendChild(document.createElement('br'));
			  
			});
		}
		
		break;
		
	case 'VIEW-TIMELINE':
		
		if (dataToProcess)
		{
			dataToProcess.forEach(function(item,index,arr){
			  var chkbox_btn = document.createElement('input');
			  chkbox_btn.type = 'checkbox';
			  chkbox_btn.id = item.timeline_id;
			  chkbox_btn.value = item.timeline_id;
			  chkbox_btn.name = 'view_timeline_radio';
			  chkbox_btn.style = 'margin-right:10px;';
			  chkbox_btn.onclick = function(){processUserSelection(this);};

			  var label = document.createElement('label');
			  label.setAttribute('for',chkbox_btn.id); // this corresponds to the checkbox id
			  label.appendChild(chkbox_btn);
			  label.appendChild(document.createTextNode(item.description));
			  
	     	  combo_box.appendChild(label);
	     	  combo_box.appendChild(document.createElement('br'));
			  
			});
		}
		
		break;
		
	case 'ACTIVE-ACTIONS': case 'COMPLETED-ACTIONS': case 'LOCKED-ACTIONS': case 'LOCKED-PATIENTS':
		
		if (dataToProcess)
		{
			dataToProcess.forEach(function(item,index,arr){

			  var chkbox_btn = document.createElement('input');
			  chkbox_btn.type = 'checkbox';
			  switch (whatToProcess) {
			  case 'ACTIVE-ACTIONS': case 'COMPLETED-ACTIONS': case 'LOCKED-ACTIONS':
				  chkbox_btn.id = item.action_which_department + '_' + item.action_type + '_radio_' + item.action_id;
				  chkbox_btn.value = item.action_id;
				  break;
			  case 'LOCKED-PATIENTS':
				  chkbox_btn.id = item.patient_which_department + '_patient_radio_' + item.patient_id;
				  chkbox_btn.value = item.patient_id;
				  break;
			  }
			  switch (whatToProcess) {
			  case 'ACTIVE-ACTIONS': 
				  chkbox_btn.name = 'user_action_radio';
				  break;
			  case 'COMPLETED-ACTIONS':
				  chkbox_btn.name = 'completed_task_radio';
				  break;
			  case 'LOCKED-ACTIONS': 
				  chkbox_btn.name = 'locked_task_radio';
				  break;
			  case 'LOCKED-PATIENTS':
				  chkbox_btn.name = 'locked_patient_radio';
				  break;
			  }
			  chkbox_btn.style = 'margin-right:10px;';
			  chkbox_btn.onclick = function(){processUserSelection(this);};

			  var label = document.createElement('label');
			  label.htmlFor = chkbox_btn.id;
			  switch (whatToProcess) {
			  case 'ACTIVE-ACTIONS': case 'COMPLETED-ACTIONS':
				  label.innerHTML = item.description;
				  break;
			  case 'LOCKED-ACTIONS': case 'LOCKED-PATIENTS':
				  label.innerHTML = item.locked_description;
				  break;
			  }
	     	  combo_box.appendChild(chkbox_btn);
	     	  combo_box.appendChild(label);
	     	  combo_box.appendChild(document.createElement('br'));
			  
			});
		}
		
		break;
		
	case 'ACTION':
		
		var action_list_text = '';
		
		if (dataToProcess)
		{
			dataToProcess.forEach(function(item,index,arr){
				
				var li = document.createElement('li');	    	
		    	var input = document.createElement('input');
		    	var label = document.createElement('label');
		    	
		    	input.setAttribute('type', 'checkbox');
		    	input.setAttribute('value', item.action_id);
		    	input.setAttribute('class', 'prev_action_chk_boxes');
		    	input.setAttribute('name', 'prev_action_chk_boxes');
		    	input.setAttribute('onchange', 'processUserSelection(this);');

				myCounter = myCounter + 1;
				if(window.location.pathname.includes('user_actions')) {
					action_list_text =  myCounter + '. ' + item.description;
				} else {
					action_list_text =  myCounter + '. ';
					if(item.status.toLowerCase().includes('locked')) {
						action_list_text = action_list_text + 'Current Action. In progress. '
						label.style.fontWeight = 'bold';
					} else if(item.status.toLowerCase().includes('active')) {
						action_list_text = action_list_text + 'In progress. '
					} else if(item.status.toLowerCase().includes('completed')) {
						action_list_text = action_list_text + 'Completed. '
					}
					action_list_text = action_list_text + item.all_roles + ': ' 
						+ item.action_type.replace(/(^\w{1})|(\s{1}\w{1})/g, match => match.toUpperCase()).replace('_',' ') 
						+ '. Action sent: ' + item.created_date_time;
				}
	
				label.appendChild(input);
				if(window.location.pathname.includes('user_actions') && action_list_text.length > 100) {
					label.appendChild(document.createTextNode(' ' + action_list_text.substring(0,100) + '...'));
				} else {
					label.appendChild(document.createTextNode(' ' + action_list_text));
				}
				
				li.appendChild(label);
		    	
		    	combo_box.appendChild(li);

			});
		}
		
		break;
	
	case 'CONSENT': 
		
		var li = document.createElement('li');	    	
		var anchor = document.createElement('a');
		
		anchor.setAttribute('name', 'prev_consent_link');
		anchor.style.fontSize = '15px';
		if(window.location.pathname.includes('view_searched_patient') || window.location.pathname.includes('view_timeline')) {
			anchor.setAttribute('href', 'view_searched_consent?consent_id=' + dataToProcess.consent_id);
		} else {
			anchor.setAttribute('href', 'view_consent?consent_id=' + dataToProcess.consent_id);
		}
		if(dataToProcess.is_withdrawn.toLowerCase().includes('yes') && !document.getElementById('consent_withdrawn_access').value.toLowerCase().includes('edit')) {
			anchor.setAttribute('href', '#');
		}
		anchor.innerText =  myCounter + '. ';

		if(!dataToProcess.consent_deletion_date && dataToProcess.is_withdrawn.toLowerCase().includes('yes')) {
			anchor.innerText =  anchor.innerText + ' withdrawn on ' + dataToProcess.withdrawal_date;
		} else if(dataToProcess.sam_coll_before_sep_2006.toLowerCase().includes('yes')) {
			anchor.innerText =  anchor.innerText + ' sample collected before Sep 2006';
		} else {
			anchor.innerText =  anchor.innerText + dataToProcess.date_of_consent;
			if(dataToProcess.verbal_consent.toLowerCase().includes('yes')) {
				anchor.innerText = anchor.innerText + ', verbal';
				if(dataToProcess.verbal_consent_recorded) {
					anchor.innerText = anchor.innerText + ', ' + dataToProcess.verbal_consent_recorded;
				}
				if(dataToProcess.verbal_consent_recorded_by) {
					anchor.innerText = anchor.innerText + ', ' + dataToProcess.verbal_consent_recorded_by;
				}
			} else {
				if(dataToProcess.form_type) {
					anchor.innerText = anchor.innerText + ', ' + dataToProcess.form_type;
				}
				if(dataToProcess.form_version) {
					anchor.innerText = anchor.innerText + ', ' + dataToProcess.form_version.description;
				}
				if(dataToProcess.consent_type) {
					anchor.innerText = anchor.innerText + ', ' + dataToProcess.consent_type;
				}
			}
		}
		if (dataToProcess.is_validated.toLowerCase().includes('yes')) {
			anchor.innerText = anchor.innerText + ' (VALIDATED)'
		} else if (dataToProcess.is_finalised.toLowerCase().includes('yes') && dataToProcess.is_imported.toLowerCase().includes('yes')) {
			anchor.innerText = anchor.innerText + ' (IMPORTED)'
		} else if (dataToProcess.is_imported.toLowerCase().includes('yes')) {
			anchor.innerText = anchor.innerText + ' (IMPORTED)!'
		}
		
		li.appendChild(anchor);
		combo_box.appendChild(li);
		
		break;

	case 'SAMPLE-AUDIT-CHECK-BOXES':
		
    	var li = document.createElement('li');	    	
    	var input = document.createElement('input');
    	var label = document.createElement('label');

		input.setAttribute('type', 'checkbox');
    	input.setAttribute('value', dataToProcess.aud_sample_pid);
    	input.setAttribute('name', 'prev_sample_chk_boxes');
    	input.setAttribute('class', 'prev_sample_chk_boxes');
    	input.setAttribute('onchange', 'processUserSelection(this);');

    	label.innerHTML =  myCounter + '. ' + dataToProcess.aud_sample_id + ', ' + dataToProcess.sample_date;
    	
    	li.appendChild(input);
    	li.innerHTML += ' ';
    	li.appendChild(label);
    	
    	combo_box.appendChild(li);
		
		break;
		
	case 'INFECTION-RISK-LIST': case 'INFECTION-RISK-CHECK-BOXES': // case 'INFECTION-RISK-COMBO-BOX':

		var myDataToShow = '';
		switch (whatToProcess) {
    	case 'INFECTION-RISK-CHECK-BOXES': case 'INFECTION-RISK-LIST': // case 'INFECTION-RISK-COMBO-BOX':
    		if(dataToProcess != null && dataToProcess.infection_type != null) {
    	    	if(dataToProcess.infection_risk_exist.toLowerCase().includes('yes')) {
    	    		myDataToShow = 'Yes, ';
    	    	} else if (dataToProcess.infection_risk_exist.toLowerCase().includes('unknown')) {
    	    		myDataToShow = 'Unknown, ';
    	    	}
		    	if(dataToProcess.infection_type.description.length >= 12) {
		    		myDataToShow = myDataToShow + dataToProcess.infection_type.short_description + ', '; 
		    	} else {
		    		myDataToShow = myDataToShow + dataToProcess.infection_type.description + ', '; 
		    	}
    	    	if (dataToProcess.continued_risk.toLowerCase().includes('no')) {
    	    		myDataToShow = myDataToShow + dataToProcess.episode_start_date + ' & End: ' + dataToProcess.episode_finished_date;
    	    	} else if (dataToProcess.continued_risk.toLowerCase().includes('yes')) {
    	    		myDataToShow = myDataToShow + dataToProcess.episode_start_date + ' - continued risk'; 
    	    	}
        	} else if (dataToProcess != null && dataToProcess.infection_risk_exist) {
        		myDataToShow = dataToProcess.infection_risk_exist;
        	}
    		break;
    	}

    	if(myDataToShow) {
    		
        	switch (whatToProcess) {
        	case 'INFECTION-RISK-CHECK-BOXES':
        		
		    	var li = document.createElement('li');	    	
		    	var input = document.createElement('input');
		    	var label = document.createElement('label');

        		input.setAttribute('type', 'checkbox');
		    	input.setAttribute('value', dataToProcess.infection_risk_id);
		    	input.setAttribute('name', 'prev_infection_chk_boxes');
		    	input.setAttribute('class', 'prev_infection_chk_boxes');
		    	input.setAttribute('onchange', 'processUserSelection(this);');

		    	label.innerHTML =  myCounter + '. ' + myDataToShow;
		    	
		    	li.appendChild(input);
		    	li.innerHTML += ' ';
		    	li.appendChild(label);
		    	
		    	combo_box.appendChild(li);

        		break;
        		
        	case 'INFECTION-RISK-LIST':

        		var li = document.createElement('li');	    	
        		var anchor = document.createElement('a');
        		
        		if(window.location.pathname.includes('view_searched_patient') || window.location.pathname.includes('view_timeline')) {
        			anchor.setAttribute('href', 'view_searched_infection_risk?patient_id=' + document.getElementById('patient_id').value);
        		} else {
        			anchor.setAttribute('href', 'view_infection_risk?patient_id=' + document.getElementById('patient_id').value);
        		}
    			anchor.setAttribute('onclick', 'processUserSelection(this)');
    			anchor.style.fontSize = '15px';
    	    	anchor.innerText = myCounter + '. ' + myDataToShow; 
    	    	
    			li.appendChild(anchor);
    			combo_box.appendChild(li);
    			
    			break;
        	}
    	}
		
		break;
	}
}
function initialiseForm(whichForm, dataToUse)
{
	var myCounter = 0;

	switch (whichForm) {
	case 'BLANK-PATIENT-FORM':
		
		initialiseForm('PATIENT',null);
		document.getElementById('patient_surname').focus();
		break;
		
	case 'POPULATE-EXISTING-PATIENT':
	
		processWaitingButtonSpinner('START-PATIENT');
		initialiseForm('PATIENT',dataToUse);
		validateFormFields('validate_patient',null,null,false);
		if(window.location.pathname.includes('patient')) {
    		processPatientConsentInfectionRisk('GET-ALL-CONSENTS-FROM-DB',document.getElementById('patient_id'),false);
    		processPatientConsentInfectionRisk('GET-ALL-INFECTION-RISKS-FROM-DB-SESSION',document.getElementById('patient_id'),false);
		}
		processWaitingButtonSpinner('END-PATIENT');
		break;
	
	case 'POPULATE-BASIC-SEARCH': case 'POPULATE-ADVANCED-SEARCH': case 'POPULATE-PREVIOUS-ADVANCED-SEARCH':
		
		$("#search_result_div").empty();
		switch (whichForm) {
		case 'POPULATE-BASIC-SEARCH': 
			myCounter = dataToUse.patients.length;
			break;
		case 'POPULATE-ADVANCED-SEARCH': case 'POPULATE-PREVIOUS-ADVANCED-SEARCH':
			myCounter = dataToUse.length;
			break;
		}
		if (myCounter > 0) {
			addItemsToList(whichForm,document.getElementById('search_result_div'),dataToUse,null);
       		document.getElementById('search_result_div').style.display = '';
			document.getElementById('no_search_result_found_h6').style.display = 'none';
		} else {
       		document.getElementById('search_result_div').style.display = 'none';
			document.getElementById('no_search_result_found_h6').style.display = '';
			document.getElementById('no_search_result_found_h6').innerHTML = 'No Search Results Found';
		}
		break;
		
	case 'TIMELINE':
		
		if (dataToUse != null && dataToUse.notes.length > 0) {
			document.getElementById('view_timeline_notes_div').style.display = '';
			if(dataToUse.is_encrypted.toLowerCase().includes('yes')) {
		   		document.getElementById('view_timeline_notes').value = dataToUse.decrypted_notes.replace(/,/g, '\n');
			} else {
		   		document.getElementById('view_timeline_notes').value = dataToUse.notes.replace(/,/g, '\n');
			}
		} else {
			document.getElementById('view_timeline_notes_div').style.display = 'none';
		}
		break;
		
	case 'POPULATE-ACTIVE-ACTIONS': case 'POPULATE-COMPLETED-ACTIONS': case 'POPULATE-LOCKED-ACTIONS': case 'TIMELINE-LIST-DB': 
	case 'POPULATE-AUDIT-CONSENTS': case 'POPULATE-LOCKED-PATIENTS': case 'POPULATE-IMPORT-CONSENTS':

		switch (whichForm) {
		case 'TIMELINE-LIST-DB':
			
			$('#view_timeline_list_div').empty();
			var view_timeline_list_div = document.getElementById('view_timeline_list_div');
			
			addItemsToList('VIEW-TIMELINE',view_timeline_list_div,dataToUse,myCounter);

			if (view_timeline_list_div.getElementsByTagName('input').length > 0) {
           		document.getElementById('timeline_div').style.display = '';
				document.getElementById('view_timeline_list_div').style.display = '';
				document.getElementById('no_timeline_found_h6').style.display = 'none';
			} else {
           		document.getElementById('timeline_div').style.display = 'none';
				document.getElementById('view_timeline_list_div').style.display = 'none';
				document.getElementById('no_timeline_found_h6').style.display = '';
			}
			document.getElementById('view_timeline_notes_div').style.display = 'none';
			break;

		case 'POPULATE-IMPORT-CONSENTS':
			
			$('#imported_consents_div').empty();
			var import_consents_div = document.getElementById('imported_consents_div');
			
			addItemsToList('IMPORT-CONSENTS',import_consents_div,dataToUse,myCounter);

			if (import_consents_div.getElementsByTagName('input').length > 0) {
				document.getElementById('imported_consents_div').style.display = '';
				document.getElementById('validate_imported_consent_btn').style.display = '';
				document.getElementById('imported_consents_span').style.display = '';
				document.getElementById('imported_consents_span').innerHTML = import_consents_div.getElementsByTagName('input').length;
				document.getElementById('no_validate_imported_consent_h6').style.display = 'none';
			} else {
				document.getElementById('imported_consents_div').style.display = 'none';
				document.getElementById('validate_imported_consent_btn').style.display = 'none';
				document.getElementById('imported_consents_span').style.display = 'none';
				document.getElementById('no_validate_imported_consent_h6').style.display = '';
			}
			break;
			
		case 'POPULATE-AUDIT-CONSENTS':
			
			$('#audit_consents_div').empty();
			var audit_consents_div = document.getElementById('audit_consents_div');
			
			addItemsToList('AUDIT-CONSENTS',audit_consents_div,dataToUse,myCounter);

			if (audit_consents_div.getElementsByTagName('input').length > 0) {
				document.getElementById('audit_consents_div').style.display = '';
				document.getElementById('audit_consent_btn').style.display = '';
				document.getElementById('audit_consents_span').style.display = '';
				document.getElementById('audit_consents_span').innerHTML = audit_consents_div.getElementsByTagName('input').length;
				document.getElementById('no_audit_consents_found_h6').style.display = 'none';
			} else {
				document.getElementById('audit_consents_div').style.display = 'none';
				document.getElementById('audit_consents_span').style.display = 'none';
				document.getElementById('audit_consent_btn').style.display = 'none';
				document.getElementById('no_audit_consents_found_h6').style.display = '';
			}
			break;
			
		case 'POPULATE-ACTIVE-ACTIONS':
			
			$('#active_actions_div').empty();
			var active_actions_div = document.getElementById('active_actions_div');
			
			addItemsToList('ACTIVE-ACTIONS',active_actions_div,dataToUse,myCounter);

			if (active_actions_div.getElementsByTagName('input').length > 0) {
				document.getElementById('active_actions_div').style.display = '';
				document.getElementById('view_action_btn').style.display = '';
				document.getElementById('active_action_span').style.display = '';
				document.getElementById('active_action_span').innerHTML = active_actions_div.getElementsByTagName('input').length;
				document.getElementById('no_active_actions_found_h6').style.display = 'none';
			} else {
				document.getElementById('active_actions_div').style.display = 'none';
				document.getElementById('view_action_btn').style.display = 'none';
				document.getElementById('active_action_span').style.display = 'none';
				document.getElementById('no_active_actions_found_h6').style.display = '';
			}
			break;
		
		case 'POPULATE-COMPLETED-ACTIONS':
			
			$('#completed_actions_div').empty();
			var completed_actions_div = document.getElementById('completed_actions_div');
			
			addItemsToList('COMPLETED-ACTIONS',completed_actions_div,dataToUse,myCounter);

			if (completed_actions_div.getElementsByTagName('input').length > 0) {
				document.getElementById('completed_actions_div').style.display = '';
				document.getElementById('completed_actions_notes_div').style.display = '';
				document.getElementById('no_completed_actions_found_h6').style.display = 'none';
			} else {
				document.getElementById('completed_actions_div').style.display = 'none';
				document.getElementById('completed_actions_notes_div').style.display = 'none';
				document.getElementById('no_completed_actions_found_h6').style.display = '';
			}
			break;

		case 'POPULATE-LOCKED-ACTIONS': case 'POPULATE-LOCKED-PATIENTS':

			var locked_actions_div = document.getElementById('locked_actions_div');
			
			switch (whichForm) {
			case 'POPULATE-LOCKED-ACTIONS':
				$('#locked_actions_div').empty();
				addItemsToList('LOCKED-ACTIONS',locked_actions_div,dataToUse,myCounter);
				break;
			case 'POPULATE-LOCKED-PATIENTS':
				addItemsToList('LOCKED-PATIENTS',locked_actions_div,dataToUse,myCounter);
				break;
			}

			if (locked_actions_div.getElementsByTagName('input').length > 0) {
				document.getElementById('locked_actions_div').style.display = '';
				document.getElementById('unlock_action_btn').style.display = '';
				document.getElementById('locked_action_span').style.display = '';
				document.getElementById('locked_action_span').innerHTML = locked_actions_div.getElementsByTagName('input').length;
				document.getElementById('no_locked_actions_found_h6').style.display = 'none';
			} else {
				document.getElementById('locked_actions_div').style.display = 'none';
				document.getElementById('unlock_action_btn').style.display = 'none';
				document.getElementById('locked_action_span').style.display = 'none';
				document.getElementById('no_locked_actions_found_h6').style.display = '';
			}
			break;
		
		}
		
		break;
	
	case 'ACTIONS-LIST-DB':
	
		$('#prev_user_action_list').empty();
		
		var prev_user_action_list = document.getElementById('prev_user_action_list');
		
		addItemsToList('ACTION',prev_user_action_list,dataToUse,myCounter);
		
		if (prev_user_action_list.getElementsByTagName('li').length > 0) {
			document.getElementById('prev_user_action_list_label').innerHTML = 'Select action to view notes';
		} else {
			document.getElementById('prev_user_action_list_label').innerHTML = 'No Previous User Actions Found';
		}
		break;

	case 'ACTION-COUNT':

		if(document.getElementById('user_action_count_badge')) {
			if (dataToUse != null && dataToUse.length > 0) {	
				document.getElementById('user_action_count_badge').style.display = '';
				document.getElementById('user_action_count_badge').innerHTML = dataToUse.length;
			} else {
				document.getElementById('user_action_count_badge').style.display = 'none';
			}
		}
		break;
	
	case 'CONSENT-AUDIT':
		
		document.getElementById('aud_verbal_document_checked').selectedIndex = 0;
		document.getElementById('aud_digital_cf_attached').selectedIndex = 0;
		document.getElementById('aud_physical_consent_form').selectedIndex = 0;
		document.getElementById('aud_cf_physical_location').value = '';
		document.getElementById('aud_date_of_consent_stated').selectedIndex = 0;
		document.getElementById('aud_patient_signature').selectedIndex = 0;
		document.getElementById('aud_person_taking_consent').selectedIndex = 0;
		document.getElementById('aud_cf_validity').selectedIndex = 0;
		document.getElementById('aud_verify_consent_exclusions').selectedIndex = 0;
		document.getElementById('aud_statements_excluded').value = '';
		document.getElementById('reapproach_patient').selectedIndex = 0;
		document.getElementById('reapproach_reason').value = '';
		document.getElementById('aud_cf_audit_notes').selectedIndex = 0;
		document.getElementById('aud_data_discrepancies_identified').selectedIndex = 0;
		document.getElementById('aud_source_of_verified_data').selectedIndex = 0;
		document.getElementById('discrepancies_description').value = '';
		document.getElementById('samples_obtained_electronically').selectedIndex = 0;
		document.getElementById('sample_missing').selectedIndex = 0;
		document.getElementById('primary_auditor').value = '';
		document.getElementById('secondary_auditor').value = '';
		document.getElementById('audit_date').value = '';
		
		if (dataToUse)
		{
			document.getElementById('ca_consent_id').value = dataToUse.ca_consent_id;
			if(dataToUse.ca_which_department) {
				document.getElementById('ca_which_department').value = dataToUse.ca_which_department;
			}
			if(dataToUse.consent_audit_id > 0) {
				document.getElementById('consent_audit_id').value = dataToUse.consent_audit_id;
				document.getElementById('aud_verbal_document_checked').value = dataToUse.aud_verbal_document_checked;
				document.getElementById('aud_digital_cf_attached').value = dataToUse.aud_digital_cf_attached;
				document.getElementById('aud_physical_consent_form').value = dataToUse.aud_physical_consent_form;
				document.getElementById('aud_cf_physical_location').value = dataToUse.aud_cf_physical_location;
				document.getElementById('aud_date_of_consent_stated').value = dataToUse.aud_date_of_consent_stated;
				document.getElementById('aud_patient_signature').value = dataToUse.aud_patient_signature;
				document.getElementById('aud_person_taking_consent').value = dataToUse.aud_person_taking_consent;
				document.getElementById('aud_cf_validity').value = dataToUse.aud_cf_validity;
				document.getElementById('aud_verify_consent_exclusions').value = dataToUse.aud_verify_consent_exclusions;
				document.getElementById('aud_statements_excluded').value = dataToUse.aud_statements_excluded;
				document.getElementById('reapproach_patient').value = dataToUse.reapproach_patient;
				document.getElementById('reapproach_reason').value = dataToUse.reapproach_reason;
				document.getElementById('aud_cf_audit_notes').value = dataToUse.aud_cf_audit_notes;
				document.getElementById('aud_data_discrepancies_identified').value = dataToUse.aud_data_discrepancies_identified;
				document.getElementById('aud_source_of_verified_data').value = dataToUse.aud_source_of_verified_data;
				document.getElementById('discrepancies_description').value = dataToUse.discrepancies_description;
				document.getElementById('samples_obtained_electronically').value = dataToUse.samples_obtained_electronically;
				document.getElementById('sample_missing').value = dataToUse.sample_missing;
				document.getElementById('primary_auditor').value = dataToUse.primary_auditor;
				document.getElementById('secondary_auditor').value = dataToUse.secondary_auditor;
				document.getElementById('audit_date').value = dataToUse.audit_date;
			}
		}
		
		hideAndShowContainer(document.getElementById('aud_digital_cf_attached'));
		hideAndShowContainer(document.getElementById('aud_data_discrepancies_identified'));
		
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
		document.getElementById('cf_checked_by').value = '';
		document.getElementById('verbal_consent_checked_by').value = '';
		document.getElementById('data_discrepancies_verified_by').value = '';
		document.getElementById('data_accuracy_checked_by').value = '';
		
		if (dataToUse)
		{	
			document.getElementById('cv_consent_id').value = dataToUse.cv_consent_id;
			if(dataToUse.cv_which_department) {
				document.getElementById('cv_which_department').value = dataToUse.cv_which_department;
			}
			if(dataToUse.consent_validate_id > 0) {
				document.getElementById('consent_validate_id').value = dataToUse.consent_validate_id;
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

	    hideAndShowContainer(document.getElementById('verify_consent_exclusions'));
	    hideAndShowContainer(document.getElementById('data_discrepancies_identified'));
		
	    break;

	case 'ACTION':

		if(window.location.pathname.includes('user_actions') || window.location.pathname.includes('save_action')) {
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

	case 'SAMPLE-AUDIT-CHECK-BOXES':
	
		var ul = document.getElementById('prev_audit_samples_chkbox');
		
		if (dataToUse)
		{	
			$('#prev_audit_samples_chkbox').empty();

			if(Array.isArray(dataToUse)) {
				for (var i = 0; i < dataToUse.length; i++) {
					myCounter = myCounter + 1;
					addItemsToList(whichForm, ul, dataToUse[i], myCounter);
				}				
			} else {
				myCounter = myCounter + 1;
				addItemsToList(whichForm, ul, dataToUse, myCounter);
			}
		}
		if (ul.getElementsByTagName('li').length > 0) {
			document.getElementById('prev_audit_samples_chkbox_div').style.display = '';
		} else {
			document.getElementById('prev_audit_samples_chkbox_div').style.display = 'none';
		}
	    break;

	case 'INFECTION-RISK-CHECK-BOXES':
		
		var ul = document.getElementById('prev_infection_risk_chkbox');
		
		if (dataToUse)
		{	
			$('#prev_infection_risk_chkbox').empty();

			if(Array.isArray(dataToUse)) {
				for (var i = 0; i < dataToUse.length; i++) {
					myCounter = myCounter + 1;
					addItemsToList(whichForm, ul, dataToUse[i], myCounter);
				}				
			} else {
				myCounter = myCounter + 1;
				addItemsToList(whichForm, ul, dataToUse, myCounter);
			}
		}
		if (ul.getElementsByTagName('li').length > 0) {
			document.getElementById('prev_infection_risk_chkbox_div').style.display = '';
		} else {
			document.getElementById('prev_infection_risk_chkbox_div').style.display = 'none';
		}
	    break;

	case 'INFECTION-RISK-LIST': 
		
		var ul = document.getElementById('prev_infection_risk_lst');

		if (dataToUse) 
		{	
			$('#prev_infection_risk_lst').empty();
			if(Array.isArray(dataToUse)) {
				for (var i = 0; i < dataToUse.length; i++) {
					myCounter = myCounter + 1;
					addItemsToList(whichForm, ul, dataToUse[i], myCounter);
				}				
			} else {
				myCounter = myCounter + 1;
				addItemsToList(whichForm, ul, dataToUse, myCounter);
			}
		}

		if (ul.getElementsByTagName('li').length > 0) {
			document.getElementById('prev_infection_risk_lst_div').style.display = '';
		} else {
			document.getElementById('prev_infection_risk_lst_div').style.display = 'none';
		}

	    break;
	    
	case 'CONSENTS-LIST':
		
		var ul = document.getElementById('prev_consent_list');

		if (dataToUse)
		{
			$('#prev_consent_list').empty();
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

		if (ul.getElementsByTagName('li').length > 0) {
			document.getElementById('prev_consent_list_div').style.display = '';
		} else {
			document.getElementById('prev_consent_list_div').style.display = 'none';
		}
		
		break;
	
	case 'AUDIT-SAMPLE':
		
		document.getElementById('aud_sample_id').value = '';
		document.getElementById('aud_select_sample_type_id').selectedIndex = 0;
		document.getElementById('sample_date').value = '';
		document.getElementById('after_consent_date').selectedIndex = 0;
		document.getElementById('sample_in_assigned_location').selectedIndex = 0;
		document.getElementById('sample_details_legible').selectedIndex = 0;
		document.getElementById('appropriate_consent_present').selectedIndex = 0;
		document.getElementById('non_conformances_details').value = '';
		document.getElementById('aud_sample_pid').value = 0;
		document.getElementById('ca_id').value = 0;

		if (dataToUse)
		{ 	
			document.getElementById('aud_sample_id').value = dataToUse.aud_sample_id;
			document.getElementById('aud_select_sample_type_id').value = dataToUse.aud_sample_type_id;
			document.getElementById('sample_date').value = dataToUse.sample_date;
			document.getElementById('after_consent_date').value = dataToUse.after_consent_date;
			document.getElementById('sample_in_assigned_location').value = dataToUse.sample_in_assigned_location;
			document.getElementById('sample_details_legible').value = dataToUse.sample_details_legible;
			document.getElementById('appropriate_consent_present').value = dataToUse.appropriate_consent_present;
			document.getElementById('non_conformances_details').value = dataToUse.non_conformances_details;

			document.getElementById('aud_sample_pid').value = dataToUse.aud_sample_pid;
			document.getElementById('aud_sample_type_id').value = dataToUse.aud_sample_type_id;
			document.getElementById('ca_id').value = dataToUse.ca_id;
		}
		break;
		
	case 'SESSION-PATIENT':
		
	    document.getElementById('select_gender').value = document.getElementById('gender').value;
	    document.getElementById('select_volunteer').value = document.getElementById('volunteer').value;

	    hideAndShowContainer(document.getElementById('select_volunteer'));
	    
		break;
	    
	case 'PATIENT':

		if (dataToUse)
		{	
			document.getElementById('patient_id').value = dataToUse.patient_id;
		    document.getElementById('database_id').value = dataToUse.database_id;
		    document.getElementById('secondary_id').value = dataToUse.secondary_id;
		    document.getElementById('patient_surname').value = dataToUse.patient_surname;
		    document.getElementById('patient_firstname').value = dataToUse.patient_firstname;
		    document.getElementById('select_gender').value = dataToUse.gender;
		    document.getElementById('gender').value = dataToUse.gender;
		    document.getElementById('select_volunteer').value = dataToUse.volunteer;
		    document.getElementById('volunteer').value = dataToUse.volunteer.charAt(0).toUpperCase() + dataToUse.volunteer.slice(1);
		    document.getElementById('select_age').value = dataToUse.age;
		    document.getElementById('age').value = dataToUse.age;
		    document.getElementById('date_of_birth').value = dataToUse.date_of_birth;
		    document.getElementById('hospital_number').value = dataToUse.hospital_number;
		    document.getElementById('nhs_number').value = dataToUse.nhs_number;
		    document.getElementById('old_pat_id').value = dataToUse.old_pat_id;
		    document.getElementById('locked_description').value = dataToUse.locked_description;
		    document.getElementById('locked_by').value = dataToUse.locked_by;
		    document.getElementById('withdrawn_count').value = dataToUse.withdrawn_count;
			document.getElementById('patient_which_department').value = dataToUse.patient_which_department;

		} else {
			
			document.getElementById('patient_id').value = 0;
		    document.getElementById('database_id').value = '';
		    document.getElementById('secondary_id').value = '';
		    document.getElementById('patient_surname').value = '';
		    document.getElementById('patient_firstname').value = '';
		    document.getElementById('select_gender').selectedIndex = 0;
		    document.getElementById('select_volunteer').selectedIndex = 0;
		    document.getElementById('select_age').selectedIndex = 0;
		    document.getElementById('age').value = '';
		    document.getElementById('date_of_birth').value = '';
		    document.getElementById('hospital_number').value = '';
		    document.getElementById('nhs_number').value = '';
		    document.getElementById('old_pat_id').value = '';
		    document.getElementById('locked_description').value = '';
		    document.getElementById('locked_by').value = '';
		    document.getElementById('withdrawn_count').value = 0;
			document.getElementById('patient_which_department').value = '';
		}
	    
		hideAndShowContainer(document.getElementById('select_volunteer'));
		
	    break;
		
	case 'CONSENT':
		
		document.getElementById('sam_coll_before_sep_2006').selectedIndex = 1;
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
		document.getElementById('exclusions_comment').value = '';
		document.getElementById('samples_consented_to').value = '';
		document.getElementById('consent_notes').value = '';
		if(window.location.pathname.includes('/edit_consent')) {
			document.getElementById('withdrawn').selectedIndex = 1;
		} else {
			document.getElementById('withdrawn').selectedIndex = 0;
		}
		document.getElementById('withdrawal_date').value = '';
		document.getElementById('additional_document_file_label').innerHTML = '';
		document.getElementById('digital_cf_attachment_file_label').innerHTML = '';
		document.getElementById('withdrawal_document_file_label').innerHTML = '';
		document.getElementById('verbal_consent_document_file_label').innerHTML = '';
		document.getElementById('stop_sample_donation').selectedIndex = 0;
		document.getElementById('stop_sample_donation_date').value = '';
		document.getElementById('consent_status').value = '';
		document.getElementById('consent_which_department').value = '';
		document.getElementById('consent_id').value = 0;
		document.getElementById('is_imported').value = '';
		document.getElementById('is_validated').value = '';
		document.getElementById('is_audited').value = '';
		document.getElementById('is_finalised').value = '';
		document.getElementById('is_withdrawn').value = '';
		document.getElementById('withdrawal_document_id').value = '';
		document.getElementById('marked_for_auditing').value = '';
		document.getElementById('consent_deletion_date').value = '';

		if (dataToUse)
		{	
			document.getElementById('consent_id').value = dataToUse.consent_id;
			document.getElementById('cn_patient_id').value = dataToUse.cn_patient_id;
			document.getElementById('sam_coll_before_sep_2006').value = dataToUse.sam_coll_before_sep_2006;
			document.getElementById('date_of_consent').value = dataToUse.date_of_consent;
			document.getElementById('consent_taken_by').value = dataToUse.consent_taken_by;
			document.getElementById('loc_id').value = dataToUse.loc_id;
			document.getElementById('verbal_consent').value = dataToUse.verbal_consent;
			document.getElementById('form_type').value = dataToUse.form_type;
			document.getElementById('form_version_id').value = dataToUse.form_version_id;
			document.getElementById('select_form_version_id').value = dataToUse.form_version_id;
			document.getElementById('verbal_consent_recorded').value = dataToUse.verbal_consent_recorded;
			document.getElementById('verbal_consent_recorded_by').value = dataToUse.verbal_consent_recorded_by;
			document.getElementById('consent_type').value = dataToUse.consent_type;
			document.getElementById('consent_exclusions').value = dataToUse.consent_exclusions;
			document.getElementById('exclusions_comment').value = dataToUse.exclusions_comment;
			document.getElementById('select_consent_exclusions').value = dataToUse.consent_exclusions;
			document.getElementById('samples_consented_to').value = dataToUse.samples_consented_to;
			document.getElementById('select_samples_consented_to').value = dataToUse.samples_consented_to;
			document.getElementById('consent_notes').value = dataToUse.consent_notes;
			if(dataToUse.withdrawn) {
				document.getElementById('withdrawn').value = dataToUse.withdrawn;
			}
			document.getElementById('withdrawal_date').value = dataToUse.withdrawal_date;
			document.getElementById('consent_status').value = dataToUse.status;
			document.getElementById('stop_sample_donation').value = dataToUse.stop_sample_donation;
			document.getElementById('stop_sample_donation_date').value = dataToUse.stop_sample_donation_date;
			document.getElementById('consent_which_department').value = dataToUse.consent_which_department;
			document.getElementById('is_imported').value = dataToUse.is_imported;
			document.getElementById('is_validated').value = dataToUse.is_validated;
			document.getElementById('is_audited').value = dataToUse.is_audited;
			document.getElementById('marked_for_auditing').value = dataToUse.marked_for_auditing;
			document.getElementById('is_finalised').value = dataToUse.is_finalised;
			document.getElementById('is_withdrawn').value = dataToUse.is_withdrawn;
			document.getElementById('withdrawal_document_id').value = dataToUse.withdrawal_document_id;
			document.getElementById('consent_deletion_date').value = dataToUse.consent_deletion_date;

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
	    
	    hideAndShowContainer(document.getElementById('verbal_consent'));
	    hideAndShowContainer(document.getElementById('consent_type'));
	    hideAndShowContainer(document.getElementById('withdrawn'));
	    uploadFormDataToSessionObjects('CONSENT',document.getElementById('withdrawn'));
	    hideAndShowContainer(document.getElementById('additional_document_file_label'));
	    hideAndShowContainer(document.getElementById('digital_cf_attachment_file_label'));
	    hideAndShowContainer(document.getElementById('withdrawal_document_file_label'));
	    hideAndShowContainer(document.getElementById('verbal_consent_document_file_label'));
	    hideAndShowContainer(document.getElementById('stop_sample_donation'));

	    $('#select_consent_exclusions').selectpicker('refresh');
	    $('#select_samples_consented_to').selectpicker('refresh');

//	    hideAndShowContainer(document.getElementById('sam_coll_before_sep_2006'));
	    
		break;
	
	case 'INFECTION-RISK':
		
	    document.getElementById('infection_risk_exist').selectedIndex = 0;
	    document.getElementById('select_infection_type_id').selectedIndex = 0;
	    document.getElementById('other_infection_risk').value = '';
	    document.getElementById('infection_risk_notes').value = '';
	    document.getElementById('select_episode_of_infection').selectedIndex = 0;
	    document.getElementById('episode_start_date').value = '';
	    document.getElementById('select_continued_risk').selectedIndex = 0;
	    document.getElementById('episode_finished_date').value = '';
	    document.getElementById('episode_of_infection').value = '';
	    document.getElementById('infection_type_id').value = '';
	    document.getElementById('continued_risk').value = 'No';
	    document.getElementById('select_sample_collection').selectedIndex = 0;
	    document.getElementById('select_sample_type_id').selectedIndex = 0;
	    document.getElementById('sample_collection_date').value = '';
    	document.getElementById('infection_risk_id').value = '0';
		document.getElementById('ir_which_department').value = '';
		document.getElementById('ir_deletion_date').value = '';
		document.getElementById('ir_patient_id').value = '';
		
		if (dataToUse)
		{	
	    	document.getElementById('infection_risk_id').value = dataToUse.infection_risk_id;
		    document.getElementById('infection_risk_exist').value = dataToUse.infection_risk_exist;
		    document.getElementById('select_infection_type_id').value = dataToUse.infection_type_id;
		    document.getElementById('other_infection_risk').value = dataToUse.other_infection_risk;
		    document.getElementById('infection_risk_notes').value = dataToUse.infection_risk_notes;
		    document.getElementById('select_episode_of_infection').value = dataToUse.episode_of_infection;
		    document.getElementById('episode_start_date').value = dataToUse.episode_start_date;
		    document.getElementById('select_continued_risk').value = dataToUse.continued_risk;
		    document.getElementById('episode_finished_date').value = dataToUse.episode_finished_date;
		    document.getElementById('episode_of_infection').value = dataToUse.episode_of_infection;
		    document.getElementById('infection_type_id').value = dataToUse.infection_type_id;
		    document.getElementById('continued_risk').value = dataToUse.continued_risk;
		    document.getElementById('select_sample_collection').value = dataToUse.sample_collection;
		    document.getElementById('select_sample_type_id').value = dataToUse.sample_type_id;
		    document.getElementById('sample_collection_date').value = dataToUse.sample_collection_date;
			document.getElementById('ir_which_department').value = dataToUse.ir_which_department;
			document.getElementById('ir_deletion_date').value = dataToUse.ir_deletion_date;
			document.getElementById('ir_patient_id').value = dataToUse.ir_patient_id;
    	} 
	    
	    hideAndShowContainer(document.getElementById('infection_risk_exist'));
	    hideAndShowContainer(document.getElementById('select_infection_type_id'));
	    hideAndShowContainer(document.getElementById('select_continued_risk'));
		
		break;
		
	}
}
function processKeyPressEvent(whichEvent,whichInput) 
{
	switch ($(whichInput).attr('id')) {
	case 'nhs_number':

		if(whichEvent.keyCode >= 48 && whichEvent.keyCode <= 57) {
			if($(whichInput).val().length === 3 || $(whichInput).val().length === 7){
				$(whichInput).val($(whichInput).val() + '-');
			}    	
			return true;
		}
		
		return false;
		
		break;
	}
}    		
function processButtonClick(whichInput,dataToUse)
{
	switch ($(whichInput).attr('id')) {
	case 'yes_btn': case 'no_btn': case 'dismiss_btn':
		if(window.location.pathname.includes('add_patient')) {
    		switch ($(whichInput).attr('id')) {
			case 'yes_btn':
				initialiseForm('POPULATE-EXISTING-PATIENT',dataToUse);
				break;
			case 'dismiss_btn':	
				initialiseForm('BLANK-PATIENT-FORM',null);
				break;
    		} 
    		$('.my_document_preview_modal').modal('hide');
		}
		break;
	default:
		break;
	}
	
}
function processUserSelection(whichInput)
{	
    var selectedCheckBoxValues = [];
    var checked = false;

    if (window.location.pathname.includes('select_department_locations')) {

    	var brands = $('#' + whichInput.id + '_selected_locations option:selected');
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

    	switch ($(whichInput).attr('name')) {
    	case 'search_criteria_delete_row':

    		$(whichInput).parents("tr").remove();
    		if(document.getElementById('advanced_search_options_table').rows.length <= 1) {
    			processUserSelection(document.getElementById('advanced_search_clear_option_btn'));
    		}
    		break;
    		
    	case 'advanced_search_btn':

    		var error_occured = '', processed_text = '', this_sql_script = '', col_to_use = '', 
    			which_dept = $('#whichDepartment').val(), tables_to_use = '';
    		
			$('#select_search_result_columns').css('border','');
			if(!$('#select_search_result_columns option:selected').val()) {
				$('#select_search_result_columns').css('border','#E11E26 2px solid');
				document.getElementById('select_search_result_columns').focus({preventScroll:false});
				alert('Search result columns cannot be blank');
				return false;
			} else {
				this_sql_script = 'pat.patient_id';
	    		tables_to_use = which_dept + '_PATIENT_V pat';
			    $('#select_search_result_columns option:selected').each(function() {
			    	switch ($(this).val()) {
					case 'patient_firstname': case 'patient_surname': case 'database_id': case 'secondary_id': case 'volunteer':
					case 'date_of_birth': case 'nhs_number': case 'hospital_number': case 'age': case 'old_pat_id':
				    	
						switch ($(this).val()) {
						case 'patient_firstname': case 'patient_surname': case 'date_of_birth': case 'nhs_number':
							col_to_use = 'encrypt_decrypt.decrypt(pat.' + $(this).val() + '|pat.database_id)';
							break;
						default:
							col_to_use = 'pat.' + $(this).val();
							break;
				    	}
						break;
						
					case 'date_of_consent': case 'consent_taken_by': case 'location': case 'verbal_consent': case 'verbal_consent_recorded':
					case 'verbal_consent_recorded_by': case 'verbal_consent_document_id': case 'form_type': case 'form_version_id': 
					case 'digital_cf_attachment_id': case 'exclusions_comment': case 'consent_notes': case 'withdrawn': case 'withdrawal_date':
					case 'withdrawal_document_id': case 'is_validated': case 'is_imported': case 'is_finalised': case 'consent_type':
						
						switch ($(this).val()) {
						case 'location':
							col_to_use = 'con.loc_id';
							break;
						default:
							col_to_use = 'con.' + $(this).val();
							break;
						}
				    	if(!tables_to_use.includes(which_dept + '_CONSENT_V con')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_CONSENT_V con';
				    		} else {
					    		tables_to_use = which_dept + '_CONSENT_V con';
				    		}
				    	}
						break;
						
					case 'samples_consented_to': 
						
						col_to_use = 'sc.consented_samples_id';
				    	if(!tables_to_use.includes(which_dept + '_CONSENTED_SAMPLES_V sc')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_CONSENTED_SAMPLES_V sc';
				    		} else {
					    		tables_to_use = which_dept + '_CONSENTED_SAMPLES_V sc';
				    		}
				    	}
						break;
						
					case 'consent_exclusions':
						
						col_to_use = 'ct.description';
//						col_to_use = 'ce.consent_exclusion_id';
				    	if(!tables_to_use.includes(which_dept + '_CONSENT_EXCLUSION_V ce')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_CONSENT_EXCLUSION_V ce';
				    		} else {
					    		tables_to_use = which_dept + '_CONSENT_EXCLUSION_V ce';
				    		}
				    	}
				    	if(!tables_to_use.includes(which_dept + '_CONSENT_TERMS_V ct')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_CONSENT_TERMS_V ct';
				    		} else {
					    		tables_to_use = which_dept + '_CONSENT_TERMS_V ct';
				    		}
				    	}
						break;
						
					case 'number_of_consents':

						col_to_use = '(SELECT COUNT(*) FROM ' + which_dept + '_CONSENT_V WHERE ' 
						+ which_dept + '_CONSENT_V.cn_patient_id = pat.patient_id) AS Consents_Count';
				          
						break;
						
					case 'number_of_infection_risks':
						
						col_to_use = '(SELECT COUNT(*) FROM ' + which_dept + '_INFECTION_RISK_V WHERE ' 
							+ which_dept + '_INFECTION_RISK_V.ir_patient_id = pat.patient_id) AS Infection_Count';
					          
						break;
						
					}
	    			this_sql_script = this_sql_script + '|' + col_to_use;
			    });
			}
			
			$('#advanced_search_options_div input, #advanced_search_options_div select').each(
					
				function(index){  

					if ($(this).attr('id').includes('search_age_start') || $(this).attr('id').includes('search_volunteer')
							|| $(this).attr('id').includes('search_date_of_birth_start') || $(this).attr('id').includes('search_secondary_id')
							|| $(this).attr('id').includes('search_database_id')) {
				    	if(!tables_to_use.includes(which_dept + '_PATIENT_V pat')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_PATIENT_V pat';
				    		} else {
					    		tables_to_use = which_dept + '_PATIENT_V pat';
				    		}
				    	}
					} else if ($(this).attr('id').includes('search_infection_risk_exist')) {
				    	if(!tables_to_use.includes(which_dept + '_INFECTION_RISK_V ir')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_INFECTION_RISK_V ir';
				    		} else {
					    		tables_to_use = which_dept + '_INFECTION_RISK_V ir';
				    		}
				    	}
					} else if ($(this).attr('id').includes('search_consent_exclusions')) {
				    	if(!tables_to_use.includes(which_dept + '_CONSENT_EXCLUSION_V ce')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_CONSENT_EXCLUSION_V ce';
				    		} else {
					    		tables_to_use = which_dept + '_CONSENT_EXCLUSION_V ce';
				    		}
				    	}
				    	if(!tables_to_use.includes(which_dept + '_CONSENT_TERMS_V ct')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_CONSENT_TERMS_V ct';
				    		} else {
					    		tables_to_use = which_dept + '_CONSENT_TERMS_V ct';
				    		}
				    	}
					} else if ($(this).attr('id').includes('search_withdrawn') || $(this).attr('id').includes('search_digital_cf_attachment')
							|| $(this).attr('id').includes('search_consent_taken_by') || $(this).attr('id').includes('search_form_type')
							|| $(this).attr('id').includes('search_location') || $(this).attr('id').includes('search_form_versions')
							|| $(this).attr('id').includes('search_validated_consents') || $(this).attr('id').includes('search_imported_consents')
							|| $(this).attr('id').includes('search_finalised_consents') || $(this).attr('id').includes('search_date_of_consent_start')
							|| $(this).attr('id').includes('search_sam_coll_before_sep_2006')) {
				    	if(!tables_to_use.includes(which_dept + '_CONSENT_V con')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_CONSENT_V con';
				    		} else {
					    		tables_to_use = which_dept + '_CONSENT_V con';
				    		}
				    	}
					} else if ($(this).attr('id').includes('search_samples_consented_to')) {
				    	if(!tables_to_use.includes(which_dept + '_CONSENTED_SAMPLES_V sc')) {
				    		if(tables_to_use) {
					    		tables_to_use = tables_to_use + '|' + which_dept + '_CONSENTED_SAMPLES_V sc';
				    		} else {
					    		tables_to_use = which_dept + '_CONSENTED_SAMPLES_V sc';
				    		}
				    	}
					}
						
					if ($(this).attr('id').includes('search_volunteer') || $(this).attr('id').includes('search_infection_risk_exist')
						|| $(this).attr('id').includes('search_consent_exclusions') || $(this).attr('id').includes('search_withdrawn')
						|| $(this).attr('id').includes('search_digital_cf_attachment') || $(this).attr('id').includes('search_consent_taken_by')
						|| $(this).attr('id').includes('search_form_type') || $(this).attr('id').includes('search_validated_consents')
						|| $(this).attr('id').includes('search_imported_consents') || $(this).attr('id').includes('search_finalised_consents')
						|| $(this).attr('id').includes('search_location') || $(this).attr('id').includes('search_form_versions')
						|| $(this).attr('id').includes('search_samples_consented_to') || $(this).attr('id').includes('search_sam_coll_before_sep_2006')
						|| $(this).attr('id').includes('search_secondary_id') || $(this).attr('id').includes('search_database_id')) {
						
//						$(this).css('border','');
//						if ($(this).attr('id').includes('search_volunteer') || $(this).attr('id').includes('search_withdrawn')
//							|| $(this).attr('id').includes('search_digital_cf_attachment') || $(this).attr('id').includes('search_consent_taken_by')
//							|| $(this).attr('id').includes('search_validated_consents') || $(this).attr('id').includes('search_imported_consents')
//							|| $(this).attr('id').includes('search_finalised_consents') || $(this).attr('id').includes('search_sam_coll_before_sep_2006')) {
//							if(!$(this).val()) {
//								$(this).css('border','#E11E26 2px solid');
//								document.getElementById($(this).attr('id')).focus({preventScroll:false});
//								error_occured = error_occured + $(this).attr('id').replaceAll('_', ' ').slice(0,-2) + ' is blank' + '\r\n';
//							}
//						} else if ($(this).attr('id').includes('search_infection_risk_exist') || $(this).attr('id').includes('search_consent_exclusions')
//								|| $(this).attr('id').includes('search_form_type') || $(this).attr('id').includes('search_location')
//								|| $(this).attr('id').includes('search_form_versions') || $(this).attr('id').includes('search_samples_consented_to')){
//							if(!$('#' + $(this).attr('id') + ' option:selected').val()) {
//								$(this).css('border','#E11E26 2px solid');
//								document.getElementById($(this).attr('id')).focus({preventScroll:false});
//								error_occured = error_occured + $(this).attr('id').replaceAll('_', ' ').slice(0,-2) + ' is blank' + '\r\n';
//							}
//						}

						processed_text = '';
						if ($(this).attr('id').includes('search_volunteer')) {
							
							if($('#' + $(this).attr('id') + ' option:selected').val()) {
								processed_text = 'upper(pat.' + $(this).attr('id').replaceAll('search_', '').slice(0,-2) + ")='" 
								+ $('#' + $(this).attr('id') + ' option:selected').val().toUpperCase() + "' ";
							} else {
								processed_text = 'pat.' + $(this).attr('id').replaceAll('search_', '').slice(0,-2) + " IS NULL ";
							}
							
						} else if ($(this).attr('id').includes('search_secondary_id') || $(this).attr('id').includes('search_database_id')) {

							if($('#' + $(this).attr('id')).val()) {
								processed_text = 'upper(pat.' + $(this).attr('id').replaceAll('search_', '').slice(0,-2) + ")='" 
									+ $('#' + $(this).attr('id')).val().toUpperCase() + "' ";
							} else {
								processed_text = 'pat.' + $(this).attr('id').replaceAll('search_', '').slice(0,-2) + " IS NULL ";
							}
							
						} else if ($(this).attr('id').includes('search_consent_taken_by')) {

							if($('#' + $(this).attr('id')).val()) {
								processed_text = 'upper(con.' + $(this).attr('id').replaceAll('search_', '').slice(0,-2) + ")='" 
									+ $('#' + $(this).attr('id')).val().toUpperCase() + "' ";
							} else {
								processed_text = 'con.' + $(this).attr('id').replaceAll('search_', '').slice(0,-2) + " IS NULL ";
							}
							
						} else if ($(this).attr('id').includes('search_withdrawn') || $(this).attr('id').includes('search_sam_coll_before_sep_2006')) {
							
							if($('#' + $(this).attr('id') + ' option:selected').val()) {
								processed_text = 'upper(con.' + $(this).attr('id').replaceAll('search_', '').slice(0,-2) + ")='" 
									+ $('#' + $(this).attr('id') + ' option:selected').val().toUpperCase() + "' ";
							} else {
								processed_text = 'con.' + $(this).attr('id').replaceAll('search_', '').slice(0,-2) + " IS NULL ";
							}
							
						} else if ($(this).attr('id').includes('search_infection_risk_exist') || $(this).attr('id').includes('search_form_type')) {
							if($(this).attr('id').includes('search_infection_risk_exist')) {
								Array.from(document.getElementById($(this).attr('id')).options).forEach(function(option_element) {
									if(option_element.selected) {
								    	if(processed_text) {
									    	processed_text = processed_text + " OR upper(ir.infection_risk_exist)='" + option_element.value.toUpperCase() + "'";
								    	} else {
									    	processed_text = "(upper(ir.infection_risk_exist)='" + option_element.value.toUpperCase() + "'";
								    	}
									}
								});							
						    	if(processed_text) {
						    		processed_text = processed_text + ') ';
						    	} else {
							    	processed_text = "ir.infection_risk_exist IS NULL ";
						    	}
					    	} else if($(this).attr('id').includes('search_form_type')) {
								Array.from(document.getElementById($(this).attr('id')).options).forEach(function(option_element) {
									if(option_element.selected) {
								    	if(processed_text) {
									    	processed_text = processed_text + " OR upper(con.form_type)='" + option_element.value.toUpperCase() + "' ";
								    	} else {
									    	processed_text = "(upper(con.form_type)='" + option_element.value.toUpperCase() + "' ";
								    	}
									}
								});							
						    	if(processed_text) {
						    		processed_text = processed_text + ') ';
						    	} else {
							    	processed_text = "con.form_type IS NULL ";
						    	}
							}
						} else if ($(this).attr('id').includes('search_location') || $(this).attr('id').includes('search_form_versions')
								|| $(this).attr('id').includes('search_samples_consented_to') || $(this).attr('id').includes('search_consent_exclusions')) {
							Array.from(document.getElementById($(this).attr('id')).options).forEach(function(option_element) {
								if(option_element.selected) {
							    	if(processed_text) {
								    	processed_text = processed_text + '|' + option_element.value;
							    	} else {
								    	processed_text = '(' + option_element.value;
							    	}
								}
							});
					    	if(processed_text) {
					    		processed_text = processed_text + ') ';
						    	if($(this).attr('id').includes('search_location')) {
									processed_text = 'con.loc_id IN ' + processed_text;
						    	} else if($(this).attr('id').includes('search_form_versions')) {
									processed_text = 'con.form_version_id IN ' + processed_text;
						    	} else if($(this).attr('id').includes('search_samples_consented_to')) {
									processed_text = 'sc.consent_sample_type_id IN ' + processed_text;
						    	} else if($(this).attr('id').includes('search_consent_exclusions')) {
									processed_text = 'ce.consent_exclusion_id IN ' + processed_text;
						    	}
					    	} else {
						    	if($(this).attr('id').includes('search_location')) {
							    	processed_text = "con.loc_id IS NULL ";
						    	} else if($(this).attr('id').includes('search_form_versions')) {
							    	processed_text = "con.form_version_id IS NULL ";
						    	} else if($(this).attr('id').includes('search_samples_consented_to')) {
							    	processed_text = "sc.consent_sample_type_id IS NULL ";
						    	} else if($(this).attr('id').includes('search_consent_exclusions')) {
							    	processed_text = "ce.consent_exclusion_id IS NULL ";
						    	}
					    	}
						} else if ($(this).attr('id').includes('search_digital_cf_attachment') || $(this).attr('id').includes('search_validated_consents')
								|| $(this).attr('id').includes('search_imported_consents') || $(this).attr('id').includes('search_finalised_consents')) {
					    	if($(this).attr('id').includes('search_digital_cf_attachment')) {
								if($('#' + $(this).attr('id') + ' option:selected').val()) {
							    	processed_text =  "con.digital_cf_attachment_id > 0 ";
								} else {
							    	processed_text = "con.digital_cf_attachment_id IS NULL ";
								}
					    	} else if($(this).attr('id').includes('search_validated_consents')) {
								if($('#' + $(this).attr('id') + ' option:selected').val()) {
							    	processed_text =  "upper(con.is_validated)='YES' ";
								} else {
							    	processed_text = "con.is_validated IS NULL ";
								}
					    	} else if($(this).attr('id').includes('search_imported_consents')) {
								if($('#' + $(this).attr('id') + ' option:selected').val()) {
							    	processed_text =  "upper(con.is_imported)='YES' ";
								} else {
							    	processed_text = "con.is_imported IS NULL ";
								}
					    	} else if($(this).attr('id').includes('search_finalised_consents')) {
								if($('#' + $(this).attr('id') + ' option:selected').val()) {
							    	processed_text =  "upper(con.is_finalised)='YES' ";
								} else {
							    	processed_text = "con.is_finalised IS NULL ";
								}
					    	}
						}
						if(document.getElementById('and_or_' + $(this).attr('id').substr($(this).attr('id').length - 1))) {
							processed_text = $('#and_or_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val().toUpperCase() + ' ' + processed_text;
						}
						if(document.getElementById('open_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value) {
							processed_text = document.getElementById('open_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value.toUpperCase() + ' ' + processed_text;
						}
						if(document.getElementById('close_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value) {
							processed_text = processed_text + document.getElementById('close_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value.toUpperCase();
						}
						
						selectedCheckBoxValues.push(processed_text);
						
					} else if($(this).attr('id').includes('search_age_start')) {
						
						processed_text = '';
						$('#search_age_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).css('border','');
//						if($(this).val() > $('#search_age_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val()) {
//							$('#search_age_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).css('border','#E11E26 2px solid');
//							document.getElementById($('#search_age_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).attr('id')).focus({preventScroll:false});
//							error_occured = error_occured + 'Age start range is greater than end range' + '\r\n';
//						} else {
							if ($(this).val() && $('#search_age_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val()) {
								processed_text = "pat.age BETWEEN " + $(this).val() + " AND " + 
									$('#search_age_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val() + ' ';
							} else {
								processed_text = "pat.age IS NULL ";
							}
							if(document.getElementById('and_or_' + $(this).attr('id').substr($(this).attr('id').length - 1))) {
								processed_text = $('#and_or_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val().toUpperCase() + ' ' + processed_text;
							}
							if(document.getElementById('open_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value) {
								processed_text = document.getElementById('open_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value.toUpperCase() + ' ' + processed_text;
							}
							if(document.getElementById('close_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value) {
								processed_text = processed_text + document.getElementById('close_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value.toUpperCase();
							}
							selectedCheckBoxValues.push(processed_text);
//						}

					} else if($(this).attr('id').includes('search_date_of_birth_start') || $(this).attr('id').includes('search_date_of_consent_start')) {

//						$(this).css('border','');
//						if($(this).val() == '') {
//							$(this).css('border','#E11E26 2px solid');
//							document.getElementById($(this).attr('id')).focus({preventScroll:false});
//							error_occured = error_occured + $(this).attr('id').replaceAll('_', ' ').slice(0,-2) + ' is blank' + '\r\n';
//						}
						processed_text = '';
						if($(this).attr('id').includes('search_date_of_birth_start')) {
//							$('#search_date_of_birth_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).css('border','');
//							if($('#search_date_of_birth_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val() == '' || 
//									Date.parse($('#search_date_of_birth_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val()) <= Date.parse($(this).val())) {
//								$('#search_date_of_birth_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).css('border','#E11E26 2px solid');
//								document.getElementById($('#search_date_of_birth_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).attr('id')).focus({preventScroll:false});
//								if($('#search_date_of_birth_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val() == '') {
//									error_occured = error_occured + $('#search_date_of_birth_end').attr('id').replaceAll('_', ' ').slice(0,-2) + ' is blank' + '\r\n';
//								} else {
//									error_occured = error_occured + 'End date is before start date' + '\r\n';
//								}
//							} else {
								if ($(this).val() && $('#search_date_of_birth_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val()) {
									processed_text = "pat.date_of_birth BETWEEN encrypt_decrypt.encrypt('" + $(this).val() + "'|pat.database_id) AND encrypt_decrypt.encrypt('" 
										+ $('#search_date_of_birth_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val() + "'|pat.database_id) ";
								} else {
									processed_text = "pat.date_of_birth IS NULL ";
								}
								if(document.getElementById('and_or_' + $(this).attr('id').substr($(this).attr('id').length - 1))) {
									processed_text = $('#and_or_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val().toUpperCase() + ' ' + processed_text;
								}
								if(document.getElementById('open_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value) {
									processed_text = document.getElementById('open_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value.toUpperCase() + ' ' + processed_text;
								}
								if(document.getElementById('close_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value) {
									processed_text = processed_text + document.getElementById('close_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value.toUpperCase();
								}
								selectedCheckBoxValues.push(processed_text);
//							}
						} else if($(this).attr('id').includes('search_date_of_consent_start')) {
//							$('#search_date_of_consent_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).css('border','');
//							if($('#search_date_of_consent_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val() == '' || 
//									Date.parse($('#search_date_of_consent_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val()) <= Date.parse($(this).val())) {
//								$('#search_date_of_consent_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).css('border','#E11E26 2px solid');
//								document.getElementById($('#search_date_of_consent_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).attr('id')).focus({preventScroll:false});
//								if($('#search_date_of_birth_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val() == '') {
//									error_occured = error_occured + $('#search_date_of_consent_end_').attr('id').replaceAll('_', ' ').slice(0,-2) + ' is blank' + '\r\n';
//								} else {
//									error_occured = error_occured + 'End date is before start date' + '\r\n';
//								}
//							} else {
								if ($(this).val() && $('#search_date_of_consent_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val()) {
									processed_text = "con.date_of_consent BETWEEN '" + $(this).val() + "' AND '" + 
										$('#search_date_of_consent_end_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val() + "' ";
								} else {
									processed_text = "con.date_of_consent IS NULL ";
								}
								if(document.getElementById('and_or_' + $(this).attr('id').substr($(this).attr('id').length - 1))) {
									processed_text = $('#and_or_' + $(this).attr('id').substr($(this).attr('id').length - 1)).val().toUpperCase() + ' ' + processed_text;
								}
								if(document.getElementById('open_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value) {
									processed_text = document.getElementById('open_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value.toUpperCase() + processed_text;
								}
								if(document.getElementById('close_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value) {
									processed_text = processed_text + document.getElementById('close_bracket_' + $(this).attr('id').substr($(this).attr('id').length - 1)).value.toUpperCase();
								}
								selectedCheckBoxValues.push(processed_text);
//							}
						}
					}
			    }
			);
			
			if(error_occured) {
				alert('Error occurred..' + '\r\n' + error_occured);
				return false;
			} else {
				this_sql_script = 'SELECT ' + this_sql_script;
		    	if(tables_to_use.toUpperCase().includes(which_dept + '_PATIENT_V PAT')) {
		    		this_sql_script = this_sql_script + ' FROM ' + which_dept + '_PATIENT_V pat';
		    		if(tables_to_use.toUpperCase().includes(which_dept + '_CONSENT_V CON')) {
			    		this_sql_script = this_sql_script + ' FULL JOIN ' + which_dept + '_CONSENT_V con ON con.cn_patient_id = pat.patient_id';
			    		if(tables_to_use.toUpperCase().includes(which_dept + '_CONSENT_EXCLUSION_V CE')) {
				    		this_sql_script = this_sql_script + ' FULL JOIN ' + which_dept + '_CONSENT_EXCLUSION_V ce ON ce.consent_id = con.consent_id';
			    		}
			    		if(tables_to_use.toUpperCase().includes(which_dept + '_CONSENT_TERMS_V CT')) {
				    		this_sql_script = this_sql_script + ' FULL JOIN ' + which_dept + '_CONSENT_TERMS_V ct ON ce.consent_term_id = ct.consent_term_id';
			    		}
			    		if(tables_to_use.toUpperCase().includes(which_dept + '_CONSENTED_SAMPLES_V CS')) {
				    		this_sql_script = this_sql_script + ' FULL JOIN ' + which_dept + '_CONSENTED_SAMPLES_V cs ON cs.consent_id = con.consent_id';
			    		}
		    		} else if(tables_to_use.toUpperCase().includes(which_dept + '_INFECTION_RISK_V IR')) {
			    		this_sql_script = this_sql_script + ' FULL JOIN ' + which_dept + '_INFECTION_RISK_V ir ON ir.ir_patient_id = pat.patient_id';
			    	}
		    	} else if(tables_to_use.toUpperCase().includes(which_dept + '_CONSENT_V CON')) {
		    		this_sql_script = this_sql_script + ' FROM ' + which_dept + '_CONSENT_V con';
		    		if(tables_to_use.toUpperCase().includes(which_dept + '_CONSENT_EXCLUSION_V CE')) {
			    		this_sql_script = this_sql_script + ' FULL JOIN ' + which_dept + '_CONSENT_EXCLUSION_V ce ON ce.consent_id = con.consent_id';
		    		}
		    		if(tables_to_use.toUpperCase().includes(which_dept + '_CONSENT_TERMS_V CT')) {
			    		this_sql_script = this_sql_script + ' FULL JOIN ' + which_dept + '_CONSENT_TERMS_V ct ON ce.consent_term_id = ct.consent_term_id';
		    		}
		    		if(tables_to_use.toUpperCase().includes(which_dept + '_CONSENTED_SAMPLES_V CS')) {
			    		this_sql_script = this_sql_script + ' FULL JOIN ' + which_dept + '_CONSENTED_SAMPLES_V cs ON cs.consent_id = con.consent_id';
		    		}
		    		if(tables_to_use.toUpperCase().includes(which_dept + '_INFECTION_RISK_V IR')) {
			    		this_sql_script = this_sql_script + ' FULL JOIN ' + which_dept + '_INFECTION_RISK_V ir ON con.cn_patient_id = ir.ir_patient_id';
		    		}
	    		}
				this_sql_script = this_sql_script + ' WHERE ';
				$(selectedCheckBoxValues).each( function( index, element ){
			    	this_sql_script = this_sql_script + element;
				});
        		processWaitingButtonSpinner('START-SEARCH-PATIENT');
        		processPatientConsentInfectionRisk('ADVANCED-SEARCH',this_sql_script,false);
			}
    		break;
    		
    	case 'advanced_search_clear_option_btn':
    		
    		$("#advanced_search_options_div").empty();
    		$("#search_result_div").empty();
    		document.getElementById('select_advanced_search_criteria').selectedIndex = 0;
    		document.getElementById('select_search_result_columns_div').style.display = 'none';
       		document.getElementById('search_result_div').style.display = 'none';
    		document.getElementById('no_search_result_found_h6').style.display = 'none';
//    		$('#select_advanced_search_criteria').attr('disabled',false);
//    		$('#select_search_result_columns').attr('disabled',false);
//    		$('#advanced_search_btn').attr('disabled',false);
//    		$('#advanced_search_add_option_btn').attr('disabled',false);
    		break;
    		
    	case 'advanced_search_add_option_btn':
    		
    		if($('#select_advanced_search_criteria :selected').val()) {
    			switch ($('#select_advanced_search_criteria :selected').val()) {
				case 'location': case 'form_type': case 'form_version': case 'samples_consented_to': case 'consent_exclusions':
	        		processPatientConsentInfectionRisk('GET-VARIOUS-SEARCH-OPTIONS',$('#select_advanced_search_criteria :selected'),false);
					break;
				default:
            		addItemsToList('POPULATE-ADVANCED-SEARCH-OPTIONS-' + $('#select_advanced_search_criteria :selected').val().toUpperCase(), 
            				document.getElementById('advanced_search_options_div'),null,null);
					break;
				}
        		document.getElementById('select_search_result_columns_div').style.display = '';
    		}
    		break;
    		
    	case 'discard_audit_sample_btn':
    	
    		if(document.getElementById('aud_sample_pid').value == null || document.getElementById('aud_sample_pid').value == 0) {
    			alert('No audit sample selected. Nothing to discard');
    			return false;
    		}
    		processPatientConsentInfectionRisk('DISCARD-SAMPLE-AUDIT-FROM-SESSION',document.getElementById('aud_sample_pid'),false);
    		break;

    	case 'save_consent_audit_btn':
    		
    		if(document.getElementById('audit_sample_div').style.display == '' 
    			&& validateFormFields('validate_audit_sample',null,'',true) == false) {
    			return false;
    		}
    		
    		processWaitingButtonSpinner('START-CONSENT-AUDIT');
    		processPatientConsentInfectionRisk('SAVE-CONSENT-AUDIT',null,false);

    		break;
    		
    	case 'patient_locked_btn':
    		
    		alert($(whichInput).attr('id'));
    		break;
    		
    	case 'validate_imported_consent_btn': case 'audit_consent_btn':

        	switch ($(whichInput).attr('name')) {
        	case 'validate_imported_consent_btn':
        		if ($('input[name=import_consent_radio]:checked').val()) {
            		processWaitingButtonSpinner('START-VIEW-IMPORT-CONSENT');
            		processPatientConsentInfectionRisk('CHECK-PATIENT-STATUS-IMPORTED-CONSENT-AND-SUBMIT',$('input[name=import_consent_radio]:checked'),false);
        		} else {
        			alert('You must select a task above to view it');
        		}
        		break
        	case 'audit_consent_btn':
        		if ($('input[name=audit_consent_radio]:checked').val()) {
	        		processWaitingButtonSpinner('START-VIEW-AUDIT-CONSENT');
	        		processPatientConsentInfectionRisk('CHECK-PATIENT-STATUS-AUDIT-CONSENT-AND-SUBMIT',$('input[name=audit_consent_radio]:checked'),false);
        		} else {
        			alert('You must select a task above to view it');
        		}
        		break
        	}
    		break;
    		
    	case 'action_cancel_btn':
    		
    		$('.my_waiting_modal').modal('show');
    		document.action_form.action = 'user_actions';
			document.action_form.submit();
    		break;
    		
    	case 'user_action_radio': case 'audit_consent_radio':  case 'import_consent_radio': 

    	    checked = $(whichInput).is(':checked');
       	    $('input[name=' + $(whichInput).attr('name') + ']:checkbox').attr('checked', false)
    	    if(checked) {
    	        $(whichInput).prop('checked',true);
    	    }
    	    break;

    	case 'unlock_action_btn': 

    		if($('input[name=locked_task_radio]:checked').length > 0 || $('input[name=locked_patient_radio]:checked').length > 0) {
        		
    			if($('input[name=locked_task_radio]:checked').length > 0) {
        			processWaitingButtonSpinner('START-LOCKED-ACTION');
            		$('input[name=locked_task_radio]').each(function () {
                		processPatientConsentInfectionRisk('UNLOCK-ACTION-AND-REFRESH',this,false);
            		});
        			processWaitingButtonSpinner('END-LOCKED-ACTION');
        		}
        		
        		if($('input[name=locked_patient_radio]:checked').length > 0) {
        			processWaitingButtonSpinner('START-LOCKED-ACTION');
            		$('input[name=locked_patient_radio]').each(function () {
                		processPatientConsentInfectionRisk('UNLOCK-PATIENT-AND-REFRESH',this,false);
            		});
        			processWaitingButtonSpinner('END-LOCKED-ACTION');
        		}
    			
        		document.user_actions_form.action = 'select_department_locations';
        		$('.my_waiting_modal').modal('show');
    			document.user_actions_form.submit();
    			
    		} else {
    			
    			alert('You must select a task above to unlock it');
    			
    		}
    		break;
    		
    	case 'view_searched_patient': 
		
    		processWaitingButtonSpinner('START-SEARCH-PATIENT');
    		processPatientConsentInfectionRisk('CHECK-PATIENT-LOCKED-AND-SUBMIT',whichInput,false);
    		break;

    	case 'view_action_btn': 

    		if ($('input[name=user_action_radio]:checked').val()) {
    			processWaitingButtonSpinner('START-USER-ACTION');
        		processPatientConsentInfectionRisk('CHECK-ACTION-LOCKED-AND-SUBMIT',$('input[name=user_action_radio]:checked'),false);
    		} else {
    			alert('You must select a task above to view it');
    		}
    		break;
    	
    	case 'prev_action_chk_boxes': case 'view_timeline_radio':

    	    checked = $(whichInput).is(':checked');
    	    $('input[name=' + $(whichInput).attr('name') + ']:checkbox').attr('checked', false)
    	    if(checked) {
    	        $(whichInput).prop('checked',true);
    	        switch ($(whichInput).attr('name')) {
    	        case 'prev_action_chk_boxes':
        	    	processPatientConsentInfectionRisk('GET-SINGLE-COMPLETED-ACTION',whichInput,false);
    	        	break;
    	        case 'view_timeline_radio':
        	    	processPatientConsentInfectionRisk('GET-SINGLE-TIMELINE',whichInput,false);
    	        	break;
    	        }
    	    } else {
    	        switch ($(whichInput).attr('name')) {
    	        case 'prev_action_chk_boxes':
            	    document.getElementById('prev_user_action_notes').value = '';
    	        	break;
    	        case 'view_timeline_radio':
            	    document.getElementById('view_timeline_notes').value = '';
            	    document.getElementById('view_timeline_notes_div').style.display = 'none';
    	        	break;
    	        }
    	    }
    		
    		break;

    	case 'completed_task_radio': 

      		initialiseForm('ACTION',null);
      		
    	    checked = $(whichInput).is(':checked');
    	    $('input[name=' + $(whichInput).attr('name') + ']:checkbox').attr('checked', false)
    	    if(checked) {
    	        $(whichInput).prop('checked',true);
    			processPatientConsentInfectionRisk('GET-SINGLE-COMPLETED-ACTION-FROM-SESSION',whichInput,false);
    	    }
    		break;

    	case 'prev_sample_chk_boxes':
    		
    		$('.prev_sample_chk_boxes').on('change', function() {
    		    $('.prev_sample_chk_boxes').not(this).prop('checked', false);
    		});	
    	    if ($(whichInput).prop('checked') == true) {
    	    	processPatientConsentInfectionRisk('GET-SINGLE-AUDIT-SAMPLE-FROM-SESSION',whichInput,false);
    	    } else {
    	    	initialiseForm('AUDIT-SAMPLE', null);
    	   		document.getElementById('audit_sample_div').style.display = 'none';
    	   		document.getElementById('add_audit_sample_btn').style.display = '';
        		document.getElementById('discard_audit_sample_btn').style.display = '';
    	   		document.getElementById('save_audit_sample_btn').style.display = 'none';
    	    }
    		break;
    		
    	case 'prev_infection_chk_boxes':
    		
    		$('.prev_infection_chk_boxes').on('change', function() {
    		    $('.prev_infection_chk_boxes').not(this).prop('checked', false);
    		});	
    		
    	    if ($(whichInput).prop('checked') == true) {
    	    	processPatientConsentInfectionRisk('GET-SINGLE-INFECTION-RISK-FROM-SESSION',whichInput,false);
    	    } else {
    	    	initialiseForm('INFECTION-RISK', null);
    	   		document.getElementById('infection_risk_body_div').style.display = 'none';
    			$('#infection_risk_body_div *').prop('disabled',true);
           		if(window.location.pathname.includes('view_infection_risk') || window.location.pathname.includes('view_searched_infection_risk')) {
        	   		document.getElementById('add_another_infection_risk_btn').style.display = '';
        	   		document.getElementById('save_all_btn').style.display = 'none';
        	   		document.getElementById('edit_patient_btn').style.display = '';
        	   		document.getElementById('edit_consent_btn').style.display = 'none';
        	   		document.getElementById('discard_infection_risk_btn').style.display = 'none';
        	   		document.getElementById('save_infection_risk_btn').style.display = 'none';
           		} else if(window.location.pathname.includes('add_infection_risk')) {
        	   		document.getElementById('add_another_infection_risk_btn').style.display = '';
        	   		document.getElementById('save_all_btn').style.display = '';
        	   		document.getElementById('edit_patient_btn').style.display = 'none';
        	   		document.getElementById('edit_consent_btn').style.display = '';
        	   		document.getElementById('discard_infection_risk_btn').style.display = 'none';
        	   		document.getElementById('save_infection_risk_btn').style.display = 'none';
           		}
    	    }
    		
    		break;

    	case 'discard_infection_risk_btn':
    		
    		processPatientConsentInfectionRisk('DISCARD-INFECTION-RISK-FROM-SESSION-IRS',document.getElementById('infection_risk_id'),false);
    		document.getElementById('discard_infection_risk_btn').style.display = 'none';
       		document.getElementById('infection_risk_body_div').style.display = 'none';
    		$('#infection_risk_body_div *').prop('disabled',true);

       		if(window.location.pathname.includes('view_infection_risk') || window.location.pathname.includes('view_searched_infection_risk')) {
        		document.getElementById('add_another_infection_risk_btn').style.display = '';
    	   		document.getElementById('edit_patient_btn').style.display = '';
    	   		document.getElementById('edit_consent_btn').style.display = 'none';
    	   		document.getElementById('save_all_btn').style.display = 'none';
       		} else if(window.location.pathname.includes('add_infection_risk')) {
        		document.getElementById('add_another_infection_risk_btn').style.display = '';
    	   		document.getElementById('edit_patient_btn').style.display = 'none';
    	   		document.getElementById('edit_consent_btn').style.display = '';
    	   		document.getElementById('save_all_btn').style.display = '';
       		}
	   		document.getElementById('save_infection_risk_btn').style.display = 'none';
    		break;
    	
    	case 'add_audit_sample_btn':
    		
    		if($('.prev_audit_samples_chk_boxes:checked').size() > 0) {
    			alert('Please untick the previous audit sample(s) in order to add a new one');
        		return false;
    		}
    		
		    $('.prev_audit_samples_chk_boxes').prop('checked', false);
		    initialiseForm('AUDIT-SAMPLE',null);
    		processPatientConsentInfectionRisk('ADD-NEW-AUDIT-SAMPLE-TO-SESSION',null,false);
    		document.getElementById('add_audit_sample_btn').style.display = 'none';
    		document.getElementById('discard_audit_sample_btn').style.display = '';
    		document.getElementById('audit_sample_div').style.display = '';
       		document.getElementById('save_audit_sample_btn').style.display = '';
	   		
    		break;
    		
    	case 'add_another_infection_risk_btn':
    		
    		if($('.prev_infection_chk_boxes:checked').size() > 0) {
    			alert('Please untick the previous infection risk in order to add a new one');
        		return false;
    		}
    		
		    $('.prev_infection_chk_boxes').prop('checked', false);
		    initialiseForm('INFECTION-RISK',null);
    		processPatientConsentInfectionRisk('ADD-NEW-INFECTION-RISK-TO-SESSION-IRS',null,false);
    		document.getElementById('add_another_infection_risk_btn').style.display = 'none';
    		document.getElementById('discard_infection_risk_btn').style.display = '';
       		document.getElementById('infection_risk_body_div').style.display = '';
	   		document.getElementById('save_infection_risk_btn').style.display = '';
	   		
    		$('#infection_risk_body_div *').prop('disabled',false);
    		
    		if(window.location.pathname.includes('view_infection_risk') || window.location.pathname.includes('view_searched_infection_risk')) {
    	   		document.getElementById('save_all_btn').style.display = 'none';
    	   		document.getElementById('edit_patient_btn').style.display = '';
    	   		document.getElementById('edit_consent_btn').style.display = 'none';
    		} else if(window.location.pathname.includes('add_infection_risk')) {
    	   		document.getElementById('save_all_btn').style.display = '';
    	   		document.getElementById('edit_patient_btn').style.display = 'none';
    	   		document.getElementById('edit_consent_btn').style.display = '';
    		}
	   		document.getElementById('edit_infection_risk_btn').style.display = 'none';

	   		break;
    	
    	case 'edit_infection_risk_btn':

       		if(window.location.pathname.includes('infection_risk_found_notify_om') || window.location.pathname.includes('infection_risk_found_notify_tech')
       			|| window.location.pathname.includes('infection_risk_unknown') || window.location.pathname.includes('infection_risk_unknown_notify_tech')
       			|| window.location.pathname.includes('consent_withdrawn')) {
           		if(window.location.pathname.includes('infection_risk_found_notify_om') || window.location.pathname.includes('infection_risk_found_notify_tech')
           				|| window.location.pathname.includes('infection_risk_unknown_notify_tech')) {
            		$('#infection_risk_notes').prop('disabled',false);
           		} else {
            		$('#infection_risk_body_div *').prop('disabled',false);
           		}
           		document.getElementById('save_infection_risk_btn').style.display = '';
           		document.getElementById('save_infection_risk_btn').onclick = function() {
           			validateFormFields('validate_save_session_infection_risk',null,null,true);};
           		document.getElementById('edit_infection_risk_btn').style.display = 'none';
       		} else {
           		processWaitingButtonSpinner('START-INFECTION-RISK');
        		processPatientConsentInfectionRisk('ALLOWING-USER-TO-EDIT-INFECTION-RISK',document.getElementById('ir_patient_id'),false);
       		}
    		break;

    	case 'save_infection_risk_btn':

    		$('#infection_risk_body_div *').prop('disabled',true);
       		document.getElementById('save_infection_risk_btn').style.display = 'none';
       		if(window.location.pathname.includes('add_infection_risk') || window.location.pathname.includes('view_infection_risk')
       			|| window.location.pathname.includes('view_searched_infection_risk')) {
           		document.getElementById('add_another_infection_risk_btn').style.display = '';
       		}       		
       		document.getElementById('edit_infection_risk_btn').style.display = '';
    		break;

    	case 'edit_consent_btn':
    		
    		if(window.location.pathname.includes('add_infection_risk')) {
    			$('.my_waiting_modal').modal('show');
    	    	document.infection_risk_form.action = 'edit_consent';
    	       	document.infection_risk_form.submit();
    		} else {
        		if(document.getElementById('is_validated').value.toLowerCase().includes('yes') 
        				&& !document.getElementById('consent_access').value.toLowerCase().includes('edit')) {
            		alert('You are not allowed to edit a validated consent');
            		return false;
        		} else {
            		$('#consent_body_div *').prop('disabled',false);
            	    $('#select_consent_exclusions').selectpicker('refresh');
            	    $('#select_samples_consented_to').selectpicker('refresh');
               		document.getElementById('save_consent_btn').style.display = '';
                	if(document.getElementById('is_imported').value.toLowerCase() == 'yes') {
                   		document.getElementById('save_consent_btn').onclick = function() {validateFormFields('validate_consent',this,'update_consent',false)};
                	} 
               		document.getElementById('edit_consent_btn').style.display = 'none';
        		}
    		}
    		break;
    	
    	case 'confirm_notification_consent_btn':
    		
       		if(window.location.pathname.includes('remove_samples') || window.location.pathname.includes('consent_withdrawn')) {
				document.consent_form.action = 'save_action';
				$('.my_waiting_modal').modal('show');
		       	document.consent_form.submit();
       		}
    		break;
    		
    	case 'save_consent_btn':
    		
    		$('#consent_body_div *').prop('disabled',true);
       		$('#consent_exclusion_btn').attr('disabled',false);
    	    if(document.getElementById('select_samples_consented_to_div').style.display == '') {
    	   		$('#samples_consented_to_btn').attr('disabled',false);
    	    }
       		document.getElementById('save_consent_btn').style.display = 'none';
       		document.getElementById('edit_consent_btn').style.display = '';
    		
    		break;
    		
    	case 'edit_patient_btn':
    		
    		if(window.location.pathname.includes('edit_consent')) {
    			$('.my_waiting_modal').modal('show');
    	    	document.consent_form.action = 'edit_patient';
    	       	document.consent_form.submit();
    		} else {
        		processWaitingButtonSpinner('START-PATIENT');
           		processPatientConsentInfectionRisk('ALLOWING-USER-TO-EDIT-PATIENT',document.getElementById('patient_id'),false);
    		}
    		
    		break;

    	case 'save_patient_btn':
    		
    		$('#patient_body_div *').prop('disabled',true);
       		document.getElementById('save_patient_btn').style.display = 'none';
       		document.getElementById('edit_patient_btn').style.display = '';
    		break;
    		
    	case 'clear_consent_data_btn':
    		
    		initialiseForm('CONSENT',null);
    		break;
    		
    	case 'select_consent_exclusions':
    		
			var select_consent_exclusions = document.getElementById('select_consent_exclusions');
	    	for(i=0; i < select_consent_exclusions.options.length;i++){
	    		if (select_consent_exclusions.options[i].selected == true) {
	    			selectedCheckBoxValues.push([select_consent_exclusions.options[i].value]);
	    		}
	    	}		
    	    document.getElementById('consent_exclusions').value = selectedCheckBoxValues;
    		break;
    	
    	case 'select_samples_consented_to':
    		
			var select_samples_consented_to = document.getElementById('select_samples_consented_to');
    		
	    	for(i=0; i < select_samples_consented_to.options.length;i++){
	    		if (select_samples_consented_to.options[i].selected == true) {
	    			selectedCheckBoxValues.push([select_samples_consented_to.options[i].value]);
	    		}
	    	}		
    	    document.getElementById('samples_consented_to').value = selectedCheckBoxValues;
    		break;
    	
    	default:

    		if(document.getElementById($(whichInput).attr('id'))) {
        	    if ($(whichInput).attr('id').includes('link')) {
            		$('.my_waiting_modal').modal('show');
            	}
    		}
    		break;
    	}
    }
}
function validateFormFields(whatToProcess,whichButtonId,actionType,returnFalseOnEachError)
{	
	switch (whatToProcess.toLowerCase()) {
	case 'validate_audit_sample':
		
		if (!checkEmpty(document.getElementById('aud_sample_id'),'Sample ID') 
				|| !checkEmpty(document.getElementById('aud_select_sample_type_id'),'Sample Type') 
				|| !checkEmpty(document.getElementById('after_consent_date'),'Sample Collected After Consent Date') 
				|| !checkEmpty(document.getElementById('sample_in_assigned_location'),'Sample In Assigned Location') 
				|| !checkEmpty(document.getElementById('sample_details_legible'),'Sample Details Legible') 
				|| !checkEmpty(document.getElementById('appropriate_consent_present'),'Consent Appropriate')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
		}
    	if(!checkEmpty(document.getElementById('sample_date'),'Sample Date')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
    	} else {
    		if(ValidateAllDates(document.getElementById('sample_date').id) == false){
        		if(returnFalseOnEachError) {
            		return false;
        		}
    		}
    	}
    	
		processWaitingButtonSpinner('START-CONSENT-AUDIT');
		uploadFormDataToSessionObjects('SAVE-FULL-AUDIT-SAMPLE', null);
    	
		break;
		
	case 'validate_imported_consent_verify':

    	if (confirm('Are you sure you want to finalise this imported consent?') == false) {
    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
			return false;
		}
		
		if(validateFormFields('validate_action',null,'finalise_imported_consent',true) == false) {
    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
			return false;
		}
    	
		break;
	
	case 'validate_action':
		
		if (!checkEmpty(document.getElementById('action_type'),'Action To Send')) {
			return false;
		}		
       	
		if(actionType) {
			document.action_form.action = actionType;
			$('.my_waiting_modal').modal('show');
	       	document.action_form.submit();
		}

       	break;

	case 'validate_consent_audit_verify': 

    	if (document.getElementById('verbal_consent').value.toLowerCase().includes('yes')) {
			if (!checkEmpty(document.getElementById('aud_verbal_document_checked'),'Verbal Document Checked')) {
	    		if(returnFalseOnEachError) {
	        		return false;
	    		}
			}
			document.getElementById('aud_digital_cf_attached').value = '';
			document.getElementById('aud_physical_consent_form').value = '';
			document.getElementById('aud_cf_physical_location').value = '';
			document.getElementById('aud_date_of_consent_stated').value = '';
			document.getElementById('aud_patient_signature').value = '';
			document.getElementById('aud_person_taking_consent').value = '';
			document.getElementById('aud_cf_validity').value = '';
    	} else {
			if (!checkEmpty(document.getElementById('aud_digital_cf_attached'),'Digital CF Attached') 
					|| !checkEmpty(document.getElementById('aud_physical_consent_form'),'Physical Consent Form') 
					|| !checkEmpty(document.getElementById('aud_cf_physical_location'),'CF Physical Location') 
					|| !checkEmpty(document.getElementById('aud_date_of_consent_stated'),'Consent Date Stated') 
					|| !checkEmpty(document.getElementById('aud_patient_signature'),'Patient Signature') 
					|| !checkEmpty(document.getElementById('aud_person_taking_consent'),'Person Taking Consent') 
					|| !checkEmpty(document.getElementById('aud_cf_validity'),'CF Validity')) {
	    		if(returnFalseOnEachError) {
	        		return false;
	    		}
			}
			document.getElementById('aud_verbal_document_checked').value = '';
    	} 
    	if (!checkEmpty(document.getElementById('aud_verify_consent_exclusions'),'Verify Consent Exclusions')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
    	} else {
        	if (document.getElementById('aud_verify_consent_exclusions').value.toLowerCase().includes('no')) {
    			if (!checkEmpty(document.getElementById('aud_statements_excluded'),'Consent Excluded Statements')) {
    	    		if(returnFalseOnEachError) {
    	        		return false;
    	    		}
    			}
        	} else {
    			document.getElementById('aud_statements_excluded').value = '';
        	}
    	}
		if (!checkEmpty(document.getElementById('aud_data_discrepancies_identified'),'Data Discrepancies Identified')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
		}
		if (document.getElementById('aud_data_discrepancies_identified').value.toLowerCase().includes('no')) {
			document.getElementById('aud_source_of_verified_data').value = '';
			document.getElementById('discrepancies_description').value = '';
    	}		
		if (!checkEmpty(document.getElementById('samples_obtained_electronically'),'Samples Obtained Electronically')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
		}

		if (!checkEmpty(document.getElementById('sample_missing'),'Sample Missing')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
		}
		
		if (!checkEmpty(document.getElementById('primary_auditor'),'Primary Auditor') 
				|| !checkEmpty(document.getElementById('secondary_auditor'),'Secondary Auditor') 
				|| !checkEmpty(document.getElementById('audit_date'),'Audit Date'))
		{
    		if(returnFalseOnEachError) {
        		return false;
    		}
		}

		if(document.getElementById('audit_sample_div').style.display == '' 
			&& validateFormFields('validate_audit_sample',null,'',true) == false) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
		}
		
    	if (confirm('Are you sure you want to finalise this audit?') == false) {
			return false;
		}
		$('.my_waiting_modal').modal('show');
		uploadFormDataToSessionObjects('FINALISE-CONSENT-AUDIT', null);
		
		break;
		
	case 'validate_consent_verify': case 'save_validate_consent_data':
		
		processWaitingButtonSpinner('START-CONSENT-VALIDATE');
		if(document.getElementById('consent_validate_div').style.display == 'none') {
    		alert('Invalid Action Selected');
    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
    		return false;
    	}

    	if (document.getElementById('verbal_consent').value.toLowerCase().includes('yes')) {
			if (!checkEmpty(document.getElementById('verbal_document_checked'),'Verbal Document Checked') 
					|| !checkEmpty(document.getElementById('verbal_consent_checked_date'),'Verbal Consent Checked Date')
					|| !checkEmpty(document.getElementById('verbal_consent_checked_by'),'Verbal Consent Checked By')) {
	    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
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
					|| !checkEmpty(document.getElementById('cf_checked_by'),'CF Checked By')) {
	    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
	    		return false;
			}
			document.getElementById('verbal_document_checked').value = '';
			document.getElementById('verbal_consent_checked_date').value = '';
			document.getElementById('verbal_consent_checked_by').value = '';
    	} 
    	if (!checkEmpty(document.getElementById('verify_consent_exclusions'),'Verify Consent Exclusions')) {
    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
    		return false;
    	} else {
        	if (document.getElementById('verify_consent_exclusions').value.toLowerCase().includes('no')) {
    			if (!checkEmpty(document.getElementById('statements_excluded'),'Consent Excluded Statements')) {
    	    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
    	    		return false;
    			}
        	} else {
    			document.getElementById('statements_excluded').value = '';
        	}
    	}
		if (!checkEmpty(document.getElementById('data_discrepancies_identified'),'Data Discrepancies Identified') 
				|| !checkEmpty(document.getElementById('data_accuracy_date'),'Data Accuracy Date')
				|| !checkEmpty(document.getElementById('data_accuracy_checked_by'),'Data Accuracy Checked By')) {
    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
    		return false;
		}
    	if (document.getElementById('data_discrepancies_identified').value.toLowerCase().includes('yes')) {
			if (!checkEmpty(document.getElementById('source_of_verified_data'),'Source Verified Data')
					|| !checkEmpty(document.getElementById('data_discrepancies_description'),'Data Discrepancies Description')) {
	    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
	    		return false;
			}
    	} else {
			document.getElementById('source_of_verified_data').value = '';
			document.getElementById('data_discrepancies_description').value = '';
			document.getElementById('data_discrepancies_verified').value = '';
			document.getElementById('data_discrepancies_verification_date').value = '';
			document.getElementById('data_discrepancies_verified_by').value = '';
    	}		

		switch (whatToProcess.toLowerCase()) {
		case 'save_validate_consent_data':
    		uploadFormDataToSessionObjects('SAVE-FULL-CONSENT-VALIDATE', null);
			break;
		case 'validate_consent_verify':
			if(validateFormFields('validate_action',null,'',true) == false) {
	    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
				return false;
			}
	    	if (confirm('Are you sure you want to validate this consent?') == false) {
	    		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
				return false;
			}
    		uploadFormDataToSessionObjects('FULL-CONSENT-VALIDATE', null);
			break;
		}
		processWaitingButtonSpinner('END-CONSENT-VALIDATE');
    	break;
		
    case 'validate_patient': case 'validate_save_session_patient': case 'just_validate_patient':

    	if(!checkEmpty(document.getElementById('patient_surname'),'Surname')
    		|| !checkSpecialUserInput(document.getElementById('patient_surname'),'')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
    	}
    	if(!checkEmpty(document.getElementById('patient_firstname'),'Forename')
    		|| !checkSpecialUserInput(document.getElementById('patient_firstname'),'')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
    	}
    	if(!checkEmpty(document.getElementById('select_gender'),'Gender')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
    	} else {
    		document.getElementById('gender').value = document.getElementById('select_gender').value;
    	}
		if (!checkEmpty(document.getElementById('select_volunteer'),'Volunteer')) {
    		if(returnFalseOnEachError) {
        		return false;
    		}
		} else {
    		document.getElementById('volunteer').value = document.getElementById('select_volunteer').value;
        	if (document.getElementById('select_volunteer').value.toLowerCase().includes('no')) {
            	if(!checkEmpty(document.getElementById('date_of_birth'),'Date Of Birth')) {
            		if(returnFalseOnEachError) {
                		return false;
            		}
            	} else {
            		if(ValidateAllDates(document.getElementById('date_of_birth').id) == false){
	            		return false;
            		}
            	}
            	if(!checkEmpty(document.getElementById('hospital_number'),'Hospital No') 
            		|| !checkSpecialUserInput(document.getElementById('hospital_number'),'')) {
            		if(returnFalseOnEachError) {
                		return false;
            		}
            	}
            	if(!checkEmpty(document.getElementById('nhs_number'),'NHS No')
            			|| !checkSpecialUserInput(document.getElementById('nhs_number'),'Invalid characters found')) {
            		if(returnFalseOnEachError) {
                		return false;
            		}
            	}
            	document.getElementById('age').value = '';
    		} else if (document.getElementById('select_volunteer').value.toLowerCase().includes('yes')) {
    			document.getElementById('date_of_birth').value = '';
    			document.getElementById('hospital_number').value = '';
    			document.getElementById('nhs_number').value = '';
    			document.getElementById('age').value = document.getElementById('select_age').value;
        	}
		}

		switch (whatToProcess.toLowerCase()) {
    	case 'validate_save_session_patient':
    		processWaitingButtonSpinner('START-PATIENT');
    		uploadFormDataToSessionObjects('FULL-PATIENT', null);
    		break;
    	default:
        	if(actionType != null) {
    	       	document.patient_form.action = actionType;
    			$('.my_waiting_modal').modal('show');
    	       	document.patient_form.submit();
        	}
    		break;
    	}
    	
    	break;
	
//    case 'validate_consent': case 'validate_save_session_consent': case 'just_validate_consent':
//    	
//    	if(!$('#sam_coll_before_sep_2006').prop('disabled')) { // If the first input field is disabled then don't validate
//
//			if (!checkEmpty(document.getElementById('sam_coll_before_sep_2006'),'Sample collection before Sep 2006')) {
//        		if(returnFalseOnEachError) {
//            		return false;
//        		}
//			}		
//	    	
//	    	if (!document.getElementById('sam_coll_before_sep_2006').value.toLowerCase().includes('yes')){
//
//	        	if (!checkEmpty(document.getElementById('date_of_consent'),'Date Of Consent')) {
//	        		if(returnFalseOnEachError) {
//	            		return false;
//	        		}
//            	} else {
//            		if(ValidateAllDates(document.getElementById('date_of_consent').id) == false){
//	            		return false;
//            		}
//	        	}
//	        	if (!checkEmpty(document.getElementById('consent_taken_by'),'Consent Taken By')) {
//	        		if(returnFalseOnEachError) {
//	            		return false;
//	        		}
//	        	}
//	        	if (!checkEmpty(document.getElementById('loc_id'),'Location')) {
//	        		if(returnFalseOnEachError) {
//	            		return false;
//	        		}
//	        	}
//	        	if (!checkEmpty(document.getElementById('verbal_consent'),'Verbal Consent')) {
//	        		if(returnFalseOnEachError) {
//	            		return false;
//	        		}
//	        	}
//	        	if (document.getElementById('verbal_consent').value.toLowerCase().includes('yes')) {
//		        	if (!checkEmpty(document.getElementById('verbal_consent_recorded'),'Verbal Consent Recorded')) {
//		        		if(returnFalseOnEachError) {
//		            		return false;
//		        		}
//		        	}
//		        	if (!checkEmpty(document.getElementById('verbal_consent_recorded_by'),'Verbal Consent Recorded By')) {
//		        		if(returnFalseOnEachError) {
//		            		return false;
//		        		}
//		        	}
//		        	if (!checkEmpty(document.getElementById('verbal_consent_document_file_label'),'Verbal Consent Document')) {
//		        		if(returnFalseOnEachError) {
//		            		return false;
//		        		}
//		        	}
//	    			processPatientConsentInfectionRisk('REMOVE-FILE',document.getElementById('digital_cf_attachment_file_button'),false);			
//	        		document.getElementById('form_type').value = '';
//	        		document.getElementById('form_version_id').value = '';
//	        		document.getElementById('consent_type').value = '';
//	        		document.getElementById('consent_exclusions').value = '';
//	        	} else {
//		        	if (!checkEmpty(document.getElementById('form_type'),'Form Type')) {
//		        		if(returnFalseOnEachError) {
//		            		return false;
//		        		}
//		        	}
//		        	if (!checkEmpty(document.getElementById('select_form_version_id'),'Form Version')) {
//		        		if(returnFalseOnEachError) {
//		            		return false;
//		        		}
//		        	}
//		        	if (!checkEmpty(document.getElementById('digital_cf_attachment_file_label'),'Digital CF')) {
//		        		if(returnFalseOnEachError) {
//		            		return false;
//		        		}
//		        	}
//		        	if (!checkEmpty(document.getElementById('consent_type'),'Consent Type')) {
//		        		if(returnFalseOnEachError) {
//		            		return false;
//		        		}
//		        	}
//	        		document.getElementById('form_version_id').value = document.getElementById('select_form_version_id').value;
//	            	if (document.getElementById('consent_type').value.toLowerCase().includes('partial')) {
//	        			if (!checkEmpty(document.getElementById('select_consent_exclusions'),'Consent Exclusions')) {
//			        		if(returnFalseOnEachError) {
//			            		return false;
//			        		}
//	        			}
//	            	} else {
//	            		document.getElementById('consent_exclusions').value = '';
//	            	}
//	        		document.getElementById('verbal_consent_recorded').value = '';
//	        		document.getElementById('verbal_consent_recorded_by').value = '';
//	        		document.getElementById('verbal_consent_document_file').value = '';
//	        		if(document.getElementById('verbal_consent_document_file_label').innerHTML != '') {
//	        			processPatientConsentInfectionRisk('REMOVE-FILE',document.getElementById('verbal_consent_document_file_button'),false);
//	        		} 
//	        	}
//	        	if (!checkEmpty(document.getElementById('withdrawn'),'Consent Withdrawn')) {
//	        		if(returnFalseOnEachError) {
//	            		return false;
//	        		}
//	        	}
//            	if (document.getElementById('withdrawn').value.toLowerCase().includes('yes')) {
//    	        	if (!checkEmpty(document.getElementById('withdrawal_date'),'Withdrawal Date')) {
//    	        		if(returnFalseOnEachError) {
//    	            		return false;
//    	        		}
//                	} else {
//                		if(ValidateAllDates(document.getElementById('withdrawal_date').id) == false){
//    	            		return false;
//                		}
//    	        	}
//    	        	if (!checkEmpty(document.getElementById('withdrawal_document_file_label'),'Withdrawal Document')) {
//    	        		if(returnFalseOnEachError) {
//    	            		return false;
//    	        		}
//    	        	}
//            	} else { 
//            		if (!(document.getElementById('withdrawal_document_id').value > 0)) { // Always show any previous withdrawl date and document
//                		document.getElementById('withdrawal_date').value = '';
//                		document.getElementById('withdrawal_document_file').value = '';
//                		if(document.getElementById('withdrawal_document_file_label').innerHTML != '') {
//                    		processPatientConsentInfectionRisk('REMOVE-FILE',document.getElementById('withdrawal_document_file'),false);
//                		}
//            		}
//            	}
//	        	if (!checkEmpty(document.getElementById('stop_sample_donation'),'Stop Sample Donation')) {
//	        		if(returnFalseOnEachError) {
//	            		return false;
//	        		}
//	        	}
//            	if (document.getElementById('stop_sample_donation').value.toLowerCase().includes('yes')) {
//        			if (!checkEmpty(document.getElementById('stop_sample_donation_date'),'Stop Sample Donation Date')) {
//    	        		if(returnFalseOnEachError) {
//    	            		return false;
//    	        		}
//                	} else {
//                		if(ValidateAllDates(document.getElementById('stop_sample_donation_date').id) == false){
//    	            		return false;
//                		}
//        			}
//            	} else {
//            		document.getElementById('stop_sample_donation_date').value = '';
//            	}
//	        	switch (document.getElementById('user_department').value.toUpperCase()) {
//	    		case 'HOTB':
//	    			if (!checkEmpty(document.getElementById('select_samples_consented_to'),'Consented Samples')) {
//    	        		if(returnFalseOnEachError) {
//    	            		return false;
//    	        		}
//	    			}
//	    			break;
//	    		}
//	    	}
//    	}
//    	
//    	switch (whatToProcess.toLowerCase()) {
//    	case 'validate_save_session_consent':
//    		processWaitingButtonSpinner('START-CONSENT');
//    		uploadFormDataToSessionObjects('FULL-CONSENT', null);
//    		break;
//    	default:
//	    	if(actionType != null) {
//				$('.my_waiting_modal').modal('show');
//		    	document.consent_form.action = actionType;
//		       	document.consent_form.submit();
//	    	}
//    		break;
//    	}
//
//       	break;

    case 'validate_consent': case 'validate_save_session_consent': case 'just_validate_consent':
    	
    	if(document.getElementById('sam_coll_before_sep_2006') && !$('#sam_coll_before_sep_2006').prop('disabled') 
    			&& !document.getElementById('sam_coll_before_sep_2006').value.toLowerCase().includes('yes')) { // If the first input field is disabled then don't validate

			if (!checkEmpty(document.getElementById('sam_coll_before_sep_2006'),'Sample collection before Sep 2006')) {
        		if(returnFalseOnEachError) {
            		return false;
        		}
			}		
	    	
        	if (!checkEmpty(document.getElementById('date_of_consent'),'Date Of Consent')) {
        		if(returnFalseOnEachError) {
            		return false;
        		}
        	} else {
        		if(ValidateAllDates(document.getElementById('date_of_consent').id) == false){
            		return false;
        		}
        	}
        	if (!checkEmpty(document.getElementById('consent_taken_by'),'Consent Taken By')) {
        		if(returnFalseOnEachError) {
            		return false;
        		}
        	}
        	if (!checkEmpty(document.getElementById('loc_id'),'Location')) {
        		if(returnFalseOnEachError) {
            		return false;
        		}
        	}
        	if (!checkEmpty(document.getElementById('verbal_consent'),'Verbal Consent')) {
        		if(returnFalseOnEachError) {
            		return false;
        		}
        	}
        	if (document.getElementById('verbal_consent').value.toLowerCase().includes('yes')) {
	        	if (!checkEmpty(document.getElementById('verbal_consent_recorded'),'Verbal Consent Recorded')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
	        	}
	        	if (!checkEmpty(document.getElementById('verbal_consent_recorded_by'),'Verbal Consent Recorded By')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
	        	}
	        	if (!checkEmpty(document.getElementById('verbal_consent_document_file_label'),'Verbal Consent Document')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
	        	}
    			processPatientConsentInfectionRisk('REMOVE-FILE',document.getElementById('digital_cf_attachment_file_button'),false);			
        		document.getElementById('form_type').value = '';
        		document.getElementById('form_version_id').value = '';
        		document.getElementById('consent_type').value = '';
        		document.getElementById('consent_exclusions').value = '';
        	} else {
	        	if (!checkEmpty(document.getElementById('form_type'),'Form Type')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
	        	}
	        	if (!checkEmpty(document.getElementById('select_form_version_id'),'Form Version')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
	        	}
	        	if (!checkEmpty(document.getElementById('digital_cf_attachment_file_label'),'Digital CF')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
	        	}
	        	if (!checkEmpty(document.getElementById('consent_type'),'Consent Type')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
	        	}
        		document.getElementById('form_version_id').value = document.getElementById('select_form_version_id').value;
            	if (document.getElementById('consent_type').value.toLowerCase().includes('partial')) {
        			if (!checkEmpty(document.getElementById('select_consent_exclusions'),'Consent Exclusions')) {
		        		if(returnFalseOnEachError) {
		            		return false;
		        		}
        			}
            	} else {
            		document.getElementById('consent_exclusions').value = '';
            	}
        		document.getElementById('verbal_consent_recorded').value = '';
        		document.getElementById('verbal_consent_recorded_by').value = '';
        		document.getElementById('verbal_consent_document_file').value = '';
        		if(document.getElementById('verbal_consent_document_file_label').innerHTML != '') {
        			processPatientConsentInfectionRisk('REMOVE-FILE',document.getElementById('verbal_consent_document_file_button'),false);
        		} 
        	}
        	if (!checkEmpty(document.getElementById('withdrawn'),'Consent Withdrawn')) {
        		if(returnFalseOnEachError) {
            		return false;
        		}
        	}
        	if (document.getElementById('withdrawn').value.toLowerCase().includes('yes')) {
	        	if (!checkEmpty(document.getElementById('withdrawal_date'),'Withdrawal Date')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
            	} else {
            		if(ValidateAllDates(document.getElementById('withdrawal_date').id) == false){
	            		return false;
            		}
	        	}
	        	if (!checkEmpty(document.getElementById('withdrawal_document_file_label'),'Withdrawal Document')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
	        	}
        	} else { 
        		if (!(document.getElementById('withdrawal_document_id').value > 0)) { // Always show any previous withdrawl date and document
            		document.getElementById('withdrawal_date').value = '';
            		document.getElementById('withdrawal_document_file').value = '';
            		if(document.getElementById('withdrawal_document_file_label').innerHTML != '') {
                		processPatientConsentInfectionRisk('REMOVE-FILE',document.getElementById('withdrawal_document_file'),false);
            		}
        		}
        	}
        	if (!checkEmpty(document.getElementById('stop_sample_donation'),'Stop Sample Donation')) {
        		if(returnFalseOnEachError) {
            		return false;
        		}
        	}
        	if (document.getElementById('stop_sample_donation').value.toLowerCase().includes('yes')) {
    			if (!checkEmpty(document.getElementById('stop_sample_donation_date'),'Stop Sample Donation Date')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
            	} else {
            		if(ValidateAllDates(document.getElementById('stop_sample_donation_date').id) == false){
	            		return false;
            		}
    			}
        	} else {
        		document.getElementById('stop_sample_donation_date').value = '';
        	}
        	switch (document.getElementById('user_department').value.toUpperCase()) {
    		case 'HOTB':
    			if (!checkEmpty(document.getElementById('select_samples_consented_to'),'Consented Samples')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
    			}
    			break;
    		}
    	}
    	
    	switch (whatToProcess.toLowerCase()) {
    	case 'validate_save_session_consent':
    		processWaitingButtonSpinner('START-CONSENT');
    		uploadFormDataToSessionObjects('FULL-CONSENT', null);
    		break;
    	default:
	    	if(actionType != null) {
				$('.my_waiting_modal').modal('show');
		    	document.consent_form.action = actionType;
		       	document.consent_form.submit();
	    	}
    		break;
    	}

       	break;
    	
    case 'validate_infection_risk': case 'validate_save_session_infection_risk': case 'do_not_validate_just_submit': case 'just_validate_infection_risk':
    	
    	switch (whatToProcess.toLowerCase()) {
        case 'validate_infection_risk': case 'validate_save_session_infection_risk': case 'just_validate_infection_risk':

    		if(window.location.pathname.includes('add_infection_risk') && $('input[name="prev_infection_chk_boxes"]').length <= 0 
        			&& !document.getElementById('infection_risk_exist').value) {
        		alert('There must be at least ONE infection risk');
        		return false;
        	}
        	
        	if(document.getElementById('infection_risk_body_div').style.display == '') {
        		if (!checkEmpty(document.getElementById('infection_risk_exist'),'Infection Risk')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
        		}
        	}
        	
        	if (document.getElementById('infection_risk_exist').value.toLowerCase().includes('yes')
        			|| document.getElementById('infection_risk_exist').value.toLowerCase().includes('unknown')) {
        		
        		if (!checkEmpty(document.getElementById('select_infection_type_id'),'Infection Type')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
        		} else {
            		if (document.getElementById('select_infection_type_id').options[
            			document.getElementById('select_infection_type_id').selectedIndex].text.toLowerCase().includes('other')) {
        				if (!checkEmpty(document.getElementById('other_infection_risk'),'Other Infection Risk')) {
        	        		if(returnFalseOnEachError) {
        	            		return false;
        	        		}
        				}
        			}
        		}
    			if (!checkEmpty(document.getElementById('select_episode_of_infection'),'Infection Episode')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
    			}
    			if (!checkEmpty(document.getElementById('episode_start_date'),'Start Date')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
            	} else {
            		if(ValidateAllDates(document.getElementById('episode_start_date').id) == false){
	            		return false;
            		}
    			}
    	    	if (!checkEmpty(document.getElementById('select_continued_risk'),'Continue Risk')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
    	    	} else {
        			if (document.getElementById('episode_finished_date_div').style.display == '') {
        				if (!checkEmpty(document.getElementById('episode_finished_date'),'Finished Date')) {
        	        		if(returnFalseOnEachError) {
        	            		return false;
        	        		}
                    	} else {
                    		if(ValidateAllDates(document.getElementById('episode_finished_date').id) == false){
        	            		return false;
                    		}
       					}
        			}
    				document.getElementById('continued_risk').value = document.getElementById('select_continued_risk').value;
    	    	}
    			if (!checkEmpty(document.getElementById('select_sample_collection'),'Sample Collection')) {
	        		if(returnFalseOnEachError) {
	            		return false;
	        		}
    			}
    			
        		document.getElementById('infection_type_id').value = document.getElementById('select_infection_type_id').value;
        		document.getElementById('episode_of_infection').value = document.getElementById('select_episode_of_infection').value;
        		document.getElementById('sample_collection').value = document.getElementById('select_sample_collection').value;
        		document.getElementById('sample_type_id').value = document.getElementById('select_sample_type_id').value;
        		
       		} else {
       			
        		document.getElementById('infection_type_id').value = '0';
        		document.getElementById('other_infection_risk').value = '';
        		document.getElementById('episode_of_infection').value = '';
    			document.getElementById('continued_risk').value = '';
    			document.getElementById('episode_start_date').value = '';
    			document.getElementById('episode_finished_date').value = '';
    			document.getElementById('sample_collection').value = '';
    			document.getElementById('sample_type_id').value = '0';
    			document.getElementById('sample_collection_date').value = '';

       		}
    		break;
    	}

    	switch (whatToProcess.toLowerCase()) {
    	case 'validate_save_session_infection_risk':
    		processWaitingButtonSpinner('START-INFECTION-RISK');
    		processPatientConsentInfectionRisk('SAVE-SESSION-INFECTION-RISK',null,false);
    		alert('Infection risk data saved');
    		break;
    	default:
        	if(actionType != null) {
            	document.infection_risk_form.action = actionType;
        		$('.my_waiting_modal').modal('show');
            	document.infection_risk_form.submit();
        	}
    		break;
    	}
    	
		break;
    	
    default:
    	
        switch(whichButtonId.id) {
        case 'basic_search_patient_btn': // case 'search_patient_btn': 
        	
        	var this_date_of_birth = '';
    		processWaitingButtonSpinner('START-SEARCH-PATIENT');
        	if (!checkEmpty(document.getElementById('search_patient_keyword'),'Search Keyword')){
        		document.getElementById('select_search_criteria').selectedIndex = 0;
        		processWaitingButtonSpinner('END-SEARCH-PATIENT');
        		return false;
        	}
        	if(document.getElementById('select_search_criteria').value == 'date_of_birth') {
        		this_date_of_birth = document.getElementById('search_patient_keyword').value;
        		if(document.getElementById('search_patient_keyword').value.split('-').length == 3){
        			document.getElementById('search_patient_keyword').value = document.getElementById('search_patient_keyword').value.split('-')[2] +
        				'-' + document.getElementById('search_patient_keyword').value.split('-')[1] + '-' + document.getElementById('search_patient_keyword').value.split('-')[0]
        		} else  if(document.getElementById('search_patient_keyword').value.split('-').length == 2){
        			document.getElementById('search_patient_keyword').value = document.getElementById('search_patient_keyword').value.split('-')[1] +
    				'-' + document.getElementById('search_patient_keyword').value.split('-')[0]
        		} else  if(document.getElementById('search_patient_keyword').value.split('/').length == 3){
        			document.getElementById('search_patient_keyword').value = document.getElementById('search_patient_keyword').value.split('/')[2] +
    				'-' + document.getElementById('search_patient_keyword').value.split('/')[1] + '-' + document.getElementById('search_patient_keyword').value.split('/')[0]
        		} else {
            		document.getElementById('select_search_criteria').selectedIndex = 0;
            		processWaitingButtonSpinner('END-SEARCH-PATIENT');
            		return false;
        		}
        	}
        	
            switch(whichButtonId.id) {
//            case 'search_patient_btn': 
//        		$('.my_waiting_modal').modal('show');
//            	document.search_patient_form.submit();
//            	break;
            case 'basic_search_patient_btn':
        		processPatientConsentInfectionRisk('BASIC-SEARCH',null,false);
        		if(document.getElementById('select_search_criteria').value == 'date_of_birth' && this_date_of_birth.length > 0) {
            		document.getElementById('search_patient_keyword').value = this_date_of_birth;
        		}
            	break;
            }
        	
        	break;
        
        case 'save_user_btn':
        	
        	if (!checkEmpty(document.getElementById('title'),'Title') || !checkEmpty(document.getElementById('user_firstname'),'Firstname') 
        			|| !checkEmpty(document.getElementById('user_surname'),'Surname')) {
    	    		return false;
    	    } else {
    		  	    document.getElementById('selected_role_id').value = document.getElementById('current_role_id').value;
    	    		$('.my_waiting_modal').modal('show');
    		  	    document.user_profile_form.submit();
    	  	}
        	break;
        }
	}
}
function get_date_difference(whatToProcess,first_date,second_date) 
{
	switch (whatToProcess) {
	case 'AGE':
	    return Math.abs(new Date(Date.now() - first_date.getTime()).getUTCFullYear() - 1970);
		break;
	case 'DIFF':
	    return Math.abs(new Date(second_date.getTime() - first_date.getTime()).getUTCFullYear() - 1970);
		break;
	}
}
function checkDateRange(whatToProcess,inputBox,textToShow,maximumYearsAllowed)
{	
	var dateString = $(inputBox).val();
	var dateParts = dateString.split('-');
	var myDate = new Date(); 
	var name = $(inputBox).attr('id');
	var date_to_compare_with = new Date();

	document.getElementById(name + '-validation').innerHTML = '';
	document.getElementById(name + '-validation').style.display = 'none';
	$(inputBox).css('border','');

	switch (whatToProcess.toUpperCase()) {
	case 'COMPARE-SAMPLE-COLLECTION-WITH-PATIENT-DOB':
		date_to_compare_with = new Date(2006, 9, 1);
		dateString = $('#patient_dob').val();
		dateParts = dateString.split('-');
		myDate = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
		if(dateParts[0] < 1900) {
			myDate.setFullYear(dateParts[0]);
		}
		break;
	case 'COMPARE-WITH-IR-START-DATE': case 'COMPARE-PATIENT-AGE-WITH-CONSENT': case 'COMPARE-WITH-PATIENT-DOB': case 'COMPARE-WITH-CONSENT-DATE':
		date_to_compare_with = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
		if(dateParts[0] < 1900) { // Java script picks up 0020 as 1920
			date_to_compare_with.setFullYear(dateParts[0]);
		}
		switch (whatToProcess.toUpperCase()) {
		case 'COMPARE-WITH-IR-START-DATE':
			dateString = $('#episode_start_date').val();
			break;
		case 'COMPARE-PATIENT-AGE-WITH-CONSENT': case 'COMPARE-WITH-PATIENT-DOB':
			dateString = $('#patient_dob').val();
			break;
		case 'COMPARE-WITH-CONSENT-DATE':
			dateString = $('#date_of_consent').val();
			break;
		}
		dateParts = dateString.split('-');
		myDate = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
		if(dateParts[0] < 1900) {
			myDate.setFullYear(dateParts[0]);
		}
		break;
	case 'NO-FUTURE-DATE': case 'AGE-RANGE': case 'DATE-CHECK':
		myDate = new Date(dateParts[0], dateParts[1]-1, dateParts[2]);
		if(dateParts[0] < 1900) { // Java script picks up 0020 as 1920
			myDate.setFullYear(dateParts[0]);
		}
		break;
	}

	var error_found = false;
	switch (whatToProcess.toUpperCase()) {
	case 'COMPARE-WITH-CONSENT-DATE': case 'NO-FUTURE-DATE': case 'COMPARE-WITH-PATIENT-DOB': case 'COMPARE-SAMPLE-COLLECTION-WITH-PATIENT-DOB': case 'COMPARE-WITH-IR-START-DATE':
		if(myDate > date_to_compare_with){
			error_found = true;
		}
		break;
	case 'COMPARE-PATIENT-AGE-WITH-CONSENT': 
		switch (document.getElementById('whichDepartment').value) {
		case 'BGTB':
			if(get_date_difference('DIFF',myDate,date_to_compare_with) < 11){
				switch (whatToProcess.toUpperCase()) {
				case 'COMPARE-PATIENT-AGE-WITH-CONSENT':
					textToShow = "This date and patient's DoB (" + $('#patient_dob').val() + ") difference is " + get_date_difference('DIFF',myDate,date_to_compare_with) + " years. Must be 11+ years";
					break;
				}
				error_found = true;
			}
			break;
		case 'HOTB':
			if(get_date_difference('DIFF',myDate,date_to_compare_with) < 16){
				switch (whatToProcess.toUpperCase()) {
				case 'COMPARE-PATIENT-AGE-WITH-CONSENT':
					textToShow = "This date and patient's DoB (" + $('#patient_dob').val() + ") difference is " + get_date_difference('DIFF',myDate,date_to_compare_with) + " years. Must be 16+ years";
					break;
				}
				error_found = true;
			}
			break;
		}
		break;
	case 'DATE-CHECK':
		if(get_date_difference('DIFF',date_to_compare_with,myDate) > maximumYearsAllowed){
			textToShow = 'Date cannot be greater than ' + maximumYearsAllowed;
			error_found = true;
		}
		break;
	case 'AGE-RANGE':
		if(get_date_difference('AGE',myDate,null) > maximumYearsAllowed){
			textToShow = 'Age cannot be greater than ' + maximumYearsAllowed;
			error_found = true;
		}
		switch (document.getElementById('whichDepartment').value) {
		case 'BGTB':
			if(get_date_difference('AGE',myDate,null) < 11){
				textToShow = 'Age cannot be less than 11';
				error_found = true;
			}
			break;
		case 'HOTB':
			if(get_date_difference('AGE',myDate,null) < 16){
				textToShow = 'Age cannot be less than 16';
				error_found = true;
			}
			break;
		}
		break;
	}
	
	if(error_found == true){
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '-validation').innerHTML = textToShow;
		document.getElementById(name + '-validation').style.display = '';
		return false;
	}
	
	return true;	
	
}
function checkEmpty(inputBox,textToShow) {

	var whatToCheck = '';
	var name = $(inputBox).attr('id');
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
	
	document.getElementById(name + '-validation').innerHTML = '';
	document.getElementById(name + '-validation').style.display = 'none';
	$(inputBox).css('border','');
	if(whatToCheck == '') {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '-validation').innerHTML = textToShow + ' required';
		document.getElementById(name + '-validation').style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}
function checkSpecialUserInput(inputBox,textToShow) {

	var errorFound = false;
	switch ($(inputBox).attr('id')) {
	case 'nhs_number':
		if (!inputBox.value.match(/^[0-9 -]*$/)) {
			errorFound = true;
			textToShow = 'Invalid characters found';
		} else if (inputBox.value.replace(new RegExp('\-', 'g'), '').length != 10) {
			errorFound = true;
			textToShow = 'Must have 10 digits [found ' + inputBox.value.replace(new RegExp('\-', 'g'), '').length + ']';
		}
		break;
	case 'patient_surname': case 'patient_firstname': 
		if (!inputBox.value.match(/^[A-Za-z-' ]*$/)) {
			errorFound = true;
			textToShow = 'Alphabets, hyphens and apostrophes only. No other characters allowed.';
		}
		break;
	case 'hospital_number': case 'old_pat_id': 
		if (!inputBox.value.match(/^[A-Za-z-, 0-9]*$/)) {
			errorFound = true;
			textToShow = 'Alphabets, numbers, hyphens and commas only. No other characters allowed.';
		}
		break;
	}

	document.getElementById($(inputBox).attr('id') + '-validation').innerHTML = '';
	document.getElementById($(inputBox).attr('id') + '-validation').style.display = 'none';
	$(inputBox).css('border','');
	if(errorFound == true) {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById($(inputBox).attr('id') + '-validation').innerHTML = textToShow;
		document.getElementById($(inputBox).attr('id') + '-validation').style.display = '';
		return false;
	}
	return true;	
}
function processUserPrimaryRoleSelection(this_object)
{
  $('#current_role_id').on('changed.bs.select', function(e, clickedIndex, newValue) {
	  var selectedD = $(this).find('option').eq(clickedIndex).text();
	  $('#current_role_id > option').each(function() {
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
	  document.getElementById('dept_details_alert').style.display = '';
	  break;
  case 'HIDE':
	  document.getElementById('dept_details_alert').style.display = 'none';
	  break;
  }
}
function doFileValidation(inputBox) {

	var name = $(inputBox).attr('id');
	var fileTypes = ['/jpg','/jpeg','/png','/pdf'];
	var fileTypeFound = false;
	
	document.getElementById(name + '_label-validation').innerHTML = '';
	document.getElementById(name + '_label-validation').style.display = 'none';
	$(inputBox).css('border','');

    for(var i=0; i < fileTypes.length; i++){
        if (document.getElementById(name).files[0].type.toLowerCase().indexOf(fileTypes[i].toLowerCase()) != -1){
        	fileTypeFound = true;
        }
    }	
	if(!fileTypeFound) {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '_label-validation').innerHTML = 'Invalid file! Only [JPG, JPEG, PNG & PDF] supported';
		document.getElementById(name + '_label-validation').style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}
function uploadFormDataToSessionObjects(whatToProcess, whichInput)
{
	var idOfInputbox = $(whichInput).attr('id');
	var formData = new FormData();

	switch($(whichInput).attr('type')) {
	case 'file':
		
		var goAhead = false;
		var urlToPass = '';

		document.getElementById(idOfInputbox + '_label').innerHTML = '';
		hideAndShowContainer(document.getElementById(idOfInputbox + '_label'));
		goAhead = doFileValidation(document.getElementById(idOfInputbox));
		urlToPass = 'uploadFileToSessionConsent';
		
		if(goAhead == true) {
			
			formData.append(idOfInputbox,whichInput.files[0]);

			$.ajax({    
				headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')},
		        url : urlToPass,     
		        data : formData,
		        cache: false,
		        contentType: false,
		        processData: false,
		        type: 'POST',     
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

		var url_path = '';
		switch(whatToProcess.toUpperCase()) {
		case 'SAVE-FULL-AUDIT-SAMPLE':
	    	url_path = 'uploadFullAuditSampleFormData';
			break;
		case 'CONSENT_FILES':
	    	url_path = 'logConsentFileViewClick';
			break;
		case 'PATIENT': case 'FULL-PATIENT':
			switch(whatToProcess.toUpperCase()) {
			case 'PATIENT': 
		    	url_path = 'uploadPatientFormData';
				break;
			case 'FULL-PATIENT':
		    	url_path = 'uploadFullPatientFormData';
				break;
			}
			break;
		case 'CONSENT': case 'FULL-CONSENT':
			switch(whatToProcess.toUpperCase()) {
			case 'CONSENT': 
		    	url_path = 'uploadConsentFormData';
				break;
			case 'FULL-CONSENT':
		    	url_path = 'uploadFullConsentFormData';
				break;
			}
			break;
		case 'INFECTION-RISK':
	    	url_path = 'uploadInfectionRiskFormData';
			formData.append(document.getElementById('infection_risk_id').id,document.getElementById('infection_risk_id').value);
			break;
		case 'CONSENT-VALIDATE': case 'FULL-CONSENT-VALIDATE': case 'SAVE-FULL-CONSENT-VALIDATE':
			switch(whatToProcess.toUpperCase()) {
			case 'CONSENT-VALIDATE': 
		    	url_path = 'uploadValidateFormData';
				break;
			case 'FULL-CONSENT-VALIDATE': case 'SAVE-FULL-CONSENT-VALIDATE':
		    	url_path = 'uploadFullConsentValidateFormData';
				break;
			}
			break;
		case 'FINALISE-CONSENT-AUDIT': case 'CONSENT-AUDIT':
			switch(whatToProcess.toUpperCase()) {
			case 'CONSENT-AUDIT': 
		    	url_path = 'uploadConsentAuditFormData';
				break;
			case 'FINALISE-CONSENT-AUDIT':
		    	url_path = 'uploadFullConsentAuditFormData';
				break;
			}
			break;
		case 'ACTION':
	    	url_path = 'uploadActionFormData';
			break;
		}
		
	    var selectedCheckBoxValues = [];
		var select_picker;
		
		switch(whatToProcess.toUpperCase()) {
		case 'SAVE-FULL-AUDIT-SAMPLE':
			
			formData.append('aud_sample_pid',document.getElementById('aud_sample_pid').value);
			formData.append('aud_sample_id',document.getElementById('aud_sample_id').value);
			formData.append('aud_sample_type_id',document.getElementById('aud_select_sample_type_id').value);
			formData.append('sample_date',document.getElementById('sample_date').value);
			formData.append('after_consent_date',document.getElementById('after_consent_date').value);
			formData.append('sample_in_assigned_location',document.getElementById('sample_in_assigned_location').value);
			formData.append('sample_details_legible',document.getElementById('sample_details_legible').value);
			formData.append('appropriate_consent_present',document.getElementById('appropriate_consent_present').value);
			formData.append('non_conformances_details',document.getElementById('non_conformances_details').value);
			break;
			
		case 'CONSENT_FILES':
			
			formData.append($(whichInput).attr('id').replace('_label',''),$(whichInput).html());
			break;
			
		case 'FULL-PATIENT': case 'FULL-CONSENT': case 'FULL-CONSENT-VALIDATE': case 'FINALISE-CONSENT-AUDIT': 
		case 'SAVE-FULL-CONSENT-VALIDATE': 

			$('input, select, textarea').each(
				function(index){  
			    	if(document.getElementById($(this).attr('id'))) {
						formData.append($(this).attr('name').replace('select_',''),document.getElementById($(this).attr('id')).value);  
			    	}
			    }
			);
			break;
			
		default:
			
			if(whichInput != null) {
				if(whichInput.type == 'select-multiple') {
					select_picker = document.getElementById($(whichInput).attr('id'));
			    	for(i=0; i < select_picker.options.length;i++){
			    		if (select_picker.options[i].selected == true) {
			    			selectedCheckBoxValues.push([select_picker.options[i].value]);
			    		}
			    	}		
					formData.append($(whichInput).attr('name').replace('select_',''),selectedCheckBoxValues); // All non-binded elements are prefix with 'select_'. So remove the prefix before uploading data.
				} else {
					formData.append($(whichInput).attr('name').replace('select_',''),whichInput.value);  // All non-binded elements are prefix with 'select_'. So remove the prefix before uploading data.
				}
			}
				
			break;
		}
		
		$.ajax({    
			headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')},
	        url : url_path,     
	        data : formData,
	        cache: false,
	        contentType: false,
	        processData: false,
	        type: 'POST',     
	        success : function(response) {
				switch(whatToProcess.toUpperCase()) {
				case 'SAVE-FULL-AUDIT-SAMPLE':
            		alert('Sample data saved');
            		processPatientConsentInfectionRisk('GET-ALL-AUDIT-SAMPLES-FROM-SESSION',null,false);
					break;
				case 'FULL-PATIENT':
					processWaitingButtonSpinner('END-PATIENT');					
					alert('Patient form data saved');
					break;
				case 'FULL-CONSENT': 
					processWaitingButtonSpinner('END-CONSENT');					
					alert('Consent form data saved');
					break;
				case 'SAVE-FULL-CONSENT-VALIDATE':
					processWaitingButtonSpinner('END-CONSENT-VALIDATE');
					alert('Consent validate form data saved');
					break;
				case 'FINALISE-CONSENT-AUDIT':
					document.pat_con_ir_val_form.action = 'finalise_audit_consent';
	            	document.pat_con_ir_val_form.submit();
					break;
				case 'FULL-CONSENT-VALIDATE':
	            	validateFormFields('validate_action',null,'save_action',true);
	            	break;
				}
	        },    
	        error : function(e) {    
	       	 	console.log('Error occured in uploadFormDataToSessionObjects with error description = ' + e);     
	        }    
	    });

		break;
		
	}
}
function loadAndUnloadWaitingModal(whatToDo)
{
	switch (whatToDo) {
	case 'WAIT':
		$('#index_body *').prop('disabled',true);
		document.getElementById('loading_container').style.display = '';
		break;
	default:
		$('#index_body *').prop('disabled',false);
		document.getElementById('loading_container').style.display = 'none';
		break;
	}
}
function ValidateAllDates(date_input_box_id)
{
	  if(document.getElementById(date_input_box_id).value != '') {
		  
		  if(!checkDateRange('NO-FUTURE-DATE',document.getElementById(date_input_box_id),'Future date not allowed',null)
				  || !checkDateRange('DATE-CHECK',document.getElementById(date_input_box_id),'Date should be less than 120 years',120)) {
			  return false;
		  }
		  switch (date_input_box_id) {
		  case 'date_of_birth':
			  if(!checkDateRange('AGE-RANGE',document.getElementById(date_input_box_id),'',120)) {
				  return false;
			  }
			  break;
		  default:
			  if(!checkDateRange('COMPARE-WITH-PATIENT-DOB',document.getElementById(date_input_box_id),'Date should not be before patient date of birth',null)) {
				  return false;
			  }			  
			  switch (date_input_box_id) {
			  case 'cf_checked_date': case 'data_accuracy_date': case 'data_discrepancies_verification_date': case 'sample_date': case 'withdrawal_date':
				  if(!checkDateRange('COMPARE-WITH-CONSENT-DATE',document.getElementById(date_input_box_id),'Date is before date of consent',null)) {
					  return false;
				  }
				  break;
			  case 'date_of_consent':
				  if(!checkDateRange('COMPARE-PATIENT-AGE-WITH-CONSENT',document.getElementById(date_input_box_id),'Consent date and patient age are NOT in sync',null)) {
					  return false;
				  }
				  break;
			  case 'episode_finished_date':
				  if(!checkDateRange('COMPARE-WITH-IR-START-DATE',document.getElementById(date_input_box_id),'Finished date should not be before start date',null)) {
					  return false;
				  }			  
				  break;
			  }
			  break;
		  }
	  }
}
function processModalPopUp(whatToProcess,titleText,bodyText,variousButtons,dataToUse) {
	
  switch (whatToProcess) {
  case 'VIEW-CONSENT-DOCUMENT':
	  
	  var my_object = document.createElement('object');
	  $(my_object).attr('id','my_object');
	  $(my_object).width('100%').height('100%');
	  $(my_object).attr('type','application/pdf');
	  $(my_object).attr('data',dataToUse.attr('href') + '#toolbar=0');
	  
	  $('#pdf_doc_modal_body').empty();
	  document.getElementById('pdf_doc_modal_body').appendChild(my_object);
	  $('.close').show();
	  
	  $('.modal-content').height(800);
	  
	  document.getElementById('my_document_preview_header').innerHTML = dataToUse.html();
	  $('#dismiss_btn').show();
	  $('#dismiss_btn').attr('data-dismiss','modal');
	  $('#yes_btn').hide();
	  $('#no_btn').hide();
	  $('.my_document_preview_modal').modal('show');
	  
	  break;
  
  case 'CUSTOM-ALERT':

	  $('#pdf_doc_modal_body').empty();
	  
	  var text = document.createElement('h4');
	  text.style = 'text-align:center;color:red;';
	  text.innerHTML = bodyText;
	  document.getElementById('pdf_doc_modal_body').appendChild(text);
	  $('.close').hide();

	  $('.modal-content').height(300);
	  
	  document.getElementById('my_document_preview_header').innerHTML = titleText;
	  switch (variousButtons) {
	  case 'YES-NO':
		  $('#dismiss_btn').hide();
		  $('#yes_btn').show();
     	  document.getElementById('yes_btn').onclick = function() {processButtonClick(document.getElementById('yes_btn'),dataToUse)};
		  $('#no_btn').show();
     	  document.getElementById('no_btn').onclick = function() {processButtonClick(document.getElementById('no_btn'),dataToUse)};
		  break;
	  case 'DISMISS':
		  $('#dismiss_btn').show();
     	  document.getElementById('dismiss_btn').onclick = function() {processButtonClick(document.getElementById('dismiss_btn'),dataToUse)};
		  $('#yes_btn').hide();
		  $('#no_btn').hide();
		  break;
	  }
	  $('.my_document_preview_modal').modal({
          show: true,
          keyboard: false,
          backdrop: 'static'
      });
	  
	  break;
  }
}
jQuery(function ($) {
  
  $('.consents_document_label_link').click(function(e) {
	  
	  processModalPopUp('VIEW-CONSENT-DOCUMENT',$(this).html(),'','DISMISS',$(this));
	  e.preventDefault();

//	  var my_object = document.createElement('object');
//	  $(my_object).attr('id','my_object');
//	  $(my_object).width('100%').height('100%');
//	  $(my_object).attr('type','application/pdf');
//	  $(my_object).attr('data',$(this).attr('href') + '#toolbar=0');
//	  
//	  $('#pdf_doc_modal_body').empty();
//	  document.getElementById('pdf_doc_modal_body').appendChild(my_object);
//	  
//	  document.getElementById('my_document_preview_header').innerHTML = $(this).html();
//	  $('.my_document_preview_modal').modal('show');
//	  e.preventDefault();
	  
  });
	  
  $('[data-toggle="popover"]').popover();
	
  $('.validate_this_selection').change(function() {
	  checkDateRange('',$(this),'',null)
	  if($(this).val().toLowerCase() == 'yes') {
		  if(!checkDateRange('COMPARE-SAMPLE-COLLECTION-WITH-PATIENT-DOB',$(this),'Patient DoB cannot be greater than Sep 2006',null)) {
			  return false;
		  }
	  }
  });
  
  $('.validate_this_date').focusout(function() {
	  ValidateAllDates($(this).prop('id'));
  });
  
  $('.avoid_pid_data').tooltip({
      title: 'Do <b>not</b> enter Patient Identifiable Information in this field',  
      html: true
  });   
  
//  $('#search_result_table').on( 'click', 'button', function () {
//      var data = table.row( $(this).parents('tr') ).data();
//      alert(data[0] + "'s salary is: " + data[5] );
//  } );  
  
  $('form input').keydown(function (e) {
	    if (e.keyCode == 13) {
	        e.preventDefault();
	        return false;
	    }
	});  
  
});