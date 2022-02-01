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
@Table(name = "TB_DEPARTMENTS_LOCATIONS_V")
public class DepartmentLocation 
{
  @Id
  @Column(name = "DEPT_LOC_ID")
  private int dept_loc_id;
	
  @Column(name = "LOC_ID")
  private int loc_id;

  @Column(name = "DEPT_ID")
  private int dept_id;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "DEPT_ID", insertable = false, updatable = false)  
  private Department department;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "LOC_ID", insertable = false, updatable = false)  
  private Location location;

public int getDept_loc_id() {
	return dept_loc_id;
}

public void setDept_loc_id(int dept_loc_id) {
	this.dept_loc_id = dept_loc_id;
}

public int getLoc_id() {
	return loc_id;
}

public void setLoc_id(int loc_id) {
	this.loc_id = loc_id;
}

public int getDept_id() {
	return dept_id;
}

public void setDept_id(int dept_id) {
	this.dept_id = dept_id;
}

public Department getDepartment() {
	return department;
}

public void setDepartment(Department department) {
	this.department = department;
}

public Location getLocation() {
	return location;
}

public void setLocation(Location location) {
	this.location = location;
}

}