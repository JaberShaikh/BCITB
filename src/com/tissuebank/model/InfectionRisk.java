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

import javax.persistence.Column;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class InfectionRisk implements Diffable<InfectionRisk> {

  @Id
  @Column(name = "INFECTION_RISK_ID")
  private int infection_risk_id;

  @Column(name = "INFECTION_RISK_EXIST")
  private String infection_risk_exist;

  @Column(name = "OTHER_INFECTION_RISK")
  private String other_infection_risk;

  @Column(name = "INFECTION_RISK_NOTES")
  private String infection_risk_notes;
  
  @Column(name = "EPISODE_OF_INFECTION")
  private String episode_of_infection;

  @Column(name = "EPISODE_START_DATE")
  private String episode_start_date;

  @Column(name = "EPISODE_FINISHED_DATE")
  private String episode_finished_date;

  @Column(name = "CONTINUED_RISK")
  private String continued_risk;
  
  @Column(name = "IR_PATIENT_ID")
  private Integer ir_patient_id;
  
  @Column(name = "INFECTION_TYPE_ID")
  private Integer infection_type_id;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "SAMPLE_COLLECTION")
  private String sample_collection;

  @Column(name = "SAMPLE_TYPE_ID")
  private Integer sample_type_id;
  
  @Column(name = "SAMPLE_COLLECTION_DATE")
  private String sample_collection_date;

  @Column(name = "IR_DELETION_DATE")
  private String ir_deletion_date;
  
  @Transient
  private InfectionType infection_type;

  @Transient
  private SampleType sample_type;

  @Transient
  private String ir_which_department;
  
public InfectionRisk() {
	super();
}

public InfectionRisk(int infection_risk_id, String ir_which_department) {
	super();
	this.infection_risk_id = infection_risk_id;
	this.ir_which_department = ir_which_department;
}

public InfectionRisk(int infection_risk_id, String ir_which_department, int ir_patient_id) {
	super();
	this.infection_risk_id = infection_risk_id;
	this.ir_which_department = ir_which_department;
	this.ir_patient_id = ir_patient_id;
}

public InfectionRisk(int infection_risk_id, String infection_risk_exist, int ir_patient_id, String status, String infection_risk_notes) {
	super();
	this.infection_risk_id = infection_risk_id;
	this.infection_risk_exist = infection_risk_exist;
	this.ir_patient_id = ir_patient_id;
	this.status = status;
	this.infection_risk_notes = infection_risk_notes;
	this.infection_type_id = 0;
}

public String getIr_deletion_date() {
	return ir_deletion_date;
}

public void setIr_deletion_date(String ir_deletion_date) {
	this.ir_deletion_date = ir_deletion_date;
}

public SampleType getSample_type() {
	return sample_type;
}

public void setSample_type(SampleType sample_type) {
	this.sample_type = sample_type;
}

public String getSample_collection() {
	return sample_collection;
}

public String getIr_which_department() {
	return ir_which_department;
}

public void setIr_which_department(String ir_which_department) {
	this.ir_which_department = ir_which_department;
}

public void setSample_collection(String sample_collection) {
	this.sample_collection = sample_collection;
}

public Integer getSample_type_id() {
	return sample_type_id;
}

public String getSample_collection_date() {
	return sample_collection_date;
}

public void setSample_collection_date(String sample_collection_date) {
	this.sample_collection_date = sample_collection_date;
}

public String getStatus() {
	return status;
}

public void setSample_type_id(Integer sample_type_id) {
	this.sample_type_id = sample_type_id;
}

public void setStatus(String status) {
	this.status = status;
}

public InfectionType getInfection_type() {
	return infection_type;
}

public void setInfection_type(InfectionType infection_type) {
	this.infection_type = infection_type;
}

public Integer getInfection_type_id() {
	return infection_type_id;
}

public void setInfection_type_id(Integer infection_type_id) {
	this.infection_type_id = infection_type_id;
}

public Integer getIr_patient_id() {
	return ir_patient_id;
}

public void setIr_patient_id(Integer ir_patient_id) {
	this.ir_patient_id = ir_patient_id;
}

public String getContinued_risk() {
	return continued_risk;
}

public void setContinued_risk(String continued_risk) {
	this.continued_risk = continued_risk;
}

public String getOther_infection_risk() {
	return other_infection_risk;
}

public void setOther_infection_risk(String other_infection_risk) {
	this.other_infection_risk = other_infection_risk;
}

public String getInfection_risk_notes() {
	return infection_risk_notes;
}

public void setInfection_risk_notes(String infection_risk_notes) {
	this.infection_risk_notes = infection_risk_notes;
}

public int getInfection_risk_id() {
	return infection_risk_id;
}

public void setInfection_risk_id(int infection_risk_id) {
	this.infection_risk_id = infection_risk_id;
}

public String getInfection_risk_exist() {
	return infection_risk_exist;
}

public void setInfection_risk_exist(String infection_risk_exist) {
	this.infection_risk_exist = infection_risk_exist;
}

public String getEpisode_of_infection() {
	return episode_of_infection;
}

public void setEpisode_of_infection(String episode_of_infection) {
	this.episode_of_infection = episode_of_infection;
}

public String getEpisode_start_date() {
	return episode_start_date;
}

public void setEpisode_start_date(String episode_start_date) {
	this.episode_start_date = episode_start_date;
}

public String getEpisode_finished_date() {
	return episode_finished_date;
}

public void setEpisode_finished_date(String episode_finished_date) {
	this.episode_finished_date = episode_finished_date;
}

@Override
public String toString() {
	return "Infection Risk Exist = " + infection_risk_exist + ", Other Infection Risk = " + other_infection_risk 
			+ ", Notes = " + infection_risk_notes + ", Episode Of Infection = " + episode_of_infection + ", Episode Start Date = " 
			+ episode_start_date + ", Episode Finished Date = " + episode_finished_date 
			+ ", Continued Risk = " + continued_risk + ", Infection Type ID = " + infection_type_id + 
			", Sample Collection = " + sample_collection + ", Sample Type ID = " + sample_type_id + 
			", Sample Collection Date = " + sample_collection_date + 
			", Infection Type = " + infection_type + ", Sample Type = " + sample_type;
}

@Override
public DiffResult diff(InfectionRisk ir) {
	DiffBuilder db = new DiffBuilder(this, ir, ToStringStyle.SHORT_PREFIX_STYLE);
    if ((this.infection_risk_exist != null && !this.infection_risk_exist.isEmpty()) || (ir.infection_risk_exist != null && !ir.infection_risk_exist.isEmpty()))
	 	db.append("infection_risk_exist", this.infection_risk_exist, ir.infection_risk_exist);
    if ((this.other_infection_risk != null && !this.other_infection_risk.isEmpty()) || (ir.other_infection_risk != null && !ir.other_infection_risk.isEmpty()))
	 	db.append("other_infection_risk", this.other_infection_risk, ir.other_infection_risk);
    if ((this.infection_risk_notes != null && !this.infection_risk_notes.isEmpty()) || (ir.infection_risk_notes != null && !ir.infection_risk_notes.isEmpty()))
	 	db.append("infection_risk_notes", this.infection_risk_notes, ir.infection_risk_notes);
    if ((this.episode_of_infection != null && !this.episode_of_infection.isEmpty()) || (ir.episode_of_infection != null && !ir.episode_of_infection.isEmpty()))
	 	db.append("episode_of_infection", this.episode_of_infection, ir.episode_of_infection);
    if ((this.episode_start_date != null && !this.episode_start_date.isEmpty()) || (ir.episode_start_date != null && !ir.episode_start_date.isEmpty()))
	 	db.append("episode_start_date", this.episode_start_date, ir.episode_start_date);
    if ((this.episode_finished_date != null && !this.episode_finished_date.isEmpty()) || (ir.episode_finished_date != null && !ir.episode_finished_date.isEmpty()))
	 	db.append("episode_finished_date", this.episode_finished_date, ir.episode_finished_date);
    if ((this.continued_risk != null && !this.continued_risk.isEmpty()) || (ir.continued_risk != null && !ir.continued_risk.isEmpty()))
	 	db.append("continued_risk", this.continued_risk, ir.continued_risk);
    if ((this.sample_collection != null && !this.sample_collection.isEmpty()) || (ir.sample_collection != null && !ir.sample_collection.isEmpty()))
	 	db.append("sample_collection", this.sample_collection, ir.sample_collection);

    if(this.sample_type_id == null) this.sample_type_id = 0;
    if(ir.sample_type_id == null) ir.sample_type_id = 0;
    if(this.sample_type_id.intValue() != ir.sample_type_id.intValue()) {
    	db.append("sample_type_id", this.sample_type_id, ir.sample_type_id);
    	db.append("sample_type", this.sample_type, ir.sample_type);
    }
    
    if ((this.sample_collection_date != null && !this.sample_collection_date.isEmpty()) || (ir.sample_collection_date != null && !ir.sample_collection_date.isEmpty()))
	 	db.append("sample_collection_date", this.sample_collection_date, ir.sample_collection_date);

    if(this.infection_type_id == null) this.infection_type_id = 0;
    if(ir.infection_type_id == null) ir.infection_type_id = 0;
    if(this.infection_type_id.intValue() != ir.infection_type_id.intValue()) {
	 	db.append("infection_type_id", this.infection_type_id, ir.infection_type_id);
	 	db.append("infection_type", this.infection_type, ir.infection_type);
    }
 	return db.build();
}

}