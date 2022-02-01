package com.tissuebank.model.global.views;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import javax.persistence.CascadeType;

@Entity
@Table(name = "TB_USERS_DEPARTMENTS_V")
public class UserDepartment 
{
  @Id
  @Column(name = "USER_DEPT_ID")
  private int user_dept_id;
	
  @Column(name = "DEPT_ID")
  private int dept_id;

  @Column(name = "USER_ID")
  private int user_id;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", insertable = false, updatable = false)  
  private User user;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "DEPT_ID", insertable = false, updatable = false)  
  private Department department;

public int getUser_dept_id() {
	return user_dept_id;
}

public void setUser_dept_id(int user_dept_id) {
	this.user_dept_id = user_dept_id;
}

public int getDept_id() {
	return dept_id;
}

public void setDept_id(int dept_id) {
	this.dept_id = dept_id;
}

public int getUser_id() {
	return user_id;
}

public void setUser_id(int user_id) {
	this.user_id = user_id;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Department getDepartment() {
	return department;
}

public void setDepartment(Department department) {
	this.department = department;
}

}