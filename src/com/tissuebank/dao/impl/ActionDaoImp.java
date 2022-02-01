package com.tissuebank.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.ActionDao;
import com.tissuebank.model.Action;
import com.tissuebank.model.global.views.ActionRole;
import com.tissuebank.util.BCITBVariousVariables;

@Transactional
@Repository("actionDao")
public class ActionDaoImp implements ActionDao {

	@Autowired
	private SessionFactory sessionFactory;

	String sql_script = "";
	Query this_query;
	
	@Override
	public Action saveAction(String whichDepartment, Action action) {
		action.setAction_id((int) (long) ((sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT MAX(action_id) FROM " + whichDepartment + "_Action_V").uniqueResult() != null) ?
				((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT MAX(action_id) FROM " + whichDepartment + "_Action_V").uniqueResult()).longValue() + 1 : 1));
		sessionFactory.getCurrentSession().createSQLQuery(
				"INSERT into " + whichDepartment + "_Action(action_id,data_id,data_type,action_type,notes,status,created_date_time,created_by,description) "
						+ "VALUES (" + action.getAction_id() + "," + action.getData_id() + ",'" + action.getData_type() + "','" 
						+ action.getAction_type() + "',:actionNotes,'" + action.getStatus() + "','" + action.getCreated_date_time() 
						+ "'," + action.getCreated_by() + ",'" + action.getDescription() + "')").setParameter("actionNotes", action.getNotes()).executeUpdate();
		return action;
	}

	@Override
	public void saveActionVariousColumns(String whichDepartment, int action_id, String dbColumnName, String dbColumnValue) 
	{
		if (action_id > 0) {
			sql_script = "";
			for (String col_name: sessionFactory.getClassMetadata(Action.class).getPropertyNames())
				if (col_name.equalsIgnoreCase(dbColumnName))
					if(sql_script.trim().isEmpty())
						sql_script = dbColumnName + "=:" + dbColumnName + "_val";
					else 
						sql_script = sql_script + ", " + dbColumnName + "=:" + dbColumnName + "_val";

			if(!sql_script.trim().isEmpty()) {
				this_query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE " + whichDepartment + "_Action SET " 
					+ sql_script + " WHERE action_id=:action_id_val");
				for (String col_name: sessionFactory.getClassMetadata(Action.class).getPropertyNames())
					if (col_name.equalsIgnoreCase(dbColumnName)) {
						this_query.setParameter(dbColumnName + "_val", dbColumnValue);
						this_query.setParameter("action_id_val", action_id);
						this_query.executeUpdate();
					}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> getActionsFromDataId(String whichDepartment, int data_id, String data_type, String action_type, String status, String ascendingOrDescending) {
		
		sql_script = "FROM " + whichDepartment + "_Action_V WHERE data_id=" + data_id;
		if (!data_type.trim().isEmpty())
			sql_script = sql_script + " AND upper(data_type)='" + data_type.toUpperCase() + "'";
		if (!action_type.trim().isEmpty())
			sql_script = sql_script + " AND upper(action_type)='" + action_type.toUpperCase() + "'";
		if (!status.trim().isEmpty())
			sql_script = sql_script + " AND upper(status)='" + status.toUpperCase() + "'";
		if (!ascendingOrDescending.trim().isEmpty())
			sql_script = sql_script + " ORDER BY action_id " + ascendingOrDescending.toUpperCase();
		
		return sessionFactory.getCurrentSession().createQuery(sql_script).list();
	
	}

	@Override
	public Action getAction(String whichDepartment, int action_id) {
		return (Action) sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Action_V WHERE action_id=" + action_id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActionRole> getActionRoles(String action_type) {
		return sessionFactory.getCurrentSession().createQuery("FROM ActionRole WHERE action_type='" + action_type + "'").list();	    
	}

	@Override
	public void saveActionVariousColumns(String whichDepartment, int action_id, List<String> dbColumnName, List<String> dbColumnValue) {
		sql_script = "";
		for(int i=0;i<dbColumnName.size();i++) 
			if(sql_script.trim().isEmpty())
				sql_script = dbColumnName.get(i) + "=:" + dbColumnName.get(i) + "_val";
			else 
				sql_script = sql_script + ", " + dbColumnName.get(i) + "=:" + dbColumnName.get(i) + "_val";

		if(!sql_script.trim().isEmpty()) {
			this_query = sessionFactory.getCurrentSession().createSQLQuery(
					"UPDATE " + whichDepartment + "_Action SET " + sql_script + " WHERE action_id=:action_id_val");
			for(int i=0;i<dbColumnName.size();i++) 
				this_query.setParameter(dbColumnName.get(i) + "_val", dbColumnValue.get(i));
			this_query.setParameter("action_id_val", action_id);
			this_query.executeUpdate();
		}
	}

	@Override
	public Action getAction(String whichDepartment, int action_id, String data_type) {
		return (Action) sessionFactory.getCurrentSession().createQuery(
				"FROM " + whichDepartment + "_Action_V WHERE action_id=" + action_id + " AND data_type='" + data_type + "'").uniqueResult();
	}

	@Override
	public void deleteAction(String whichDepartment, int data_id, String data_type) {
		sessionFactory.getCurrentSession().createSQLQuery(
				"DELETE " + whichDepartment + "_Action WHERE data_id = " + data_id + " AND data_type='" + data_type + "'").executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> getActions(String whatToProcess, String whichDepartment, int id_to_use, String ascendingOrDescending) {
		switch (whatToProcess) {
		case BCITBVariousVariables.active:
			return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment 
					+ "_Action_V WHERE upper(status) = '" + BCITBVariousVariables.active.toUpperCase() 
					+ "' ORDER BY action_id " + ascendingOrDescending).list();
		case "LOCKED-ACTIONS":
			return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Action_V WHERE locked_by = " + id_to_use +  " ORDER BY action_id " + ascendingOrDescending).list();
		default:
			return sessionFactory.getCurrentSession().createQuery("FROM " + whichDepartment + "_Action_V ORDER BY action_id " + ascendingOrDescending).list();
		}
	}	

}