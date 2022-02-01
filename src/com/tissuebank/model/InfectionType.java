package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class InfectionType {

  @Id
  @Column(name = "INFECTION_TYPE_ID")
  private int infection_type_id;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "SHORT_DESCRIPTION")
  private String short_description;

public String getShort_description() {
	return short_description;
}

public void setShort_description(String short_description) {
	this.short_description = short_description;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public int getInfection_type_id() {
	return infection_type_id;
}

public void setInfection_type_id(int infection_type_id) {
	this.infection_type_id = infection_type_id;
}

@Override
public String toString() {
	return "InfectionType [infection_type_id=" + infection_type_id + ", description=" + description
			+ ", short_description=" + short_description + "]";
}

}