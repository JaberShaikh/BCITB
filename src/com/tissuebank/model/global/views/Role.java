package com.tissuebank.model.global.views;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tissuebank.model.global.views.UserRole;

@Entity
@Table(name = "TB_ROLES_V")
public class Role 
{
  @Id
  @Column(name = "ROLE_ID")
  private int role_id;
	
  @Column(name = "ROLE_DESCRIPTION")
  private String role_description;

  @Column(name = "ROLE_ACRONYM")
  private String role_acronym;
  
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
  private Set<UserRole> userRoles = new HashSet<UserRole>();

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
  private Set<RoleAccess> roleAccesses = new HashSet<RoleAccess>();

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
  private Set<ActionRole> actionRoles = new HashSet<ActionRole>();
  
public Set<ActionRole> getActionRoles() {
	return actionRoles;
}

public void setActionRoles(Set<ActionRole> actionRoles) {
	this.actionRoles = actionRoles;
}

public Set<RoleAccess> getRoleAccesses() {
	return roleAccesses;
}

public void setRoleAccesses(Set<RoleAccess> roleAccesses) {
	this.roleAccesses = roleAccesses;
}

public String getRole_acronym() {
	return role_acronym;
}

public void setRole_acronym(String role_acronym) {
	this.role_acronym = role_acronym;
}

public int getRole_id() {
	return role_id;
}

public void setRole_id(int role_id) {
	this.role_id = role_id;
}

public String getRole_description() {
	return role_description;
}

public void setRole_description(String role_description) {
	this.role_description = role_description;
}

public Set<UserRole> getUserRoles() {
	return userRoles;
}

public void setUserRoles(Set<UserRole> userRoles) {
	this.userRoles = userRoles;
}

public String getUserRoleAccess(String typeOfAccess) 
{
	String roleAccess = "";
	for(RoleAccess ra:this.getRoleAccesses()) {
		if (ra.getAccess().getWhat_to_access().trim().equalsIgnoreCase(typeOfAccess)) {
			if (roleAccess.trim().equalsIgnoreCase("")) { 
				roleAccess = ra.getAccess().getType_of_access();
			} else {
				roleAccess = roleAccess + "_" + ra.getAccess().getType_of_access();
			}
		}
	}
	return roleAccess;
}

@Override
public String toString() {
	return "Role [role_id=" + role_id + ", role_description=" + role_description + ", role_acronym=" + role_acronym
			+ "]";
}

}