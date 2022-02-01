package com.tissuebank.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.AuditProcessDao;
import com.tissuebank.model.AuditSampleType;
import com.tissuebank.model.global.tables.AuditProcess;
import com.tissuebank.model.global.tables.AuditTeam;

@Transactional
@Repository("auditProcessDao")
public class AuditProcessDaoImp implements AuditProcessDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public AuditProcess getAuditProcess() {
		return (AuditProcess) sessionFactory.getCurrentSession().createQuery("FROM AuditProcess").uniqueResult();
	}

	@Override
	public void updateAuditProcess(int auditProcessId, String columnToUpdate, String valueToUpdate) {
		for (String col_name: sessionFactory.getClassMetadata(AuditProcess.class).getPropertyNames())
			if (col_name.equalsIgnoreCase(columnToUpdate))
				sessionFactory.getCurrentSession().createSQLQuery("UPDATE TB_Audit_Process SET " + columnToUpdate 
						+ "=:" + columnToUpdate + "_val WHERE audit_process_id=:audit_process_id_val")
					.setParameter(columnToUpdate + "_val", valueToUpdate)
					.setParameter("audit_process_id_val", auditProcessId).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditSampleType> getAllAuditSampleTypes(String which_department) {
		return sessionFactory.getCurrentSession().createQuery("FROM " + which_department + "_Audit_Sample_Type_V").list();
	}

	@Override
	public AuditSampleType getAuditSampleType(String which_department, int aud_sample_type_id) {
		return (AuditSampleType) sessionFactory.getCurrentSession().createQuery(
				"FROM " + which_department + "_Audit_Sample_Type_V WHERE audit_sample_type_id = " + aud_sample_type_id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditTeam> getAuditTeams() {
		return sessionFactory.getCurrentSession().createQuery("FROM AuditTeam").list();
	}

}