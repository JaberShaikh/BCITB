package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.DepartmentDao;
import com.tissuebank.model.global.views.Department;
import com.tissuebank.service.DepartmentService;

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService
{
	@Autowired
	private DepartmentDao departmentDao;
	
	@Override
	public List<Department> getAllDepartments() {
		return departmentDao.getAllDepartments();
	}

	@Override
	public Department getDepartmentById(int dept_id) {
		return departmentDao.getDepartmentById(dept_id);
	}

	@Override
	public Department getDepartmentByDeptAcronym(String dept_acronym) {
		return departmentDao.getDepartmentByDeptAcronym(dept_acronym);
	}
	
}
