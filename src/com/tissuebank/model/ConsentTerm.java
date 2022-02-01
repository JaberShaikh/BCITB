package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConsentTerm {

  @Id
  @Column(name = "CONSENT_TERM_ID")
  private int consent_term_id;

  @Column(name = "DESCRIPTION")
  private String description;

public int getConsent_term_id() {
	return consent_term_id;
}

public void setConsent_term_id(int consent_term_id) {
	this.consent_term_id = consent_term_id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

}