package com.tissuebank.model.global.views;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_DEPARTMENT_V")
public class Department {
  
  @Id
  @Column(name="DEPT_ID")
  private int dept_id;

  @Column(name="DEPT_ACRONYM")
  private String dept_acronym;

  @Column(name="DEPT_NAME")
  private String dept_name;

  @Column(name="DEPT_DESCRIPTION")
  private String dept_description;

  @Column(name="DEPT_PREFIX")
  private String dept_prefix;
  
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "department")
  private Set<UserDepartment> userDepartments = new HashSet<UserDepartment>();

public String getDept_prefix() {
	return dept_prefix;
}

public void setDept_prefix(String dept_prefix) {
	this.dept_prefix = dept_prefix;
}

public String getDept_description() {
	return dept_description;
}

public void setDept_description(String dept_description) {
	this.dept_description = dept_description;
}

public int getDept_id() {
	return dept_id;
}

public void setDept_id(int dept_id) {
	this.dept_id = dept_id;
}

public String getDept_acronym() {
	return dept_acronym;
}

public void setDept_acronym(String dept_acronym) {
	this.dept_acronym = dept_acronym;
}

public String getDept_name() {
	return dept_name;
}

public void setDept_name(String dept_name) {
	this.dept_name = dept_name;
}

public Set<UserDepartment> getUserDepartments() {
	return userDepartments;
}

public void setUserDepartments(Set<UserDepartment> userDepartments) {
	this.userDepartments = userDepartments;
}

@Override
public String toString() {
	return "Department [dept_id=" + dept_id + ", dept_acronym=" + dept_acronym + ", dept_name=" + dept_name
			+ ", dept_description=" + dept_description + ", dept_prefix=" + dept_prefix + ", userDepartments="
			+ userDepartments + "]";
}

}
