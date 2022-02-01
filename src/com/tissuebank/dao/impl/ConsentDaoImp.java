package com.tissuebank.dao.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffResult;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.ConsentDao;
import com.tissuebank.model.Consent;
import com.tissuebank.model.ConsentAudit;
import com.tissuebank.model.ConsentAuditSample;
import com.tissuebank.model.ConsentValidate;
import com.tissuebank.model.ConsentedSamples;
import com.tissuebank.model.ConsentFormType;
import com.tissuebank.model.ConsentSampleType;
import com.tissuebank.model.ConsentExclusion;
import com.tissuebank.model.ConsentTerm;
import com.tissuebank.model.FormVersion;
import com.tissuebank.model.global.views.Location;
import com.tissuebank.util.BCITBVariousVariables;

@Transactional
@Repository("consentDao")
public class ConsentDaoImp implements ConsentDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Query this_query;
	public String this_sql_script = "";
	
	@Override
	public Consent insertNewConsent(String whichDepartment, Consent consent) {

		consent.setConsent_id((int) (long) ((sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(consent_id) FROM " + whichDepartment + "_Consent_V").uniqueResult() != null) ?
				((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(consent_id) FROM " + whichDepartment + "_Consent_V").uniqueResult()).longValue() + 1 : 1));
		sessionFactory.getCurrentSession().createSQLQuery(
				"INSERT into " + whichDepartment + "_Consent(consent_id,date_of_consent,verbal_consent,verbal_consent_recorded,verbal_consent_recorded_by,"
						+ "verbal_consent_document_id,form_type,form_version_id,digital_cf_attachment_id,exclusions_comment,consent_notes,withdrawn,withdrawal_date,"
						+ "withdrawal_document_id,cn_patient_id,loc_id,consent_type,consent_taken_by,additional_document_id,status,stop_sample_donation,stop_sample_donation_date,"
						+ "sam_coll_before_sep_2006,creation_date) VALUES (" + consent.getConsent_id() + ",:date_of_consent_value,:verbal_consent_value,:verbal_consent_recorded_value,"
						+ ":verbal_consent_recorded_by_value," + consent.getVerbal_consent_document_id() + ",:form_type_value," + consent.getForm_version_id() 
						+ "," + consent.getDigital_cf_attachment_id() + ",:exclusions_comment_value,:consent_notes_value,:withdrawn_value,:withdrawal_date_value," 
						+ consent.getWithdrawal_document_id() + "," + consent.getCn_patient_id() + "," + consent.getLoc_id() + ",:consent_type_value,:consent_taken_by_value,"
						+ consent.getAdditional_document_id() + ",:status_value,:stop_sample_donation_value,:stop_sample_donation_date_value,:sam_coll_before_sep_2006_value,:creation_date_val)")
				.setParameter("date_of_consent_value", consent.getDate_of_consent())
				.setParameter("verbal_consent_value", consent.getVerbal_consent())
				.setParameter("verbal_consent_recorded_value", consent.getVerbal_consent_recorded())
				.setParameter("verbal_consent_recorded_by_value", consent.getVerbal_consent_recorded_by())
				.setParameter("form_type_value", consent.getForm_type())
				.setParameter("exclusions_comment_value", consent.getExclusions_comment())
				.setParameter("consent_notes_value", consent.getConsent_notes())
				.setParameter("withdrawn_value", consent.getWithdrawn())
				.setParameter("withdrawal_date_value", consent.getWithdrawal_date())
				.setParameter("consent_type_value", consent.getConsent_type())
				.setParameter("consent_taken_by_value", consent.getConsent_taken_by())
				.setParameter("status_value", consent.getStatus())
				.setParameter("stop_sample_donation_value", consent.getStop_sample_donation())
				.setParameter("stop_sample_donation_date_value", consent.getStop_sample_donation_date())
				.setParameter("sam_coll_before_sep_2006_value", consent.getSam_coll_before_sep_2006())
				.setParameter("creation_date_val", consent.getCreation_date())
				.executeUpdate(); 
		return consent;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Consent> getPatientConsents(String consents_creteria, String whichDepartment, int patient_id, 
			String status_operator, String status, List<Location> locations, String asc_or_desc) 
	{
		List<Consent> this_consents=null;
		List<Integer> locationIds = new ArrayList<Integer>();
		
		if(locations != null)
			if(locations.size() > 0)
				for(Location loc:locations)
					locationIds.add(loc.getLoc_id());

		if(locationIds.size() > 0)
			switch(consents_creteria.toLowerCase()) {
			case BCITBVariousVariables.consent_withdrawn:
				this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE cn_patient_id = " 
						+ patient_id + " AND upper(is_withdrawn)='YES' AND (loc_id IS NULL OR loc_id IN (:locationIds)) ORDER BY consent_id " 
						+ asc_or_desc.toUpperCase()).setParameterList("locationIds", locationIds).list();
				break;
			default:
				if(patient_id > 0 && !status_operator.trim().isEmpty() && !status.trim().isEmpty()) {
					this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE cn_patient_id = " 
							+ patient_id + " AND upper(status) " + status_operator + " '" + status.toUpperCase() + 
							"' AND (loc_id IS NULL OR loc_id IN (:locationIds)) ORDER BY consent_id "
							+ asc_or_desc.toUpperCase()).setParameterList("locationIds", locationIds).list();
					if(this_consents.isEmpty())
						this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE cn_patient_id = " 
								+ patient_id + " AND upper(status) " + status_operator + " '" + status.toUpperCase() + "' ORDER BY consent_id "
								+ asc_or_desc.toUpperCase()).list();
				} 
				else if(patient_id <= 0 && !status_operator.trim().isEmpty() && !status.trim().isEmpty())
						this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V "
							+ "WHERE upper(status) " + status_operator + " '" + status.toUpperCase() + 
							"' AND (loc_id IS NULL OR loc_id IN (:locationIds)) ORDER BY consent_id "
							+ asc_or_desc.toUpperCase()).setParameterList("locationIds", locationIds).list();
				else if(patient_id > 0 && status_operator.trim().isEmpty() && status.trim().isEmpty())
						this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE cn_patient_id = "
							+ patient_id + " AND (loc_id IS NULL OR loc_id IN (:locationIds)) ORDER BY consent_id " + asc_or_desc.toUpperCase())
							.setParameterList("locationIds", locationIds).list();
				else
						this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V "
							+ "WHERE loc_id IS NULL OR loc_id IN (:locationIds) ORDER BY consent_id " + asc_or_desc.toUpperCase())
							.setParameterList("locationIds", locationIds).list();
					
				break;
			}
		else
			switch(consents_creteria) {
			case BCITBVariousVariables.creation_date:
				this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE creation_date IS NULL" 
						+ " ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				break;
			case BCITBVariousVariables.MONTHLY_REPORT_SENT_DATE:
				this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE SUBSTR(creation_date,6,2)" 
						+ " = '" + new DecimalFormat("00").format(LocalDate.now().minusMonths(1).getMonthValue()) 
						+ "' AND SUBSTR(creation_date,1,4) = '" + LocalDate.now().minusMonths(1).getYear() 
						+ "' ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				break;
			case BCITBVariousVariables.consent_deletion_date:
				this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE consent_deletion_date IS NOT NULL" 
						+ " ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				break;
			case BCITBVariousVariables.ignore_audited_withdrawn:
				this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE is_audited IS NULL" 
						+ " AND is_withdrawn IS NULL ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				break;
			case BCITBVariousVariables.is_imported:
				this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE " 
						+ " lower(is_imported)='" + BCITBVariousVariables.yes + "' AND IS_FINALISED IS NULL ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				break;
			case BCITBVariousVariables.is_validated:
				this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE " 
						+ " lower(is_validated)='" + BCITBVariousVariables.yes + "' ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				break;
			case BCITBVariousVariables.marked_for_auditing:
				this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE lower(marked_for_auditing)='yes'" 
						+ " ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				break;
			case BCITBVariousVariables.consent_withdrawn:
				if(patient_id > 0)
					this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE cn_patient_id = " 
							+ patient_id + " AND upper(is_withdrawn)='YES' ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				else
					this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE "
							+ "upper(is_withdrawn)='YES' ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				break;
			default:
				if(patient_id > 0 && !status_operator.trim().isEmpty() && !status.trim().isEmpty())
					this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE cn_patient_id = " 
							+ patient_id +  " AND upper(status) " + status_operator + " '" + status.toUpperCase() 
							+ "' ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				else if(patient_id <= 0 && !status_operator.trim().isEmpty() && !status.trim().isEmpty())
					this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V "
							+ "WHERE upper(status) " + status_operator + " '" + status.toUpperCase() + "' ORDER BY consent_id "
							+ asc_or_desc.toUpperCase()).list();
				else if(patient_id > 0 && status_operator.trim().isEmpty() && status.trim().isEmpty())
					this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE cn_patient_id = "
							+ patient_id + " ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
				else
					this_consents = sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V ORDER BY consent_id "
							+ asc_or_desc.toUpperCase()).list();
					
				break;
			}
			
		switch(consents_creteria.toLowerCase()) {
		case BCITBVariousVariables.is_imported: case BCITBVariousVariables.creation_date: // Just need all imported consents. No need of 'form versions'
			break;
		default:
			for (Consent con: this_consents) 
				if(con.getForm_version_id() != null)
					con.setForm_version((FormVersion) sessionFactory.getCurrentSession().createQuery(
							"FROM " + whichDepartment + "_Form_Version_V WHERE form_version_id = " + con.getForm_version_id()).uniqueResult());
			break;
		}
		
		return this_consents;
		
	}

	@Override
	public void insertNewConsentExclusions(String whichDepartment, Consent consent) {
		if (consent.getConsent_exclusions() != null && !consent.getConsent_exclusions().trim().isEmpty()) {
			int next_con_excl = 0;
			for(String selExclusion: consent.getConsent_exclusions().split(",")) {
				if(doestRecordAlreadyExist("consent_exclusion",whichDepartment,consent.getConsent_id(),Integer.parseInt(selExclusion)) == false) {
					next_con_excl = (int) (long) ((sessionFactory.getCurrentSession().createSQLQuery(
							"SELECT MAX(consent_exclusion_id) FROM " + whichDepartment + "_Consent_Exclusion_V").uniqueResult() != null) ?
							((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(
							"SELECT MAX(consent_exclusion_id) FROM " + whichDepartment + "_Consent_Exclusion_V").uniqueResult()).longValue() + 1 : 1);
					sessionFactory.getCurrentSession().createSQLQuery(
							"INSERT into " + whichDepartment + "_Consent_Exclusion(consent_exclusion_id,consent_term_id,consent_id) "
									+ "VALUES (" + next_con_excl + "," + selExclusion + "," + consent.getConsent_id() + ")").executeUpdate();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentExclusion> getConsentExclusions(String whichDepartment, int consent_id) {
		List<ConsentExclusion> this_ce = new ArrayList<ConsentExclusion>();
		if(consent_id > 0)
			this_ce = new ArrayList<ConsentExclusion>(sessionFactory.getCurrentSession().createQuery(
					"FROM " + whichDepartment + "_Consent_Exclusion_V WHERE consent_id = " + consent_id).list());
		else
			this_ce = new ArrayList<ConsentExclusion>(sessionFactory.getCurrentSession().createQuery(
					"FROM " + whichDepartment + "_Consent_Exclusion_V ").list());
		for(ConsentExclusion ce : this_ce)
			ce.setConsent_term(getConsentTerm(whichDepartment, ce.getConsent_term_id()));
		return this_ce;
	}

	public boolean doestRecordAlreadyExist(String whichDBTable, String whichDepartment, int first_search_creteria, int second_search_creteria) {
		this_sql_script = "";
		switch (whichDBTable.toLowerCase()) {
		case "consent_exclusion":
			this_sql_script = "FROM " + whichDepartment + "_Consent_Exclusion_V WHERE consent_id = " + first_search_creteria + " AND consent_term_id = " + second_search_creteria;
			break;
		case "sample_consented_to":
			this_sql_script = "FROM " + whichDepartment + "_Consented_Samples_V WHERE consent_id = " + first_search_creteria + " AND consent_sample_type_id = " + second_search_creteria;
			break;
		}
		if (sessionFactory.getCurrentSession().createQuery(this_sql_script).uniqueResult() != null)
			return true;
		else
			return false;
	}
	
	@Override
	public ConsentTerm getConsentTerm(String whichDepartment, int consent_term_id) {
		return (ConsentTerm) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Consent_Terms_V WHERE consent_term_id = " + consent_term_id).uniqueResult();
	}

	@Override
	public void deleteConsentExclusion(String whichDepartment, int consent_id, int consent_term_id) {
		if(consent_term_id > 0) {
			sessionFactory.getCurrentSession().createSQLQuery(
					"DELETE " + whichDepartment + "_Consent_Exclusion WHERE consent_id = " + consent_id + " AND consent_term_id = " + consent_term_id).executeUpdate();
		} else {
			sessionFactory.getCurrentSession().createSQLQuery(
					"DELETE " + whichDepartment + "_Consent_Exclusion WHERE consent_id = " + consent_id).executeUpdate();
		}
	}

	@Override
	public ConsentValidate insertNewConsentValidate(String whichDepartment, ConsentValidate consent_validate) {
		
		consent_validate.setConsent_validate_id((int) (long) ((sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT MAX(consent_validate_id) FROM " + whichDepartment + "_Consent_Validate_V").uniqueResult() != null) ?
				((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(consent_validate_id) FROM " + 
				whichDepartment + "_Consent_Validate_V").uniqueResult()).longValue() + 1 : 1));

		sessionFactory.getCurrentSession().createSQLQuery(
				"INSERT into " + whichDepartment + "_Consent_Validate(consent_validate_id,cv_consent_id,verbal_document_checked,verbal_consent_checked_by,"
						+ "verbal_consent_checked_date,digital_cf_attached,cf_physical_location,date_of_consent_stated,patient_signature,person_taking_consent,"
						+ "cf_validity,cf_checked_date,cf_checked_by,verify_consent_exclusions,statements_excluded,"
						+ "cf_audit_notes,data_discrepancies_identified,data_accuracy_date,data_accuracy_checked_by,source_of_verified_data,"
						+ "data_discrepancies_description,data_discrepancies_verified,data_discrepancies_verification_date,data_discrepancies_verified_by,status) "
						+ "VALUES (" + consent_validate.getConsent_validate_id() + "," + consent_validate.getCv_consent_id() + ",:verbal_document_checked_val,"
						+ ":verbal_consent_checked_by_val,:verbal_consent_checked_date_val,:digital_cf_attached_val,:cf_physical_location_val,:date_of_consent_stated_val,"
						+ ":patient_signature_val,:person_taking_consent_val,:cf_validity_val,:cf_checked_date_val,:cf_checked_by_val,:verify_consent_exclusions_val,"
						+ ":statements_excluded_val,:cf_audit_notes_val,:data_discrepancies_identified_val,:data_accuracy_date_val,:data_accuracy_checked_by_val,"
						+ ":source_of_verified_data_val,:data_discrepancies_description_val,:data_discrepancies_verified_val,"
						+ ":data_discrepancies_verification_date_val,:data_discrepancies_verified_by_val,:status_val)")
				.setParameter("verbal_document_checked_val", consent_validate.getVerbal_document_checked())
				.setParameter("verbal_consent_checked_by_val", consent_validate.getVerbal_consent_checked_by())
				.setParameter("verbal_consent_checked_date_val", consent_validate.getVerbal_consent_checked_date())
				.setParameter("digital_cf_attached_val", consent_validate.getDigital_cf_attached())
				.setParameter("cf_physical_location_val", consent_validate.getCf_physical_location())
				.setParameter("date_of_consent_stated_val", consent_validate.getDate_of_consent_stated())
				.setParameter("patient_signature_val", consent_validate.getPatient_signature())
				.setParameter("person_taking_consent_val", consent_validate.getPerson_taking_consent())
				.setParameter("cf_validity_val", consent_validate.getCf_validity())
				.setParameter("cf_checked_date_val", consent_validate.getCf_checked_date())
				.setParameter("cf_checked_by_val", consent_validate.getCf_checked_by())
				.setParameter("verify_consent_exclusions_val", consent_validate.getVerify_consent_exclusions())
				.setParameter("statements_excluded_val", consent_validate.getStatements_excluded())
				.setParameter("cf_audit_notes_val", consent_validate.getCf_audit_notes())
				.setParameter("data_discrepancies_identified_val", consent_validate.getData_discrepancies_identified())
				.setParameter("data_accuracy_date_val", consent_validate.getData_accuracy_date())
				.setParameter("data_accuracy_checked_by_val", consent_validate.getData_accuracy_checked_by())
				.setParameter("source_of_verified_data_val", consent_validate.getSource_of_verified_data())
				.setParameter("data_discrepancies_description_val", consent_validate.getData_discrepancies_description())
				.setParameter("data_discrepancies_verified_val", consent_validate.getData_discrepancies_verified())
				.setParameter("data_discrepancies_verification_date_val", consent_validate.getData_discrepancies_verification_date())
				.setParameter("data_discrepancies_verified_by_val", consent_validate.getData_discrepancies_verified_by())
				.setParameter("status_val", consent_validate.getStatus())
				.executeUpdate();
				
		return consent_validate;
	}

	@Override
	public ConsentValidate getConsentValidate(String whichDepartment, int consent_id) {
		return (ConsentValidate) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Consent_Validate_V WHERE cv_consent_id=" + consent_id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentFormType> getFormTypes(String whichDepartment) {
		return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_Form_Type_V").list();
	}

	@Override
	public Consent getPatientConsent(String whichDepartment, int consent_id, String status_operator, String status, List<Location> locations) 
	{
		List<Integer> locationIds = new ArrayList<Integer>();
		if(locations != null)
			for(Location loc:locations)
				locationIds.add(loc.getLoc_id());
		
		if(locationIds.size() > 0)
			if(!status_operator.isEmpty() && !status.isEmpty())
				return (Consent) sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE consent_id = " + consent_id + 
						" AND upper(status) " + status_operator + " '" + status.toUpperCase() + "' AND loc_id IN (:locationIds)")
						.setParameterList("locationIds", locationIds).uniqueResult();
			else
				return (Consent) sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE consent_id = " + 
						consent_id + " AND loc_id IN (:locationIds)").setParameterList("locationIds", locationIds).uniqueResult();
		else
			if(!status_operator.isEmpty() && !status.isEmpty())
				return (Consent) sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE consent_id = " + consent_id + 
						" AND upper(status) " + status_operator + " '" + status.toUpperCase() + "'").uniqueResult();
			else
				return (Consent) sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE consent_id = " + consent_id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentSampleType> getAllConsentedSampleTypes(String whichDepartment) {
		return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_Sample_Type_V").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentedSamples> getConsentedSamples(String whichDepartment, int consent_id) {
		return sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Consented_Samples_V WHERE consent_id = " + consent_id).list();
	}

	@Override
	public void insertNewConsentedSamples(String whichDepartment, Consent consent) {
		int next_con_sample = 0;
		if (consent.getSamples_consented_to() != null && !consent.getSamples_consented_to().trim().isEmpty()) {
			for(String selConSample: consent.getSamples_consented_to().split(",")) {
				if(doestRecordAlreadyExist("sample_consented_to",whichDepartment,consent.getConsent_id(),Integer.parseInt(selConSample)) == false) {
					next_con_sample = (int) (long) ((sessionFactory.getCurrentSession().createSQLQuery(
							"SELECT MAX(consented_samples_id) FROM " + whichDepartment + "_Consented_Samples_V").uniqueResult() != null) ?
							((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(
							"SELECT MAX(consented_samples_id) FROM " + whichDepartment + "_Consented_Samples_V").uniqueResult()).longValue() + 1 : 1);
					sessionFactory.getCurrentSession().createSQLQuery(
							"INSERT into " + whichDepartment + "_Consented_Samples(consented_samples_id,consent_sample_type_id,consent_id) "
									+ "VALUES (" + next_con_sample + "," + selConSample + "," + consent.getConsent_id() + ")").executeUpdate();
				}
			}
		}
	}

	@Override
	public void deleteConsentedSamples(String whichDepartment, int consent_id, int consent_sample_type_id) {
		sessionFactory.getCurrentSession().createSQLQuery(
				"DELETE " + whichDepartment + "_Consented_Samples WHERE consent_id = " + consent_id 
				+ " AND consent_sample_type_id = " + consent_sample_type_id).executeUpdate();
	}

	@Override
	public ConsentSampleType getConsentSampleType(String whichDepartment, int consent_sample_type_id) {
		return (ConsentSampleType) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Consent_Sample_Type_V WHERE consent_sample_type_id = " + consent_sample_type_id).uniqueResult();
	}

	@Override
	public ConsentSampleType getConsentSampleType(String whichDepartment, String description) {
		return (ConsentSampleType) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Consent_Sample_Type_V WHERE UPPER(description)='" + description.toUpperCase() + "'").uniqueResult();
	}
	
	@Override
	public void updateConsentUsingDiff(String whichDepartment, int consent_id, DiffResult diff_result) {
		this_sql_script = "";
		for(Diff<?> d: diff_result.getDiffs()) {
			for(String col_nm:sessionFactory.getClassMetadata(Consent.class).getPropertyNames()) {
				if(col_nm.equalsIgnoreCase(d.getFieldName())) {
					if(!this_sql_script.trim().isEmpty())
						this_sql_script = this_sql_script + ",";
					this_sql_script = this_sql_script + d.getFieldName() + "=:" + d.getFieldName() + "_val";
				}
			}
		}
		if(!this_sql_script.trim().isEmpty()) {
			this_query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE " + whichDepartment + "_Consent SET " + this_sql_script + 
					" WHERE consent_id=:consent_id_val");
			for(Diff<?> d: diff_result.getDiffs()) 
				for(String col_nm:sessionFactory.getClassMetadata(Consent.class).getPropertyNames()) 
					if(col_nm.equalsIgnoreCase(d.getFieldName())) 
						this_query.setParameter(d.getFieldName() + "_val", String.valueOf(d.getRight()));
			this_query.setParameter("consent_id_val", consent_id);
			this_query.executeUpdate();
		}
	}

	@Override
	public void updateConsentValidateUsingDiff(String whichDepartment, int consent_validate_id, DiffResult diff_result) 
	{
		this_sql_script = "";
		for(Diff<?> d: diff_result.getDiffs()) {
			for(String col_nm:sessionFactory.getClassMetadata(ConsentValidate.class).getPropertyNames()) {
				if(col_nm.equalsIgnoreCase(d.getFieldName())) {
					if(!this_sql_script.trim().isEmpty())
						this_sql_script = this_sql_script + ",";
					this_sql_script = this_sql_script + d.getFieldName() + "=:" + d.getFieldName() + "_val";
				}
			}
		}
		if(!this_sql_script.trim().isEmpty()) {
			this_query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE " + whichDepartment + "_Consent_Validate SET " + this_sql_script + 
					" WHERE consent_validate_id=:consent_validate_id_val");
			for(Diff<?> d: diff_result.getDiffs()) 
				for(String col_nm:sessionFactory.getClassMetadata(ConsentValidate.class).getPropertyNames()) 
					if(col_nm.equalsIgnoreCase(d.getFieldName())) 
						this_query.setParameter(d.getFieldName() + "_val", String.valueOf(d.getRight()));
			this_query.setParameter("consent_validate_id_val", consent_validate_id);
			this_query.executeUpdate();
		}
	}

	@Override
	public void saveConsentVariousColumns(String whichDepartment, int consent_id, String dbColumnName, String dbColumnValue) {
		for (String col_name: sessionFactory.getClassMetadata(Consent.class).getPropertyNames())
			if (col_name.equalsIgnoreCase(dbColumnName))
				sessionFactory.getCurrentSession().createSQLQuery(
						"UPDATE " + whichDepartment + "_Consent SET " + dbColumnName + "=:" + dbColumnName + "_val WHERE consent_id=:consent_id_val")
				.setParameter(dbColumnName + "_val", dbColumnValue).setParameter("consent_id_val", consent_id).executeUpdate();
	}

	@Override
	public ConsentAudit insertNewConsentAudit(String whichDepartment, ConsentAudit consent_audit) {
		consent_audit.setConsent_audit_id((int) (long) ((sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(consent_audit_id) FROM " + whichDepartment + "_Consent_Audit_V").uniqueResult() != null) ?
				((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(consent_audit_id) FROM " + whichDepartment + "_Consent_Audit_V").uniqueResult()).longValue() + 1 : 1));

		sessionFactory.getCurrentSession().createSQLQuery(
				"INSERT into " + whichDepartment + "_Consent_Audit(consent_audit_id,ca_consent_id,aud_verbal_document_checked,aud_digital_cf_attached,aud_cf_physical_location,"
						+ "aud_date_of_consent_stated,aud_patient_signature,aud_person_taking_consent,aud_cf_validity,aud_verify_consent_exclusions,aud_statements_excluded,aud_cf_audit_notes,"
						+ "aud_data_discrepancies_identified,aud_source_of_verified_data,reapproach_patient,reapproach_reason,discrepancies_description,samples_obtained_electronically,"
						+ "aud_physical_consent_form,sample_missing,primary_auditor,secondary_auditor,audit_date,audit_triggered_date) VALUES (" + consent_audit.getConsent_audit_id()  
						+ "," + consent_audit.getCa_consent_id() + ",:aud_verbal_document_checked_val,:aud_digital_cf_attached_val,:aud_cf_physical_location_val,"
						+ ":aud_date_of_consent_stated_val,:aud_patient_signature_val,:aud_person_taking_consent_val,:aud_cf_validity_val,:aud_verify_consent_exclusions_val,"
						+ ":aud_statements_excluded_val,:aud_cf_audit_notes_val,:aud_data_discrepancies_identified_val,:aud_source_of_verified_data_val,"
						+ ":reapproach_patient_val,:reapproach_reason_val,:discrepancies_description_val,:samples_obtained_electronically_val,"
						+ ":aud_physical_consent_form_val,:sample_missing_val,:primary_auditor_val,:secondary_auditor_val,:audit_date_val,:audit_triggered_date_val)")
				.setParameter("aud_verbal_document_checked_val", consent_audit.getAud_verbal_document_checked())
				.setParameter("aud_digital_cf_attached_val", consent_audit.getAud_digital_cf_attached())
				.setParameter("aud_cf_physical_location_val", consent_audit.getAud_cf_physical_location())
				.setParameter("aud_date_of_consent_stated_val", consent_audit.getAud_date_of_consent_stated())
				.setParameter("aud_patient_signature_val", consent_audit.getAud_patient_signature())
				.setParameter("aud_person_taking_consent_val", consent_audit.getAud_person_taking_consent())
				.setParameter("aud_cf_validity_val", consent_audit.getAud_cf_validity())
				.setParameter("aud_verify_consent_exclusions_val", consent_audit.getAud_verify_consent_exclusions())
				.setParameter("aud_statements_excluded_val", consent_audit.getAud_statements_excluded())
				.setParameter("aud_cf_audit_notes_val", consent_audit.getAud_cf_audit_notes())
				.setParameter("aud_data_discrepancies_identified_val", consent_audit.getAud_data_discrepancies_identified())
				.setParameter("aud_source_of_verified_data_val", consent_audit.getAud_source_of_verified_data())
				.setParameter("reapproach_patient_val", consent_audit.getReapproach_patient())
				.setParameter("reapproach_reason_val", consent_audit.getReapproach_reason())
				.setParameter("discrepancies_description_val", consent_audit.getDiscrepancies_description())
				.setParameter("samples_obtained_electronically_val", consent_audit.getSamples_obtained_electronically())
				.setParameter("aud_physical_consent_form_val", consent_audit.getAud_physical_consent_form())
				.setParameter("sample_missing_val", consent_audit.getSample_missing())
				.setParameter("primary_auditor_val", consent_audit.getPrimary_auditor())
				.setParameter("secondary_auditor_val", consent_audit.getSecondary_auditor())
				.setParameter("audit_date_val", consent_audit.getAudit_date())
				.setParameter("audit_triggered_date_val", consent_audit.getAudit_triggered_date())
				.executeUpdate();
		
		return consent_audit;

	}

	@Override
	public ConsentAudit getConsentAudit(String whichDepartment, int consent_id) {
		return (ConsentAudit) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Consent_Audit_V WHERE ca_consent_id=" + consent_id).uniqueResult();
	}

	@Override
	public void updateConsentAuditUsingDiff(String whichDepartment, int consent_audit_id, DiffResult diff_result) {
		this_sql_script = "";
		for(Diff<?> d: diff_result.getDiffs()) {
			for(String col_nm:sessionFactory.getClassMetadata(ConsentAudit.class).getPropertyNames()) {
				if(col_nm.equalsIgnoreCase(d.getFieldName())) {
					if(!this_sql_script.trim().isEmpty())
						this_sql_script = this_sql_script + ",";
					this_sql_script = this_sql_script + d.getFieldName() + "=:" + d.getFieldName() + "_val";
				}
			}
		}
		if(!this_sql_script.trim().isEmpty()) {
			this_query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE " + whichDepartment + "_Consent_Audit SET " + this_sql_script + 
					" WHERE consent_audit_id=:consent_audit_id_val");
			for(Diff<?> d: diff_result.getDiffs()) 
				for(String col_nm:sessionFactory.getClassMetadata(ConsentAudit.class).getPropertyNames()) 
					if(col_nm.equalsIgnoreCase(d.getFieldName())) 
						this_query.setParameter(d.getFieldName() + "_val", String.valueOf(d.getRight()));
			this_query.setParameter("consent_audit_id_val", consent_audit_id);
			this_query.executeUpdate();
		}
	}

	@Override
	public ConsentAuditSample getConsentAuditSample(String whichDepartment, int aud_sample_pid) {
		return (ConsentAuditSample) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Consent_Audit_Samples_V WHERE aud_sample_pid=" + aud_sample_pid).uniqueResult();
	}

	@Override
	public void updateConsentAuditSampleUsingDiff(String whichDepartment, int aud_sample_id, DiffResult diff_result) {
		this_sql_script = "";
		for(Diff<?> d: diff_result.getDiffs()) {
			for(String col_nm:sessionFactory.getClassMetadata(ConsentAuditSample.class).getPropertyNames()) {
				if(col_nm.equalsIgnoreCase(d.getFieldName())) {
					if(!this_sql_script.trim().isEmpty())
						this_sql_script = this_sql_script + ",";
					this_sql_script = this_sql_script + d.getFieldName() + "=:" + d.getFieldName() + "_val";
				}
			}
		}
		if(!this_sql_script.trim().isEmpty()) {
			this_query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE " + whichDepartment + "_Consent_Audit_Samples SET " + this_sql_script + 
					" WHERE aud_sample_id=:aud_sample_id_val");
			for(Diff<?> d: diff_result.getDiffs()) 
				for(String col_nm:sessionFactory.getClassMetadata(ConsentAudit.class).getPropertyNames()) 
					if(col_nm.equalsIgnoreCase(d.getFieldName())) 
						this_query.setParameter(d.getFieldName() + "_val", String.valueOf(d.getRight()));
			this_query.setParameter("aud_sample_id_val", aud_sample_id);
			this_query.executeUpdate();
		}
	}

	@Override
	public ConsentAuditSample insertNewConsentAuditSample(String whichDepartment, int consent_audit_id, ConsentAuditSample consent_audit_sample) {
		
		consent_audit_sample.setAud_sample_pid((int) (long) ((sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(aud_sample_pid) FROM " + whichDepartment + "_Consent_Audit_Samples_V").uniqueResult() != null) ?
				((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(aud_sample_pid) FROM " + whichDepartment + "_Consent_Audit_Samples_V").uniqueResult()).longValue() + 1 : 1));
		sessionFactory.getCurrentSession().createSQLQuery(
				"INSERT into " + whichDepartment + "_Consent_Audit_Samples(aud_sample_pid,ca_id,aud_sample_type_id,aud_sample_id,sample_date,after_consent_date,"
						+ "sample_in_assigned_location,sample_details_legible,appropriate_consent_present,non_conformances_details) VALUES (" + consent_audit_sample.getAud_sample_pid()
						+ "," + consent_audit_id + "," + consent_audit_sample.getAud_sample_type_id() + ",:aud_sample_id_val,:sample_date_val,:after_consent_date_val,"
						+ ":sample_in_assigned_location_val,:sample_details_legible_val,:appropriate_consent_present_val,:non_conformances_details_val)")
				.setParameter("aud_sample_id_val", consent_audit_sample.getAud_sample_id())
				.setParameter("sample_date_val", consent_audit_sample.getSample_date())
				.setParameter("after_consent_date_val", consent_audit_sample.getAfter_consent_date())
				.setParameter("sample_in_assigned_location_val", consent_audit_sample.getSample_in_assigned_location())
				.setParameter("sample_details_legible_val", consent_audit_sample.getSample_details_legible())
				.setParameter("appropriate_consent_present_val", consent_audit_sample.getAppropriate_consent_present())
				.setParameter("non_conformances_details_val", consent_audit_sample.getNon_conformances_details())
				.executeUpdate();
		
		return consent_audit_sample;
		
	}

	@Override
	public void deleteConsent(String whatToProcess, String whichDepartment, int consent_id) {
		switch (whatToProcess) {
		case "KEEP-WTHDRAWAL-DATA":
			sessionFactory.getCurrentSession().createSQLQuery(
					"UPDATE " + whichDepartment + "_Consent SET date_of_consent='',verbal_consent='',verbal_consent_recorded='',verbal_consent_recorded_by=''"
							+ ",verbal_consent_document_id='',form_type='',form_version_id='',digital_cf_attachment_id='',exclusions_comment=''"
							+ ",consent_notes='',loc_id='',consent_type='',consent_taken_by='',additional_document_id='',stop_sample_donation=''"
							+ ",stop_sample_donation_date='',sam_coll_before_sep_2006='',consent_deletion_date='' WHERE consent_id=" + consent_id).executeUpdate();
			sessionFactory.getCurrentSession().createSQLQuery(
					"DELETE " + whichDepartment + "_Consent_Audit WHERE ca_consent_id = " + consent_id).executeUpdate();
			sessionFactory.getCurrentSession().createSQLQuery(
					"DELETE " + whichDepartment + "_Consent_Audit_Samples WHERE ca_id = " + consent_id).executeUpdate();
			sessionFactory.getCurrentSession().createSQLQuery(
					"DELETE " + whichDepartment + "_Consent_Exclusion WHERE consent_id = " + consent_id).executeUpdate();
			sessionFactory.getCurrentSession().createSQLQuery(
					"DELETE " + whichDepartment + "_Consent_Validate WHERE cv_consent_id = " + consent_id).executeUpdate();
			switch (whichDepartment) {
			case BCITBVariousVariables.HOTB:
				sessionFactory.getCurrentSession().createSQLQuery(
						"DELETE " + whichDepartment + "_Consented_Samples WHERE consent_id = " + consent_id).executeUpdate();
				break;
			}
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Consent> searchConsents(String search_creteria, String whichDepartment, String what_to_search, String asc_or_desc) {
		switch(search_creteria.toLowerCase()) {
		case BCITBVariousVariables.date_of_consent:
			return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_V WHERE date_of_consent='" 
					+ what_to_search + "' ORDER BY consent_id " + asc_or_desc.toUpperCase()).list();
		default:
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentAuditSample> getConsentAuditSamples(String whichDepartment, int consent_audit_id) {
		return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_Audit_Samples_V WHERE ca_id=" + consent_audit_id).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentAudit> getConsentAudits(String whichDepartment, String column_name, String column_value) {
		return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Consent_Audit_V WHERE " 
				+ column_name + " = '" + column_value + "'").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentTerm> getAllConsentTerms(String whichDepartment) {
		return sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Consent_Terms_V").list();
	}

}