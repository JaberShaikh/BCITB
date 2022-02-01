package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SampleType {

  @Id
  @Column(name = "SAMPLE_TYPE_ID")
  private int sample_type_id;

  @Column(name = "DESCRIPTION")
  private String description;

public int getSample_type_id() {
	return sample_type_id;
}

public void setSample_type_id(int sample_type_id) {
	this.sample_type_id = sample_type_id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

@Override
public String toString() {
	return description;
}

}