package com.tissuebank.service;

import java.util.List;

import com.tissuebank.model.AuditSampleType;
import com.tissuebank.model.global.tables.AuditProcess;
import com.tissuebank.model.global.tables.AuditTeam;

public interface AuditProcessService 
{
	AuditProcess getAuditProcess(); 
	void updateAuditProcess(int auditProcessId, String columnToUpdate, String valueToUpdate); 
	List<AuditSampleType> getAllAuditSampleTypes(String which_department);
	List<AuditTeam> getAuditTeams();
	AuditSampleType getAuditSampleType(String which_department, int aud_sample_type_id);
}
