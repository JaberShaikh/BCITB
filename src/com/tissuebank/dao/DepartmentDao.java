package com.tissuebank.dao;

import java.util.List;

import com.tissuebank.model.global.views.Department;

public interface DepartmentDao
{
	List<Department> getAllDepartments(); 
	Department getDepartmentById(int dept_id); 
	Department getDepartmentByDeptAcronym(String dept_acronym); 
}
