package com.tissuebank.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.RoleDao;
import com.tissuebank.model.global.views.Role;

@Transactional
@Repository("roleDao")
public class RoleDaoImp implements RoleDao {

@Autowired
private SessionFactory sessionFactory;

@SuppressWarnings("unchecked")
@Override
public List<Role> getAllRoles() {
	return sessionFactory.getCurrentSession().createQuery("FROM Role").list();	    
}

public Role getRoleById(int role_id) {
	return (Role) sessionFactory.getCurrentSession().createQuery("FROM Role WHERE role_id=" + role_id).uniqueResult();	    
}

}
