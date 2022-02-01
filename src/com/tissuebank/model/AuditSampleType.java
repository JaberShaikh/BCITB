package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AuditSampleType {

  @Id
  @Column(name = "AUDIT_SAMPLE_TYPE_ID")
  private int audit_sample_type_id;

  @Column(name = "DESCRIPTION")
  private String description;

public int getAudit_sample_type_id() {
	return audit_sample_type_id;
}

public void setAudit_sample_type_id(int audit_sample_type_id) {
	this.audit_sample_type_id = audit_sample_type_id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

@Override
public String toString() {
	return "AuditSampleType [audit_sample_type_id=" + audit_sample_type_id + ", description=" + description + "]";
}

}