package com.tissuebank.model.global.views;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tissuebank.model.global.views.Role;
import com.tissuebank.model.global.views.User;

import javax.persistence.JoinColumn;

import javax.persistence.CascadeType;

@Entity
@Table(name = "TB_USERS_ROLES_V")
public class UserRole 
{
  @Id
  @Column(name = "USER_ROLE_ID")
  private int user_role_id;
	
  @Column(name = "ROLE_ID")
  private int role_id;

  @Column(name = "USER_ID")
  private int user_id;

  @Column(name = "IS_PRIMARY_ROLE")
  private int is_primary_role;
  
  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", insertable = false, updatable = false)  
  private User user;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)  
  private Role role;

public int getUser_role_id() {
	return user_role_id;
}

public void setUser_role_id(int user_role_id) {
	this.user_role_id = user_role_id;
}

public int getRole_id() {
	return role_id;
}

public void setRole_id(int role_id) {
	this.role_id = role_id;
}

public int getUser_id() {
	return user_id;
}

public void setUser_id(int user_id) {
	this.user_id = user_id;
}

public int getIs_primary_role() {
	return is_primary_role;
}

public void setIs_primary_role(int is_primary_role) {
	this.is_primary_role = is_primary_role;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}

@Override
public String toString() {
	return "UserRole [user_role_id=" + user_role_id + ", role_id=" + role_id + ", user_id=" + user_id
			+ ", is_primary_role=" + is_primary_role + ", user=" + user + ", role=" + role + "]";
}  
  
}