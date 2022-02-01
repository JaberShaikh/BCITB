package com.tissuebank.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.DepartmentDao;
import com.tissuebank.model.global.views.Department;

@Transactional
@Repository("departmentDao")
public class DepartmentDaoImp implements DepartmentDao {

  @Autowired
  private SessionFactory sessionFactory;

@SuppressWarnings("unchecked")
@Override
public List<Department> getAllDepartments() {
	return sessionFactory.getCurrentSession().createQuery("FROM Department").list();	    
}

@Override
public Department getDepartmentById(int dept_id) {
	return (Department) sessionFactory.getCurrentSession().createQuery("FROM Department WHERE dept_id=" + dept_id).uniqueResult();	    
}

@Override
public Department getDepartmentByDeptAcronym(String dept_acronym) {
	return (Department) sessionFactory.getCurrentSession().createQuery("FROM Department WHERE dept_acronym='" + dept_acronym + "'").uniqueResult();	    
}
  
}
