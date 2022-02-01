package com.tissuebank.model.global.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_LOCATION_V")
public class Location {
  
  @Id
  @Column(name="LOC_ID")
  private int loc_id;

  @Column(name="LOC_NAME")
  private String loc_name;
  
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "location")
  private Set<UserLocation> userLocations = new HashSet<UserLocation>();

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "location")
  private List<DepartmentLocation> departmentLocations = new ArrayList<DepartmentLocation>();

public List<DepartmentLocation> getDepartmentLocations() {
	return departmentLocations;
}

public void setDepartmentLocations(List<DepartmentLocation> departmentLocations) {
	this.departmentLocations = departmentLocations;
}

public int getLoc_id() {
	return loc_id;
}

public void setLoc_id(int loc_id) {
	this.loc_id = loc_id;
}

public String getLoc_name() {
	return loc_name;
}

public void setLoc_name(String loc_name) {
	this.loc_name = loc_name;
}

public Set<UserLocation> getUserLocations() {
	return userLocations;
}

public void setUserLocations(Set<UserLocation> userLocations) {
	this.userLocations = userLocations;
}

@Override
public String toString() {
	return "[Location Name=" + loc_name + "]";
}

}
