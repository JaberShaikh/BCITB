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

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConsentValidate implements Diffable<ConsentValidate> {

  @Id
  @Column(name = "CONSENT_VALIDATE_ID")
  private int consent_validate_id;

  @Column(name = "CV_CONSENT_ID")
  private int cv_consent_id;
  
  @Column(name = "VERBAL_DOCUMENT_CHECKED")
  private String verbal_document_checked;

  @Column(name = "VERBAL_CONSENT_CHECKED_BY")
  private String verbal_consent_checked_by;
  
  @Column(name = "VERBAL_CONSENT_CHECKED_DATE")
  private String verbal_consent_checked_date;
  
  @Column(name = "DIGITAL_CF_ATTACHED")
  private String digital_cf_attached;

  @Column(name = "CF_PHYSICAL_LOCATION")
  private String cf_physical_location;
  
  @Column(name = "DATE_OF_CONSENT_STATED")
  private String date_of_consent_stated;
  
  @Column(name = "PATIENT_SIGNATURE")
  private String patient_signature;

  @Column(name = "PERSON_TAKING_CONSENT")
  private String person_taking_consent;
  
  @Column(name = "CF_VALIDITY")
  private String cf_validity;

  @Column(name = "CF_CHECKED_DATE")
  private String cf_checked_date;

  @Column(name = "CF_CHECKED_BY")
  private String cf_checked_by;

  @Column(name = "VERIFY_CONSENT_EXCLUSIONS")
  private String verify_consent_exclusions;
  
  @Column(name = "STATEMENTS_EXCLUDED")
  private String statements_excluded;
  
  @Column(name = "CF_AUDIT_NOTES")
  private String cf_audit_notes;

  @Column(name = "DATA_DISCREPANCIES_IDENTIFIED")
  private String data_discrepancies_identified;

  @Column(name = "DATA_ACCURACY_DATE")
  private String data_accuracy_date;

  @Column(name = "DATA_ACCURACY_CHECKED_BY")
  private String data_accuracy_checked_by;
  
  @Column(name = "SOURCE_OF_VERIFIED_DATA")
  private String source_of_verified_data;
  
  @Column(name = "DATA_DISCREPANCIES_DESCRIPTION")
  private String data_discrepancies_description;
  
  @Column(name = "DATA_DISCREPANCIES_VERIFIED")
  private String data_discrepancies_verified;

  @Column(name = "DATA_DISCREPANCIES_VERIFICATION_DATE")
  private String data_discrepancies_verification_date;

  @Column(name = "DATA_DISCREPANCIES_VERIFIED_BY")
  private String data_discrepancies_verified_by;

  @Column(name = "STATUS")
  private String status;
  
  @Transient
  private String cv_which_department;

public ConsentValidate() {
	super();
}

public ConsentValidate(int cv_consent_id, String cv_which_department) {
	super();
	this.cv_consent_id = cv_consent_id;
	this.cv_which_department = cv_which_department;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getCv_which_department() {
	return cv_which_department;
}

public void setCv_which_department(String cv_which_department) {
	this.cv_which_department = cv_which_department;
}

public int getConsent_validate_id() {
	return consent_validate_id;
}

public void setConsent_validate_id(int consent_validate_id) {
	this.consent_validate_id = consent_validate_id;
}

public int getCv_consent_id() {
	return cv_consent_id;
}

public void setCv_consent_id(int cv_consent_id) {
	this.cv_consent_id = cv_consent_id;
}

public String getVerbal_document_checked() {
	return verbal_document_checked;
}

public void setVerbal_document_checked(String verbal_document_checked) {
	this.verbal_document_checked = verbal_document_checked;
}

public String getVerbal_consent_checked_by() {
	return verbal_consent_checked_by;
}

public void setVerbal_consent_checked_by(String verbal_consent_checked_by) {
	this.verbal_consent_checked_by = verbal_consent_checked_by;
}

public String getVerbal_consent_checked_date() {
	return verbal_consent_checked_date;
}

public void setVerbal_consent_checked_date(String verbal_consent_checked_date) {
	this.verbal_consent_checked_date = verbal_consent_checked_date;
}

public String getDigital_cf_attached() {
	return digital_cf_attached;
}

public void setDigital_cf_attached(String digital_cf_attached) {
	this.digital_cf_attached = digital_cf_attached;
}

public String getCf_physical_location() {
	return cf_physical_location;
}

public void setCf_physical_location(String cf_physical_location) {
	this.cf_physical_location = cf_physical_location;
}

public String getDate_of_consent_stated() {
	return date_of_consent_stated;
}

public void setDate_of_consent_stated(String date_of_consent_stated) {
	this.date_of_consent_stated = date_of_consent_stated;
}

public String getPatient_signature() {
	return patient_signature;
}

public void setPatient_signature(String patient_signature) {
	this.patient_signature = patient_signature;
}

public String getPerson_taking_consent() {
	return person_taking_consent;
}

public void setPerson_taking_consent(String person_taking_consent) {
	this.person_taking_consent = person_taking_consent;
}

public String getCf_validity() {
	return cf_validity;
}

public void setCf_validity(String cf_validity) {
	this.cf_validity = cf_validity;
}

public String getCf_checked_date() {
	return cf_checked_date;
}

public void setCf_checked_date(String cf_checked_date) {
	this.cf_checked_date = cf_checked_date;
}

public String getCf_checked_by() {
	return cf_checked_by;
}

public void setCf_checked_by(String cf_checked_by) {
	this.cf_checked_by = cf_checked_by;
}

public String getVerify_consent_exclusions() {
	return verify_consent_exclusions;
}

public void setVerify_consent_exclusions(String verify_consent_exclusions) {
	this.verify_consent_exclusions = verify_consent_exclusions;
}

public String getStatements_excluded() {
	return statements_excluded;
}

public void setStatements_excluded(String statements_excluded) {
	this.statements_excluded = statements_excluded;
}

public String getCf_audit_notes() {
	return cf_audit_notes;
}

public void setCf_audit_notes(String cf_audit_notes) {
	this.cf_audit_notes = cf_audit_notes;
}

public String getData_discrepancies_identified() {
	return data_discrepancies_identified;
}

public void setData_discrepancies_identified(String data_discrepancies_identified) {
	this.data_discrepancies_identified = data_discrepancies_identified;
}

public String getData_accuracy_date() {
	return data_accuracy_date;
}

public void setData_accuracy_date(String data_accuracy_date) {
	this.data_accuracy_date = data_accuracy_date;
}

public String getData_accuracy_checked_by() {
	return data_accuracy_checked_by;
}

public void setData_accuracy_checked_by(String data_accuracy_checked_by) {
	this.data_accuracy_checked_by = data_accuracy_checked_by;
}

public String getSource_of_verified_data() {
	return source_of_verified_data;
}

public void setSource_of_verified_data(String source_of_verified_data) {
	this.source_of_verified_data = source_of_verified_data;
}

public String getData_discrepancies_description() {
	return data_discrepancies_description;
}

public void setData_discrepancies_description(String data_discrepancies_description) {
	this.data_discrepancies_description = data_discrepancies_description;
}

public String getData_discrepancies_verified() {
	return data_discrepancies_verified;
}

public void setData_discrepancies_verified(String data_discrepancies_verified) {
	this.data_discrepancies_verified = data_discrepancies_verified;
}

public String getData_discrepancies_verification_date() {
	return data_discrepancies_verification_date;
}

public void setData_discrepancies_verification_date(String data_discrepancies_verification_date) {
	this.data_discrepancies_verification_date = data_discrepancies_verification_date;
}

public String getData_discrepancies_verified_by() {
	return data_discrepancies_verified_by;
}

public void setData_discrepancies_verified_by(String data_discrepancies_verified_by) {
	this.data_discrepancies_verified_by = data_discrepancies_verified_by;
}

@Override
public String toString() {
	return "Verbal Document Checked = " + verbal_document_checked + ", Verbal Document Checked By = "
			+ verbal_consent_checked_by + ", Verbal Document Checked Date = " + verbal_consent_checked_date
			+ ", Digital CF Attached = " + digital_cf_attached + ", CF Physical Location = " + cf_physical_location
			+ ", Date Of Consent Stated = " + date_of_consent_stated + ", Patient Signature = " + patient_signature
			+ ", Person Taking Consent = " + person_taking_consent + ", CF Validity = " + cf_validity + ", CF Checked Date = "
			+ cf_checked_date + ", CF Checked By = " + cf_checked_by + ", Verify Consent Exclusions = "
			+ verify_consent_exclusions + ", Statements Excluded = " + statements_excluded + ", CF Audit Notes = "
			+ cf_audit_notes + ", Data Discrepancies Identified = " + data_discrepancies_identified
			+ ", Data Accuracy Date = " + data_accuracy_date + ", Data Accuracy Checked By = " + data_accuracy_checked_by
			+ ", Source Of Verified Data = " + source_of_verified_data + ", Data Discrepancies Description = "
			+ data_discrepancies_description + ", Data Discrepancies Verified = " + data_discrepancies_verified
			+ ", Data Discrepancies Verification Date = " + data_discrepancies_verification_date
			+ ", Data Discrepancies Verified By = " + data_discrepancies_verified_by;
}

@Override
public DiffResult diff(ConsentValidate consent_validation) {
	DiffBuilder db = new DiffBuilder(this, consent_validation, ToStringStyle.SHORT_PREFIX_STYLE);
    if ((this.verbal_document_checked != null && !this.verbal_document_checked.isEmpty()) || (consent_validation.verbal_document_checked != null && !consent_validation.verbal_document_checked.isEmpty()))
	    db.append("verbal_document_checked", this.verbal_document_checked, consent_validation.verbal_document_checked);
    if ((this.verbal_consent_checked_by != null && !this.verbal_consent_checked_by.isEmpty()) || (consent_validation.verbal_consent_checked_by != null && !consent_validation.verbal_consent_checked_by.isEmpty()))
	    db.append("verbal_consent_checked_by", this.verbal_consent_checked_by, consent_validation.verbal_consent_checked_by);
    if ((this.verbal_consent_checked_date != null && !this.verbal_consent_checked_date.isEmpty()) || (consent_validation.verbal_consent_checked_date != null && !consent_validation.verbal_consent_checked_date.isEmpty()))
	    db.append("verbal_consent_checked_date", this.verbal_consent_checked_date, consent_validation.verbal_consent_checked_date);
    if ((this.digital_cf_attached != null && !this.digital_cf_attached.isEmpty()) || (consent_validation.digital_cf_attached != null && !consent_validation.digital_cf_attached.isEmpty()))
	    db.append("digital_cf_attached", this.digital_cf_attached, consent_validation.digital_cf_attached);
    if ((this.cf_physical_location != null && !this.cf_physical_location.isEmpty()) || (consent_validation.cf_physical_location != null && !consent_validation.cf_physical_location.isEmpty()))
	    db.append("cf_physical_location", this.cf_physical_location, consent_validation.cf_physical_location);
    if ((this.date_of_consent_stated != null && !this.date_of_consent_stated.isEmpty()) || (consent_validation.date_of_consent_stated != null && !consent_validation.date_of_consent_stated.isEmpty()))
		db.append("date_of_consent_stated", this.date_of_consent_stated, consent_validation.date_of_consent_stated);
    if ((this.patient_signature != null && !this.patient_signature.isEmpty()) || (consent_validation.patient_signature != null && !consent_validation.patient_signature.isEmpty()))
		db.append("patient_signature", this.patient_signature, consent_validation.patient_signature);
    if ((this.person_taking_consent != null && !this.person_taking_consent.isEmpty()) || (consent_validation.person_taking_consent != null && !consent_validation.person_taking_consent.isEmpty()))
		db.append("person_taking_consent", this.person_taking_consent, consent_validation.person_taking_consent);
    if ((this.cf_validity != null && !this.cf_validity.isEmpty()) || (consent_validation.cf_validity != null && !consent_validation.cf_validity.isEmpty()))
		db.append("cf_validity", this.cf_validity, consent_validation.cf_validity);
    if ((this.cf_checked_date != null && !this.cf_checked_date.isEmpty()) || (consent_validation.cf_checked_date != null && !consent_validation.cf_checked_date.isEmpty()))
		db.append("cf_checked_date", this.cf_checked_date, consent_validation.cf_checked_date);
    if ((this.cf_checked_by != null && !this.cf_checked_by.isEmpty()) || (consent_validation.cf_checked_by != null && !consent_validation.cf_checked_by.isEmpty()))
	    db.append("cf_checked_by", this.cf_checked_by, consent_validation.cf_checked_by);
    if ((this.verify_consent_exclusions != null && !this.verify_consent_exclusions.isEmpty()) || (consent_validation.verify_consent_exclusions != null && !consent_validation.verify_consent_exclusions.isEmpty()))
	    db.append("verify_consent_exclusions", this.verify_consent_exclusions, consent_validation.verify_consent_exclusions);
    if ((this.statements_excluded != null && !this.statements_excluded.isEmpty()) || (consent_validation.statements_excluded != null && !consent_validation.statements_excluded.isEmpty()))
	    db.append("statements_excluded", this.statements_excluded, consent_validation.statements_excluded);
    if ((this.cf_audit_notes != null && !this.cf_audit_notes.isEmpty()) || (consent_validation.cf_audit_notes != null && !consent_validation.cf_audit_notes.isEmpty()))
	    db.append("cf_audit_notes", this.cf_audit_notes, consent_validation.cf_audit_notes);
    if ((this.data_discrepancies_identified != null && !this.data_discrepancies_identified.isEmpty()) || (consent_validation.data_discrepancies_identified != null && !consent_validation.data_discrepancies_identified.isEmpty()))
	    db.append("data_discrepancies_identified", this.data_discrepancies_identified, consent_validation.data_discrepancies_identified);
    if ((this.data_accuracy_date != null && !this.data_accuracy_date.isEmpty()) || (consent_validation.data_accuracy_date != null && !consent_validation.data_accuracy_date.isEmpty()))
	    db.append("data_accuracy_date", this.data_accuracy_date, consent_validation.data_accuracy_date);
    if ((this.data_accuracy_checked_by != null && !this.data_accuracy_checked_by.isEmpty()) || (consent_validation.data_accuracy_checked_by != null && !consent_validation.data_accuracy_checked_by.isEmpty()))
	    db.append("data_accuracy_checked_by", this.data_accuracy_checked_by, consent_validation.data_accuracy_checked_by);
    if ((this.source_of_verified_data != null && !this.source_of_verified_data.isEmpty()) || (consent_validation.source_of_verified_data != null && !consent_validation.source_of_verified_data.isEmpty()))
	    db.append("source_of_verified_data", this.source_of_verified_data, consent_validation.source_of_verified_data);
    if ((this.data_discrepancies_description != null && !this.data_discrepancies_description.isEmpty()) || (consent_validation.data_discrepancies_description != null && !consent_validation.data_discrepancies_description.isEmpty()))
	    db.append("data_discrepancies_description", this.data_discrepancies_description, consent_validation.data_discrepancies_description);
    if ((this.data_discrepancies_verified != null && !this.data_discrepancies_verified.isEmpty()) || (consent_validation.data_discrepancies_verified != null && !consent_validation.data_discrepancies_verified.isEmpty()))
	    db.append("data_discrepancies_verified", this.data_discrepancies_verified, consent_validation.data_discrepancies_verified);
    if ((this.data_discrepancies_verification_date != null && !this.data_discrepancies_verification_date.isEmpty()) || (consent_validation.data_discrepancies_verification_date != null && !consent_validation.data_discrepancies_verification_date.isEmpty()))
	    db.append("data_discrepancies_verification_date", this.data_discrepancies_verification_date, consent_validation.data_discrepancies_verification_date);
    if ((this.data_discrepancies_verified_by != null && !this.data_discrepancies_verified_by.isEmpty()) || (consent_validation.data_discrepancies_verified_by != null && !consent_validation.data_discrepancies_verified_by.isEmpty()))
	    db.append("data_discrepancies_verified_by", this.data_discrepancies_verified_by, consent_validation.data_discrepancies_verified_by);
    return db.build();
}
  
}