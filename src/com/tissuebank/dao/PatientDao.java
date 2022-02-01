package com.tissuebank.dao;

import java.util.List;
import org.apache.commons.lang3.builder.DiffResult;
import com.tissuebank.model.Patient;

public interface PatientDao
{
	String generateDatabaseID(String dept_acronym,String dept_prefix);
	String generateSecondaryID(Patient patient, String date_of_consent);
	Patient insertNewPatientRecord(String which_department, String dept_prefix, Patient patient);
    void updatePatientUsingDiff(String which_department, int patient_id, DiffResult diff_result, String encryptSecretKey);
	List<Patient> getPatientDetails(String which_department, String columnToSearch[], String textToSearch[]);
	List<Patient> searchPatientDetails(String which_department, String search_function_to_use, String columnToSearch, String textToSearch);
	List<Patient> getLockedPatient(String whatToProcess, String which_department, int locked_by);
	List<Object[]> getPatientObjectArrayUsingSQL(String which_department, String sql_script);
	List<Object> getPatientObjectUsingSQL(String which_department, String sql_script);
	Patient getPatientFromID(String which_department, int patient_id);
    void savePatientVariousColumns(String which_department, int patient_id, String dbColumnName, String dbColumnValue);
}
