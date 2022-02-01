package com.tissuebank.dao;

import java.util.List;

import com.tissuebank.model.FormVersionConsentTerm;

public interface FormVersionConsentTermDao
{
	List<FormVersionConsentTerm> getAllFormVersionConsentTerms(String dept_acronym); 
}
