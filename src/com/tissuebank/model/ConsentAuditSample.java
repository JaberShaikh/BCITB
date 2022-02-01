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
public class ConsentAuditSample implements Diffable<ConsentAuditSample> {

  @Id
  @Column(name = "AUD_SAMPLE_PID")
  private int aud_sample_pid;

  @Column(name = "CA_ID")
  private int ca_id;
  
  @Column(name = "AUD_SAMPLE_TYPE_ID")
  private int aud_sample_type_id;
  
  @Column(name = "AUD_SAMPLE_ID")
  private String aud_sample_id;
  
  @Column(name = "SAMPLE_DATE")
  private String sample_date;
  
  @Column(name = "AFTER_CONSENT_DATE")
  private String after_consent_date;
  
  @Column(name = "SAMPLE_IN_ASSIGNED_LOCATION")
  private String sample_in_assigned_location;
  
  @Column(name = "SAMPLE_DETAILS_LEGIBLE")
  private String sample_details_legible;
  
  @Column(name = "APPROPRIATE_CONSENT_PRESENT")
  private String appropriate_consent_present;
  
  @Column(name = "NON_CONFORMANCES_DETAILS")
  private String non_conformances_details;

  @Transient
  private AuditSampleType audit_sample_type;
  
  @Transient
  private String cas_which_department;

public ConsentAuditSample(int aud_sample_pid, String cas_which_department) {
	super();
	this.aud_sample_pid = aud_sample_pid;
	this.cas_which_department = cas_which_department;
}

public AuditSampleType getAudit_sample_type() {
	return audit_sample_type;
}

public void setAudit_sample_type(AuditSampleType audit_sample_type) {
	this.audit_sample_type = audit_sample_type;
}

public ConsentAuditSample() {
	super();
}

public int getAud_sample_pid() {
	return aud_sample_pid;
}

public void setAud_sample_pid(int aud_sample_pid) {
	this.aud_sample_pid = aud_sample_pid;
}

public String getAud_sample_id() {
	return aud_sample_id;
}

public void setAud_sample_id(String aud_sample_id) {
	this.aud_sample_id = aud_sample_id;
}

public int getCa_id() {
	return ca_id;
}

public void setCa_id(int ca_id) {
	this.ca_id = ca_id;
}

public int getAud_sample_type_id() {
	return aud_sample_type_id;
}

public void setAud_sample_type_id(int aud_sample_type_id) {
	this.aud_sample_type_id = aud_sample_type_id;
}

public String getSample_date() {
	return sample_date;
}

public void setSample_date(String sample_date) {
	this.sample_date = sample_date;
}

public String getAfter_consent_date() {
	return after_consent_date;
}

public void setAfter_consent_date(String after_consent_date) {
	this.after_consent_date = after_consent_date;
}

public String getSample_in_assigned_location() {
	return sample_in_assigned_location;
}

public void setSample_in_assigned_location(String sample_in_assigned_location) {
	this.sample_in_assigned_location = sample_in_assigned_location;
}

public String getSample_details_legible() {
	return sample_details_legible;
}

public void setSample_details_legible(String sample_details_legible) {
	this.sample_details_legible = sample_details_legible;
}

public String getAppropriate_consent_present() {
	return appropriate_consent_present;
}

public void setAppropriate_consent_present(String appropriate_consent_present) {
	this.appropriate_consent_present = appropriate_consent_present;
}

public String getNon_conformances_details() {
	return non_conformances_details;
}

public void setNon_conformances_details(String non_conformances_details) {
	this.non_conformances_details = non_conformances_details;
}

public String getCas_which_department() {
	return cas_which_department;
}

public void setCas_which_department(String cas_which_department) {
	this.cas_which_department = cas_which_department;
}

@Override
public String toString() {
	return "ConsentAuditSample [aud_sample_pid=" + aud_sample_pid + ", ca_id=" + ca_id + ", aud_sample_type_id="
			+ aud_sample_type_id + ", aud_sample_id=" + aud_sample_id + ", sample_date=" + sample_date
			+ ", after_consent_date=" + after_consent_date + ", sample_in_assigned_location="
			+ sample_in_assigned_location + ", sample_details_legible=" + sample_details_legible
			+ ", appropriate_consent_present=" + appropriate_consent_present + ", non_conformances_details="
			+ non_conformances_details + ", audit_sample_type=" + audit_sample_type + ", cas_which_department="
			+ cas_which_department + "]";
}

@Override
public DiffResult diff(ConsentAuditSample consent_audit) {
	DiffBuilder db = new DiffBuilder(this, consent_audit, ToStringStyle.SHORT_PREFIX_STYLE);
    if ((this.aud_sample_id != null && !this.aud_sample_id.isEmpty()) || (consent_audit.aud_sample_id != null && !consent_audit.aud_sample_id.isEmpty()))
	    db.append("aud_sample_id", this.aud_sample_id, consent_audit.aud_sample_id);
    if(this.aud_sample_type_id != consent_audit.aud_sample_type_id) {
    	db.append("aud_sample_type_id", this.aud_sample_type_id, consent_audit.aud_sample_type_id);
    	db.append("audit_sample_type", this.audit_sample_type, consent_audit.audit_sample_type);
    }
    if ((this.sample_date != null && !this.sample_date.isEmpty()) || (consent_audit.sample_date != null && !consent_audit.sample_date.isEmpty()))
	    db.append("sample_date", this.sample_date, consent_audit.sample_date);
    if ((this.after_consent_date != null && !this.after_consent_date.isEmpty()) || (consent_audit.after_consent_date != null && !consent_audit.after_consent_date.isEmpty()))
	    db.append("after_consent_date", this.after_consent_date, consent_audit.after_consent_date);
    if ((this.sample_in_assigned_location != null && !this.sample_in_assigned_location.isEmpty()) || (consent_audit.sample_in_assigned_location != null && !consent_audit.sample_in_assigned_location.isEmpty()))
	    db.append("sample_in_assigned_location", this.sample_in_assigned_location, consent_audit.sample_in_assigned_location);
    if ((this.sample_details_legible != null && !this.sample_details_legible.isEmpty()) || (consent_audit.sample_details_legible != null && !consent_audit.sample_details_legible.isEmpty()))
	    db.append("sample_details_legible", this.sample_details_legible, consent_audit.sample_details_legible);
    if ((this.appropriate_consent_present != null && !this.appropriate_consent_present.isEmpty()) || (consent_audit.appropriate_consent_present != null && !consent_audit.appropriate_consent_present.isEmpty()))
	    db.append("consent_appropriate", this.appropriate_consent_present, consent_audit.appropriate_consent_present);
    if ((this.non_conformances_details != null && !this.non_conformances_details.isEmpty()) || (consent_audit.non_conformances_details != null && !consent_audit.non_conformances_details.isEmpty()))
	    db.append("non_conformances_details", this.non_conformances_details, consent_audit.non_conformances_details);
    
    return db.build();
}
  
}