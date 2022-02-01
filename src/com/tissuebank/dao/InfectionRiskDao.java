package com.tissuebank.dao;

import java.util.List;

import org.apache.commons.lang3.builder.DiffResult;

import com.tissuebank.model.InfectionRisk;
import com.tissuebank.model.InfectionType;
import com.tissuebank.model.SampleType;

public interface InfectionRiskDao
{
	List<InfectionRisk> getPatientInfectionRisks(String whatToProcess, String whichDepartment, int patient_id); 
	List<InfectionRisk> getInfectionRisksUsingSQL(String whichDepartment, String sql_script); 
	int generateInfectionRiskId(String whichDepartment,InfectionRisk infection_risk);
	InfectionRisk insertNewInfectionRisk(String whichDepartment, InfectionRisk infection_risk);
    void updateInfectionRiskUsingObject(String whichDepartment, InfectionRisk ir);
    void updateInfectionRiskUsingDiff(String whichDepartment, int infection_risk_id, DiffResult diff_result);
	InfectionRisk getInfectionRisk(String whichDepartment, int infection_risk_id); 
	List<InfectionType> getAllInfectionTypes(String whichDepartment); 
	List<SampleType> getAllSampleTypes(String whichDepartment); 
	InfectionType getInfectionType(String whichDepartment, int infection_type_id); 
	InfectionType getInfectionType(String whichDepartment, String short_description); 
	SampleType getSampleType(String whichDepartment, String description); 
	SampleType getSampleType(String whichDepartment, int sample_type_id); 
	InfectionRisk populateInfectionRiskVariousBits(InfectionRisk infection_risk, String whichDepartment, String whatToProcess);
    void saveInfectionRiskVariousColumns(String whichDepartment, int infection_risk_id, String dbColumnName, String dbColumnValue);
    void deleteInfectionRisk(String whichDepartment, int infection_risk_id);
}
