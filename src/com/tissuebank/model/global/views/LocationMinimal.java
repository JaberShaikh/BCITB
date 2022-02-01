package com.tissuebank.model.global.views;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_LOCATION_V")
public class LocationMinimal {
  
  @Id
  @Column(name="LOC_ID")
  private int loc_id;

  @Column(name="LOC_NAME")
  private String loc_name;
  
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

@Override
public String toString() {
	return "[Location Name=" + loc_name + "]";
}

}
