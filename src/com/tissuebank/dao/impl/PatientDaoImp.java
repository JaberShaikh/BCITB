package com.tissuebank.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffResult;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.PatientDao;
import com.tissuebank.model.Patient;
import com.tissuebank.util.BCITBVariousVariables;

@Transactional
@Repository("patientDao")
public class PatientDaoImp implements PatientDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	Query this_query;
	String this_sql_script = "";
	
	@Override
	public String generateDatabaseID(String dept_acronym,String dept_prefix)
	{
		boolean pat_id_exist = true;
		String this_pat_id = "";
		while (pat_id_exist == true)
		{
			this_pat_id = dept_prefix.toUpperCase() + "-" + RandomStringUtils.random(6,true, true);
			if (sessionFactory.getCurrentSession().createQuery("FROM " + dept_acronym + "_Patient_V WHERE upper(pat_id) = '" + this_pat_id.toUpperCase() + "'") != null)
				pat_id_exist = false;
		}
		return this_pat_id;
	}

	@Override
	public Patient insertNewPatientRecord(String dept_acronym, String dept_prefix, Patient patient) 
	{
		patient.setPatient_id((int) (long) ((sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(patient_id) FROM " + dept_acronym + "_Patient_V").uniqueResult() != null) ?
				((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(patient_id) FROM " + dept_acronym + "_Patient_V").uniqueResult()).longValue() + 1 : 1));
		patient.setDatabase_id(generateDatabaseID(dept_acronym,dept_prefix));
		if(patient.getVolunteer().equalsIgnoreCase(BCITBVariousVariables.yes))
			sessionFactory.getCurrentSession().createSQLQuery(
				"INSERT into " + dept_acronym + "_Patient(patient_id,database_id,secondary_id,patient_firstname,patient_surname,volunteer,old_pat_id,age,gender,status) " + 
				"VALUES (:patient_id_val,:database_id_val,:secondary_id_val,:patient_firstname_val,"
				+ ":patient_surname_val,:volunteer_val,:old_pat_id_val,:age_val,:gender_val,:status_val)")
				.setParameter("patient_id_val", patient.getPatient_id()).setParameter("database_id_val",patient.getDatabase_id()).setParameter("secondary_id_val", patient.getSecondary_id())
				.setParameter("patient_firstname_val", WordUtils.capitalize(patient.getPatient_firstname()))
				.setParameter("patient_surname_val", patient.getPatient_surname().toUpperCase())
				.setParameter("volunteer_val", patient.getVolunteer()).setParameter("old_pat_id_val", patient.getOld_pat_id()).setParameter("age_val", patient.getAge() == null? 0:patient.getAge())
				.setParameter("gender_val", patient.getGender()).setParameter("status_val", patient.getStatus()).executeUpdate();
		else
			sessionFactory.getCurrentSession().createSQLQuery(
				"INSERT into " + dept_acronym + "_Patient(patient_id,database_id,secondary_id,patient_firstname,patient_surname,date_of_birth,nhs_number,hospital_number,volunteer,old_pat_id,gender,status) "
				+ "VALUES (:patient_id_val,:database_id_val,:secondary_id_val,:patient_firstname_val,:patient_surname_val,:date_of_birth_val,"
				+ ":nhs_number_val,:hospital_number_val,:volunteer_val,:old_pat_id_val,:gender_val,:status_val)")
				.setParameter("patient_id_val", patient.getPatient_id()).setParameter("database_id_val",patient.getDatabase_id()).setParameter("secondary_id_val", patient.getSecondary_id())
				.setParameter("patient_firstname_val", WordUtils.capitalize(patient.getPatient_firstname()))
				.setParameter("patient_surname_val", patient.getPatient_surname().toUpperCase())
				.setParameter("date_of_birth_val", patient.getDate_of_birth())
				.setParameter("nhs_number_val", patient.getNhs_number())
				.setParameter("hospital_number_val", patient.getHospital_number()).setParameter("volunteer_val", patient.getVolunteer()).setParameter("old_pat_id_val", patient.getOld_pat_id())
				.setParameter("gender_val", patient.getGender()).setParameter("status_val", patient.getStatus()).executeUpdate();
		
		sessionFactory.getCurrentSession().createSQLQuery("UPDATE " + dept_acronym + "_Patient SET patient_firstname=encrypt_decrypt.encrypt(patient_firstname,database_id)," + 
				"patient_surname=encrypt_decrypt.encrypt(patient_surname,database_id),nhs_number=encrypt_decrypt.encrypt(nhs_number,database_id)," + 
				"date_of_birth=encrypt_decrypt.encrypt(date_of_birth,database_id) WHERE patient_id=" + patient.getPatient_id()).executeUpdate();
		
		return patient;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getPatientDetails(String dept_acronym, String columnToSearch[], String textToSearch[]) 
	{
		this_sql_script = "FROM " + dept_acronym + "_Patient_V WHERE upper(" + columnToSearch[0] + ")=:" + columnToSearch[0] + "_val";
		if (columnToSearch.length == 2) {
			this_sql_script = this_sql_script + " AND upper(" + columnToSearch[1] + ")=:" + columnToSearch[1] + "_val";
			return sessionFactory.getCurrentSession().createQuery(this_sql_script)
					.setParameter(columnToSearch[0] + "_val", textToSearch[0].toUpperCase())
					.setParameter(columnToSearch[1] + "_val", textToSearch[1].toUpperCase()).list();
		} else 
			return sessionFactory.getCurrentSession().createQuery(this_sql_script)
					.setParameter(columnToSearch[0] + "_val", textToSearch[0].toUpperCase()).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> searchPatientDetails(String dept_acronym, String search_function_to_use, String columnToSearch, String textToSearch) {
		
		if(!columnToSearch.trim().isEmpty() && !textToSearch.trim().isEmpty()) 
			switch (search_function_to_use) {
			case "LIKE-STRING":
				if(columnToSearch.equalsIgnoreCase(BCITBVariousVariables.old_pat_id))
					this_sql_script = "FROM " + dept_acronym + "_Patient_V WHERE upper(" + columnToSearch + ") LIKE '%" + textToSearch.toUpperCase().replace("'", "''") + "%'";
				else
					this_sql_script = "FROM " + dept_acronym + "_Patient_V WHERE upper(" + columnToSearch + ") LIKE '" + textToSearch.toUpperCase().replace("'", "''") + "%'";
				break;
			case "EQUAL-STRING":
				this_sql_script = "FROM " + dept_acronym + "_Patient_V WHERE upper(" + columnToSearch + ")='" + textToSearch.toUpperCase().replace("'", "''") + "'";
				break;
			}
		else
			this_sql_script = "FROM " + dept_acronym + "_Patient_V";
		
		return sessionFactory.getCurrentSession().createQuery(this_sql_script + " ORDER BY patient_id ASC").list();
	}

	@Override
	public Patient getPatientFromID(String dept_acronym, int patient_id) {
		return (Patient) sessionFactory.getCurrentSession().createQuery("FROM " + dept_acronym + "_Patient_V WHERE patient_id=" + patient_id).uniqueResult();
	}

	@Override
	public void updatePatientUsingDiff(String dept_acronym, int patient_id, DiffResult diff_result, String encryptSecretKey)
	{
		Patient patient = new Patient();
		this_sql_script = "";
		for(Diff<?> d: diff_result.getDiffs()) {
			for(String col_nm:sessionFactory.getClassMetadata(Patient.class).getPropertyNames()) {
				if(col_nm.equalsIgnoreCase(d.getFieldName())) {
					if(!this_sql_script.trim().isEmpty())
						this_sql_script = this_sql_script + ",";
					if(patient.patientIdentifiableColumnNames(dept_acronym).contains(d.getFieldName()))
						this_sql_script = this_sql_script + d.getFieldName() + "=encrypt_decrypt.encrypt('" + d.getRight() + "','" + encryptSecretKey + "')";
					else if (sessionFactory.getClassMetadata(Patient.class).getPropertyType(d.getFieldName()).toString().toLowerCase().contains("string"))
						this_sql_script = this_sql_script + d.getFieldName() + "='" + d.getRight() + "'";
					else
						this_sql_script = this_sql_script + d.getFieldName() + "=" + d.getRight();
				}
			}
		}
		sessionFactory.getCurrentSession().createSQLQuery(
				"UPDATE " + dept_acronym + "_Patient SET " + this_sql_script + " WHERE patient_id=" + patient_id).executeUpdate();
	}
	
	@Override
	public void savePatientVariousColumns(String dept_acronym, int patient_id, String dbColumnName, String dbColumnValue) {
		for (String col_name: sessionFactory.getClassMetadata(Patient.class).getPropertyNames())
			if (col_name.equalsIgnoreCase(dbColumnName))
				sessionFactory.getCurrentSession().createSQLQuery(
						"UPDATE " + dept_acronym + "_Patient SET " + dbColumnName + "=:" + dbColumnName + "_val WHERE patient_id=:patient_id_val")
				.setParameter(dbColumnName + "_val", dbColumnValue)
				.setParameter("patient_id_val", patient_id)
				.executeUpdate();
	}

	@Override
	public String generateSecondaryID(Patient patient, String date_of_consent) {

		String secondary_id = "";
		
		if (patient.getPatient_firstname() != null && !patient.getPatient_firstname().trim().isEmpty())
			secondary_id = patient.getPatient_firstname().substring(0, 1);
		if (patient.getPatient_surname() != null && !patient.getPatient_surname().trim().isEmpty())
			secondary_id = secondary_id + patient.getPatient_surname().substring(0, 1);
		if (date_of_consent != null && !date_of_consent.trim().isEmpty())
			try {
				secondary_id = secondary_id + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date_of_consent)).replaceAll("-", "");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		if(patient.getNhs_number() != null && patient.getNhs_number().replace("-", "").length() >= 4)
			secondary_id = secondary_id + patient.getNhs_number().replace("-", "").substring(patient.getNhs_number().replace("-", "").length() - 4);
		else if (patient.getHospital_number() != null && patient.getHospital_number().length() >= 4)
			secondary_id = secondary_id + patient.getHospital_number().substring(patient.getHospital_number().length() - 4);
		else if (patient.getAge() != null && patient.getAge() > 0)
			secondary_id = secondary_id + "00" + Integer.toString(patient.getAge());
		
		return secondary_id;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getLockedPatient(String whatToProcess, String which_department, int locked_by) {
		switch (whatToProcess) {
		case BCITBVariousVariables.locked_description:
			return sessionFactory.getCurrentSession().createQuery("FROM " + which_department + "_Patient_V WHERE locked_description IS NOT NULL").list();
		default:
			return sessionFactory.getCurrentSession().createQuery("FROM " + which_department + "_Patient_V WHERE locked_by=" + locked_by).list();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Override
	public List<Object[]> getPatientObjectArrayUsingSQL(String which_department, String sql_script) {
		List result = sessionFactory.getCurrentSession().createSQLQuery(sql_script).list();
		if (result.isEmpty() || result.get(0) instanceof Object[]) {
		    return (List<Object[]>) result;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getPatientObjectUsingSQL(String which_department, String sql_script) {
		return (List<Object>) sessionFactory.getCurrentSession().createSQLQuery(sql_script).list();
	}

}