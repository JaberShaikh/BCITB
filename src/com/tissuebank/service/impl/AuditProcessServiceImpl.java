package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.AuditProcessDao;
import com.tissuebank.model.AuditSampleType;
import com.tissuebank.model.global.tables.AuditProcess;
import com.tissuebank.model.global.tables.AuditTeam;
import com.tissuebank.service.AuditProcessService;

@Service("auditProcessService")
@Transactional
public class AuditProcessServiceImpl implements AuditProcessService
{
	@Autowired
	private AuditProcessDao auditProcessDao;

	@Override
	public AuditProcess getAuditProcess() {
		return auditProcessDao.getAuditProcess();
	}

	@Override
	public void updateAuditProcess(int auditProcessId, String columnToUpdate, String valueToUpdate) {
		auditProcessDao.updateAuditProcess(auditProcessId, columnToUpdate, valueToUpdate);
	}

	@Override
	public List<AuditSampleType> getAllAuditSampleTypes(String which_department) {
		return auditProcessDao.getAllAuditSampleTypes(which_department);
	}

	@Override
	public AuditSampleType getAuditSampleType(String which_department, int aud_sample_type_id) {
		return auditProcessDao.getAuditSampleType(which_department, aud_sample_type_id);
	}

	@Override
	public List<AuditTeam> getAuditTeams() {
		return auditProcessDao.getAuditTeams();
	}

}