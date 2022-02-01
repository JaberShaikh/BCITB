package com.tissuebank.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.FormVersionDao;
import com.tissuebank.model.FormVersion;

@Transactional
@Repository("formVersionDao")
public class FormVersionDaoImp implements FormVersionDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormVersion> getAllFormVersions(String dept_acronym) {
		return sessionFactory.getCurrentSession().createQuery("FROM " + dept_acronym + "_Form_Version_V").list();	    
	}

	@Override
	public FormVersion getFormVersion(String dept_acronym, int form_version_id) {
		return (FormVersion) sessionFactory.getCurrentSession().createQuery("FROM " + dept_acronym 
				+ "_Form_Version_V WHERE form_version_id=" + form_version_id).uniqueResult();	    
	}

	@Override
	public FormVersion getFormVersion(String dept_acronym, String form_type, String import_description) {
		return (FormVersion) sessionFactory.getCurrentSession().createQuery("FROM " + dept_acronym 
				+ "_Form_Version_V WHERE upper(form_type) LIKE '%" + form_type.toUpperCase() 
				+ "%' AND upper(import_description)='" + import_description.toUpperCase() + "'").uniqueResult();	    
	}
}
