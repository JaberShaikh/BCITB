package com.tissuebank.model.global.views;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "TB_ROLE_ACCESS_V")
public class RoleAccess 
{
  @Id
  @Column(name = "ROLE_ACCESS_ID")
  private int role_access_id;
	
  @Column(name = "ROLE_ID")
  private int role_id;

  @Column(name = "ACCESS_ID")
  private int access_id;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)  
  private Role role;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "ACCESS_ID", insertable = false, updatable = false)  
  private Access access;
  
public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}

public Access getAccess() {
	return access;
}

public void setAccess(Access access) {
	this.access = access;
}

public int getRole_access_id() {
	return role_access_id;
}

public void setRole_access_id(int role_access_id) {
	this.role_access_id = role_access_id;
}

public int getRole_id() {
	return role_id;
}

public void setRole_id(int role_id) {
	this.role_id = role_id;
}

public int getAccess_id() {
	return access_id;
}

public void setAccess_id(int access_id) {
	this.access_id = access_id;
}

}