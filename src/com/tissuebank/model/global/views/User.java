package com.tissuebank.model.global.views;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tissuebank.model.global.tables.AuditTeam;
import com.tissuebank.model.global.views.UserRole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

@Entity
@Table(name = "TB_USERS_V")
public class User {

  @Id
  @Column(name = "USER_ID")
  private int user_id;
	  
  @Column(name = "USERNAME")
  private String username;

  @Column(name = "USER_FIRSTNAME")
  private String user_firstname;

  @Column(name = "USER_SURNAME")
  private String user_surname;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "EMAIL")
  private String email;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private List<UserRole> userRoles = new ArrayList<UserRole>();
  
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private List<UserDepartment> userDepartments = new ArrayList<UserDepartment>();

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private Set<UserLocation> userLocations = new HashSet<UserLocation>();

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private Set<AuditTeam> userAuditTeams = new HashSet<AuditTeam>();
  
public Set<AuditTeam> getUserAuditTeams() {
	return userAuditTeams;
}

public void setUserAuditTeams(Set<AuditTeam> userAuditTeams) {
	this.userAuditTeams = userAuditTeams;
}

public List<UserDepartment> getUserDepartments() {
	return userDepartments;
  }

public void setUserDepartments(List<UserDepartment> userDepartments) {
	this.userDepartments = userDepartments;
}

public Set<UserLocation> getUserLocations() {
	return userLocations;
}

public void setUserLocations(Set<UserLocation> userLocations) {
	this.userLocations = userLocations;
}

public List<UserRole> getUserRoles() {
	return userRoles;
}

public void setUserRoles(List<UserRole> userRoles) {
	this.userRoles = userRoles;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getUser_firstname() {
	return user_firstname;
}

public void setUser_firstname(String user_firstname) {
	this.user_firstname = user_firstname;
}

public String getUser_surname() {
	return user_surname;
}

public void setUser_surname(String user_surname) {
	this.user_surname = user_surname;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public int getUser_id() {
	return user_id;
}

public void setUser_id(int user_id) {
	this.user_id = user_id;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

@Override
public String toString() {
	return "User [user_id=" + user_id + ", username=" + username + ", user_firstname=" + user_firstname
			+ ", user_surname=" + user_surname + ", title=" + title + ", email=" + email + "]";
}

}