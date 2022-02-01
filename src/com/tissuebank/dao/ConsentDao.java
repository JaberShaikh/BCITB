package com.tissuebank.dao;

import java.util.List;

import org.apache.commons.lang3.builder.DiffResult;

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

public interface ConsentDao
{
	List<Consent> getPatientConsents(String consents_creteria, String whichDepartment, int patient_id, String status_operator, String status, List<Location> locations, String asc_or_desc); 
	List<Consent> searchConsents(String search_creteria, String whichDepartment, String what_to_search, String asc_or_desc); 
	List<ConsentAudit> getConsentAudits(String whichDepartment, String column_name, String column_value); 
    List<ConsentExclusion> getConsentExclusions(String whichDepartment, int consent_id);
    List<ConsentedSamples> getConsentedSamples(String whichDepartment, int consent_id);
    List<ConsentFormType> getFormTypes(String whichDepartment);
    List<ConsentSampleType> getAllConsentedSampleTypes(String whichDepartment);
    List<ConsentAuditSample> getConsentAuditSamples(String whichDepartment, int consent_audit_id);
    List<ConsentTerm> getAllConsentTerms(String whichDepartment);
    ConsentAuditSample getConsentAuditSample(String whichDepartment, int aud_sample_pid);
	Consent getPatientConsent(String whichDepartment, int consent_id, String status_operator, String status, List<Location> locations); 
    ConsentValidate getConsentValidate(String whichDepartment, int consent_id);
    ConsentAudit getConsentAudit(String whichDepartment, int consent_id);
    ConsentTerm getConsentTerm(String whichDepartment, int consent_term_id);
    ConsentSampleType getConsentSampleType(String whichDepartment, int consent_sample_type_id);
    ConsentSampleType getConsentSampleType(String whichDepartment, String description);
    Consent insertNewConsent(String whichDepartment, Consent consent);
    void insertNewConsentExclusions(String whichDepartment, Consent consent);
    void insertNewConsentedSamples(String whichDepartment, Consent consent);
    ConsentValidate insertNewConsentValidate(String whichDepartment, ConsentValidate consent_validate);
    ConsentAudit insertNewConsentAudit(String whichDepartment, ConsentAudit consent_audit);
    ConsentAuditSample insertNewConsentAuditSample(String whichDepartment, int consent_audit_id, ConsentAuditSample consent_audit_sample);
    void updateConsentAuditSampleUsingDiff(String whichDepartment, int aud_sample_id, DiffResult diff_result);
    void updateConsentUsingDiff(String whichDepartment, int consent_id, DiffResult diff_result);
    void updateConsentValidateUsingDiff(String whichDepartment, int consent_validate_id, DiffResult diff_result);
    void updateConsentAuditUsingDiff(String whichDepartment, int consent_audit_id, DiffResult diff_result);
    void saveConsentVariousColumns(String whichDepartment, int consent_id, String dbColumnName, String dbColumnValue);
    void deleteConsent(String whatToProcess, String whichDepartment, int consent_id);
    void deleteConsentExclusion(String whichDepartment, int consent_id, int consent_term_id);
    void deleteConsentedSamples(String whichDepartment, int consent_id, int consent_sample_type_id);
}
