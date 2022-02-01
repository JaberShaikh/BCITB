package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.FormTypeVersionConsentTermDao;
import com.tissuebank.model.FormTypeVersionConsentTerm;
import com.tissuebank.service.FormTypeVersionConsentTermService;

@Service("formTypeConsentTermService")
@Transactional
public class FormTypeVersionConsentTermServiceImpl implements FormTypeVersionConsentTermService
{
	@Autowired
	private FormTypeVersionConsentTermDao formTypeConsentTermDao;
	
	@Override
	public List<FormTypeVersionConsentTerm> getAllFormTypeVersionConsentTerms(String dept_acronym) {
		return formTypeConsentTermDao.getAllFormTypeVersionConsentTerms(dept_acronym);
	}
	
}
