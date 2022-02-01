package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.DepartmentLocationDao;
import com.tissuebank.model.global.views.Department;
import com.tissuebank.model.global.views.DepartmentLocation;
import com.tissuebank.model.global.views.UserDepartment;
import com.tissuebank.service.DepartmentLocationService;

@Service("departmentLocationService")
@Transactional
public class DepartmentLocationServiceImpl implements DepartmentLocationService
{
	@Autowired
	private DepartmentLocationDao departmentLocationDao;
	
	@Override
	public List<DepartmentLocation> getDepartmentAllLocations(List<UserDepartment> user_depts) {
		return departmentLocationDao.getDepartmentAllLocations(user_depts);
	}

	@Override
	public List<DepartmentLocation> getLocationsOfDepartment(Department dept) {
		return departmentLocationDao.getLocationsOfDepartment(dept);
	}
	
}
