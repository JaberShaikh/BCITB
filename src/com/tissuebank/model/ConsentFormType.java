package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConsentFormType {

  @Id
  @Column(name = "CONSENT_FORM_TYPE_ID")
  private int consent_form_type_id;

  @Column(name = "DESCRIPTION")
  private String description;

public int getConsent_form_type_id() {
	return consent_form_type_id;
}

public void setConsent_form_type_id(int consent_form_type_id) {
	this.consent_form_type_id = consent_form_type_id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

}