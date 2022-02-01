package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConsentAudit implements Diffable<ConsentAudit> {

  @Id
  @Column(name = "CONSENT_AUDIT_ID")
  private int consent_audit_id;
	
  @Column(name = "CA_CONSENT_ID")
  private int ca_consent_id;
  
  @Column(name = "AUD_VERBAL_DOCUMENT_CHECKED")
  private String aud_verbal_document_checked;

  @Column(name = "AUD_DIGITAL_CF_ATTACHED")
  private String aud_digital_cf_attached;

  @Column(name = "AUD_CF_PHYSICAL_LOCATION")
  private String aud_cf_physical_location;
  
  @Column(name = "AUD_DATE_OF_CONSENT_STATED")
  private String aud_date_of_consent_stated;
  
  @Column(name = "AUD_PATIENT_SIGNATURE")
  private String aud_patient_signature;

  @Column(name = "AUD_PERSON_TAKING_CONSENT")
  private String aud_person_taking_consent;
  
  @Column(name = "AUD_CF_VALIDITY")
  private String aud_cf_validity;

  @Column(name = "AUD_VERIFY_CONSENT_EXCLUSIONS")
  private String aud_verify_consent_exclusions;
  
  @Column(name = "AUD_STATEMENTS_EXCLUDED")
  private String aud_statements_excluded;
  
  @Column(name = "AUD_CF_AUDIT_NOTES")
  private String aud_cf_audit_notes;

  @Column(name = "AUD_DATA_DISCREPANCIES_IDENTIFIED")
  private String aud_data_discrepancies_identified;

  @Column(name = "AUD_SOURCE_OF_VERIFIED_DATA")
  private String aud_source_of_verified_data;

  @Column(name = "REAPPROACH_PATIENT")
  private String reapproach_patient;

  @Column(name = "REAPPROACH_REASON")
  private String reapproach_reason;

  @Column(name = "DISCREPANCIES_DESCRIPTION")
  private String discrepancies_description;
  
  @Column(name = "SAMPLES_OBTAINED_ELECTRONICALLY")
  private String samples_obtained_electronically;
  
  @Column(name = "PRIMARY_AUDITOR")
  private String primary_auditor;

  @Column(name = "SECONDARY_AUDITOR")
  private String secondary_auditor;

  @Column(name = "AUDIT_DATE")
  private String audit_date;

  @Column(name = "AUD_PHYSICAL_CONSENT_FORM")
  private String aud_physical_consent_form;

  @Column(name = "SAMPLE_MISSING")
  private String sample_missing;

  @Column(name = "AUDIT_TRIGGERED_DATE")
  private String audit_triggered_date;
  
  @Transient
  private String ca_which_department;
  
  @Transient
  private List<ConsentAuditSample> sample_types;
  
public ConsentAudit(int ca_consent_id, String ca_which_department) {
	super();
	this.ca_consent_id = ca_consent_id;
	this.ca_which_department = ca_which_department;
}

public ConsentAudit() {
	super();
}

public String getAudit_triggered_date() {
	return audit_triggered_date;
}

public void setAudit_triggered_date(String audit_triggered_date) {
	this.audit_triggered_date = audit_triggered_date;
}

public List<ConsentAuditSample> getSample_types() {
	return sample_types;
}

public void setSample_types(List<ConsentAuditSample> sample_types) {
	this.sample_types = sample_types;
}

public String getAud_physical_consent_form() {
	return aud_physical_consent_form;
}

public void setAud_physical_consent_form(String aud_physical_consent_form) {
	this.aud_physical_consent_form = aud_physical_consent_form;
}

public String getSample_missing() {
	return sample_missing;
}

public void setSample_missing(String sample_missing) {
	this.sample_missing = sample_missing;
}

public String getCa_which_department() {
	return ca_which_department;
}

public void setCa_which_department(String ca_which_department) {
	this.ca_which_department = ca_which_department;
}

public int getConsent_audit_id() {
	return consent_audit_id;
}

public void setConsent_audit_id(int consent_audit_id) {
	this.consent_audit_id = consent_audit_id;
}

public int getCa_consent_id() {
	return ca_consent_id;
}

public void setCa_consent_id(int ca_consent_id) {
	this.ca_consent_id = ca_consent_id;
}

public String getAud_verbal_document_checked() {
	return aud_verbal_document_checked;
}

public void setAud_verbal_document_checked(String aud_verbal_document_checked) {
	this.aud_verbal_document_checked = aud_verbal_document_checked;
}

public String getAud_digital_cf_attached() {
	return aud_digital_cf_attached;
}

public void setAud_digital_cf_attached(String aud_digital_cf_attached) {
	this.aud_digital_cf_attached = aud_digital_cf_attached;
}

public String getAud_cf_physical_location() {
	return aud_cf_physical_location;
}

public void setAud_cf_physical_location(String aud_cf_physical_location) {
	this.aud_cf_physical_location = aud_cf_physical_location;
}

public String getAud_date_of_consent_stated() {
	return aud_date_of_consent_stated;
}

public void setAud_date_of_consent_stated(String aud_date_of_consent_stated) {
	this.aud_date_of_consent_stated = aud_date_of_consent_stated;
}

public String getAud_patient_signature() {
	return aud_patient_signature;
}

public void setAud_patient_signature(String aud_patient_signature) {
	this.aud_patient_signature = aud_patient_signature;
}

public String getAud_person_taking_consent() {
	return aud_person_taking_consent;
}

public void setAud_person_taking_consent(String aud_person_taking_consent) {
	this.aud_person_taking_consent = aud_person_taking_consent;
}

public String getAud_cf_validity() {
	return aud_cf_validity;
}

public void setAud_cf_validity(String aud_cf_validity) {
	this.aud_cf_validity = aud_cf_validity;
}

public String getAud_verify_consent_exclusions() {
	return aud_verify_consent_exclusions;
}

public void setAud_verify_consent_exclusions(String aud_verify_consent_exclusions) {
	this.aud_verify_consent_exclusions = aud_verify_consent_exclusions;
}

public String getAud_statements_excluded() {
	return aud_statements_excluded;
}

public void setAud_statements_excluded(String aud_statements_excluded) {
	this.aud_statements_excluded = aud_statements_excluded;
}

public String getAud_cf_audit_notes() {
	return aud_cf_audit_notes;
}

public void setAud_cf_audit_notes(String aud_cf_audit_notes) {
	this.aud_cf_audit_notes = aud_cf_audit_notes;
}

public String getAud_data_discrepancies_identified() {
	return aud_data_discrepancies_identified;
}

public void setAud_data_discrepancies_identified(String aud_data_discrepancies_identified) {
	this.aud_data_discrepancies_identified = aud_data_discrepancies_identified;
}

public String getAud_source_of_verified_data() {
	return aud_source_of_verified_data;
}

public void setAud_source_of_verified_data(String aud_source_of_verified_data) {
	this.aud_source_of_verified_data = aud_source_of_verified_data;
}

public String getReapproach_patient() {
	return reapproach_patient;
}

public void setReapproach_patient(String reapproach_patient) {
	this.reapproach_patient = reapproach_patient;
}

public String getReapproach_reason() {
	return reapproach_reason;
}

public void setReapproach_reason(String reapproach_reason) {
	this.reapproach_reason = reapproach_reason;
}

public String getDiscrepancies_description() {
	return discrepancies_description;
}

public void setDiscrepancies_description(String discrepancies_description) {
	this.discrepancies_description = discrepancies_description;
}

public String getSamples_obtained_electronically() {
	return samples_obtained_electronically;
}

public void setSamples_obtained_electronically(String samples_obtained_electronically) {
	this.samples_obtained_electronically = samples_obtained_electronically;
}

public String getPrimary_auditor() {
	return primary_auditor;
}

public void setPrimary_auditor(String primary_auditor) {
	this.primary_auditor = primary_auditor;
}

public String getSecondary_auditor() {
	return secondary_auditor;
}

public void setSecondary_auditor(String secondary_auditor) {
	this.secondary_auditor = secondary_auditor;
}

public String getAudit_date() {
	return audit_date;
}

public void setAudit_date(String audit_date) {
	this.audit_date = audit_date;
}

@Override
public String toString() {
	return "Verbal Document Checked = " + aud_verbal_document_checked + ", Digital CF Attached = "
			+ aud_digital_cf_attached + ", CF Physical Location = " + aud_cf_physical_location
			+ ", Date Of Consent Stated = " + aud_date_of_consent_stated + ", Patient Signature = "
			+ aud_patient_signature + ", Person Taking Consent = " + aud_person_taking_consent + ", CF Validity = "
			+ aud_cf_validity + ", Verify Consent Exclusions = " + aud_verify_consent_exclusions
			+ ", Statements Excluded = " + aud_statements_excluded + ", CF Audit Notes = " + aud_cf_audit_notes
			+ ", Data Discrepancies Identified = " + aud_data_discrepancies_identified
			+ ", Source Of Verified Data = " + aud_source_of_verified_data + ", Reapproach Patient = "
			+ reapproach_patient + ", Reapproach Reason = " + reapproach_reason + ", Discrepancies Description = "
			+ discrepancies_description + ", Samples Obtained Electronically = " + samples_obtained_electronically
			+ ", Physical Consent Form = " + aud_physical_consent_form + ", Sample Missing = " + sample_missing 
			+ ", Primary Auditor = " + primary_auditor + ", Secondary Auditor = " + secondary_auditor 
			+ ", Audit Date = " + audit_date; 
}

@Override
public DiffResult diff(ConsentAudit consent_audit) {
	DiffBuilder db = new DiffBuilder(this, consent_audit, ToStringStyle.SHORT_PREFIX_STYLE);
    if ((this.aud_verbal_document_checked != null && !this.aud_verbal_document_checked.isEmpty()) || (consent_audit.aud_verbal_document_checked != null && !consent_audit.aud_verbal_document_checked.isEmpty()))
	    db.append("aud_verbal_document_checked", this.aud_verbal_document_checked, consent_audit.aud_verbal_document_checked);
    if ((this.aud_digital_cf_attached != null && !this.aud_digital_cf_attached.isEmpty()) || (consent_audit.aud_digital_cf_attached != null && !consent_audit.aud_digital_cf_attached.isEmpty()))
	    db.append("aud_digital_cf_attached", this.aud_digital_cf_attached, consent_audit.aud_digital_cf_attached);
    if ((this.aud_cf_physical_location != null && !this.aud_cf_physical_location.isEmpty()) || (consent_audit.aud_cf_physical_location != null && !consent_audit.aud_cf_physical_location.isEmpty()))
	    db.append("aud_cf_physical_location", this.aud_cf_physical_location, consent_audit.aud_cf_physical_location);
    if ((this.aud_date_of_consent_stated != null && !this.aud_date_of_consent_stated.isEmpty()) || (consent_audit.aud_date_of_consent_stated != null && !consent_audit.aud_date_of_consent_stated.isEmpty()))
		db.append("aud_date_of_consent_stated", this.aud_date_of_consent_stated, consent_audit.aud_date_of_consent_stated);
    if ((this.aud_patient_signature != null && !this.aud_patient_signature.isEmpty()) || (consent_audit.aud_patient_signature != null && !consent_audit.aud_patient_signature.isEmpty()))
		db.append("aud_patient_signature", this.aud_patient_signature, consent_audit.aud_patient_signature);
    if ((this.aud_person_taking_consent != null && !this.aud_person_taking_consent.isEmpty()) || (consent_audit.aud_person_taking_consent != null && !consent_audit.aud_person_taking_consent.isEmpty()))
		db.append("aud_person_taking_consent", this.aud_person_taking_consent, consent_audit.aud_person_taking_consent);
    if ((this.aud_cf_validity != null && !this.aud_cf_validity.isEmpty()) || (consent_audit.aud_cf_validity != null && !consent_audit.aud_cf_validity.isEmpty()))
		db.append("aud_cf_validity", this.aud_cf_validity, consent_audit.aud_cf_validity);
    if ((this.aud_verify_consent_exclusions != null && !this.aud_verify_consent_exclusions.isEmpty()) || (consent_audit.aud_verify_consent_exclusions != null && !consent_audit.aud_verify_consent_exclusions.isEmpty()))
	    db.append("aud_verify_consent_exclusions", this.aud_verify_consent_exclusions, consent_audit.aud_verify_consent_exclusions);
    if ((this.aud_statements_excluded != null && !this.aud_statements_excluded.isEmpty()) || (consent_audit.aud_statements_excluded != null && !consent_audit.aud_statements_excluded.isEmpty()))
	    db.append("aud_statements_excluded", this.aud_statements_excluded, consent_audit.aud_statements_excluded);
    if ((this.aud_cf_audit_notes != null && !this.aud_cf_audit_notes.isEmpty()) || (consent_audit.aud_cf_audit_notes != null && !consent_audit.aud_cf_audit_notes.isEmpty()))
	    db.append("aud_cf_audit_notes", this.aud_cf_audit_notes, consent_audit.aud_cf_audit_notes);
    if ((this.aud_data_discrepancies_identified != null && !this.aud_data_discrepancies_identified.isEmpty()) || (consent_audit.aud_data_discrepancies_identified != null && !consent_audit.aud_data_discrepancies_identified.isEmpty()))
	    db.append("aud_data_discrepancies_identified", this.aud_data_discrepancies_identified, consent_audit.aud_data_discrepancies_identified);
    if ((this.aud_source_of_verified_data != null && !this.aud_source_of_verified_data.isEmpty()) || (consent_audit.aud_source_of_verified_data != null && !consent_audit.aud_source_of_verified_data.isEmpty()))
	    db.append("aud_source_of_verified_data", this.aud_source_of_verified_data, consent_audit.aud_source_of_verified_data);
    if ((this.reapproach_patient != null && !this.reapproach_patient.isEmpty()) || (consent_audit.reapproach_patient != null && !consent_audit.reapproach_patient.isEmpty()))
	    db.append("reapproach_patient", this.reapproach_patient, consent_audit.reapproach_patient);
    if ((this.reapproach_reason != null && !this.reapproach_reason.isEmpty()) || (consent_audit.reapproach_reason != null && !consent_audit.reapproach_reason.isEmpty()))
	    db.append("reapproach_reason", this.reapproach_reason, consent_audit.reapproach_reason);
    if ((this.discrepancies_description != null && !this.discrepancies_description.isEmpty()) || (consent_audit.discrepancies_description != null && !consent_audit.discrepancies_description.isEmpty()))
	    db.append("discrepancies_description", this.discrepancies_description, consent_audit.discrepancies_description);
    if ((this.samples_obtained_electronically != null && !this.samples_obtained_electronically.isEmpty()) || (consent_audit.samples_obtained_electronically != null && !consent_audit.samples_obtained_electronically.isEmpty()))
	    db.append("samples_obtained_electronically", this.samples_obtained_electronically, consent_audit.samples_obtained_electronically);
    if ((this.primary_auditor != null && !this.primary_auditor.isEmpty()) || (consent_audit.primary_auditor != null && !consent_audit.primary_auditor.isEmpty()))
	    db.append("primary_auditor", this.primary_auditor, consent_audit.primary_auditor);
    if ((this.secondary_auditor != null && !this.secondary_auditor.isEmpty()) || (consent_audit.secondary_auditor != null && !consent_audit.secondary_auditor.isEmpty()))
	    db.append("secondary_auditor", this.secondary_auditor, consent_audit.secondary_auditor);
    if ((this.audit_date != null && !this.audit_date.isEmpty()) || (consent_audit.audit_date != null && !consent_audit.audit_date.isEmpty()))
	    db.append("audit_date", this.audit_date, consent_audit.audit_date);
    if ((this.aud_physical_consent_form != null && !this.aud_physical_consent_form.isEmpty()) || (consent_audit.aud_physical_consent_form != null && !consent_audit.aud_physical_consent_form.isEmpty()))
	    db.append("aud_physical_consent_form", this.aud_physical_consent_form, consent_audit.aud_physical_consent_form);
    if ((this.sample_missing != null && !this.sample_missing.isEmpty()) || (consent_audit.sample_missing != null && !consent_audit.sample_missing.isEmpty()))
	    db.append("sample_missing", this.sample_missing, consent_audit.sample_missing);
    
    return db.build();
}
  
}