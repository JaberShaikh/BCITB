package com.tissuebank.util;

import java.util.ArrayList;
import java.util.List;

import com.tissuebank.model.Consent;
import com.tissuebank.model.InfectionRisk;
import com.tissuebank.model.Patient;

public class AllPatientData {

	private List<Patient> patients;
	private List<Consent> consents;
	private List<InfectionRisk> infection_risks;
	
	private String search_result_view_type;
    private String access_data;
    private String advanced_search_sql_script;
    private String error_occured_txt;
	
	public AllPatientData() {
		super();
	}
	public AllPatientData(String error_occured_txt) {
		super();
		this.error_occured_txt = error_occured_txt;
	}
	public AllPatientData(String advanced_search_sql_script, String error_occured_txt) {
		super();
		this.advanced_search_sql_script = advanced_search_sql_script;
		this.error_occured_txt = error_occured_txt;
	}
	public String getAdvanced_search_sql_script() {
		return advanced_search_sql_script;
	}
	public void setAdvanced_search_sql_script(String advanced_search_sql_script) {
		this.advanced_search_sql_script = advanced_search_sql_script;
	}
	public String getError_occured_txt() {
		return error_occured_txt;
	}
	public void setError_occured_txt(String error_occured_txt) {
		this.error_occured_txt = error_occured_txt;
	}
	public String getAccess_data() {
		return access_data;
	}
	public void setAccess_data(String access_data) {
		this.access_data = access_data;
	}
	public String getSearch_result_view_type() {
		return search_result_view_type;
	}
	public void setSearch_result_view_type(String search_result_view_type) {
		this.search_result_view_type = search_result_view_type;
	}
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	public List<Consent> getConsents() {
		return consents;
	}
	public void setConsents(List<Consent> consents) {
		this.consents = consents;
	}
	public List<InfectionRisk> getInfection_risks() {
		return infection_risks;
	}
	public void setInfection_risks(List<InfectionRisk> infection_risks) {
		this.infection_risks = infection_risks;
	}
	
	public ArrayList<String> getSearchColumnNames(String which_department, String typeOfSearch, String typeOfAccess) {
		
		ArrayList<String> searchColNames = new ArrayList<String>();
		
		switch (typeOfSearch) {
		case BCITBVariousVariables.search_result_columns:
				searchColNames.add("database_id");
				searchColNames.add("secondary_id");
				if (!typeOfAccess.toLowerCase().contains(BCITBVariousVariables.view_restricted)) {
					searchColNames.add("patient_firstname");
					searchColNames.add("patient_surname");
					searchColNames.add("date_of_birth");
					searchColNames.add("nhs_number");
					searchColNames.add("hospital_number");
				}
				searchColNames.add("volunteer");
				searchColNames.add("age");
				searchColNames.add("old_pat_id");
				searchColNames.add("number_of_infection_risks");
				searchColNames.add("number_of_consents");
				searchColNames.add("date_of_consent");
				searchColNames.add("consent_taken_by");
				searchColNames.add("location");
				searchColNames.add("verbal_consent");
				searchColNames.add("verbal_consent_recorded");
				searchColNames.add("verbal_consent_recorded_by");
				searchColNames.add("verbal_consent_document_id");
				searchColNames.add("form_type");
				searchColNames.add("form_version_id");
				searchColNames.add("digital_cf_attachment_id");
				if(which_department == BCITBVariousVariables.HOTB) {
					searchColNames.add("samples_consented_to");
				}
				searchColNames.add("consent_type");
				searchColNames.add("consent_exclusions");
				searchColNames.add("exclusions_comment");
				searchColNames.add("consent_notes");
				searchColNames.add("withdrawn");
				searchColNames.add("withdrawal_date");
				searchColNames.add("withdrawal_document_id");
				searchColNames.add("is_validated");
				searchColNames.add("is_imported");
				searchColNames.add("is_finalised");
			break;
		case BCITBVariousVariables.basic_search:
			if (typeOfAccess.toLowerCase().contains(BCITBVariousVariables.view_restricted)) {
				searchColNames.add("database_id");
				searchColNames.add("old_pat_id");
				searchColNames.add("secondary_id");
			} else {
				searchColNames.add("patient_surname");
				searchColNames.add("hospital_number");
				searchColNames.add("database_id");
				searchColNames.add("old_pat_id");
				searchColNames.add("date_of_birth");
				searchColNames.add("nhs_number");
				searchColNames.add("patient_firstname");
				searchColNames.add("secondary_id");
			}
			break;
		case BCITBVariousVariables.advanced_search:
			searchColNames.add("database_id");
			searchColNames.add("secondary_id");
			searchColNames.add("volunteer");
			searchColNames.add("date_of_birth");
			searchColNames.add("age");
			searchColNames.add("infection_risk_exist");
			searchColNames.add("date_of_consent");
			searchColNames.add("consent_taken_by");
			searchColNames.add("location");
			searchColNames.add("form_type");
			searchColNames.add("form_version");
			searchColNames.add("digital_cf_attachment");
			if(which_department == BCITBVariousVariables.HOTB) {
				searchColNames.add("samples_consented_to");
			}
			searchColNames.add("consent_exclusions");
			searchColNames.add("withdrawn");
			searchColNames.add("sam_coll_before_sep_2006");
			searchColNames.add("validated_consents");
			searchColNames.add("imported_consents");
			searchColNames.add("finalised_consents");
			break;
		}
		
		return searchColNames;
	}
	@Override
	public String toString() {
		return "AllPatientData [patients=" + patients + ", consents=" + consents + ", infection_risks="
				+ infection_risks + ", search_result_view_type=" + search_result_view_type + ", access_data="
				+ access_data + ", advanced_search_sql_script=" + advanced_search_sql_script + ", error_occured_txt=" + error_occured_txt;
	}
}
