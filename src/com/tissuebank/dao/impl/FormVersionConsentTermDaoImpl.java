package com.tissuebank.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.FormVersionConsentTermDao;
import com.tissuebank.model.ConsentTerm;
import com.tissuebank.model.FormVersion;
import com.tissuebank.model.FormVersionConsentTerm;

@Transactional
@Repository("formVersionConsentTermDao")
public class FormVersionConsentTermDaoImpl implements FormVersionConsentTermDao {

  @Autowired
  private SessionFactory sessionFactory;

@SuppressWarnings("unchecked")
@Override
public List<FormVersionConsentTerm> getAllFormVersionConsentTerms(String dept_acronym) {
	
	List<FormVersionConsentTerm> fvct_list = sessionFactory.getCurrentSession().createQuery(
			"FROM " + dept_acronym + "_Form_Version_Consent_Term_V ORDER BY fvct_id ASC").list();
	for (FormVersionConsentTerm this_fvct: fvct_list) {
		this_fvct.setConsent_term((ConsentTerm) sessionFactory.getCurrentSession().createQuery(
				"FROM " + dept_acronym + "_Consent_Terms_V WHERE consent_term_id = " + this_fvct.getConsent_term_id()).uniqueResult());
		this_fvct.setForm_version((FormVersion) sessionFactory.getCurrentSession().createQuery(
				"FROM " + dept_acronym + "_Form_Version_V WHERE form_version_id = " + this_fvct.getForm_version_id()).uniqueResult());
	}
	return fvct_list;
}
  
}
