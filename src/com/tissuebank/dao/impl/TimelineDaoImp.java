package com.tissuebank.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.TimelineDao;
import com.tissuebank.model.Timeline;
import com.tissuebank.util.BCITBEncryptDecrypt;
import com.tissuebank.util.BCITBVariousVariables;

@Transactional
@Repository("timelineDao")
public class TimelineDaoImp implements TimelineDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveTimeline(String which_department, int data_id, int user_id, int role_id, String status, String notes, 
			String date_of_entry, String data_type, String description, String is_encrypted, int tl_patient_id) {
		
		int next_timeline_id = (int) (long) ((sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(timeline_id) FROM " + which_department + "_Timeline_V").uniqueResult() != null) ?
				((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(timeline_id) FROM " + which_department + "_Timeline_V").uniqueResult()).longValue() + 1 : 1);
		
		if(is_encrypted.equalsIgnoreCase(BCITBVariousVariables.yes))
			sessionFactory.getCurrentSession().createSQLQuery(
					"INSERT into " + which_department + "_Timeline(timeline_id,data_id,user_id,role_id,status,notes,date_of_entry,data_type,description,is_encrypted,tl_patient_id) " + 
					"VALUES (" + next_timeline_id + "," + data_id + "," + user_id + "," + role_id + ",'" + status + 
					"',:timelineNotes,'" + date_of_entry + "','" + data_type + "',:description_val,'" + is_encrypted + "'," + tl_patient_id + ")")
					.setParameter("timelineNotes", BCITBEncryptDecrypt.encrypt(notes, data_type)).setParameter("description_val", description).executeUpdate();
		else
			sessionFactory.getCurrentSession().createSQLQuery(
					"INSERT into " + which_department + "_Timeline(timeline_id,data_id,user_id,role_id,status,notes,date_of_entry,data_type,description,is_encrypted,tl_patient_id) " + 
					"VALUES (" + next_timeline_id + "," + data_id + "," + user_id + "," + role_id + ",'" + status + 
					"',:timelineNotes,'" + date_of_entry + "','" + data_type + "',:description_val,'" + is_encrypted + "'," + tl_patient_id + ")")
					.setParameter("timelineNotes", notes).setParameter("description_val", description).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Timeline> getTimeline(String whatToProcess, String which_department, String whichDataType, int data_id, String asc_or_desc) {
		
		String sql_statement = "FROM " + which_department + "_Timeline_V";
		switch (whatToProcess) {
		case "PATIENT-ID":
			sql_statement = sql_statement + " WHERE tl_patient_id=" + data_id;
			break;
		default:
			if (data_id > 0 && !whichDataType.trim().isEmpty())
				sql_statement = sql_statement + " WHERE data_id=" + data_id + " AND upper(data_type)='" + whichDataType.toUpperCase() + "'";
			else if (!whichDataType.trim().isEmpty())
				sql_statement = sql_statement + " WHERE upper(data_type)='" + whichDataType.toUpperCase() + "'";
			break;
		}
		
		if (!asc_or_desc.trim().isEmpty())
			sql_statement = sql_statement + " ORDER BY timeline_id " + asc_or_desc.toUpperCase();

		return sessionFactory.getCurrentSession().createQuery(sql_statement).list();
		
	}

	@Override
	public void deleteTimeline(String which_department, int data_id, String data_type) {
		sessionFactory.getCurrentSession().createSQLQuery("DELETE " + which_department + "_Timeline WHERE data_id = " + 
				data_id + " AND data_type='" + data_type + "'").executeUpdate();
	}

	@Override
	public Timeline getTimeline(String which_department, String whichDataType, int data_id, String status) {
		return (Timeline) sessionFactory.getCurrentSession().createQuery("FROM " + which_department + "_Timeline_V WHERE data_id=" 
				+ data_id + " AND upper(data_type)='" + whichDataType.toUpperCase() + "'"
				+ " AND upper(status)='" + status.toUpperCase() + "'").uniqueResult();
	}
}
