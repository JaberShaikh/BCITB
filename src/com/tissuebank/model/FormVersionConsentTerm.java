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
public class FormVersionConsentTerm implements Serializable
{
  @Id
  @Column(name = "FVCT_ID")
  private int fvct_id;
	
  @Column(name = "FORM_VERSION_ID")
  private int form_version_id;

  @Column(name = "CONSENT_TERM_ID")
  private int consent_term_id;

  @Transient
  private FormVersion form_version;

  @Transient
  private ConsentTerm consent_term;

public int getFvct_id() {
	return fvct_id;
}

public void setFvct_id(int fvct_id) {
	this.fvct_id = fvct_id;
}

public int getForm_version_id() {
	return form_version_id;
}

public void setForm_version_id(int form_version_id) {
	this.form_version_id = form_version_id;
}

public int getConsent_term_id() {
	return consent_term_id;
}

public void setConsent_term_id(int consent_term_id) {
	this.consent_term_id = consent_term_id;
}

public FormVersion getForm_version() {
	return form_version;
}

public void setForm_version(FormVersion form_version) {
	this.form_version = form_version;
}

public ConsentTerm getConsent_term() {
	return consent_term;
}

public void setConsent_term(ConsentTerm consent_term) {
	this.consent_term = consent_term;
}

}