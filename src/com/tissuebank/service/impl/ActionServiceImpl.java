package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.ActionDao;
import com.tissuebank.model.Action;
import com.tissuebank.model.global.views.ActionRole;
import com.tissuebank.service.ActionService;

@Service("actionService")
@Transactional
public class ActionServiceImpl implements ActionService
{
	@Autowired
	private ActionDao actionDao;

	@Override
	public Action saveAction(String whichDepartment, Action action) {
		return actionDao.saveAction(whichDepartment, action);
	}

	@Override
	public void saveActionVariousColumns(String whichDepartment, int action_id, String dbColumnName, String dbColumnValue) {
		actionDao.saveActionVariousColumns(whichDepartment, action_id, dbColumnName, dbColumnValue);
	}

	@Override
	public List<Action> getActionsFromDataId(String whichDepartment, int data_id, String data_type, String action_type, String status, String ascendingOrDescending) {
		return actionDao.getActionsFromDataId(whichDepartment, data_id, data_type, action_type, status, ascendingOrDescending);
	}

	@Override
	public Action getAction(String whichDepartment, int action_id) {
		return actionDao.getAction(whichDepartment, action_id);
	}

	@Override
	public List<ActionRole> getActionRoles(String action_type) {
		return actionDao.getActionRoles(action_type);
	}

	@Override
	public void saveActionVariousColumns(String whichDepartment, int action_id, List<String> dbColumnName, List<String> dbColumnValue) {
		actionDao.saveActionVariousColumns(whichDepartment, action_id, dbColumnName, dbColumnValue);
	}

	@Override
	public Action getAction(String whichDepartment, int action_id, String data_type) {
		return actionDao.getAction(whichDepartment, action_id, data_type);
	}

	@Override
	public void deleteAction(String whichDepartment, int data_id, String data_type) {
		actionDao.deleteAction(whichDepartment, data_id, data_type);
	}

	@Override
	public List<Action> getActions(String whatToProcess, String whichDepartment, int id_to_use, String ascendingOrDescending) {
		return actionDao.getActions(whatToProcess, whichDepartment, id_to_use, ascendingOrDescending);
	}
}