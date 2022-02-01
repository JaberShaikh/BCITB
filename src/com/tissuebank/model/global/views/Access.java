package com.tissuebank.model.global.views;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "TB_ACCESS_V")
public class Access 
{
  @Id
  @Column(name = "ACCESS_ID")
  private int access_id;
	
  @Column(name = "WHAT_TO_ACCESS")
  private String what_to_access;

  @Column(name = "TYPE_OF_ACCESS")
  private String type_of_access;

public int getAccess_id() {
	return access_id;
}

public void setAccess_id(int access_id) {
	this.access_id = access_id;
}

public String getWhat_to_access() {
	return what_to_access;
}

public void setWhat_to_access(String what_to_access) {
	this.what_to_access = what_to_access;
}

public String getType_of_access() {
	return type_of_access;
}

public void setType_of_access(String type_of_access) {
	this.type_of_access = type_of_access;
}

}