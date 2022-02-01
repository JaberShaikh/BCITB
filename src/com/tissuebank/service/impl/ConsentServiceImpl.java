package com.tissuebank.service.impl;

import java.util.List;

import org.apache.commons.lang3.builder.DiffResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.ConsentDao;
import com.tissuebank.model.Consent;
import com.tissuebank.model.ConsentAudit;
import com.tissuebank.model.ConsentAuditSample;
import com.tissuebank.model.ConsentValidate;
import com.tissuebank.model.ConsentedSamples;
import com.tissuebank.model.global.views.Location;
import com.tissuebank.model.ConsentFormType;
import com.tissuebank.model.ConsentSampleType;
import com.tissuebank.model.ConsentExclusion;
import com.tissuebank.model.ConsentTerm;
import com.tissuebank.service.ConsentService;

@Service("consentService")
@Transactional
public class ConsentServiceImpl implements ConsentService
{
	@Autowired
	private ConsentDao consentDao;

	@Override
	public Consent insertNewConsent(String dept_acronym, Consent consent) {
		return consentDao.insertNewConsent(dept_acronym, consent);
	}

	@Override
	public void insertNewConsentExclusions(String dept_acronym, Consent consent) {
		consentDao.insertNewConsentExclusions(dept_acronym, consent);
	}

	@Override
	public List<ConsentExclusion> getConsentExclusions(String dept_acronym, int consent_id) {
		return consentDao.getConsentExclusions(dept_acronym, consent_id);
	}

	@Override
	public ConsentTerm getConsentTerm(String dept_acronym, int consent_term_id) {
		return consentDao.getConsentTerm(dept_acronym, consent_term_id);
	}

	@Override
	public void deleteConsentExclusion(String dept_acronym, int consent_id, int consent_term_id) {
		consentDao.deleteConsentExclusion(dept_acronym, consent_id, consent_term_id);
	}

	@Override
	public ConsentValidate getConsentValidate(String dept_acronym, int consent_id) {
		return consentDao.getConsentValidate(dept_acronym, consent_id);
	}

	@Override
	public ConsentValidate insertNewConsentValidate(String dept_acronym, ConsentValidate consent_validate) {
		return consentDao.insertNewConsentValidate(dept_acronym, consent_validate);
	}

	@Override
	public List<ConsentFormType> getFormTypes(String dept_acronym) {
		return consentDao.getFormTypes(dept_acronym);
	}

	@Override
	public List<ConsentSampleType> getAllConsentedSampleTypes(String dept_acronym) {
		return consentDao.getAllConsentedSampleTypes(dept_acronym);
	}

	@Override
	public List<ConsentedSamples> getConsentedSamples(String dept_acronym, int consent_id) {
		return consentDao.getConsentedSamples(dept_acronym, consent_id);
	}

	@Override
	public ConsentSampleType getConsentSampleType(String dept_acronym, int consent_sample_type_id) {
		return consentDao.getConsentSampleType(dept_acronym, consent_sample_type_id);
	}

	@Override
	public void insertNewConsentedSamples(String dept_acronym, Consent consent) {
		consentDao.insertNewConsentedSamples(dept_acronym, consent);
	}

	@Override
	public void deleteConsentedSamples(String dept_acronym, int consent_id, int consent_sample_type_id) {
		consentDao.deleteConsentedSamples(dept_acronym, consent_id, consent_sample_type_id);
	}

	@Override
	public void updateConsentUsingDiff(String dept_acronym, int consent_id, DiffResult diff_result) {
		consentDao.updateConsentUsingDiff(dept_acronym, consent_id, diff_result);
	}

	@Override
	public void updateConsentValidateUsingDiff(String dept_acronym, int consent_validate_id, DiffResult diff_result) {
		consentDao.updateConsentValidateUsingDiff(dept_acronym, consent_validate_id, diff_result);
	}

	@Override
	public void saveConsentVariousColumns(String dept_acronym, int consent_id, String dbColumnName, String dbColumnValue) {
		consentDao.saveConsentVariousColumns(dept_acronym, consent_id, dbColumnName, dbColumnValue);
	}

	@Override
	public List<Consent> getPatientConsents(String consents_creteria, String dept_acronym, int patient_id,
			String status_operator, String status, List<Location> locations, String asc_or_desc) {
		return consentDao.getPatientConsents(consents_creteria, dept_acronym, patient_id, status_operator, status, locations, asc_or_desc);
	}

	@Override
	public Consent getPatientConsent(String dept_acronym, int consent_id, String status_operator, String status, List<Location> locations) {
		return consentDao.getPatientConsent(dept_acronym, consent_id, status_operator, status, locations);
	}

	@Override
	public ConsentAudit insertNewConsentAudit(String dept_acronym, ConsentAudit consent_audit) {
		return consentDao.insertNewConsentAudit(dept_acronym, consent_audit);
	}

	@Override
	public ConsentAudit getConsentAudit(String dept_acronym, int consent_id) {
		return consentDao.getConsentAudit(dept_acronym, consent_id);
	}

	@Override
	public void updateConsentAuditUsingDiff(String dept_acronym, int consent_audit_id, DiffResult diff_result) {
		consentDao.updateConsentAuditUsingDiff(dept_acronym, consent_audit_id, diff_result);
	}

	@Override
	public ConsentSampleType getConsentSampleType(String whichDepartment, String description) {
		return consentDao.getConsentSampleType(whichDepartment, description);
	}

	@Override
	public ConsentAuditSample getConsentAuditSample(String whichDepartment, int consent_audit_id) {
		return consentDao.getConsentAuditSample(whichDepartment, consent_audit_id);
	}

	@Override
	public void updateConsentAuditSampleUsingDiff(String whichDepartment, int aud_sample_id, DiffResult diff_result) {
		consentDao.updateConsentAuditSampleUsingDiff(whichDepartment, aud_sample_id, diff_result);
	}

	@Override
	public void deleteConsent(String whatToProcess, String whichDepartment, int consent_id) {
		consentDao.deleteConsent(whatToProcess, whichDepartment, consent_id);
	}

	@Override
	public List<Consent> searchConsents(String search_creteria, String whichDepartment, String what_to_search, String asc_or_desc) {
		return consentDao.searchConsents(search_creteria, whichDepartment, what_to_search, asc_or_desc);
	}

	@Override
	public List<ConsentAuditSample> getConsentAuditSamples(String whichDepartment, int consent_audit_id) {
		return consentDao.getConsentAuditSamples(whichDepartment, consent_audit_id);
	}

	@Override
	public ConsentAuditSample insertNewConsentAuditSample(String whichDepartment, int consent_audit_id, ConsentAuditSample consent_audit_sample) {
		return consentDao.insertNewConsentAuditSample(whichDepartment, consent_audit_id, consent_audit_sample);
	}

	@Override
	public List<ConsentAudit> getConsentAudits(String whichDepartment, String column_name, String column_value) {
		return consentDao.getConsentAudits(whichDepartment, column_name, column_value);
	}

	@Override
	public List<ConsentTerm> getAllConsentTerms(String whichDepartment) {
		return consentDao.getAllConsentTerms(whichDepartment);
	}

}
