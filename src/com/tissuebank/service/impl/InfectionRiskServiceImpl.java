package com.tissuebank.service.impl;

import java.util.List;

import org.apache.commons.lang3.builder.DiffResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.InfectionRiskDao;
import com.tissuebank.model.InfectionRisk;
import com.tissuebank.model.InfectionType;
import com.tissuebank.model.SampleType;
import com.tissuebank.service.InfectionRiskService;

@Service("infectionRiskService")
@Transactional
public class InfectionRiskServiceImpl implements InfectionRiskService
{
	@Autowired
	private InfectionRiskDao infectionRiskDao;

	@Override
	public InfectionRisk insertNewInfectionRisk(String dept_acronym, InfectionRisk infection_risk) {
		return infectionRiskDao.insertNewInfectionRisk(dept_acronym, infection_risk);
	}

	@Override
	public List<InfectionRisk> getPatientInfectionRisks(String whatToProcess, String dept_acronym, int patient_id) {
		return infectionRiskDao.getPatientInfectionRisks(whatToProcess, dept_acronym, patient_id);
	}

	@Override
	public InfectionRisk getInfectionRisk(String dept_acronym, int infection_risk_id) {
		return infectionRiskDao.getInfectionRisk(dept_acronym, infection_risk_id);
	}

	@Override
	public List<InfectionType> getAllInfectionTypes(String dept_acronym) {
		return infectionRiskDao.getAllInfectionTypes(dept_acronym);
	}

	@Override
	public InfectionType getInfectionType(String dept_acronym, int infection_type_id) {
		return infectionRiskDao.getInfectionType(dept_acronym, infection_type_id);
	}

	@Override
	public List<SampleType> getAllSampleTypes(String dept_acronym) {
		return infectionRiskDao.getAllSampleTypes(dept_acronym);
	}

	@Override
	public InfectionRisk populateInfectionRiskVariousBits(InfectionRisk infection_risk, String dept_acronym, String whatToProcess) {
		return infectionRiskDao.populateInfectionRiskVariousBits(infection_risk, dept_acronym, whatToProcess);
	}

	@Override
	public void updateInfectionRiskUsingDiff(String dept_acronym, int infection_risk_id, DiffResult diff_result) {
		infectionRiskDao.updateInfectionRiskUsingDiff(dept_acronym, infection_risk_id, diff_result);
	}

	@Override
	public void updateInfectionRiskUsingObject(String dept_acronym, InfectionRisk ir) {
		infectionRiskDao.updateInfectionRiskUsingObject(dept_acronym, ir);
	}

	@Override
	public int generateInfectionRiskId(String whichDepartment, InfectionRisk infection_risk) {
		return infectionRiskDao.generateInfectionRiskId(whichDepartment, infection_risk);
	}

	@Override
	public InfectionType getInfectionType(String whichDepartment, String short_description) {
		return infectionRiskDao.getInfectionType(whichDepartment, short_description);
	}

	@Override
	public SampleType getSampleType(String whichDepartment, String description) {
		return infectionRiskDao.getSampleType(whichDepartment, description);
	}

	@Override
	public void saveInfectionRiskVariousColumns(String whichDepartment, int infection_risk_id, String dbColumnName, String dbColumnValue) {
		infectionRiskDao.saveInfectionRiskVariousColumns(whichDepartment, infection_risk_id, dbColumnName, dbColumnValue);
	}

	@Override
	public void deleteInfectionRisk(String whichDepartment, int infection_risk_id) {
		infectionRiskDao.deleteInfectionRisk(whichDepartment, infection_risk_id);
	}

	@Override
	public SampleType getSampleType(String whichDepartment, int sample_type_id) {
		return infectionRiskDao.getSampleType(whichDepartment, sample_type_id);
	}

	@Override
	public List<InfectionRisk> getInfectionRisksUsingSQL(String whichDepartment, String sql_script) {
		return infectionRiskDao.getInfectionRisksUsingSQL(whichDepartment, sql_script);
	}

}
