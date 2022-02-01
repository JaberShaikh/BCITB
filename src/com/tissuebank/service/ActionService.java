package com.tissuebank.service;

import java.util.List;

import com.tissuebank.model.Action;
import com.tissuebank.model.global.views.ActionRole;

public interface ActionService 
{
	List<Action> getActionsFromDataId(String whichDepartment, int data_id, String data_type, String action_type, String status, String ascendingOrDescending);
	List<Action> getActions(String whatToProcess, String whichDepartment, int id_to_use, String ascendingOrDescending);
	Action getAction(String whichDepartment, int action_id);
	Action getAction(String whichDepartment, int action_id, String data_type);
	Action saveAction(String whichDepartment, Action action);
	List<ActionRole> getActionRoles(String action_type); 
    void saveActionVariousColumns(String whichDepartment, int action_id, String dbColumnName, String dbColumnValue);
    void saveActionVariousColumns(String whichDepartment, int action_id, List<String> dbColumnName, List<String> dbColumnValue);
	void deleteAction(String whichDepartment, int data_id, String data_type);
}
