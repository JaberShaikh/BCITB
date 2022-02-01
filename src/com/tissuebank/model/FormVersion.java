package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FormVersion {

  @Id
  @Column(name = "FORM_VERSION_ID")
  private int form_version_id;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "FORM_TYPE")
  private String form_type;

public int getForm_version_id() {
	return form_version_id;
}

public void setForm_version_id(int form_version_id) {
	this.form_version_id = form_version_id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getForm_type() {
	return form_type;
}

public void setForm_type(String form_type) {
	this.form_type = form_type;
}

@Override
public String toString() {
	return "[Description=" + description + ", form_type=" + form_type + "]";
}

}