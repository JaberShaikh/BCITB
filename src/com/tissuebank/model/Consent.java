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
public class Consent implements Diffable<Consent> {

  @Id
  @Column(name = "CONSENT_ID")
  private int consent_id;

  @Column(name = "SAM_COLL_BEFORE_SEP_2006")
  private String sam_coll_before_sep_2006;
  
  @Column(name = "DATE_OF_CONSENT")
  private String date_of_consent;

  @Column(name = "VERBAL_CONSENT")
  private String verbal_consent;

  @Column(name = "VERBAL_CONSENT_RECORDED")
  private String verbal_consent_recorded;

  @Column(name = "VERBAL_CONSENT_RECORDED_BY")
  private String verbal_consent_recorded_by;

  @Column(name = "VERBAL_CONSENT_DOCUMENT_ID")
  private Integer verbal_consent_document_id;

  @Column(name = "FORM_TYPE")
  private String form_type;

  @Column(name = "FORM_VERSION_ID")
  private Integer form_version_id;
  
  @Column(name = "DIGITAL_CF_ATTACHMENT_Id")
  private Integer digital_cf_attachment_id;
  
  @Column(name = "EXCLUSIONS_COMMENT")
  private String exclusions_comment;
  
  @Column(name = "CONSENT_NOTES")
  private String consent_notes;
  
  @Column(name = "WITHDRAWN")
  private String withdrawn;

  @Column(name = "WITHDRAWAL_DATE")
  private String withdrawal_date;
  
  @Column(name = "WITHDRAWAL_DOCUMENT_ID")
  private Integer withdrawal_document_id;

  @Column(name = "CN_PATIENT_ID")
  private Integer cn_patient_id;

  @Column(name = "LOC_ID")
  private Integer loc_id;

  @Column(name = "CONSENT_TYPE")
  private String consent_type;

  @Column(name = "CONSENT_TAKEN_BY")
  private String consent_taken_by;
  
  @Column(name = "ADDITIONAL_DOCUMENT_ID")
  private Integer additional_document_id;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "STOP_SAMPLE_DONATION")
  private String stop_sample_donation;

  @Column(name = "STOP_SAMPLE_DONATION_DATE")
  private String stop_sample_donation_date;

  @Column(name = "IS_IMPORTED")
  private String is_imported;

  @Column(name = "IS_VALIDATED")
  private String is_validated;

  @Column(name = "IS_AUDITED")
  private String is_audited;

  @Column(name = "MARKED_FOR_AUDITING")
  private String marked_for_auditing;

  @Column(name = "IS_FINALISED")
  private String is_finalised;

  @Column(name = "CONSENT_DELETION_DATE")
  private String consent_deletion_date;

  @Column(name = "IS_WITHDRAWN")
  private String is_withdrawn;

  @Column(name = "CREATION_DATE")
  private String creation_date;
  
  @Transient
  private String consent_exclusions;

  @Transient
  private String consent_exclusions_list;
  
  @Transient
  private String samples_consented_to;

  @Transient
  private String samples_consented_to_list;
  
  @Transient
  private ConsentFile withdrawal_document;

  @Transient
  private ConsentFile digital_cf_attachment;

  @Transient
  private ConsentFile verbal_consent_document;

  @Transient
  private ConsentFile additional_document;
  
  @Transient
  private FormVersion form_version;

  @Transient
  private String location;

  @Transient
  private String description;

  @Transient
  private String consent_which_department;
  
public String getCreation_date() {
	return creation_date;
}

public void setCreation_date(String creation_date) {
	this.creation_date = creation_date;
}

public String getIs_withdrawn() {
	return is_withdrawn;
}

public void setIs_withdrawn(String is_withdrawn) {
	this.is_withdrawn = is_withdrawn;
}

public String getIs_finalised() {
	return is_finalised;
}

public void setIs_finalised(String is_finalised) {
	this.is_finalised = is_finalised;
}

public String getConsent_deletion_date() {
	return consent_deletion_date;
}

public void setConsent_deletion_date(String consent_deletion_date) {
	this.consent_deletion_date = consent_deletion_date;
}

public String getMarked_for_auditing() {
	return marked_for_auditing;
}

public void setMarked_for_auditing(String marked_for_auditing) {
	this.marked_for_auditing = marked_for_auditing;
}

public String getIs_imported() {
	return is_imported;
}

public void setIs_imported(String is_imported) {
	this.is_imported = is_imported;
}

public String getIs_validated() {
	return is_validated;
}

public void setIs_validated(String is_validated) {
	this.is_validated = is_validated;
}

public String getIs_audited() {
	return is_audited;
}

public void setIs_audited(String is_audited) {
	this.is_audited = is_audited;
}

public String getSam_coll_before_sep_2006() {
	return sam_coll_before_sep_2006;
}

public void setSam_coll_before_sep_2006(String sam_coll_before_sep_2006) {
	this.sam_coll_before_sep_2006 = sam_coll_before_sep_2006;
}

public String getConsent_exclusions_list() {
	return consent_exclusions_list;
}

public void setConsent_exclusions_list(String consent_exclusions_list) {
	this.consent_exclusions_list = consent_exclusions_list;
}

public String getSamples_consented_to_list() {
	return samples_consented_to_list;
}

public void setSamples_consented_to_list(String samples_consented_to_list) {
	this.samples_consented_to_list = samples_consented_to_list;
}

public String getConsent_which_department() {
	return consent_which_department;
}

public void setConsent_which_department(String consent_which_department) {
	this.consent_which_department = consent_which_department;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getSamples_consented_to() {
	return this.samples_consented_to;
}

public void setSamples_consented_to(String samples_consented_to) {
	this.samples_consented_to = samples_consented_to;
}

public String getStop_sample_donation() {
	return this.stop_sample_donation;
}

public void setStop_sample_donation(String stop_sample_donation) {
	this.stop_sample_donation = stop_sample_donation;
}

public String getStop_sample_donation_date() {
	return stop_sample_donation_date;
}

public void setStop_sample_donation_date(String stop_sample_donation_date) {
	this.stop_sample_donation_date = stop_sample_donation_date;
}

public String getConsent_exclusions() {
	return consent_exclusions;
}

public void setConsent_exclusions(String consent_exclusions) {
	this.consent_exclusions = consent_exclusions;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public Integer getAdditional_document_id() {
	return additional_document_id;
}

public void setAdditional_document_id(Integer additional_document_id) {
	this.additional_document_id = additional_document_id;
}

public FormVersion getForm_version() {
	return form_version;
}

public void setForm_version(FormVersion form_version) {
	this.form_version = form_version;
}

public String getConsent_taken_by() {
	return consent_taken_by;
}

public void setConsent_taken_by(String consent_taken_by) {
	this.consent_taken_by = consent_taken_by;
}

public String getConsent_type() {
	return consent_type;
}

public void setConsent_type(String consent_type) {
	this.consent_type = consent_type;
}

public Integer getLoc_id() {
	return loc_id;
}

public void setLoc_id(Integer loc_id) {
	this.loc_id = loc_id;
}

public Integer getCn_patient_id() {
	return cn_patient_id;
}

public void setCn_patient_id(Integer cn_patient_id) {
	this.cn_patient_id = cn_patient_id;
}

public String getVerbal_consent() {
	return verbal_consent;
}

public void setVerbal_consent(String verbal_consent) {
	this.verbal_consent = verbal_consent;
}

public String getVerbal_consent_recorded() {
	return verbal_consent_recorded;
}

public void setVerbal_consent_recorded(String verbal_consent_recorded) {
	this.verbal_consent_recorded = verbal_consent_recorded;
}

public String getVerbal_consent_recorded_by() {
	return verbal_consent_recorded_by;
}

public void setVerbal_consent_recorded_by(String verbal_consent_recorded_by) {
	this.verbal_consent_recorded_by = verbal_consent_recorded_by;
}

public String getForm_type() {
	return form_type;
}

public void setForm_type(String form_type) {
	this.form_type = form_type;
}

public String getExclusions_comment() {
	return exclusions_comment;
}

public Integer getForm_version_id() {
	return form_version_id;
}

public void setForm_version_id(Integer form_version_id) {
	this.form_version_id = form_version_id;
}

public void setExclusions_comment(String exclusions_comment) {
	this.exclusions_comment = exclusions_comment;
}

public String getConsent_notes() {
	return consent_notes;
}

public void setConsent_notes(String consent_notes) {
	this.consent_notes = consent_notes;
}

public String getWithdrawn() {
	return withdrawn;
}

public void setWithdrawn(String withdrawn) {
	this.withdrawn = withdrawn;
}

public String getWithdrawal_date() {
	return withdrawal_date;
}

public void setWithdrawal_date(String withdrawal_date) {
	this.withdrawal_date = withdrawal_date;
}

public int getConsent_id() {
	return consent_id;
}

public void setConsent_id(int consent_id) {
	this.consent_id = consent_id;
}

public String getDate_of_consent() {
	return date_of_consent;
}

public void setDate_of_consent(String date_of_consent) {
	this.date_of_consent = date_of_consent;
}

public Integer getVerbal_consent_document_id() {
	return verbal_consent_document_id;
}

public void setVerbal_consent_document_id(Integer verbal_consent_document_id) {
	this.verbal_consent_document_id = verbal_consent_document_id;
}

public Integer getDigital_cf_attachment_id() {
	return digital_cf_attachment_id;
}

public void setDigital_cf_attachment_id(Integer digital_cf_attachment_id) {
	this.digital_cf_attachment_id = digital_cf_attachment_id;
}

public Integer getWithdrawal_document_id() {
	return withdrawal_document_id;
}

public void setWithdrawal_document_id(Integer withdrawal_document_id) {
	this.withdrawal_document_id = withdrawal_document_id;
}

public ConsentFile getWithdrawal_document() {
	return withdrawal_document;
}

public void setWithdrawal_document(ConsentFile withdrawal_document) {
	this.withdrawal_document = withdrawal_document;
}

public ConsentFile getDigital_cf_attachment() {
	return digital_cf_attachment;
}

public void setDigital_cf_attachment(ConsentFile digital_cf_attachment) {
	this.digital_cf_attachment = digital_cf_attachment;
}

public ConsentFile getVerbal_consent_document() {
	return verbal_consent_document;
}

public void setVerbal_consent_document(ConsentFile verbal_consent_document) {
	this.verbal_consent_document = verbal_consent_document;
}

public ConsentFile getAdditional_document() {
	return additional_document;
}

public void setAdditional_document(ConsentFile additional_document) {
	this.additional_document = additional_document;
}

@Override
public String toString() {
	return "Date Of Consent = " + date_of_consent + ", Verbal Consent = "
			+ verbal_consent + ", Verbal Consent Recorded = " + verbal_consent_recorded + ", Verbal Consent Recorded By = "
			+ verbal_consent_recorded_by + ", Form Type = " + form_type + ", Exclusions Comment = " + exclusions_comment + ", Notes = " + consent_notes
			+ ", Withdrawn = " + withdrawn + ", Withdrawal Date = " + withdrawal_date + ", Consent Type = "
			+ consent_type + ", Consent Taken By = " + consent_taken_by + ", Stop Sample Donation = " + stop_sample_donation
			+ ", Stop Sample Donation Date = " + stop_sample_donation_date + ", Consent Exclusions = " + consent_exclusions
			+ ", Samples Consented To = " + samples_consented_to + ", Withdrawal Document = " + withdrawal_document
			+ ", Digital Cf Attachment = " + digital_cf_attachment + ", Verbal Consent Document = "
			+ verbal_consent_document + ", Additional Document = " + additional_document + ", Form Version = " 
			+ form_version + ", Location = " + location + ", Consent Exclusions = " + consent_exclusions_list 
			+ ", Samples Consented To List = " + samples_consented_to_list + ", Samples Collected Before September 2006 = " + sam_coll_before_sep_2006;
}

@Override
public DiffResult diff(Consent con) {
	
	DiffBuilder db = new DiffBuilder(this, con, ToStringStyle.SHORT_PREFIX_STYLE);

	if ((this.sam_coll_before_sep_2006 != null && !this.sam_coll_before_sep_2006.isEmpty()) || (con.sam_coll_before_sep_2006 != null && !con.sam_coll_before_sep_2006.isEmpty()))
	    db.append("sam_coll_before_sep_2006", this.sam_coll_before_sep_2006, con.sam_coll_before_sep_2006);
    if ((this.date_of_consent != null && !this.date_of_consent.isEmpty()) || (con.date_of_consent != null && !con.date_of_consent.isEmpty()))
	    db.append("date_of_consent", this.date_of_consent, con.date_of_consent);
    if ((this.verbal_consent != null && !this.verbal_consent.isEmpty()) || (con.verbal_consent != null && !con.verbal_consent.isEmpty()))
	    db.append("verbal_consent", this.verbal_consent, con.verbal_consent);
    if ((this.verbal_consent_recorded != null && !this.verbal_consent_recorded.isEmpty()) || (con.verbal_consent_recorded != null && !con.verbal_consent_recorded.isEmpty()))
	    db.append("verbal_consent_recorded", this.verbal_consent_recorded, con.verbal_consent_recorded);
    if ((this.verbal_consent_recorded_by != null && !this.verbal_consent_recorded_by.isEmpty()) || (con.verbal_consent_recorded_by != null && !con.verbal_consent_recorded_by.isEmpty()))
	    db.append("verbal_consent_recorded_by", this.verbal_consent_recorded_by, con.verbal_consent_recorded_by);

    if(this.verbal_consent_document_id == null) this.verbal_consent_document_id = 0;
    if(con.verbal_consent_document_id == null) con.verbal_consent_document_id = 0;
    if(this.verbal_consent_document_id.intValue() != con.verbal_consent_document_id.intValue()) {
    	db.append("verbal_consent_document_id", this.verbal_consent_document_id, con.verbal_consent_document_id);
    	db.append("verbal_consent_document", this.verbal_consent_document, con.verbal_consent_document);
    }
    if(this.digital_cf_attachment_id == null) this.digital_cf_attachment_id = 0;
    if(con.digital_cf_attachment_id == null) con.digital_cf_attachment_id = 0;
    if(this.digital_cf_attachment_id.intValue() != con.digital_cf_attachment_id.intValue()) {
    	db.append("digital_cf_attachment_id", this.digital_cf_attachment_id, con.digital_cf_attachment_id);
    	db.append("digital_cf_attachment", this.digital_cf_attachment, con.digital_cf_attachment);
    }
    if(this.withdrawal_document_id == null) this.withdrawal_document_id = 0;
    if(con.withdrawal_document_id == null) con.withdrawal_document_id = 0;
    if(this.withdrawal_document_id.intValue() != con.withdrawal_document_id.intValue()) {
    	db.append("withdrawal_document_id", this.withdrawal_document_id, con.withdrawal_document_id);
    	db.append("withdrawal_document", this.withdrawal_document, con.withdrawal_document);
    }
    if(this.additional_document_id == null) this.additional_document_id = 0;
    if(con.additional_document_id == null) con.additional_document_id = 0;
    if(this.additional_document_id.intValue() != con.additional_document_id.intValue()) {
    	db.append("additional_document_id", this.additional_document_id, con.additional_document_id);
    	db.append("additional_document", this.additional_document, con.additional_document);
    }
    if(this.form_version_id == null) this.form_version_id = 0;
    if(con.form_version_id == null) con.form_version_id = 0;
    if(this.form_version_id.intValue() != con.form_version_id.intValue()) {
    	db.append("form_version_id", this.form_version_id, con.form_version_id);
    	db.append("form_version", this.form_version, con.form_version);
    }

    if(this.loc_id == null) this.loc_id = 0;
    if(con.loc_id == null) con.loc_id = 0;
    if(this.loc_id.intValue() != con.loc_id.intValue()) {
    	db.append("loc_id", this.loc_id, con.loc_id);
    	db.append("location", this.location, con.location);
    }
    
    if ((this.form_type != null && !this.form_type.isEmpty()) || (con.form_type != null && !con.form_type.isEmpty()))
	    db.append("form_type", this.form_type, con.form_type);
    if ((this.exclusions_comment != null && !this.exclusions_comment.isEmpty()) || (con.exclusions_comment != null && !con.exclusions_comment.isEmpty()))
		db.append("exclusions_comment", this.exclusions_comment, con.exclusions_comment);
    if ((this.consent_notes != null && !this.consent_notes.isEmpty()) || (con.consent_notes != null && !con.consent_notes.isEmpty()))
		db.append("consent_notes", this.consent_notes, con.consent_notes);
    if ((this.withdrawn != null && !this.withdrawn.isEmpty()) || (con.withdrawn != null && !con.withdrawn.isEmpty()))
		db.append("withdrawn", this.withdrawn, con.withdrawn);
    if ((this.withdrawal_date != null && !this.withdrawal_date.isEmpty()) || (con.withdrawal_date != null && !con.withdrawal_date.isEmpty()))
		db.append("withdrawal_date", this.withdrawal_date, con.withdrawal_date);
    if ((this.consent_type != null && !this.consent_type.isEmpty()) || (con.consent_type != null && !con.consent_type.isEmpty()))
	    db.append("consent_type", this.consent_type, con.consent_type);
    if ((this.consent_taken_by != null && !this.consent_taken_by.isEmpty()) || (con.consent_taken_by != null && !con.consent_taken_by.isEmpty()))
	    db.append("consent_taken_by", this.consent_taken_by, con.consent_taken_by);
    if ((this.stop_sample_donation != null && !this.stop_sample_donation.isEmpty()) || (con.stop_sample_donation != null && !con.stop_sample_donation.isEmpty()))
	    db.append("stop_sample_donation", this.stop_sample_donation, con.stop_sample_donation);
    if ((this.stop_sample_donation_date != null && !this.stop_sample_donation_date.isEmpty()) || (con.stop_sample_donation_date != null && !con.stop_sample_donation_date.isEmpty()))
	    db.append("stop_sample_donation_date", this.stop_sample_donation_date, con.stop_sample_donation_date);
    
    return db.build();
    
}

}
