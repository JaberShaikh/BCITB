package com.tissuebank.model.global.tables;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tissuebank.model.global.views.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "TB_AUDIT_TEAM")
public class AuditTeam 
{
  @Id
  @Column(name = "AUDIT_TEAM_ID")
  private int audit_team_id;
	
  @Column(name = "DEPARTMENT")
  private String department;

  @Column(name = "USER_ID")
  private int user_id;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", insertable = false, updatable = false)  
  private User user;
  
public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public int getAudit_team_id() {
	return audit_team_id;
}

public void setAudit_team_id(int audit_team_id) {
	this.audit_team_id = audit_team_id;
}

public String getDepartment() {
	return department;
}

public void setDepartment(String department) {
	this.department = department;
}

public int getUser_id() {
	return user_id;
}

public void setUser_id(int user_id) {
	this.user_id = user_id;
}

}