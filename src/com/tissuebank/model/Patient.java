package com.tissuebank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.ColumnTransformer;

import java.util.ArrayList;

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Patient implements Diffable<Patient> {

  @Id
  @Column(name = "PATIENT_ID")
  private int patient_id;

  @Column(name = "PATIENT_FIRSTNAME")
  @ColumnTransformer(read = "encrypt_decrypt.decrypt(patient_firstname,database_id)")
  private String patient_firstname;

  @Column(name = "PATIENT_SURNAME")
  @ColumnTransformer(read = "encrypt_decrypt.decrypt(patient_surname,database_id)")
  private String patient_surname;

  @Column(name = "DATABASE_ID")
  private String database_id;
  
  @Column(name = "SECONDARY_ID")
  private String secondary_id;
  
  @Column(name = "VOLUNTEER")
  private String volunteer;

  @Column(name = "HOSPITAL_NUMBER")
  private String hospital_number;

  @Column(name = "NHS_NUMBER")
  @ColumnTransformer(read = "encrypt_decrypt.decrypt(nhs_number,database_id)")
  private String nhs_number;

  @Column(name = "DATE_OF_BIRTH")
  @ColumnTransformer(read = "encrypt_decrypt.decrypt(date_of_birth,database_id)")
  private String date_of_birth;

  @Column(name = "AGE")
  private Integer age;

  @Column(name = "GENDER")
  private String gender;

  @Column(name = "OLD_PAT_ID")
  private String old_pat_id;
  
  @Column(name = "STATUS")
  private String status;

  @Column(name = "LOCKED_DESCRIPTION")
  private String locked_description;

  @Column(name = "LOCKED_BY")
  private Integer locked_by;
  
  @Column(name = "WITHDRAWN_COUNT")
  private Integer withdrawn_count;
  
  @Transient
  private Integer number_consents;

  @Transient
  private Integer number_validated_consents;

  @Transient
  private Integer number_finalise_import_consents;
  
  @Transient
  private Integer number_infection_risks;

  @Transient
  private String patient_which_department;

public Integer getWithdrawn_count() {
	return withdrawn_count;
}

public void setWithdrawn_count(Integer withdrawn_count) {
	this.withdrawn_count = withdrawn_count;
}

public Integer getLocked_by() {
	return locked_by;
}

public void setLocked_by(Integer locked_by) {
	this.locked_by = locked_by;
}

public Integer getNumber_finalise_import_consents() {
	return number_finalise_import_consents;
}

public void setNumber_finalise_import_consents(Integer number_finalise_import_consents) {
	this.number_finalise_import_consents = number_finalise_import_consents;
}

public String getLocked_description() {
	return locked_description;
}

public void setLocked_description(String locked_description) {
	this.locked_description = locked_description;
}

public String getPatient_which_department() {
	return patient_which_department;
}

public void setPatient_which_department(String patient_which_department) {
	this.patient_which_department = patient_which_department;
}

public int getPatient_id() {
	return patient_id;
}

public void setPatient_id(int patient_id) {
	this.patient_id = patient_id;
}

public String getPatient_firstname() {
	return patient_firstname;
}

public void setPatient_firstname(String patient_firstname) {
	this.patient_firstname = patient_firstname;
}

public String getPatient_surname() {
	return patient_surname;
}

public void setPatient_surname(String patient_surname) {
	this.patient_surname = patient_surname;
}

public String getDatabase_id() {
	return database_id;
}

public void setDatabase_id(String database_id) {
	this.database_id = database_id;
}

public String getSecondary_id() {
	return secondary_id;
}

public void setSecondary_id(String secondary_id) {
	this.secondary_id = secondary_id;
}

public String getVolunteer() {
	return volunteer;
}

public void setVolunteer(String volunteer) {
	this.volunteer = volunteer;
}

public String getHospital_number() {
	return hospital_number;
}

public void setHospital_number(String hospital_number) {
	this.hospital_number = hospital_number;
}

public String getNhs_number() {
	return nhs_number;
}

public void setNhs_number(String nhs_number) {
	this.nhs_number = nhs_number;
}

public String getDate_of_birth() {
	return date_of_birth;
}

public void setDate_of_birth(String date_of_birth) {
	this.date_of_birth = date_of_birth;
}

public Integer getAge() {
	return age;
}

public void setAge(Integer age) {
	this.age = age;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public String getOld_pat_id() {
	return old_pat_id;
}

public void setOld_pat_id(String old_pat_id) {
	this.old_pat_id = old_pat_id;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public Integer getNumber_consents() {
	return number_consents;
}

public void setNumber_consents(Integer number_consents) {
	this.number_consents = number_consents;
}

public Integer getNumber_validated_consents() {
	return number_validated_consents;
}

public void setNumber_validated_consents(Integer number_validated_consents) {
	this.number_validated_consents = number_validated_consents;
}

public Integer getNumber_infection_risks() {
	return number_infection_risks;
}

public void setNumber_infection_risks(Integer number_infection_risks) {
	this.number_infection_risks = number_infection_risks;
}

public ArrayList<String> patientIdentifiableColumnNames(String dept_acronym) {
	
	ArrayList<String> identifiableColNames = new ArrayList<String>();
	
	switch (dept_acronym.toUpperCase()) {
	case "BGTB": case "HOTB": case "CTB":
		identifiableColNames.add("patient_surname");
		identifiableColNames.add("date_of_birth");
		identifiableColNames.add("nhs_number");
		identifiableColNames.add("patient_firstname");
		break;
	}
	
	return identifiableColNames;
}

@Override
public String toString() {
	return "Patient firstname=" + patient_firstname + ", Surname=" + patient_surname + ", Database ID=" + database_id 
			+ ", Secondary ID=" + secondary_id + ", Volunteer=" + volunteer + ", Hospital Number=" + hospital_number 
			+ ", NHS Number=" + nhs_number + ", Date Of Birth=" + date_of_birth + ", Age=" + age 
			+ ", Gender=" + gender + ", Old Pat ID=" + old_pat_id; 
}

@Override
public DiffResult diff(Patient pat) {
	DiffBuilder db = new DiffBuilder(this, pat, ToStringStyle.SHORT_PREFIX_STYLE);
    if ((this.patient_firstname != null && !this.patient_firstname.isEmpty()) || (pat.patient_firstname != null && !pat.patient_firstname.isEmpty()))
    	db.append("patient_firstname", this.patient_firstname, pat.patient_firstname);
    if ((this.patient_surname != null && !this.patient_surname.isEmpty()) || (pat.patient_surname != null && !pat.patient_surname.isEmpty()))
    	db.append("patient_surname", this.patient_surname, pat.patient_surname);
    if ((this.volunteer != null && !this.volunteer.isEmpty()) || (pat.volunteer != null && !pat.volunteer.isEmpty()))
	    db.append("volunteer", this.volunteer, pat.volunteer);
    if ((this.hospital_number != null && !this.hospital_number.isEmpty()) || (pat.hospital_number != null && !pat.hospital_number.isEmpty()))
	    db.append("hospital_number", this.hospital_number, pat.hospital_number);
    if ((this.nhs_number != null && !this.nhs_number.isEmpty()) || (pat.nhs_number != null && !pat.nhs_number.isEmpty()))
	    db.append("nhs_number", this.nhs_number, pat.nhs_number);
    if ((this.date_of_birth != null && !this.date_of_birth.isEmpty()) || (pat.date_of_birth != null && !pat.date_of_birth.isEmpty()))
	    db.append("date_of_birth", this.date_of_birth, pat.date_of_birth);
    if ((this.gender != null && !this.gender.isEmpty()) || (pat.gender != null && !pat.gender.isEmpty()))
	    db.append("gender", this.gender, pat.gender);
    if ((this.old_pat_id != null && !this.old_pat_id.isEmpty()) || (pat.old_pat_id != null && !pat.old_pat_id.isEmpty()))
    	db.append("old_pat_id", this.old_pat_id, pat.old_pat_id);
    db.append("age", this.age, pat.age);
    return db.build();
}

}