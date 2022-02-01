package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConsentTimeline {

  @Id
  @Column(name = "CONSENT_TIMELINE_ID")
  private int consent_timeline_id;

  @Column(name = "CONSENT_ID")
  private int consent_id;

  @Column(name = "USER_ID")
  private int user_id;

  @Column(name = "ROLE_ID")
  private int role_id;
  
  @Column(name = "STATUS")
  private String status;

  @Column(name = "NOTES")
  private String notes;

  @Column(name = "DATE_OF_ENTRY")
  private String date_of_entry;

  public String getDate_of_entry() {
	return date_of_entry;
  }

public int getConsent_timeline_id() {
	return consent_timeline_id;
}

public void setConsent_timeline_id(int consent_timeline_id) {
	this.consent_timeline_id = consent_timeline_id;
}

public void setDate_of_entry(String date_of_entry) {
	this.date_of_entry = date_of_entry;
}

public int getConsent_id() {
	return consent_id;
}

public void setConsent_id(int consent_id) {
	this.consent_id = consent_id;
}

public int getUser_id() {
	return user_id;
}

public void setUser_id(int user_id) {
	this.user_id = user_id;
}

public int getRole_id() {
	return role_id;
}

public void setRole_id(int role_id) {
	this.role_id = role_id;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getNotes() {
	return notes;
}

public void setNotes(String notes) {
	this.notes = notes;
}

}