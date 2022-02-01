package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.FormVersionConsentTermDao;
import com.tissuebank.model.FormVersionConsentTerm;
import com.tissuebank.service.FormVersionConsentTermService;

@Service("formVersionConsentTermService")
@Transactional
public class FormVersionConsentTermServiceImpl implements FormVersionConsentTermService
{
	@Autowired
	private FormVersionConsentTermDao formVersionConsentTermDao;
	
	@Override
	public List<FormVersionConsentTerm> getAllFormVersionConsentTerms(String dept_acronym) {
		return formVersionConsentTermDao.getAllFormVersionConsentTerms(dept_acronym);
	}
	
}
