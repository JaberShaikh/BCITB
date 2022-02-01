package com.tissuebank.service.impl;

import java.util.List;

import org.apache.commons.lang3.builder.DiffResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.PatientDao;
import com.tissuebank.model.Patient;
import com.tissuebank.service.PatientService;

@Service("patientService")
@Transactional
public class PatientServiceImpl implements PatientService
{
	@Autowired
	private PatientDao patientDao;

	@Override
	public List<Patient> getPatientDetails(String which_department, String columnToSearch[], String textToSearch[]) 
	{
		return patientDao.getPatientDetails(which_department, columnToSearch, textToSearch);
	}

	@Override
	public Patient insertNewPatientRecord(String which_department, String dept_prefix, Patient patient) {
		return patientDao.insertNewPatientRecord(which_department, dept_prefix, patient);
	}

	@Override
	public Patient getPatientFromID(String which_department, int patient_id) {
		return patientDao.getPatientFromID(which_department, patient_id);
	}

	@Override
	public void updatePatientUsingDiff(String which_department, int patient_id, DiffResult diff_result, String encryptSecretKey) {
		patientDao.updatePatientUsingDiff(which_department, patient_id, diff_result, encryptSecretKey);
	}

	@Override
	public void savePatientVariousColumns(String which_department, int patient_id, String dbColumnName, String dbColumnValue) {
		patientDao.savePatientVariousColumns(which_department, patient_id, dbColumnName, dbColumnValue);
	}

	@Override
	public String generateSecondaryID(Patient patient, String date_of_consent) {
		return patientDao.generateSecondaryID(patient, date_of_consent);
	}

	@Override
	public List<Object[]> getPatientObjectArrayUsingSQL(String which_department, String sql_script) {
		return patientDao.getPatientObjectArrayUsingSQL(which_department, sql_script);
	}

	@Override
	public List<Object> getPatientObjectUsingSQL(String which_department, String sql_script) {
		return patientDao.getPatientObjectUsingSQL(which_department, sql_script);
	}

	@Override
	public List<Patient> getLockedPatient(String whatToProcess, String which_department, int locked_by) {
		return patientDao.getLockedPatient(whatToProcess, which_department, locked_by);
	}

	@Override
	public String generateDatabaseID(String dept_acronym, String dept_prefix) {
		return patientDao.generateDatabaseID(dept_acronym, dept_prefix);
	}

	@Override
	public List<Patient> searchPatientDetails(String which_department, String search_function_to_use, String columnToSearch, String textToSearch) {
		return patientDao.searchPatientDetails(which_department, search_function_to_use, columnToSearch, textToSearch);
	}

}
