package com.tissuebank.model;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConsentExclusion {

  @Id
  @Column(name = "CONSENT_EXCLUSION_ID")
  private int consent_exclusion_id;
	
  @Column(name = "CONSENT_TERM_ID")
  private int consent_term_id;
  
  @Column(name = "CONSENT_ID")
  private int consent_id;

  @Transient
  private ConsentTerm consent_term;
  
public ConsentTerm getConsent_term() {
	return consent_term;
}

public void setConsent_term(ConsentTerm consent_term) {
	this.consent_term = consent_term;
}

public int getConsent_exclusion_id() {
	return consent_exclusion_id;
}

public void setConsent_exclusion_id(int consent_exclusion_id) {
	this.consent_exclusion_id = consent_exclusion_id;
}

public int getConsent_term_id() {
	return consent_term_id;
}

public void setConsent_term_id(int consent_term_id) {
	this.consent_term_id = consent_term_id;
}

public int getConsent_id() {
	return consent_id;
}

public void setConsent_id(int consent_id) {
	this.consent_id = consent_id;
}

@Override
public String toString() {
	return "ConsentExclusion [consent_exclusion_id=" + consent_exclusion_id + ", consent_term_id=" + consent_term_id
			+ ", consent_id=" + consent_id + ", consent_term=" + consent_term + "]";
}

}