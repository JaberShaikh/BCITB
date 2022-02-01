package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FormTypeVersionConsentTerm implements Serializable
{
  @Id
  @Column(name = "FTVCT_ID")
  private int ftvct_id;
	
  @Column(name = "CONSENT_TERM_ID")
  private int consent_term_id;

  @Column(name = "FORM_TYPE_ID")
  private int form_type_id;

  @Column(name = "FORM_VERSION_ID")
  private int form_version_id;
  
  @Transient
  private ConsentTerm consent_term;

  @Transient
  private ConsentFormType consent_form_type;
  
public ConsentFormType getConsent_form_type() {
	return consent_form_type;
}

public void setConsent_form_type(ConsentFormType consent_form_type) {
	this.consent_form_type = consent_form_type;
}

public int getFtvct_id() {
	return ftvct_id;
}

public void setFtvct_id(int ftvct_id) {
	this.ftvct_id = ftvct_id;
}

public int getConsent_term_id() {
	return consent_term_id;
}

public void setConsent_term_id(int consent_term_id) {
	this.consent_term_id = consent_term_id;
}

public int getForm_type_id() {
	return form_type_id;
}

public void setForm_type_id(int form_type_id) {
	this.form_type_id = form_type_id;
}

public int getForm_version_id() {
	return form_version_id;
}

public void setForm_version_id(int form_version_id) {
	this.form_version_id = form_version_id;
}

public ConsentTerm getConsent_term() {
	return consent_term;
}

public void setConsent_term(ConsentTerm consent_term) {
	this.consent_term = consent_term;
}

}