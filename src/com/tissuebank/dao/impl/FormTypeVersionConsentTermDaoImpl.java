package com.tissuebank.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.FormTypeVersionConsentTermDao;
import com.tissuebank.model.ConsentFormType;
import com.tissuebank.model.ConsentTerm;
import com.tissuebank.model.FormTypeVersionConsentTerm;

@Transactional
@Repository("formTypeConsentTermDao")
public class FormTypeVersionConsentTermDaoImpl implements FormTypeVersionConsentTermDao {

  @Autowired
  private SessionFactory sessionFactory;

@Override
public List<FormTypeVersionConsentTerm> getAllFormTypeVersionConsentTerms(String dept_acronym) {
	@SuppressWarnings("unchecked")
	List<FormTypeVersionConsentTerm> ftct_list = sessionFactory.getCurrentSession().createQuery(
			"FROM " + dept_acronym + "_Form_Type_Version_Consent_Term_V ORDER BY FTVCT_ID ASC").list();
	for (FormTypeVersionConsentTerm this_ftct: ftct_list) {
		this_ftct.setConsent_form_type((ConsentFormType) sessionFactory.getCurrentSession().createQuery(
				"FROM " + dept_acronym + "_Consent_Form_Type_V WHERE consent_form_type_id = " + this_ftct.getForm_type_id()).uniqueResult());
		this_ftct.setConsent_term((ConsentTerm) sessionFactory.getCurrentSession().createQuery(
				"FROM " + dept_acronym + "_Consent_Terms_V WHERE consent_term_id = " + this_ftct.getConsent_term_id()).uniqueResult());
	}
	return ftct_list;
}
  
}
