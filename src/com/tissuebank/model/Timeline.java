package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Timeline  implements Comparable<Timeline>
{

  @Id
  @Column(name = "TIMELINE_ID")
  private int timeline_id;

  @Column(name = "DATA_ID")
  private int data_id;

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
  
  @Column(name = "DATA_TYPE")
  private String data_type;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "IS_ENCRYPTED")
  private String is_encrypted;

  @Column(name = "TL_PATIENT_ID")
  private Integer tl_patient_id;

  @Transient
  private String decrypted_notes;
  
public String getIs_encrypted() {
	return is_encrypted;
}

public void setIs_encrypted(String is_encrypted) {
	this.is_encrypted = is_encrypted;
}

public Integer getTl_patient_id() {
	return tl_patient_id;
}

public void setTl_patient_id(Integer tl_patient_id) {
	this.tl_patient_id = tl_patient_id;
}

public String getDecrypted_notes() {
	return decrypted_notes;
}

public void setDecrypted_notes(String decrypted_notes) {
	this.decrypted_notes = decrypted_notes;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public int getData_id() {
	return data_id;
}

public void setData_id(int data_id) {
	this.data_id = data_id;
}

public int getUser_id() {
	return user_id;
}

public int getTimeline_id() {
	return timeline_id;
}

public void setTimeline_id(int timeline_id) {
	this.timeline_id = timeline_id;
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

public String getDate_of_entry() {
	return date_of_entry;
}

public void setDate_of_entry(String date_of_entry) {
	this.date_of_entry = date_of_entry;
}

public String getData_type() {
	return data_type;
}

public void setData_type(String data_type) {
	this.data_type = data_type;
}

@Override
public String toString() {
	return "Timeline [timeline_id=" + timeline_id + ", data_id=" + data_id + ", user_id=" + user_id + ", role_id="
			+ role_id + ", status=" + status + ", notes=" + notes + ", date_of_entry=" + date_of_entry + ", data_type="
			+ data_type + ", description=" + description + "]";
}

@Override
public int compareTo(Timeline tl) {
	return (int) (this.timeline_id-tl.timeline_id);
}

}