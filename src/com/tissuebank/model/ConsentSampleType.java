package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConsentSampleType {

  @Id
  @Column(name = "CONSENT_SAMPLE_TYPE_ID")
  private int consent_sample_type_id;

  @Column(name = "DESCRIPTION")
  private String description;

public int getConsent_sample_type_id() {
	return consent_sample_type_id;
}

public void setConsent_sample_type_id(int consent_sample_type_id) {
	this.consent_sample_type_id = consent_sample_type_id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

@Override
public String toString() {
	return "ConsentSampleType [consent_sample_type_id=" + consent_sample_type_id + ", description=" + description + "]";
}

}