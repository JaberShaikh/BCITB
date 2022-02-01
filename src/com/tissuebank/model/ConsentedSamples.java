package com.tissuebank.model;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConsentedSamples {

  @Id
  @Column(name = "CONSENTED_SAMPLES_ID")
  private int consented_samples_id;
	
  @Column(name = "CONSENT_SAMPLE_TYPE_ID")
  private int consent_sample_type_id;
  
  @Column(name = "CONSENT_ID")
  private String consent_id;

public int getConsented_samples_id() {
	return consented_samples_id;
}

public void setConsented_samples_id(int consented_samples_id) {
	this.consented_samples_id = consented_samples_id;
}

public int getConsent_sample_type_id() {
	return consent_sample_type_id;
}

public void setConsent_sample_type_id(int consent_sample_type_id) {
	this.consent_sample_type_id = consent_sample_type_id;
}

public String getConsent_id() {
	return consent_id;
}

public void setConsent_id(String consent_id) {
	this.consent_id = consent_id;
}

}