package com.tissuebank.model.global.views;

import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "TB_ACTION_ROLE_V")
public class ActionRole {

  @Id
  @Column(name = "ACTION_ROLE_ID")
  private int action_role_id;
	
  @Column(name = "ACTION_TYPE")
  private String action_type;
  
  @Column(name = "ROLE_ID")
  private int role_id;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)  
  private Role role;
  
public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}

public int getAction_role_id() {
	return action_role_id;
}

public void setAction_role_id(int action_role_id) {
	this.action_role_id = action_role_id;
}

public String getAction_type() {
	return action_type;
}

public void setAction_type(String action_type) {
	this.action_type = action_type;
}

public int getRole_id() {
	return role_id;
}

public void setRole_id(int role_id) {
	this.role_id = role_id;
}

@Override
public String toString() {
	return "ActionRole [action_role_id=" + action_role_id + ", action_type=" + action_type + ", role_id=" + role_id
			+ ", role=" + role + "]";
}
  
}