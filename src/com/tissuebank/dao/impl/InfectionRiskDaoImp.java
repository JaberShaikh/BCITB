package com.tissuebank.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffResult;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.InfectionRiskDao;
import com.tissuebank.model.InfectionRisk;
import com.tissuebank.model.InfectionType;
import com.tissuebank.model.SampleType;
import com.tissuebank.util.BCITBVariousVariables;

@Transactional
@Repository("infectionRiskDao")
public class InfectionRiskDaoImp implements InfectionRiskDao {

	@Autowired
	private SessionFactory sessionFactory;

	String this_sql_script = ""; 
	Query this_query;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InfectionRisk> getPatientInfectionRisks(String whatToProcess, String whichDepartment, int patient_id) {
		
		this_sql_script = "FROM " + whichDepartment + "_Infection_Risk_V";
		switch (whatToProcess) {
		case BCITBVariousVariables.ir_deletion_date:
			this_sql_script = this_sql_script + " WHERE ir_deletion_date IS NOT NULL";
			break;
		default:
			if (patient_id > 0) 
				this_sql_script = this_sql_script + " WHERE ir_patient_id = " + patient_id;
			break;
		}
		
		this_sql_script = this_sql_script + " ORDER BY infection_risk_id ASC";
		List<InfectionRisk> this_irs = new ArrayList<InfectionRisk>(sessionFactory.getCurrentSession().createQuery(this_sql_script).list());
		for(InfectionRisk ir: this_irs) {
			ir = populateInfectionRiskVariousBits(ir,whichDepartment,"INFECTION-TYPE");
			ir = populateInfectionRiskVariousBits(ir,whichDepartment,"SAMPLE-TYPE");
		}
			
		return this_irs;
	}
	
	public InfectionRisk populateInfectionRiskVariousBits(InfectionRisk infection_risk, String whichDepartment, String whatToProcess) {
		
		switch (whatToProcess) {
		case "INFECTION-TYPE":
			infection_risk.setInfection_type((InfectionType) sessionFactory.getCurrentSession().createQuery(
					"FROM " + whichDepartment + "_Infection_Type_V WHERE infection_type_id = " + infection_risk.getInfection_type_id()).uniqueResult());
			break;
		case "SAMPLE-TYPE":
			infection_risk.setSample_type((SampleType) sessionFactory.getCurrentSession().createQuery(
					"FROM " + whichDepartment + "_Sample_Type_V WHERE sample_type_id = " + infection_risk.getSample_type_id()).uniqueResult());
			break;
		}
		return infection_risk;
	}
	
	@Override
	public InfectionRisk getInfectionRisk(String whichDepartment, int infection_risk_id) {
		InfectionRisk ir = (InfectionRisk) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Infection_Risk_V where infection_risk_id = " + infection_risk_id).uniqueResult();
		if(ir != null) {
			ir = populateInfectionRiskVariousBits(ir,whichDepartment,"INFECTION-TYPE");
			ir = populateInfectionRiskVariousBits(ir,whichDepartment,"SAMPLE-TYPE");
		}
		return ir;
	}
	
	@Override
	public InfectionRisk insertNewInfectionRisk(String whichDepartment, InfectionRisk infection_risk) {
		
		infection_risk.setInfection_risk_id(generateInfectionRiskId(whichDepartment, infection_risk));
		if (infection_risk.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes) || infection_risk.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown)) {
			
			sessionFactory.getCurrentSession().createSQLQuery("INSERT into " + whichDepartment 
					+ "_Infection_Risk(infection_risk_id,infection_risk_exist,infection_type_id,other_infection_risk,infection_risk_notes,episode_of_infection,episode_start_date,"
					+ "episode_finished_date,continued_risk,ir_patient_id,status,sample_collection,sample_type_id,sample_collection_date) " 
					+ "VALUES (" + infection_risk.getInfection_risk_id() + ",'" + infection_risk.getInfection_risk_exist() + "'," + infection_risk.getInfection_type_id() 
					+ ",'" + infection_risk.getOther_infection_risk() + "',:infection_risk_notes_val,'" + infection_risk.getEpisode_of_infection()  
					+ "','" + infection_risk.getEpisode_start_date() + "','" + infection_risk.getEpisode_finished_date() + "','" + infection_risk.getContinued_risk() 
					+ "'," + infection_risk.getIr_patient_id() + ",'" + infection_risk.getStatus() + "','" + infection_risk.getSample_collection() 
					+ "'," + infection_risk.getSample_type_id() + ",'" + infection_risk.getSample_collection_date() + "')")
					.setParameter("infection_risk_notes_val", infection_risk.getInfection_risk_notes()).executeUpdate();

			infection_risk = populateInfectionRiskVariousBits(infection_risk,whichDepartment,"INFECTION-TYPE");
			infection_risk = populateInfectionRiskVariousBits(infection_risk,whichDepartment,"SAMPLE-TYPE");
			
		} else {
			
			sessionFactory.getCurrentSession().createSQLQuery("INSERT into " + whichDepartment + "_Infection_Risk(infection_risk_id,infection_risk_exist,ir_patient_id,status,infection_risk_notes) " 
					+ "VALUES (" + infection_risk.getInfection_risk_id() + ",'" + infection_risk.getInfection_risk_exist() + "'," + infection_risk.getIr_patient_id() 
					+ ",'" + infection_risk.getStatus() + "',:infection_risk_notes_val)").setParameter("infection_risk_notes_val", infection_risk.getInfection_risk_notes()).executeUpdate();
			
		}
		return infection_risk;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InfectionType> getAllInfectionTypes(String whichDepartment) {
		return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Infection_Type_V").list();
	}

	@Override
	public InfectionType getInfectionType(String whichDepartment, int infection_type_id) {
		return (InfectionType) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Infection_Type_V WHERE infection_type_id = " + infection_type_id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampleType> getAllSampleTypes(String whichDepartment) {
		return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Sample_Type_V").list();
	}

	@Override
	public void updateInfectionRiskUsingDiff(String whichDepartment, int infection_risk_id, DiffResult diff_result) {
		this_sql_script = "";
		for(Diff<?> d: diff_result.getDiffs()) 
			for(String col_nm:sessionFactory.getClassMetadata(InfectionRisk.class).getPropertyNames()) 
				if(col_nm.equalsIgnoreCase(d.getFieldName())) {
					if(!this_sql_script.trim().isEmpty())
						this_sql_script = this_sql_script + ",";
					this_sql_script = this_sql_script + d.getFieldName() + "=:" + d.getFieldName() + "_val";
				}
		if(!this_sql_script.trim().isEmpty()) {
			this_query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE " + whichDepartment + "_Infection_Risk SET " + this_sql_script + 
					" WHERE infection_risk_id=:infection_risk_id_val");
			for(Diff<?> d: diff_result.getDiffs())
				for(String col_nm:sessionFactory.getClassMetadata(InfectionRisk.class).getPropertyNames()) 
					if(col_nm.equalsIgnoreCase(d.getFieldName())) 
						this_query.setParameter(d.getFieldName() + "_val", String.valueOf(d.getRight()));

			this_query.setParameter("infection_risk_id_val", infection_risk_id);
			this_query.executeUpdate();
		}
	}

	@Override
	public void updateInfectionRiskUsingObject(String whichDepartment, InfectionRisk ir) {
		sessionFactory.getCurrentSession().createSQLQuery("UPDATE " + whichDepartment + "_Infection_Risk SET infection_risk_exist='" + ir.getInfection_risk_exist() 
				+ "',other_infection_risk='" + ir.getOther_infection_risk() + "',infection_risk_notes=:infection_risk_notes_val,episode_of_infection='" + ir.getEpisode_of_infection() 
				+ "',episode_start_date='" + ir.getEpisode_start_date() + "',episode_finished_date='" + ir.getEpisode_finished_date() + "',continued_risk='" + ir.getContinued_risk() 
				+ "',ir_patient_id=" + ir.getIr_patient_id() +  ",infection_type_id=" + ir.getInfection_type_id()
				+ ",status='" + ir.getStatus() + "',sample_collection='" + ir.getSample_collection() + "',sample_type_id=" + ir.getSample_type_id() + ",sample_collection_date='"
				+ ir.getSample_collection_date() + "' WHERE infection_risk_id = " + ir.getInfection_risk_id())
				.setParameter("infection_risk_notes_val", ir.getInfection_risk_notes()).executeUpdate();
	}

	@Override
	public int generateInfectionRiskId(String whichDepartment, InfectionRisk infection_risk) {
		if(!(infection_risk.getInfection_risk_id() > 0)) // Change any temporary IDs to proper DB ids
			return (int) (long) ((sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(infection_risk_id) FROM " + whichDepartment + "_Infection_Risk_V").uniqueResult() != null) ?
					((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(infection_risk_id) FROM " + whichDepartment + "_Infection_Risk_V").uniqueResult()).longValue() + 1 : 1);
		else
			return infection_risk.getInfection_risk_id();
	}

	@Override
	public InfectionType getInfectionType(String whichDepartment, String short_description) {
		return (InfectionType) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Infection_Type_V WHERE upper(short_description)='" + short_description.toUpperCase() + "'").uniqueResult();
	}

	@Override
	public SampleType getSampleType(String whichDepartment, String description) {
		return (SampleType) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Sample_Type_V WHERE upper(description)='" + description.toUpperCase() + "'").uniqueResult();
	}

	@Override
	public void saveInfectionRiskVariousColumns(String whichDepartment, int infection_risk_id, String dbColumnName, String dbColumnValue) {
		sessionFactory.getCurrentSession().createSQLQuery(
				"UPDATE " + whichDepartment + "_Infection_Risk SET " + dbColumnName + "=:" + dbColumnName + "_val WHERE infection_risk_id=:infection_risk_id_val")
				.setParameter(dbColumnName + "_val", dbColumnValue).setParameter("infection_risk_id_val", infection_risk_id).executeUpdate();
	}

	@Override
	public void deleteInfectionRisk(String whichDepartment, int infection_risk_id) {
		sessionFactory.getCurrentSession().createSQLQuery(
				"DELETE " + whichDepartment + "_Infection_Risk WHERE infection_risk_id = " + infection_risk_id).executeUpdate();
	}

	@Override
	public SampleType getSampleType(String whichDepartment, int sample_type_id) {
		return (SampleType) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Sample_Type_V WHERE sample_type_id = " + sample_type_id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InfectionRisk> getInfectionRisksUsingSQL(String whichDepartment, String sql_script) {
		return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Infection_Risk_V WHERE " + sql_script).list();
	}

}
