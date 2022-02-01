package com.tissuebank.service;

import java.util.List;

import com.tissuebank.model.global.views.Department;

public interface DepartmentService 
{
	List<Department> getAllDepartments(); 
	Department getDepartmentById(int dept_id); 
	Department getDepartmentByDeptAcronym(String dept_acronym); 
}
