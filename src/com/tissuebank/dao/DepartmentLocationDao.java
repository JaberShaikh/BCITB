package com.tissuebank.dao;

import java.util.List;

import com.tissuebank.model.global.views.Department;
import com.tissuebank.model.global.views.DepartmentLocation;
import com.tissuebank.model.global.views.UserDepartment;

public interface DepartmentLocationDao
{
	List<DepartmentLocation> getDepartmentAllLocations(List<UserDepartment> user_depts);
	List<DepartmentLocation> getLocationsOfDepartment(Department dept);
}
