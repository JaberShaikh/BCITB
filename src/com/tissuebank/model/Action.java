package com.tissuebank.model;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Action implements Diffable<Action> 
{

  @Id
  @Column(name = "ACTION_ID")
  private int action_id;
	
  @Column(name = "DATA_ID")
  private int data_id;

  @Column(name = "DATA_TYPE")
  private String data_type;
  
  @Column(name = "ACTION_TYPE")
  private String action_type;

  @Column(name = "NOTES")
  private String notes;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "CREATED_BY")
  private Integer created_by;

  @Column(name = "CREATED_DATE_TIME")
  private String created_date_time;
  
  @Column(name = "COMPLETED_BY")
  private Integer completed_by;

  @Column(name = "COMPLETED_DATE_TIME")
  private String completed_date_time;
  
  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "LOCKED_DESCRIPTION")
  private String locked_description;

  @Column(name = "LOCKED_BY")
  private Integer locked_by;
  
  @Transient
  private String action_which_department;
  
  @Transient
  private String user_selected_action_type;

  @Transient
  private String user_selected_action_notes;

  @Transient
  private String all_roles;

public Action(int data_id, String data_type, String action_type, String notes, String status, String created_date_time, int created_by, String description) {
	super();
	this.data_id = data_id;
	this.data_type = data_type;
	this.action_type = action_type;
	this.notes = notes;
	this.status = status;
	this.created_date_time = created_date_time;
	this.created_by = created_by;
	this.description = description;
}

public Action(int data_id, String data_type, String action_type, String status, String action_which_department) {
	super();
	this.data_id = data_id;
	this.data_type = data_type;
	this.action_type = action_type;
	this.status = status;
	this.action_which_department = action_which_department;
}

public Action() {
	super();
}

public Integer getLocked_by() {
	return locked_by;
}

public void setLocked_by(Integer locked_by) {
	this.locked_by = locked_by;
}

public void setData_id(int data_id) {
	this.data_id = data_id;
}

public String getAll_roles() {
	return all_roles;
}

public void setAll_roles(String all_roles) {
	this.all_roles = all_roles;
}

public String getAction_which_department() {
	return action_which_department;
}

public void setAction_which_department(String action_which_department) {
	this.action_which_department = action_which_department;
}

public String getLocked_description() {
	return locked_description;
}

public void setLocked_description(String locked_description) {
	this.locked_description = locked_description;
}

public int getAction_id() {
	return action_id;
}

public void setAction_id(int action_id) {
	this.action_id = action_id;
}

public Integer getCreated_by() {
	return created_by;
}

public void setCreated_by(Integer created_by) {
	this.created_by = created_by;
}

public String getCreated_date_time() {
	return created_date_time;
}

public void setCreated_date_time(String created_date_time) {
	this.created_date_time = created_date_time;
}

public Integer getCompleted_by() {
	return completed_by;
}

public void setCompleted_by(Integer completed_by) {
	this.completed_by = completed_by;
}

public String getCompleted_date_time() {
	return completed_date_time;
}

public void setCompleted_date_time(String completed_date_time) {
	this.completed_date_time = completed_date_time;
}

public String getUser_selected_action_notes() {
	return user_selected_action_notes;
}

public void setUser_selected_action_notes(String user_selected_action_notes) {
	this.user_selected_action_notes = user_selected_action_notes;
}

public String getUser_selected_action_type() {
	return user_selected_action_type;
}

public void setUser_selected_action_type(String user_selected_action_type) {
	this.user_selected_action_type = user_selected_action_type;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getNotes() {
	return notes;
}

public void setNotes(String notes) {
	this.notes = notes;
}

public String getData_type() {
	return data_type;
}

public void setData_type(String data_type) {
	this.data_type = data_type;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public Integer getData_id() {
	return data_id;
}

public void setData_id(Integer data_id) {
	this.data_id = data_id;
}

public String getAction_type() {
	return action_type;
}

public void setAction_type(String action_type) {
	this.action_type = action_type;
}

@Override
public String toString() {
	return "Action [action_id=" + action_id + ", data_id=" + data_id + ", data_type=" + data_type + ", action_type="
			+ action_type + ", notes=" + notes + ", status=" + status + ", created_by=" + created_by
			+ ", created_date_time=" + created_date_time + ", completed_by=" + completed_by + ", completed_date_time="
			+ completed_date_time + ", description=" + description + ", action_which_department="
			+ action_which_department + ", user_selected_action_type=" + user_selected_action_type
			+ ", user_selected_action_notes=" + user_selected_action_notes + ", all_roles=" + all_roles + "]";
}

@Override
public DiffResult diff(Action act) {
	DiffBuilder db = new DiffBuilder(this, act, ToStringStyle.SHORT_PREFIX_STYLE);
    if ((this.notes != null && !this.notes.isEmpty()) || (act.notes != null && !act.notes.isEmpty()))
	    db.append("notes", this.notes, act.notes);
    if ((this.status != null && !this.status.isEmpty()) || (act.status != null && !act.status.isEmpty()))
	    db.append("status", this.status, act.status);
    if ((this.created_date_time != null && !this.created_date_time.isEmpty()) || (act.created_date_time != null && !act.created_date_time.isEmpty()))
	    db.append("created_date_time", this.created_date_time, act.created_date_time);
    if ((this.completed_date_time != null && !this.completed_date_time.isEmpty()) || (act.completed_date_time != null && !act.completed_date_time.isEmpty()))
	    db.append("completed_date_time", this.completed_date_time, act.completed_date_time);
    if ((this.description != null && !this.description.isEmpty()) || (act.description != null && !act.description.isEmpty()))
	    db.append("description", this.description, act.description);
    db.append("created_by", this.created_by, act.created_by);
    db.append("completed_by", this.completed_by, act.completed_by);
	return db.build();
}

}