package com.tissuebank.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.DepartmentLocationDao;
import com.tissuebank.model.global.views.Department;
import com.tissuebank.model.global.views.DepartmentLocation;
import com.tissuebank.model.global.views.UserDepartment;

@Transactional
@Repository("departmentLocationDao")
public class DepartmentLocationDaoImp implements DepartmentLocationDao {

  @Autowired
  private SessionFactory sessionFactory;

@SuppressWarnings({"unchecked"})
@Override
public List<DepartmentLocation> getDepartmentAllLocations(List<UserDepartment> user_depts) {
	List<DepartmentLocation> dept_locations = new ArrayList<DepartmentLocation>();
	List<DepartmentLocation> departmentLocations = null;
	for(UserDepartment dept:user_depts)
	{
		departmentLocations = sessionFactory.getCurrentSession().createQuery("FROM DepartmentLocation WHERE dept_id = " + dept.getDept_id()).list();
		for(DepartmentLocation departmentLocation: departmentLocations)
			dept_locations.add(departmentLocation);
	}
	return dept_locations;
}

@SuppressWarnings("unchecked")
@Override
public List<DepartmentLocation> getLocationsOfDepartment(Department dept) {
	return sessionFactory.getCurrentSession().createQuery("FROM DepartmentLocation WHERE dept_id = " + dept.getDept_id()).list();
}

}
