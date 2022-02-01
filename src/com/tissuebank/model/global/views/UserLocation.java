package com.tissuebank.model.global.views;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

@Entity
@Table(name = "TB_USERS_LOCATIONS_V")
public class UserLocation 
{
  @Id
  @Column(name = "USER_LOC_ID")
  private int user_loc_id;
	
  @Column(name = "LOC_ID")
  private int loc_id;

  @Column(name = "USER_ID")
  private int user_id;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", insertable = false, updatable = false)  
  private User user;

  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "LOC_ID", insertable = false, updatable = false)  
  private Location location;
  
  @Transient
  public String selected_locations;
  
public String getSelected_locations() {
	return selected_locations;
}

public void setSelected_locations(String selected_locations) {
	this.selected_locations = selected_locations;
}

public int getUser_loc_id() {
	return user_loc_id;
}

public void setUser_loc_id(int user_loc_id) {
	this.user_loc_id = user_loc_id;
}

public int getLoc_id() {
	return loc_id;
}

public void setLoc_id(int loc_id) {
	this.loc_id = loc_id;
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

public Location getLocation() {
	return location;
}

public void setLocation(Location location) {
	this.location = location;
}
 
}