package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConsentFile implements Serializable, Diffable<ConsentFile> {

  @Id
  @Column(name = "FILE_ID")
  private int file_id;

  @Column(name = "FILE_NAME")
  private String file_name;

  @Column(name = "CONTENT_TYPE")
  private String content_type;
  
  @Column(name = "FILE_DATA")
  private byte[] file_data;

  @Column(name = "DESCRIPTION")
  private String description;

public ConsentFile() {
	super();
}

public ConsentFile(String file_name, String content_type, byte[] file_data, String description) {
	this.file_name = file_name;
	this.content_type = content_type;
	this.file_data = file_data;
	this.description = description;
}

public byte[] getFile_data() {
	return file_data;
}

public void setFile_data(byte[] file_data) {
	this.file_data = file_data;
}

public String getContent_type() {
	return content_type;
}

public void setContent_type(String content_type) {
	this.content_type = content_type;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public int getFile_id() {
	return file_id;
}

public void setFile_id(int file_id) {
	this.file_id = file_id;
}

public String getFile_name() {
	return file_name;
}

public void setFile_name(String file_name) {
	this.file_name = file_name;
}

@Override
public String toString() {
	return "[File name = " + file_name + ", Content Type = " + content_type + ", Description = " + description + "]";
}

@Override
public DiffResult diff(ConsentFile fl) {
	DiffBuilder db = new DiffBuilder(this, fl, ToStringStyle.SHORT_PREFIX_STYLE);
    if ((this.file_name != null && !this.file_name.isEmpty()) || (fl.file_name != null && !fl.file_name.isEmpty()))
	    db.append("file_name", this.file_name, fl.file_name);
    if ((this.content_type != null && !this.content_type.isEmpty()) || (fl.content_type != null && !fl.content_type.isEmpty()))
	    db.append("content_type", this.content_type, fl.content_type);
    return db.build();
}

}