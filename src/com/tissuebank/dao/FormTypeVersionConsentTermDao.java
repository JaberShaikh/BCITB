package com.tissuebank.dao;

import java.util.List;

import com.tissuebank.model.FormTypeVersionConsentTerm;

public interface FormTypeVersionConsentTermDao
{
	List<FormTypeVersionConsentTerm> getAllFormTypeVersionConsentTerms(String dept_acronym); 
}
